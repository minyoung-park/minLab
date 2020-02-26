package miny.common.exception;

public class MyException extends Exception {

	private MyError err;

	public MyException(MyError err){
		this.err = err;
	}

	public static int successCode = 0;
	public static int systemErrorCode = 9999;
	public static String systemErrorMsg = "SYSTEM_EXCEPTION";
	public static enum MyError {
		SUCCESS,
		INVALID_PARAMS,
		NO_DATA_FOUND, 
		TOO_MANY_RESULTS,
		NO_REQUIED_PARAMS,
		SYSTEM_EXCEPTION,
		NOT_MATCH_USERINFO,
		ALREADY_USER_UID,
		////action error
		REQUEST_CANCEL,
		ALREADY_REQUESTED,
		INVALID_ORDER_ID,
		INVALID_ADDR,
		INVALID_DATE,
		EXTERNAL_SERVER_ERROR,
		NO_STATION_LIST,
		NO_STOP_BUS,
		INVALID_BUS_NUM
	}
	public MyError getMyError(){
		return this.err;
	}
	public String getMsg() {
		String returnValue = systemErrorMsg;
		switch(err){
			case SUCCESS : returnValue = "SUCCESS"; break;
			case SYSTEM_EXCEPTION : returnValue = "SYSTEM_EXCEPTION"; break;
			case INVALID_PARAMS : returnValue = "INVALID_PARAMS"; break;
			case NO_DATA_FOUND : returnValue = "NO_DATA_FOUND"; break;
			case TOO_MANY_RESULTS : returnValue = "TOO_MANY_RESULTS"; break;
			case NO_REQUIED_PARAMS : returnValue = "NO_REQUIED_PARAMS"; break;
			case INVALID_ADDR : returnValue = "INVALID_ADDR"; break;
			case INVALID_DATE : returnValue = "INVALID_DATE"; break;
			case INVALID_ORDER_ID : returnValue = "INVALID_ORDER_ID"; break;
			case ALREADY_REQUESTED : returnValue = "ALREADY_REQUESTED"; break;
			case REQUEST_CANCEL : returnValue = "REQUEST_CANCEL"; break;
			case EXTERNAL_SERVER_ERROR : returnValue = "EXTERNAL_SERVER_ERROR"; break;
			case NOT_MATCH_USERINFO : returnValue = "NOT_MATCH_USERINFO"; break;
			case ALREADY_USER_UID : returnValue = "ALREADY_USER_UID"; break;
			case NO_STATION_LIST : returnValue = "NO_STATION_LIST"; break;
			case NO_STOP_BUS : returnValue = "NO_STOP_BUS"; break;
			case INVALID_BUS_NUM : returnValue = "INVALID_BUS_NUM"; break;
			
		}
		return returnValue;
	}
	public int getCode() {
		int returnValue = systemErrorCode;
		switch(err){
			case SUCCESS : returnValue = successCode; break;	
			case SYSTEM_EXCEPTION : returnValue = systemErrorCode; break;
			case INVALID_PARAMS : returnValue = 9997; break;
			case NO_DATA_FOUND : returnValue = 9996; break;
			case TOO_MANY_RESULTS : returnValue = 9995; break;
			case NO_REQUIED_PARAMS : returnValue = 9998; break;
			case EXTERNAL_SERVER_ERROR : returnValue = 6000; break;
			case INVALID_ADDR : returnValue = 6001; break;
			case INVALID_DATE : returnValue = 6002; break;
			case INVALID_ORDER_ID : returnValue = 6003; break;
			case ALREADY_REQUESTED : returnValue = 6004; break;
			case REQUEST_CANCEL : returnValue = 6005 ; break;
			case NOT_MATCH_USERINFO : returnValue = 6006 ; break;
			case ALREADY_USER_UID : returnValue = 6007; break;
			case NO_STATION_LIST : returnValue = 6008; break;
			case NO_STOP_BUS : returnValue = 6009; break;
			case INVALID_BUS_NUM : returnValue = 6010; break;
		}		
		return returnValue;
	}
}