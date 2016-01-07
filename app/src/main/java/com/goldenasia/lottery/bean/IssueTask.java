package com.goldenasia.lottery.bean;

public class IssueTask {
	private String issuerule;
	private String issuecount;
	private String maxtaskcount;
	private String finalissue;
	
	public IssueTask() {
	}
	public IssueTask(String issuerule, String issuecount, String maxtaskcount,
			String finalissue) {
		this.issuerule = issuerule;
		this.issuecount = issuecount;
		this.maxtaskcount = maxtaskcount;
		this.finalissue = finalissue;
	}
	public String getIssuerule() {
		return issuerule;
	}
	public void setIssuerule(String issuerule) {
		this.issuerule = issuerule;
	}
	public String getIssuecount() {
		return issuecount;
	}
	public void setIssuecount(String issuecount) {
		this.issuecount = issuecount;
	}
	public String getMaxtaskcount() {
		return maxtaskcount;
	}
	public void setMaxtaskcount(String maxtaskcount) {
		this.maxtaskcount = maxtaskcount;
	}
	public String getFinalissue() {
		return finalissue;
	}
	public void setFinalissue(String finalissue) {
		this.finalissue = finalissue;
	}
	
}
