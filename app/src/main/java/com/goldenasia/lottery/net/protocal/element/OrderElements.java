package com.goldenasia.lottery.net.protocal.element;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.net.protocal.Element;

public class OrderElements extends Element{
	
	private OrderElement orderElement=new OrderElement();
	private List<OrderElement> orderElementList=new ArrayList<OrderElement>();
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "orders");
			orderElement.serializerElement(serializer);
			serializer.endTag(null, "orders");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public OrderElement getOrderElement() {
		return orderElement;
	}

	public void setOrderElement(OrderElement orderElement) {
		this.orderElement = orderElement;
	}

	public List<OrderElement> getOrderElementList() {
		return orderElementList;
	}

	public void setOrderElementList(List<OrderElement> orderElementList) {
		this.orderElementList = orderElementList;
	}

}
