package com.goldenasia.lottery.engine;

import com.goldenasia.lottery.net.protocal.Message;

/**
 * 公共数据处理
 * @author Administrator
 *
 */
public interface CommonInfoEngine {
	
	/**
	 * 获取当前彩种列表信息
	 * @param integer
	 * @return
	 */
	
	Message getSpeciesListInfo(Integer integer);
	/**
	 * 获取当前销售期信息
	 * @param integer：彩种的标示
	 * @return
	 */
	Message getCurrentIssueInfo(Integer integer);
	
	/**
	 * 获取当前彩种玩法
	 * @param integer 彩种标示
	 * @return
	 */
	Message getLotteryMenuInfo(Integer integer);
	
}
