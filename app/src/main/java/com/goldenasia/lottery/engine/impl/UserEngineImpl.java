package com.goldenasia.lottery.engine.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.bean.BankInfo;
import com.goldenasia.lottery.bean.BankSupportInfo;
import com.goldenasia.lottery.bean.BanksBoundCards;
import com.goldenasia.lottery.bean.BindingData;
import com.goldenasia.lottery.bean.CardLimit;
import com.goldenasia.lottery.bean.MyCardInfo;
import com.goldenasia.lottery.bean.PassWord;
import com.goldenasia.lottery.bean.ShoppingCart;
import com.goldenasia.lottery.bean.Ticket;
import com.goldenasia.lottery.bean.User;
import com.goldenasia.lottery.bean.WithdrawalsInfo;
import com.goldenasia.lottery.bean.WithdrawalsUserInfo;
import com.goldenasia.lottery.engine.BaseEngine;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.element.BalanceElement;
import com.goldenasia.lottery.net.protocal.element.BankInfoElement;
import com.goldenasia.lottery.net.protocal.element.BankInfoElements;
import com.goldenasia.lottery.net.protocal.element.BetElement;
import com.goldenasia.lottery.net.protocal.element.MyCardInfoElements;
import com.goldenasia.lottery.net.protocal.element.VersionElement;
import com.goldenasia.lottery.util.DES;

public class UserEngineImpl extends BaseEngine implements UserEngine {

