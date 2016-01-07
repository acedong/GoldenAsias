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
import android.provider.OpenableColumns;
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
import com.goldenasia.lottery.view.adapter.OnePoolAdapter;
import com.goldenasia.lottery.view.controls.Anticlockwise;
import com.goldenasia.lottery.view.controls.RushBuyCountDownTimerView;
import com.goldenasia.lottery.view.custom.MyOneGridView;
import com.goldenasia.lottery.view.custom.MyOneGridView.OnActionUpListener;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.BottomManager;
import com.goldenasia.lottery.view.manager.HallMiddleManager;
import com.goldenasia.lottery.view.manager.PlayGame;
import com.goldenasia.lottery.view.manager.PlayGameMethod;
import com.goldenasia.lottery.view.manager.TitleManager;
import com.goldenasia.lottery.view.sensor.ShakeListener;

/**
 * 时时彩选号界面
 * 
 * @author Ace
 * 
 */
public class PlaySSC extends BaseUI implements PlayGame, PlayGameMethod {
	// 通用三步

	// ①标题
	// 判断购彩大厅是否获取到期次信息
	// 如果获取到：拼装标题
	// 否则默认的标题展示

	// ②填充选号容器
	// ③选号：单击+机选跟距玩法选择号码

	// ④手机摇晃处理
	// 加速度传感器：
	// 方式一：任意一个轴的加速度值在单位时间内（1秒），变动的速率达到设置好的阈值
	// 方式二：获取三个轴的加速度值，记录，当过一段时间之后再次获取三个轴的加速度值，计算增量，将相邻两个点的增量进行汇总，当达到设置好的阈值

	// ⑤提示信息+清空+选好了
	// 机选
	private TextView randomRed;
	//private Anticlockwise mTimer;
	private RushBuyCountDownTimerView salestime;
	private RushBuyCountDownTimerView mopenTimer;
	private TextView playLotteryName;
	private TextView issueNumber;
	private TextView issueSales;
	
	private TextView playBottomNotice;				//底部标题
	private Button gamePlusButton;
	private Button gameMinusButton;

	private LinearLayout wanFaceContainer;
	private LinearLayout qianFaceContainer;
	private LinearLayout baiFaceContainer;
	private LinearLayout shiFaceContainer;
	private LinearLayout geFaceContainer;
	private LinearLayout assembleFaceContainer;

	private TextView assembleTitle;

	// 选号容器
	private MyOneGridView wanContainer;
	private MyOneGridView qianContainer;
	private MyOneGridView baiContainer;
	private MyOneGridView shiContainer;
	private MyOneGridView geContainer;
	private MyOneGridView assembleContainer;

	private OnePoolAdapter wanAdapter;
	private OnePoolAdapter qianAdapter;
	private OnePoolAdapter baiAdapter;
	private OnePoolAdapter shiAdapter;
	private OnePoolAdapter geAdapter;
	private OnePoolAdapter assembleAdapter;

	private List<Integer> wanNums;
	private List<Integer> qianNums;
	private List<Integer> baiNums;
	private List<Integer> shiNums;
	private List<Integer> geNums;
	private List<Integer> assembleNums;

