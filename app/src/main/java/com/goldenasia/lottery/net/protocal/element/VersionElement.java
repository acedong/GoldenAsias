package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

public class VersionElement extends Element {

	private Leaf version=new Leaf("versionno");	
	/*************** 回复信息 **********************/
	// investvalues 可投注金额
	private String versionno;

	public String getVersionno() {
		return versionno;
	}

	public void setVersionno(String versionno) {
		this.versionno = versionno;
	}

	/*************************************/

	@Override
	public void serializerElement(XmlSerializer serializer) {
		
		try {
			serializer.startTag(null, "element");
			version.serializerLeaf(serializer);
			serializer.endTag(null, "element");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		return "1.0.0";
	}

}