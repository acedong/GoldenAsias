package com.goldenasia.lottery.bean;

public class CityInfo {
	private String cityId;
	private String cityName;
	
	public CityInfo(){}
	
	public CityInfo(String cityId, String cityName) {
		this.cityId = cityId;
		this.cityName = cityName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
