package com.goldenasia.lottery.bean;


public class LucreMode {
	
	private String lucreUnit; 	//模式单位
	private int lucreReckonId;	//钱财合算
	private double lucreReckon;
	
	public LucreMode(){}

	public LucreMode(String lucreUnit, int lucreReckonId, double d) {
		this.lucreUnit = lucreUnit;
		this.lucreReckonId = lucreReckonId;
		this.lucreReckon = d;
	}

	public String getLucreUnit() {
		return lucreUnit;
	}

	public void setLucreUnit(String lucreUnit) {
		this.lucreUnit = lucreUnit;
	}

	public int getLucreReckonId() {
		return lucreReckonId;
	}

	public void setLucreReckonId(int lucreReckonId) {
		this.lucreReckonId = lucreReckonId;
	}

	public double getLucreReckon() {
		return lucreReckon;
	}

}
