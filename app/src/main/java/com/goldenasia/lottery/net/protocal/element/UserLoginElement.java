package com.goldenasia.lottery.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.goldenasia.lottery.bean.User;
import com.goldenasia.lottery.net.protocal.Element;
import com.goldenasia.lottery.net.protocal.Leaf;

/**
 * 用户登录请求
 */
public class UserLoginElement extends Element {
	private Leaf loginpass = new Leaf("loginpass");
	private User userlogin=new User();
	@Override
	public void serializerElement(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "header");
			loginpass.serializerLeaf(serializer);
			serializer.endTag(null, "header");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		return "14001";
	}

	public Leaf getLoginpass() {
		return loginpass;
	}

	public User getUserlogin() {
		return userlogin;
	}

	public void setUserlogin(User userlogin) {
		this.userlogin = userlogin;
	}

}
