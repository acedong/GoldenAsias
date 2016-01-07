package com.goldenasia.lottery.engine;

import com.goldenasia.lottery.bean.BanksBoundCards;
import com.goldenasia.lottery.bean.PassWord;
import com.goldenasia.lottery.bean.User;
import com.goldenasia.lottery.bean.WithdrawalsInfo;
import com.goldenasia.lottery.net.protocal.Message;
/**
 * 用户相关的业务操作的接口
 * @author Administrator
 *
 */
public interface UserEngine {
	/**
	 * 获取路径
	 * @return
	 */
	Message takeApiPathTxT();
	/**
	 * 获取路径
	 * @return
	 */
	Message takeApiPathBlog();
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	Message login(User user);
	
	/**
	 * 修改登录密码
	 * @param password
	 * @return
	 */
	Message loginPassword(PassWord password);
	
	/**
	 * 修改资金密码
	 * @param password
	 * @return
	 */
	Message fundsPassword(PassWord password);
	
	/**
	 * 获取用户余额
	 * @param user
	 * @return
	 */
	Message getBalance();
	
	/**
	 * 取款信息
	 * @param withdrawal
	 * @return
	 */
	Message getWithdrawals(WithdrawalsInfo withdrawal);
	
	/**
	 * 银行种类
	 * @param user
	 * @return
	 */
	Message bankInfoAddCard(BanksBoundCards boundcards);
	
	/**
	 * 用户投注
	 * @param user
	 * @return
	 */
//	Message bet(User user);
	
	/**
	 * 退出系统
	 * @return
	 */
	Message Logout();
	
	/**
	 * 版本号
	 */
	Message Version();
}
