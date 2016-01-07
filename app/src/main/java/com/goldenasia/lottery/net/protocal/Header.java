package com.goldenasia.lottery.net.protocal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.bean.User;

/**
 * 头结点的封装
 * 
 * @author Administrator
 * 
 */
public class Header {
	private Leaf agenterid = new Leaf("agenterid", ConstantValue.AGENTERID);
	private Leaf source = new Leaf("source",ConstantValue.SOURCE);
	private Leaf compress = new Leaf("compress",ConstantValue.COMPRESS);
	private Leaf messengerid = new Leaf("messengerid");
	private Leaf timestamp = new Leaf("timestamp");
	private Leaf digest = new Leaf("digest");
	private Leaf transactiontype = new Leaf("transactiontype");
	private Leaf username = new Leaf("username");
	private Leaf loginpass=new Leaf("loginpass");
	private Leaf rempassword = new Leaf("rempassword"); 
	private Leaf autoLogin =new Leaf("autoLogin");
	private Leaf cellphone=new Leaf("cellphone"); 		//记录登录
	private Leaf parentid=new Leaf("parentid");
	private Leaf securitypwd=new Leaf("securitypwd");
	private Leaf nickname=new Leaf("nickname");
	private Leaf usertype=new Leaf("usertype");
	private Leaf isfrozen=new Leaf("isfrozen");
	private Leaf fmoney=new Leaf("fmoney");
	private Leaf fpoint=new Leaf("fpoint");
    
	private User userInfo=new User();

	/**
	 * 序列化头
	 * @param serializer
	 * @param body 完整的body（明文）
	 */
	public void serializerHeader(XmlSerializer serializer, String body) {
		// 将timestamp、messengerid、digest设置数据
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(new Date());
		timestamp.setTagValue(time);
		// messengerid：时间戳+六位的随机数
		Random random = new Random();
		int num = random.nextInt(999999) + 1;// [0, n).[1,999999]
		DecimalFormat decimalFormat = new DecimalFormat("000000");
		messengerid.setTagValue(time + decimalFormat.format(num));

		// digest:时间戳+代理商的密码+完整的body（明文）
		String orgInfo = time + ConstantValue.AGENTER_PASSWORD + body;
		String md5Hex = DigestUtils.md5Hex(orgInfo);
		digest.setTagValue(md5Hex);

		try {
			serializer.startTag(null, "header");
			agenterid.serializerLeaf(serializer);
			source.serializerLeaf(serializer);
			compress.serializerLeaf(serializer);

			messengerid.serializerLeaf(serializer);
			timestamp.serializerLeaf(serializer);
			digest.serializerLeaf(serializer);

			transactiontype.serializerLeaf(serializer);
			
			username.serializerLeaf(serializer);
			loginpass.serializerLeaf(serializer);
			cellphone.serializerLeaf(serializer);
			
			rempassword.serializerLeaf(serializer);
			autoLogin.serializerLeaf(serializer);
			
			parentid.serializerLeaf(serializer);
			securitypwd.serializerLeaf(serializer);
			nickname.serializerLeaf(serializer);
			usertype.serializerLeaf(serializer);
			isfrozen.serializerLeaf(serializer);
			fmoney.serializerLeaf(serializer);
			fpoint.serializerLeaf(serializer);
			

			serializer.endTag(null, "header");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Leaf getTransactiontype() {
		return transactiontype;
	}

	public Leaf getUsername() {
		return username;
	}

	public Leaf getAgenterid() {
		return agenterid;
	}


	public Leaf getSource() {
		return source;
	}

	public Leaf getCompress() {
		return compress;
	}

	public Leaf getMessengerid() {
		return messengerid;
	}


	public Leaf getLoginpass() {
		return loginpass;
	}


	public Leaf getCellphone() {
		return cellphone;
	}

	public Leaf getParentid() {
		return parentid;
	}


	public Leaf getSecuritypwd() {
		return securitypwd;
	}

	public Leaf getNickname() {
		return nickname;
	}

	public Leaf getFmoney() {
		return fmoney;
	}

	public Leaf getFpoint() {
		return fpoint;
	}

	/*********************处理服务器回复*************************/
	public Leaf getTimestamp() {
		return timestamp;
	}

	public Leaf getDigest() {
		return digest;
	}

	public Leaf getRempassword() {
		return rempassword;
	}

	public Leaf getAutoLogin() {
		return autoLogin;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	public Leaf getUsertype() {
		return usertype;
	}

	public Leaf getIsfrozen() {
		return isfrozen;
	}

}
