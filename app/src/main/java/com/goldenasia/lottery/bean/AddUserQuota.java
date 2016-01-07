package com.goldenasia.lottery.bean;


public class AddUserQuota {
	private String username;
	private String minpoint;	//"0.0"
	private String maxpoint;	//"7.5"
	private String usermaxpoint;//用户返点 最大
	private String userminpoint;//用户返点 最小
	private String userquota;	//(7.5-7.5)":"0","(7.1-7.4)":"0","(6.6-7.0)":"0","(6.1-6.5)":"0"
	
	public AddUserQuota(){
		
	}

	public AddUserQuota(String username, String minpoint, String maxpoint,
			String usermaxpoint, String userminpoint, String userquota) {
		this.username = username;
		this.minpoint = minpoint;
		this.maxpoint = maxpoint;
		this.usermaxpoint = usermaxpoint;
		this.userminpoint = userminpoint;
		this.userquota = userquota;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMinpoint() {
		return minpoint;
	}

	public void setMinpoint(String minpoint) {
		this.minpoint = minpoint;
	}

	public String getMaxpoint() {
		return maxpoint;
	}

	public void setMaxpoint(String maxpoint) {
		this.maxpoint = maxpoint;
	}

	public String getUsermaxpoint() {
		return usermaxpoint;
	}

	public void setUsermaxpoint(String usermaxpoint) {
		this.usermaxpoint = usermaxpoint;
	}

	public String getUserminpoint() {
		return userminpoint;
	}

	public void setUserminpoint(String userminpoint) {
		this.userminpoint = userminpoint;
	}

	public String getUserquota() {
		return userquota;
	}

	public void setUserquota(String userquota) {
		this.userquota = userquota;
	}

}
