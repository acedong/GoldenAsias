package com.goldenasia.lottery.bean;

public class APIInfo {

	public static final String API_LOGIN_INITIATIVE = "API_LOGIN_INITIATIVE"; /* 登录 */
	public static final String API_LOGIN_PASSIVE = "API_LOGIN_PASSIVE"; /* 被动登录 */
	public static final String API_BALANCE = "API_BALANCE"; /* 取用户金额 */
	public static final String API_PLAYMETHOD = "API_PLAYMETHOD"; /* 彩种玩法 */
	public static final String API_ISSUE = "API_ISSUE"; /* 奖期 */
	public static final String API_PLAY = "API_PLAY"; /* 投注 */
	public static final String API_NOTICE_LIST = "API_NOTICE_LIST"; /* 公告列表 */
	public static final String API_NOTICE_DETAILS = "API_NOTICE_DETAILS"; /* 公告详情 */
	public static final String API_BETTING_HISTORY = "API_BETTING_HISTORY"; /* 投注历史 */
	public static final String API_BETTING_DETAILS = "API_BETTING_DETAILS"; /* 投注详情 */
	public static final String API_REVOCATION = "API_REVOCATION"; /* 撤单 */
	public static final String API_ADDITIONAL_LIST = "API_ADDITIONAL_LIST"; /* 追号列表 */
	public static final String API_ADDITIONAL_DETAILS = "API_ADDITIONAL_DETAILS"; /* 追号详情 */
	public static final String API_REVOCATION_ADDITIONAL = "API_REVOCATION_ADDITIONAL"; /* 撤消追号 */
	public static final String API_VARIABLE_ACCOUNTS_LIST = "API_VARIABLE_ACCOUNTS_LIST"; /* 账变列表 */
	public static final String API_HISTORY_LOTTERY_CODE = "API_HISTORY_LOTTERY_CODE"; /* 历史开奖号码 */
	public static final String API_SYSTEM_INFO = "API_SYSTEM_INFO"; /* 系统信息 */
	public static final String API_PROMOTION_INFO = "API_PROMOTION_INFO"; /* 活动信息 */
	public static final String API_ADD_CARD_INFO = "API_ADD_CARD_INFO"; /* 绑卡相关数据 */
	public static final String API_WITHDRAW = "API_WITHDRAW"; /* 银行提现信息 */
	public static final String API_PASSWORD = "API_PASSWORD"; /* 密码管理接口 */
	public static final String API_LOGOUT = "API_LOGOUT"; /* 退出登录 */
	public static final String API_ADDUSER = "API_ADDUSER"; /* 添加用户 */
	public static final String API_REPORT = "API_REPORT"; /* 盈亏统计 */
	public static final String API_VERSION = "API_VERSION"; /* 版本号 */
	private static APIInfo _instance = new APIInfo();

	private APIUrl platform;

	private APIInfo() {
	}

	private static boolean isLogin(String coding) {
		return coding.equals(API_LOGIN_INITIATIVE);
	}

	private static boolean isBalance(String coding) {
		return coding.equals(API_BALANCE);
	}

	private static boolean isPlayMethod(String coding) {
		return coding.equals(API_PLAYMETHOD);
	}

	private static boolean isIssue(String coding) {
		return coding.equals(API_ISSUE);
	}

	private static boolean isPlay(String coding) {
		return coding.equals(API_PLAY);
	}

	private static boolean isNoticeList(String coding) {
		return coding.equals(API_NOTICE_LIST);
	}

	private static boolean isNoticeDetails(String coding) {
		return coding.equals(API_NOTICE_DETAILS);
	}

	private static boolean isBettingHistoryList(String coding) {
		return coding.equals(API_BETTING_HISTORY);
	}

	private static boolean isBettingDetails(String coding) {
		return coding.equals(API_BETTING_DETAILS);
	}

	private static boolean isRevoation(String coding) {
		return coding.equals(API_REVOCATION);
	}

	private static boolean isAdditionalList(String coding) {
		return coding.equals(API_ADDITIONAL_LIST);
	}

	private static boolean isAdditionalDetails(String coding) {
		return coding.equals(API_ADDITIONAL_DETAILS);
	}

	private static boolean isRevoationAdditional(String coding) {
		return coding.equals(API_REVOCATION_ADDITIONAL);
	}

