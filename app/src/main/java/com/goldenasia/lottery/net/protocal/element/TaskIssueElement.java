package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.TaskIssue;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

public class TaskIssueElement extends Element{
	
	private Leaf serialnumber=new Leaf("serialnumber");	
	private Leaf taskissueno=new Leaf("taskissueno");
	private Leaf issuedate=new Leaf("issuedate");

	private TaskIssue taskIssueinfo=new TaskIssue();
	
	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "taskissue");
			serialnumber.serializerLeaf(serializer);
			taskissueno.serializerLeaf(serializer);
			issuedate.serializerLeaf(serializer);
			serializer.endTag(null, "taskissue");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return "001";
	}

	public Leaf getSerialnumber() {
		return serialnumber;
	}

	public Leaf getTaskissueno() {
		return taskissueno;
	}

	public Leaf getIssuedate() {
		return issuedate;
	}

	public TaskIssue getTaskIssueinfo() {
		return taskIssueinfo;
	}

	public void setTaskIssueinfo(TaskIssue taskIssueinfo) {
		this.taskIssueinfo = taskIssueinfo;
	}

}
