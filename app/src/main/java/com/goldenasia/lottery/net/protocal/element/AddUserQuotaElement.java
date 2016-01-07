package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.AddUserQuota;
import com.goldenasia.lottery.net.protocal.Element;

public class AddUserQuotaElement extends Element{
	
	private AddUserQuota addUserQuota=new AddUserQuota();

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public AddUserQuota getAddUserQuota() {
		return addUserQuota;
	}

	public void setAddUserQuota(AddUserQuota addUserQuota) {
		this.addUserQuota = addUserQuota;
	}

}
