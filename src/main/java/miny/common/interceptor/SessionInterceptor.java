package miny.common.interceptor;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class SessionInterceptor extends HandlerInterceptorAdapter{


	private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);	

	@Value("#{prop['ci_url']}")
	private String  baseUrl;
	
	static final String[] noLoginUrl = {
	};
	
	
	@Override
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String referer = request.getHeader("Referer");
		String servletPath = request.getServletPath();
		String requestURI = request.getRequestURI();
		String requestURL = new String(request.getRequestURL());
		String contextPath = request.getContextPath();
		String serverPath = requestURL.replace(requestURI, "");
		
//		logger.debug("requestURI:[{}]" , requestURI );
//		logger.debug("requestURL:[{}]" , requestURL );
//		logger.debug("serverPath:[{}]" , serverPath );
//		logger.debug("getScheme:[{}]" , request.getScheme() );
//		logger.debug("isSecure:[{}]" , request.isSecure() );
//		logger.debug("contextPath:[{}]", contextPath);
//		logger.debug("servletPath:[{}]", servletPath);

		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,	HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		
		String requestURI = request.getRequestURI();
		String requestURL = new String(request.getRequestURL());
		String serverPath = requestURL.replace(requestURI, "");
		String filePath = serverPath + "/uploadfile";
			
		Map map = new HashMap();
		if (modelAndView != null)	modelAndView.addObject("SVR" , map);
	}
}
