package com.goldenasia.lottery.net;

import java.util.Map;

/**
 * @project: TIIS
 * @Title: HttpClientUtil.java
 * @Package com.newbee.lottery.net
 * @Description: HTTP请求factory
 * @author Ace
 * @version V1.0
 */
@SuppressWarnings({ "rawtypes", "deprecation" })
public class HttpClientUtil {

	public static String doGet(String url, Map params,boolean flag) throws Exception {
		if(flag)
			return doGetCookie(url, params);
		else
			return doGetNotCookie(url, params);
	}
	
	/**
	 * @Title: doGet
	 * @author Ace
	 * @Description: doGet请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 *             String 返回
	 */
	public static String doGetNotCookie(String url, Map params) throws Exception {
		return null;
	}

	/**
	 * @Title: doGet
	 * @author Ace
	 * @Description: doGet请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 *             String 返回
	 */
	public static String doGetCookie(String url, Map params) throws Exception {return null;}
}