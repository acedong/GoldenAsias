package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

public class LotteryIssueElement extends Element{
	
	private Leaf lotteryissue=new Leaf("lotteryissue");
	
	private Leaf lotterycode=new Leaf("lotterycode");
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "lastissues");
			lotteryissue.serializerLeaf(serializer);
			lotterycode.serializerLeaf(serializer);
			serializer.endTag(null, "lastissues");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Leaf getLotteryissue() {
		return lotteryissue;
	}

	public Leaf getLotterycode() {
		return lotterycode;
	}

}
