package com.goldenasia.lottery.bean;

public class HistoryLottery {
	private String lotteryId;
	private String lotteryName;
	private String lotteryIssue;
	private String lotterydigit;
	private String lotterytype;
	private String sorts;
	
	public HistoryLottery(){
		
	}

	public HistoryLottery(String lotteryId, String lotteryName,
			String lotteryIssue, String lotterydigit, String lotterytype,
			String sorts) {
		this.lotteryId = lotteryId;
		this.lotteryName = lotteryName;
		this.lotteryIssue = lotteryIssue;
		this.lotterydigit = lotterydigit;
		this.lotterytype = lotterytype;
		this.sorts = sorts;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getLotteryIssue() {
		return lotteryIssue;
	}

	public void setLotteryIssue(String lotteryIssue) {
		this.lotteryIssue = lotteryIssue;
	}

	public String getLotterydigit() {
		return lotterydigit;
	}

	public void setLotterydigit(String lotterydigit) {
		this.lotterydigit = lotterydigit;
	}

	public String getLotterytype() {
		return lotterytype;
	}

	public void setLotterytype(String lotterytype) {
		this.lotterytype = lotterytype;
	}

	public String getSorts() {
		return sorts;
	}

	public void setSorts(String sorts) {
		this.sorts = sorts;
	}
	
}
