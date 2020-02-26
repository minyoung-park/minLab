package miny.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import miny.common.exception.MyException;
import miny.common.exception.MyException.MyError;
import miny.common.util.TimeUtil;
import miny.service.CultureService;
import miny.vo.ActionResultForMessage;
import miny.vo.CallResult;

@Controller
public class CultureController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CultureService cultureService;

	@RequestMapping(value = "/get/cultureInfo")
	@ResponseBody
	public CallResult cultureInfoArea(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();
		String sido = ServletRequestUtils.getStringParameter(request, "sido", "서울");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", null);
		
		Map<String, Object> params = new HashMap<>();
		TimeUtil tu = new TimeUtil();
		
		params.put("from", tu.toStr(new Date(),"yyyyMMdd"));
		params.put("to", tu.toStr(tu.addMonth(new Date(), 1),"yyyyMMdd"));
		params.put("sortStdr", 2); //1:등록일, 2:공연명, 3:지역
		
		if(sido != null)
			params.put("sido", sido);
		if(keyword != null)
			params.put("keyword", keyword);
		
		logger.debug("params={}",params);
		
		try {
			Map<String,Object> data = cultureService.getCultureInfoArea(params);
			
			if(data == null)
				throw new MyException(MyError.SYSTEM_EXCEPTION);
			
			result.setData(data);
			throw new MyException(MyError.SUCCESS);
			
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
	
	@RequestMapping(value = "/new/get/cultureInfo")
	@ResponseBody
	public CallResult cultureInfoArea_new(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();
		String sido = ServletRequestUtils.getStringParameter(request, "sido", "서울");
		String keyword = ServletRequestUtils.getStringParameter(request, "keyword", null);
		
		Map<String, Object> params = new HashMap<>();
		TimeUtil tu = new TimeUtil();
		
		params.put("from", tu.toStr(new Date(),"yyyyMMdd"));
		params.put("to", tu.toStr(tu.addMonth(new Date(), 1),"yyyyMMdd"));
		params.put("sortStdr", 2); //1:등록일, 2:공연명, 3:지역
		
		if(sido != null)
			params.put("sido", sido);
		if(keyword != null)
			params.put("keyword", keyword);
		
		logger.debug("params={}",params);
		
		try {
			ActionResultForMessage data = cultureService.getCultureInfoArea_new(params);
			
			if (data == null) throw new MyException(MyError.SYSTEM_EXCEPTION);
		    if (!data.getMyError().equals(MyError.SUCCESS)) throw new MyException(data.getMyError());
			
			result.setData(data);
			throw new MyException(MyError.SUCCESS);
			
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
