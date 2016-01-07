package com.goldenasia.lottery.bean;

public class SearchParam {
	private String cellphone;
	private String startdate;
	private String enddate;
	
	public SearchParam(){}
	
	public SearchParam(String cellphone, String startdate, String enddate) {
		this.cellphone = cellphone;
		this.startdate = startdate;
		this.enddate = enddate;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
}
