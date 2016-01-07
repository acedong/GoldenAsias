package com.goldenasia.lottery.bean;

public class IssueLast {
	private String code;
	private String issue;
	private String sorts;
	
	public IssueLast() {
	}
	
	public IssueLast(String code, String issue, String sorts) {
		this.code = code;
		this.issue = issue;
		this.sorts = sorts;
	}

	public IssueLast(String code, String issue) {
		this.code = code;
		this.issue = issue;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getSorts() {
		return sorts;
	}
	public void setSorts(String sorts) {
		this.sorts = sorts;
	}
	
}
