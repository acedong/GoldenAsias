package com.goldenasia.lottery.bean;

public class MyCardInfo {
	private String bankname;// 中国招商银行
    private String cardno;  //": "2222",
    private String aliasname;//": "这是",
    private String entry;	//: "KANFQU"
    
    public MyCardInfo(){
    	
    }
    
	public MyCardInfo(String bankname, String cardno, String aliasname,
			String entry) {
		this.bankname = bankname;
		this.cardno = cardno;
		this.aliasname = aliasname;
		this.entry = entry;
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
	public String getAliasname() {
		return aliasname;
	}
	public void setAliasname(String aliasname) {
		this.aliasname = aliasname;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
    
    
}
