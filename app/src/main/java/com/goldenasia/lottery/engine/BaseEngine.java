package com.goldenasia.lottery.engine;

import com.goldenasia.lottery.bean.BanksBoundCards;
import com.goldenasia.lottery.bean.PassWord;
import com.goldenasia.lottery.bean.User;
import com.goldenasia.lottery.bean.WithdrawalsInfo;
import com.goldenasia.lottery.net.protocal.Message;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public abstract class BaseEngine {
	/**
	 * 主动登录 实现
	 * @param user
	 * @return
	 */

	public Message getResultLogin(User user) {


		return null;
	}
	
	/**
	 * 种类列表
	 * @param xml
	 * @return
	 */
	
	public Message getSpeciesListMethod(String xml){
		return null;
	}
	
	

	/**
	 * 当前销售彩种信息
	 * @param xml
	 * @param lotteryid
	 * @param task
	 * @return
	 */
	public Message getResultCurrentIssueMethod(String xml,Integer lotteryid,Integer task) {
		return null;
	}

	/**
	 * 彩种玩法获取
	 * @param xml
	 * @return
	 */
	public Message getResultLotteryMenuInfoMethod(String xml,Integer lotteryId) {
		return null;
	}
	/**
	 * 余额查询
	 * @param xml
	 * @return
	 */
	public Message getResultBalance(String xml) {
		return null;
	}
	
	
	public Message getResultBankInfoAddCard(BanksBoundCards boundcards) {
		return null;
	}

	/**
	 * @Title: getResult
	 * @author Ace
	 * @Description: 请求Xml信息 （测试xml请求）
	 * @param xml
	 * @return
	 * @throws Exception
	 *             Message 返回
	 */

	public Message getResult(String xml) {return null;}
	
	public Message getResultPassword(PassWord passwrod){return null;}
	
	public Message getResultWithdrawals(WithdrawalsInfo withdrawal){return null;}
	
	public Message getResultLogOut(){return null;}
	
	
	public Message getResultVersion(String xml){return null;}
	
	public Message getResultTakeApiPath(){return null;}
	
	public Message getResultTakeApiPathTxt(){return null;}

	/**
	 * @Title: initializeHttpClient
	 * @author Ace
	 * @Description: 初始连接信息
	 * @throws Exception
	 */
	private void initializeHttpClient() {

	}

	/**
	 * @Title: jsonResolve
	 * @author Ace
	 * @Description: json信息
	 * @throws Exception
	 */
	private JSONObject jsonResolve(String jsonstr) throws JSONException {
		JSONTokener jsonParser = new JSONTokener(jsonstr);
		JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
		return jsonObject;
	}
}
