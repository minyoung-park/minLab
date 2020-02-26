package miny.vo;

import java.util.Date;

public class Lotto {
	private String user_key;
	private String drwno;
	private Date create_time;
	
	public String getUser_key() {
		return user_key;
	}
	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}
	public String getDrwno() {
		return drwno;
	}
	public void setDrwno(String drwno) {
		this.drwno = drwno;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
}
