package com.goldenasia.lottery.bean;

/**
 * 奖种信息 彩种ID 名称
 * @author Ace
 *
 */

public class LotteryInfo {
	
	private String lotteryId;
	private String lotteryName;
	private String isown;
	private String sorts;
	private String lotterytype;
	
	public LotteryInfo() {
	}

	public LotteryInfo(String lotteryId, String lotteryName, String isown,
			String sorts, String lotterytype) {
		this.lotteryId = lotteryId;
		this.lotteryName = lotteryName;
		this.isown = isown;
		this.sorts = sorts;
		this.lotterytype = lotterytype;
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

	public String getIsown() {
		return isown;
	}

	public void setIsown(String isown) {
		this.isown = isown;
	}

	public String getSorts() {
		return sorts;
	}

	public void setSorts(String sorts) {
		this.sorts = sorts;
	}

	public String getLotterytype() {
		return lotterytype;
	}

	public void setLotterytype(String lotterytype) {
		this.lotterytype = lotterytype;
	}

}
