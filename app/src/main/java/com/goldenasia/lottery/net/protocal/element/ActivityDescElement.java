package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.ActivityDesc;
import com.goldenasia.lottery.net.protocal.Element;

public class ActivityDescElement extends Element{
	
	private ActivityDesc activityDesc=new ActivityDesc();

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public ActivityDesc getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(ActivityDesc activityDesc) {
		this.activityDesc = activityDesc;
	}

	
}
