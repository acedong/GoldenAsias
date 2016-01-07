package com.goldenasia.lottery.view;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.ConstantInformation;
import com.goldenasia.lottery.bean.IssueAll;
import com.goldenasia.lottery.bean.IssueLast;
import com.goldenasia.lottery.bean.IssueSales;
import com.goldenasia.lottery.bean.LucreMode;
import com.goldenasia.lottery.bean.LucreModeList;
import com.goldenasia.lottery.bean.PlayMenu;
import com.goldenasia.lottery.bean.RecordTime;
import com.goldenasia.lottery.bean.ShoppingCart;
import com.goldenasia.lottery.bean.Ticket;
import com.goldenasia.lottery.engine.CommonInfoEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.TaskIssueInfoElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.Calculation;
import com.goldenasia.lottery.util.CustomDialog;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.TwoPoolAdapter;
import com.goldenasia.lottery.view.controls.Anticlockwise;
import com.goldenasia.lottery.view.controls.RushBuyCountDownTimerView;
import com.goldenasia.lottery.view.custom.MyTwoGridView;
import com.goldenasia.lottery.view.custom.MyTwoGridView.OnActionUpListener;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.BottomManager;
import com.goldenasia.lottery.view.manager.HallMiddleManager;
import com.goldenasia.lottery.view.manager.PlayGame;
import com.goldenasia.lottery.view.manager.PlayGameMethod;
import com.goldenasia.lottery.view.manager.TitleManager;
import com.goldenasia.lottery.view.sensor.ShakeListener;

/**
 * 十一选五
 * @author Ace
 *
 */

@SuppressLint({ "FieldGetter", "UseValueOf" })
public class PlaySYFive extends BaseUI implements PlayGame, PlayGameMethod {
	
