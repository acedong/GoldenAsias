package com.goldenasia.lottery.net.protocal.element;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.net.protocal.Element;

public class HistoryLotteryElements extends Element{
	
	private HistoryLotteryElement historyElement=new HistoryLotteryElement();
	
	private List<CurrentIssueElement> issueList=new ArrayList<CurrentIssueElement>();
	
	private List<HistoryLotteryElement> historyElementList=new ArrayList<HistoryLotteryElement>();
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "historylotterys");
			historyElement.serializerElement(serializer);
			serializer.endTag(null, "historylotterys");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public HistoryLotteryElement getHistoryElement() {
		return historyElement;
	}

	public void setHistoryElement(HistoryLotteryElement historyElement) {
		this.historyElement = historyElement;
	}

	public List<HistoryLotteryElement> getHistoryElementList() {
		return historyElementList;
	}

	public void setHistoryElementList(List<HistoryLotteryElement> historyElementList) {
		this.historyElementList = historyElementList;
	}

	public List<CurrentIssueElement> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<CurrentIssueElement> issueList) {
		this.issueList = issueList;
	}

}
