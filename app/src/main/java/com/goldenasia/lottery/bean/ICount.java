package com.goldenasia.lottery.bean;

public class ICount {
	private double in;	//": 0.15,
	private double out;	//": 2,
    private double left;	//": -1.85
    
	public ICount() {
		
	}
	public ICount(double in, int out, double left) {
		this.in = in;
		this.out = out;
		this.left = left;
	}
	public double getIn() {
		return in;
	}
	public void setIn(double in) {
		this.in = in;
	}
	public double getOut() {
		return out;
	}
	public void setOut(double out) {
		this.out = out;
	}
	public double getLeft() {
		return left;
	}
	public void setLeft(double left) {
		this.left = left;
	}
}
