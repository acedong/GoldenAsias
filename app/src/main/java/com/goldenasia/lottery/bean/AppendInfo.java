package com.goldenasia.lottery.bean;

public class AppendInfo {
	
	private String issue;
	private Integer multiple;
	private String putin;
	
	public AppendInfo() {
	}
	
	public AppendInfo(String issue, Integer multiple, String putin) {
		this.issue = issue;
		this.multiple = multiple;
		this.putin = putin;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public String getPutin() {
		return putin;
	}

	public void setPutin(String putin) {
		this.putin = putin;
	}
	
}
