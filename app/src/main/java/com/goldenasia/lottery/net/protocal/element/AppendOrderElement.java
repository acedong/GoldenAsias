package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

public class AppendOrderElement extends Element{
	
	private Leaf title=new Leaf("title");
	private Leaf taskid=new Leaf("taskid");
	private Leaf taskprice=new Leaf("taskprice");
	private Leaf finishprice=new Leaf("finishprice");
	private Leaf begintime=new Leaf("begintime");
	private Leaf modes=new Leaf("modes");
	private Leaf lotteryname=new Leaf("lotteryname");
	private Leaf status=new Leaf("status");

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "result");
			title.serializerLeaf(serializer);
			taskid.serializerLeaf(serializer);
			taskprice.serializerLeaf(serializer);
			finishprice.serializerLeaf(serializer);
			begintime.serializerLeaf(serializer);
			modes.serializerLeaf(serializer);
			lotteryname.serializerLeaf(serializer);
			status.serializerLeaf(serializer);
			serializer.endTag(null, "result");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Leaf getTitle() {
		return title;
	}

	public Leaf getTaskid() {
		return taskid;
	}

	public Leaf getTaskprice() {
		return taskprice;
	}

	public Leaf getFinishprice() {
		return finishprice;
	}

	public Leaf getBegintime() {
		return begintime;
	}

	public Leaf getModes() {
		return modes;
	}

	public Leaf getLotteryname() {
		return lotteryname;
	}

	public Leaf getStatus() {
		return status;
	}

}
