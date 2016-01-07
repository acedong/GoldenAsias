package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.MessageInfo;
import com.goldenasia.lottery.net.protocal.Element;

public class MessageInfoElement extends Element{
	
	private MessageInfo messageInfo=new MessageInfo();

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public MessageInfo getMessageInfo() {
		return messageInfo;
	}

	public void setMessageInfo(MessageInfo messageInfo) {
		this.messageInfo = messageInfo;
	}

}
