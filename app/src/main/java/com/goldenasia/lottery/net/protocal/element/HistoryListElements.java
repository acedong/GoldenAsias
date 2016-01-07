package com.goldenasia.lottery.net.protocal.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.IssueLast;
import com.goldenasia.lottery.bean.LotteryInfo;
import com.goldenasia.lottery.net.protocal.Element;

public class HistoryListElements extends Element{
	
	private List<LotteryInfo> lotteryList=new ArrayList<LotteryInfo>();
	
	private Map<String, List<IssueLast>> historyMap=new HashMap<String, List<IssueLast>>();

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<LotteryInfo> getLotteryList() {
		return lotteryList;
	}

	public void setLotteryList(List<LotteryInfo> lotteryList) {
		this.lotteryList = lotteryList;
	}

	public Map<String, List<IssueLast>> getHistoryMap() {
		return historyMap;
	}

	public void setHistoryMap(Map<String, List<IssueLast>> historyMap) {
		this.historyMap = historyMap;
	}

}