	private SensorManager manager;
	private ShakeListener listener;
	public PlaySSC(Context context) {
		super(context);
	}
	@Override
	public void init() {
		showInMiddle = (ViewGroup) View.inflate(context, R.layout.nb_playssc, null);
		
		playLotteryName = (TextView) findViewById(R.id.playssc_lotteryname);
		issueNumber = (TextView) findViewById(R.id.playssc_issuenumber);
		issueSales = (TextView) findViewById(R.id.playssc_issuesales);
//		salestime = (Anticlockwise) findViewById(R.id.playssc_salestime);
		mopenTimer = (RushBuyCountDownTimerView) findViewById(R.id.playssc_opentime);
		salestime = (RushBuyCountDownTimerView) findViewById(R.id.playssc_salestime);
		
		
		
		playBottomNotice = (TextView) findViewById(R.id.nb_playssc_bottom_game_lottery_notice);				//提示 注数 金额
		gamePlusButton = (Button) findViewById(R.id.nb_playssc_botton_game_plus);
		gameMinusButton = (Button) findViewById(R.id.nb_playssc_botton_game_minus);

		wanFaceContainer = (LinearLayout) findViewById(R.id.nb_ssc_wan_area);
		qianFaceContainer = (LinearLayout) findViewById(R.id.nb_ssc_qian_area);
		baiFaceContainer = (LinearLayout) findViewById(R.id.nb_ssc_bai_area);
		shiFaceContainer = (LinearLayout) findViewById(R.id.nb_ssc_shi_area);
		geFaceContainer = (LinearLayout) findViewById(R.id.nb_ssc_ge_area);

		assembleFaceContainer = (LinearLayout) findViewById(R.id.nb_ssc_assemble_area);
		assembleTitle = (TextView) findViewById(R.id.nb_ssc_assemble_title);

		wanContainer = (MyOneGridView) findViewById(R.id.nb_ssc_wan_number_container);
		qianContainer = (MyOneGridView) findViewById(R.id.nb_ssc_qian_number_container);
		baiContainer = (MyOneGridView) findViewById(R.id.nb_ssc_bai_number_container);
		shiContainer = (MyOneGridView) findViewById(R.id.nb_ssc_shi_number_container);
		geContainer = (MyOneGridView) findViewById(R.id.nb_ssc_ge_number_container);

		assembleContainer = (MyOneGridView) findViewById(R.id.nb_ssc_assemble_number_container);
		
		randomRed = (TextView) findViewById(R.id.nb_ssc_random_red);

		wanNums = new ArrayList<Integer>();
		qianNums = new ArrayList<Integer>();
		baiNums = new ArrayList<Integer>();
		shiNums = new ArrayList<Integer>();
		geNums = new ArrayList<Integer>();

		assembleNums = new ArrayList<Integer>();

		wanAdapter = new OnePoolAdapter(context, 10, wanNums, R.drawable.id_redball);
		qianAdapter = new OnePoolAdapter(context, 10, qianNums, R.drawable.id_redball);
		baiAdapter = new OnePoolAdapter(context, 10, baiNums, R.drawable.id_redball);
		shiAdapter = new OnePoolAdapter(context, 10, shiNums, R.drawable.id_redball);
		geAdapter = new OnePoolAdapter(context, 10, geNums, R.drawable.id_redball);

		assembleAdapter = new OnePoolAdapter(context, 10, assembleNums, R.drawable.id_redball);

		wanContainer.setAdapter(wanAdapter);
		qianContainer.setAdapter(qianAdapter);
		baiContainer.setAdapter(baiAdapter);
		shiContainer.setAdapter(shiAdapter);
		geContainer.setAdapter(geAdapter);

		assembleContainer.setAdapter(assembleAdapter);
		
		manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	public void setListener() {
		
//		salestime.setOnTimeCompleteListener(new Anticlockwise.OnTimeCompleteListener()
//		{
//			@Override
//			public void onTimeComplete(View v) {
//	        	  getCurrentIssueInfo();
//	          }
//	    });
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

		wanContainer.setOnActionUpListener(new OnActionUpListener() {

			@Override
			public void onActionUp(View view, int position) {
				// 判断当前点击的item是否被选中了
				if (!wanNums.contains(position)) {
					// 如果没有被选中
					// 背景图片切换到红色
					view.setBackgroundResource(R.drawable.id_redball);
					wanNums.add(position);
				} else {
					// 还原背景图片
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					wanNums.remove((Object) (position));
				}

				changeNotice(1);
			}
		});

		qianContainer.setOnActionUpListener(new OnActionUpListener() {

			@Override
			public void onActionUp(View view, int position) {
				// 判断当前点击的item是否被选中了
				if (!qianNums.contains(position)) {
					// 如果没有被选中
					// 背景图片切换到红色
					view.setBackgroundResource(R.drawable.id_redball);
					qianNums.add(position);
				} else {
					// 被选中
					// 还原背景图片
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					qianNums.remove((Object) (position));
				}

				changeNotice(1);
			}
		});

		baiContainer.setOnActionUpListener(new OnActionUpListener() {

			@Override
			public void onActionUp(View view, int position) {
				// 判断当前点击的item是否被选中了
				if (!baiNums.contains(position)) {
					// 如果没有被选中
					// 背景图片切换到红色
					view.setBackgroundResource(R.drawable.id_redball);
					baiNums.add(position);
				} else {
					// 被选中
					// 还原背景图片
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					baiNums.remove((Object) (position));
				}

				changeNotice(1);
			}
		});

		shiContainer.setOnActionUpListener(new OnActionUpListener() {

			@Override
			public void onActionUp(View view, int position) {
				// 判断当前点击的item是否被选中了
				if (!shiNums.contains(position)) {
					// 如果没有被选中
					// 背景图片切换到红色
					view.setBackgroundResource(R.drawable.id_redball);
					shiNums.add(position);
				} else {
					// 被选中
					// 还原背景图片
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					shiNums.remove((Object) (position));
				}

				changeNotice(1);
			}
		});

		geContainer.setOnActionUpListener(new OnActionUpListener() {

			@Override
			public void onActionUp(View view, int position) {
				// 判断当前点击的item是否被选中了
				if (!geNums.contains(position)) {
					// 如果没有被选中
					// 背景图片切换到红色
					view.setBackgroundResource(R.drawable.id_redball);
					geNums.add(position);
				} else {
					// 被选中
					// 还原背景图片
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					geNums.remove((Object) (position));
				}

				changeNotice(1);
			}
		});

		assembleContainer.setOnActionUpListener(new OnActionUpListener() {

			@Override
			public void onActionUp(View view, int position) {
				// 判断当前点击的item是否被选中了
				if (!assembleNums.contains(position)) {
					// 如果没有被选中
					// 背景图片切换到红色
					view.setBackgroundResource(R.drawable.id_redball);
					// 摇晃的动画
					//view.startAnimation(AnimationUtils.loadAnimation(context,R.anim.ia_ball_shake));
					assembleNums.add(position);
				} else {
					// 被选中
					// 还原背景图片
					view.setBackgroundResource(R.drawable.id_defalut_ball);
					assembleNums.remove((Object) (position));
				}
				changeNotice(1);
			}
		});

	}

	@Override
	public int getID() {
		return ConstantValue.VIEW_SSC;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nb_lottery_buy_bottom:
			randomMethod();
			break;
		}
		super.onClick(v);
	}

