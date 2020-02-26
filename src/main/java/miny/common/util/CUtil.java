package miny.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import miny.common.CValue;


public class CUtil {
	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		return body;
	}

	public boolean isMobile(String userAgent) {
		boolean ismobile = false;
		String[] mobile = { "iPhone", "iPod", "Android" };
		for (int i = 0; i < mobile.length; i++) {
			if (userAgent.indexOf(mobile[i]) > -1) {
				ismobile = true;
				break;
			}
		}
		return ismobile;
	}

	public static Integer objectToInteger(Object d) {
		Integer result = null;
		if (d == null) return null;
		
		if (d instanceof Double ) {
			result = ((Double) d).intValue();	
		}else if (d instanceof Long){
			result = ((Long) d).intValue();
		}else if (d instanceof String){
			String str = ((String) d).replace(".0", "");
			result = Integer.valueOf(str);
		}else if (d instanceof Integer){
			result = (Integer) d;
		}
			
		return result;
	}
	public static String GetRandomCode(int len){
		String[] chars = new String[]{"1","2","3","4","5","6","7","8","9","0","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","r","s","t","u","v","w","x","y","z"};
		String str = "";
		for (int i=0; i<len; i++){
			Random rnd = new Random();
			int ii = rnd.nextInt(60);
			str += chars[ii];
		}
		return str;
	}
	public static String getServerUrl(HttpServletRequest request){
		
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		String requestURL = new String(request.getRequestURL());
		String serverPath = requestURL.replace(requestURI, "");
		String url = serverPath + contextPath;
		return url;
	}
	public static boolean isOnlySyncInterfaceType(Integer interface_type) {

		if (interface_type.equals(CValue.kakao)) { return true; }
		return false;

	}
	public static boolean isExistsGPSFunctionInterfaceType(Integer interface_type) {

		if (interface_type.equals(CValue.kakao)) { return false; }
		return true;

	}
}
