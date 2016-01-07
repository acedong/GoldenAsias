package com.goldenasia.lottery.bean;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.goldenasia.lottery.GlobalParams;

/**
 * 生成发布期数
 * @author Ace
 *
 */

public class SerialNumber {

    private static final String FORMAT_BRIEF = "yyMMdd";
    private static final String FORMAT_INTACT_YEAR = "yyyy";
	private static final String FORMAT_INTACT = "yyyyMMdd";
	private static Format DF = null;
	public static String[] newInstance(IssueAll issueall) {
		if(issueall.getIssueTask()!=null){
			
			IssueTask issueTask=issueall.getIssueTask();
			IssueSales issueSales=issueall.getIssueSales();
			
			int day=0; 		//天
			int flag=0;		//数据下标 
			
			//判断是否是今年的最后一天
			String rule=issueTask.getIssuerule();				//年月日规则
			int issuecount=Integer.parseInt(issueTask.getIssuecount());					//当天最后一期自增序号
			int maxtaskcount=Integer.parseInt(issueTask.getMaxtaskcount());				//最大追号期数
			String[] iss = new String[maxtaskcount];
			
			//判断年格式
			if(Character.isUpperCase(rule.charAt(0)))
			{
				
				if(String.valueOf(rule.charAt(1)).equals("n")){
					//一天一期
					DF = newFormatIntact_Year();
				}else if(String.valueOf(rule.charAt(1)).equals("w")){
					//一周一期
					
				}else{
					DF = newFormatIntact();
				}
			}else if(Character.isLowerCase(rule.charAt(0))){
				DF = newFormatBrief();
			}
			String currentIssue="";
			if(String.valueOf(rule.charAt(1)).equals("n")){
				//一天一期
				currentIssue=issueSales.getIssue().substring(4,issueSales.getIssue().length());
			}else if(String.valueOf(rule.charAt(1)).equals("w")){
				//一周一期
				
			}else{
				currentIssue=issueSales.getIssue().substring(6,issueSales.getIssue().length());
			}
			
			int currentIssueSerial=Integer.parseInt(currentIssue);//当前期
			
			int ruleno=Integer.parseInt(rule.substring(rule.length()-1,rule.length()));//取最后一位
			
			String datetime ="",issuearray="",plusIssue="";
			int plus=1,plusIssueSerial=0;
			while(true) {
				
				if(maxtaskcount<=flag){
					break;
				}
				//判断是否大约期 
				Date date=incrementDay(day);
				
				if(String.valueOf(rule.charAt(1)).equals("n")){
					//一天一期
					datetime=format(date);
				}else if(String.valueOf(rule.charAt(1)).equals("w")){
					//一周一期
					datetime=format(date);
				}else{
					datetime=format(date);
				}
				
				if(plusIssueSerial<issuecount&&day==0){   //任务期数 < 最大期数 日期
					plusIssue=String.format("%0"+ruleno+"d", currentIssueSerial);
					plusIssueSerial=Integer.parseInt(plusIssue);
					issuearray= datetime + plusIssue;
					currentIssueSerial=currentIssueSerial+1;
				}else{
					plusIssue=String.format("%0"+ruleno+"d", plus);
					plusIssueSerial=Integer.parseInt(plusIssue);
					issuearray= datetime + plusIssue;
					plus+=1;
				}
				
				if(plusIssueSerial==issuecount){ //最后一期 加一天
					day++;
				}
				
				iss[flag]=issuearray;
				flag+=1;
			}
			
			return iss;
		}
		return null;
	}
	
	private static Format newFormatIntact(){
		return new SimpleDateFormat(FORMAT_INTACT);
	}
	
	private static Format newFormatIntact_Year(){
		return new SimpleDateFormat(FORMAT_INTACT_YEAR);
	}
	
	private static Format newFormatBrief(){
		return new SimpleDateFormat(FORMAT_BRIEF);
	}

	private static String format(Date date) {
		return DF.format(date);
	}
	
	private static Date incrementDay(int day){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());  
        calendar.add(Calendar.DAY_OF_MONTH, day);  
		return calendar.getTime();
	}
}
