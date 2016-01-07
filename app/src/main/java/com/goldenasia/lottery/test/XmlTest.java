package com.goldenasia.lottery.test;

import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.test.AndroidTestCase;
import android.util.Log;
import android.util.Xml;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.bean.User;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.element.CurrentIssueElement;
import com.goldenasia.lottery.util.BeanFactory;

public class XmlTest extends AndroidTestCase {
	private static final String TAG = "XmlTest";

	public void createXMl() {
		Message message = new Message();
		CurrentIssueElement element = new CurrentIssueElement();
		element.getLotteryids().setTagValue("118");
		String xml = message.getXml(element);
		Log.i(TAG, xml);
	}

	public void createXMl2() {
		// 序列化
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		// This method can only be called just after setOutput
		try {
			serializer.setOutput(writer);
			serializer.startDocument(ConstantValue.ENCONDING, null);

			Message message = new Message();
			message.serializerMessage(serializer);

			serializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void createXMl1() {
		// 序列化
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		// This method can only be called just after setOutput
		try {
			serializer.setOutput(writer);
			serializer.startDocument(ConstantValue.ENCONDING, null);

			serializer.startTag(null, "message");
			serializer.startTag(null, "header");

			serializer.startTag(null, "agenterid");
			serializer.text(ConstantValue.AGENTERID);
			serializer.endTag(null, "agenterid");

			serializer.startTag(null, "agenterid");
			serializer.text(ConstantValue.AGENTERID);
			serializer.endTag(null, "agenterid");

			serializer.startTag(null, "agenterid");
			serializer.text(ConstantValue.AGENTERID);
			serializer.endTag(null, "agenterid");

			serializer.endTag(null, "header");
			serializer.startTag(null, "body");
			serializer.endTag(null, "body");
			serializer.endTag(null, "message");

			serializer.endDocument();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 存在问题：
		// 项目组形势开发：5人，100请求

		// 无法预估
		// ①开发、维护成本高——代码冗余，极易出错
		// ②交接代码：开发、沟通成本
		// ③生成了五个封装协议的版本代码
		// ④协议变更了

		// 预估 :
		// 学习协议：5*0.5天
		// 学习协议结果的交流：5*0.5天

		// 风险管理——规避风险、时间影响最小化

		// 单独抽一人学习协议，抽取出一个协议封装的版本（公共）
		// 协议 不同之处需要交由其他开发人员处理

	}

	public void testUserLogin() {
		// UserEngineImpl impl=new UserEngineImpl();
		// UserEngineImpl1
		// User user=new User();
		// user.setUsername("13200000000");
		// user.setPassword("0000000");
		// Message login = impl.login(user);
		// Log.i(TAG, login.getBody().getOelement().getErrorcode());

		UserEngine engine = BeanFactory.getImpl(UserEngine.class);

		User user = new User();
		user.setUsername("13200000000");
		user.setPassword("0000000");
		Message login = engine.login(user);
		Log.i(TAG, login.getBody().getOelement().getErrorcode());
	}
}
