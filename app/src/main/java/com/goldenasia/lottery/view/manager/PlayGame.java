package com.goldenasia.lottery.view.manager;

import com.goldenasia.lottery.bean.LucreMode;

/**
 * 玩法相关界面操作
 */
public interface PlayGame {
	
	int getLotteryId();
	
	/**
	 * 彩票ID 设置
	 * @param id
	 */
	void setLotteryId(int id);
	
	/**
	 * 彩种类型
	 * @return
	 */
	void setLotteryType(int type);
	
	/**
	 * 彩种类型
	 * @return
	 */
	int getLotteryType();
	
	/**
	 * 彩种名称 设置
	 * @return
	 */
	void setLotteryName(String name);
	
	/**
	 * 彩种名称
	 * @return
	 */
	String getLotteryName();
	
	/**
	 * 元角分模式选择
	 */
	void setSelectLucreMode(LucreMode selectmode);
	/**
	 * 获取元角分选择模式
	 */
	LucreMode getSelectLucreMode();
	
	/**
	 * 清空
	 */
	void clearMethod();
	
	/**
	 * 随机选号
	 */
	void randomMethod();
	
	/**
	 * 模式选择 计算处理（元、角、分）
	 */
	void setMoneyModeMethod(LucreMode mode);
	
	/**
	 * 获取 倍数
	 */
	Integer getAppnumbers();
	
	/**
	 * 增加倍数
	 * @return
	 */
	boolean addAppnumbers(boolean isAdd);
	
	/**
	 * 选好了
	 */
	void done();
}
