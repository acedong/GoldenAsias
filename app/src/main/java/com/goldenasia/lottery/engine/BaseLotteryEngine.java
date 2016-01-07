package com.goldenasia.lottery.engine;

import android.util.Xml;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.bean.APIInfo;
import com.goldenasia.lottery.net.HttpClientUtil;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.element.HistoryListElements;
import com.goldenasia.lottery.util.DES;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 彩票开奖信息
 * @author Ace
 */

public abstract class BaseLotteryEngine {

	/**
	 * 彩种开奖信息 列表 前三个
	 * @param xml
	 * @return
	 */
	
	public Message getRecentLotteryMethod(String xml){
		
		try {
			// 初始化连接数据
			initializeHttpClient();
			
			Map<String, String> paramsHashMap = new HashMap<String, String>();
			paramsHashMap.put("cellphone", "1");

			String playPath = APIInfo.getUrlPathStr(APIInfo.API_PLAYMETHOD).toPathurl();
			JSONObject jsonObject = jsonResolve(HttpClientUtil.doGet(playPath, paramsHashMap,true));
			
			String jsoncontent="";
			if(jsonObject!=null){
				JSONObject jsonlotteryObject = jsonObject.getJSONObject("lottery");
				jsoncontent+="{";
				jsoncontent+="\"lottery\":"+jsonlotteryObject+",";
				
				String loName="";
				for (int i = 0; i < jsonlotteryObject.length(); i++) {
					String lotteryid = jsonlotteryObject.names().getString(i);
					loName+=lotteryid+",";
				}
				loName=loName.substring(0,loName.length()-1)+"";
				Map<String, String> paramsHistoryHashMap = new HashMap<String, String>();
				paramsHistoryHashMap.put("cellphone", "1");
				paramsHistoryHashMap.put("lotteryid", loName.toString());

				String pathHistory = APIInfo.getUrlPathStr(APIInfo.API_HISTORY_LOTTERY_CODE).toPathurl();
				JSONObject jsonHistoryLotteryInfoObj = jsonResolve(HttpClientUtil.doGet(pathHistory, paramsHistoryHashMap,true));
				
				if(jsonHistoryLotteryInfoObj!=null)
				{
					JSONObject jsonHistoryObject = jsonHistoryLotteryInfoObj.getJSONObject("allcode");
					jsoncontent+="\"allcode\":"+jsonHistoryObject+",";
				}
				jsoncontent+="\"errNo\":"+jsonObject.getString("errNo");
			}
			jsoncontent+="}";
			
			JSONObject jsoncontentObject=new JSONObject(jsoncontent); 
			if(jsoncontentObject!=null)
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
							
							String elementLottery = "", dataNodeXml = "" , lotterys= "", allcode="" ;
							//彩票种类数据
							
							if(!jsoncontentObject.isNull("lottery")){
								JSONObject jsonLotteryObj = jsoncontentObject.getJSONObject("lottery");
								lotterys ="<lotterys>";
								for(int lo=0;lo<jsonLotteryObj.length();lo++)
								{
									String lotteryId = jsonLotteryObj.names().getString(lo); // 取json method ID
									lotterys += "<lottery><lotteryid>"+ lotteryId + "</lotteryid>";
									JSONObject jsonLotteryInfo=jsonLotteryObj.getJSONObject(lotteryId);
									for(int i=0;i<jsonLotteryInfo.length();i++){
										String infoName=jsonLotteryInfo.names().getString(i);
										lotterys +="<"+infoName+">"+ jsonLotteryInfo.getString(infoName) + "</"+infoName+">";
									}
									lotterys +="</lottery>";
								}
								lotterys +="</lotterys>";
							}	
							
							if(!jsoncontentObject.isNull("allcode")){
								JSONObject jsonAllCodeObj= jsoncontentObject.getJSONObject("allcode");
								allcode += "<historyissues>";
								for(int d=0; d<jsonAllCodeObj.length(); d++){
									String lotterycodeId = jsonAllCodeObj.names().getString(d);
										allcode += "<issues><codeid>"+lotterycodeId+"</codeid>";
										JSONObject codeInfoObj=jsonAllCodeObj.getJSONObject(lotterycodeId);
										for(int info=0; info<codeInfoObj.length();info++){
											String codeName=codeInfoObj.names().getString(info);
											if(codeName.equals("code")){
												JSONObject codeIssue=codeInfoObj.getJSONObject(codeName);
												for(int n=0;n<codeIssue.length();n++){
													String issueName=codeIssue.names().getString(n);
													allcode +="<code><issuename>"+issueName+"</issuename><issuecode>"+codeIssue.getString(issueName)+"</issuecode></code>";
												}
											}
											if(codeName.equals("sorts")){
												allcode+="<"+codeName+"code>"+codeInfoObj.getString(codeName)+"</"+codeName+"code>";
											}
											
										}
									allcode += "</issues>";
									
								}
								allcode += "</historyissues>";
								
								elementLottery="<elements><element>"+ lotterys + allcode + "</element></elements>";
							}
							
							
							
							
							// 错误，停售
							if (jsoncontentObject.getInt("errNo") == 0)
								dataNodeXml += "<errorcode>"+ jsoncontentObject.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
							else
								dataNodeXml += "<errorcode>"+ jsoncontentObject.getInt("errNo")+ "</errorcode><errormsg>"+ jsoncontentObject.getString("errInfo")+ "</errormsg>";

							
							String lotteryListInfo = des.authcode(dataNodeXml+elementLottery, "DECODE", ConstantValue.DES_PASSWORD);
							result.getBody().setServiceBodyInsideDESInfo(lotteryListInfo);
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
	
	public Message getRecentSingleLotteryInfo(Integer integer)
	{
		HistoryListElements historyElement=new HistoryListElements();
		Message message = new Message();
		String xml = message.getXml(historyElement);
		
		try {
			// 初始化连接数据
			initializeHttpClient();
					
			Map<String, String> paramsHistoryHashMap = new HashMap<String, String>();
			paramsHistoryHashMap.put("cellphone", "1");
			paramsHistoryHashMap.put("lotteryid", String.valueOf(integer));

			String pathHistory = APIInfo.getUrlPathStr(APIInfo.API_HISTORY_LOTTERY_CODE).toPathurl();
			JSONObject jsonHistoryLotteryInfoObj = jsonResolve(HttpClientUtil.doGet(pathHistory, paramsHistoryHashMap,true));
					
			if(jsonHistoryLotteryInfoObj!=null)
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
							
							String elementLottery = "", dataNodeXml = "",allcode="";
							//彩票种类数据
							
							if(!jsonHistoryLotteryInfoObj.isNull("allcode")){
								JSONObject jsonAllCodeObj= jsonHistoryLotteryInfoObj.getJSONObject("allcode");
								for(int d=0; d<jsonAllCodeObj.length(); d++){
									String lotterycodeId = jsonAllCodeObj.names().getString(d);
									allcode += "<issues><codeid>"+lotterycodeId+"</codeid>";
									JSONObject codeInfoObj=jsonAllCodeObj.getJSONObject(lotterycodeId);
									for(int info=0; info<codeInfoObj.length();info++){
										String codeName=codeInfoObj.names().getString(info);
										if(codeName.equals("code")){
											JSONObject codeIssue=codeInfoObj.getJSONObject(codeName);
											for(int n=0;n<codeIssue.length();n++){
												String issueName=codeIssue.names().getString(n);
												allcode +="<code><issuename>"+issueName+"</issuename><issuecode>"+codeIssue.getString(issueName)+"</issuecode></code>";
											}
										}
										if(codeName.equals("sorts")){
											allcode+="<"+codeName+"code>"+codeInfoObj.getString(codeName)+"</"+codeName+"code>";
										}
									}
									allcode += "</issues>";
									
								}
								elementLottery="<elements><element>"+ allcode + "</element></elements>";
							}
										
							// 错误，停售
							if (jsonHistoryLotteryInfoObj.getInt("errNo") == 0)
								dataNodeXml = "<errorcode>"+ jsonHistoryLotteryInfoObj.getInt("errNo")+ "</errorcode><errormsg>Success</errormsg>";
							else{
								dataNodeXml = "<errorcode>"+ jsonHistoryLotteryInfoObj.getInt("errNo")+ "</errorcode>";
								if(!jsonHistoryLotteryInfoObj.isNull("errInfo")){
									dataNodeXml += "<errormsg>"+ jsonHistoryLotteryInfoObj.getString("errInfo")+ "</errormsg>";
								}
							}

							String lotteryListInfo = des.authcode(dataNodeXml+elementLottery, "DECODE", ConstantValue.DES_PASSWORD);
							result.getBody().setServiceBodyInsideDESInfo(lotteryListInfo);
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
	 * @Title: initializeHttpClient
	 * @author Ace
	 * @Description: 初始连接信息
	 * @throws Exception
	 */
	private void initializeHttpClient() throws Exception {

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
