package com.goldenasia.lottery.bean;


public class OrderDetails {
	
	private String projectid;	//": "968705514",
	private String taskid;		//": "22362427",
	private String modes;		//": "元",
	private String issue;		//": "15081345",
	private String nocode;		//": "",
	private String bonus;		//": "0.0000",
	private String code;		//": "01|02|03",
	private String totalprice;	//": "2.0000",
	private String writetime;	//": "2015-08-13 16:16:49",
	private String dypointdec;	//": "1782.00-6%",
	private String cnname;		//": "十一运夺金",
	private String methodname;	//": "前三直选",
	private String username;	//": "testzion",
	private String statusdesc;	//": "未开奖",
	private String projectno;	//": "8HD2B9"
	
	public OrderDetails(){}

	public OrderDetails(String projectid, String taskid, String modes,
			String issue, String nocode, String bonus, String code,
			String totalprice, String writetime, String dypointdec,
			String cnname, String methodname, String username,
			String statusdesc, String projectno) {
		this.projectid = projectid;
		this.taskid = taskid;
		this.modes = modes;
		this.issue = issue;
		this.nocode = nocode;
		this.bonus = bonus;
		this.code = code;
		this.totalprice = totalprice;
		this.writetime = writetime;
		this.dypointdec = dypointdec;
		this.cnname = cnname;
		this.methodname = methodname;
		this.username = username;
		this.statusdesc = statusdesc;
		this.projectno = projectno;
	}


	public String getProjectid() {
		return projectid;
	}


	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}


	public String getTaskid() {
		return taskid;
	}


	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}


	public String getModes() {
		return modes;
	}


	public void setModes(String modes) {
		this.modes = modes;
	}


	public String getIssue() {
		return issue;
	}


	public void setIssue(String issue) {
		this.issue = issue;
	}


	public String getNocode() {
		return nocode;
	}


	public void setNocode(String nocode) {
		this.nocode = nocode;
	}


	public String getBonus() {
		return bonus;
	}


	public void setBonus(String bonus) {
		this.bonus = bonus;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getTotalprice() {
		return totalprice;
	}


	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}


	public String getWritetime() {
		return writetime;
	}


	public void setWritetime(String writetime) {
		this.writetime = writetime;
	}


	public String getDypointdec() {
		return dypointdec;
	}


	public void setDypointdec(String dypointdec) {
		this.dypointdec = dypointdec;
	}


	public String getCnname() {
		return cnname;
	}


	public void setCnname(String cnname) {
		this.cnname = cnname;
	}


	public String getMethodname() {
		return methodname;
	}


	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getStatusdesc() {
		return statusdesc;
	}


	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc;
	}


	public String getProjectno() {
		return projectno;
	}


	public void setProjectno(String projectno) {
		this.projectno = projectno;
	}

}
