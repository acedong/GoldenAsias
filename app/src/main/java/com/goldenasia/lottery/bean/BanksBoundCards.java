package com.goldenasia.lottery.bean;

public class BanksBoundCards {
	
	private String cellphone;
	private String flag;
	private String bankid; 
	private String provinceid;
	private String cityid;
	private String branch; 
	private String realname; 
	private String cardno; 
	private String secpass; 
	private String oldrealname;
	private String oldcardno;
	
	public BanksBoundCards(){}
	
	public BanksBoundCards(String cellphone, String flag, String bankid,
			String provinceid, String cityid, String branch, String realname,
			String cardno, String secpass, String oldrealname, String oldcardno) {
		this.cellphone = cellphone;
		this.flag = flag;
		this.bankid = bankid;
		this.provinceid = provinceid;
		this.cityid = cityid;
		this.branch = branch;
		this.realname = realname;
		this.cardno = cardno;
		this.secpass = secpass;
		this.oldrealname = oldrealname;
		this.oldcardno = oldcardno;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getSecpass() {
		return secpass;
	}
	public void setSecpass(String secpass) {
		this.secpass = secpass;
	}
	public String getOldrealname() {
		return oldrealname;
	}
	public void setOldrealname(String oldrealname) {
		this.oldrealname = oldrealname;
	}
	public String getOldcardno() {
		return oldcardno;
	}
	public void setOldcardno(String oldcardno) {
		this.oldcardno = oldcardno;
	}

}
