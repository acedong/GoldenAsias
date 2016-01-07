package com.goldenasia.lottery.bean;

public class ProgramBuyInfo {
	private int methodid;
	private String codes;
	private int nums;
	private int times;
	private double money;
	private int mode;
	private boolean point;
	
	public ProgramBuyInfo(){
		
	}
	
	public ProgramBuyInfo(int methodid, String codes, int nums, int times,
			double money, int mode, boolean point) {
		this.methodid = methodid;
		this.codes = codes;
		this.nums = nums;
		this.times = times;
		this.money = money;
		this.mode = mode;
		this.point = point;
	}

	public int getMethodid() {
		return methodid;
	}



	public void setMethodid(int methodid) {
		this.methodid = methodid;
	}



	public String getCodes() {
		return codes;
	}



	public void setCodes(String codes) {
		this.codes = codes;
	}



	public int getNums() {
		return nums;
	}



	public void setNums(int nums) {
		this.nums = nums;
	}



	public int getTimes() {
		return times;
	}



	public void setTimes(int times) {
		this.times = times;
	}



	public double getMoney() {
		return money;
	}



	public void setMoney(double money) {
		this.money = money;
	}



	public int getMode() {
		return mode;
	}



	public void setMode(int mode) {
		this.mode = mode;
	}



	public boolean getPoint() {
		return point;
	}



	public void setPoint(boolean point) {
		this.point = point;
	}



	public String toString()
	{
		return "methodid:"+methodid+",codes:"+codes+",nums:"+nums+",times:"+times+",money:"+money+",mode:"+mode+",point:"+point;
	}
}
