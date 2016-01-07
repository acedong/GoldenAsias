package com.goldenasia.lottery.bean;

public class BankSupportInfo {
	private String bankid;
	private String bankname;
    private String cardno;
    private String entry;
    //"userbankinfo":{"entry":"KAQQ6R","bankname":"中国农业银行","bankid":"2","cardno":"***************9876"},
    public BankSupportInfo(){
    	
    }
    
	public BankSupportInfo(String bankid, String bankname, String cardno,
			String entry) {
		super();
		this.bankid = bankid;
		this.bankname = bankname;
		this.cardno = cardno;
		this.entry = entry;
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
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}

}
