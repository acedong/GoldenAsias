package com.goldenasia.lottery.bean;

public class AppendTaskInfo {

	private String title;
	private String taskid;
	private String taskprice;
	private String finishprice;
	private String begintime;
	private String modes;
	private String lotteryname;
	private String status;
	
	public AppendTaskInfo(){}
	
	public AppendTaskInfo(String title, String taskid, String taskprice,
			String finishprice, String begintime, String modes,
			String lotteryname, String status) {
		this.title = title;
		this.taskid = taskid;
		this.taskprice = taskprice;
		this.finishprice = finishprice;
		this.begintime = begintime;
		this.modes = modes;
		this.lotteryname = lotteryname;
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getTaskprice() {
		return taskprice;
	}
	public void setTaskprice(String taskprice) {
		this.taskprice = taskprice;
	}
	public String getFinishprice() {
		return finishprice;
	}
	public void setFinishprice(String finishprice) {
		this.finishprice = finishprice;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getModes() {
		return modes;
	}
	public void setModes(String modes) {
		this.modes = modes;
	}
	public String getLotteryname() {
		return lotteryname;
	}
	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
