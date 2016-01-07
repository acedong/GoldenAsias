package com.goldenasia.lottery.bean;

public class LotteryLogo {
	
	private String lotteryName;
	private int lotteryLogo;
	private String lotteryPrompt;
	
	public LotteryLogo() {
	}

	public LotteryLogo(String lotteryName, int lotteryLogo, String lotteryPrompt) {
		this.lotteryName = lotteryName;
		this.lotteryLogo = lotteryLogo;
		this.lotteryPrompt = lotteryPrompt;
	}

	public LotteryLogo(String lotteryName, int lotteryLogo) {
		this.lotteryName = lotteryName;
		this.lotteryLogo = lotteryLogo;
	}
	
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public int getLotteryLogo() {
		return lotteryLogo;
	}
	public void setLotteryLogo(int lotteryLogo) {
		this.lotteryLogo = lotteryLogo;
	}

	public String getLotteryPrompt() {
		return lotteryPrompt;
	}

	public void setLotteryPrompt(String lotteryPrompt) {
		this.lotteryPrompt = lotteryPrompt;
	}

}
