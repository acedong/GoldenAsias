package com.goldenasia.lottery.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * //参数playdata为以下结构序列化为json结果
	//无追号情况
	let param = [
	        "lt_trace_if":0,
	        "cellphone":1,
	        "lotteryid":1,
	        "lt_issue_start":"150122082",
	        "lt_project":[["methodid":11,"codes":"2|2|2","nums":1,"times":1,"money":2,"mode":1,"point":7.4],
	                      ["methodid":11,"codes":"1&2|1|1","nums":2,"times":1,"money":0.4,"mode":2,"point":0]]
	    ]
	//追号情况
    let param = [
        "cellphone":1,
        "lotteryid":1,
        "lt_issue_start":"150122090",
        "lt_project":[["methodid":11,"codes":"3|3|3","nums":1,"times":1,"money":2,"mode":1,"point":7.4],
            ["methodid":11,"codes":"1&2|1|1","nums":2,"times":1,"money":0.4,"mode":2,"point":0]],
        "lt_trace_if":1,
        "lt_trace_stop":0,
        "lt_trace_issues":["150122090","150122091"],
        "lt_trace_times_150122090":1,
        "lt_trace_times_150122091":2
    ]
 * @author Ace
 *
 */

public class BettingInfo {
	
	private boolean tracestatus;			//0|1
	private int cellphone;		
	private int lotteryid;					//彩种ID
	private String issuestartNo;			//投注期号
	private List<ProgramBuyInfo> ltproject;	//投注内容 ["methodid":11,"codes":"3&2|3|3","nums":1,"times":1,"money":2,"mode":1,"point":7.4]
	private boolean tracestop;				//停止追号状态
	private String[] traceissues;			//所有追号期号
	private HashMap<String, Boolean> isSelected;
	private Map<String,Integer> tracetimesMap=new HashMap<String,Integer>(); 	//追号倍数
	
	public BettingInfo(){}

	public BettingInfo(boolean tracestatus, int cellphone, int lotteryid,
			String issuestartNo, List<ProgramBuyInfo> ltproject,
			boolean tracestop, String[] traceissues,
			HashMap<String, Boolean> isSelected,
			Map<String, Integer> tracetimesMap) {
		this.tracestatus = tracestatus;
		this.cellphone = cellphone;
		this.lotteryid = lotteryid;
		this.issuestartNo = issuestartNo;
		this.ltproject = ltproject;
		this.tracestop = tracestop;
		this.traceissues = traceissues;
		this.isSelected = isSelected;
		this.tracetimesMap = tracetimesMap;
	}

	public boolean isTracestatus() {
		return tracestatus;
	}

	public void setTracestatus(boolean tracestatus) {
		this.tracestatus = tracestatus;
	}

	public int getCellphone() {
		return cellphone;
	}

	public void setCellphone(int cellphone) {
		this.cellphone = cellphone;
	}

	public int getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(int lotteryid) {
		this.lotteryid = lotteryid;
	}

	public String getIssuestartNo() {
		return issuestartNo;
	}

	public void setIssuestartNo(String issuestartNo) {
		this.issuestartNo = issuestartNo;
	}

	public List<ProgramBuyInfo> getLtproject() {
		return ltproject;
	}

	public void setLtproject(List<ProgramBuyInfo> ltproject) {
		this.ltproject = ltproject;
	}

	public boolean isTracestop() {
		return tracestop;
	}

	public void setTracestop(boolean tracestop) {
		this.tracestop = tracestop;
	}

	public String[] getTraceissues() {
		return traceissues;
	}

	public void setTraceissues(String[] traceissues) {
		this.traceissues = traceissues;
	}

	public Map<String, Integer> getTracetimesMap() {
		return tracetimesMap;
	}

	public void setTracetimesMap(Map<String, Integer> tracetimesMap) {
		this.tracetimesMap = tracetimesMap;
	}

	public HashMap<String, Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(HashMap<String, Boolean> isSelected) {
		this.isSelected = isSelected;
	}

}
