package com.goldenasia.lottery.bean;

public class AppendTaskDetailItems {
	private String projectid;	//": "968691704",
	private String multiple;	//": "1",
	private String status;		//": "1",
	private String statusdes;	//": "已完成",
	private String entry;		//": "269642510",
	private String issue;		//": "15081344"
	
	public AppendTaskDetailItems() {
	}
	
	public AppendTaskDetailItems(String projectid, String multiple,
			String status, String statusdes, String entry, String issue) {
		this.projectid = projectid;
		this.multiple = multiple;
		this.status = status;
		this.statusdes = statusdes;
		this.entry = entry;
		this.issue = issue;
	}

	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusdes() {
		return statusdes;
	}
	public void setStatusdes(String statusdes) {
		this.statusdes = statusdes;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}

}