	@Override
	public void onResume() {
		mopenTimer.setTime(0,0,0);
		mopenTimer.start();
		salestime.setTime(0,0,0);
		salestime.start();
		//getCurrentIssueInfo();
		setLotteryId(bundle.getInt("lotteryid"));
		setLotteryType(bundle.getInt("lotterytype"));
		setLotteryName(bundle.getString("lotteryname"));
		
		playLotteryName.setText(bundle.getString("lotteryname"));
		String code="- - - - -";
		String lastissuetext = context.getResources().getString(R.string.is_playssc_lastissue_wait_label);
		SpannableStringBuilder laststyle=null;  
		lastissuetext = StringUtils.replaceEach(lastissuetext, new String[] { "ISSUE", "NUMBER" }, new String[] {"--", code });
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
		setSelectLucreMode(LucreModeList.getLucremode().get(0));	//元角分
		ShoppingCart.getInstance().setLucreMode(getSelectLucreMode()); // 初使化 模式
		BottomManager.getInstrance().changeGameBottomMoneyMode(getSelectLucreMode());
		changeNotice(-1); // 初始化底部
		clearMethod();
		
		// 注册
		listener = new ShakeListener(context) {

			@Override
			public void randomCure() {
				randomMethod();
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
		wanNums.clear();
		qianNums.clear();
		baiNums.clear();
		shiNums.clear();
		geNums.clear();
		assembleNums.clear();
		changeNotice(-1);

		wanAdapter.notifyDataSetChanged();
		qianAdapter.notifyDataSetChanged();
		baiAdapter.notifyDataSetChanged();
		shiAdapter.notifyDataSetChanged();
		geAdapter.notifyDataSetChanged();
		assembleAdapter.notifyDataSetChanged();

	}

	/**
	 * 随机生成选好
	 */
	@Override
	public void randomMethod() {
		// TODO Auto-generated method stub
		randomPickPane();
		changeNotice(1);
	}

	/**
	 * 设置元角分模式
	 */
	@Override
	public void setMoneyModeMethod(LucreMode mode) {
		// TODO Auto-generated method stub
		setSelectLucreMode(mode);
		ShoppingCart.getInstance().setLucreMode(getSelectLucreMode());
		changeNotice(1);
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
			changeNotice(-1);
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
	private void changeNotice(int guide) {
		ChangeBottomInfo(guide);
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

	private PlayMenu selectplay = null;
	private LucreMode lucreModeSelect = null;
	
	/**
	 * 开奖期号请求
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
	 * 销售期号请求
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
	 * 
	 * 当前期号 投注结束请求服务器
	 */
	private void currentIssueInfo(Message result) {
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
			salestime.setTime(timeRecord.getHour(),timeRecord.getMinute(),timeRecord.getSecond());
			SpannableStringBuilder laststyle=null;  
			int fstart=lastissuetext.indexOf(code);
	        int fend=fstart+code.length();
	        laststyle=new SpannableStringBuilder(lastissuetext); 
	        laststyle.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.goldenrod)),fstart,fend,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); 
			issueNumber.setText(laststyle);
			issueSales.setText(salesissuetext);
			
			if(codenum.length()>0&&codenum!=null){
				PromptManager.showToast(context, "第"+ previousIssueLast.getIssue()+ "期已经开奖");
			}else{
				PromptManager.showToast(context, "第"+ previousIssueLast.getIssue()+ "期正在开奖");
			}
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
	public PlayMenu getSelectplay() {
		return selectplay;
	}

	public void setSelectplay(PlayMenu selectplay) {
		this.selectplay = selectplay;
	}

	public void setSelectLucreMode(LucreMode selectmode) {
		// TODO Auto-generated method stub
		this.lucreModeSelect = selectmode;
	}

	public LucreMode getSelectLucreMode() {
		// TODO Auto-generated method stub
		return lucreModeSelect;
	}
	
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
			DecimalFormat decimalFormat = new DecimalFormat("0");
			Ticket ticket = new Ticket();
			bundle.putInt("lotteryid",bundle.getInt("lotteryid"));
			bundle.putInt("lotterytype", bundle.getInt("lotterytype"));
			bundle.putString("lotteryname", bundle.getString("lotteryname"));
			
			PlayMenu playmenuKey = getSelectplay();
			if (playmenuKey.getJscode().equalsIgnoreCase("ZX3")) { // 前三 后三直选
				if (playmenuKey.getPlaymode().equals("AGO")) {
					if ((wanNums.size() > 0) && (qianNums.size() > 0) && (baiNums.size() > 0)) // 如果万千百其中一个等于0就进入
					{
						StringBuffer wanBuffer = new StringBuffer();
						Collections.sort(wanNums);
						for (Integer item : wanNums) {
							wanBuffer.append(" ").append(decimalFormat.format(item));
							
						}
						ticket.setWanNum(wanBuffer.substring(1));

						StringBuffer qianBuffer = new StringBuffer();
						Collections.sort(qianNums);
						for (Integer item : qianNums) {
							qianBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setQianNum(qianBuffer.substring(1));

						StringBuffer baiBuffer = new StringBuffer();
						Collections.sort(baiNums);
						for (Integer item : baiNums) {
							baiBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setBaiNum(baiBuffer.substring(1));

						ticket.setNum(getBetsdigit());
						ticket.setMoneyMode(getSelectLucreMode());
						ticket.setMultiple(1);
						ticket.setSelectPlay(playmenuKey);
						
						int betsdigit=Calculation.getInstance().calculationNote(wanNums,qianNums,baiNums,null,null, null,playmenuKey);
						
						BigDecimal b=new BigDecimal(betsdigit * 2 * ShoppingCart.getInstance().getLucreMode().getLucreReckon());  
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
					} else {
						agoThreeRandom();
					}
				} else if (playmenuKey.getPlaymode().equals("AFTER")) {
					if ((baiNums.size() > 0) && (shiNums.size() > 0) && (geNums.size() > 0)) {
						StringBuffer baiBuffer = new StringBuffer();
						Collections.sort(baiNums);
						for (Integer item : baiNums) {
							baiBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setBaiNum(baiBuffer.substring(1));

						StringBuffer shiBuffer = new StringBuffer();
						Collections.sort(shiNums);
						for (Integer item : shiNums) {
							shiBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setShiNum(shiBuffer.substring(1));

						StringBuffer geBuffer = new StringBuffer();
						Collections.sort(geNums);
						for (Integer item : geNums) {
							geBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setGeNum(geBuffer.substring(1));

						ticket.setNum(getBetsdigit());
						ticket.setSelectPlay(playmenuKey);
						ticket.setMultiple(1);
						ticket.setMoneyMode(getSelectLucreMode());
						
						int betsdigit=Calculation.getInstance().calculationNote(null,null,baiNums,shiNums,geNums, null,playmenuKey);
						
						BigDecimal b=new BigDecimal(betsdigit * 2 * ShoppingCart.getInstance().getLucreMode().getLucreReckon());  
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
					} else {
						afterThreeRandom();
					}
				}
			} else if (playmenuKey.getJscode().equalsIgnoreCase("ZUS")) { // 前三 后三 组选 组三 C(n,2)*2
				if (assembleNums.size() >= 2) {
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSscNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(null,null,null,null,null, assembleNums,playmenuKey);
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * ShoppingCart.getInstance().getLucreMode().getLucreReckon());  
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
			} else if (playmenuKey.getJscode().equalsIgnoreCase("ZUL")) { // 前三 后三组选 组六 C(n,3)
				if (assembleNums.size() >= 3) {
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSscNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(null,null,null,null,null, assembleNums,playmenuKey);
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * ShoppingCart.getInstance().getLucreMode().getLucreReckon());  
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
					// ⑤设置彩种的标示，设置彩种期次
					ShoppingCart.getInstance().setPlaymenu(playmenuKey);
					ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
					ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
					ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
					ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
					// ⑥界面跳转——购物车展示
					HallMiddleManager.getInstance().changeUI(Shopping.class,bundle);

				} else {
					groupSixRandom();
				}
			} else if (playmenuKey.getJscode().equalsIgnoreCase("ZX2")) { // 前二  后二直选 n1*n2
				if (playmenuKey.getPlaymode().equals("AGO")) {
					if ((wanNums.size() > 0) && (qianNums.size() > 0)) {
						StringBuffer wanBuffer = new StringBuffer();
						Collections.sort(wanNums);
						for (Integer item : wanNums) {
							wanBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setWanNum(wanBuffer.substring(1));

						StringBuffer qianBuffer = new StringBuffer();
						for (Integer item : qianNums) {
							qianBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setQianNum(qianBuffer.substring(1));
						
						ticket.setNum(getBetsdigit());
						ticket.setSelectPlay(playmenuKey);
						ticket.setMultiple(1);
						ticket.setMoneyMode(getSelectLucreMode());
						
						int betsdigit=Calculation.getInstance().calculationNote(wanNums,qianNums,null,null,null,null,playmenuKey);
						
						BigDecimal b=new BigDecimal(betsdigit * 2 * ShoppingCart.getInstance().getLucreMode().getLucreReckon());  
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
						ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
						ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
						ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
						ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
						// ⑥界面跳转——购物车展示
						HallMiddleManager.getInstance().changeUI(Shopping.class, bundle);

					} else {
						agoTwoRandom();
					}
				} else if (playmenuKey.getPlaymode().equals("AFTER")) {
					if ((shiNums.size() > 0) && (geNums.size() > 0)) {

						StringBuffer shiBuffer = new StringBuffer();
						Collections.sort(shiNums);
						for (Integer item : shiNums) {
							shiBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setShiNum(shiBuffer.substring(1));

						StringBuffer geBuffer = new StringBuffer();
						Collections.sort(geNums);
						for (Integer item : geNums) {
							geBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setGeNum(geBuffer.substring(1));
						
						ticket.setNum(getBetsdigit());
						ticket.setSelectPlay(playmenuKey);
						ticket.setMultiple(1);
						ticket.setMoneyMode(getSelectLucreMode());
						
						int betsdigit=Calculation.getInstance().calculationNote(null,null,null,shiNums,geNums, null,playmenuKey);
						
						BigDecimal b=new BigDecimal(betsdigit * 2 * ShoppingCart.getInstance().getLucreMode().getLucreReckon());  
						double m =b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
						
						ticket.setNoteMoney(m);
						ticket.setLotteryid(bundle.getInt("lotteryid"));
						ticket.setLotterytype(bundle.getInt("lotterytype"));
						
						ShoppingCart.getInstance().setPlaymenu(playmenuKey);
						// ④创建彩票购物车，将投注信息添加到购物车中
						if(ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid"))==null){
							List<Ticket> ticketList=new ArrayList<Ticket>();
							ticketList.add(ticket);
							ShoppingCart.getInstance().getTicketsMap().put(bundle.getInt("lotteryid"), ticketList);
						}else{
							ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).add(ticket);
						}
						// ⑤设置彩种的标示，设置彩种期次
						ShoppingCart.getInstance().setIssue(bundle.getString("issue"));
						ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
						ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
						ShoppingCart.getInstance().setLotteryname(bundle.getString("lotteryname"));
						// ⑥界面跳转——购物车展示
						HallMiddleManager.getInstance().changeUI(Shopping.class, bundle);

					} else {
						afterTwoRandom();
					}
				}
			} else if (playmenuKey.getJscode().equalsIgnoreCase("ZU2")) { // 前二 后二组选 C(n,2)
				if ((assembleNums.size() >= 2)) {
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSscNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(null,null,null,null,null, assembleNums,playmenuKey);
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * ShoppingCart.getInstance().getLucreMode().getLucreReckon());  
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
					group_2_Random();
				}
			} else if (playmenuKey.getJscode().equalsIgnoreCase("DWD")) { // 个、十、百、千、万 C(n,1)
				
				if ((wanNums.size() > 0) || (qianNums.size() > 0) || (baiNums.size() > 0) || (shiNums.size() > 0) || (geNums.size() > 0)) {
					if(wanNums.size() > 0){
						StringBuffer wanBuffer = new StringBuffer();
						Collections.sort(wanNums);
						for (Integer item : wanNums) {
							wanBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setWanNum(wanBuffer.substring(1));
					}
					
					if(qianNums.size() > 0){
						StringBuffer qianBuffer = new StringBuffer();
						Collections.sort(qianNums);
						for (Integer item : qianNums) {
							qianBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setQianNum(qianBuffer.substring(1));
					}
					
					if(baiNums.size() > 0){
						StringBuffer baiBuffer = new StringBuffer();
						Collections.sort(baiNums);
						for (Integer item : baiNums) {
							baiBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setBaiNum(baiBuffer.substring(1));
					}
					
					if(shiNums.size() > 0){
						StringBuffer shiBuffer = new StringBuffer();
						Collections.sort(shiNums);
						for (Integer item : shiNums) {
							shiBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setShiNum(shiBuffer.substring(1));
					}

					if(geNums.size() > 0){
						StringBuffer geBuffer = new StringBuffer();
						Collections.sort(geNums);
						for (Integer item : geNums) {
							geBuffer.append(" ").append(decimalFormat.format(item));
						}
						ticket.setGeNum(geBuffer.substring(1));
					}

					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(wanNums,qianNums,baiNums,shiNums,geNums, assembleNums,playmenuKey);
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * ShoppingCart.getInstance().getLucreMode().getLucreReckon());  
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
				} else {
					afterThreeRandom();
				}													 

			} else if (playmenuKey.getJscode().equalsIgnoreCase("BDW1")) { // 后三一码不定位 前三一码不定位 C(n,1)
				if (assembleNums.size() >= 1) {
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSscNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(null,null,null,null,null, assembleNums,playmenuKey);
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * ShoppingCart.getInstance().getLucreMode().getLucreReckon());  
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
					group_1_Random();
				}
			}else if(playmenuKey.getJscode().equals("BDW2")){
				if (assembleNums.size() >= 2) {
					StringBuffer assembleBuffer = new StringBuffer();
					Collections.sort(assembleNums);
					for (Integer item : assembleNums) {
						assembleBuffer.append(" ").append(decimalFormat.format(item));
					}
					ticket.setAssembleSscNum(assembleBuffer.substring(1));
					
					ticket.setNum(getBetsdigit());
					ticket.setSelectPlay(playmenuKey);
					ticket.setMultiple(1);
					ticket.setMoneyMode(getSelectLucreMode());
					
					int betsdigit=Calculation.getInstance().calculationNote(null,null,null,null,null, assembleNums,playmenuKey);
					
					BigDecimal b=new BigDecimal(betsdigit * 2 * ShoppingCart.getInstance().getLucreMode().getLucreReckon());  
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
					group_2_Random();
				}
			}
			changeNotice(1);
		} else {
			// 重新获取期次信息
			getCurrentIssueInfo();
		}
	}

	/**
	 * 更改底部投注 注数，金额信息
	 */
	private void ChangeBottomInfo(int guide) {
		String notice = "";
		setBetsdigit(0);
		double money = 0.00;
		// 以一注为分割
		PlayMenu playmenuKey = getSelectplay();
		if (playmenuKey.getJscode().equalsIgnoreCase("ZX3")) { // 前三 后三直选
			if (playmenuKey.getPlaymode().equals("AGO")) {
				if ((wanNums.size() > 0) && (qianNums.size() > 0) && (baiNums.size() > 0)){ // 如果万千百其中一个等于0就进入
					setBetsdigit(Calculation.getInstance().calculationNote(wanNums, qianNums, baiNums, shiNums, geNums, assembleNums, getSelectplay()));
					money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
				}
			} else if (playmenuKey.getPlaymode().equals("AFTER")) {
				if ((baiNums.size() > 0) && (shiNums.size() > 0) && (geNums.size() > 0)) {
					setBetsdigit(Calculation.getInstance().calculationNote(wanNums, qianNums, baiNums, shiNums, geNums, assembleNums, getSelectplay()));
					money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
				}
			}
		} else if (playmenuKey.getJscode().equalsIgnoreCase("ZUS")) { // 前三 后三组选 组三 C(n,2)*2
			if (assembleNums.size() >= 2) {
				setBetsdigit(Calculation.getInstance().calculationNote(wanNums, qianNums, baiNums, shiNums, geNums, assembleNums, getSelectplay()));
				money = getBetsdigit() * 2* getSelectLucreMode().getLucreReckon();
			}
		} else if (playmenuKey.getJscode().equalsIgnoreCase("ZUL")) { // 前三 后三组选 组六 C(n,3)
			if (assembleNums.size() >= 2) {
				setBetsdigit(Calculation.getInstance().calculationNote( wanNums, qianNums, baiNums, shiNums, geNums, assembleNums, getSelectplay()));
				money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
			}
		} else if (playmenuKey.getJscode().equalsIgnoreCase("ZX2")) { // 前二 后二直选 n1*n2
			if (playmenuKey.getPlaymode().equals("AGO")) {
				if ((wanNums.size() > 0) && (qianNums.size() > 0)) {
					setBetsdigit(Calculation.getInstance().calculationNote(wanNums, qianNums, baiNums, shiNums, geNums, assembleNums, getSelectplay()));
					money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
				}
			} else if (playmenuKey.getPlaymode().equals("AFTER")) {
				if ((shiNums.size() > 0) && (geNums.size() > 0)) {
					setBetsdigit(Calculation.getInstance().calculationNote(wanNums, qianNums, baiNums, shiNums, geNums, assembleNums, getSelectplay()));
					money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
				}
			}
		} else if (playmenuKey.getJscode().equalsIgnoreCase("ZU2")) { // 前二 后二组选 C(n,2)
			if ((assembleNums.size() >= 2)) {
				setBetsdigit(Calculation.getInstance().calculationNote(wanNums, qianNums, baiNums, shiNums, geNums, assembleNums, getSelectplay()));
				money = getBetsdigit() * 2* getSelectLucreMode().getLucreReckon();
			}
		} else if (playmenuKey.getJscode().equalsIgnoreCase("DWD")) { // 个、十、百、千、万 C(n,1)
			if ((wanNums.size() != 0) || (qianNums.size() != 0) || (baiNums.size() != 0) || (shiNums.size() != 0) || (geNums.size() != 0)){ // 如果万千百其中一个等于0就进入
				setBetsdigit(Calculation.getInstance().calculationNote(wanNums, qianNums, baiNums, shiNums, geNums, assembleNums, getSelectplay()));
				money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();	
			}
		} else if (playmenuKey.getJscode().equals("BDW2")) {		//前三二码不定位
			if ((assembleNums.size() >= 2)) {
				setBetsdigit(Calculation.getInstance().calculationNote(wanNums, qianNums, baiNums, shiNums, geNums, assembleNums, getSelectplay()));
				money = getBetsdigit() * 2* getSelectLucreMode().getLucreReckon();
			}
		}
		else if (playmenuKey.getJscode().equalsIgnoreCase("BDW1")) { // 后三一码不定位 前三一码不定位 C(n,1)
			if (assembleNums.size() >= 1) {
				setBetsdigit(Calculation.getInstance().calculationNote(wanNums, qianNums, baiNums, shiNums, geNums, assembleNums, getSelectplay()));
				money = getBetsdigit() * 2 * getSelectLucreMode().getLucreReckon();
			}
		}
		
		BigDecimal b=new BigDecimal(money);  
		double m =b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		
		String noticeInfo = context.getResources().getString(R.string.is_shopping_list_betting_notice);
		noticeInfo = StringUtils.replaceEach(noticeInfo, new String[] {"NUM", "MONEY","MUL"}, new String[] { String.valueOf(betsdigit),String.valueOf(m),String.valueOf(1)});
		
		// 投注数量
		promptNotice(noticeInfo);
		//BottomManager.getInstrance().changeGameBottomBetsNotice(noticeInfo,betsdigit);
	}

	/**
	 * 随机选择一注号码
	 */
	private void randomPickPane() {
		PlayMenu playmenuKey = getSelectplay();
		
		if (playmenuKey.getJscode().equals("ZX3")) 				//前三直选	后三直选
		{
			if(playmenuKey.getPlaymode().equals("AGO"))
				agoThreeRandom();
			else if(playmenuKey.getPlaymode().equals("AFTER"))
				afterThreeRandom();
		} else if (playmenuKey.getJscode().equals("ZX2")) 		//前二直选	后二直选
		{
			if(playmenuKey.getPlaymode().equals("AGO"))
				agoTwoRandom();
			else if(playmenuKey.getPlaymode().equals("AFTER"))
				afterTwoRandom();
		} else if (playmenuKey.getJscode().equals("ZUS")) { 	//前三组选_组三	后三组选_组三
			groupThreeRandom();
		} else if (playmenuKey.getJscode().equals("ZUL")){ 		//前三组选_组六	后三组选_组六
			groupSixRandom();
		} else if (playmenuKey.getJscode().equals("ZU2")){ 		//前二组选	后二组选
			group_2_Random();
		} else if (playmenuKey.getJscode().equals("BDW1")){ 	//前三一码不定位	后三一码不定位
			group_1_Random();
		}else if (playmenuKey.getJscode().equals("BDW2")) {		//前三二码不定位 后三二码不定位
			group_2_Random();
		}else if(playmenuKey.getJscode().equals("DWD")){		//定位单
			fiveRandom();
		}
	
	}

	/**
	 * 更新选号 界面
	 * 
	 * @param playmenuKey
	 * @param lotteryId
	 */
	private void updatePickPane() {

		PlayMenu playmenuKey = getSelectplay();
		
		if (playmenuKey.getJscode().equals("ZX3")) 				//前三直选	
		{
			if(playmenuKey.getMethodid().equals("27")||playmenuKey.getMethodid().equals("1742")||playmenuKey.getMethodid().equals("1469")){
				agoThreeReveal();
				getfullPlay(0);
			}else if(playmenuKey.getMethodid().equals("11")||playmenuKey.getMethodid().equals("1453")||playmenuKey.getMethodid().equals("1726")){	//后三直选
				afterThreeReveal();
				getfullPlay(1);
			}
		} else if (playmenuKey.getJscode().equals("ZX2")) 		//前二直选	后二直选
		{
			if(playmenuKey.getMethodid().equals("43")||playmenuKey.getMethodid().equals("1485")||playmenuKey.getMethodid().equals("1758")){
				agoTwoReveal();
				getfullPlay(0);
			}else if(playmenuKey.getMethodid().equals("47")||playmenuKey.getMethodid().equals("1489")||playmenuKey.getMethodid().equals("1762")){
				afterTwoReveal();
				getfullPlay(1);
			}
		} else if (playmenuKey.getJscode().equals("ZUS")) { 	//前三组选_组三	后三组选_组三
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_ssc_assemble_point_3);
			assemble_Reveal(title, point);
		} else if (playmenuKey.getJscode().equals("ZUL")){ 		//前三组选_组六	后三组选_组六
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_ssc_assemble_point_6);
			assemble_Reveal(title, point);
		} else if (playmenuKey.getJscode().equals("ZU2")){ 		//前二组选	后二组选
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_ssc_assemble_point);
			assemble_Reveal(title, point);
		} else if (playmenuKey.getJscode().equals("BDW1")){ 	//前三一码不定位	后三一码不定位
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_ssc_notlocate_point);
			assemble_Reveal(title, point);
		}else if (playmenuKey.getJscode().equals("BDW2")) {		//前三二码不定位
			String title = playmenuKey.getMethodname();
			String point = context.getResources().getString(R.string.is_hall_ssc_assemble_point);
			assemble_Reveal(title, point);
		}else if(playmenuKey.getJscode().equals("DWD")){		//定位单
			fivesReveal();
		}
	}

	/**
	 * 更新玩法参数 供后面使用
	 * @param mode
	 */
	private void getfullPlay(int mode) {
		PlayMenu playmenu = getSelectplay();
		playmenu.setPlaymode(playmenu.getPlaymodeList().get(mode));
		setSelectplay(playmenu);
	}

	private void HandlingShow() {
		// 处理展示
		wanAdapter.notifyDataSetChanged();
		qianAdapter.notifyDataSetChanged();
		baiAdapter.notifyDataSetChanged();
		shiAdapter.notifyDataSetChanged();
		geAdapter.notifyDataSetChanged();
		assembleAdapter.notifyDataSetChanged();
	}

	private void fiveRandom() {
		Random random = new Random();
		wanNums.clear();
		int wannum = random.nextInt(10);
		wanNums.add(wannum);

		qianNums.clear();
		int qiannum = random.nextInt(10);
		qianNums.add(qiannum);

		baiNums.clear();
		int bainum = random.nextInt(10);
		baiNums.add(bainum);

		shiNums.clear();
		int shinum = random.nextInt(10);
		shiNums.add(shinum);

		geNums.clear();
		int genum = random.nextInt(10);
		geNums.add(genum);
		HandlingShow();
	}

	/**
	 * 随机前三
	 */
	private void agoThreeRandom() {
		Random random = new Random();
		wanNums.clear();
		int wannum = random.nextInt(10);
		wanNums.add(wannum);

		qianNums.clear();
		int qiannum = random.nextInt(10);
		qianNums.add(qiannum);

		baiNums.clear();
		int bainum = random.nextInt(10);
		baiNums.add(bainum);
		HandlingShow();
	}

	/**
	 * 随机后三
	 */
	private void afterThreeRandom() {
		Random random = new Random();
		baiNums.clear();
		int bainum = random.nextInt(10);
		baiNums.add(bainum);

		shiNums.clear();
		int shinum = random.nextInt(10);
		shiNums.add(shinum);

		geNums.clear();
		int genum = random.nextInt(10);
		geNums.add(genum);
		HandlingShow();
	}

	/**
	 * 随机组三
	 */
	private void groupThreeRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 2) {
			int num = random.nextInt(10);

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}

	/**
	 * 随机组六
	 */
	private void groupSixRandom() {
		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 3) {
			int num = random.nextInt(10);

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}
	/**
	 * 随机 组2
	 */
	private void group_2_Random() {

		Random random = new Random();
		assembleNums.clear();
		while (assembleNums.size() < 2) {
			int num = random.nextInt(10);

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		HandlingShow();
	}

	private void group_1_Random() {

		Random random = new Random();
		assembleNums.clear();
		int assemblenum = random.nextInt(10);
		assembleNums.add(assemblenum);
		HandlingShow();
	}

	/**
	 * 随机前二
	 */
	private void agoTwoRandom() {
		Random random = new Random();
		wanNums.clear();
		int wannum = random.nextInt(10);
		wanNums.add(wannum);

		qianNums.clear();
		int qiannum = random.nextInt(10);
		qianNums.add(qiannum);
		HandlingShow();
	}

	/**
	 * 随机后二
	 */
	private void afterTwoRandom() {
		Random random = new Random();
		shiNums.clear();
		int shinum = random.nextInt(10);
		shiNums.add(shinum);

		geNums.clear();
		int genum = random.nextInt(10);
		geNums.add(genum);
		HandlingShow();
	}

	/**
	 * 隐藏
	 */
	private void initWanPickFaceHide() {
		wanFaceContainer.setVisibility(View.GONE);
	}

	private void initQianPickFaceHide() {
		qianFaceContainer.setVisibility(View.GONE);
	}

	private void initBaiPickFaceHide() {
		baiFaceContainer.setVisibility(View.GONE);
	}

	private void initShiPickFaceHide() {
		shiFaceContainer.setVisibility(View.GONE);
	}

	private void initGePickFaceHide() {
		geFaceContainer.setVisibility(View.GONE);
	}

	private void initAssemblePickFaceHide() {
		assembleFaceContainer.setVisibility(View.GONE);
	}

	/**
	 * 显示
	 */
	private void initWanPickFaceShow() {
		wanFaceContainer.setVisibility(View.VISIBLE);
	}

	private void initQianPickFaceShow() {
		qianFaceContainer.setVisibility(View.VISIBLE);
	}

	private void initBaiPickFaceShow() {
		baiFaceContainer.setVisibility(View.VISIBLE);
	}

	private void initShiPickFaceShow() {
		shiFaceContainer.setVisibility(View.VISIBLE);
	}

	private void initGePickFaceShow() {
		geFaceContainer.setVisibility(View.VISIBLE);
	}

	private void initAssemblePickFaceShow() {
		assembleFaceContainer.setVisibility(View.VISIBLE);
	}

	private void allRevealHide() {
		initWanPickFaceHide();
		initQianPickFaceHide();
		initBaiPickFaceHide();
		initShiPickFaceHide();
		initGePickFaceHide();
		initAssemblePickFaceHide();
	}

	private void allRevealShow() {
		initWanPickFaceShow();
		initQianPickFaceShow();
		initBaiPickFaceShow();
		initShiPickFaceShow();
		initGePickFaceShow();
		initAssemblePickFaceShow();
	}

	private void setAssembleRitle(String title, String prompt) {
		assembleTitle.setText(title + "\t\t\t\t\t" + prompt);
	}

	/**
	 * 前三个
	 */
	private void agoThreeReveal() {
		initAssemblePickFaceHide();
		initWanPickFaceShow();
		initQianPickFaceShow();
		initBaiPickFaceShow();
		initShiPickFaceHide();
		initGePickFaceHide();
	}

	/**
	 * 后三
	 */
	private void afterThreeReveal() {
		initAssemblePickFaceHide();
		initWanPickFaceHide();
		initQianPickFaceHide();
		initBaiPickFaceShow();
		initShiPickFaceShow();
		initGePickFaceShow();
	}

	/**
	 * 三码 组选 3 组选 6 二码2 一码
	 */
	private void assemble_Reveal(String title, String prompt) {
		setAssembleRitle(title, prompt);
		allRevealHide();
		initAssemblePickFaceShow();
	}

	/**
	 * 前二
	 */
	private void agoTwoReveal() {
		initAssemblePickFaceHide();
		initWanPickFaceShow();
		initQianPickFaceShow();
		initBaiPickFaceHide();
		initShiPickFaceHide();
		initGePickFaceHide();
	}

	/**
	 * 后二
	 */
	private void afterTwoReveal() {
		initAssemblePickFaceHide();
		initWanPickFaceHide();
		initQianPickFaceHide();
		initBaiPickFaceHide();
		initShiPickFaceShow();
		initGePickFaceShow();
	}

	private void fivesReveal() {
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
