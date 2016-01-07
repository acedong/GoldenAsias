package com.goldenasia.lottery.net.protocal.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.CardLimit;
import com.goldenasia.lottery.bean.MyCardInfo;
import com.goldenasia.lottery.bean.WithdrawalsUserInfo;
import com.goldenasia.lottery.net.protocal.Element;

public class MyCardInfoElements extends Element{
	
	private MyCardInfoElement mycardEle=new MyCardInfoElement();
	private WithdrawalsUserInfo userInfoWithdrawals=new WithdrawalsUserInfo();
	private List<MyCardInfo> mycardList=new ArrayList<MyCardInfo>();
	private Map<String, List<CardLimit>> cardLimitMap=new HashMap<String, List<CardLimit>>();
	

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "mycard");
			mycardEle.serializerElement(serializer);
			serializer.endTag(null, "mycard");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public WithdrawalsUserInfo getUserInfoWithdrawals() {
		return userInfoWithdrawals;
	}

	public void setUserInfoWithdrawals(WithdrawalsUserInfo userInfoWithdrawals) {
		this.userInfoWithdrawals = userInfoWithdrawals;
	}

	public List<MyCardInfo> getMycardList() {
		return mycardList;
	}

	public void setMycardList(List<MyCardInfo> mycardList) {
		this.mycardList = mycardList;
	}

	public Map<String, List<CardLimit>> getCardLimitMap() {
		return cardLimitMap;
	}

	public void setCardLimitMap(Map<String, List<CardLimit>> cardLimitMap) {
		this.cardLimitMap = cardLimitMap;
	}

}
