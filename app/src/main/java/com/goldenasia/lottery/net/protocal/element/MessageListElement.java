package com.goldenasia.lottery.net.protocal.element;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.MessageInfo;
import com.goldenasia.lottery.net.protocal.Element;

public class MessageListElement extends Element{
	
	private int count=0;
	
	private List<MessageInfo> messageInfoList=new ArrayList<MessageInfo>();

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MessageInfo> getMessageInfoList() {
		return messageInfoList;
	}

	public void setMessageInfoList(List<MessageInfo> messageInfoList) {
		this.messageInfoList = messageInfoList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
