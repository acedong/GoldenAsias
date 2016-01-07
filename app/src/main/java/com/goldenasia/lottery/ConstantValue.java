package com.goldenasia.lottery;


public interface ConstantValue {
	
	String VERSION="1.0.0";
	
	// class :public static final
	String ENCONDING="UTF-8";
	/**
	 * 代理的id
	 */
	String AGENTERID="889931";
	//<source>ivr</source>
	/**
	 * 信息来源
	 */
	String SOURCE="ivr";
	//<compress>DES</compress>
	/**
	 * body里面的数据加密算法
	 */
	String COMPRESS="DES";
	
	/**
	 * 子代理商的密钥(.so) JNI
	 */
	String AGENTER_PASSWORD = "9ab62a694d8bf6ced1fab6acd48d02f8";
	
	String URL_KEY="e66fda45cf56472bafa91228ff3b0593";
	
	/**
	 * des加密用密钥
	 */
	String DES_PASSWORD = "9b2648fcdfbad80f";
	/**
	 * 服务器地址
	 */
	String LOTTERY_API_URI = "http://119.9.71.158/_client/_sj/Address.txt";
	//String LOTTERY_API_URI = "http://ylsjblog.blog.163.com/blog/static/2484760542015412111238959/#";// 10.0.2.2模拟器如果需要跟PC机通信127.0.0.1
	// String LOTTERY_URI = "http://192.168.1.100:8080/ZCWService/Entrance";// 10.0.2.2模拟器如果需要跟PC机通信127.0.0.1 http://120.24.175.122:16888/
	
	/**
	 * 欢迎界面
	 */
	int VIEW_WELCOME=1;
	/**
	 * 用户登录
	 */
	int VIEW_LOGIN=2;
	/**
	 * 
	 */
	int VIEW_SECOND=3;
	/**
	 * 首页大厅
	 */
	int VIEW_HOME_HALL=10;

	/**
	 * 合买大厅
	 */
	int VIEW_COMPOSEBUY_HALL=20;
	
	/**
	 * 活动公告
	 */
	int VIEW_CAMPAIGN_HALL=30;
	
	/**
	 * 游戏记录
	 */
	int VIEW_GAMEHISTORY=40;
	
	/**
	 * 购物车
	 */
	int VIEW_SHOPPING=50;
	
	/**
	 * 生成追号
	 */
	int VIEW_APPENDBETTING= 55;
	/**
	 * 历史开奖列表，多彩种
	 */
	int VIEW_HISTORY= 60;
	
	/**
	 * 单彩种历史开奖 详情
	 */
	int VIEW_HISTORY_DETAIL=61;
	/**==================================================选号界面标实=============================================**/
	
	/**
	 * 时时彩选号界面
	 */
	int VIEW_SSC=11;
	
	/**
	 * 十一选五
	 */
	int VIEW_PLAYSYFIVE=12;
	
	/**
	 * 双色球选号界面
	 */
	int VIEW_SSQ=15;
	
	/**
	 * 追期和倍投的设置界面
	 */
	int VIEW_PREBET=25;
	
	/**
	 * 我的彩票大厅
	 */
	int VIEW_MYSELF_HALL=70;
	
	/**
	 * 用户消息
	 */
	int VIEW_MESSAGE=71;
	
	/**
	 * 个人消息详情
	 */
	int VIEW_MESSAGE_ONESELF_DETAIL=72;
	
	/**
	 * 系统消息详情
	 */
	int VIEW_MESSAGE_SYSTEM_DETAIL=73;
	
	/**
	 * 绑定信息 银行
	 */
	int VIEW_INFO_BACK=74;
	
	/**
	 * 提现
	 */
	int VIEW_INFO_WITHDRAWALS=75;
	
	/**
	 * 提现状态页面
	 */
	int VIEW_INFO_WITHDRAWALS_STATUS=76; 
	
	/**
	 * 订单详情
	 */
	int VIEW_ORDERDETAILS=78;
	
	/**
	 * 追号详情
	 */
	int VIEW_APPENDORDERDETAILS=79;
	
	/**
	 * 设置页面
	 */
	int VIEW_SETTINGS=80;
	
	/**
	 * 帐号密码
	 */
	int VIEW_ACCOUNT_PASSWORD=81;
	
	/**
	 * 资金密码
	 */
	int VIEW_FUNDS_PASSWORD=82;
	
	/**
	 * 添加新用户
	 */
	int VIEW_ADD_USER=83;
	
	/**
	 * 服务器返回成功状态码
	 */
	String SUCCESS="0";
	
	
	
	/**
	XmlPullParser parser = Xml.newPullParser();
			try {
				parser.setInput(is, ConstantValue.ENCONDING);

				int eventType = parser.getEventType();
				String name;

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("".equals(name)) {

						}
						break;
					}
					eventType = parser.next();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
	 */

}
