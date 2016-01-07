package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.AnalysisBank;
import com.goldenasia.lottery.net.protocal.Element;

public class AnalysisBankElement extends Element{
	
	private AnalysisBank analysisbank=new AnalysisBank();

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public AnalysisBank getAnalysisbank() {
		return analysisbank;
	}

	public void setAnalysisbank(AnalysisBank analysisbank) {
		this.analysisbank = analysisbank;
	}

}
