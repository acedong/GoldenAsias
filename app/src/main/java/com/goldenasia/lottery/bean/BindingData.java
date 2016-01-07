package com.goldenasia.lottery.bean;


public class BindingData {
	private String iCardmaxbind;
	private String iMyBindCount;
	private String allowdifname;
	private String needverify;
	
	public BindingData(){}

	public BindingData(String iCardmaxbind, String iMyBindCount,
			String allowdifname, String needverify) {
		this.iCardmaxbind = iCardmaxbind;
		this.iMyBindCount = iMyBindCount;
		this.allowdifname = allowdifname;
		this.needverify = needverify;
	}

	public String getiCardmaxbind() {
		return iCardmaxbind;
	}

	public void setiCardmaxbind(String iCardmaxbind) {
		this.iCardmaxbind = iCardmaxbind;
	}

	public String getiMyBindCount() {
		return iMyBindCount;
	}

	public void setiMyBindCount(String iMyBindCount) {
		this.iMyBindCount = iMyBindCount;
	}

	public String getAllowdifname() {
		return allowdifname;
	}

	public void setAllowdifname(String allowdifname) {
		this.allowdifname = allowdifname;
	}

	public String getNeedverify() {
		return needverify;
	}

	public void setNeedverify(String needverify) {
		this.needverify = needverify;
	}
	
	
}
