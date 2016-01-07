package com.goldenasia.lottery.net.protocal.element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.IssueAll;
import com.goldenasia.lottery.bean.LotteryInfo;
import com.goldenasia.lottery.bean.PlayMenu;
import com.goldenasia.lottery.bean.RecordTime;
import com.goldenasia.lottery.net.protocal.Element;

public class SpeciesListElements extends Element implements Serializable{
	
	/***********************************************彩种信息*************************************************************/
	private GameplayElements playmenus=new GameplayElements();
	/***********************************************玩法信息*************************************************************/
	
	
	private List<LotteryInfo> catalogueList=new ArrayList<LotteryInfo>();
	private Map<String, List<PlayMenu>> menuMap=new HashMap<String, List<PlayMenu>>();
	private Map<String, IssueAll> issueAllMap=new HashMap<String, IssueAll>();
	private Map<String, RecordTime> saleendTime=new HashMap<String, RecordTime>();
	private Map<String, RecordTime> openTime=new HashMap<String, RecordTime>();
	
	public SpeciesListElements() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void serializerElement(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "element");
			playmenus.serializerElement(serializer);
			serializer.endTag(null, "element");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		return "12002";
	}

	public GameplayElements getPlaymenus() {
		return playmenus;
	}

	public void setPlaymenus(GameplayElements playmenus) {
		this.playmenus = playmenus;
	}

	public List<LotteryInfo> getCatalogueList() {
		return catalogueList;
	}

	public void setCatalogueList(List<LotteryInfo> catalogueList) {
		this.catalogueList = catalogueList;
	}

	public Map<String, List<PlayMenu>> getMenuMap() {
		return menuMap;
	}

	public void setMenuMap(Map<String, List<PlayMenu>> menuMap) {
		this.menuMap = menuMap;
	}

	public Map<String, IssueAll> getIssueAllMap() {
		return issueAllMap;
	}

	public void setIssueAllMap(Map<String, IssueAll> issueAllMap) {
		this.issueAllMap = issueAllMap;
	}

	public Map<String, RecordTime> getSaleendTime() {
		return saleendTime;
	}

	public void setSaleendTime(Map<String, RecordTime> saleendTime) {
		this.saleendTime = saleendTime;
	}

	public Map<String, RecordTime> getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Map<String, RecordTime> openTime) {
		this.openTime = openTime;
	}

}
