package com.goldenasia.lottery.engine;

import com.goldenasia.lottery.bean.AddUserInfo;
import com.goldenasia.lottery.bean.BanksBoundCards;
import com.goldenasia.lottery.bean.PersonalMsg;
import com.goldenasia.lottery.net.protocal.Message;

public interface SettingInfoEngine {
	
	/**
	 * 开户配额
	 * @return
	 */
	Message addUserQuota();
	
	/**
	 * 添加用户数据
	 * @return
	 */
	Message addUserData(AddUserInfo user);
	
	/**
	 * AnalysisData
	 * @param boundcards
	 * @return
	 */
	Message analysisData(String cardNo);
	
	/**
	 * 个人消息
	 * @return
	 */
	Message oneselfMessages(PersonalMsg msgParam);
	
	/**
	 * 系统消息
	 * @return
	 */
	Message systemMessages();
	
	/**
	 * 系统消息详情
	 * @param id
	 * @return
	 */
	Message systemMessagesDetail(String id);
	
	/**
	 * 活动详情
	 * @param id
	 * @return
	 */
	Message getcampaignDetail(String id);
	
	/**
	 * 活动领取
	 * @param id
	 * @return 
	 */
	Message postcampaignDetail(String id);
}
