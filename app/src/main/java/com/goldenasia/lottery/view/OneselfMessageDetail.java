package com.goldenasia.lottery.view;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.MessageInfo;
import com.goldenasia.lottery.bean.PersonalMsg;
import com.goldenasia.lottery.engine.SettingInfoEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.MessageListElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.TitleManager;

public class OneselfMessageDetail extends BaseUI{

	private TextView title;
	private TextView detail;
	private TextView time;
	private String intentSpace = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	public OneselfMessageDetail(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (ViewGroup) View.inflate(context, R.layout.nb_lottery_message_hall_oneself_detail, null);
		
		title=(TextView)findViewById(R.id.nb_oneself_title);
		detail=(TextView)findViewById(R.id.nb_oneself_detail);
		time=(TextView)findViewById(R.id.nb_oneself_time);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onResume() {
		TitleManager.getInstance().changeSettingTitle("个人消息详情");
		getDataRequest();
		super.onResume();
	}
	
	/**
	 * 期号请求
	 */
	private void getDataRequest() {
		PersonalMsg personalMsg=new PersonalMsg();
		personalMsg.setType(bundle.getString("msgid"));
		new MyHttpTask<PersonalMsg>() {

			@Override
			protected Message doInBackground(PersonalMsg... params) {
				// 获取数据——业务的调用

				SettingInfoEngine engine = BeanFactory.getImpl(SettingInfoEngine.class);
				return engine.oneselfMessages(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						MessageListElement messageElement = (MessageListElement) result.getBody().getElements().get(0);
						List<MessageInfo> messageList=messageElement.getMessageInfoList();
						for(int i=0; i<messageList.size();i++){
							MessageInfo info=messageList.get(i);
							title.setText(info.getTitle());
							detail.setText(Html.fromHtml(intentSpace+info.getContent()));
							time.setText(info.getSendtime());
						}
						
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					// 如何提示用户
					PromptManager.showToast(context, "暂无消息");
				}

				super.onPostExecute(result);
			}
		}.executeProxy(personalMsg);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_MESSAGE_ONESELF_DETAIL;
	}

}
