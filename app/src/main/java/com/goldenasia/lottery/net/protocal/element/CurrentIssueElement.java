package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.LotteryInfo;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

/**
 * 获取当前销售期的请求
 * 
 * @author Administrator
 * 
 */
public class CurrentIssueElement extends Element {
	// <lotteryid>118</lotteryid>
	private Leaf lotteryids = new Leaf("lotteryid", "1");		//彩种ID
	// <lotteryname>新蜂时时彩</lotteryname>
	private Leaf lotterynames =new Leaf("lotteryname");			//名称
	
	/******************服务器回复**********************/

	
	private LotteryInfo currentissue=new LotteryInfo();


	public LotteryInfo getCurrentissue() {
		return currentissue;
	}

	public void setCurrentissue(LotteryInfo currentissue) {
		this.currentissue = currentissue;
	}

	/*********************************************/
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "lottery");
			lotteryids.serializerLeaf(serializer);
			lotterynames.serializerLeaf(serializer);
			serializer.endTag(null, "lottery");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		return "12002";
	}

	public Leaf getLotteryids() {
		return lotteryids;
	}
	
	public Leaf getLotterynames() {
		return lotterynames;
	}

}
