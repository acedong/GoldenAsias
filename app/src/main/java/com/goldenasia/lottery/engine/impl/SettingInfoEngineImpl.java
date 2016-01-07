package com.goldenasia.lottery.engine.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.bean.ActivityDesc;
import com.goldenasia.lottery.bean.AddUserInfo;
import com.goldenasia.lottery.bean.AddUserQuota;
import com.goldenasia.lottery.bean.AnalysisBank;
import com.goldenasia.lottery.bean.BankInfo;
import com.goldenasia.lottery.bean.BankSupportInfo;
import com.goldenasia.lottery.bean.BanksBoundCards;
import com.goldenasia.lottery.bean.BindingData;
import com.goldenasia.lottery.bean.MessageInfo;
import com.goldenasia.lottery.bean.PersonalMsg;
import com.goldenasia.lottery.engine.BaseSetting;
import com.goldenasia.lottery.engine.SettingInfoEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.element.ActivityDescElement;
import com.goldenasia.lottery.net.protocal.element.AddUserQuotaElement;
import com.goldenasia.lottery.net.protocal.element.AnalysisBankElement;
import com.goldenasia.lottery.net.protocal.element.BankInfoElement;
import com.goldenasia.lottery.net.protocal.element.BankInfoElements;
import com.goldenasia.lottery.net.protocal.element.MessageInfoElement;
import com.goldenasia.lottery.net.protocal.element.MessageListElement;
import com.goldenasia.lottery.util.DES;

public class SettingInfoEngineImpl extends BaseSetting implements SettingInfoEngine {

