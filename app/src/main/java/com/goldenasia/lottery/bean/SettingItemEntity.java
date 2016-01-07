package com.goldenasia.lottery.bean;


public class SettingItemEntity {
	private String content;
    private int icoImage;
    
	public SettingItemEntity(String content, int icoImage) {
		this.content = content;
		this.icoImage = icoImage;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getIcoImage() {
		return icoImage;
	}
	public void setIcoImage(int icoImage) {
		this.icoImage = icoImage;
	}  

}