	public PlaySYFive(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private TextView randomRed;
//	private Anticlockwise mTimer;
	private RushBuyCountDownTimerView salestime;
	private RushBuyCountDownTimerView mopenTimer;
	private TextView playLotteryName;
	private TextView issueNumber;
	private TextView issueSales;
	private TextView assembleTitle;
	
	private TextView playBottomNotice;				//底部标题
	private Button gamePlusButton;
	private Button gameMinusButton;

	private LinearLayout oneFaceContainer;
	private LinearLayout twoFaceContainer;
	private LinearLayout threeFaceContainer;
	private LinearLayout assembleFaceContainer;

	// 选号容器
	private MyTwoGridView oneContainer;
	private MyTwoGridView twoContainer;
	private MyTwoGridView threeContainer;
	private MyTwoGridView assembleContainer;

	private TwoPoolAdapter oneAdapter;
	private TwoPoolAdapter twoAdapter;
	private TwoPoolAdapter threeAdapter;
	private TwoPoolAdapter assembleAdapter;

	private List<Integer> oneNums;
	private List<Integer> twoNums;
	private List<Integer> threeNums;
	private List<Integer> assembleNums;

	private SensorManager manager;
	private ShakeListener listener;
	
	@Override
	public void init() {
		showInMiddle = (ViewGroup) View.inflate(context, R.layout.nb_playsyfive, null);
		
		playLotteryName = (TextView) findViewById(R.id.play_syfive_lotteryname);

		issueNumber = (TextView) findViewById(R.id.play_syfive_issuenumber);
		issueSales = (TextView) findViewById(R.id.play_syfive_issuesales);
		salestime = (RushBuyCountDownTimerView) findViewById(R.id.play_syfive_salestime);
		mopenTimer = (RushBuyCountDownTimerView) findViewById(R.id.play_syfive_opentime);
		
		playBottomNotice = (TextView) findViewById(R.id.nb_playsyfive_bottom_game_lottery_notice);				//提示 注数 金额
		gamePlusButton = (Button) findViewById(R.id.nb_playsyfive_botton_game_plus);
		gameMinusButton = (Button) findViewById(R.id.nb_playsyfive_botton_game_minus);

		oneFaceContainer = (LinearLayout) findViewById(R.id.nb_syfive_one_area);
		twoFaceContainer = (LinearLayout) findViewById(R.id.nb_syfive_two_area);
		threeFaceContainer = (LinearLayout) findViewById(R.id.nb_syfive_three_area);
		assembleFaceContainer = (LinearLayout) findViewById(R.id.nb_syfive_assemble_area);
		
		assembleTitle = (TextView) findViewById(R.id.nb_syfive_assemble_title);

		oneContainer = (MyTwoGridView) findViewById(R.id.nb_syfive_one_number_container);
		twoContainer = (MyTwoGridView) findViewById(R.id.nb_syfive_two_number_container);
		threeContainer = (MyTwoGridView) findViewById(R.id.nb_syfive_three_number_container);
		assembleContainer = (MyTwoGridView) findViewById(R.id.nb_syfive_assemble_number_container);
		
		randomRed = (TextView) findViewById(R.id.nb_syfive_random_red);

		oneNums = new ArrayList<Integer>();
		twoNums = new ArrayList<Integer>();
		threeNums = new ArrayList<Integer>();
		assembleNums = new ArrayList<Integer>();

		oneAdapter = new TwoPoolAdapter(context, 11, oneNums, R.drawable.id_redball);
		twoAdapter = new TwoPoolAdapter(context, 11, twoNums, R.drawable.id_redball);
		threeAdapter = new TwoPoolAdapter(context, 11, threeNums, R.drawable.id_redball);
		assembleAdapter = new TwoPoolAdapter(context, 11, assembleNums, R.drawable.id_redball);

		oneContainer.setAdapter(oneAdapter);
		twoContainer.setAdapter(twoAdapter);
		threeContainer.setAdapter(threeAdapter);
		assembleContainer.setAdapter(assembleAdapter);

		manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	public void setListener() {
		
//		salestime.setOnTimeCompleteListener(new Anticlockwise.OnTimeCompleteListener()
//		{
//			@Override
//			public void onTimeComplete(View v) {
//	        	  getCurrentIssueInfo(true);
//	          }
//	    });
		
		salestime.setOncountdown(new RushBuyCountDownTimerView.OnUpdateCountdownListener() {
			
			@Override
			public void onUpdateCountdown() {
				// TODO Auto-generated method stub
				getCurrentIssueInfo();
			}

			@Override
			public void onUpdateRecordTime(boolean hourFlag,
					boolean minuteFlag, boolean secondFlag) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mopenTimer.setOncountdown(new RushBuyCountDownTimerView.OnUpdateCountdownListener() {
			
			@Override
			public void onUpdateCountdown() {
				// TODO Auto-generated method stub
				getCurrentOpenIssueInfo();
			}

			@Override
			public void onUpdateRecordTime(boolean hourFlag,
					boolean minuteFlag, boolean secondFlag) {
				// TODO Auto-generated method stub
				
			}
		});
		
		String randomIco=context.getResources().getString(R.string.fa_random);
		String resmobileshakesext=context.getResources().getString(R.string.is_mobile_shakes_lable);
		randomRed.setText(randomIco+" "+resmobileshakesext);
		randomRed.setTypeface(font);
		randomRed.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				randomMethod();
			}
		});
		
		gamePlusButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int doubleMinusNo=ShoppingCart.getInstance().getAppnumbers();
				if(doubleMinusNo<=1)
				{
					PromptManager.showToast(context, "投注倍数不能小于1");
				}
				ShoppingCart.getInstance().addAppnumbers(false);
			}
			
		});
		gameMinusButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int doublePlusNo=ShoppingCart.getInstance().getAppnumbers();
				if(doublePlusNo>=99)
				{
					PromptManager.showToast(context, "投注倍数不能大于100");
				}
				ShoppingCart.getInstance().addAppnumbers(true);
			}
			
		});

		oneContainer.setOnActionUpListener(new OnActionUpListener() {

			@Override
			public void onActionUp(View view, int position) {
				TextView textview=(TextView)view;
				// 判断当前点击的item是否被选中了
				if (!oneNums.contains(position + 1)) {
					// 如果没有被选中
					// 背景图片切换到红色
					textview.setBackgroundResource(R.drawable.id_redball);
					textview.setTextColor(context.getResources().getColor(R.color.white));
					oneNums.add(position + 1);
				} else {
					// 还原背景图片
					textview.setBackgroundResource(R.drawable.id_defalut_ball);
					textview.setTextColor(context.getResources().getColor(R.color.darkgray));
					oneNums.remove((Object) (position + 1));
				}

				changeNotice();
			}
		});

		twoContainer.setOnActionUpListener(new OnActionUpListener() {

			@Override
			public void onActionUp(View view, int position) {
				TextView textview=(TextView)view;
				// 判断当前点击的item是否被选中了
				if (!twoNums.contains(position + 1)) {
					// 如果没有被选中
					// 背景图片切换到红色
					textview.setBackgroundResource(R.drawable.id_redball);
					textview.setTextColor(context.getResources().getColor(R.color.white));
					twoNums.add(position + 1);
				} else {
					// 被选中
					// 还原背景图片
					textview.setBackgroundResource(R.drawable.id_defalut_ball);
					textview.setTextColor(context.getResources().getColor(R.color.darkgray));
					twoNums.remove((Object) (position + 1));
				}

				changeNotice();
			}
		});

		threeContainer.setOnActionUpListener(new OnActionUpListener() {

			@Override
			public void onActionUp(View view, int position) {
				TextView textview=(TextView)view;
				// 判断当前点击的item是否被选中了
				if (!threeNums.contains(position + 1)) {
					// 如果没有被选中
					// 背景图片切换到红色
					textview.setBackgroundResource(R.drawable.id_redball);
					textview.setTextColor(context.getResources().getColor(R.color.white));
					threeNums.add(position + 1);
				} else {
					// 被选中
					// 还原背景图片
					textview.setBackgroundResource(R.drawable.id_defalut_ball);
					textview.setTextColor(context.getResources().getColor(R.color.darkgray));
					threeNums.remove((Object) (position + 1));
				}

				changeNotice();
			}
		});
		

		assembleContainer.setOnActionUpListener(new OnActionUpListener() {

			@Override
			public void onActionUp(View view, int position) {
				TextView textview=(TextView)view;
				// 判断当前点击的item是否被选中了
				if (!assembleNums.contains(position + 1)) {
					// 如果没有被选中
					// 背景图片切换到红色
					textview.setBackgroundResource(R.drawable.id_redball);
					textview.setTextColor(context.getResources().getColor(R.color.white));
					assembleNums.add(position + 1);
				} else {
					// 被选中
					// 还原背景图片
					textview.setBackgroundResource(R.drawable.id_defalut_ball);
					textview.setTextColor(context.getResources().getColor(R.color.darkgray));
					assembleNums.remove((Object) (position + 1));
				}

				changeNotice();
			}
		});

	}

	@Override
	public int getID() {
		return ConstantValue.VIEW_PLAYSYFIVE;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nb_syfive_lottery_buy_bottom:
			randomPickPane();
			break;
		}
		super.onClick(v);
	}

	@Override
	public void onResume() {
		salestime.setTime(0,0,0);
		salestime.start();
		
		mopenTimer.setTime(0,0,0);
		mopenTimer.start();
		
//		getCurrentIssueInfo();
		setLotteryId(bundle.getInt("lotteryid"));
		setLotteryType(bundle.getInt("lotterytype"));
		setLotteryName(bundle.getString("lotteryname"));
		
		playLotteryName.setText(bundle.getString("lotteryname"));
		
		String lastissuetext = context.getResources().getString(R.string.is_playssc_lastissue_wait_label);
		String code="- - - - -";
		SpannableStringBuilder laststyle=null;  
		lastissuetext = StringUtils.replaceEach(lastissuetext, new String[] { "ISSUE", "NUMBER" }, new String[] {"- -", code });
		int fstart=lastissuetext.indexOf(code);
        int fend=fstart+code.length();
        laststyle=new SpannableStringBuilder(lastissuetext); 
        laststyle.setSpan(new ForegroundColorSpan(Color.RED),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
		
		issueNumber.setText(laststyle);
		
		String salesissuetext = context.getResources().getString(R.string.is_playssc_salesissue_label);
		salesissuetext = StringUtils.replaceEach(salesissuetext,new String[] { "ISSUE" }, new String[] { "- - - -" });
		issueSales.setText(salesissuetext);
		
		setPlayMenuList(ConstantInformation.playMap.get(getLotteryId()+""));
		
		initPlayMenu();
		setSelectLucreMode(LucreModeList.getLucremode().get(0));
		ShoppingCart.getInstance().setLucreMode(getSelectLucreMode()); // 初使化 模式
		BottomManager.getInstrance().changeGameBottomMoneyMode(getSelectLucreMode());
		clearMethod();
		
		// 注册
		listener = new ShakeListener(context) {

			@Override
			public void randomCure() {
				randomPickPane();
			}

		};
		manager.registerListener(listener,manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);

		super.onResume();
	}

	@Override
	public void onPause() {
		// 注销
		manager.unregisterListener(listener);
		super.onPause();
	}

	/**
	 * 清除
	 */
	@Override
	public void clearMethod() {
		oneNums.clear();
		twoNums.clear();
		threeNums.clear();
		assembleNums.clear();
		changeNotice();

		oneAdapter.notifyDataSetChanged();
		twoAdapter.notifyDataSetChanged();
		threeAdapter.notifyDataSetChanged();
		assembleAdapter.notifyDataSetChanged();
	}

	/**
	 * 随机生成选好
	 */
	@Override
	public void randomMethod() {
		// TODO Auto-generated method stub
		randomPickPane();
	}

	/**
	 * 设置元角分模式
	 */
	@Override
	public void setMoneyModeMethod(LucreMode mode) {
		// TODO Auto-generated method stub
		setSelectLucreMode(mode);
		ShoppingCart.getInstance().setLucreMode(getSelectLucreMode());
		changeNotice();
	}

	/**
	 * 玩法模式选择
	 */
	@Override
	public void formulateMethod(String draftMethod) {
		// 服务器菜单
		List<PlayMenu> playmenuList = getPlayMenuList();
		if (playmenuList.size() > 0) {
			for (PlayMenu playmenu : playmenuList) {
				String playmenuname = playmenu.getMethodname();
				if (draftMethod.equals(playmenuname)) {
					initMenuTitle(playmenuname);
					setSelectplay(playmenu);
					ConstantInformation.recordPlayMenu.put(String.valueOf(getLotteryId()), playmenu);
					updatePickPane();
				}
			}
			changeNotice();
		}
	}

	@Override
	public void done() {
		if(getBetsdigit()>0)
			ConfirmBettingInfo();
		else{
			CustomDialog.Builder builder = new CustomDialog.Builder(context);
			builder.setMessage("号码选择不完整，请重新选择");
			builder.setTitle("温馨提示");
			builder.setPositiveButton("随机一注", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					randomMethod();
					dialog.dismiss();
					//设置你的操作事项
				}
			});
			
			builder.setNegativeButton("知道了", new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			
			builder.create().show();
		}
			
	}

	/**
	 * 改变底部导航的提示信息
	 */
	private void changeNotice() {
		ChangeBottomInfo();
	}
	
	private void promptNotice(String notice){
		playBottomNotice.setText(Html.fromHtml(notice));
	}

	/**
	 * 初始化玩法菜单
	 * 
	 * @param string
	 */
	private void initMenuTitle(String text) {
		TitleManager.getInstance().getInitMenu(bundle.getInt("lotteryid"), text);
	}

	public void initPlayMenu() {
		
		if(ConstantInformation.recordPlayMenu.get(String.valueOf(getLotteryId()))!=null){
			if(ConstantInformation.recordPlayMenu.get(String.valueOf(getLotteryId())).getMethodname()!=null)
				formulateMethod(ConstantInformation.recordPlayMenu.get(String.valueOf(getLotteryId())).getMethodname());
		}else{
			String[] notice= ConstantInformation.playmuneMap.get(getLotteryId()+"");
			if (notice != null) {
				formulateMethod(notice[3]);
			}
		}
	}
	
	/**
	 * 期号请求
	 */
	private void getCurrentOpenIssueInfo() {
		new MyHttpTask<Integer>() {
			@Override
			protected Message doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				CommonInfoEngine engine = BeanFactory.getImpl(CommonInfoEngine.class);
				return engine.getCurrentIssueInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						TaskIssueInfoElement element = (TaskIssueInfoElement) result.getBody().getElements().get(0);
						IssueAll currentIssueAll=element.getIssueAllMap().get(String.valueOf(bundle.getInt("lotteryid"))); 
						if(currentIssueAll!=null){
							
							IssueSales currentIssueSales = currentIssueAll.getIssueSales();
							IssueLast previousIssueLast = currentIssueAll.getIssueLast();
							// 上一期开奖号码
							String issueLastStr=previousIssueLast.getIssue();
							issueLastStr=issueLastStr.substring(6, issueLastStr.length());
							
							String codenum=previousIssueLast.getCode();
							String code="",lastissuetext="";
							
							if(codenum.length()>0&&codenum!=null){
								code=codenum;
								lastissuetext = context.getResources().getString( R.string.is_playssc_lastissue_already_label);
							}else{
								getCurrentOpenIssueInfo();
								return;
							}
							lastissuetext = StringUtils.replaceEach(lastissuetext, new String[] { "ISSUE", "NUMBER" }, new String[] {issueLastStr, code });
							
							RecordTime timeRecord = getLasttime(currentIssueSales.getCurrenttime(), currentIssueSales.getOpentime());
							mopenTimer.setTime(timeRecord.getHour(),timeRecord.getMinute(),timeRecord.getSecond());
							SpannableStringBuilder laststyle=null;  
							int fstart=lastissuetext.indexOf(code);
					        int fend=fstart+code.length();
					        laststyle=new SpannableStringBuilder(lastissuetext); 
					        laststyle.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.goldenrod)),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
							issueNumber.setText(laststyle);
							mopenTimer.start();
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
					PromptManager.showToast(context, "网络状态差，请重试！");
				}

				super.onPostExecute(result);

			}
		}.executeProxy(bundle.getInt("lotteryid"));
	}

	/**
	 * 期号请求
	 */
	private void getCurrentIssueInfo() {
		new MyHttpTask<Integer>() {
			@Override
			protected Message doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				CommonInfoEngine engine = BeanFactory.getImpl(CommonInfoEngine.class);
				return engine.getCurrentIssueInfo(params[0]);
			}

			@Override
			protected void onPostExecute(Message result) {
				// 更新界面
				if (result != null) {
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						currentIssueInfo(result);
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
					PromptManager.showToast(context, "网络状态差，请重试！");
				}

				super.onPostExecute(result);

			}
		}.executeProxy(bundle.getInt("lotteryid"));
	}
	
	/** 
	 * 当前期号 投注结束请求服务器
	 */
	private void currentIssueInfo(Message result) {
		TaskIssueInfoElement element = (TaskIssueInfoElement) result.getBody().getElements().get(0);
		IssueAll currentIssueAll=element.getIssueAllMap().get(String.valueOf(bundle.getInt("lotteryid"))); 
		if(currentIssueAll!=null){
			IssueSales currentIssueSales = currentIssueAll.getIssueSales();
			IssueLast previousIssueLast = currentIssueAll.getIssueLast();
			
			String issueLastStr=previousIssueLast.getIssue();
			issueLastStr=issueLastStr.substring(6, issueLastStr.length());
			
			String codenum=previousIssueLast.getCode();
			String code="",lastissuetext="";
			if(codenum.length()>0&&codenum!=null){
				code=codenum;
				lastissuetext = context.getResources().getString( R.string.is_playssc_lastissue_already_label);
			}else{
				code="- - - - -";
				lastissuetext = context.getResources().getString( R.string.is_playssc_lastissue_wait_label);
			}
			
			lastissuetext = StringUtils.replaceEach(lastissuetext, new String[] { "ISSUE", "NUMBER" }, new String[] {issueLastStr, code });
			
			String  issueSalesStr=currentIssueSales.getIssue();
			issueSalesStr=issueSalesStr.substring(6, issueSalesStr.length());
			
			// 第ISSUE期 还有TIME停售
			String salesissuetext = context.getResources().getString(R.string.is_playssc_salesissue_label);
			salesissuetext = StringUtils.replaceEach(salesissuetext,new String[] { "ISSUE" }, new String[] { issueSalesStr });
			RecordTime timeRecord = getLasttime( currentIssueSales.getCurrenttime(), currentIssueSales.getSaleend());
			
			salestime.setTime(timeRecord.getHour(), timeRecord.getMinute(), timeRecord.getSecond());
			SpannableStringBuilder laststyle=null;  
			int fstart=lastissuetext.indexOf(code);
	        int fend=fstart+code.length();
	        laststyle=new SpannableStringBuilder(lastissuetext); 
	        laststyle.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.goldenrod)),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
			issueNumber.setText(laststyle);
			issueSales.setText(salesissuetext);

			salestime.start();
			
			bundle = new Bundle();
			bundle.putInt("lotteryid", getLotteryId());
			bundle.putString("issue", currentIssueSales.getIssue());
			bundle.putInt("lotterytype", getLotteryType());
			bundle.putString("lotteryname", getLotteryName());
		}
	}

	@SuppressLint("SimpleDateFormat")
	private RecordTime getLasttime(String currenttime,String end) {
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 RecordTime recTime=new RecordTime();
		 try {
			//Date currentTime = new Date();
			//String dateString = df.format(currentTime);
			Date d1 = df.parse(currenttime);
			Date d2 = df.parse(end);
			// Date d2 = new Date(System.currentTimeMillis());//你也可以获取当前时间
			long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
			long days = diff / (1000 * 60 * 60 * 24);
			long hours = (diff - days * (1000 * 60 * 60 * 24))
					/ (1000 * 60 * 60);
			long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours* (1000 * 60 * 60))/ (1000 * 60);
			long seconds = (diff/1000-days*24*60*60-hours*60*60-minutes*60);  
			int day= new Long(days).intValue();  int hour= new Long(hours).intValue(); int minute= new Long(minutes).intValue(); int second= new Long(seconds).intValue();
			recTime.setDay(day);recTime.setHour(hour); recTime.setMinute(minute);recTime.setSecond(second);
			return recTime;
		 } catch (Exception e) {
		 }
		 return recTime;
	}


	/**
	 * 玩法列表
	 * 
	 * @return
	 */
	private List<PlayMenu> playMenuList=new ArrayList<PlayMenu>();
	
	public List<PlayMenu> getPlayMenuList() {
		return playMenuList;
	}
	public void setPlayMenuList(List<PlayMenu> playMenuList) {
		this.playMenuList = playMenuList;
	}
	
	/**
	 * 选择玩法
	 * 
	 * @return
	 */
	private PlayMenu selectplay = null;
	
	public PlayMenu getSelectplay() {
		return selectplay;
	}

	public void setSelectplay(PlayMenu selectplay) {
		this.selectplay = selectplay;
	}
	
	/**
	 * 选择元角分模式
	 */
	
	private LucreMode lucreModeSelect = null;
	
	public void setSelectLucreMode(LucreMode selectmode) {
		// TODO Auto-generated method stub
		this.lucreModeSelect = selectmode;
	}
	
	public LucreMode getSelectLucreMode() {
		// TODO Auto-generated method stub
		return lucreModeSelect;
	}
	
	/**
	 * 选择注数金额
	 */
	private int betsdigit;
	
	public int getBetsdigit() {
		return betsdigit;
	}

	public void setBetsdigit(int betsdigit) {
		this.betsdigit = betsdigit;
	}

	/**
	 * 确认投注信息
	 */
	private void ConfirmBettingInfo() {
		// 一个购物车中，只能放置一个彩种，当前期的投注信息
		// ②判断：是否获取到了当前销售期的信息
		if (bundle != null) {
			DecimalFormat decimalFormat = new DecimalFormat("00");
			Ticket ticket = new Ticket();
			
			PlayMenu playmenuKey = getSelectplay();
			if (playmenuKey.getJscode().equals("LTZX3")) // 前三直选
			{
				if ((oneNums.size() != 0) && (twoNums.size() != 0) && (threeNums.size() != 0)){
					
					StringBuffer oneBuffer = new StringBuffer();
					Collections.sort(oneNums);
					for (Integer item : oneNums) {
						oneBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setOneNum(oneBuffer.substring(1));

					StringBuffer twoBuffer = new StringBuffer();
					Collections.sort(twoNums);
					for (Integer item : twoNums) {
						twoBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setTwoNum(twoBuffer.substring(1));

					StringBuffer threeBuffer = new StringBuffer();
					Collections.sort(threeNums);
					for (Integer item : threeNums) {
						threeBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setThreeNum(threeBuffer.substring(1));

					ticket.setNum(getBetsdigit());
					ticket.setMoneyMode(getSelectLucreMode());
					ticket.setMultiple(1);
					ticket.setSelectPlay(playmenuKey);
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class, bundle);
				}else{
					agoThreeRandom();
				}
				
			} else if (playmenuKey.getJscode().equals("LTZU3")) // 前三组选
			{
				if (assembleNums.size() >= 3) {
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
				} else {
					groupThreeRandom();
				}
			} else if (playmenuKey.getJscode().equals("LTZX2")){ // 前二直选
			
				if ((oneNums.size() != 0) && (twoNums.size() != 0)){
					StringBuffer oneBuffer = new StringBuffer();
					Collections.sort(oneNums);
					for (Integer item : oneNums) {
						oneBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setOneNum(oneBuffer.substring(1));

					StringBuffer twoBuffer = new StringBuffer();
					Collections.sort(twoNums);
					for (Integer item : twoNums) {
						twoBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setTwoNum(twoBuffer.substring(1));

					ticket.setNum(getBetsdigit());
					ticket.setMoneyMode(getSelectLucreMode());
					ticket.setMultiple(1);
					ticket.setSelectPlay(playmenuKey);
					
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class, bundle);
				}else{
					agoTwoRandom();
				}
			} else if (playmenuKey.getJscode().equals("LTZU2")) // 前二组选
			{
				if (assembleNums.size() >= 2){
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
				}else{
					groupTwoRandom();
				}
			} else if (playmenuKey.getJscode().equals("LTDWD")){ // 第一位 二位 三位
				if(oneNums.size()>0||twoNums.size()>0||threeNums.size()>0){
					if (oneNums.size()>0){
						StringBuffer oneBuffer = new StringBuffer();
						Collections.sort(oneNums);
						for (Integer item : oneNums) {
							oneBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setOneNum(oneBuffer.substring(1));
					}
					if (twoNums.size()>0){
						StringBuffer twoBuffer = new StringBuffer();
						Collections.sort(twoNums);
						for (Integer item : twoNums) {
							twoBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setTwoNum(twoBuffer.substring(1));
					}
					if(threeNums.size()>0){
						StringBuffer threeBuffer = new StringBuffer();
						Collections.sort(threeNums);
						for (Integer item : threeNums) {
							threeBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setThreeNum(threeBuffer.substring(1));
					}

					ticket.setNum(getBetsdigit());
					ticket.setMoneyMode(getSelectLucreMode());
					ticket.setMultiple(1);
					ticket.setSelectPlay(playmenuKey);
					
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class, bundle);
				}else{
					locationRandom();
				}
			} else if (playmenuKey.getJscode().equals("LTRX1")){ 	// 任选一中一
				if (assembleNums.size()!=0){
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
				}else{
					oneToOneRandom();
				}
			} else if (playmenuKey.getJscode().equals("LTRX2")){ 	// 任选二中二
				if (assembleNums.size()>=2){
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
				}else{
					twoToTwoRandom();
				}
			} else if (playmenuKey.getJscode().equals("LTRX3")){ 	// 任选三中三
				if (assembleNums.size()>=3){
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
				}else{
					threeToThreeRandom();
				}
			} else if (playmenuKey.getJscode().equals("LTRX4")){	// 任选四中四
				if (assembleNums.size()>=4){
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
				
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
				}else{
					FourToFourRandom();
				}
			}else if (playmenuKey.getJscode().equals("LTRX5")){		// 任选五中五
				if (assembleNums.size()>=5){
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
				}else{
					FivesToFivesRandom();
				}
			}else if (playmenuKey.getJscode().equals("LTRX6")){		// 任选六中五
				if (assembleNums.size()>=6){
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
				}else{
					SixToFivesRandom();
				}
			}else if (playmenuKey.getJscode().equals("LTRX7")){		// 任选七中五
				if (assembleNums.size()>=7){
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
				}else{
					SevenToFivesRandom();
				}
				
			}else if (playmenuKey.getJscode().equals("LTRX8")){		// 任选八中五
				if (assembleNums.size()>=8){
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay());
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * getSelectLucreMode().getLucreReckon());  
					double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
					ticket.setNoteMoney(m);
					ticket.setLotteryid(bundle.getInt("lotteryid"));
					ticket.setLotterytype(bundle.getInt("lotterytype"));
					// ④创建彩票购物车，将投注信息添加到购物车中
					if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
						List<Ticket> ticketList=new ArrayList<Ticket>();
						ticketList.add(ticket);
						ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
					}else{
						ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
					}
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);
				}else{
					EightToFivesRandom();
				}
			}
			changeNotice();
		} else {
			// 重新获取期次信息
			getCurrentIssueInfo();
		}
	}

	/**
	 * 更改底部投注 注数，金额信息
	 */
	private void ChangeBottomInfo() {
		setBetsdigit(0);
		double money = 0.00;
		// 以一注为分割
		PlayMenu playmenuKey = getSelectplay();
		if (playmenuKey.getJscode().equals("LTZX3")) // 前三直选
		{
			if (oneNums.size() > 0 && twoNums.size() > 0 && threeNums.size() > 0){
				setBetsdigit(Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, null, playmenuKey));
				money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
			}
		} else if (playmenuKey.getJscode().equals("LTZU3")) // 前三组选
		{
			if (assembleNums.size() >= 3){
				setBetsdigit(Calculation.getInstance().calculationNote(null, null, null, assembleNums, playmenuKey));
				money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
			}
		} else if (playmenuKey.getJscode().equals("LTZX2")) // 前二直选
		{
			if ((oneNums.size() > 0) && (twoNums.size() > 0)){
				setBetsdigit(Calculation.getInstance().calculationNote(oneNums, twoNums, null, null, playmenuKey));
				money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
			}
		} else if (playmenuKey.getJscode().equals("LTZU2")) // 前二组选
		{
			if (assembleNums.size() >= 2){
				setBetsdigit(Calculation.getInstance().calculationNote(null, null, null, assembleNums, playmenuKey));
				money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
			}
		} else if (playmenuKey.getJscode().equals("LTDWD")){ // 第一位 二位 三位
			if ((oneNums.size() !=0)||(twoNums.size()!=0)||(threeNums.size()!=0)){
				setBetsdigit(Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, playmenuKey));
				money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
			}
		} else if (playmenuKey.getJscode().equals("LTRX1")){ 	// 任选一中一
			money = isPickNum(assembleNums.size(),1);
		} else if (playmenuKey.getJscode().equals("LTRX2")){ 	// 任选二中二
			money = isPickNum(assembleNums.size(),2);
		} else if (playmenuKey.getJscode().equals("LTRX3")){ 	// 任选三中三
			money = isPickNum(assembleNums.size(),3);
		} else if (playmenuKey.getJscode().equals("LTRX4")){	// 任选四中四
			money = isPickNum(assembleNums.size(),4);
		}else if (playmenuKey.getJscode().equals("LTRX5")){		// 任选五中五
			money = isPickNum(assembleNums.size(),5);
		}else if (playmenuKey.getJscode().equals("LTRX6")){		// 任选六中五
			money = isPickNum(assembleNums.size(),6);
		}else if (playmenuKey.getJscode().equals("LTRX7")){		// 任选七中五
			money = isPickNum(assembleNums.size(),7);
		}else if (playmenuKey.getJscode().equals("LTRX8")){		// 任选八中五
			money = isPickNum(assembleNums.size(),8);
		}
		
		BigDecimal b=new BigDecimal(money);  
		double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		
		String noticeInfo = context.getResources().getString(R.string.is_shopping_list_betting_notice);
		noticeInfo = StringUtils.replaceEach(noticeInfo, new String[] {"NUM", "MONEY","MUL"}, new String[] { String.valueOf(betsdigit),String.valueOf(m),String.valueOf(1)});
		// 投注数量
		promptNotice(noticeInfo);
		//BottomManager.getInstrance().changeGameBottomBetsNotice(noticeInfo, betsdigit);// 投注数量
	}
	
	private double isPickNum(int value,int count){
		double money = 0;
		if (value >=count){
			setBetsdigit(Calculation.getInstance().calculationNote(oneNums, twoNums, threeNums, assembleNums, getSelectplay()));
			money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
		}
		return money;
	}

	/**
	 * 随机选择一注号码
	 */
	private void randomPickPane() {
		PlayMenu playmenuKey = getSelectplay();
		switch (playmenuKey.getJscode()) {
		case "LTZX3":	// 前三直选
			agoThreeRandom();
			break;
		case "LTZU3":	// 前三组选
			groupThreeRandom();	
			break;
		case "LTZX2":	// 前二直选
			agoTwoRandom();
			break;
		case "LTZU2":	// 前二组选
			groupTwoRandom();
			break;
		case "LTDWD":	//定位
			locationRandom();
			break;
		case "LTRX1":// 任选一中一
			oneToOneRandom();
			break;
		case "LTRX2":// 任选二中二
			twoToTwoRandom();
			break;
		case "LTRX3":// 任选三中三
			threeToThreeRandom();
			break;
		case "LTRX4"://任选四中四
			FourToFourRandom();
			break;
		case "LTRX5"://任选五中五
			FivesToFivesRandom();
			break;
		case "LTRX6"://任选六中五
			SixToFivesRandom();
			break;
		case "LTRX7"://任选七中五
			SevenToFivesRandom();
			break;
		case "LTRX8"://任选八中五
			EightToFivesRandom();
			break;
		default:
			break;
		}
		changeNotice();
	}

	/**
	 * 更新选号 界面
	 * @param playmenuKey
	 * @param lotteryId
	 */
	private void updatePickPane() {

		PlayMenu playmenuKey = getSelectplay();
		
		if (playmenuKey.getJscode().equals("LTZX3")) 			// 前三直选
		{
			agoThreeReveal();
		} else if(playmenuKey.getJscode().equals("LTZU3")){ 	// 前三组选
		
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_syfive_assemble_three_point);
			assembleReveal(title,point);
		} else if (playmenuKey.getJscode().equals("LTZX2")) 	// 前二直选
		{
			agoTwoReveal();
		} else if (playmenuKey.getJscode().equals("LTZU2")) 	// 前二组选
		{
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_syfive_assemble_two_point);
			assembleReveal(title,point);
		} else if (playmenuKey.getJscode().equals("LTDWD")){ 	// 第一位 二位 三位
			locationReveal();
		} else if (playmenuKey.getJscode().equals("LTRX1")){ 	// 任选一中一
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_syfive_assemble_one_point);
			assembleReveal(title,point);
		} else if (playmenuKey.getJscode().equals("LTRX2")){ 	// 任选二中二
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_syfive_assemble_two_point);
			assembleReveal(title,point);
		} else if (playmenuKey.getJscode().equals("LTRX3")){ 	// 任选三中三
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_syfive_assemble_three_point);
			assembleReveal(title,point);
		} else if (playmenuKey.getJscode().equals("LTRX4")){	//任选四中四
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_syfive_assemble_four_point);
			assembleReveal(title,point);
		}else if (playmenuKey.getJscode().equals("LTRX5")){		//任选五中五
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_syfive_assemble_fives_point);
			assembleReveal(title,point);
		}else if (playmenuKey.getJscode().equals("LTRX6")){		//任选六中五
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_syfive_assemble_six_point);
			assembleReveal(title,point);
		}else if (playmenuKey.getJscode().equals("LTRX7")){		//任选七中五
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_syfive_assemble_seven_point);
			assembleReveal(title,point);
		}else if (playmenuKey.getJscode().equals("LTRX8")){		//任选八中五
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_syfive_assemble_eight_point);
			assembleReveal(title,point);
		}
	}

	private void HandlingShow() {
		// 处理展示
		oneAdapter.notifyDataSetChanged();
		twoAdapter.notifyDataSetChanged();
		threeAdapter.notifyDataSetChanged();
		assembleAdapter.notifyDataSetChanged();
	}

	/**
	 * 随机前三
	 */
	private void agoThreeRandom() {
		Random random = new Random();
		oneNums.clear();
		int onenum = random.nextInt(11)+ 1;
		oneNums.add(onenum );
		
		twoNums.clear();
		int twonum = random.nextInt(11)+ 1;
		twoNums.add(twonum);

		threeNums.clear();
		int threenum = random.nextInt(11)+ 1;
		threeNums.add(threenum);
		HandlingShow();
	}
	
	/**
	 * 随机前二
	 */
	private void agoTwoRandom() {
		Random random = new Random();
		oneNums.clear();
		int onenum = random.nextInt(11) + 1;
		oneNums.add(onenum);

		twoNums.clear();
		int twonum = random.nextInt(11) + 1;
		twoNums.add(twonum);

		HandlingShow();
	}

	/**
	 * 随机前三 组选
	 */
	private void groupThreeRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 3) {
			int num = random.nextInt(11) + 1;

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}

	/**
	 * 随机前二 组选
	 */
	private void groupTwoRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 2) {
			int num = random.nextInt(11) + 1;

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}
	
	private void locationRandom() {
		Random random = new Random();
		oneNums.clear();
		int onenum = random.nextInt(11) + 1;
		oneNums.add(onenum);

		twoNums.clear();
		int twonum = random.nextInt(11) + 1;
		twoNums.add(twonum);

		threeNums.clear();
		int threenum = random.nextInt(11) + 1;
		threeNums.add(threenum);
		HandlingShow();
	}


	private void oneToOneRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 1) {
			int num = random.nextInt(11)+ 1;

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}

	private void twoToTwoRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 2) {
			int num = random.nextInt(11) + 1;

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}

	private void threeToThreeRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 3) {
			int num = random.nextInt(11) + 1;

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}

	private void FourToFourRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 4) {
			int num = random.nextInt(11) + 1;

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}

	private void FivesToFivesRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 5) {
			int num = random.nextInt(11) + 1;

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}
	
	private void SixToFivesRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 6) {
			int num = random.nextInt(11) + 1;

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}
	
	private void SevenToFivesRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 7) {
			int num = random.nextInt(11) + 1;

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}
	
	private void EightToFivesRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 8) {
			int num = random.nextInt(11) + 1;

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}

	/**
	 * 隐藏
	 */
	private void initOnePickFaceHide() {
		oneFaceContainer.setVisibility(View.GONE);
	}

	private void initTwoPickFaceHide() {
		twoFaceContainer.setVisibility(View.GONE);
	}

	private void initThreePickFaceHide() {
		threeFaceContainer.setVisibility(View.GONE);
	}
	
	private void initAssemblePickFaceHide() {
		assembleFaceContainer.setVisibility(View.GONE);
	}

	/**
	 * 显示
	 */
	private void initOnePickFaceShow() {
		oneFaceContainer.setVisibility(View.VISIBLE);
	}

	private void initTwoPickFaceShow() {
		twoFaceContainer.setVisibility(View.VISIBLE);
	}

	private void initThreePickFaceShow() {
		threeFaceContainer.setVisibility(View.VISIBLE);
	}
	
	private void initAssemblePickFaceShow() {
		assembleFaceContainer.setVisibility(View.VISIBLE);
	}

	private void allRevealHide() {
		initOnePickFaceHide();
		initTwoPickFaceHide();
		initThreePickFaceHide();
		initAssemblePickFaceHide();
	}

	private void allRevealShow() {
		initOnePickFaceShow();
		initTwoPickFaceShow();
		initThreePickFaceShow();
		initAssemblePickFaceShow();
	}

	/**
	 * 前三 直选
	 */
	private void agoThreeReveal() {
		allRevealShow();
		initAssemblePickFaceHide();
	}

	/**
	 * 前二 直选
	 */
	private void agoTwoReveal() {
		allRevealShow();
		initThreePickFaceHide();
		initAssemblePickFaceHide();
	}
	private void setAssembleRitle(String title, String prompt) {
		assembleTitle.setText(title + "\t\t\t\t\t" + prompt);
	}
	/**
	 * 组合 选一中一  选二中二 选三中一  选一中一  选一中一  选一中一  选一中一  选一中一
	 */
	private void assembleReveal(String title, String prompt){
		setAssembleRitle(title, prompt);
		allRevealHide();
		initAssemblePickFaceShow();
	}
	
	/**
	 * 定位单
	 */
	private void locationReveal(){
		allRevealShow();
		initAssemblePickFaceHide();
	}
	
	private int lotteryid=0;
	
	@Override
	public int getLotteryId() {
		// TODO Auto-generated method stub
		return lotteryid;
	}

	@Override
	public void setLotteryId(int id) {
		// TODO Auto-generated method stub
		this.lotteryid=id;
	}
	
	private int lotteryType=0;
	
	@Override
	public void setLotteryType(int type) {
		// TODO Auto-generated method stub
		this.lotteryType=type;
	}

	@Override
	public int getLotteryType() {
		// TODO Auto-generated method stub
		return lotteryType;
	}
	
	private String lotteryName;
	
	@Override
	public void setLotteryName(String name) {
		// TODO Auto-generated method stub
		this.lotteryName=name;
	}
	
	@Override
	public String getLotteryName() {
		// TODO Auto-generated method stub
		return lotteryName;
	}

	private Integer appnumbers = 1;
	
	/**
	 * 获取倍数
	 */
	@Override
	public Integer getAppnumbers() {
		return appnumbers;
	}
	
	/**
	 * 操作倍数
	 */
	@Override
	public boolean addAppnumbers(boolean isAdd) {
		// TODO Auto-generated method stub
		if (isAdd) {
			appnumbers++;
			if (appnumbers > 99) {
				appnumbers--;
				return false;
			}
			/*
			 * if (getLotteryvalue() > GlobalParams.MONEY) { appnumbers--;
			 * return false; }
			 */
		} else {
			appnumbers--;
			if (appnumbers == 0) {
				appnumbers++;
				return false;
			}
		}
		return true;
	}

}
