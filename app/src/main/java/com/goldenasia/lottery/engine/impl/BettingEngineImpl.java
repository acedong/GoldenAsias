package com.goldenasia.lottery.engine.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.bean.AccountChangeInfo;
import com.goldenasia.lottery.bean.AppendTaskDetail;
import com.goldenasia.lottery.bean.AppendTaskDetailItems;
import com.goldenasia.lottery.bean.AppendTaskInfo;
import com.goldenasia.lottery.bean.AppendUndo;
import com.goldenasia.lottery.bean.BettingInfo;
import com.goldenasia.lottery.bean.ICount;
import com.goldenasia.lottery.bean.MessageInfo;
import com.goldenasia.lottery.bean.OrderCatalog;
import com.goldenasia.lottery.bean.OrderDetails;
import com.goldenasia.lottery.bean.PageInfo;
import com.goldenasia.lottery.bean.ReportData;
import com.goldenasia.lottery.bean.SearchParam;
import com.goldenasia.lottery.engine.BaseBetting;
import com.goldenasia.lottery.engine.BettingEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.element.AccountChangeElement;
import com.goldenasia.lottery.net.protocal.element.AppendOrderElements;
import com.goldenasia.lottery.net.protocal.element.AppendTaskDetailElement;
import com.goldenasia.lottery.net.protocal.element.MessageListElement;
import com.goldenasia.lottery.net.protocal.element.OrderDetailsElement;
import com.goldenasia.lottery.net.protocal.element.OrderElement;
import com.goldenasia.lottery.net.protocal.element.OrderElements;
import com.goldenasia.lottery.net.protocal.element.OrderListElements;
import com.goldenasia.lottery.net.protocal.element.ReportDataElement;
import com.goldenasia.lottery.util.DES;

public class BettingEngineImpl extends BaseBetting implements BettingEngine {

	/**
	 * 投注信息 无追号，追号
	 */

