package miny.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import miny.common.exception.MyException;
import miny.common.exception.MyException.MyError;
import miny.common.vo.ErrorStatus;
import miny.service.TacoService;
import miny.vo.CallResult;
import miny.vo.TacoVO;

@Controller
public class TacoController {
	@Autowired
	private TacoService tacoService;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/get/instaCafe")
	@ResponseBody
	public CallResult getInstaCafe(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();
		try {
			Map<String, Object> data = tacoService.getCafe();
			String text = (String)data.get("message");
//			tacoService.tacoBotSend(text);
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
	
	@RequestMapping(value = "/get/dining")
	@ResponseBody
	public CallResult getDining(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();
		try {
			
			String[] area = {"홍대","상수","합정","한남동","이태원","강남","잠실","가로수길"};
			String tag = "맛집";
			
			int index = (int) (Math.random()*(area.length));
			
			Map<String, Object> params = new HashMap();
			params.put("query", area[index]+tag);
			
			result.setData(tacoService.getRestaurantDC(params));
			
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
	
	
	@RequestMapping(value = "/instaToken")
	@ResponseBody
	public CallResult instaToken(HttpServletRequest request) throws Exception {
		CallResult result = new CallResult();
		String code = ServletRequestUtils.getStringParameter(request, "code");
		result.setData(code);
		return result;
	}
	
	@RequestMapping(value = "/insertTacoData")
	@ResponseBody
	public Map<String, Object> insertTacoData() {
		Map<String, Object> jsonReturn = new HashMap<String, Object>();
		ErrorStatus error = new ErrorStatus();
		logger.debug("insertTacoData @Controller");
		try {
			jsonReturn.put("data",tacoService.getCafeAndInsert());
		} catch (Exception e) {
			e.printStackTrace();
        	error.setCode(MyException.systemErrorCode);
        	error.setMsg(MyException.systemErrorMsg);
		}
		jsonReturn.put("error", error);
        return jsonReturn;
	}
	
	@RequestMapping(value = "/modifyTacoData")
	@ResponseBody
	public Map<String, Object> modifyTacoData() {
		Map<String, Object> jsonReturn = new HashMap<String, Object>();
		ErrorStatus error = new ErrorStatus();
		logger.debug("modifyTacoData @Controller");
		try {
			tacoService.modifyTacoInfo();
//			jsonReturn.put("data",tacoService.getCafeAndInsert());
		} catch (Exception e) {
			e.printStackTrace();
        	error.setCode(MyException.systemErrorCode);
        	error.setMsg(MyException.systemErrorMsg);
		}
		jsonReturn.put("error", error);
        return jsonReturn;
	}
	
}
