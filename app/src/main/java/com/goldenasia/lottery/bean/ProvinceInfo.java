package com.goldenasia.lottery.bean;

public class ProvinceInfo {
	private String provinceid;
	private String provinceName;
	
	public ProvinceInfo()
	{}
	
	public ProvinceInfo(String provinceid, String provinceName) {
		this.provinceid = provinceid;
		this.provinceName = provinceName;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
}
