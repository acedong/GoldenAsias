package com.goldenasia.lottery.bean;

import java.util.List;
import java.util.Map;

public class SettingItemClump {
	
	private String titleStr;
	private List<SettingItemEntity> itemEntity;
	
	public SettingItemClump() {
	}
	public SettingItemClump(String titleStr, List<SettingItemEntity> itemEntity) {
		this.titleStr = titleStr;
		this.itemEntity = itemEntity;
	}
	public String getTitleStr() {
		return titleStr;
	}
	public void setTitleStr(String titleStr) {
		this.titleStr = titleStr;
	}
	public List<SettingItemEntity> getItemEntity() {
		return itemEntity;
	}
	public void setItemEntity(List<SettingItemEntity> itemEntity) {
		this.itemEntity = itemEntity;
	}

}
