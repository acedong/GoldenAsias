package com.goldenasia.lottery.bean;

public class AnalysisBank {
	private String card;	//6212262010007462465
	private String province;//广东省
	private String city;	//东莞
	private String bank;	//工商银行
	private String type;	//借记卡
	private String cardname;//借记卡
	private String tel;		//95588
	
	public AnalysisBank() {
	}
	
	public AnalysisBank(String card, String province, String city, String bank,
			String type, String cardname, String tel) {
		this.card = card;
		this.province = province;
		this.city = city;
		this.bank = bank;
		this.type = type;
		this.cardname = cardname;
		this.tel = tel;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCardname() {
		return cardname;
	}
	public void setCardname(String cardname) {
		this.cardname = cardname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
}
