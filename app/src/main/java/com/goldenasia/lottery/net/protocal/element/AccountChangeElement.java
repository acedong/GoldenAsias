package com.goldenasia.lottery.net.protocal.element;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.AccountChangeInfo;
import com.goldenasia.lottery.bean.AppendTaskInfo;
import com.goldenasia.lottery.bean.ICount;
import com.goldenasia.lottery.bean.PageInfo;
import com.goldenasia.lottery.net.protocal.Element;

public class AccountChangeElement extends Element{

	private PageInfo pageinfo=new PageInfo();
	private ICount icount=new ICount();
	private List<AccountChangeInfo> accountChangeList=new ArrayList<AccountChangeInfo>();
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public PageInfo getPageinfo() {
		return pageinfo;
	}

	public void setPageinfo(PageInfo pageinfo) {
		this.pageinfo = pageinfo;
	}

	public ICount getIcount() {
		return icount;
	}

	public void setIcount(ICount icount) {
		this.icount = icount;
	}

	public List<AccountChangeInfo> getAccountChangeList() {
		return accountChangeList;
	}

	public void setAccountChangeList(List<AccountChangeInfo> accountChangeList) {
		this.accountChangeList = accountChangeList;
	}

}
