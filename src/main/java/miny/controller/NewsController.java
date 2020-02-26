package miny.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.taglibs.standard.tag.common.core.ParamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import miny.common.CValue;
import miny.common.exception.MyException;
import miny.common.exception.MyException.MyError;
import miny.service.ActionService;
import miny.service.NewsService;
import miny.vo.CallResult;

@Controller
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private ActionService actionService;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/get/news")
	@ResponseBody
	public CallResult getSearchNaver(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();
		StringBuffer sb = new StringBuffer();
		String query = ServletRequestUtils.getStringParameter(request, "query");
		
		Map<String, Object> params = new HashMap<>();
//		params.put("query", query);
		params.put("display", 5);
		Map<String, String> header = new HashMap<>();
		header.put("X-Naver-Client-Id", "FT8pY2Ghyo6olzPxn9k5");
		header.put("X-Naver-Client-Secret", "1wJ_AHuiKX");
		
		logger.debug("params={}",params);
		
		try {
			Map<String, Object> returnData = new HashMap<>();
			
			if(query == null || query == "") {
				throw new MyException(MyError.NO_REQUIED_PARAMS);
			} else {
				logger.debug("@Controller Thread id=[{}]", Thread.currentThread().getId());
//				List<Map> data = actionService.getSearchNaver(params, header);
				
				String[] queryList = query.split(",");
				if(queryList.length > 1)
					params.put("display", 2);
				
				if(queryList.length > 5) {
					sb.append("키워드는 최대 5개까지 설정가능해요 ˃̣̣̣̣̣̣︿˂̣̣̣̣̣̣ ");
					sb.append(CValue.newlineStr);
					sb.append("키워드를 조금 줄여주세요~");
					returnData.put("message", sb.toString());
					result.setData(returnData);
					
					throw new MyException(MyError.SUCCESS);
				}
					
				List<String> msg_list = new ArrayList<>();
				List<String> url_list = new ArrayList<>();
				for(String q : queryList) {
					params.put("query", q);
					Map<String, Object> data = newsService.getSearchNaver_modi2(params, header);
					msg_list.addAll((List)data.get("list"));
					url_list.addAll((List)data.get("url"));
				}
				logger.debug("msg_list: {}", new Gson().toJson(msg_list));
				
				//중복 제거
				HashSet hs = new HashSet(msg_list);
				msg_list = new ArrayList<String>(hs);
				StringBuffer news = new StringBuffer();
				for(String s : msg_list) {
					news.append(s).append(CValue.newlineStr).append(CValue.newlineStr);
				}
				String finalNews = news.toString();
				
				HashSet hs_url = new HashSet(url_list);
				url_list = new ArrayList<String>(hs_url);
				
				//url 쇼트너
				Map<String, Object> urlMap = actionService.urlShortner(url_list);
				if(urlMap != null) {
					for(String url : url_list) {
						String trans_url = (String) ((Map<String, Object>)urlMap.get("data")).get(url);
						finalNews = finalNews.replace(url, trans_url);
					}
				}
				
				if(msg_list.size() == 0) {
					sb.append("키워드에 해당하는 뉴스가 없어요 :(");
					sb.append(CValue.newlineStr);
					sb.append("원하는 키워드가 ").append(query).append(" 이게 맞나요? 아닐 경우, 다시 설정해주세요~");
				} else {
					sb.append("당신이 원하는 핫!한 '뉴스'").append(CValue.newlineStr).append(CValue.newlineStr);
					sb.append(finalNews);
				}
				returnData.put("message", sb.toString());
				result.setData(returnData);
				
				throw new MyException(MyError.SUCCESS);
			}
		} catch (MyException e) {
			result.getError().setCode(e.getCode());
			result.getError().setMsg(e.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			result.getError().setCode(MyException.systemErrorCode);
			result.getError().setMsg(MyException.systemErrorMsg);
		}

		return result;
	}
	
	@RequestMapping(value = "/get/comments")
	@ResponseBody
	public CallResult getCommentsNews(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();
		
		String oid = ServletRequestUtils.getStringParameter(request, "oid", null);
		String aid = ServletRequestUtils.getStringParameter(request, "aid", null);

		try {
			
			if(oid == null || aid == null) {
				throw new MyException(MyError.NO_REQUIED_PARAMS);
			}
			
			String param = "oid="+oid+"&aid="+aid+"&m_url=/comment/all.nhn&m_view=1";
			String url = "http://news.naver.com/main/read.nhn?"+param;
			result.setData(newsService.getComments(url));
			
		} catch (MyException e) {
			result.getError().setCode(e.getCode());
			result.getError().setMsg(e.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			result.getError().setCode(MyException.systemErrorCode);
			result.getError().setMsg(MyException.systemErrorMsg);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/get/reaction")
	@ResponseBody
	public CallResult getReactionNews(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();
		
		String oid = ServletRequestUtils.getStringParameter(request, "oid", null);
		String aid = ServletRequestUtils.getStringParameter(request, "aid", null);

		try {
			
			if(oid == null || aid == null) {
				throw new MyException(MyError.NO_REQUIED_PARAMS);
			}
			
			String param = "oid="+oid+"&aid="+aid;
			String url = "http://news.naver.com/main/read.nhn?"+param;
			result.setData(newsService.getReactions(url));
			
		} catch (MyException e) {
			result.getError().setCode(e.getCode());
			result.getError().setMsg(e.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			result.getError().setCode(MyException.systemErrorCode);
			result.getError().setMsg(MyException.systemErrorMsg);
		}
		
		return result;
	}
}
