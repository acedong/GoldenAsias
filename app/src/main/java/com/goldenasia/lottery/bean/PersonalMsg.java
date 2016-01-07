package com.goldenasia.lottery.bean;

public class PersonalMsg {
	private String cellphone;
	private String id;
	private String type;
	public PersonalMsg() {
	}
	public PersonalMsg(String cellphone, String id, String type) {
		this.cellphone = cellphone;
		this.id = id;
		this.type = type;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
