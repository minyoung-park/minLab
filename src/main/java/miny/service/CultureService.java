package miny.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import miny.common.CValue;
import miny.common.apis.CallApi;
import miny.common.exception.MyException;
import miny.common.exception.MyException.MyError;
import miny.common.util.TimeUtil;
import miny.vo.ActionResultForMessage;
import miny.vo.Culture;
import miny.vo.CultureInfo;
import miny.vo.Template;
import retrofit2.Call;
import retrofit2.Response;

@Service
public class CultureService {

	@Autowired
	private CallApi api;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Map<String,Object> getCultureInfoArea(Map<String, Object> param) {
		Map<String,Object> returnData = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		Culture data = new Culture();
		try {
			Call<Culture> call = api.getCallXml().getCultureInfoArea(param);
			Response<Culture> res = call.execute();
			
			int code = res.code();
			if(code != 200) {
				logger.error("code: "+ code + ", "+ res.errorBody().string());
				return null;
			}
			
			data = res.body();//call.execute().body();
			if(data == null) {
				logger.error("data is null!!!!");
				return null;
			}
			
			List list = new ArrayList<>();
			Integer count = Integer.parseInt(data.getTotalCount());
			
			if(count < 1) {
				returnData.put("message", "조건에 맞는 공연이 없어요😢");
				return returnData;
			}
			
			if(count > 50) count = 50;
			for(int i=0; i<count; i++) {
				list.add(data.getPerformance(i));
			}
			Collections.shuffle(list);
			map.put("list", list);
			map.put("total", count);
			returnData.put("message", getCultureInfoMsg(map));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	public String getCultureInfoMsg(Map<String, Object> data) {
		TimeUtil tu = new TimeUtil();
		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> list =(List)data.get("list");
		int total = (int)data.get("total");
		
		if(total > 5) total = 5;
		
		for(int i=0; i<total; i++) {
			Map<String,Object> map = list.get(i);
			sb.append(i+1).append(". ").append(map.get("title")).append(CValue.newlineStr).append("(");
			String start = (String)map.get("startDate");
			String end = (String)map.get("endDate");
			sb.append(tu.toStr(tu.toDate(start), "yy/MM/dd")).append("~").append(tu.toStr(tu.toDate(end), "yy/MM/dd"));
			sb.append(" ").append(map.get("place")).append(")").append(CValue.newlineStr).append(CValue.newlineStr); //append(map.get("area")).append(" ").
		}
		sb.append("문화생활도 문봇과 함께😘");
		return sb.toString();
	}
	
	public ActionResultForMessage getCultureInfoArea_new(Map<String, Object> param) {
		ActionResultForMessage returnData = new ActionResultForMessage();
		
		Map<String, Object> map = new HashMap<>();
		Culture data = new Culture();
		try {
			Call<Culture> call = api.getCallXml().getCultureInfoArea(param);
			Response<Culture> res = call.execute();
			
			int code = res.code();
			if(code != 200) {
				logger.error("code: "+ code + ", "+ res.errorBody().string());
				return null;
			}
			
			data = res.body();//call.execute().body();
			if(data == null) {
				logger.error("data is null!!!!");
				return null;
			}
			
			List list = new ArrayList<>();
			Integer count = Integer.parseInt(data.getTotalCount());
			
			if(count < 1) {
				logger.error("NO_DATA_FOUND");
				throw new MyException(MyError.NO_DATA_FOUND);
			}
			
			if(count > 50) count = 50;
			for(int i=0; i<count; i++) {
				list.add(data.getPerformance(i));
			}
			Collections.shuffle(list);
			map.put("list", list);
			map.put("total", count);
			
			returnData = getCultureInfoMsg_new(map);
			
			throw new MyException(MyError.SUCCESS);
		} catch (MyException e) {
			returnData.setMyError(e.getMyError());
		} catch (Exception e) {
			e.printStackTrace();
			returnData = null;
		}
		return returnData;
	}
	
	public Template getCultureInfo(Map<String, Object> param) {
		Template template = new Template();
		TimeUtil tu = new TimeUtil();
		CultureInfo data = new CultureInfo();
		try {
			Call<CultureInfo> call = api.getCallXml().getCultureInfo(param);
			Response<CultureInfo> res = call.execute();
			
			int code = res.code();
			if(code != 200) {
				logger.error("code: "+ code + ", "+ res.errorBody().string());
				return null;
			}
			
			data = res.body();//call.execute().body();
			if(data == null) {
				logger.error("data is null!!!!");
				return null;
			}
			
			String title = "["+ data.getPerformance().get("realmName") +"]" + data.getPerformance().get("title");
			StringBuffer contents = new StringBuffer();
			contents.append("장소: ").append(data.getPerformance().get("place")).append(CValue.newlineStr);
			contents.append("일시: ").append(tu.toStr(tu.toDate((String)data.getPerformance().get("startDate")), "yy/MM/dd")).append("~");
			contents.append(tu.toStr(tu.toDate((String)data.getPerformance().get("endDate")), "yy/MM/dd")).append(CValue.newlineStr);
			contents.append("가격: ").append(data.getPerformance().get("price"));
			template.setTitle(title);
			template.setContents(contents.toString());
			template.setImage_url((String)data.getPerformance().get("imgUrl"));
			template.setImage_action((String)data.getPerformance().get("url"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return template;
	}
	
	public ActionResultForMessage getCultureInfoMsg_new(Map<String, Object> data) {
		ActionResultForMessage returnData = new ActionResultForMessage();
		List<Map<String,Object>> list =(List)data.get("list");
		int total = (int)data.get("total");
		
		if(total > 10) total = 10;
		
		list = list.subList(0, total);
		
		for(Map<String,Object> map : list) {
			Map<String, Object> param = new HashMap<>();
			param.put("seq", map.get("seq"));
			Template template = getCultureInfo(param);
			returnData.addTemplate(template);
		}
		
		return returnData;
	}
}
