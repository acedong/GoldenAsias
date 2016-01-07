package com.goldenasia.lottery.bean;

import com.goldenasia.lottery.GlobalParams;

public enum APIUrl {
	
	LOGIN_INITIATIVE_API("LOGIN_INITIATIVE_API",GlobalParams.aipurl+"cellphone_login.shtml"),//主动登录
	
	LOGIN_PASSIVE_API("LOGIN_PASSIVE_API",GlobalParams.aipurl+"cellphone_login.shtml"),		//被动登录
	
	PLAYMETHOD_API("PLAYMETHOD_API", GlobalParams.aipurl+"cellphone_method.shtml"),			//彩种玩法
	
	ISSUE_API("ISSUE_API",GlobalParams.aipurl+"cellphone_issue.shtml"),						//奖期
	
	BALANCE_API("BALANCE_API",GlobalParams.aipurl+"cellphone_balance.shtml"),					//获取金额
	
	NOTICE_LIST_API("NOTICE_LIST_API", GlobalParams.aipurl+"cellphone_noticelist.shtml"),		//公告列表
	
	NOTICE_DETAILS_API("NOTICE_DETAILS_API", GlobalParams.aipurl+"cellphone_noticeinfo.shtml"),//公告详情
	
	PLAY_API("PLAY_API", GlobalParams.aipurl+"cellphone_play.shtml"),
	
	BETTING_HISTORY_LIST_API("BETTING_HISTORY_LIST_API", GlobalParams.aipurl+"cellphone_projectlist.shtml"),		//投注历史
	
	BETTING_DETAILS_API("BETTING_DETAILS_API", GlobalParams.aipurl+"cellphone_projectinfo.shtml"),		//投注详情
	
	REVOCATION_API("REVOCATION_API", GlobalParams.aipurl+"cellphone_projectcancel.shtml"),			//撤单
	
	ADDITIONAL_LIST_API("ADDITIONAL_LIST_API", GlobalParams.aipurl+"cellphone_tasklist.shtml"),		//追号列表
	
	ADDITIONAL_DETAILS_API("ADDITIONAL_DETAILS_API", GlobalParams.aipurl+"cellphone_taskinfo.shtml"),		//追号详情
	
	REVOCATION_ADDITIONAL_API("REVOCATION_ADDITIONAL_API", GlobalParams.aipurl+"cellphone_taskcancel.shtml"),	//撤消追号
	
	VARIABLE_ACCOUNTS_LIST_API("VARIABLE_ACCOUNTS_LIST_API", GlobalParams.aipurl+"cellphone_orderlist.shtml"),	//账变列表
	
	HISTORY_LOTTERY_CODE_API("HISTORY_LOTTERY_CODE_API", GlobalParams.aipurl+"cellphone_historycode.shtml"),  	//历史开奖号码
	
	SYSTEM_INFO_API("SYSTEM_INFO_API", GlobalParams.aipurl+"cellphone_message.shtml"),			//系统信息
	
	PROMOTION_INFO_API("PROMOTION_INFO_API", GlobalParams.aipurl+"cellphone_promotion.shtml"),			//活动
	
	ADDCARD_API("HISTORY_LOTTERY_CODE_API", GlobalParams.aipurl+"cellphone_addcard.shtml"),  	//银行绑定卡 绑卡相关数据
	
	WITHDRAW_API("WITHDRAW_API",GlobalParams.aipurl+"cellphone_withdraw.shtml"),	//提现接口
	
	PASSWORD_API("PASSWORD_API",GlobalParams.aipurl+"cellphone_password.shtml"),    //密码管理接口
	
	VERSION_API("VERSION_API",GlobalParams.aipurl+"cellphone_getversion.shtml"),    //版本号
	
	ADDUSER_API("ADDUSER_API",GlobalParams.aipurl+"cellphone_adduser.shtml"), 	//添加用户
	
	REPORT_API("REPORT_API",GlobalParams.aipurl+"cellphone_report.shtml"),		//盈亏统计
	
	LOGOUT_API("LOGOUT_API",GlobalParams.aipurl+"cellphone_logout.shtml")			//登出
	;
	
    private APIUrl(String codingapi,String urlpath){  
    	this.coding=codingapi;
        this.apiurl = urlpath;  
    }  
    
    public String toPathurl(){  
        return apiurl;  
    }
      
    public String toString(){  
        return apiurl;  
    }
    
    
    
    private String coding;
    private String apiurl;
}

