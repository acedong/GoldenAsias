package com.goldenasia.lottery.bean;

public class CardLimit {
	private int min;	//: "100",
	private int max;	//": "50000"
	
	public CardLimit() {
	}
	public CardLimit(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	
}
