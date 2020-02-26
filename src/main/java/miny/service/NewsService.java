package miny.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;

import miny.common.CValue;
import miny.common.apis.CallApi;
import miny.common.util.CUtil;
import retrofit2.Call;
import retrofit2.Response;

@Service
public class NewsService {

	@Autowired
	private CallApi api;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private WebClient webClient = null;
	
	public void initWebClient() {
		webClient = new WebClient();
		
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getCookieManager().setCookiesEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		webClient.waitForBackgroundJavaScript(1000);
	}
	
	public void closeWebClient() {
		webClient.close();
	}
	

	// 네이버 뉴스 검색 - 기존
	public Map<String, Object> getSearchNaver(Map<String, Object> map, Map<String, String> header) {
		Map<String, Object> returnData = new HashMap<>();
		List<Map> resultList = new ArrayList<>();
		try {
			Call<HashMap<String, Object>> call = api.getCall().getNaverNews(map, header);
			Response<HashMap<String, Object>> body = call.execute();
			HashMap<String, Object> result = body.body();

			if (result == null) {
				logger.error(body.errorBody().string());
				returnData.put("message", "현재 해당 기능을 사용할 수 없어요\n이따가 다시 시도해주세요ㅠㅠ");
				return returnData;
			} else if (result.get("items") != null) {
				resultList = (List<Map>) result.get("items");
				StringBuffer message = new StringBuffer();
				message.append("당신이 원하는 핫!한 '뉴스'").append(CValue.newlineStr).append(CValue.newlineStr);

				for (Map news : resultList) {
					String url = (String) news.get("originallink");
					String title = (String) news.get("title");
					title = reduceTag(title);
					message.append(title).append(" ").append(url);
					message.append(CValue.newlineStr).append(CValue.newlineStr);
				}

//				message.append("매일 뉴스를 받고 싶다면 문랩알림을 설정해주세요 <3");
				returnData.put("message", message.toString());
				return returnData;
			} else {
				logger.error("{}", new Gson().toJson(result));
				returnData.put("message", "다른 키워드로 검색해보세요ㅠㅜ");
				return returnData;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	public Map<String, Object> getSearchNaver_modi2(Map<String, Object> map, Map<String, String> header) {
		Map<String, Object> returnData = new HashMap<>();
		List<Map> resultList = new ArrayList<>();
		try {
			Call<HashMap<String, Object>> call = api.getCall().getNaverNews(map, header);
			Response<HashMap<String, Object>> body = call.execute();
			HashMap<String, Object> result = body.body();

			if (result == null) {
				logger.error(body.errorBody().string());
				returnData.put("message", "현재 해당 기능을 사용할 수 없어요"+CValue.newlineStr+"잠시후 다시 시도해주세요ㅠㅠ");
				return returnData;
			} else if (result.get("items") != null) {
				resultList = (List<Map>) result.get("items");
				
				List<String> msg_list = new ArrayList<>();
				List<String> url_list = new ArrayList<>();
				for (Map news : resultList) {
					String url = (String) news.get("originallink");
					if(url.equals("")) url = (String) news.get("link");
					String title = (String) news.get("title");
					title = reduceTag(title);
					msg_list.add(title+" "+url);
					url_list.add(url);
				}
				returnData.put("url", url_list);
				returnData.put("list", msg_list);
				return returnData;
			} else {
				logger.error("{}", new Gson().toJson(result));
				returnData.put("message", "다른 키워드로 검색해보세요ㅠㅜ");
				return returnData;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}

	public String getSummaryNews(String aid, String oid) {
		String summary = null;
		try {
			Call<HashMap<String, Object>> call = api.getCall().getSummaryNews(oid, aid);
			Response<HashMap<String, Object>> body = call.execute();
			HashMap<String, Object> result = body.body();

			if (result == null) {
				logger.error(body.errorBody().string());
				return null;
			} else if (!result.get("error_msg").equals("null")) {
				logger.error((String) result.get("error_msg"));
				return null;
			} else {
				summary = (String) result.get("summary");
				return reduceTag(summary);
			}

		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		return summary;
	}
	
	public ArrayList<Map<String, String>> getComments(String url) {
		ArrayList<Map<String, String>> list = new ArrayList<>();
		try {
			initWebClient();
			
			HtmlPage page = (HtmlPage)webClient.getPage(url);
			HtmlDivision div = page.getFirstByXPath("//div[@class='u_cbox_paginate']");
			DomNodeList<HtmlElement> links = div.getElementsByTagName("a");
			int i = 0;
			while(i<20) {
				page = links.get(0).click();
				i++;
				if(links.get(0).asXml().contains("display: none;"))
					break;
			}
			
			Document doc = Jsoup.parse(page.asXml());
			
			Elements comments = doc.select("div.u_cbox_content_wrap").select("ul.u_cbox_list").select("li");
			for (Element comm : comments) {
				HashMap<String, String> input = new HashMap<>();
				if(!comm.select("span.u_cbox_contents").text().equals("")) {
					input.put("name", comm.select("span.u_cbox_nick").text());
					input.put("text", comm.select("span.u_cbox_contents").text());
					input.put("date", comm.select("span.u_cbox_date").text());
					list.add(input);
				}
			}
			logger.info("{}", new Gson().toJson(list));
			
			StringBuffer sb = new StringBuffer();
			for(Map<String, String> map : list) {
				sb.append(map.get("text")).append(CValue.newlineStr);
			}
			filesave(sb.toString());
			closeWebClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void filesave(String text) {
		try {
			File file = new File("test.txt");
			FileWriter fw = new FileWriter(file);
			fw.write(text);
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
	}

	public Map<String, String> getReactions(String url) {
		Map<String, String> emotion = new HashMap<>();
		try {
			initWebClient();
			
			HtmlPage page = (HtmlPage)webClient.getPage(url);
			Document doc = Jsoup.parse(page.asXml());
			
			Elements feels = doc.select("ul.u_likeit_layer._faceLayer").select("li.u_likeit_list");
			
			for (Element feel : feels) {
				if(feel.select("span.u_likeit_list_name._label").text().equals("좋아요")) {
					emotion.put("like", feel.select("span.u_likeit_list_count._count").text());
				} else if(feel.select("span.u_likeit_list_name._label").text().equals("훈훈해요")) {
					emotion.put("warm", feel.select("span.u_likeit_list_count._count").text());
				} else if(feel.select("span.u_likeit_list_name._label").text().equals("슬퍼요")) {
					emotion.put("sad", feel.select("span.u_likeit_list_count._count").text());
				} else if(feel.select("span.u_likeit_list_name._label").text().equals("화나요")) {
					emotion.put("angry", feel.select("span.u_likeit_list_count._count").text());
				}
			}
			closeWebClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emotion;
	}


	public String reduceTag(String text) {
		text = text.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		text = text.replaceAll(".*(&nbsp;|&amp;|&lt;|&gt;|&copy;).*", "");
		text = text.replaceAll("&quot;", "'");
		return text;
	}

}
