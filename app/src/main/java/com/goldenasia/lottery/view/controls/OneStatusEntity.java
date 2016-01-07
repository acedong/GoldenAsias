package com.goldenasia.lottery.view.controls;

import java.util.List;

/**
 * һ��״̬ʵ����
 * @author gaoxy
 * @2013-11-7
 */
public class OneStatusEntity {
	/* ״̬���� */
	private String statusName;
	/* Ԥ�����ʱ�� */
	private String completeTime;
	/* ����״̬list */
	private List<TwoStatusEntity> twoList;
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public List<TwoStatusEntity> getTwoList() {
		return twoList;
	}
	public void setTwoList(List<TwoStatusEntity> twoList) {
		this.twoList = twoList;
	}
	
	
}
