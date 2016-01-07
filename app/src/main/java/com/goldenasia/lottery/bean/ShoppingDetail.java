package com.goldenasia.lottery.bean;

public class ShoppingDetail {
	
	private String pickstr; 
	private String playmenuName;
	private String noteNum;
	private String money;
	private String moneyMode;
	
	public ShoppingDetail() {
		
	}
	public ShoppingDetail(String pickstr, String playmenuName) {
		this.pickstr = pickstr;
		this.playmenuName = playmenuName;
	}
	
	public ShoppingDetail(String pickstr, String playmenuName, String noteNum) {
		this.pickstr = pickstr;
		this.playmenuName = playmenuName;
		this.noteNum = noteNum;
	}
	
	public String getPickstr() {
		return pickstr;
	}
	public void setPickstr(String pickstr) {
		this.pickstr = pickstr;
	}
	public String getPlaymenuName() {
		return playmenuName;
	}
	public void setPlaymenuName(String playmenuName) {
		this.playmenuName = playmenuName;
	}
	
	public String getNoteNum() {
		return noteNum;
	}
	
	public void setNoteNum(String noteNum) {
		this.noteNum = noteNum;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getMoneyMode() {
		return moneyMode;
	}
	public void setMoneyMode(String moneyMode) {
		this.moneyMode = moneyMode;
	}
}
