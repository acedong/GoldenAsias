package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.BankInfo;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

public class BankInfoElement extends Element{
	
	private Leaf bankid=new Leaf("bankid");
	private Leaf bankname=new Leaf("bankname");
	
	private BankInfo bankInfo=new BankInfo();

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "bank");
			bankid.serializerLeaf(serializer);
			bankname.serializerLeaf(serializer);
			serializer.endTag(null, "bank");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Leaf getBankid() {
		return bankid;
	}

	public Leaf getBankname() {
		return bankname;
	}

	public BankInfo getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(BankInfo bankInfo) {
		this.bankInfo = bankInfo;
	}

}
