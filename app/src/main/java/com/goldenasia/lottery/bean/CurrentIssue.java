package com.goldenasia.lottery.bean;

/**
 * 当前销售期号
 * @author Ace
 *
 */

public class CurrentIssue {
	private String lotteryid;
	private String saleend;
	private String opentime;
	private String currenttime;
	private String issue;
	
	public CurrentIssue() {
	}
	public CurrentIssue(String lotteryid, String saleend, String opentime,
			String currenttime, String issue) {
		this.lotteryid = lotteryid;
		this.saleend = saleend;
		this.opentime = opentime;
		this.currenttime = currenttime;
		this.issue = issue;
	}
	public String getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(String lotteryid) {
		this.lotteryid = lotteryid;
	}
	public String getSaleend() {
		return saleend;
	}
	public void setSaleend(String saleend) {
		this.saleend = saleend;
	}
	public String getOpentime() {
		return opentime;
	}
	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}
	public String getCurrenttime() {
		return currenttime;
	}
	public void setCurrenttime(String currenttime) {
		this.currenttime = currenttime;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	
}
