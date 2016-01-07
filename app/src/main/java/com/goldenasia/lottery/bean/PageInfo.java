package com.goldenasia.lottery.bean;

public class PageInfo {
	private int pagecount;
    private int currentpage;
    
	public PageInfo() {
	}
	public PageInfo(int pagecount, int currentpage) {
		this.pagecount = pagecount;
		this.currentpage = currentpage;
	}
	public int getPagecount() {
		return pagecount;
	}
	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
}
