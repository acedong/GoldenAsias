package com.goldenasia.lottery.bean;

public class AddUserInfo {
	private String cellphone; 
	private String username;
	private String userpass; 
	private String nickname; 
	private String keeppoint; 
	private String usertype; 
	private String pgtype;
	
	public AddUserInfo() {
		
	}
	public AddUserInfo(String cellphone, String username, String userpass,
			String nickname, String keeppoint, String usertype, String pgtype) {
		this.cellphone = cellphone;
		this.username = username;
		this.userpass = userpass;
		this.nickname = nickname;
		this.keeppoint = keeppoint;
		this.usertype = usertype;
		this.pgtype = pgtype;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getKeeppoint() {
		return keeppoint;
	}
	public void setKeeppoint(String keeppoint) {
		this.keeppoint = keeppoint;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getPgtype() {
		return pgtype;
	}
	public void setPgtype(String pgtype) {
		this.pgtype = pgtype;
	}
	
}
