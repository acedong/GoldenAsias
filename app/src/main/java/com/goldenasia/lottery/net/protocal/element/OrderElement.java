package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.OrderCatalog;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

/**
 * 彩票订单
 * @author Ace
 *
 */

public class OrderElement extends Element{
	
	/**
	 private String lotteryid; 	//彩种ID
	 private String lotteryname;//名称
	 private String betting_issue; //期号
	 private String playmethod;		//玩法
	 private String bettingMoney;	//金额
	 private String pattern;		//模式
	 private String rebate;			//返点	
	 private String bonus;			//奖金
	 private String orderSstatus;	//状态
	 
	 private String issueNumber; 	//开奖号码
	 private String bettingNumber;	//投注号码
	 private String lasting;		//投注时间
	 */
	
    private Leaf lotteryname = new Leaf("lotteryname","");	//名称
	private Leaf methodname = new Leaf("methodname");	//名称
	private Leaf writetime = new Leaf("writetime");	//名称
	private Leaf status = new Leaf("status");	//名称
	private Leaf totalprice = new Leaf("totalprice");	//名称
	private Leaf issue = new Leaf("issue");	//名称
	private Leaf projectid = new Leaf("projectid");	//名称
	private Leaf modes = new Leaf("modes");	//名称
	
	
	private OrderCatalog orderCatalog=new OrderCatalog();
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "order");
			lotteryname.serializerLeaf(serializer);
			methodname.serializerLeaf(serializer);
			writetime.serializerLeaf(serializer);
			status.serializerLeaf(serializer);
			totalprice.serializerLeaf(serializer);
			issue.serializerLeaf(serializer);
			projectid.serializerLeaf(serializer);
			modes.serializerLeaf(serializer);
			serializer.endTag(null, "order");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Leaf getLotteryname() {
		return lotteryname;
	}

	public Leaf getMethodname() {
		return methodname;
	}

	public Leaf getWritetime() {
		return writetime;
	}

	public Leaf getStatus() {
		return status;
	}

	public Leaf getTotalprice() {
		return totalprice;
	}

	public Leaf getIssue() {
		return issue;
	}

	public Leaf getProjectid() {
		return projectid;
	}

	public Leaf getModes() {
		return modes;
	}

	public OrderCatalog getOrderCatalog() {
		return orderCatalog;
	}

	public void setOrderCatalog(OrderCatalog orderCatalog) {
		this.orderCatalog = orderCatalog;
	}
	
}
