package com.goldenasia.lottery.bean;

public class PassWord {
	private String cellphone;
	private String type;  
	private String oldpass;
	private String newpass;
	private String password;
	
	public PassWord(){}
	
	public PassWord(String cellphone,String type,String password){
		this.cellphone=cellphone;
		this.type = type;
		this.password = password;
	}
	
	public PassWord(String cellphone,String type, String oldpass, String newpass, String password) {
		this.type = type;
		this.oldpass = oldpass;
		this.newpass = newpass;
		this.password = password;
	}
	
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOldpass() {
		return oldpass;
	}
	public void setOldpass(String oldpass) {
		this.oldpass = oldpass;
	}
	public String getNewpass() {
		return newpass;
	}
	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
