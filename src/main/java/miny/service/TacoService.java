package miny.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.javascript.host.Element;
import com.google.gson.Gson;

import miny.common.CValue;
import miny.common.apis.CallApi;
import miny.dao.TacoDAO;
import miny.vo.TacoVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Service
public class TacoService {
	@Autowired
	private CallApi api;
	@Autowired
	private SqlSession sqlSession;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String getIntroMsg() {
		String[] msg = {
				"요즘 핫플 문봇이 알려줄게!",
				"여기 인스타에서 유명하다며?",
				"여기서 인생사진 남길 수 있음 개이득 :)",
				"여기서 데이트하면 좋겠다 <3",
				"여기 분위기 괜찮지? 안가봤으면 믿고 가봐~",
				"여기 가봤어? 분위기 쩐다는뎅"
		};
		
		int index = (int)(Math.random()*msg.length);
		return msg[index]+CValue.newlineStr;
	}
	
	public String rebuildStore(String store) {
		String alpha_reg = "[a-zA-Z\\s]+";
		if(Pattern.matches(alpha_reg, store)) {
			return store;
		}
		
		String regex = "[a-zA-Z가-힣\\s]+";
		if(Pattern.matches(regex, store)) {
			store = store.replaceAll("[a-zA-z]", ""); //영어한글 섞여있을 때 영어 제외
			store = store.replaceAll(" ", "");
			return store;
		}
		
		return store;
	}
	
