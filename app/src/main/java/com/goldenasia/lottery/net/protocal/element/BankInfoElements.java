package com.goldenasia.lottery.net.protocal.element;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.BankSupportInfo;
import com.goldenasia.lottery.bean.BindingData;
import com.goldenasia.lottery.net.protocal.Element;

public class BankInfoElements extends Element{
	
	private BankInfoElement bankinfo=new BankInfoElement();
	
	private BindingData bindingData=new BindingData();
	
	private List<BankSupportInfo> userBankInfoList=new ArrayList<BankSupportInfo>();
	
	private List<BankInfoElement> bankinfoEleList=new ArrayList<BankInfoElement>();

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "banks");
			bankinfo.serializerElement(serializer);
			serializer.endTag(null, "banks");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public BankInfoElement getBankinfo() {
		return bankinfo;
	}

	public void setBankinfo(BankInfoElement bankinfo) {
		this.bankinfo = bankinfo;
	}

	public List<BankInfoElement> getBankinfoEleList() {
		return bankinfoEleList;
	}

	public void setBankinfoEleList(List<BankInfoElement> bankinfoEleList) {
		this.bankinfoEleList = bankinfoEleList;
	}

	public BindingData getBindingData() {
		return bindingData;
	}

	public void setBindingData(BindingData bindingData) {
		this.bindingData = bindingData;
	}

	public List<BankSupportInfo> getUserBankInfoList() {
		return userBankInfoList;
	}

	public void setUserBankInfoList(List<BankSupportInfo> userBankInfoList) {
		this.userBankInfoList = userBankInfoList;
	}

}
