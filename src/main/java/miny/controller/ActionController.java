package miny.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import miny.service.ActionService;
import miny.vo.CallResult;

@Controller
public class ActionController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ActionService actionService;
	
	@RequestMapping(value = "/get/loanInfo")
	@ResponseBody
	public CallResult sendSmsMsg(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();
		
//		String loan_ym = ServletRequestUtils.getStringParameter(request, "loan_ym", null);		
		String cb_grd = ServletRequestUtils.getStringParameter(request, "cb_grd", null);
		String house_tycd = ServletRequestUtils.getStringParameter(request, "house_tycd", null);
		String job_cd = ServletRequestUtils.getStringParameter(request, "job_cd", null);		
		String age = ServletRequestUtils.getStringParameter(request, "age", null);
		String income = ServletRequestUtils.getStringParameter(request, "income", null);
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("loan_ym", "L3M");
		if(cb_grd != null && !cb_grd.equals("@@skip@@")) {
			cb_grd = cb_grd.replaceAll("[^-?0-9]+", "");
			params.put("cb_grd", cb_grd);
		}
		
		if(house_tycd != null)
			params.put("house_tycd", house_tycd);
		
		if(job_cd != null) {
			if(job_cd.equals("직장인"))
				params.put("job_cd", "01");
			else if(job_cd.equals("자영업자"))
				params.put("job_cd", "02");
			else if(job_cd.equals("기타(무직 등)"))
				params.put("job_cd", "03");
			else
				params.put("job_cd", job_cd);
		}
		
		if(age != null && !age.equals("@@skip@@")) {
			age = age.replaceAll("[^-?0-9]+", "");
			if(age.length() > 1) {
				age = age.substring(0, 1);
				params.put("age", age);
			}
		}
			
		if(income != null && !income.equals("@@skip@@")) {
			income = income.replaceAll("[^-?0-9]+", "");
			if(income.length() > 1) {
				income = income.substring(0, 1);
				int sal = Integer.parseInt(income)+1;
				params.put("income", sal);
			}
		}
		
		logger.debug("params={}",params);
		
		try {
			Map<String,Object> data = actionService.getLoanInfo(params);
			
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
	
}
