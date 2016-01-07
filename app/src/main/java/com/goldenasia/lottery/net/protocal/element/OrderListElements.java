package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.net.protocal.Element;

public class OrderListElements extends Element{
	
	private OrderElements orderElements=new OrderElements();
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "element");
			orderElements.serializerElement(serializer);
			serializer.endTag(null, "element");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public OrderElements getOrderElements() {
		return orderElements;
	}

	public void setOrderElements(OrderElements orderElements) {
		this.orderElements = orderElements;
	}

}
