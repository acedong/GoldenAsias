package com.goldenasia.lottery.bean;

/**
 * "userinfo": {
    "transferdesc": "",
    "withdrawtime": "10:00-2:00",
    "cardlist": [
        {
            "bankname": "中国招商银行",
            "cardno": "2222",
            "aliasname": "这是",
            "entry": "KANFQU"
        }
    ],
    "bankaddtimelimit": 6,
    "iMyRequestOfToday": 0,
    "withdrawmax": "50000",
    "withdrawmin": "100",
    "iMaxRequestOfAday": "5",
    "useravailablebalance": "0.0000",
    "iswithdrawfee": "1",
    "cardlimit": {
        "KANFQU": {
            "min": "100",
            "max": "50000"
        }
    }
 * @author Ace
 *
 */

public class WithdrawalsUserInfo {
	private String transferdesc;
	private String withdrawtime;
	private int bankaddtimelimit;
	private int iMyRequestOfToday;
	private int withdrawmax;
	private int withdrawmin;
	private String iMaxRequestOfAday;
	private String useravailablebalance;
	private String iswithdrawfee;	//手须费
	
	public WithdrawalsUserInfo() {
	}
	
	public WithdrawalsUserInfo(String transferdesc, String withdrawtime,
			int bankaddtimelimit, int iMyRequestOfToday, int withdrawmax,
			int withdrawmin, String iMaxRequestOfAday,
			String useravailablebalance, String iswithdrawfee) {
		this.transferdesc = transferdesc;
		this.withdrawtime = withdrawtime;
		this.bankaddtimelimit = bankaddtimelimit;
		this.iMyRequestOfToday = iMyRequestOfToday;
		this.withdrawmax = withdrawmax;
		this.withdrawmin = withdrawmin;
		this.iMaxRequestOfAday = iMaxRequestOfAday;
		this.useravailablebalance = useravailablebalance;
		this.iswithdrawfee = iswithdrawfee;
	}

	public String getTransferdesc() {
		return transferdesc;
	}
	public void setTransferdesc(String transferdesc) {
		this.transferdesc = transferdesc;
	}
	public String getWithdrawtime() {
		return withdrawtime;
	}
	public void setWithdrawtime(String withdrawtime) {
		this.withdrawtime = withdrawtime;
	}
	public int getBankaddtimelimit() {
		return bankaddtimelimit;
	}
	public void setBankaddtimelimit(int bankaddtimelimit) {
		this.bankaddtimelimit = bankaddtimelimit;
	}
	public int getiMyRequestOfToday() {
		return iMyRequestOfToday;
	}
	public void setiMyRequestOfToday(int iMyRequestOfToday) {
		this.iMyRequestOfToday = iMyRequestOfToday;
	}
	public int getWithdrawmax() {
		return withdrawmax;
	}
	public void setWithdrawmax(int withdrawmax) {
		this.withdrawmax = withdrawmax;
	}
	public int getWithdrawmin() {
		return withdrawmin;
	}
	public void setWithdrawmin(int withdrawmin) {
		this.withdrawmin = withdrawmin;
	}
	public String getiMaxRequestOfAday() {
		return iMaxRequestOfAday;
	}
	public void setiMaxRequestOfAday(String iMaxRequestOfAday) {
		this.iMaxRequestOfAday = iMaxRequestOfAday;
	}
	public String getUseravailablebalance() {
		return useravailablebalance;
	}
	public void setUseravailablebalance(String useravailablebalance) {
		this.useravailablebalance = useravailablebalance;
	}
	public String getIswithdrawfee() {
		return iswithdrawfee;
	}
	public void setIswithdrawfee(String iswithdrawfee) {
		this.iswithdrawfee = iswithdrawfee;
	}
	
}
