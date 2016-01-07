package com.goldenasia.lottery.bean;

public class ReportData {
	private String realpayment; 	//":0.0001,
	private String realwithdraw; 	//":0.0001,
	private String bets; 			//":0.0001,
	private String points; 			//":0.0001,
	private String bonus; 			//":0.0001,
	private String profit; 			//":0.0002,
	private String teambets; 		//":0.0001,
	private String teampoints; 		//":0.0001,
	private String teambonus; 		//":0.0001,
	private String teamrealpayment; //":0.0001,
	private String teamrealwithdraw;//":0.0001,
	private String teamprofit; 		//":0.0002
	
	public ReportData() {
	}
	public ReportData(String realpayment, String realwithdraw, String bets,
			String points, String bonus, String profit, String teambets,
			String teampoints, String teambonus, String teamrealpayment,
			String teamrealwithdraw, String teamprofit) {
		this.realpayment = realpayment;
		this.realwithdraw = realwithdraw;
		this.bets = bets;
		this.points = points;
		this.bonus = bonus;
		this.profit = profit;
		this.teambets = teambets;
		this.teampoints = teampoints;
		this.teambonus = teambonus;
		this.teamrealpayment = teamrealpayment;
		this.teamrealwithdraw = teamrealwithdraw;
		this.teamprofit = teamprofit;
	}
	public String getRealpayment() {
		return realpayment;
	}
	public void setRealpayment(String realpayment) {
		this.realpayment = realpayment;
	}
	public String getRealwithdraw() {
		return realwithdraw;
	}
	public void setRealwithdraw(String realwithdraw) {
		this.realwithdraw = realwithdraw;
	}
	public String getBets() {
		return bets;
	}
	public void setBets(String bets) {
		this.bets = bets;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getBonus() {
		return bonus;
	}
	public void setBonus(String bonus) {
		this.bonus = bonus;
	}
	public String getProfit() {
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public String getTeambets() {
		return teambets;
	}
	public void setTeambets(String teambets) {
		this.teambets = teambets;
	}
	public String getTeampoints() {
		return teampoints;
	}
	public void setTeampoints(String teampoints) {
		this.teampoints = teampoints;
	}
	public String getTeambonus() {
		return teambonus;
	}
	public void setTeambonus(String teambonus) {
		this.teambonus = teambonus;
	}
	public String getTeamrealpayment() {
		return teamrealpayment;
	}
	public void setTeamrealpayment(String teamrealpayment) {
		this.teamrealpayment = teamrealpayment;
	}
	public String getTeamrealwithdraw() {
		return teamrealwithdraw;
	}
	public void setTeamrealwithdraw(String teamrealwithdraw) {
		this.teamrealwithdraw = teamrealwithdraw;
	}
	public String getTeamprofit() {
		return teamprofit;
	}
	public void setTeamprofit(String teamprofit) {
		this.teamprofit = teamprofit;
	}
	
}
