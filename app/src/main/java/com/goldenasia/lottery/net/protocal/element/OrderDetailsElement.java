package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.OrderDetails;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

public class OrderDetailsElement extends Element{

	private Leaf nocode = new Leaf("nocode");	//名称
	private Leaf dypointdec = new Leaf("dypointdec");	//名称
	private Leaf writetime = new Leaf("writetime");	//名称
	private Leaf totalprice = new Leaf("totalprice");	//名称
	private Leaf issue = new Leaf("issue");	//名称
	private Leaf taskid = new Leaf("taskid");	//名称
	private Leaf code = new Leaf("code");	//名称
	private Leaf username = new Leaf("username");	//名称
	private Leaf methodname = new Leaf("methodname");	//名称
	private Leaf cnname = new Leaf("cnname");	//名称
	private Leaf bonus = new Leaf("bonus");	//名称
	private Leaf modes = new Leaf("modes");	//名称
	private Leaf projectid = new Leaf("projectid");	//名称
	private Leaf statusdesc = new Leaf("statusdesc");	//名称
	
	
	private OrderDetails orderDetails=new OrderDetails();
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "details");
			nocode.serializerLeaf(serializer);
			dypointdec.serializerLeaf(serializer);
			writetime.serializerLeaf(serializer);
			totalprice.serializerLeaf(serializer);
			issue.serializerLeaf(serializer);
			projectid.serializerLeaf(serializer);
			modes.serializerLeaf(serializer);
			taskid.serializerLeaf(serializer);
			code.serializerLeaf(serializer);
			username.serializerLeaf(serializer);
			methodname.serializerLeaf(serializer);
			cnname.serializerLeaf(serializer);
			bonus.serializerLeaf(serializer);
			statusdesc.serializerLeaf(serializer);
			
			serializer.endTag(null, "details");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Leaf getNocode() {
		return nocode;
	}

	public Leaf getDypointdec() {
		return dypointdec;
	}

	public Leaf getWritetime() {
		return writetime;
	}

	public Leaf getTotalprice() {
		return totalprice;
	}

	public Leaf getIssue() {
		return issue;
	}

	public Leaf getTaskid() {
		return taskid;
	}

	public Leaf getCode() {
		return code;
	}

	public Leaf getUsername() {
		return username;
	}

	public Leaf getMethodname() {
		return methodname;
	}

	public Leaf getCnname() {
		return cnname;
	}

	public Leaf getBonus() {
		return bonus;
	}

	public Leaf getModes() {
		return modes;
	}

	public Leaf getProjectid() {
		return projectid;
	}

	public Leaf getStatusdesc() {
		return statusdesc;
	}

	public OrderDetails getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

}
