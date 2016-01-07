package com.goldenasia.lottery.bean;

public class MessageInfo {
	private String id;		//": "172414510",
	private String content;	//": "恭喜您，编号为【<font color=blue>8IRVEA</font>】的方案已中奖,中奖金额为<font color=red>3.6</font>元,请注意查看您的帐变信息，如果有任何疑问请联系在线客服。",
	private String subject;	//": "恭喜您，编号为【<font color=blue>8IRVEA</font>】的方案已中奖。",
	private String title;	//": "中奖消息",
	private String sendtime;//": "2015-08-10 18:32:05"
	
	private String sendday;//": "2015-08-04",
	private String istop; // ": "0"
	
	
	public MessageInfo(){}
	
	public MessageInfo(String id, String subject, String sendday, String istop) {
		this.id = id;
		this.subject = subject;
		this.sendday = sendday;
		this.istop = istop;
	}

	public MessageInfo(String id, String content, String subject,
			String title, String sendtime) {
		this.id = id;
		this.content = content;
		this.subject = subject;
		this.title = title;
		this.sendtime = sendtime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getSendday() {
		return sendday;
	}

	public void setSendday(String sendday) {
		this.sendday = sendday;
	}

	public String getIstop() {
		return istop;
	}

	public void setIstop(String istop) {
		this.istop = istop;
	}

}
