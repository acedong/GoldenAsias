package com.goldenasia.lottery.bean;

public class BankInfo {
	private String bankid;
	private String bankname;
	
	public BankInfo()
	{}
	
	public BankInfo(String bankid, String bankname) {
		this.bankid = bankid;
		this.bankname = bankname;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
}
