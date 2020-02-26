package miny.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CValue {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final String newlineStr = "\n";
	public static final String newlineStrForMail = "<br/>";
//	public static String simpleRoot;
//	
//	@Value("#{prop['simpleRoot']}")
//	public void setSimpleRoots(String simpleRoot) {
//		this.simpleRoot = simpleRoot;
//	}
	
	public static final int default_noti_min = 10;
	public static final String order_display_id  = "P8";
	public static final String app_type  = "S85";
	
	
	public static final int LIMIT_COUNT = 10; //address list limit count
	
	public static String mailimgurl;
	
	@Value("#{prop['mailimgurl']}")
	public void setSimpleRoots(String mailimgurl) {
		this.mailimgurl = mailimgurl;
	}
	
	public static final int CONNECT_TIMEOUT = 3;
	public static final int WRITE_TIMEOUT = 3;
	public static final int READ_TIMEOUT = 3;
	
	
	// Interface ID
	public static final int faceBook = 1001;
	public static final int munbs = 1003;
	public static final int kakao = 1004;
	public static final int webtester = 9999;
	
	// bot type
	public static final int moonBot = 1;
	public static final int busBot = 2;

	public static final int dev_moonBot = 101;
	public static final int dev_busBot = 102;
	
}