	/**
	 * 用户登录 主动登录
	 * 
	 * @param user
	 */
	public Message login(User user) {

		// 如果第三步比对通过result，否则返回空
		Message result = getResultLogin(user);

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

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		} 
		return null;

	}

	/**
	 * 查看银行卡是不绑定，如果没有绑定者绑定绑行卡，有则查看余额
	 */

	/**
	 * 余额获取实现
	 */
	@Override
	public Message getBalance() {
		BalanceElement element = new BalanceElement();

		Message message = new Message();
		String xml = message.getXml(element);

		Message result = super.getResultBalance(xml);

		if (result != null) {

			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"
						+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE",ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				BalanceElement resultElement = null;

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

						// 正对于当前请求
						if ("element".equals(name)) {
							resultElement = new BalanceElement();
							result.getBody().getElements().add(resultElement);
						}

						if ("investvalue".equals(name)) {
							if (resultElement != null) {
								resultElement
										.setInvestvalues(parser.nextText());
							}
						}

						break;
					}
					eventType = parser.next();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}

	/*@Override
	public Message bet(User user) {
		BetElement element = new BetElement();
		element.getLotteryid().setTagValue(
				ShoppingCart.getInstance().getLotteryid().toString());

		// 彩票的业务里面：
		// ①关于注数的计算
		// ②关于投注信息封装（用户投注号码）

		// 010203040506|01^01020304050607|01

		StringBuffer codeBuffer = new StringBuffer();
		for (Ticket item : ShoppingCart.getInstance().getTickets()) {
			codeBuffer.append("^").append(item.getRedNum().replaceAll(" ", "")).append("|").append(item.getBlueNum().replaceAll(" ", ""));
		}

		element.getLotterycode().setTagValue(codeBuffer.substring(1));

		element.getIssue().setTagValue(ShoppingCart.getInstance().getIssue());
		element.getLotteryvalue().setTagValue((ShoppingCart.getInstance().getLotteryvalue() * 100) + "");

		element.getLotterynumber().setTagValue(ShoppingCart.getInstance().getLotterynumber().toString());
		element.getAppnumbers().setTagValue(String.valueOf(ShoppingCart.getInstance().getAppnumbers()));
		element.getIssuesnumbers().setTagValue(ShoppingCart.getInstance().getIssuesnumbers().toString());

		element.getIssueflag().setTagValue(ShoppingCart.getInstance().getIssuesnumbers() > 1 ? "1" : "0");

		Message message = new Message();
		message.getHeader().getUsername().setTagValue(user.getUsername());

		String xml = message.getXml(element);

		Message result = super.getResult(xml);

		if (result != null) {

			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容

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

				BetElement resultElement = null;

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

						// 正对于当前请求
						if ("element".equals(name)) {
							resultElement = new BetElement();
							result.getBody().getElements().add(resultElement);
						}

						if ("actvalue".equals(name)) {
							if (resultElement != null) {
								resultElement.setActvalue(parser.nextText());
							}
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
	}*/
	
	@Override
	public Message bankInfoAddCard(BanksBoundCards boundcards) {
		// TODO Auto-generated method stub
		Message result = getResultBankInfoAddCard(boundcards);

		if (result != null) {

			XmlPullParser parser = Xml.newPullParser();
			try {

				DES des = new DES();
				String body = "<body>"+ des.authcode(result.getBody().getServiceBodyInsideDESInfo(), "ENCODE", ConstantValue.DES_PASSWORD) + "</body>";

				parser.setInput(new StringReader(body));

				int eventType = parser.getEventType();
				String name;

				BankInfoElements resultElement = null;
				BankInfoElement bankInfoEle = null;
				BankInfo bankInfo = null;
				BindingData bindingData = null;
				BankSupportInfo userBankInfo = null;
				List<BankInfoElement> bankinfoEleList = new ArrayList<BankInfoElement>();
				List<BankSupportInfo> userBankInfoList = new ArrayList<BankSupportInfo>();
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
							bindingData = new BindingData();
							resultElement = new BankInfoElements();
							result.getBody().getElements().add(resultElement);
						}

						if ("bank".equals(name)) {
							if (resultElement != null) {
								bankInfo = new BankInfo();
								bankInfoEle = new BankInfoElement();
							}
						}

						if ("bankid".equals(name)) {
							if (bankInfo != null) {
								bankInfo.setBankid(parser.nextText());
							}
						}

						if ("bankname".equals(name)) {
							if (bankInfo != null) {
								bankInfo.setBankname(parser.nextText());
							}
						}

						if ("icardmaxbind".equals(name)) {
							if (bindingData != null) {
								bindingData.setiCardmaxbind(parser
										.nextText());
							}
						}

						if ("imybindcount".equals(name)) {
							if (bindingData != null) {
								bindingData.setiMyBindCount(parser
										.nextText());
							}
						}

						if ("needverify".equals(name)) {
							if (bindingData != null) {
								bindingData.setNeedverify(parser.nextText());
							}
						}

						if ("allowdifname".equals(name)) {
							if (bindingData != null) {
								bindingData.setAllowdifname(parser
										.nextText());
							}
						}

						if ("userbank".equals(name)) {
							if (resultElement != null) {
								userBankInfo = new BankSupportInfo();
							}
						}

						if ("userbankid".equals(name)) {
							if (userBankInfo != null) {
								userBankInfo.setBankid(parser.nextText());
							}
						}

						if ("userbankname".equals(name)) {
							if (userBankInfo != null) {
								userBankInfo.setBankname(parser.nextText());
							}
						}

						if ("usercardno".equals(name)) {
							if (userBankInfo != null) {
								userBankInfo.setCardno(parser.nextText());
							}
						}

						if ("userentry".equals(name)) {
							if (userBankInfo != null) {
								userBankInfo.setEntry(parser.nextText());
							}
						}
						 
						break;
					case XmlPullParser.END_TAG:
						String nameend = parser.getName();
						
						if ("bank".equals(nameend) && bankInfo != null) {
							bankInfoEle.setBankInfo(bankInfo);
							bankinfoEleList.add(bankInfoEle);
						}

						if ("userbank".equals(nameend)&& userBankInfo != null) {
							userBankInfoList.add(userBankInfo);
						}

						if ("element".equals(nameend)) {
							resultElement.setBindingData(bindingData);
							resultElement.setBankinfoEleList(bankinfoEleList);
							if (userBankInfoList.size() > 0)
								resultElement.setUserBankInfoList(userBankInfoList);
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
	public Message Logout() {
		// TODO Auto-generated method stub
		// 如果第三步比对通过result，否则返回空
		Message result = getResultLogOut();

		if (result != null) {

			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容

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

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}

	@Override
	public Message loginPassword(PassWord password) {
		// TODO Auto-generated method stub
		Message result = getResultPassword(password);

		if (result != null) {

			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容

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

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}

	@Override
	public Message fundsPassword(PassWord password) {
		// TODO Auto-generated method stub
		Message result = getResultPassword(password);

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

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}

	@Override
	public Message getWithdrawals(WithdrawalsInfo withdrawal) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Message result = getResultWithdrawals(withdrawal);

		if (result != null) {

			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容
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
				
				MyCardInfoElements resultElement=null;
				WithdrawalsUserInfo userInfo=null;
				MyCardInfo mycardInfo=null;
				List<MyCardInfo> mycardList=new ArrayList<MyCardInfo>();
				CardLimit limit=null;
				String signid="";
				List<CardLimit> cardLimit=new ArrayList<CardLimit>();
				Map<String, List<CardLimit>> cardLimitMap=new HashMap<String, List<CardLimit>>();
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
						if(withdrawal==null){
							if ("element".equals(name)) {
								resultElement = new MyCardInfoElements();
								userInfo=new WithdrawalsUserInfo();
								result.getBody().getElements().add(resultElement);
							}
							
							if("desc".equals(name)){
								if(userInfo!=null){
									userInfo.setTransferdesc(parser.nextText());
								}
							}
							
							if("withdrawtime".equals(name)){
								if(userInfo!=null){
									userInfo.setWithdrawtime(parser.nextText());
								}
							}
							
							if("bankaddtimelimit".equals(name)){
								if(userInfo!=null){
									userInfo.setBankaddtimelimit(Integer.parseInt(parser.nextText()));
								}
							}
							
							if("imyrequestoftoday".equals(name)){
								if(userInfo!=null){
									userInfo.setiMyRequestOfToday(Integer.parseInt(parser.nextText()));
								}
							}
							
							if("withdrawmax".equals(name)){
								if(userInfo!=null){
									userInfo.setWithdrawmax(Integer.parseInt(parser.nextText()));
								}
							}
							
							if("withdrawmin".equals(name)){
								if(userInfo!=null){
									userInfo.setWithdrawmin(Integer.parseInt(parser.nextText()));
								}
							}
							
							if("imaxrequestffaday".equals(name)){
								if(userInfo!=null){
									userInfo.setiMaxRequestOfAday(parser.nextText());
								}
							}
							
							if("useravailablebalance".equals(name)){
								if(userInfo!=null){
									userInfo.setUseravailablebalance(parser.nextText());
								}
							}
							
							if("iswithdrawfee".equals(name)){
								if(userInfo!=null){
									userInfo.setIswithdrawfee(parser.nextText());
								}
							}
							
							if("mycard".equals(name)){
								if(resultElement!=null){
									mycardInfo=new MyCardInfo();
								}
							}
							
							if("bankname".equals(name)){
								if(mycardInfo!=null){
									mycardInfo.setBankname(parser.nextText());
								}
							}
							if("cardno".equals(name)){
								if(mycardInfo!=null){
									mycardInfo.setCardno(parser.nextText());
								}
							}
							if("aliasname".equals(name)){
								if(mycardInfo!=null){
									mycardInfo.setAliasname(parser.nextText());
								}
							}
							if("entry".equals(name)){
								if(mycardInfo!=null){
									mycardInfo.setEntry(parser.nextText());
								}
							}
							
							if("limit".equals(name)){
								if(resultElement!=null){
									limit=new CardLimit();
								}
							}
							if("cardsign".equals(name)){
								if(limit!=null){
									signid=parser.nextText();
								}
							}
							if("min".equals(name)){
								if(limit!=null){
									limit.setMin(Integer.parseInt(parser.nextText()));
								}
							}
							if("max".equals(name)){
								if(limit!=null){
									limit.setMax(Integer.parseInt(parser.nextText()));
								}
							}
						}
						break;
					case XmlPullParser.END_TAG:
						String nameend=parser.getName();
						if(withdrawal==null){
							if("mycard".equals(nameend)&&mycardInfo!=null){
								mycardList.add(mycardInfo);
							}
							if("limit".equals(nameend)&&limit!=null){
								cardLimit.add(limit);
								cardLimitMap.put(signid, cardLimit);
							}
							if("element".equals(nameend)&&resultElement!=null){
								resultElement.setUserInfoWithdrawals(userInfo);
								resultElement.setMycardList(mycardList);
								resultElement.setCardLimitMap(cardLimitMap);
							}
						}
						break;
					}
					eventType = parser.next();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}

	@Override
	public Message Version() {
		VersionElement element = new VersionElement();

		Message message = new Message();
		String xml = message.getXml(element);

		Message result = super.getResultVersion(xml);

		if (result != null) {

			// 第四步：请求结果的数据处理
			// body部分的第二次解析，解析的是明文内容

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

				VersionElement resultElement = null;

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

						// 正对于当前请求
						if ("element".equals(name)) {
							resultElement = new VersionElement();
							result.getBody().getElements().add(resultElement);
						}

						if ("versionno".equals(name)) {
							if (resultElement != null) {
								resultElement.setVersionno(parser.nextText());
							}
						}

						break;
					}
					eventType = parser.next();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}
	
	

	@Override
	public Message takeApiPathBlog() {
		// TODO Auto-generated method stub
		Message result = getResultTakeApiPath();

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
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("errorcode".equals(name)) {
							result.getBody().getOelement()
									.setErrorcode(parser.nextText());
						}
						if ("errormsg".equals(name)) {
							result.getBody().getOelement().setErrormsg(parser.nextText());
						}

						break;
					}
					eventType = parser.next();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}

	@Override
	public Message takeApiPathTxT() {
		// TODO Auto-generated method stub
		Message result = getResultTakeApiPathTxt();
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
				while (eventType != XmlPullParser.END_DOCUMENT) {
					switch (eventType) {
					case XmlPullParser.START_TAG:
						name = parser.getName();
						if ("errorcode".equals(name)) {
							result.getBody().getOelement()
									.setErrorcode(parser.nextText());
						}
						if ("errormsg".equals(name)) {
							result.getBody().getOelement().setErrormsg(parser.nextText());
						}

						break;
					}
					eventType = parser.next();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}

}
