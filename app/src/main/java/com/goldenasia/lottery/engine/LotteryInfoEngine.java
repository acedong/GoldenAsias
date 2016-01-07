package com.goldenasia.lottery.engine;

import com.goldenasia.lottery.net.protocal.Message;

public interface LotteryInfoEngine {
	
	/**
	 * 获取近期开奖信息 所有彩种 前三种开奖信息
	 * @param integer
	 * @return
	 */
	Message getRecentLotteryInfo();
	
	/**
	 * 获取单彩种 所有开奖信息
	 * @param integer
	 * @return
	 */
	Message getSingleLotteryInfo(Integer integer);
}
