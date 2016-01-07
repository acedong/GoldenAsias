package com.goldenasia.lottery.net.protocal.element;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.AppendTaskDetail;
import com.goldenasia.lottery.bean.AppendTaskDetailItems;
import com.goldenasia.lottery.net.protocal.Element;

public class AppendTaskDetailElement extends Element{
	
	private AppendTaskDetail taskDetail=new AppendTaskDetail();
	
	private List<AppendTaskDetailItems> taskDetailList=new ArrayList<AppendTaskDetailItems>();

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public AppendTaskDetail getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(AppendTaskDetail taskDetail) {
		this.taskDetail = taskDetail;
	}

	public List<AppendTaskDetailItems> getTaskDetailList() {
		return taskDetailList;
	}

	public void setTaskDetailList(List<AppendTaskDetailItems> taskDetailList) {
		this.taskDetailList = taskDetailList;
	}

}
