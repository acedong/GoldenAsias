package com.goldenasia.lottery.net.protocal.element;

import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.IssueAll;
import com.goldenasia.lottery.bean.RecordTime;
import com.goldenasia.lottery.net.protocal.Element;

public class TaskIssueInfoElement extends Element{
	
	private Map<String, IssueAll> issueAllMap=new HashMap<String, IssueAll>();
	private Map<String, RecordTime> saleendTime=new HashMap<String, RecordTime>();
	private Map<String, RecordTime> openTime=new HashMap<String, RecordTime>();
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
	}
	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<String, IssueAll> getIssueAllMap() {
		return issueAllMap;
	}
	public void setIssueAllMap(Map<String, IssueAll> issueAllMap) {
		this.issueAllMap = issueAllMap;
	}
	
	public Map<String, RecordTime> getSaleendTime() {
		return saleendTime;
	}
	public void setSaleendTime(Map<String, RecordTime> saleendTime) {
		this.saleendTime = saleendTime;
	}
	public Map<String, RecordTime> getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Map<String, RecordTime> openTime) {
		this.openTime = openTime;
	}
	
}
