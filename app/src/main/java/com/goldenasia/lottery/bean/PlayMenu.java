package com.goldenasia.lottery.bean;

import java.util.Arrays;
import java.util.List;

public class PlayMenu{
	
	private String iscellphone;
	private String jscode;
	private String methodname;
	private String methodid;
	private String playmode;
	
	private List<String> playmodeList=Arrays.asList("AGO","AFTER");
	
	public PlayMenu(){
		
	}
	
	public PlayMenu(String iscellphone, String jscode, String methodname,
			String methodid) {
		this.iscellphone = iscellphone;
		this.jscode = jscode;
		this.methodname = methodname;
		this.methodid = methodid;
	}

	public PlayMenu(String iscellphone, String jscode, String methodname,
			String methodid,String playmode) {
		this.iscellphone = iscellphone;
		this.jscode = jscode;
		this.methodname = methodname;
		this.methodid = methodid;
		this.playmode=playmode;
	}

	public String getIscellphone() {
		return iscellphone;
	}
	public void setIscellphone(String iscellphone) {
		this.iscellphone = iscellphone;
	}
	public String getJscode() {
		return jscode;
	}
	public void setJscode(String jscode) {
		this.jscode = jscode;
	}
	public String getMethodname() {
		return methodname;
	}
	public void setMethodname(String methodname) {
		this.methodname = methodname;
	}
	public String getMethodid() {
		return methodid;
	}
	public void setMethodid(String methodid) {
		this.methodid = methodid;
	}

	public String getPlaymode() {
		return playmode;
	}

	public void setPlaymode(String playmode) {
		this.playmode = playmode;
	}

	public List<String> getPlaymodeList() {
		return playmodeList;
	}

}