	private static boolean isVariableAccountsList(String coding) {
		return coding.equals(API_VARIABLE_ACCOUNTS_LIST);
	}

	private static boolean isHistoryLotteryCode(String coding) {
		return coding.equals(API_HISTORY_LOTTERY_CODE);
	}

	private static boolean isSystemInfo(String coding) {
		return coding.equals(API_SYSTEM_INFO);
	}

	private static boolean isAddCardInfo(String coding) {
		return coding.equals(API_ADD_CARD_INFO);
	}

	private static boolean isWithdraw(String coding) {
		return coding.equals(API_WITHDRAW);
	}

	private static boolean isPassword(String coding) {
		return coding.equals(API_PASSWORD);
	}

	private static boolean isLogout(String coding) {
		return coding.equals(API_LOGOUT);
	}

	private static boolean isAddUser(String coding) {
		return coding.equals(API_ADDUSER);
	}

	private static boolean isReport(String coding) {
		return coding.equals(API_REPORT);
	}
	
	private static boolean isPromotion(String coding) {
		return coding.equals(API_PROMOTION_INFO);
	}

	private static boolean isVersion(String coding) {
		return coding.equals(API_VERSION);
	}

	/**
	 * 获取url 路径
	 * 
	 * @return url
	 */
	public static APIUrl getUrlPathStr(String coding) {

		if (isLogin(coding)) {
			_instance.platform = APIUrl.LOGIN_INITIATIVE_API;
		} else if (isBalance(coding)) {
			_instance.platform = APIUrl.BALANCE_API;
		} else if (isPlayMethod(coding)) {
			_instance.platform = APIUrl.PLAYMETHOD_API;
		} else if (isIssue(coding)) {
			_instance.platform = APIUrl.ISSUE_API;
		} else if (isPlay(coding)) {
			_instance.platform = APIUrl.PLAY_API;
		} else if (isNoticeList(coding)) {
			_instance.platform = APIUrl.NOTICE_LIST_API;
		} else if (isNoticeDetails(coding)) {
			_instance.platform = APIUrl.NOTICE_DETAILS_API;
		} else if (isBettingHistoryList(coding)) {
			_instance.platform = APIUrl.BETTING_HISTORY_LIST_API;
		} else if (isBettingDetails(coding)) {
			_instance.platform = APIUrl.BETTING_DETAILS_API;
		} else if (isRevoation(coding)) {
			_instance.platform = APIUrl.REVOCATION_API;
		} else if (isAdditionalList(coding)) {
			_instance.platform = APIUrl.ADDITIONAL_LIST_API;
		} else if (isAdditionalDetails(coding)) {
			_instance.platform = APIUrl.ADDITIONAL_DETAILS_API;
		} else if (isRevoationAdditional(coding)) {
			_instance.platform = APIUrl.REVOCATION_ADDITIONAL_API;
		} else if (isVariableAccountsList(coding)) {
			_instance.platform = APIUrl.VARIABLE_ACCOUNTS_LIST_API;
		} else if (isHistoryLotteryCode(coding)) {
			_instance.platform = APIUrl.HISTORY_LOTTERY_CODE_API;
		} else if (isSystemInfo(coding)) {
			_instance.platform = APIUrl.SYSTEM_INFO_API;
		} else if (isAddCardInfo(coding)) {
			_instance.platform = APIUrl.ADDCARD_API;
		} else if (isWithdraw(coding)) {
			_instance.platform = APIUrl.WITHDRAW_API;
		} else if (isPassword(coding)) {
			_instance.platform = APIUrl.PASSWORD_API;
		} else if (isVersion(coding)) {
			_instance.platform = APIUrl.VERSION_API;
		} else if (isAddUser(coding)) {
			_instance.platform = APIUrl.ADDUSER_API;
		} else if (isReport(coding)) {
			_instance.platform = APIUrl.REPORT_API;
		} else if(isPromotion(coding)){
			_instance.platform = APIUrl.PROMOTION_INFO_API;
		}else if (isLogout(coding)) {
			_instance.platform = APIUrl.LOGOUT_API;
		}
		return _instance.platform;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("这里是路径封装"
				+ APIInfo.getUrlPathStr("API_LOGIN_INITIATIVE").toPathurl());
	}
}
