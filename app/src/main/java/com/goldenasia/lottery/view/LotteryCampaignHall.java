package com.goldenasia.lottery.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.ActivityDesc;
import com.goldenasia.lottery.bean.MessageInfo;
import com.goldenasia.lottery.engine.SettingInfoEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.ActivityDescElement;
import com.goldenasia.lottery.net.protocal.element.MessageInfoElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.TitleManager;

/**
 * 活动公告
 * @author Ace
 */
public class LotteryCampaignHall extends BaseUI {
	private LinearLayout campaignDetail;
	private TextView campaignPrompt;
	private TextView title; 
	private TextView desc; 
	private TextView detail; 
	private TextView minbet; 
	private TextView baseprize; 
	private TextView starttime; 
	private TextView endtime; 
	private Button receive;

	public LotteryCampaignHall(Context context) {
		super(context);
	}

	public void init() {
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.nb_lottery_campaign_hall, null);
		
		campaignDetail=(LinearLayout)findViewById(R.id.nb_lottery_campaign_hall_detail);
		
		campaignPrompt=(TextView)findViewById(R.id.nb_lottery_campaign_hall_prompt);

		title=(TextView)findViewById(R.id.nb_lottery_campaign_title);
		desc=(TextView)findViewById(R.id.nb_campaign_desc);
		detail=(TextView)findViewById(R.id.nb_campaign_detail);
		minbet=(TextView)findViewById(R.id.nb_campaign_minbet);
		baseprize=(TextView)findViewById(R.id.nb_campaign_baseprize);
		starttime=(TextView)findViewById(R.id.nb_campaign_starttime);
		endtime=(TextView)findViewById(R.id.nb_campaign_endtime);
		
		receive=(Button)findViewById(R.id.nb_campaign_receive);
		
	}

	@Override
	public void onResume() {
		TitleManager.getInstance().changeSettingTitle("活动公告");
		getDataRequest();
		super.onResume();
	}

	@Override
	public int getID() {
		return ConstantValue.VIEW_CAMPAIGN_HALL;
	}

	@Override
	public void setListener() {
		receive.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				postDataRequest();
			}
		});
	}
	
	/**
	 * 请求 活动信息
	 */
	private void getDataRequest() {
		new MyHttpTask<String>() {

			@Override
			protected Message doInBackground(String... params) {
				// 获取数据——业务的调用

				SettingInfoEngine engine = BeanFactory.getImpl(SettingInfoEngine.class);
				return engine.getcampaignDetail(params[0]);
			}
			
			@SuppressLint("NewApi")
			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						ActivityDescElement activityElement = (ActivityDescElement) result.getBody().getElements().get(0);
						ActivityDesc info=activityElement.getActivityDesc();
						
						title.setText(info.getTitle());
						desc.setText(info.getDesc());
						detail.setText(info.getDetail());
						minbet.setText(info.getMinbet());
						baseprize.setText(info.getBaseprize());
						starttime.setText(info.getStarttime());
						endtime.setText(info.getEndtime());
						if(info.getHavegift()==1){
							receive.setFocusable(false);
							receive.setBackground(context.getResources().getDrawable(R.drawable.nb_fillet_goldenyellow_backdrop_button));
							receive.setText("活动领取");
						}else if(info.getHavegift()==2){
							receive.setFocusable(true);
							receive.setBackground(context.getResources().getDrawable(R.drawable.nb_fillet_gray_backdrop_button));
							receive.setText("已领取");
						}
						campaignDetail.setVisibility(View.VISIBLE);
						campaignPrompt.setVisibility(View.GONE);
						
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else if(oelement.getErrorcode().equals("102")){
							PromptManager.showToast(context, oelement.getErrormsg());
							campaignDetail.setVisibility(View.GONE);
							campaignPrompt.setVisibility(View.VISIBLE);
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
		}.executeProxy("");
	}

	/**
	 * 领取信息
	 */
	private void postDataRequest() {
		new MyHttpTask<String>() {

			@Override
			protected Message doInBackground(String... params) {
				// 获取数据——业务的调用

				SettingInfoEngine engine = BeanFactory.getImpl(SettingInfoEngine.class);
				return engine.postcampaignDetail(params[0]);
			}
			
			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						
						
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
		}.executeProxy("");
	}
}
