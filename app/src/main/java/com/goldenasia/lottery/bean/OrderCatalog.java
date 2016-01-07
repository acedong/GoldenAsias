package com.goldenasia.lottery.bean;

public class OrderCatalog {
	private String lotteryname;
	private String methodname;
	private String writetime;
	private String status;
	private String totalprice;
	private String issue;
	private String projectid;
	private String modes;
	
	public OrderCatalog(){}
	
	public OrderCatalog(String lotteryname, String methodname,
			String writetime, String status, String totalprice, String issue,
			String projectid, String modes) {
		this.lotteryname = lotteryname;
		this.methodname = methodname;
		this.writetime = writetime;
		this.status = status;
		this.totalprice = totalprice;
		this.issue = issue;
		this.projectid = projectid;
		this.modes = modes;
	}
	public String getLotteryname() {
		return lotteryname;
	}
	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
	}
	public String getMethodname() {
		return methodname;
	}
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	public String getWritetime() {
		return writetime;
	}
	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getModes() {
		return modes;
	}
	public void setModes(String modes) {
		this.modes = modes;
	}
	
}
