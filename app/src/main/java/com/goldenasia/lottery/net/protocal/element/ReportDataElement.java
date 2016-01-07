package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.ReportData;
import com.goldenasia.lottery.net.protocal.Element;

public class ReportDataElement extends Element{

	private ReportData report=new ReportData();
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public ReportData getReport() {
		return report;
	}

	public void setReport(ReportData report) {
		this.report = report;
	}

}
