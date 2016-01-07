package com.goldenasia.lottery.engine.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.bean.HistoryLottery;
import com.goldenasia.lottery.bean.IssueLast;
import com.goldenasia.lottery.bean.LotteryInfo;
import com.goldenasia.lottery.engine.BaseLotteryEngine;
import com.goldenasia.lottery.engine.LotteryInfoEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.element.HistoryDetailsElements;
import com.goldenasia.lottery.net.protocal.element.HistoryListElements;
import com.goldenasia.lottery.net.protocal.element.HistoryLotteryElement;
import com.goldenasia.lottery.net.protocal.element.HistoryLotteryElements;
import com.goldenasia.lottery.util.DES;

public class LotteryInfoEngineImpl extends BaseLotteryEngine implements LotteryInfoEngine{
	
	@Override
	public Message getRecentLotteryInfo() {
		// TODO Auto-generated method stub
		HistoryListElements historyElement=new HistoryListElements();
		Message message = new Message();
		String xml = message.getXml(historyElement);
		
		Message result = super.getRecentLotteryMethod(xml);
		if (result != null) {
			XmlPullParser parser = Xml.newPullParser();
			try {
				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";
				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;
				
				HistoryListElements resultElement=null;			
				LotteryInfo lotteryInfo=null;
				List<LotteryInfo> lotteryList=new ArrayList<LotteryInfo>();
				IssueLast issueLast=null;
				List<IssueLast> issueLastList=null;
				Map<String, List<IssueLast>> historyMap=new HashMap<String, List<IssueLast>>();
				
				String lotteryidstr="";
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("errorcode".equals(name)) {
								result.getBody().getOelement().setErrorcode(parser.nextText());
							}
							if ("errormsg".equals(name)) {
								result.getBody().getOelement().setErrormsg(parser.nextText());
							}
							
							if ("element".equals(name)) {
								resultElement = new HistoryListElements();
								result.getBody().getElements().add(resultElement);
							}
							
							if("lottery".equals(name)){
								if(resultElement!=null){
									lotteryInfo=new LotteryInfo();
								}
							}
							
							if("lotteryid".equals(name)){
								if(lotteryInfo!=null)
								{
									lotteryInfo.setLotteryId(parser.nextText());
								}
							}
							
							if("cnname".equals(name)){
								if(lotteryInfo!=null)
								{
									lotteryInfo.setLotteryName(parser.nextText());
								}
							}
							
							if("isown".equals(name)){
								if(lotteryInfo!=null)
								{
									lotteryInfo.setIsown(parser.nextText());
								}
							}
							
							if("sorts".equals(name)){
								if(lotteryInfo!=null)
								{
									lotteryInfo.setSorts(parser.nextText());
								}
							}
							
							if("lotterytype".equals(name)){
								if(lotteryInfo!=null)
								{
									lotteryInfo.setLotterytype(parser.nextText());
								}
							}
							
							
							if("issues".equals(name)){
								if (resultElement != null) {
									issueLastList=new ArrayList<IssueLast>();
								}
							}
							
							if("code".equals(name)){
								if(issueLastList!=null)
									issueLast=new IssueLast();
							}
							
							if("codeid".equals(name)){
								if(issueLastList!=null){
									lotteryidstr=parser.nextText();
								}
							}
							
							if("issuename".equals(name)){
								if(issueLast!=null){
									issueLast.setIssue(parser.nextText());
								}
							}
							
							if("issuecode".equals(name)){
								if(issueLast!=null){
									issueLast.setCode(parser.nextText());
								}
							}
							
							break;
						case XmlPullParser.END_TAG:
							String nameend=parser.getName();
							
							if(nameend.equalsIgnoreCase("lottery") && lotteryInfo != null){
								//添加一个玩法信息
								lotteryList.add(lotteryInfo);
							}
							
							if(nameend.equalsIgnoreCase("code") && issueLast != null){
								//添加一个玩法信息
								issueLastList.add(issueLast);
							}
							
							if(nameend.equalsIgnoreCase("issues") && issueLastList != null){
								historyMap.put(lotteryidstr, issueLastList);
							}
							
							if(nameend.equalsIgnoreCase("element") && resultElement != null){
								resultElement.setLotteryList(lotteryList);
								resultElement.setHistoryMap(historyMap);
							}
							break;
					}
					eventType = parser.next();
				}
				return result;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Message getSingleLotteryInfo(Integer integer) {
		// TODO Auto-generated method stub
		Message result = super.getRecentSingleLotteryInfo(integer);
		if (result != null) {
			XmlPullParser parser = Xml.newPullParser();
			try {
				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";
				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;
				
				HistoryDetailsElements resultElement = null;		
				
				IssueLast issueLast=null;
				List<IssueLast> issueLastList=null;
				Map<String, List<IssueLast>> historyMap=new HashMap<String, List<IssueLast>>();
				String lotteryidstr="";
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("errorcode".equals(name)) {
								result.getBody().getOelement().setErrorcode(parser.nextText());
							}
							if ("errormsg".equals(name)) {
								result.getBody().getOelement().setErrormsg(parser.nextText());
							}
							
							if ("element".equals(name)) {
								resultElement = new HistoryDetailsElements();
								result.getBody().getElements().add(resultElement);
							}
							
							if("issues".equals(name)){
								if (resultElement != null) {
									issueLastList=new ArrayList<IssueLast>();
								}
							}
							
							if("code".equals(name)){
								if(issueLastList!=null)
									issueLast=new IssueLast();
							}
							
							if("codeid".equals(name)){
								if(issueLastList!=null){
									lotteryidstr=parser.nextText();
								}
							}
							
							if("issuename".equals(name)){
								if(issueLast!=null){
									issueLast.setIssue(parser.nextText());
								}
							}
							
							if("issuecode".equals(name)){
								if(issueLast!=null){
									issueLast.setCode(parser.nextText());
								}
							}
							
							if("sortscode".equals(name)){
								if(issueLast!=null){
									issueLast.setSorts(parser.nextText());
								}
							}
							break;
						case XmlPullParser.END_TAG:
							String nameend=parser.getName();
							
							if(nameend.equalsIgnoreCase("code") && issueLast != null){
								issueLastList.add(issueLast);
							}
							
							if(nameend.equalsIgnoreCase("issues") && issueLastList != null){
								historyMap.put(lotteryidstr, issueLastList);
							}
							
							if(nameend.equalsIgnoreCase("element") && resultElement != null){
								resultElement.setHistoryMap(historyMap);
							}
							break;
					}
					eventType = parser.next();
				}
				return result;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
