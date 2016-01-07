package com.goldenasia.lottery.bean;

public class User {
	private String username;		//用户名
	private String password;		//密码
	private String cellphone;		//登录状态
	
	private boolean rempassword;	//记住密码
	private boolean autoLogin;		//自动登录
	
	private String parentid;		//代理id
	private String securitypwd;		//安全码
	private String nickName;		//妮称
	private String fMoney;			//钱
	private String fPoint;			//返点
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public boolean isRempassword() {
		return rempassword;
	}
	public void setRempassword(boolean rempassword) {
		this.rempassword = rempassword;
	}
	public boolean isAutoLogin() {
		return autoLogin;
	}
	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getSecuritypwd() {
		return securitypwd;
	}
	public void setSecuritypwd(String securitypwd) {
		this.securitypwd = securitypwd;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getfMoney() {
		return fMoney;
	}
	public void setfMoney(String fMoney) {
		this.fMoney = fMoney;
	}
	public String getfPoint() {
		return fPoint;
	}
	public void setfPoint(String string) {
		this.fPoint = string;
	}
	
}
