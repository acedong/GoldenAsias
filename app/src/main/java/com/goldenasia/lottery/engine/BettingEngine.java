package com.goldenasia.lottery.engine;

import com.goldenasia.lottery.bean.AppendUndo;
import com.goldenasia.lottery.bean.BettingInfo;
import com.goldenasia.lottery.bean.SearchParam;
import com.goldenasia.lottery.net.protocal.Message;

public interface BettingEngine {
	/**
	 * 投注 无追号
	 * @param user
	 * @return
	 */
	Message bettingNumber(BettingInfo betting);
	
	/**
	 * 投注订单列表
	 * @param page
	 * @return
	 */
	Message bettingOrderListInfo(Integer page);
	
	/**
	 * 追号订单列表
	 * @param page
	 * @return
	 */
	Message appendOrderListInfo(Integer page);
	
	/**
	 * 追号详情
	 * @param id
	 * @return
	 */
	Message appendOrderDetailsInfo(String id);
	
	/**
	 * 撤销追号
	 * @param id
	 * @return
	 */
	Message appendOrderDetailsUndoInfo(AppendUndo undoInfo);
	
	/**
	 * 订单详情
	 * @param id
	 * @return
	 */
	Message getOrderDetailsInfo(Integer id);
	
	/**
	 * 取消订单
	 * @param id
	 * @return
	 */
	Message getOrderCancel(Integer id);
	
	/**
	 * 帐变列表
	 * @return
	 */
	Message getBillingListInfo(Integer id);
	
	/**
	 * 盈亏数据
	 * @return
	 */
	Message getProfitStatisticsInfo(SearchParam param);
}
