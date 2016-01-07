package com.goldenasia.lottery.bean;

public class TaskIssue {
	
	private String serialNumber;
	private String taskIssueno;
	private String issueDate;
	
	public TaskIssue(){}
	
	public TaskIssue(String serialNumber, String taskIssueno, String issueDate) {
		this.serialNumber = serialNumber;
		this.taskIssueno = taskIssueno;
		this.issueDate = issueDate;
	}

	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getTaskIssueno() {
		return taskIssueno;
	}
	public void setTaskIssueno(String taskIssueno) {
		this.taskIssueno = taskIssueno;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

}
