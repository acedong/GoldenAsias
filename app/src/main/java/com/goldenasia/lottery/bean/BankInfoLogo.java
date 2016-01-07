package com.goldenasia.lottery.bean;

public class BankInfoLogo {
	
	private String bankName;
	private int logoId;
	
	public BankInfoLogo() {
	}
	public BankInfoLogo(String bankName, int logoId) {
		this.bankName = bankName;
		this.logoId = logoId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getLogoId() {
		return logoId;
	}
	public void setLogoId(int logoId) {
		this.logoId = logoId;
	}
}
