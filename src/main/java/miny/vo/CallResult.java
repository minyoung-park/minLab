package miny.vo;

import miny.common.vo.ErrorStatus;

public class CallResult{
	private ErrorStatus error;
	private Object data;
	public CallResult(){
		error = new ErrorStatus();
	}
	public ErrorStatus getError() {
		return error;
	}
	public void setError(ErrorStatus error) {
		this.error = error;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}	