package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.HistoryLottery;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

public class HistoryLotteryElement extends Element{
	//<lotteryissue></lotteryissue>
	private Leaf lotteryissue = new Leaf("lotteryissue","150401023");
	//<lotterydigit></lotterydigit>
	private Leaf lotterydigit = new Leaf("lotterydigit");

	private HistoryLottery historyLottery=new HistoryLottery();
	
	public HistoryLottery getHistoryLottery() {
		return historyLottery;
	}

	public void setHistoryLottery(HistoryLottery historyLottery) {
		this.historyLottery = historyLottery;
	}
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "historylottery");
			lotteryissue.serializerLeaf(serializer);
			lotterydigit.serializerLeaf(serializer);
			serializer.endTag(null, "historylottery");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return "150401023";
	}

	public Leaf getLotteryissue() {
		return lotteryissue;
	}

	public Leaf getLotterydigit() {
		return lotterydigit;
	}

}
