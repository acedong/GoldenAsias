package com.goldenasia.lottery.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

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
import com.goldenasia.lottery.view.adapter.OneselfMessageAdapter;
import com.goldenasia.lottery.view.adapter.SystemMessageAdapter;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

/**
 * 系统消息 与 个人消息
 * @author Ace
 *
 */

public class MessageHall extends BaseUI{
	private OneselfMessageAdapter oneselfAdapter;
	private SystemMessageAdapter systemAdapter;
	private ViewPager viewPager;//页卡内容
	private Button messagegoback,oneselfTitle,systemTitle;
	private List<View> views;	// Tab页面列表
	private int offset = 0;		// 动画图片偏移量
	private int currIndex = 0;	// 当前页卡编号
	private int bmpW;			// 动画图片宽度

	public MessageHall(Context context) {
		super(context);
	}

	public void init() {
		showInMiddle = (LinearLayout) View.inflate(context, R.layout.nb_lottery_message_hall, null);
		InitTextView();
		InitViewPager();
	}

	@Override
	public void onResume() {
		int typeinfo=bundle.getInt("typeinfo");
		switch (typeinfo) {
			case 0:
				getOneselfMessageInfo();
				viewPager.setCurrentItem(0);
				break;
			case 1:
				getSystemMessageInfo();
				viewPager.setCurrentItem(1);
				break;
		}
		
		super.onResume();
	}

	@Override
	public int getID() {
		return ConstantValue.VIEW_MESSAGE;
	}

	@Override
	public void setListener() {
		
	}
	
	private void InitViewPager() {
		viewPager=(ViewPager) findViewById(R.id.nb_message_pagerview);
		views=new ArrayList<View>();
		
		ListView oneselfList = new ListView(context);
		oneselfList.setDividerHeight(1);
		oneselfList.setDivider(new ColorDrawable(R.color.black));
		oneselfAdapter=new OneselfMessageAdapter(context);
		oneselfList.setAdapter(oneselfAdapter);
		oneselfList.setFadingEdgeLength(0);// 删除黑边（上下）
		
		ListView systemList = new ListView(context);
		systemList.setDividerHeight(1);
		systemList.setDivider(new ColorDrawable(R.color.black));
		systemAdapter=new SystemMessageAdapter(context);
		systemList.setAdapter(systemAdapter);
		systemList.setFadingEdgeLength(0);// 删除黑边（上下）
		
		views.add(oneselfList);
		views.add(systemList);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	 /**
	  *  初始化头标
	  */

	private void InitTextView() {
		messagegoback = (Button) findViewById(R.id.nb_message_goback);
		String resGoBackIco=context.getResources().getString(R.string.fa_chevron_left);
		messagegoback.setText(resGoBackIco);
		messagegoback.setTypeface(font);
		messagegoback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HallMiddleManager.getInstance().goBack();
			}
		});
		
		oneselfTitle = (Button) findViewById(R.id.nb_message_oneself_hall);
		systemTitle = (Button) findViewById(R.id.nb_system_hall);

		oneselfTitle.setOnClickListener(new MyOnClickListener(0));
		systemTitle.setOnClickListener(new MyOnClickListener(1));
	}

	/** 
	 *     
	 * 头标点击监听 3 */
	private class MyOnClickListener implements OnClickListener{
        private int index=0;
        public MyOnClickListener(int i){
        	index=i;
        }
		public void onClick(View v) {
			viewPager.setCurrentItem(index);			
		}
		
	}
	
	public class MyViewPagerAdapter extends PagerAdapter{
		private List<View> mListViews;
		
		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) 	{	
			container.removeView(mListViews.get(position));
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {			
			 container.addView(mListViews.get(position), 0);
			 return mListViews.get(position);
		}

		@Override
		public int getCount() {			
			return  mListViews.size();
		}
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {			
			return arg0==arg1;
		}
	}

    public class MyOnPageChangeListener implements OnPageChangeListener{

    	int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		public void onPageScrollStateChanged(int arg0) {
			
			
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
			
		}

		public void onPageSelected(int position) {
			/*
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
				
			}
			*/
			switch (position) {
				case 0:
					oneselfTitle.setTextColor(Color.parseColor("#df3031"));
					systemTitle.setTextColor(Color.WHITE);
					oneselfTitle.setBackgroundResource(R.drawable.baike_btn_pink_left_f_96);
					systemTitle.setBackgroundResource(R.drawable.baike_btn_trans_right_f_96);
					getOneselfMessageInfo();
					break;
				case 1:
					oneselfTitle.setTextColor(Color.WHITE);
					systemTitle.setTextColor(Color.parseColor("#df3031"));
					oneselfTitle.setBackgroundResource(R.drawable.baike_btn_trans_left_f_96);
					systemTitle.setBackgroundResource(R.drawable.baike_btn_pink_right_f_96);
					getSystemMessageInfo();
					break;
			}
		}
    	
    }
	
	/**
	 * 获取当前个人消息
	 */
	private void getOneselfMessageInfo() {
		PersonalMsg personalMsg=new PersonalMsg();
		personalMsg.setType("0");
		new MyHttpTask<PersonalMsg>() {

			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}
			
			@Override
			protected Message doInBackground(PersonalMsg... params) {
				// 获取数据——业务的调用

				SettingInfoEngine engine = BeanFactory.getImpl(SettingInfoEngine.class);
				return engine.oneselfMessages(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						MessageListElement messageElement = (MessageListElement) result.getBody().getElements().get(0);
						List<MessageInfo> messageList=messageElement.getMessageInfoList();
						oneselfAdapter.refresh(messageList);
						
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
	
	/**
	 * 获取当前系统消息
	 */
	private void getSystemMessageInfo() {
		new MyHttpTask<String>() {

			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}
			
			@Override
			protected Message doInBackground(String... params) {
				// 获取数据——业务的调用
				SettingInfoEngine engine = BeanFactory.getImpl(SettingInfoEngine.class);
				return engine.systemMessages();
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				PromptManager.closeProgressDialog();
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();

					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						MessageListElement messageElement = (MessageListElement) result.getBody().getElements().get(0);
						List<MessageInfo> messageList=messageElement.getMessageInfoList();
						systemAdapter.refresh(messageList);
						
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
		}.executeProxy("0");
	}

}
