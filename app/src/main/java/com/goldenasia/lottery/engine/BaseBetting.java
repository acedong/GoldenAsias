package com.goldenasia.lottery.engine;

import android.util.Xml;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.bean.APIInfo;
import com.goldenasia.lottery.bean.AppendUndo;
import com.goldenasia.lottery.bean.BettingInfo;
import com.goldenasia.lottery.bean.ProgramBuyInfo;
import com.goldenasia.lottery.bean.SearchParam;
import com.goldenasia.lottery.net.HttpClientUtil;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.element.AccountChangeElement;
import com.goldenasia.lottery.net.protocal.element.AppendOrderElement;
import com.goldenasia.lottery.net.protocal.element.BankInfoElement;
import com.goldenasia.lottery.net.protocal.element.OrderElement;
import com.goldenasia.lottery.util.DES;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseBetting {

	public Message getResultBettingNumber(BettingInfo bettingInfo) {return null;}

	/**
	 * 将JSON转化为数组并返回。
	 */
	public JSONArray changeJsonToArray(List<ProgramBuyInfo> programList) {
		try {
			JSONArray array = new JSONArray();
			int length = programList.size();
			for (int i = 0; i < length; i++) {
				ProgramBuyInfo program = programList.get(i);
				JSONObject stoneObject = new JSONObject();
				stoneObject.put("methodid", program.getMethodid());
				stoneObject.put("codes", program.getCodes());
				stoneObject.put("nums", program.getNums());
				stoneObject.put("times", program.getTimes());
				stoneObject.put("money", program.getMoney());
				stoneObject.put("mode", program.getMode());
				if(program.getPoint()){
					BigDecimal bd = new BigDecimal(GlobalParams.POINT);
					stoneObject.put("point",bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				}else{
					stoneObject.put("point","0");
				}
				
				
				array.put(stoneObject);
			}
			return array;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private JSONArray changeJsonToArray(String[] issues) {
		JSONArray array = new JSONArray();
		int length = issues.length;
		for (int i = 0; i < length; i++) {
			String issuesstr = issues[i];
			array.put(issuesstr);
		}
		return array;
	}
	
	/**
	 * 帐变信息
	 * @param page
	 * @return
	 */
	
	public Message getResultBillingListInfo(Integer page){
		AccountChangeElement appendOrderElement = new AccountChangeElement();
		Message message = new Message();
		String xml = message.getXml(appendOrderElement);
		
		try {
			// 初始化连接数据
			initializeHttpClient();

			Map<String, Integer> paramsBillingHashMap = new HashMap<String, Integer>();
			paramsBillingHashMap.put("cellphone", 1);
			paramsBillingHashMap.put("page", page);

			String pathBilling = APIInfo.getUrlPathStr(APIInfo.API_VARIABLE_ACCOUNTS_LIST).toPathurl();
			// 当前期数信息
			JSONObject jsonBillingInfoObj = jsonResolve(HttpClientUtil.doGet(pathBilling, paramsBillingHashMap,true));
			//{"errNo":0,"order":{"results":[],"icount":{"in":0,"out":0,"left":0},"pagecount":0,"currentpage":1}}
			if(jsonBillingInfoObj!=null)
			{
				Message result = new Message();
				DES des = new DES();
				// 第三步(代码不变)：数据的校验（MD5数据校验）
				// timestamp+digest+body
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

							String elementOrder = "", dataNodeXml = "",pageinfo="";
							
							if(!jsonBillingInfoObj.isNull("order")){
								JSONObject orderInfo=jsonBillingInfoObj.getJSONObject("order");
								if(!orderInfo.isNull("pagecount")){
									pageinfo+="<pagecount>"+orderInfo.getString("pagecount")+"</pagecount>";
								}
									
								if(!orderInfo.isNull("currentpage")){
									pageinfo+="<currentpage>"+orderInfo.getString("currentpage")+"</currentpage>";
								}
								
								if(!orderInfo.isNull("icount")){
									JSONObject icountJson =orderInfo.getJSONObject("icount");
									pageinfo+="<icount>";
									for(int i=0;i<icountJson.length();i++){
										String nameIcount=icountJson.names().getString(i);
										pageinfo+="<"+nameIcount+">"+icountJson.getString(nameIcount)+"</"+nameIcount+">";
									}
									pageinfo+="</icount>";
								}
									
								if(!orderInfo.isNull("results")){
									JSONArray results =orderInfo.getJSONArray("results");
									pageinfo+="<results>";
									for(int r=0;r<results.length();r++){
										JSONObject appendinfo=results.getJSONObject(r);
										pageinfo+="<result>";
										for(int i=0; i<appendinfo.length();i++){
											String infoName=appendinfo.names().getString(i);
											pageinfo+="<"+infoName+">"+appendinfo.getString(infoName)+"</"+infoName+">";
										}
										pageinfo+="</result>";
									}
									pageinfo+="</results>";
								}
								elementOrder="<elements><element>"+pageinfo+"</element></elements>";
							}

							// 错误，停售
							if (jsonBillingInfoObj.getInt("errNo") == 0)
								dataNodeXml = "<errorcode>"+ jsonBillingInfoObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
							else{
								dataNodeXml = "<errorcode>"+ jsonBillingInfoObj.getInt("errNo")+ "</errorcode>";
								if(!jsonBillingInfoObj.isNull("errInfo")){
									dataNodeXml+="<errormsg>"+ jsonBillingInfoObj.getString("errInfo")+ "</errormsg>";
								}
							}
							
							String currentIssue = des.authcode(dataNodeXml+elementOrder, "DECODE",ConstantValue.DES_PASSWORD);
							result.getBody().setServiceBodyInsideDESInfo(currentIssue);
						}
						break;
					}
					eventType = parser.next();
				}
				return result;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	
	public Message getResultAppendOrderList(Integer page){
		AppendOrderElement appendOrderElement = new AppendOrderElement();
		Message message = new Message();
		String xml = message.getXml(appendOrderElement);
		
		try {
			// 初始化连接数据
			initializeHttpClient();

			Map<String, Integer> paramsOrderHashMap = new HashMap<String, Integer>();
			paramsOrderHashMap.put("cellphone", 1);
			paramsOrderHashMap.put("page", page);

			String pathAppendOrder = APIInfo.getUrlPathStr(APIInfo.API_ADDITIONAL_LIST).toPathurl();
			// 当前期数信息
			JSONObject jsonAppendOrderInfoObj = jsonResolve(HttpClientUtil.doGet(pathAppendOrder, paramsOrderHashMap,true));
			//{"project":{"pagecount":1,"currentpage":2},"errNo":0}
			if(jsonAppendOrderInfoObj!=null)
			{
				Message result = new Message();
				DES des = new DES();
				// 第三步(代码不变)：数据的校验（MD5数据校验）
				// timestamp+digest+body
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

							String elementOrder = "", dataNodeXml = "",pageinfo="";
							
							if(!jsonAppendOrderInfoObj.isNull("task")){
								JSONObject taskInfo=jsonAppendOrderInfoObj.getJSONObject("task");
								if(!taskInfo.isNull("pagecount")){
									pageinfo+="<pagecount>"+taskInfo.getString("pagecount")+"</pagecount>";
								}
									
								if(!taskInfo.isNull("currentpage")){
									pageinfo+="<currentpage>"+taskInfo.getString("currentpage")+"</currentpage>";
								}
									
								if(!taskInfo.isNull("results")){
									JSONArray results =taskInfo.getJSONArray("results");
									pageinfo+="<results>";
									for(int r=0;r<results.length();r++){
										JSONObject appendinfo=results.getJSONObject(r);
										pageinfo+="<result>";
										for(int i=0; i<appendinfo.length();i++){
											String infoName=appendinfo.names().getString(i);
											if("title".equals(infoName)){
												pageinfo+="<title>"+appendinfo.getString(infoName)+"</title>";
											}
											if("taskid".equals(infoName)){
												pageinfo+="<taskid>"+appendinfo.getString(infoName)+"</taskid>";
											}
											if("taskprice".equals(infoName)){
												pageinfo+="<taskprice>"+appendinfo.getString(infoName)+"</taskprice>";
											}
											if("finishprice".equals(infoName)){
												pageinfo+="<finishprice>"+appendinfo.getString(infoName)+"</finishprice>";
											}
											if("begintime".equals(infoName)){
												pageinfo+="<begintime>"+appendinfo.getString(infoName)+"</begintime>";
											}
											if("modes".equals(infoName)){
												pageinfo+="<modes>"+appendinfo.getString(infoName)+"</modes>";
											}
											if("lotteryname".equals(infoName)){
												pageinfo+="<lotteryname>"+appendinfo.getString(infoName)+"</lotteryname>";
											}
											if("status".equals(infoName)){
												pageinfo+="<status>"+appendinfo.getString(infoName)+"</status>";
											}
										}
										pageinfo+="</result>";
									}
									pageinfo+="</results>";
								}
								elementOrder="<elements><element>"+pageinfo+"</element></elements>";
							}

							// 错误，停售
							if (jsonAppendOrderInfoObj.getInt("errNo") == 0)
								dataNodeXml = "<errorcode>"+ jsonAppendOrderInfoObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
							else{
								dataNodeXml = "<errorcode>"+ jsonAppendOrderInfoObj.getInt("errNo")+ "</errorcode>";
								if(!jsonAppendOrderInfoObj.isNull("errInfo")){
									dataNodeXml+="<errormsg>"+ jsonAppendOrderInfoObj.getString("errInfo")+ "</errormsg>";
								}
							}
							
							String currentIssue = des.authcode(dataNodeXml+elementOrder, "DECODE",ConstantValue.DES_PASSWORD);
							result.getBody().setServiceBodyInsideDESInfo(currentIssue);
						}
						break;
					}
					eventType = parser.next();
				}
				return result;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	public JSONObject getResultAppendOrderDetails(String appendId){
		
		Map<String, String> paramsOrderHashMap = new HashMap<String, String>();
		paramsOrderHashMap.put("cellphone", "1");
		paramsOrderHashMap.put("id", appendId);
		try {
			// 初始化连接数据
			initializeHttpClient();
			String pathAppendOrder = APIInfo.getUrlPathStr(APIInfo.API_ADDITIONAL_DETAILS).toPathurl();
			JSONObject jsonAppendOrderInfoObj = jsonResolve(HttpClientUtil.doGet(pathAppendOrder, paramsOrderHashMap,true));
			
			if(jsonAppendOrderInfoObj!=null){
				return jsonAppendOrderInfoObj;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	
	public Message getResultOrderList(Integer page)
	{
		OrderElement orderElement = new OrderElement();

		Message message = new Message();
		String xml = message.getXml(orderElement);
		
		try {
			// 初始化连接数据
			initializeHttpClient();

			Map<String, Integer> paramsOrderHashMap = new HashMap<String, Integer>();
			paramsOrderHashMap.put("cellphone", 1);
			paramsOrderHashMap.put("page", page);

			String pathOrder = APIInfo.getUrlPathStr(APIInfo.API_BETTING_HISTORY).toPathurl();
			// 当前期数信息
			JSONObject jsonOrderInfoObj = jsonResolve(HttpClientUtil.doGet(pathOrder, paramsOrderHashMap,true));
			//{"project":{"pagecount":1,"currentpage":2},"errNo":0}
			if(jsonOrderInfoObj!=null)
			{
				Message result = new Message();
				DES des = new DES();
				// 第三步(代码不变)：数据的校验（MD5数据校验）
				// timestamp+digest+body
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

							String elementOrder = "", dataNodeXml = "";
							
							if(!jsonOrderInfoObj.isNull("project")){
								elementOrder+="<elements><element><orders>";
								JSONObject jsonOrderProjectObj = jsonOrderInfoObj.getJSONObject("project");
								
								String pagecount=jsonOrderProjectObj.getString("pagecount");	//总页数
								String currentpage=jsonOrderProjectObj.getString("currentpage"); //页数
								
								if(!jsonOrderProjectObj.isNull("results")){
									JSONArray jsonOrderResultsArray= jsonOrderProjectObj.getJSONArray("results");
									for(int arr=0; arr<jsonOrderResultsArray.length(); arr++){
										JSONObject jsonOrderResultsObj = jsonOrderResultsArray.getJSONObject(arr);
										elementOrder += "<order>";
										for(int or=0; or<jsonOrderResultsObj.length(); or++){
											String nameRes= jsonOrderResultsObj.names().getString(or); // 取json method ID
											String valueRes = jsonOrderResultsObj.getString(nameRes);
											elementOrder += "<"+nameRes+">"+ valueRes + "</"+nameRes+">";
										}
										elementOrder += "</order>";
									}
								}
								elementOrder+="</orders></element></elements>";
							}

							// 错误，停售
							if (jsonOrderInfoObj.getInt("errNo") == 0)
								dataNodeXml = "<errorcode>"+ jsonOrderInfoObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
							else{
								dataNodeXml = "<errorcode>"+ jsonOrderInfoObj.getInt("errNo")+ "</errorcode>";
								if(!jsonOrderInfoObj.isNull("errInfo")){
									dataNodeXml+="<errormsg>"+ jsonOrderInfoObj.getString("errInfo")+ "</errormsg>";
								}
							}
							
							String currentIssue = des.authcode(dataNodeXml+elementOrder, "DECODE",
									ConstantValue.DES_PASSWORD);
							result.getBody().setServiceBodyInsideDESInfo(currentIssue);
						}
						break;
					}
					eventType = parser.next();
				}
				return result;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	
	public Message getResultOrderDetailsInfo(Integer detailsid)
	{
		OrderElement orderElement = new OrderElement();

		Message message = new Message();
		String xml = message.getXml(orderElement);
		
		try {
			// 初始化连接数据
			initializeHttpClient();

			Map<String, Integer> paramsOrderHashMap = new HashMap<String, Integer>();
			paramsOrderHashMap.put("cellphone", 1);
			paramsOrderHashMap.put("id", detailsid);
			
			String pathOrderDetails = APIInfo.getUrlPathStr(APIInfo.API_BETTING_DETAILS).toPathurl();
			// 当前期数信息
			JSONObject jsonOrderDetailsObj = jsonResolve(HttpClientUtil.doGet(pathOrderDetails, paramsOrderHashMap,true));
			if(jsonOrderDetailsObj!=null)
			{
				Message result = new Message();
				DES des = new DES();
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

							String elementOrder = "", dataNodeXml = "";
							
							if(!jsonOrderDetailsObj.isNull("project")){
								JSONObject jsonDetailsProjectObj = jsonOrderDetailsObj.getJSONObject("project");
								
								elementOrder += "<details>";
								for(int or=0; or<jsonDetailsProjectObj.length(); or++){
									String nameRes= jsonDetailsProjectObj.names().getString(or); // 取json method ID
									elementOrder += "<"+nameRes+">"+ jsonDetailsProjectObj.getString(nameRes) + "</"+nameRes+">";
								}
								elementOrder += "</details>";
							}
							

							// 错误，停售
							if (jsonOrderDetailsObj.getInt("errNo") == 0)
								dataNodeXml = "<errorcode>"+ jsonOrderDetailsObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
							else
								dataNodeXml = "<errorcode>"+ jsonOrderDetailsObj.getInt("errNo")+ "</errorcode><errormsg>"+ jsonOrderDetailsObj.getString("errInfo")+ "</errormsg>";
							
							String currentIssue = des.authcode(dataNodeXml+"<elements><element>"+ elementOrder + "</element></elements>", "DECODE", ConstantValue.DES_PASSWORD);
							result.getBody().setServiceBodyInsideDESInfo(currentIssue);
						}
						break;
					}
					eventType = parser.next();
				}
				return result;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 取消订单
	 * @param projectid
	 * @return
	 */
	
	public Message getResultOrderCancel(Integer projectid){
		
		OrderElement orderElement = new OrderElement();

		Message message = new Message();
		String xml = message.getXml(orderElement);
		
		try {
			initializeHttpClient();

			Map<String, Integer> paramsOrderHashMap = new HashMap<String, Integer>();
			paramsOrderHashMap.put("cellphone", 1);
			paramsOrderHashMap.put("id", projectid);
			
			String pathOrderCancel = APIInfo.getUrlPathStr(APIInfo.API_REVOCATION).toPathurl();
			JSONObject jsonOrderCancelObj = jsonResolve(HttpClientUtil.doGet(pathOrderCancel, paramsOrderHashMap,true));
			if(jsonOrderCancelObj!=null)
			{
				Message result = new Message();
				DES des = new DES();
				// 第三步(代码不变)：数据的校验（MD5数据校验）
				// timestamp+digest+body
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

							String dataNodeXml = "";
							
							// 错误，停售
							if (jsonOrderCancelObj.getInt("errNo") == 0)
								dataNodeXml = "<errorcode>"+ jsonOrderCancelObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
							else
								dataNodeXml = "<errorcode>"+ jsonOrderCancelObj.getInt("errNo")+ "</errorcode><errormsg>"+ jsonOrderCancelObj.getString("info")+ "</errormsg>";
							
							String currentIssue = des.authcode(dataNodeXml, "DECODE",
									ConstantValue.DES_PASSWORD);
							result.getBody().setServiceBodyInsideDESInfo(currentIssue);
						}
						break;
					}
					eventType = parser.next();
				}
				return result;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	public Message getResultProfitStatisticsInfo(SearchParam param){
		
		BankInfoElement element = new BankInfoElement();
		Message message = new Message();
		String xml = message.getXml(element);
		
		Map<String, String> paramsHashMap = new HashMap<String, String>();
		paramsHashMap.put("cellphone", param.getCellphone());
		paramsHashMap.put("startdate",param.getStartdate());
		paramsHashMap.put("enddate",param.getEnddate());
		
		String path = APIInfo.getUrlPathStr(APIInfo.API_REPORT).toPathurl();
		try {
			initializeHttpClient();

			JSONObject jsonObject = jsonResolve(HttpClientUtil.doGet(path,paramsHashMap,true));
			if(jsonObject!=null){
				Message result = new Message();
				DES des = new DES();
				// 第三步(代码不变)：数据的校验（MD5数据校验）
				// timestamp+digest+body
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
								
								if(!jsonObject.isNull("reportdata")){
									JSONObject reportJsonObj=jsonObject.getJSONObject("reportdata");
									for(int i=0; i<reportJsonObj.length();i++){
										String reportName=reportJsonObj.names().getString(i);
										
										if(reportName.equals("profit")){
											double reportval=reportJsonObj.getDouble(reportName)-0.0002;
											info+="<profit>"+reportval+"</profit>";
										}else if(reportName.equals("teamprofit")){
											double reportval=reportJsonObj.getDouble(reportName)-0.0002;
											info+="<teamprofit>"+reportval+"</teamprofit>";
										}else{
											double reportval=reportJsonObj.getDouble(reportName)-0.0001;
											info+="<"+reportName+">"+reportval+"</"+reportName+">";
										}
									}
									info="<elements><element>"+info+"</element></elements>";
								}
								
								if (jsonObject.getInt("errNo") == 0)
									dataNodeXml = "<errorcode>"+ jsonObject.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
								else
									dataNodeXml = "<errorcode>"+ jsonObject.getInt("errNo")+ "</errorcode><errormsg>"+ jsonObject.getString("errInfo")+ "</errormsg>";
	
								String lotteryListInfo = des.authcode(dataNodeXml+info, "DECODE", ConstantValue.DES_PASSWORD);
								result.getBody().setServiceBodyInsideDESInfo(lotteryListInfo);
							}
							break;
					}
					eventType = parser.next();
				}
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Message getResultAppendOrderDetailsUndo(AppendUndo undoInfo){return null;}
	

	/**
	 * @Title: initializeHttpClient
	 * @author Ace
	 * @Description: 初始连接信息
	 * @throws Exception
	 */
	private void initializeHttpClient() throws Exception {
		//HttpClientUtil.getHttpClient();
	}

	/**
	 * @Title: jsonResolve
	 * @author Ace
	 * @Description: json信息
	 * @throws Exception
	 */
	private JSONObject jsonResolve(String jsonstr) throws JSONException {
		JSONTokener jsonParser = new JSONTokener(jsonstr);
		JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
		return jsonObject;
	}
}
