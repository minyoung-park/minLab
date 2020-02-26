package miny.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import miny.common.CValue;
import miny.common.apis.CallApi;
import miny.common.util.StringUtil;
import miny.common.util.TimeUtil;
import miny.vo.Culture;
import miny.vo.RentLoan;
import retrofit2.Call;
import retrofit2.Response;

@Service
public class ActionService {
	@Autowired
	private CallApi api;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Map<String, Object> urlShortner(List<String> urlList) {
		Map<String,Object> returnData = new HashMap<>();
		try {
			Call<HashMap<String, Object>> call = api.getCall().urlShortner(urlList);
			Response<HashMap<String, Object>> resp = call.execute();
			if(resp.code() != 200) {
				logger.error(resp.code() + ", {}", new Gson().toJson(resp.errorBody()));
				return null;
			}
			
			returnData = resp.body();
//			logger.info("{}", new Gson().toJson(returnData));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	public Map<String, Object> getLoanInfo(Map<String, Object> param) {
		
		logger.info("{}", new Gson().toJson(param));
		
		Map<String,Object> returnData = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		RentLoan data = new RentLoan();
		try {
			Call<RentLoan> call = api.getCallXml().getLoanInfo(param);
			data = call.execute().body();
			
			List list = new ArrayList<>();
			Integer count = Integer.parseInt(data.getTotalCount());
			
			if(count < 1) {
				returnData.put("message", "조건에 맞는 대출 내역이 없어요😥");
				return returnData;
			}
			
			if(count > 10) count = 10;
			for(int i=0; i<count; i++) {
				list.add(data.getItem(i));
			}
			map.put("list", list);
			map.put("total", count);
			returnData.put("message", getLoanInfoMsg(map));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	public String getLoanInfoMsg(Map<String, Object> data) {
		StringBuffer sb = new StringBuffer();
		sb.append("최근 3개월 전세대출 금리 현황").append(CValue.newlineStr);
		List<Map<String,Object>> list =(List)data.get("list");
		int total = (int)data.get("total");
		
		if(total > 4) total = 3;
		for(int i=0; i<total; i++) {
			Map<String,Object> map = list.get(i);
			sb.append(i+1).append(". ").append(map.get("bank")).append(" ").append(map.get("loanrat"));
			sb.append("%(최고 ").append(map.get("max")).append("%, 최저 ").append(map.get("min")).append("%)\n");
			String amt = (String)map.get("amt");
			amt = amt.substring(0, amt.length()-4);
			sb.append("총 ").append(map.get("cnt")).append("건, ").append(StringUtil.returnCommaNum(Integer.parseInt(amt))).append("만원").append(CValue.newlineStr);
		}
		sb.append("(위 금리는 변동될 수 있습니다. 자료제공: 한국주택금융공사)");
		return sb.toString();
	}
	
}
