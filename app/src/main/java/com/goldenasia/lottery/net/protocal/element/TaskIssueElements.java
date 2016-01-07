package com.goldenasia.lottery.net.protocal.element;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.net.protocal.Element;

public class TaskIssueElements extends Element{
	
	TaskIssueElement taskIssue = new TaskIssueElement();
	
	List<TaskIssueElement> taskIssueList=new ArrayList<TaskIssueElement>();

	@Override
	public void serializerElement(XmlSerializer serializer) {
		// TODO Auto-generated method stub
		try {
			serializer.startTag(null, "taskissues");
			taskIssue.serializerElement(serializer);
			serializer.endTag(null, "taskissues");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskIssueElement> getTaskIssueList() {
		return taskIssueList;
	}

	public void setTaskIssueList(List<TaskIssueElement> taskIssueList) {
		this.taskIssueList = taskIssueList;
	}

}
