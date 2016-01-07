package com.goldenasia.lottery.bean;

public class ActivityDesc {
	private String starttime;	//": "2015-05-27 09:00:00",
	private String endtime;		//": "2015-08-15 02:30:00",
	private String minbet;		//": "2888.0000",
	private String baseprize;	//": "28.0000",
	private String title;		//": "指尖上的娱乐",
	private String desc;		//": "1. 活动投注仅限娱乐世界IOS手机客户端，安卓的用户请耐心等待，稍后即将上线。\r\n2. 每天投注时间为：当天09：00——次日02：00，活动奖金可自由领取。\r\n3. 因官方停售等原因造成的状况，不计入活动范畴。\r\n4. 所有彩金发放情况可以通过帐变列表中的活动礼金查看。\r\n5. 如发现任何个人或团体通过不正规手段，进行刷钱等违规操作，平台将视情况收回奖金或者本金。",
	private String detail;		//": "1. 娱乐世界玩家通过活动页面下载手机客户端登陆，并通过手机客户端投注金额达到2888元，即送手机红包28元。\r\n2. 同一账户只能领取一次红包，满即送。\r\n3. 红包金额可以通过帐变列表中的活动礼金查看。",
	private String function;	//": "firstphone"
	
	private int havegift;
	
	public ActivityDesc() {
	}

	public ActivityDesc(String starttime, String endtime, String minbet,
			String baseprize, String title, String desc, String detail,
			String function, int havegift) {
		this.starttime = starttime;
		this.endtime = endtime;
		this.minbet = minbet;
		this.baseprize = baseprize;
		this.title = title;
		this.desc = desc;
		this.detail = detail;
		this.function = function;
		this.havegift = havegift;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getMinbet() {
		return minbet;
	}

	public void setMinbet(String minbet) {
		this.minbet = minbet;
	}

	public String getBaseprize() {
		return baseprize;
	}

	public void setBaseprize(String baseprize) {
		this.baseprize = baseprize;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public int getHavegift() {
		return havegift;
	}

	public void setHavegift(int havegift) {
		this.havegift = havegift;
	}
	
}
