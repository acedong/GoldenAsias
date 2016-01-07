package com.goldenasia.lottery.engine;

import android.util.Xml;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.bean.APIInfo;
import com.goldenasia.lottery.bean.AddUserInfo;
import com.goldenasia.lottery.bean.PersonalMsg;
import com.goldenasia.lottery.net.HttpClientUtil;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.element.BankInfoElement;
import com.goldenasia.lottery.util.DES;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class BaseSetting {
	
	/**
	 * 个人消息
	 * @return
	 */
	public JSONObject getResultOneselfMessages(PersonalMsg msgparam){
		return null;
	}
	
	/**
	 * 系统消息 列表
	 * @return
	 */
	public JSONObject getResultSystemMessages(){
		
		Map<String, String> paramsHashMap = new HashMap<String, String>();
		paramsHashMap.put("cellphone", "1");
		
		String messagePath = APIInfo.getUrlPathStr(APIInfo.API_NOTICE_LIST).toPathurl();
		try {
			initializeHttpClient();
			JSONObject jsonObject = jsonResolve(HttpClientUtil.doGet(messagePath,paramsHashMap,true));
			if(jsonObject!=null){
				return jsonObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 系统消息 详情
	 * @return
	 */
	public JSONObject getResultSystemMessagesInfo(String idstr){
		
		Map<String, String> paramsHashMap = new HashMap<String, String>();
		paramsHashMap.put("cellphone", "1");
		paramsHashMap.put("id",idstr);
		
		String messagePath = APIInfo.getUrlPathStr(APIInfo.API_NOTICE_DETAILS).toPathurl();
		try {
			initializeHttpClient();
			JSONObject jsonObject = jsonResolve(HttpClientUtil.doGet(messagePath,paramsHashMap,true));
			if(jsonObject!=null){
				return jsonObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 活动消息 详情
	 * @return
	 */
	public JSONObject getResultCampaignDetail(){
		
		Map<String, String> paramsHashMap = new HashMap<String, String>();
		paramsHashMap.put("cellphone", "1");
		
		String messagePath = APIInfo.getUrlPathStr(APIInfo.API_PROMOTION_INFO).toPathurl();
		try {
			initializeHttpClient();
			JSONObject jsonObject = jsonResolve(HttpClientUtil.doGet(messagePath,paramsHashMap,true));
			if(jsonObject!=null){
				return jsonObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 领取 详情
	 * @return
	 */
	public JSONObject postResultCampaignDetail(){return null;}
	
	public Message getResultAnalysisData(String cardNo){return null;}
	
	public Message getResultAddUserQuota(){
		
		BankInfoElement element = new BankInfoElement();
		Message message = new Message();
		String xml = message.getXml(element);
		
		Map<String, String> paramsHashMap = new HashMap<String, String>();
		paramsHashMap.put("cellphone", "1");
		
//		List<NameValuePair> nameValuePairArrayList = new ArrayList<NameValuePair>();
//		// 将传过来的参数填充到List<NameValuePair>中
//		if (paramsHashMap != null && !paramsHashMap.isEmpty()) {
//			for (Map.Entry<String, String> entry : paramsHashMap.entrySet()) {
//				nameValuePairArrayList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//			}
//		}
		
		String adduserPath = APIInfo.getUrlPathStr(APIInfo.API_ADDUSER).toPathurl();
		try {
			initializeHttpClient();

			JSONObject jsonObject = jsonResolve(HttpClientUtil.doGet(adduserPath,paramsHashMap,true));
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
								String dataNodeXml="",info="",userinfo="";
								
								if(!jsonObject.isNull("minpoint")){
									info+="<minpoint>"+jsonObject.getString("minpoint")+"</minpoint>";
								}
								
								if(!jsonObject.isNull("maxpoint")){
									info+="<maxpoint>"+jsonObject.getString("maxpoint")+"</maxpoint>";
								}
								
								if(!jsonObject.isNull("userdata")){
									JSONObject userJsonObj=jsonObject.getJSONObject("userdata");
									for(int d=0;d<userJsonObj.length();d++){
										String userdata=userJsonObj.names().getString(d);
										if(userdata.equals("maxpoint")){
											info+="<usermaxpoint>"+userJsonObj.getString(userdata)+"</usermaxpoint>";
										}else if(userdata.equals("minpoint")){
											info+="<userminpoint>"+userJsonObj.getString(userdata)+"</userminpoint>";
										}else if(userdata.equals("username")){
											info+="<username>"+userJsonObj.getString(userdata)+"</username>";
										}else{
											userinfo+=userdata+":\t"+userJsonObj.getString(userdata)+"\t";
										}
									}
									info+="<quota>"+userinfo+"</quota>";
								}
								
								if (jsonObject.getInt("errNo") == 0)
									dataNodeXml = "<errorcode>"+ jsonObject.getInt("errNo")+ "</errorcode><errormsg>成功获取</errormsg>";
								else
									dataNodeXml = "<errorcode>"+ jsonObject.getInt("errNo")+ "</errorcode><errormsg>"+ jsonObject.getString("errInfo")+ "</errormsg>";
	
								String lotteryListInfo = des.authcode(dataNodeXml+"<elements><element>"+info+"</element></elements>", "DECODE", ConstantValue.DES_PASSWORD);
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
	
	public Message getResultAddUserData(AddUserInfo user){return null;}
	
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
