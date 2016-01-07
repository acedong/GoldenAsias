package com.goldenasia.lottery.bean;

public class IssueSales {
	private String opentime;
	private String issue;
	private String saleend;
	private String currenttime;
	
	public IssueSales() {
	}
	public IssueSales(String opentime, String issue, String saleend,
			String currenttime) {
		this.opentime = opentime;
		this.issue = issue;
		this.saleend = saleend;
		this.currenttime = currenttime;
	}
	public String getOpentime() {
		return opentime;
	}
	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getSaleend() {
		return saleend;
	}
	public void setSaleend(String saleend) {
		this.saleend = saleend;
	}
	public String getCurrenttime() {
		return currenttime;
	}
	public void setCurrenttime(String currenttime) {
		this.currenttime = currenttime;
	}
}
