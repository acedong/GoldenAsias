package com.goldenasia.lottery.bean;
/**
 * 用户投注信息封装
 * @author Administrator
 *
 */
public class Ticket {
	/**===============================双色球========================*/
	private String redNum="";	
	private String blueNum="";
	/**===============================时时彩========================*/
	private String wanNum="";	
	private String qianNum="";
	private String baiNum="";
	private String shiNum="";
	private String geNum="";
	private String assembleSscNum="";
	
	/**=============================十一选五============================*/
	private String oneNum="";
	private String twoNum="";
	private String threeNum="";
	private String assembleSyFiveNum="";
	
	private int lotteryid;
	private int lotterytype;
	private String issue;
	private PlayMenu selectPlay;
	private LucreMode moneyMode;
	private double noteMoney;
	private int multiple;
	
	private int num;// 注数

	public String getRedNum() {
		return redNum;
	}

	public void setRedNum(String redNum) {
		this.redNum = redNum;
	}

	public String getBlueNum() {
		return blueNum;
	}

	public void setBlueNum(String blueNum) {
		this.blueNum = blueNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getWanNum() {
		return wanNum;
	}

	public void setWanNum(String wanNum) {
		this.wanNum = wanNum;
	}

	public String getQianNum() {
		return qianNum;
	}

	public void setQianNum(String qianNum) {
		this.qianNum = qianNum;
	}

	public String getBaiNum() {
		return baiNum;
	}

	public void setBaiNum(String baiNum) {
		this.baiNum = baiNum;
	}

	public String getShiNum() {
		return shiNum;
	}

	public void setShiNum(String shiNum) {
		this.shiNum = shiNum;
	}

	public String getGeNum() {
		return geNum;
	}

	public void setGeNum(String geNum) {
		this.geNum = geNum;
	}

	
	public String getOneNum() {
		return oneNum;
	}

	public void setOneNum(String oneNum) {
		this.oneNum = oneNum;
	}

	public String getTwoNum() {
		return twoNum;
	}

	public void setTwoNum(String twoNum) {
		this.twoNum = twoNum;
	}

	public String getThreeNum() {
		return threeNum;
	}

	public void setThreeNum(String threeNum) {
		this.threeNum = threeNum;
	}

	public PlayMenu getSelectPlay() {
		return selectPlay;
	}

	public void setSelectPlay(PlayMenu selectPlay) {
		this.selectPlay = selectPlay;
	}

	public LucreMode getMoneyMode() {
		return moneyMode;
	}

	public void setMoneyMode(LucreMode moneyMode) {
		this.moneyMode = moneyMode;
	}

	public String getAssembleSscNum() {
		return assembleSscNum;
	}

	public void setAssembleSscNum(String assembleSscNum) {
		this.assembleSscNum = assembleSscNum;
	}

	public String getAssembleSyFiveNum() {
		return assembleSyFiveNum;
	}

	public void setAssembleSyFiveNum(String assembleSyFiveNum) {
		this.assembleSyFiveNum = assembleSyFiveNum;
	}

	public double getNoteMoney() {
		return noteMoney;
	}

	public void setNoteMoney(double noteMoney) {
		this.noteMoney = noteMoney;
	}

	public int getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(int lotteryid) {
		this.lotteryid = lotteryid;
	}

	public int getLotterytype() {
		return lotterytype;
	}

	public void setLotterytype(int lotterytype) {
		this.lotterytype = lotterytype;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}
	
}