	//카페
	public Map<String,Object> getCafe() {
		Map<String, Object> map = new HashMap<>();
		
		try {
			Call<String> call = api.getCallString().getCafe();
			String result = call.execute().body();
			Document doc = Jsoup.parse(result);
			
			Elements list = doc.select("div.t-entry-visual-tc");// ("div.search-list-item-row.second");
			int size = list.size();
			
			String store = null;
			while(store == null || store.equals("")) {
				int index = (int)(Math.random()*size);
				
				String area = list.get(index).select("div.t-entry p span").text();
				while(area.equals("이탈리아")) {
					index = (int)(Math.random()*size);
					area = list.get(index).select("div.t-entry p span").text();
					
					store = list.get(index).select("div.t-entry h3").text();
					logger.info("store1: " +  store);
					
					String msg = getIntroMsg();
					
					Map<String, Object> data = getFBlocationId2(store);
					data.put("text", msg);
//					tacoBotSend(data);
					
					msg += data.get("name")+CValue.newlineStr;
					msg += data.get("url");
					map.put("message", msg);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	//음식점
	public Map<String, Object> getRestaurantDC(Map<String, Object> param) {
		Map<String, Object> map = new HashMap<>();
		try {
			Call<String> call = api.getCallString().getRestaurantDC(param);
			String result = call.execute().body();
			Document doc = Jsoup.parse(result);
			
			Elements restaurants = doc.select("dc-restaurant");// ("div.search-list-item-row.second");
			int size = restaurants.size();
			
			int index = 0;
			while(index == 0)
				index = (int)(Math.random()*size);
			
			String store = restaurants.get(index).select("div.dc-restaurant-name").text();
			
			String msg = getIntroMsg();
			Map<String, Object> data = getFBlocationId2(store);
			data.put("text", msg);
			tacoBotSend(data);
			
			msg += data.get("name")+CValue.newlineStr;
			msg += data.get("url");
			map.put("message", msg);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public Map<String, Object> getFBlocationId(String name) {
		Map<String, Object> map = new HashMap<>();
		String url = "";
		try {
			Map<String, Object> params = new HashMap<>();
			name = rebuildStore(name);
			
			logger.info("store2: " + name);
			params.put("fields","name,checkins,location,category,website,engagement,restaurant_services,parking,overall_star_rating,hours,about,description,price_range,single_line_address,phone");
			params.put("q", name);
			Call<HashMap<String, Object>> call = api.getCall().getFBlocationID(params);
			Response<HashMap<String, Object>> resp = call.execute();
			if(resp.code() != 200) {
				logger.error(resp.message());
				
				url = "https://www.instagram.com/explore/tags/"+name;
				map.put("name", name);
				map.put("url", url);
				return map;
			}
			HashMap<String, Object> result = resp.body();
			
			List data_list = (List)result.get("data");
			
			if(data_list.isEmpty() || data_list.size() < 1) {
				//페이스북에 장소가 등록되어 있지 않을 경우
				logger.error("data list is empty");
				
				url = "https://www.instagram.com/explore/tags/"+name.replaceAll(" ", "");
				map.put("name", name);
				map.put("url", url);
				return map;
			}
			
			Map data = (Map)data_list.get(0);
			url = "https://www.instagram.com/explore/locations/" + data.get("id");
			map.put("name", data.get("name"));
			map.put("url", url);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return map;
	}
	
	public Map<String, Object> tacoBotSend(Map<String, Object> data) {
		Map<String, Object> result = new HashMap<>();
		try {
			Map<String, Object> param = new HashMap<>();
			
			String msg = data.get("text") + " " + data.get("name")+CValue.newlineStr + "<" + data.get("url") + ">";
			param.put("text", msg);
			param.put("unfurl_links", true);
			//"unfurl_links": true
			
			
			Call<Map<String, Object>> call = api.getCall().tacoBotSend(param);
			call.enqueue(new Callback<Map<String, Object>>() {
				@Override
				public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
					//					logger.info("onResponse");
					Map<String, Object> map = response.body();
//					boolean result_ok = (boolean)resultData.get("ok");
//					result.put("result", resultData);
					//					logger.info("body:{},{}", map);
				}

				@Override
				public void onFailure(Call<Map<String, Object>> call, Throwable t) {
					logger.error("onFailure");
				}
			});

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	public Map<String, Object> getFBlocationId2(String name) {
		Map<String, Object> map = new HashMap<>();
		String url = "";
		try {
			Map<String, Object> params = new HashMap<>();
			name = rebuildStore(name);
			
			logger.info("store2: " + name);
			params.put("fields","name,checkins,location,category,website,engagement,restaurant_services,parking,overall_star_rating,hours,about,description,price_range,single_line_address,phone");
			params.put("q", name);
			Call<HashMap<String, Object>> call = api.getCall().getFBlocationID(params);
			Response<HashMap<String, Object>> resp = call.execute();
			if(resp.code() != 200) {
				logger.error(resp.message());
				
				url = "https://www.instagram.com/explore/tags/"+name.replaceAll(" ", "");
				map.put("name", name);
				map.put("url", url);
				return map;
			}
			HashMap<String, Object> result = resp.body();
			
			List<Map> data_list = (List)result.get("data");
			
			if(!data_list.isEmpty() && data_list.size() > 0) {
				for(Map data : data_list) {
					Map loc = (Map) data.get("location");
					if(loc.get("country") != null && loc.get("country").equals("South Korea")) {
						map.put("name", data.get("name"));
						map.put("url", "https://www.instagram.com/explore/locations/"+data.get("id"));
						return map;
					}
				}
			}
			
			//페이스북에 장소가 등록되어 있지 않을 경우
			logger.error("data list is empty");
				
			url = "https://www.instagram.com/explore/tags/"+name.replaceAll(" ", "");
			map.put("name", name);
			map.put("url", url);
			return map;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return map;
	}
	
	public Map<String,Object> getCafeAndInsert() {
		Map<String, Object> map = new HashMap<>();
		
		try {
			Call<String> call = api.getCallString().getCafe();
			String result = call.execute().body();
			Document doc = Jsoup.parse(result);
			
			Elements list = doc.select("div.t-entry-visual-tc");// ("div.search-list-item-row.second");
			int size = list.size();
			Gson gson = new Gson();
			
			for(int i=0; i<size; i++) {
				String area = list.get(i).select("div.t-entry p span").text();
				if(!area.equals("이탈리아")) {
					String store = list.get(i).select("div.t-entry h3").text();
					Map<String, Object> data = getFBlocationId2(store);
					
					if(data == null)
						break;
					
					TacoVO taco = new TacoVO();
					taco.setAddr((String)data.get("single_line_address"));
					taco.setName((String)data.get("name"));
					taco.setPlace_id((String)data.get("id"));
					taco.setAbout((String)data.get("about"));
					taco.setCategory((String)data.get("category"));
					taco.setTel((String)data.get("phone"));
					taco.setWebsite((String)data.get("website"));
					taco.setHours(gson.toJson(data.get("hours")));
					
					
					insertTacolab(taco);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	public int insertTacolab(TacoVO vo) throws SQLException {
		TacoDAO dao = sqlSession.getMapper(TacoDAO.class);
		return dao.insertTacolab(vo);
	}
	
	public int updateTacolab(TacoVO vo) throws SQLException {
		TacoDAO dao = sqlSession.getMapper(TacoDAO.class);
		return dao.updateTacolab(vo);
	}
	
	public List<TacoVO> listTacoLab() throws SQLException {
		TacoDAO dao = sqlSession.getMapper(TacoDAO.class);
		return dao.getTacolab();
	}
	
	public void modifyTacoInfo() throws SQLException {
		List<TacoVO> total = listTacoLab();
		
		for(TacoVO taco : total) {
			Map<String, Object> data = getFBlocation(taco.getName());
			if(data != null) {
				taco.setPlace_id((String)data.get("id"));
				taco.setName((String)data.get("name"));
				taco.setWebsite((String)data.get("website"));
				taco.setCategory((String)data.get("category"));
				if(data.get("about") != null)
					taco.setAbout((String)data.get("about"));
				
				updateTacolab(taco);
			}
		}
	}
	
	
	
	public Map<String, Object> getFBlocation(String name) {
		try {
			Map<String, Object> params = new HashMap<>();
			name = rebuildStore(name);
			
			params.put("fields","name,checkins,location,category,website,engagement,restaurant_services,parking,overall_star_rating,hours,about,description,price_range,single_line_address,phone");
			params.put("q", name);
			Call<HashMap<String, Object>> call = api.getCall().getFBlocationID(params);
			Response<HashMap<String, Object>> resp = call.execute();
			if(resp.code() != 200) {
				logger.error(resp.message());
				
				return null;
			}
			HashMap<String, Object> result = resp.body();
			
			List<Map> data_list = (List)result.get("data");
			if(!data_list.isEmpty() && data_list.size() > 0) {
				for(Map data : data_list) {
					Map loc = (Map) data.get("location");
					if(loc.get("country") != null && loc.get("country").equals("South Korea")) {
						return data;
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}
}
