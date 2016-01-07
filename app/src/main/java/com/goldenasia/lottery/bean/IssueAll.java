package com.goldenasia.lottery.bean;

public class IssueAll {
	private IssueLast issueLast;
	private IssueSales issueSales;
	private IssueTask issueTask;
	
	public IssueAll() {
	}
	public IssueAll(IssueLast issueLast, IssueSales issueSales,
			IssueTask issueTask) {
		this.issueLast = issueLast;
		this.issueSales = issueSales;
		this.issueTask = issueTask;
	}
	public IssueLast getIssueLast() {
		return issueLast;
	}
	public void setIssueLast(IssueLast issueLast) {
		this.issueLast = issueLast;
	}
	public IssueSales getIssueSales() {
		return issueSales;
	}
	public void setIssueSales(IssueSales issueSales) {
		this.issueSales = issueSales;
	}
	public IssueTask getIssueTask() {
		return issueTask;
	}
	public void setIssueTask(IssueTask issueTask) {
		this.issueTask = issueTask;
	}
	
}
