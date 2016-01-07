package com.goldenasia.lottery.engine.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.bean.IssueAll;
import com.goldenasia.lottery.bean.IssueLast;
import com.goldenasia.lottery.bean.IssueSales;
import com.goldenasia.lottery.bean.IssueTask;
import com.goldenasia.lottery.bean.LotteryInfo;
import com.goldenasia.lottery.bean.PlayMenu;
import com.goldenasia.lottery.engine.BaseEngine;
import com.goldenasia.lottery.engine.CommonInfoEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.element.CurrentIssueElement;
import com.goldenasia.lottery.net.protocal.element.GameplayElement;
import com.goldenasia.lottery.net.protocal.element.GameplayElements;
import com.goldenasia.lottery.net.protocal.element.SpeciesListElements;
import com.goldenasia.lottery.net.protocal.element.TaskIssueInfoElement;
import com.goldenasia.lottery.util.DES;

/**
 * 获取彩票信信息实现
 * @author Ace
 *
 */

public class CommonInfoEngineImpl extends BaseEngine implements CommonInfoEngine {
	
	@Override
	public Message getSpeciesListInfo(Integer lotteryId)
	{
		SpeciesListElements speciesElement=new SpeciesListElements();
		Message message = new Message();
		String xml = message.getXml(speciesElement);
		
		Message result = super.getSpeciesListMethod(xml);
		if (result != null) {
			XmlPullParser parser = Xml.newPullParser();
			try {
				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";
				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				SpeciesListElements resultElement = null;		//种类信息
				LotteryInfo catalogue =null;
				PlayMenu playmenu=null;
				IssueAll issueAll=null;
				IssueLast issueLast=null;
				IssueSales issueSales=null;
				IssueTask issueTask=null;
				List<LotteryInfo> catalogueList=new ArrayList<LotteryInfo>();
				List<PlayMenu> playmenuList=null;
				Map<String, List<PlayMenu>> menuMap=new HashMap<String, List<PlayMenu>>();
				Map<String, IssueAll> issueAllMap=new HashMap<String, IssueAll>();
				String lotteryidstr="",issueid="";
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
	
							// 判断是否含有element标签，如果有的话创建resultElement
							if ("element".equals(name)) {
								resultElement = new SpeciesListElements();
								result.getBody().getElements().add(resultElement);
							}
							
							if("lottery".equals(name)){
								if (resultElement != null) {
									catalogue=new LotteryInfo();
								}
							}
							
							if("lotteryid".equals(name)){	//彩种ID
								if (catalogue != null) {
									catalogue.setLotteryId(parser.nextText());
								}
							}
						
							if("cnname".equals(name)){	//彩种ID
								if (catalogue != null) {
									catalogue.setLotteryName(parser.nextText());
								}
							}
							
							if("isown".equals(name)){
								if (catalogue != null) {
									catalogue.setIsown(parser.nextText());
								}
							}
							
							if("sorts".equals(name)){
								if (catalogue != null) {
									catalogue.setSorts(parser.nextText());
								}
							}
							
							if("lotterytype".equals(name)){
								if (catalogue != null) {
									catalogue.setLotterytype(parser.nextText());
								}
							}
							
							if("menus".equals(name)){
								playmenuList=new ArrayList<PlayMenu>();
							}
							
							if("menu".equals(name)){
								if (resultElement != null) {
									playmenu=new PlayMenu();
								}
							}
							
							if("lotterymethodid".equals(name)){
								lotteryidstr=parser.nextText();
							}
							
							if("iscellphone".equals(name)){
								if (playmenu != null) {
									playmenu.setIscellphone(parser.nextText());
								}
							}
							
							if("jscode".equals(name)){
								if (playmenu != null) {
									playmenu.setJscode(parser.nextText());
								}
							}
							
							if("methodname".equals(name)){
								if (playmenu != null) {
									playmenu.setMethodname(parser.nextText());
								}
							}
							
							if("methodid".equals(name)){
								if (playmenu != null) {
									playmenu.setMethodid(parser.nextText());
								}
							}
							
							if("issues".equals(name))
							{
								if (resultElement != null) {
									issueAll=new IssueAll();
								}
							}
							
							if("issueid".equals(name))
							{
								if(issueAll!=null){
									issueid=parser.nextText();
								}
							}
							
							if("issuesales".equals(name)){
								if (resultElement != null) {
									issueSales=new IssueSales();
								}
							}
							
							if("opentime".equals(name)){
								if(issueSales!=null){
									issueSales.setOpentime(parser.nextText());
								}
							}
							if("issuesalesno".equals(name)){
								if(issueSales!=null){
									issueSales.setIssue(parser.nextText());
								}
							}
							if("saleend".equals(name)){
								if(issueSales!=null){
									issueSales.setSaleend(parser.nextText());
								}
							}
							if("currenttime".equals(name)){
								if(issueSales!=null){
									issueSales.setCurrenttime(parser.nextText());
								}
							}
							
							if("issuelast".equals(name)){
								if (resultElement != null) {
									issueLast=new IssueLast();
								}
							}
							
							if("code".equals(name)){
								if(issueLast!=null){
									issueLast.setCode(parser.nextText());
								}
							}
							if("issuelastno".equals(name)){
								if(issueLast!=null){
									issueLast.setIssue(parser.nextText());
								}
							}
							
							if("issuetask".equals(name)){
								if (resultElement != null) {
									issueTask=new IssueTask();
								}
							}
							
							if("issuerule".equals(name)){
								if(issueTask!=null){
									issueTask.setIssuerule(parser.nextText());
								}
							}
							if("issuecount".equals(name)){
								if(issueTask!=null){
									issueTask.setIssuecount(parser.nextText());
								}
							}
							if("maxtaskcount".equals(name)){
								if(issueTask!=null){
									issueTask.setMaxtaskcount(parser.nextText());
								}
							}
							if("finalissue".equals(name)){
								if(issueTask!=null){
									issueTask.setFinalissue(parser.nextText());
								}
							}
							
							break;
						case XmlPullParser.END_TAG:
							String nameend=parser.getName();
							if(nameend.equalsIgnoreCase("lottery") && catalogue != null){
								catalogueList.add(catalogue);
							}
							
							if(nameend.equalsIgnoreCase("menu") && playmenu != null){	//添加一个玩法信息
								playmenuList.add(playmenu);
							}
							
							if(nameend.equalsIgnoreCase("menus")){	//结束时添加进集合里
								menuMap.put(lotteryidstr, playmenuList);
							}
							
							if(nameend.equalsIgnoreCase("issuesales")&&issueSales!=null){
								issueAll.setIssueSales(issueSales);
							}
							
							if(nameend.equalsIgnoreCase("issuelast")&&issueLast!=null){
								issueAll.setIssueLast(issueLast);
							}
							
							if(nameend.equalsIgnoreCase("issuetask")&&issueTask!=null){
								issueAll.setIssueTask(issueTask);
							}
							
							if(nameend.equalsIgnoreCase("issues")){
								issueAllMap.put(issueid, issueAll);
							}
							
							if(nameend.equalsIgnoreCase("element") && resultElement != null){
								resultElement.setCatalogueList(catalogueList);
								resultElement.setIssueAllMap(issueAllMap);
								resultElement.setMenuMap(menuMap);
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
	
    /** 
     * @Title:              CurrentIssueInfo
     * @author              Ace
     * @Description:        当前销售信息
     * @param lotteryId 
     * @return Message					
     */
	@Override
	public Message getCurrentIssueInfo(Integer lotteryId) {
		
		CurrentIssueElement issueElement = new CurrentIssueElement();
		issueElement.getLotteryids().setTagValue(lotteryId.toString());
		
		Message message = new Message();
		String xml = message.getXml(issueElement);
		// xml
		Message result = super.getResultCurrentIssueMethod(xml,lotteryId,1);
		if (result != null) {
			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容
			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;
				TaskIssueInfoElement resultElement = null;
				IssueAll issueAll=null;
				IssueLast issueLast=null;
				IssueSales issueSales=null;
				String issueid="";
				Map<String, IssueAll> issueAllMap=new HashMap<String, IssueAll>();
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
	
							// 判断是否含有element标签，如果有的话创建resultElement
							if("element".equals(name))
							{
								resultElement = new TaskIssueInfoElement();
								result.getBody().getElements().add(resultElement);
							}
							if("issues".equals(name)){
								if (resultElement != null) {
									issueAll=new IssueAll();
								}
							}
							
							if("issueid".equals(name)){
								issueid=parser.nextText();
							}
							
							if("issuesales".equals(name)){
								if (resultElement != null) {
									issueSales=new IssueSales();
								}
							}
							
							if("opentime".equals(name)){
								if(issueSales!=null){
									issueSales.setOpentime(parser.nextText());
								}
							}
							if("issuesalesno".equals(name)){
								if(issueSales!=null){
									issueSales.setIssue(parser.nextText());
								}
							}
							if("saleend".equals(name)){
								if(issueSales!=null){
									issueSales.setSaleend(parser.nextText());
								}
							}
							if("currenttime".equals(name)){
								if(issueSales!=null){
									issueSales.setCurrenttime(parser.nextText());
								}
							}
							
							if("issuelast".equals(name)){
								if (resultElement != null) {
									issueLast=new IssueLast();
								}
							}
							
							if("code".equals(name)){
								if(issueLast!=null){
									issueLast.setCode(parser.nextText());
								}
							}
							if("issuelastno".equals(name)){
								if(issueLast!=null){
									issueLast.setIssue(parser.nextText());
								}
							}
							
							break;
						case XmlPullParser.END_TAG:
							String nameend=parser.getName();
							if(nameend.equalsIgnoreCase("issuesales")&&issueSales!=null){
								issueAll.setIssueSales(issueSales);
							}
							
							if(nameend.equalsIgnoreCase("issuelast")&&issueLast!=null){
								issueAll.setIssueLast(issueLast);
							}
							
							if(nameend.equalsIgnoreCase("issues")){
								issueAllMap.put(issueid, issueAll);
							}
							
							if(nameend.equalsIgnoreCase("element") && resultElement != null){
								resultElement.setIssueAllMap(issueAllMap);
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

    /** 
     * @Title:              LotteryMenuInfo 
     * @author              Ace
     * @Description:        当前彩种玩法信息请求 
     * @param lotteryId 
     * @return Message					
     */ 
	@Override
	public Message getLotteryMenuInfo(Integer integer) {
		GameplayElements element = new GameplayElements();
		
		Message message = new Message();
		String xml = message.getXml(element);
		// TODO Auto-generated method stub
		Message result = super.getResultLotteryMenuInfoMethod(xml,integer);
		if (result != null) {

			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容
			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE",ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				GameplayElements gameplays = null;
				GameplayElement gameplay = null;				//玩法节点
				PlayMenu playmenu=null;
				List<GameplayElement> gameplaylist=new ArrayList<GameplayElement>();
				
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

						// 判断是否含有element标签，如果有的话创建resultElement
						if ("menus".equals(name)) {
							gameplays = new GameplayElements();
							result.getBody().getElements().add(gameplays);
						}
						
						
						if("menu".equals(name)){
							if (gameplays != null) {
								playmenu=new PlayMenu();
								gameplay =new GameplayElement();
								
							}
						}

						// 解析特殊的数据
						if ("iscellphone".equals(name)) {
							if (gameplay != null) {
								playmenu.setIscellphone(parser.nextText());
							}
						}
						if ("jscode".equals(name)) {
							if (gameplay != null) {
								playmenu.setJscode(parser.nextText());
							}
						}
						if ("methodname".equals(name)) {
							if (gameplay != null) {
								playmenu.setMethodname(parser.nextText());
							}
						}
						if ("methodid".equals(name)) {
							if (gameplay != null) {
								playmenu.setMethodid(parser.nextText());
							}
						}
						break;
					case XmlPullParser.END_TAG:
						String nameend=parser.getName();
						if(nameend.equalsIgnoreCase("menu")){
							//添加一个玩法信息
							gameplay.setPlaymenu(playmenu);
							gameplaylist.add(gameplay);
						}
						
						if(nameend.equalsIgnoreCase("menus")){
							//结束时添加进集合里
							gameplays.setGameplayList(gameplaylist); //其中一个彩种全部玩法结点集合 gameplay.setPlaymenu(playmenu);
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
