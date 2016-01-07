package com.goldenasia.lottery.bean;

import android.annotation.SuppressLint;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 * 
 * @author Administrator
 * 
 */
@SuppressLint("FieldGetter")
public class ShoppingCart {
	private static ShoppingCart instance = new ShoppingCart();

	private ShoppingCart() {
	}

	public static ShoppingCart getInstance() {
		return instance;
	}

	// 投注
	// lotteryid string * 玩法编号
	// issue string * 期号（当前销售期）
	// lotterycode string * 投注号码，注与注之间^分割
	// lotterynumber string 注数
	// lotteryvalue string 方案金额，以分为单位

	// appnumbers string 倍数
	// issuesnumbers string 追期
	// issueflag int * 是否多期追号 0否，1多期
	// bonusstop int * 中奖后是否停止：0不停，1停
	// retainRebate boolean 保留返点 
	private Integer lotteryid;
	private Integer lotterytype;
	private String lotteryname;
	private String issue;
	private PlayMenu playmenu;
	private LucreMode lucreMode;
	private Map<Integer, List<Ticket>> ticketsMap = new HashMap<Integer, List<Ticket>>();
	private Integer lotterynumber;// 计算
	private Double lotteryvalue;
	private List<Map<String, AppendInfo>> appendList;
	private HashMap<String, Boolean> isSelected;

	private Integer appnumbers = 1;
	private Integer issuesnumbers = 1;
	private boolean appstate = false;
	private boolean retainRebate = true;
	private boolean flagfocus=true;

	public boolean isFlagfocus() {
		return flagfocus;
	}

	public void setFlagfocus(boolean flagfocus) {
		this.flagfocus = flagfocus;
	}

	public Integer getAppnumbers() {
		return appnumbers;
	}
	
	public void setAppnumbers(Integer appnumbers){
		this.appnumbers=appnumbers;
	}

	public Integer getIssuesnumbers() {
		return issuesnumbers;
	}
	
	public void setIssuesnumbers(Integer issuesnumbers){
		this.issuesnumbers=issuesnumbers;
	}

	public Integer getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Integer lotteryid) {
		this.lotteryid = lotteryid;
	}

	public Integer getLotterytype() {
		return lotterytype;
	}

	public void setLotterytype(Integer lotterytype) {
		this.lotterytype = lotterytype;
	}
	
	public String getLotteryname() {
		return lotteryname;
	}

	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Map<Integer, List<Ticket>> getTicketsMap() {
		return ticketsMap;
	}

	public PlayMenu getPlaymenu() {
		return playmenu;
	}

	public void setPlaymenu(PlayMenu playmenu) {
		this.playmenu = playmenu;
	}

	public boolean isAppstate() {
		return appstate;
	}
	
	public boolean isRetainRebate() {
		return retainRebate;
	}

	public void setRetainRebate(boolean retainRebate) {
		this.retainRebate = retainRebate;
	}

	public LucreMode getLucreMode() {
		return lucreMode;
	}

	public void setLucreMode(LucreMode lucreMode) {
		this.lucreMode = lucreMode;
	}

	public void setAppstate(boolean appstate) {
		this.appstate = appstate;
	}
	
	public List<Map<String, AppendInfo>> getAppendList() {
		return appendList;
	}

	public void setAppendList(List<Map<String, AppendInfo>> appendList) {
		this.appendList = appendList;
	}

	public HashMap<String, Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(HashMap<String, Boolean> isSelected) {
		this.isSelected = isSelected;
	}

	public Integer getLotterynumber() {
		lotterynumber = 0;
		List<Ticket> tickets=ticketsMap.get(getLotteryid());
		if(tickets!=null){
			for (Ticket item : tickets) {
				lotterynumber += item.getNum();
			}
		}
		return lotterynumber;
	}

	public Double getLotteryvalue() {
		double lottermode = 0.00;
		List<Ticket> tickets=ticketsMap.get(getLotteryid());
		if(tickets!=null){
			for (Ticket item : tickets) {
				lottermode += item.getMoneyMode().getLucreReckon() * 2 * item.getNum();
			}
		}
		BigDecimal bd = new BigDecimal(lottermode * appnumbers * issuesnumbers);
		lotteryvalue = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return lotteryvalue;
	}
	
	/**
	 * 判断追号 至少一注
	 */
	public int chaseNumberNote(){
		int notenum=0;
		if (getAppendList() != null) {
			for (int i = 0; i < getAppendList().size(); i++) {
				AppendInfo  appendinfo=appendList.get(i).get("list_item_inputvalue");
				boolean selected=isSelected.get(appendinfo.getIssue());
				if(selected){
					notenum+=notenum+1;
				}
			}
		}
		return notenum;
	}

	/**
	 * 操作倍数
	 */
	public boolean addAppnumbers(boolean isAdd) {
		if (isAdd) {
			appnumbers++;
			if (appnumbers > 99) {
				appnumbers--;
				return false;
			}

			/*
			 * if (getLotteryvalue() > GlobalParams.MONEY) { appnumbers--;
			 * return false; }
			 */
		} else {
			appnumbers--;
			if (appnumbers == 0) {
				appnumbers++;
				return false;
			}
		}
		return true;
	}

	/**
	 * 操作追期
	 */
	public boolean addIssuesnumbers(boolean isAdd) {
		if (isAdd) {
			issuesnumbers++;
			if (issuesnumbers > 99) {
				issuesnumbers--;
				return false;
			}
			// 查看个人帐号的投注金额
			/*
			 * if (getLotteryvalue() > GlobalParams.MONEY) { issuesnumbers--;
			 * return false; }
			 */
		} else {
			issuesnumbers--;
			if (issuesnumbers == 0) {
				issuesnumbers++;
				return false;
			}
		}
		return true;
	}

	public void clear() {
		ticketsMap.clear();
		lotterynumber = 0;
		lotteryvalue = 0.00;

		appnumbers = 1;
		issuesnumbers = 1;

	}
	
	public void clearAppend(){
		List<Map<String, AppendInfo>> appendList=null;
		HashMap<Integer, Boolean> isSelected=null;;
	}

}
