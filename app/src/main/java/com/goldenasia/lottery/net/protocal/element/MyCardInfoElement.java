package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

public class MyCardInfoElement extends Element{
	
	private Leaf bankname = new Leaf("bankname");// 中国招商银行
    private Leaf cardno =new Leaf("cardno");  	//": "2222",
    private Leaf aliasname = new Leaf("aliasname");		//": "这是",
    private Leaf entry = new Leaf("entry");			//: "KANFQU"

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "mycard");
			bankname.serializerLeaf(serializer);
			cardno.serializerLeaf(serializer);
			aliasname.serializerLeaf(serializer);
			entry.serializerLeaf(serializer);
			serializer.endTag(null, "mycard");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

}
