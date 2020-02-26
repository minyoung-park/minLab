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
import miny.service.MovieService;
import miny.vo.ActionResultForMessage;
import miny.vo.CallResult;


@Controller
public class MovieController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MovieService movieService;

	@RequestMapping(value = "/get/weeklyBoxoffice")
	@ResponseBody
	public CallResult getWeeklyBoxoffice(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();
		TimeUtil tu = new TimeUtil();
		String period = ServletRequestUtils.getStringParameter(request, "period", tu.toStr(tu.addDay(new Date(), -7), "yyyyMMdd"));
		Map<String, Object> params = new HashMap();
		params.put("period", period);
		logger.debug("params={}", params);

		try {
			logger.debug("@Controller Thread id=[{}]", Thread.currentThread().getId());
			//			ResultBoxoffice data = actionService.getWeeklyBoxoffice(params);
			Map<String, Object> data = movieService.getWeeklyBoxoffice(params);
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
	
	@RequestMapping(value = "/new/get/weeklyBoxoffice")
	@ResponseBody
	public CallResult getWeeklyBoxoffice_new(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();
		TimeUtil tu = new TimeUtil();
		String period = ServletRequestUtils.getStringParameter(request, "period", tu.toStr(tu.addDay(new Date(), -7), "yyyyMMdd"));
		Map<String, Object> params = new HashMap();
		params.put("period", period);
		logger.debug("params={}", params);

		try {
			ActionResultForMessage data = movieService.getWeeklyBoxoffice_new(params);
			
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
	
	@RequestMapping(value = "/get/ticketingMovieChart")
	@ResponseBody
	public CallResult getTicketingMovieChart(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();

		try {
			ActionResultForMessage data = movieService.getTicketingMovieChart();
			
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