	@Override
	public Message oneselfMessages(PersonalMsg msgParam) {
		// TODO Auto-generated method stub
		MessageListElement element = new MessageListElement();
		Message message = new Message();
		String xml = message.getXml(element);
		JSONObject jsonMsgObj = getResultOneselfMessages(msgParam);
		if (jsonMsgObj != null) {
			try {
				Message result = new Message();
				DES des = new DES();
				// 第三步(代码不变)：数据的校验（MD5数据校验）
				XmlPullParser parser = Xml.newPullParser();
				// 进入使用xml存放数据
				parser.setInput(new StringReader(xml));
				int eventType = parser.getEventType();
				String name;
				int count=0;
				MessageListElement resultElement=null;
				MessageInfo messageInfo=null;
				List<MessageInfo> messageInfoList=new ArrayList<MessageInfo>();
				
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("body".equals(name)) {
								String dataNodeXml="",info="",countInfo="";
								resultElement=new MessageListElement();
								result.getBody().getElements().add(resultElement);
								if(!jsonMsgObj.isNull("count")){
									count=jsonMsgObj.getInt("count");
									countInfo+="<count>"+jsonMsgObj.getInt("count")+"</count>";
								}
								
								if(!jsonMsgObj.isNull("message")){
									JSONArray msgJsonArray=jsonMsgObj.getJSONArray("message");
									for(int i=0;i < msgJsonArray.length(); i++){
										JSONObject msgJson = (JSONObject) msgJsonArray.get(i);  
										messageInfo=new MessageInfo();
										info+="<msg>";
										for(int m=0; m<msgJson.length(); m++){
											String nameMsg = msgJson.names().getString(m); 
											info+="<"+nameMsg+">"+msgJson.getString(nameMsg)+"</"+nameMsg+">";
											if(nameMsg.equals("id")){
												messageInfo.setId(msgJson.getString(nameMsg));
											}
											
											if(nameMsg.equals("content")){
												messageInfo.setContent(msgJson.getString(nameMsg));
											}
											
											if(nameMsg.equals("subject")){
												messageInfo.setSubject(msgJson.getString(nameMsg));
											}
											
											if(nameMsg.equals("title")){
												messageInfo.setTitle(msgJson.getString(nameMsg));
											}
											
											if(nameMsg.equals("sendtime")){
												messageInfo.setSendtime(msgJson.getString(nameMsg));
											}
										}
										messageInfoList.add(messageInfo);
										info+="</msg>";
									}
								}
								
								resultElement.setCount(count);
								resultElement.setMessageInfoList(messageInfoList);
								
								if(count>0){
									info="<elements><element>"+countInfo+info+"</element></elements>";
								}else{
									info=countInfo;
								}
								
								if (jsonMsgObj.getInt("errNo") == 0){
									result.getBody().getOelement().setErrorcode(jsonMsgObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg("Success");
									dataNodeXml = "<errorcode>"+ jsonMsgObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
								}else{
									result.getBody().getOelement().setErrorcode(jsonMsgObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg(jsonMsgObj.getString("errInfo"));
									dataNodeXml = "<errorcode>"+ jsonMsgObj.getInt("errNo")+ "</errorcode><errormsg>"+ jsonMsgObj.getString("errInfo")+ "</errormsg>";
								}
	
								String msgInfo = des.authcode(dataNodeXml+info, "DECODE", ConstantValue.DES_PASSWORD);
								result.getBody().setServiceBodyInsideDESInfo(msgInfo);
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
	 * 系统消息 列表
	 */
	@Override
	public Message systemMessages() {
		// TODO Auto-generated method stub
		MessageListElement element = new MessageListElement();
		Message message = new Message();
		String xml = message.getXml(element);
		JSONObject jsonMsgObj = getResultSystemMessages();
		if (jsonMsgObj != null) {
			try {
				Message result = new Message();
				DES des = new DES();
				// 第三步(代码不变)：数据的校验（MD5数据校验）
				XmlPullParser parser = Xml.newPullParser();
				// 进入使用xml存放数据
				parser.setInput(new StringReader(xml));
				int eventType = parser.getEventType();
				String name;
				int count=0;
				MessageListElement resultElement=null;
				MessageInfo messageInfo=null;
				List<MessageInfo> messageInfoList=new ArrayList<MessageInfo>();
				
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("body".equals(name)) {
								String dataNodeXml="",info="",countInfo="";
								resultElement=new MessageListElement();
								result.getBody().getElements().add(resultElement);
								
								if(!jsonMsgObj.isNull("list")){
									JSONArray msgJsonArray=jsonMsgObj.getJSONArray("list");
									for(int i=0;i < msgJsonArray.length(); i++){
										JSONObject msgJson = (JSONObject) msgJsonArray.get(i);  
										messageInfo=new MessageInfo();
										info+="<msg>";
										for(int m=0; m<msgJson.length(); m++){
											String nameMsg = msgJson.names().getString(m); 
											info+="<"+nameMsg+">"+msgJson.getString(nameMsg)+"</"+nameMsg+">";
											if(nameMsg.equals("id")){
												messageInfo.setId(msgJson.getString(nameMsg));
											}
											
											if(nameMsg.equals("subject")){
												messageInfo.setSubject(msgJson.getString(nameMsg));
											}
											
											if(nameMsg.equals("sendday")){
												messageInfo.setSendday(msgJson.getString(nameMsg));
											}
											
											if(nameMsg.equals("istop")){
												messageInfo.setIstop(msgJson.getString(nameMsg));
											}
										}
										messageInfoList.add(messageInfo);
										info+="</msg>";
									}
								}
								
								resultElement.setCount(count);
								resultElement.setMessageInfoList(messageInfoList);
								
								if(count>0){
									info="<elements><element>"+countInfo+info+"</element></elements>";
								}else{
									info=countInfo;
								}
								
								if (jsonMsgObj.getInt("errNo") == 0){
									result.getBody().getOelement().setErrorcode(jsonMsgObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg("Success");
									dataNodeXml = "<errorcode>"+ jsonMsgObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
								}else{
									result.getBody().getOelement().setErrorcode(jsonMsgObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg(jsonMsgObj.getString("errInfo"));
									dataNodeXml = "<errorcode>"+ jsonMsgObj.getInt("errNo")+ "</errorcode><errormsg>"+ jsonMsgObj.getString("errInfo")+ "</errormsg>";
								}
	
								String msgInfo = des.authcode(dataNodeXml+info, "DECODE", ConstantValue.DES_PASSWORD);
								result.getBody().setServiceBodyInsideDESInfo(msgInfo);
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
	 * 系统消息 详情
	 */
	@Override
	public Message systemMessagesDetail(String id) {
		// TODO Auto-generated method stub
		MessageListElement element = new MessageListElement();
		Message message = new Message();
		String xml = message.getXml(element);
		JSONObject jsonMsgObj = getResultSystemMessagesInfo(id);
		if (jsonMsgObj != null) {
			try {
				Message result = new Message();
				DES des = new DES();
				// 第三步(代码不变)：数据的校验（MD5数据校验）
				XmlPullParser parser = Xml.newPullParser();
				// 进入使用xml存放数据
				parser.setInput(new StringReader(xml));
				int eventType = parser.getEventType();
				String name;
				int count=0;
				MessageInfoElement resultElement=null;
				MessageInfo messageInfo=null;
				
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("body".equals(name)) {
								String dataNodeXml="",info="";
								resultElement=new MessageInfoElement();
								result.getBody().getElements().add(resultElement);
								
								if(!jsonMsgObj.isNull("info")){
									JSONObject msgJson =jsonMsgObj.getJSONObject("info");  
									messageInfo=new MessageInfo();
									for(int m=0; m<msgJson.length(); m++){
										String nameMsg = msgJson.names().getString(m); 
										info+="<"+nameMsg+">"+msgJson.getString(nameMsg)+"</"+nameMsg+">";
										if(nameMsg.equals("id")){
											messageInfo.setId(msgJson.getString(nameMsg));
										}
										
										if(nameMsg.equals("content")){
											messageInfo.setContent(msgJson.getString(nameMsg));
										}
										
										if(nameMsg.equals("sendtime")){
											messageInfo.setSendtime(msgJson.getString(nameMsg));
										}
										
										if(nameMsg.equals("subject")){
											messageInfo.setSubject(msgJson.getString(nameMsg));
										}
									}
								}
								
								resultElement.setMessageInfo(messageInfo);;
								
								if(count>0){
									info="<elements><element>"+info+"</element></elements>";
								}else{
									info="";
								}
								
								if (jsonMsgObj.getInt("errNo") == 0){
									result.getBody().getOelement().setErrorcode(jsonMsgObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg("Success");
									dataNodeXml = "<errorcode>"+ jsonMsgObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
								}else{
									result.getBody().getOelement().setErrorcode(jsonMsgObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg(jsonMsgObj.getString("errInfo"));
									dataNodeXml = "<errorcode>"+ jsonMsgObj.getInt("errNo")+ "</errorcode><errormsg>"+ jsonMsgObj.getString("errInfo")+ "</errormsg>";
								}
	
								String msgInfo = des.authcode(dataNodeXml+info, "DECODE", ConstantValue.DES_PASSWORD);
								result.getBody().setServiceBodyInsideDESInfo(msgInfo);
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
	public Message analysisData(String cardNo) {
		// TODO Auto-generated method stub
		
		Message result = getResultAnalysisData(cardNo);

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				AnalysisBankElement resultElement = null;
				AnalysisBank bindingData = null;
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
							bindingData = new AnalysisBank();
							resultElement = new AnalysisBankElement();
							result.getBody().getElements().add(resultElement);
						}

						if ("card".equals(name)) {
							if (bindingData != null) {
								bindingData.setCard(parser.nextText());
							}
						}

						if ("province".equals(name)) {
							if (bindingData != null) {
								bindingData.setProvince(parser.nextText());
							}
						}
						
						if ("city".equals(name)) {
							if (bindingData != null) {
								bindingData.setCity(parser.nextText());
							}
						}
						
						if ("bank".equals(name)) {
							if (bindingData != null) {
								bindingData.setBank(parser.nextText());
							}
						}
						
						if ("type".equals(name)) {
							if (bindingData != null) {
								bindingData.setType(parser.nextText());
							}
						}
						
						if ("cardname".equals(name)) {
							if (bindingData != null) {
								bindingData.setCardname(parser.nextText());
							}
						}
						
						if ("tel".equals(name)) {
							if (bindingData != null) {
								bindingData.setTel(parser.nextText());
							}
						}
						break;
					case XmlPullParser.END_TAG:
						String nameend = parser.getName();
							if ("element".equals(nameend) && bindingData != null) {
								resultElement.setAnalysisbank(bindingData);;
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
	public Message addUserQuota() {
		// TODO Auto-generated method stub
		Message result = getResultAddUserQuota();

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				AddUserQuotaElement resultElement = null;
				AddUserQuota adduserquota=null;
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
							adduserquota = new AddUserQuota();
							resultElement = new AddUserQuotaElement();
							result.getBody().getElements().add(resultElement);
						}

						if ("minpoint".equals(name)) {
							if (adduserquota != null) {
								adduserquota.setMinpoint(parser.nextText());
							}
						}
						
						if ("maxpoint".equals(name)) {
							if (adduserquota != null) {
								adduserquota.setMaxpoint(parser.nextText());
							}
						}
						
						if ("usermaxpoint".equals(name)) {
							if (adduserquota != null) {
								adduserquota.setUsermaxpoint(parser.nextText());
							}
						}
						
						if ("userminpoint".equals(name)) {
							if (adduserquota != null) {
								adduserquota.setUserminpoint(parser.nextText());
							}
						}
						
						if ("username".equals(name)) {
							if (adduserquota != null) {
								adduserquota.setUsername(parser.nextText());
							}
						}
						
						if ("quota".equals(name)) {
							if (adduserquota != null) {
								adduserquota.setUserquota(parser.nextText());
							}
						}
						break;
					case XmlPullParser.END_TAG:
						String nameend = parser.getName();
						
						if ("element".equals(nameend) && adduserquota != null) {
							resultElement.setAddUserQuota(adduserquota);
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
	public Message addUserData(AddUserInfo user) {
		// TODO Auto-generated method stub
		Message result = getResultAddUserData(user);

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

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
	public Message getcampaignDetail(String id) {
		// TODO Auto-generated method stub
		ActivityDescElement element = new ActivityDescElement();
		Message message = new Message();
		String xml = message.getXml(element);
		JSONObject jsonMsgObj = getResultCampaignDetail();
		if (jsonMsgObj != null) {
			try {
				Message result = new Message();
				DES des = new DES();
				// 第三步(代码不变)：数据的校验（MD5数据校验）
				XmlPullParser parser = Xml.newPullParser();
				// 进入使用xml存放数据
				parser.setInput(new StringReader(xml));
				int eventType = parser.getEventType();
				String name;
				ActivityDescElement resultElement=null;
				ActivityDesc activity=null;
				
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("body".equals(name)) {
								String dataNodeXml="",info="";
								resultElement=new ActivityDescElement();
								result.getBody().getElements().add(resultElement);
								activity=new ActivityDesc();
								if(!jsonMsgObj.isNull("activitydesc")){
									JSONObject msgJson =jsonMsgObj.getJSONObject("activitydesc");  
									for(int m=0; m<msgJson.length(); m++){
										String nameMsg = msgJson.names().getString(m); 
										info+="<"+nameMsg+">"+msgJson.getString(nameMsg)+"</"+nameMsg+">";
										if(nameMsg.equals("starttime")){
											activity.setStarttime(msgJson.getString(nameMsg));
										}
										
										if(nameMsg.equals("endtime")){
											activity.setEndtime(msgJson.getString(nameMsg));
										}
										
										if(nameMsg.equals("minbet")){
											activity.setMinbet(msgJson.getString(nameMsg));
										}
										
										if(nameMsg.equals("baseprize")){
											activity.setBaseprize(msgJson.getString(nameMsg));
										}
										
										if(nameMsg.equals("title")){
											activity.setTitle(msgJson.getString(nameMsg));
										}
										
										if(nameMsg.equals("desc")){
											activity.setDesc(msgJson.getString(nameMsg));
										}
										
										if(nameMsg.equals("detail")){
											activity.setDetail(msgJson.getString(nameMsg));
										}
										
										if(nameMsg.equals("function")){
											activity.setFunction(msgJson.getString(nameMsg));
										}
									}
									
								}
								
								if(!jsonMsgObj.isNull("activitydata")){
									JSONObject activitydescJson =jsonMsgObj.getJSONObject("activitydata");  
									for(int m=0; m<activitydescJson.length(); m++){
										String nameMsg = activitydescJson.names().getString(m); 
										info+="<"+nameMsg+">"+activitydescJson.getString(nameMsg)+"</"+nameMsg+">";
										if(nameMsg.equals("havegift")){
											activity.setHavegift(activitydescJson.getInt(nameMsg));
										}
									}
								}
								
								if(!jsonMsgObj.isNull("activitydesc")&&!jsonMsgObj.isNull("activitydata"))
									info="<elements><element>"+info+"</element></elements>";
								
								resultElement.setActivityDesc(activity);
								
								if (jsonMsgObj.getInt("errNo") == 0){
									result.getBody().getOelement().setErrorcode(jsonMsgObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg("Success");
									dataNodeXml = "<errorcode>"+ jsonMsgObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
								}else{
									result.getBody().getOelement().setErrorcode(jsonMsgObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg(jsonMsgObj.getString("errInfo"));
									dataNodeXml = "<errorcode>"+ jsonMsgObj.getInt("errNo")+ "</errorcode><errormsg>"+ jsonMsgObj.getString("errInfo")+ "</errormsg>";
								}
	
								String msgInfo = des.authcode(dataNodeXml+info, "DECODE", ConstantValue.DES_PASSWORD);
								result.getBody().setServiceBodyInsideDESInfo(msgInfo);
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
	public Message postcampaignDetail(String id) {
		// TODO Auto-generated method stub
		ActivityDescElement element = new ActivityDescElement();
		Message message = new Message();
		String xml = message.getXml(element);
		JSONObject jsonMsgObj = getResultCampaignDetail();
		if (jsonMsgObj != null) {
			try {
				Message result = new Message();
				DES des = new DES();
				// 第三步(代码不变)：数据的校验（MD5数据校验）
				XmlPullParser parser = Xml.newPullParser();
				// 进入使用xml存放数据
				parser.setInput(new StringReader(xml));
				int eventType = parser.getEventType();
				String name;
				
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("body".equals(name)) {
								String dataNodeXml="",info="";
								
								if (jsonMsgObj.getInt("errNo") == 0){
									result.getBody().getOelement().setErrorcode(jsonMsgObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg("Success");
									dataNodeXml = "<errorcode>"+ jsonMsgObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
								}else{
									result.getBody().getOelement().setErrorcode(jsonMsgObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg(jsonMsgObj.getString("errInfo"));
									dataNodeXml = "<errorcode>"+ jsonMsgObj.getInt("errNo")+ "</errorcode><errormsg>"+ jsonMsgObj.getString("errInfo")+ "</errormsg>";
								}
	
								String msgInfo = des.authcode(dataNodeXml+info, "DECODE", ConstantValue.DES_PASSWORD);
								result.getBody().setServiceBodyInsideDESInfo(msgInfo);
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