	@Override
	public Message bettingNumber(BettingInfo betting) {
		// TODO Auto-generated method stub
		Message result = getResultBettingNumber(betting);

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"
						+ des.authcode(result.getBody()
								.getServiceBodyInsideDESInfo(), "ENCODE",
								ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("errorcode".equals(name)) {
							result.getBody().getOelement()
									.setErrorcode(parser.nextText());
						}
						if ("errormsg".equals(name)) {
							result.getBody().getOelement()
									.setErrormsg(parser.nextText());
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
	public Message bettingOrderListInfo(Integer page) {
		// TODO Auto-generated method stub
		Message result = getResultOrderList(page);

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"
						+ des.authcode(result.getBody()
								.getServiceBodyInsideDESInfo(), "ENCODE",
								ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				OrderListElements resultElement = null; // 订单身列表
				OrderElements orderEles = null;
				OrderElement orderEle = null;
				OrderCatalog orderCatalog = null;

				List<OrderElement> orderList = new ArrayList<OrderElement>();

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("errorcode".equals(name)) {
							result.getBody().getOelement()
									.setErrorcode(parser.nextText());
						}
						if ("errormsg".equals(name)) {
							result.getBody().getOelement()
									.setErrormsg(parser.nextText());
						}

						if ("element".equals(name)) {
							resultElement = new OrderListElements();
							result.getBody().getElements().add(resultElement);
						}

						if ("orders".equals(name)) {
							if (resultElement != null) {
								orderEles = resultElement.getOrderElements();
							}
						}

						if ("order".equals(name)) {
							if (resultElement != null) {
								orderCatalog = new OrderCatalog();
								orderEle = new OrderElement();
							}
						}

						if ("lotteryname".equals(name)) {
							if (orderCatalog != null) {
								orderCatalog.setLotteryname(parser.nextText());
							}
						}

						if ("methodname".equals(name)) {
							if (orderCatalog != null) {
								orderCatalog.setMethodname(parser.nextText());
							}
						}

						if ("writetime".equals(name)) {
							if (orderCatalog != null) {
								orderCatalog.setWritetime(parser.nextText());
							}
						}

						if ("status".equals(name)) {
							if (orderCatalog != null) {
								orderCatalog.setStatus(parser.nextText());
							}
						}

						if ("totalprice".equals(name)) {
							if (orderCatalog != null) {
								orderCatalog.setTotalprice(parser.nextText());
							}
						}

						if ("issue".equals(name)) {
							if (orderCatalog != null) {
								orderCatalog.setIssue(parser.nextText());
							}
						}

						if ("projectid".equals(name)) {
							if (orderCatalog != null) {
								orderCatalog.setProjectid(parser.nextText());
							}
						}

						if ("modes".equals(name)) {
							if (orderCatalog != null) {
								orderCatalog.setModes(parser.nextText());
							}
						}
						break;
					case XmlPullParser.END_TAG:
						String nameend = parser.getName();
						if (nameend.equalsIgnoreCase("order")
								&& orderEle != null) {
							orderEle.setOrderCatalog(orderCatalog);
							orderList.add(orderEle);
						}
						if (nameend.equalsIgnoreCase("orders")
								&& orderEles != null) {
							orderEles.setOrderElementList(orderList);
						}
						if (nameend.equalsIgnoreCase("element")
								&& resultElement != null) {
							resultElement.setOrderElements(orderEles);
							;
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
	public Message getOrderDetailsInfo(Integer detailsId) {
		// TODO Auto-generated method stub
		Message result = getResultOrderDetailsInfo(detailsId);

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>" + des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				OrderDetailsElement resultElement = null; // 订单身列表
				OrderDetails orderDetails = null;

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
							resultElement = new OrderDetailsElement();
							result.getBody().getElements().add(resultElement);
						}

						if ("details".equals(name)) {
							if (resultElement != null) {
								orderDetails = new OrderDetails();
							}
						}

						if ("username".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setUsername(parser.nextText());
							}
						}
						if ("cnname".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setCnname(parser.nextText());
							}
						}
						if ("methodname".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setMethodname(parser.nextText());
							}
						}
						if ("writetime".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setWritetime(parser.nextText());
							}
						}
						if ("nocode".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setNocode(parser.nextText());
							}
						}
						if ("dypointdec".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setDypointdec(parser.nextText());
							}
						}
						if ("totalprice".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setTotalprice(parser.nextText());
							}
						}
						if ("issue".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setIssue(parser.nextText());
							}
						}
						if ("taskid".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setTaskid(parser.nextText());
							}
						}
						if ("code".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setCode(parser.nextText());
							}
						}
						if ("bonus".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setBonus(parser.nextText());
							}
						}
						if ("modes".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setModes(parser.nextText());
							}
							
						}
						if ("projectid".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setProjectid(parser.nextText());
							}
						}
						if ("statusdesc".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setStatusdesc(parser.nextText());
							}
						}
						
						if ("projectno".equals(name)) {
							if (orderDetails != null) {
								orderDetails.setProjectno(parser.nextText());
							}
						}
						break;
					case XmlPullParser.END_TAG:
						String nameend = parser.getName();
						if (nameend.equalsIgnoreCase("details") && orderDetails != null) {
							resultElement.setOrderDetails(orderDetails);
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
	public Message getOrderCancel(Integer projectid) {
		// TODO Auto-generated method stub
		Message result = getResultOrderCancel(projectid);

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"
						+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE",ConstantValue.DES_PASSWORD) + "</body>";

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
	public Message appendOrderListInfo(Integer page) {
		// TODO Auto-generated method stub
		Message result = getResultAppendOrderList(page);

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE",ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				AppendOrderElements resultElement = null; // 订单身列表
				PageInfo pageinfo=null;
				AppendTaskInfo appttask=null;
				List<AppendTaskInfo> appendtaskList = new ArrayList<AppendTaskInfo>();

				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("errorcode".equals(name)) {
							result.getBody().getOelement()
									.setErrorcode(parser.nextText());
						}
						if ("errormsg".equals(name)) {
							result.getBody().getOelement()
									.setErrormsg(parser.nextText());
						}

						if ("element".equals(name)) {
							resultElement = new AppendOrderElements();
							pageinfo=new PageInfo();
							result.getBody().getElements().add(resultElement);
						}

						if ("pagecount".equals(name)) {
							if (pageinfo != null) {
								pageinfo.setPagecount(Integer.parseInt(parser.nextText()));
							}
						}

						if ("currentpage".equals(name)) {
							if (pageinfo != null) {
								pageinfo.setCurrentpage(Integer.parseInt(parser.nextText()));
							}
						}

						if ("result".equals(name)) {
							if (resultElement != null) {
								appttask=new AppendTaskInfo();
							}
						}

						if ("title".equals(name)) {
							if (appttask != null) {
								appttask.setTitle(parser.nextText());
							}
						}

						if ("taskid".equals(name)) {
							if (appttask != null) {
								appttask.setTaskid(parser.nextText());
							}
						}

						if ("taskprice".equals(name)) {
							if (appttask != null) {
								appttask.setTaskprice(parser.nextText());
							}
						}

						if ("finishprice".equals(name)) {
							if (appttask != null) {
								appttask.setFinishprice(parser.nextText());
							}
						}

						if ("begintime".equals(name)) {
							if (appttask != null) {
								appttask.setBegintime(parser.nextText());
							}
						}

						if ("modes".equals(name)) {
							if (appttask != null) {
								appttask.setModes(parser.nextText());
							}
						}

						if ("lotteryname".equals(name)) {
							if (appttask != null) {
								appttask.setLotteryname(parser.nextText());
							}
						}
						
						if ("status".equals(name)) {
							if (appttask != null) {
								appttask.setStatus(parser.nextText());
							}
						}
						break;
					case XmlPullParser.END_TAG:
						String nameend = parser.getName();
						if (nameend.equalsIgnoreCase("result")&& appttask != null) {
							appendtaskList.add(appttask);
						}
						if (nameend.equalsIgnoreCase("element")&& resultElement != null) {
							resultElement.setPageinfo(pageinfo);
							resultElement.setAppendList(appendtaskList);
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
	public Message appendOrderDetailsInfo(String id) {
		// TODO Auto-generated method stub

		AppendTaskDetailElement element = new AppendTaskDetailElement();
		Message message = new Message();
		String xml = message.getXml(element);
		JSONObject jsonAppendTaskObj = getResultAppendOrderDetails(id);
		if (jsonAppendTaskObj != null) {
			try {
				Message result = new Message();
				DES des = new DES();
				// 第三步(代码不变)：数据的校验（MD5数据校验）
				XmlPullParser parser = Xml.newPullParser();
				// 进入使用xml存放数据
				parser.setInput(new StringReader(xml));
				int eventType = parser.getEventType();
				String name;
				AppendTaskDetailElement resultElement=null;
				AppendTaskDetail appendTask=null;
				List<AppendTaskDetailItems> appendTaskInfoList=new ArrayList<AppendTaskDetailItems>();
				
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if ("body".equals(name)) {
								String dataNodeXml="",info="",task="";
								resultElement=new AppendTaskDetailElement();
								result.getBody().getElements().add(resultElement);
								
								if(!jsonAppendTaskObj.isNull("task")){
									JSONObject taskContent=jsonAppendTaskObj.getJSONObject("task");
									appendTask=new AppendTaskDetail();
									for(int t=0; t<taskContent.length(); t++){
										String nameTask=taskContent.names().getString(t);
										
										task+="<"+nameTask+">"+taskContent.getString(nameTask)+"</"+nameTask+">";
										if("taskid".equals(nameTask)){
											appendTask.setTaskid(taskContent.getString(nameTask));
										}
										if("title".equals(nameTask)){
											appendTask.setTitle(taskContent.getString(nameTask));
										}
										if("codes".equals(nameTask)){
											appendTask.setCodes(taskContent.getString(nameTask));					
										}
										if("issuecount".equals(nameTask)){
											appendTask.setIssuecount(taskContent.getString(nameTask));	
										}
										if("finishedcount".equals(nameTask)){
											appendTask.setFinishedcount(taskContent.getString(nameTask));
										}
										if("cancelcount".equals(nameTask)){
											appendTask.setCancelcount(taskContent.getString(nameTask));
										}
										if("singleprice".equals(nameTask)){
											appendTask.setSingleprice(taskContent.getString(nameTask));
										}
										if("taskprice".equals(nameTask)){
											appendTask.setTaskprice(taskContent.getString(nameTask));
										}
										if("finishprice".equals(nameTask)){
											appendTask.setFinishprice(taskContent.getString(nameTask));
										}
										if("cancelprice".equals(nameTask)){
											appendTask.setCancelprice(taskContent.getString(nameTask));
										}
										if("begintime".equals(nameTask)){
											appendTask.setBegintime(taskContent.getString(nameTask));
										}	
										if("beginissue".equals(nameTask)){
											appendTask.setBeginissue(taskContent.getString(nameTask));
										}
										if("wincount".equals(nameTask)){
											appendTask.setWincount(taskContent.getString(nameTask));
										}
										if("winprize".equals(nameTask)){
											appendTask.setWinprize(taskContent.getString(nameTask));
										}
										if("updatetime".equals(nameTask)){
											appendTask.setUpdatetime(taskContent.getString(nameTask));
										}
										if("userdefaultpoint".equals(nameTask)){
											appendTask.setUserdefaultpoint(taskContent.getString(nameTask));
										}
										if("dypointdec".equals(nameTask)){
											appendTask.setDypointdec(taskContent.getString(nameTask));
										}
										if("status".equals(nameTask)){
											appendTask.setStatus(taskContent.getString(nameTask));
										}
										if("stoponwin".equals(nameTask)){
											appendTask.setStoponwin(taskContent.getString(nameTask));
										}
										if("modes".equals(nameTask)){
											appendTask.setModes(taskContent.getString(nameTask));
										}
										if("cnname".equals(nameTask)){
											appendTask.setCnname(taskContent.getString(nameTask));
										}
										if("methodname".equals(nameTask)){
											appendTask.setMethodname(taskContent.getString(nameTask));
										}
										if("taskno".equals(nameTask)){
											appendTask.setTaskno(taskContent.getString(nameTask));
										}
									}
									resultElement.setTaskDetail(appendTask);
								}
								
								if(!jsonAppendTaskObj.isNull("detail")){
									JSONArray detailJsonArray=jsonAppendTaskObj.getJSONArray("detail");
									for(int i=0;i < detailJsonArray.length(); i++){
										JSONObject detailJson = (JSONObject) detailJsonArray.get(i);  
										AppendTaskDetailItems detailItems=new AppendTaskDetailItems();
										info+="<detail>";
										for(int m=0; m<detailJson.length(); m++){
											String nameDetail = detailJson.names().getString(m); 
											info+="<"+nameDetail+">"+detailJson.getString(nameDetail)+"</"+nameDetail+">";
											if(nameDetail.equals("projectid")){
												detailItems.setProjectid(detailJson.getString(nameDetail));
											}
											
											if(nameDetail.equals("multiple")){
												detailItems.setMultiple(detailJson.getString(nameDetail));
											}
											
											if(nameDetail.equals("status")){
												detailItems.setStatus(detailJson.getString(nameDetail));
											}
											
											if(nameDetail.equals("statusdes")){
												detailItems.setStatusdes(detailJson.getString(nameDetail));
											}
											
											if(nameDetail.equals("entry")){
												detailItems.setEntry(detailJson.getString(nameDetail));
											}
											
											if(nameDetail.equals("issue")){
												detailItems.setIssue(detailJson.getString(nameDetail));
											}
										}
										appendTaskInfoList.add(detailItems);
										info+="</detail>";
									}
									resultElement.setTaskDetailList(appendTaskInfoList);
								}
								
								if (jsonAppendTaskObj.getInt("errNo") == 0){
									result.getBody().getOelement().setErrorcode(jsonAppendTaskObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg("Success");
									dataNodeXml = "<errorcode>"+ jsonAppendTaskObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
								}else{
									result.getBody().getOelement().setErrorcode(jsonAppendTaskObj.getString("errNo"));
									result.getBody().getOelement().setErrormsg(jsonAppendTaskObj.getString("errInfo"));
									dataNodeXml = "<errorcode>"+ jsonAppendTaskObj.getInt("errNo")+ "</errorcode><errormsg>"+ jsonAppendTaskObj.getString("errInfo")+ "</errormsg>";
								}
	
								String msgInfo = des.authcode(dataNodeXml+"<elements><element>"+task+info+"</element></elements>", "DECODE", ConstantValue.DES_PASSWORD);
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
	public Message getBillingListInfo(Integer page) {
		// TODO Auto-generated method stub
		Message result = getResultBillingListInfo(page);  

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE",ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				AccountChangeElement resultElement = null; // 订单身列表
				PageInfo pageinfo=null;
				ICount icount=null;
				AccountChangeInfo accountChangeInfo=null;
				List<AccountChangeInfo> accountChangeList = new ArrayList<AccountChangeInfo>();

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
							resultElement = new AccountChangeElement();
							pageinfo=new PageInfo();
							result.getBody().getElements().add(resultElement);
						}

						if ("pagecount".equals(name)) {
							if (pageinfo != null) {
								pageinfo.setPagecount(Integer.parseInt(parser.nextText()));
							}
						}

						if ("currentpage".equals(name)) {
							if (pageinfo != null) {
								pageinfo.setCurrentpage(Integer.parseInt(parser.nextText()));
							}
						}
						
						if("icount".equals(name)){
							if(resultElement!=null){
								icount=new ICount();
							}
						}
						
						if("in".equals(name)){
							if(icount!=null){
								icount.setIn(Double.valueOf(parser.nextText()));
							}
						}
						
						if("out".equals(name)){
							if(icount!=null){
								icount.setOut(Double.valueOf(parser.nextText()));
							}
						}
						
						if("left".equals(name)){
							if(icount!=null){
								icount.setLeft(Double.valueOf(parser.nextText()));
							}
						}

						if ("result".equals(name)) {
							if (resultElement != null) {
								accountChangeInfo=new AccountChangeInfo();
							}
						}

						if ("entry".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setEntry(parser.nextText());
							}
						}
						
						if ("username".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setUsername(parser.nextText());
							}
						}
						
						if ("lotteryid".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setLotteryid(parser.nextText());
							}
						}
						
						if ("methodid".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setMethodid(parser.nextText());
							}
						}
						
						if ("taskid".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setTaskid(parser.nextText());
							}
						}
						
						if ("projectid".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setProjectid(parser.nextText());
							}
						}
						
						if ("fromuserid".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setFromuserid(parser.nextText());
							}
						}
						
						if ("times".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setTimes(parser.nextText());
							}
						}
						
						if ("ordertypeid".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setOrdertypeid(parser.nextText());
							}
						}
						
						if ("title".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setTitle(parser.nextText());
							}
						}
						
						if ("amount".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setAmount(parser.nextText());
							}
						}
						
						if ("availablebalance".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setAvailablebalance(parser.nextText());
							}
						}
						
						if ("description".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setDescription(parser.nextText());
							}
						}
						
						if ("modes".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setModes(parser.nextText());
							}
						}
						
						if ("issue".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setIssue(parser.nextText());
							}
						}
						
						if ("codetype".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setCodetype(parser.nextText());
							}
						}
						

						if ("orderno".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setOrderno(parser.nextText());
							}
						}
						
						if ("projectno".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setProjectno(parser.nextText());
							}
						}
						
						if ("operations".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setOperations(parser.nextText());
							}
						}
						
						if ("usertitle".equals(name)) {
							if (accountChangeInfo != null) {
								accountChangeInfo.setUsertitle(parser.nextText());
							}
						}

						break;
					case XmlPullParser.END_TAG:
						String nameend = parser.getName();
						if (nameend.equalsIgnoreCase("result")&& accountChangeInfo != null) {
							accountChangeList.add(accountChangeInfo);
						}
						if (nameend.equalsIgnoreCase("element")&& resultElement != null) {
							resultElement.setPageinfo(pageinfo);
							resultElement.setIcount(icount);
							resultElement.setAccountChangeList(accountChangeList);
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
	public Message getProfitStatisticsInfo(SearchParam param) {
		// TODO Auto-generated method stub
		Message result = getResultProfitStatisticsInfo(param);

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE",ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;
				
				ReportDataElement resultElement = null;
				ReportData report=null;
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("errorcode".equals(name)) {
							result.getBody().getOelement()
									.setErrorcode(parser.nextText());
						}
						if ("errormsg".equals(name)) {
							result.getBody().getOelement()
									.setErrormsg(parser.nextText());
						}
						
						if ("element".equals(name)) {
							resultElement = new ReportDataElement();
							report=new ReportData();
							result.getBody().getElements().add(resultElement);
						}

						if ("realpayment".equals(name)) {
							if (report != null) {
								report.setRealpayment(parser.nextText());
							}
						}
						
						if ("realwithdraw".equals(name)) {
							if (report != null) {
								report.setRealwithdraw(parser.nextText());
							}
						}
						
						if ("bets".equals(name)) {
							if (report != null) {
								report.setBets(parser.nextText());
							}
						}
						
						if ("points".equals(name)) {
							if (report != null) {
								report.setPoints(parser.nextText());
							}
						}
						
						if ("bonus".equals(name)) {
							if (report != null) {
								report.setBonus(parser.nextText());
							}
						}
						
						if ("profit".equals(name)) {
							if (report != null) {
								report.setProfit(parser.nextText());
							}
						}
						

						if ("teambets".equals(name)) {
							if (report != null) {
								report.setTeambets(parser.nextText());
							}
						}
						
						if ("teampoints".equals(name)) {
							if (report != null) {
								report.setTeampoints(parser.nextText());
							}
						}
						
						if ("teambonus".equals(name)) {
							if (report != null) {
								report.setTeambonus(parser.nextText());
							}
						}
						
						if ("teamrealpayment".equals(name)) {
							if (report != null) {
								report.setTeamrealpayment(parser.nextText());
							}
						}
						
						if ("teamrealwithdraw".equals(name)) {
							if (report != null) {
								report.setTeamrealwithdraw(parser.nextText());
							}
						}
						
						if ("teamprofit".equals(name)) {
							if (report != null) {
								report.setTeamprofit(parser.nextText());
							}
						}
						break;
					case XmlPullParser.END_TAG:
						String nameend = parser.getName();
						if (nameend.equalsIgnoreCase("element")&& report != null) {
							resultElement.setReport(report);
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
	public Message appendOrderDetailsUndoInfo(AppendUndo undoInfo) {
		// TODO Auto-generated method stub
		Message result = getResultAppendOrderDetailsUndo(undoInfo); 

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"
						+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE",ConstantValue.DES_PASSWORD) + "</body>";

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

}
