package com.goldenasia.lottery.net.protocal.element;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.AppendTaskInfo;
import com.goldenasia.lottery.bean.PageInfo;
import com.goldenasia.lottery.net.protocal.Element;

public class AppendOrderElements extends Element{

	private AppendOrderElement appendorderEle=new AppendOrderElement();
	private PageInfo pageinfo=new PageInfo();
	private List<AppendTaskInfo> appendList=new ArrayList<AppendTaskInfo>();
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "results");
			appendorderEle.serializerElement(serializer);
			serializer.endTag(null, "results");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public AppendOrderElement getAppendorderEle() {
		return appendorderEle;
	}

	public void setAppendorderEle(AppendOrderElement appendorderEle) {
		this.appendorderEle = appendorderEle;
	}

	public PageInfo getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageInfo pageinfo) {
		this.pageinfo = pageinfo;
	}

	public List<AppendTaskInfo> getAppendList() {
		return appendList;
	}

	public void setAppendList(List<AppendTaskInfo> appendList) {
		this.appendList = appendList;
	}

}
