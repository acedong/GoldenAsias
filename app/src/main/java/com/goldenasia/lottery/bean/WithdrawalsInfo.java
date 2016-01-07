package com.goldenasia.lottery.bean;

import java.io.Serializable;

public class WithdrawalsInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cellphone;
	private String flag="final";
	private double money;
	private String bankinfo;
	private String secpass;
	
	public WithdrawalsInfo(){}
	
	public WithdrawalsInfo(String cellphone, String flag, double money,
			String bankinfo, String secpass) {
		this.cellphone = cellphone;
		this.flag = flag;
		this.money = money;
		this.bankinfo = bankinfo;
		this.secpass = secpass;
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
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getBankinfo() {
		return bankinfo;
	}
	public void setBankinfo(String bankinfo) {
		this.bankinfo = bankinfo;
	}
	public String getSecpass() {
		return secpass;
	}
	public void setSecpass(String secpass) {
		this.secpass = secpass;
	}

}
