package miny.vo;

import java.util.Date;

public class Chatlog {
	private Integer chat_id;
	private String user_key;
	private Integer contentType;
	private String content;
	private Date create_time;

	public Integer getChat_id() {
		return chat_id;
	}
	public void setChat_id(Integer chat_id) {
		this.chat_id = chat_id;
	}
	public String getUser_key() {
		return user_key;
	}
	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
}
