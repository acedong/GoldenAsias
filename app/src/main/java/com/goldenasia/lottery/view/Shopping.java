package com.goldenasia.lottery.view;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.BettingInfo;
import com.goldenasia.lottery.bean.ConstantInformation;
import com.goldenasia.lottery.bean.IssueAll;
import com.goldenasia.lottery.bean.IssueSales;
import com.goldenasia.lottery.bean.LucreModeList;
import com.goldenasia.lottery.bean.PlayMenu;
import com.goldenasia.lottery.bean.ProgramBuyInfo;
import com.goldenasia.lottery.bean.RecordTime;
import com.goldenasia.lottery.bean.ShoppingCart;
import com.goldenasia.lottery.bean.Ticket;
import com.goldenasia.lottery.engine.BettingEngine;
import com.goldenasia.lottery.engine.CommonInfoEngine;
import com.goldenasia.lottery.engine.UserEngine;
import com.goldenasia.lottery.net.protocal.Message;
import com.goldenasia.lottery.net.protocal.Oelement;
import com.goldenasia.lottery.net.protocal.element.BalanceElement;
import com.goldenasia.lottery.net.protocal.element.TaskIssueInfoElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.Calculation;
import com.goldenasia.lottery.util.CustomDialog;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.ShoppingAdapter;
import com.goldenasia.lottery.view.controls.KeyBoardDialog;
import com.goldenasia.lottery.view.controls.PayPasswordView;
import com.goldenasia.lottery.view.controls.PayPasswordView.OnPayListener;
import com.goldenasia.lottery.view.controls.RushBuyCountDownTimerView;
import com.goldenasia.lottery.view.custom.BaseSwipeListViewListener;
import com.goldenasia.lottery.view.custom.SwipeListView;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.HallMiddleManager;
import com.goldenasia.lottery.view.manager.ShopManage;
import com.goldenasia.lottery.view.manager.TitleManager;

/**
 * 购物车展示
 * 
 * @author Ace
 * 
 */
public class Shopping extends BaseUI implements ShopManage {
	// 通用三步

	private Button addOptional; // 添加自选
	private Button addRandom; // 添加机选
	private Button doubleMinusBut; // 倍数减
	private Button doublePlusBut; // 倍数加
	private Button appendMinusBut; // 追号期数 减
	private Button appendPlusBut; // 追号期数 加

	private SwipeListView shoppingList; // 用户选择信息列表
	private ToggleButton mTogBtn;
	// private Button shoppingListClear; // 清空购物车
	private TextView noticeText; // 提示信息
	private TextView moneyText; // 金额
	private EditText appendText; // 追号期数
	private EditText doubleText; // 追号倍数
	private LinearLayout shoppingHolding;
	private CheckBox appendSettings; // 追号设置
	private Button buy; // 购买

	private Button appendbettingBut; // 智能追号
	private ShoppingAdapter adapter;

	private TextView salesissue;
	// private Anticlockwise shopTimer;
	private RushBuyCountDownTimerView shopTimer;
	private KeyBoardDialog keyboard;
	private Activity activity;
	// 1、填充购物车
	// 2、添加自选+添加机选
	// 3、清空购物车
	// 4、高亮的提示信息处理
	// 5、追号
	// 6、购买
	public Shopping(Context context) {
		super(context);
		activity=(Activity)context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		showInMiddle = (ViewGroup) View.inflate(context, R.layout.nb_shopping,null);
		
		salesissue = (TextView) findViewById(R.id.top_sales_issue);
		shopTimer = (RushBuyCountDownTimerView) findViewById(R.id.top_sales_time);

		addOptional = (Button) findViewById(R.id.nb_add_optional);
		addRandom = (Button) findViewById(R.id.nb_add_random);
		// shoppingListClear = (Button)
		// findViewById(R.id.nb_shopping_list_clear);
		noticeText = (TextView) findViewById(R.id.nb_shopping_lottery_notice);
		moneyText = (TextView) findViewById(R.id.nb_shopping_lottery_money);
		buy = (Button) findViewById(R.id.nb_lottery_shopping_buy);
		mTogBtn = (ToggleButton) findViewById(R.id.mTogBtn); // 获取到控件
		shoppingList = (SwipeListView) findViewById(R.id.nb_shopping_list);

		appendText = (EditText) findViewById(R.id.nb_shopping_append_text);
		doubleText = (EditText) findViewById(R.id.nb_shopping_double_text);

		appendMinusBut = (Button) findViewById(R.id.nb_shopping_append_minus);
		appendPlusBut = (Button) findViewById(R.id.nb_shopping_append_plus);
		doubleMinusBut = (Button) findViewById(R.id.nb_shopping_double_minus);
		doublePlusBut = (Button) findViewById(R.id.nb_shopping_double_plus);

		appendSettings = (CheckBox) findViewById(R.id.nb_shopping_append_settings);
		shoppingHolding = (LinearLayout) findViewById(R.id.nb_shopping_holding);
		appendbettingBut = (Button) findViewById(R.id.nb_lottery_shopping_initiate_buy);
	}

	@Override
	public void setListener() {

		// shopTimer.setOnTimeCompleteListener(new
		// Anticlockwise.OnTimeCompleteListener()
		// {
		// @Override
		// public void onTimeComplete(View v) {
		// getCurrentIssueInfo();
		// }
		// });

		shopTimer.setOncountdown(new RushBuyCountDownTimerView.OnUpdateCountdownListener() {

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

		shoppingList.setOffsetLeft(context.getResources().getDisplayMetrics().widthPixels * 2 / 3);
		adapter = new ShoppingAdapter(context, shoppingList);
		shoppingList.setAdapter(adapter);
		shoppingList.setSwipeListViewListener(new BaseSwipeListViewListener() {

			@Override
			public void onClickFrontView(int position) {
				super.onClickFrontView(position);
				shoppingList.closeOpenedItems();
			}

			@Override
			public void onDismiss(int[] reverseSortedPositions) {
				super.onDismiss(reverseSortedPositions);
				for (int i : reverseSortedPositions) {
					ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).remove(i);
				}
				adapter.notifyDataSetChanged();
				changeNotice();
			}

		});

		// ShoppingCart.getInstance().getTickets().remove(position);
		// adapter.notifyDataSetChanged();
		// shoppingList.invalidate();
		// changeNotice();

		String resplusIco = context.getResources().getString(R.string.fa_plus);
		String resminusIco = context.getResources().getString(R.string.fa_minus);
		String resOptionalText = context.getResources().getString(R.string.is_shopping_button_optional);
		addOptional.setText(resplusIco + " " + resOptionalText);
		addOptional.setOnClickListener(this);
		addOptional.setTypeface(font);

		String resRandomText = context.getResources().getString(R.string.is_shopping_button_random);
		addRandom.setText(resplusIco + " " + resRandomText);
		addRandom.setTypeface(font);
		addRandom.setOnClickListener(this);

		// String
		// resTrashIco=context.getResources().getString(R.string.fa_trash_o);
		// String
		// resClearText=context.getResources().getString(R.string.is_shopping_button_clear);
		// shoppingListClear.setText(resTrashIco+" "+resClearText);
		// shoppingListClear.setTypeface(font);
		// shoppingListClear.setOnClickListener(this);

		mTogBtn = (ToggleButton) findViewById(R.id.mTogBtn); // 获取到控件
		mTogBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					// 选中
					ShoppingCart.getInstance().setRetainRebate(true);
				} else {
					// 未选中
					ShoppingCart.getInstance().setRetainRebate(false);
				}
			}
		});// 添加监听事件

		doubleMinusBut.setText(resminusIco);
		doubleMinusBut.setTypeface(font);
		doubleMinusBut.setOnClickListener(this);

		doublePlusBut.setText(resplusIco);
		doublePlusBut.setTypeface(font);
		doublePlusBut.setOnClickListener(this);

		appendMinusBut.setText(resminusIco);
		appendMinusBut.setTypeface(font);
		appendMinusBut.setOnClickListener(this);

		appendPlusBut.setText(resplusIco);
		appendPlusBut.setTypeface(font);
		appendPlusBut.setOnClickListener(this);
		
		appendText.setInputType(InputType.TYPE_NULL);
		appendText.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				keyboard.setView(getDecorViewDialog());
				keyboard.show();
				ShoppingCart.getInstance().setFlagfocus(true);
				return false;
			}
		});
		doubleText.setInputType(InputType.TYPE_NULL);
		doubleText.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				keyboard.setView(getDecorViewDialog());
				keyboard.show();
				ShoppingCart.getInstance().setFlagfocus(false);
				return false;
			}
		});
		appendSettings.setChecked(true);
		appendSettings.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0,
					boolean arg1) {
				// TODO Auto-generated method stub
				ShoppingCart.getInstance().setAppstate(arg1 ? true : false);
			}
		});

		String buttonchaseText = context.getResources().getString(R.string.is_shopping_button_chase);
		appendbettingBut.setText(buttonchaseText);
		appendbettingBut.setOnClickListener(this);
		appendbettingBut.setTypeface(font);
		buy.setOnClickListener(this);
	}

	//
	@Override
	public int getID() {
		return ConstantValue.VIEW_SHOPPING;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nb_add_optional:
			// 添加自选

			switch (bundle.getInt("lotterytype")) {
			case 0:
				HallMiddleManager.getInstance().changeUI(PlaySSC.class, bundle);
				break;
			case 1:
				break;
			case 2:
				HallMiddleManager.getInstance().changeUI(PlaySYFive.class, bundle);
				break;
			}
			// HallMiddleManager.getInstance().goBack();
			break;
		case R.id.nb_add_random:
			// 添加机选
			addRandom();
			changeNotice();
			break;
		// case R.id.nb_shopping_list_clear:
		// // 清空
		// ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).clear();
		// adapter.notifyDataSetChanged();
		// changeNotice();
		// break;
		case R.id.nb_lottery_shopping_initiate_buy: // 智能追号
			HallMiddleManager.getInstance().changeUI(AppendBetting.class, bundle);
			break;
		case R.id.nb_shopping_append_minus:
			int appendminusNo = ShoppingCart.getInstance().getIssuesnumbers();
			if (appendminusNo <= 1) {
				PromptManager.showToast(context, "追号期数不能小于1");
			}
			ShoppingCart.getInstance().addIssuesnumbers(false);
			appendText.setKeyListener(null);
			appendText.setText(ShoppingCart.getInstance().getIssuesnumbers().toString());
			changeNotice();
			shoppingHolding();
			break;
		case R.id.nb_shopping_append_plus:
			int appendplusNo = ShoppingCart.getInstance().getIssuesnumbers();
			if (appendplusNo >= 99) {
				PromptManager.showToast(context, "追号期数不能大于100");
			}
			ShoppingCart.getInstance().addIssuesnumbers(true);
			appendText.setKeyListener(null);
			appendText.setText(ShoppingCart.getInstance().getIssuesnumbers()
					.toString());
			changeNotice();
			shoppingHolding();
			break;
		case R.id.nb_shopping_double_minus:
			int doubleMinusNo = ShoppingCart.getInstance().getAppnumbers();
			if (doubleMinusNo <= 1) {
				PromptManager.showToast(context, "投注倍数不能小于1");
			}
			ShoppingCart.getInstance().addAppnumbers(false);
			doubleText.setKeyListener(null);
			doubleText.setText(ShoppingCart.getInstance().getAppnumbers()
					.toString());
			changeNotice();
			shoppingHolding();
			break;
		case R.id.nb_shopping_double_plus:
			int doublePlusNo = ShoppingCart.getInstance().getAppnumbers();
			if (doublePlusNo >= 99) {
				PromptManager.showToast(context, "投注倍数不能大于100");
			}
			ShoppingCart.getInstance().addAppnumbers(true);
			doubleText.setKeyListener(null);
			doubleText.setText(ShoppingCart.getInstance().getAppnumbers().toString());
			changeNotice();
			shoppingHolding();
			break;
		case R.id.nb_lottery_shopping_buy:
			// 购买
			// ①判断：购物车中是否有投注
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")).size() >= 1) {
				// ②判断：用户是否登录——被动登录
				if (GlobalParams.isLogin) {
					// ③判断：用户的余额是否满足投注需求
					if (ShoppingCart.getInstance().getLotteryvalue() <= GlobalParams.MONEY) {
						// ④界面跳转：跳转到追期和倍投的设置界面
						buyBetting();
						// HallMiddleManager.getInstance().changeUI(PreBet.class,
						// bundle);
					} else {
						// 提示用户：充值去；界面跳转：用户充值界面
						PromptManager.showToast(context, "请充值");
					}
				} else {
					// 提示用户：登录去；界面跳转：用户登录界面
					PromptManager.showToast(context, "请重新登录");
					HallMiddleManager.getInstance().changeUI(UserLogin.class, bundle);
				}
			} else {
				// 分支
				// 提示用户需要选择一注
				PromptManager.showToast(context, "需要选择一注");
			}
			break;
		}
	}

	private View getDecorViewDialog() {
		return PayPasswordView.getInstance(context, new OnPayListener() {

			@Override
			public void onCancelPay() {
				// TODO Auto-generated method stub
				dismissProgressDialog();
			}

			@Override
			public void onSurePay(String append, String multiple) {
				// TODO Auto-generated method stub
				appendText.setText(append);
				doubleText.setText(multiple);
				ShoppingCart.getInstance().setIssuesnumbers(Integer.parseInt(append));	//期数
				ShoppingCart.getInstance().setAppnumbers(Integer.parseInt(multiple)); //倍数
				changeNotice();
				shoppingHolding();
				dismissProgressDialog();
			}
		}).getView();
	}
	
	public void dismissProgressDialog() {
		if (keyboard != null && keyboard.isShowing()) {
			keyboard.dismiss();
		}
	}

	private void shoppingHolding() {
		int appendplusNo = Integer.parseInt(appendText.getText().toString());
		if (appendplusNo > 1) {
			shoppingHolding.setVisibility(View.VISIBLE);
		} else {
			shoppingHolding.setVisibility(View.GONE);
		}
	}

	/**
	 * 随机添加号码 查看玩法
	 */
	private void addRandom() {
		randomPickPane();
	}

	@Override
	public void onResume() {
		shopTimer.setTime(0, 0, 0);
		shopTimer.start();
		
		ShoppingCart.getInstance().setIssuesnumbers(1);
		ShoppingCart.getInstance().setAppnumbers(1);
		TitleManager.getInstance().changeBettingTitle(bundle.getString("lotteryname") + "-投注单");
		setLotteryId(bundle.getInt("lotteryid"));
		setLotteryType(bundle.getInt("lotterytype"));
		setLotteryName(bundle.getString("lotteryname"));

		String salesissuetext = context.getResources().getString(R.string.is_top_sales_issuelabel);
		salesissuetext = StringUtils.replaceEach(salesissuetext,new String[] { "ISSUE" }, new String[] { "------" });
		salesissue.setText(salesissuetext);

		getCurrentIssueInfo();
		if (ShoppingCart.getInstance().getPlaymenu() == null) {
			PlayMenu playmenu = ConstantInformation.playMap.get(bundle.getInt("lotteryid") + "").get(0);
			playmenu.setPlaymode(playmenu.getPlaymodeList().get(0));
			ShoppingCart.getInstance().setPlaymenu(playmenu);
			ShoppingCart.getInstance().setLucreMode(LucreModeList.getLucremode().get(0));
			ShoppingCart.getInstance().setLotteryid(bundle.getInt("lotteryid"));
			ShoppingCart.getInstance().setLotterytype(bundle.getInt("lotterytype"));
			addRandom();
		}
		changeNotice();
		appendText.setText("1");
		doubleText.setText("1");
		appendSettings.setChecked(true);
		mTogBtn.setChecked(true);
		keyboard = new KeyBoardDialog(activity,getDecorViewDialog());
		adapter.notifyDataSetChanged();
		shoppingHolding();
		super.onResume();
	}

	private void changeNotice() {
		Integer lotterynumber = ShoppingCart.getInstance().getLotterynumber();
		Double lotteryMoney = ShoppingCart.getInstance().getLotteryvalue();
		Integer issuesnumbers = ShoppingCart.getInstance().getIssuesnumbers();
		Integer appnumbers = ShoppingCart.getInstance().getAppnumbers();

		String moneyInfo = context.getResources().getString(R.string.is_shopping_list_money);
		moneyInfo = StringUtils.replaceEach(moneyInfo, new String[] { "MONEY" }, new String[] { lotteryMoney.toString() });

		String noticeInfo = context.getResources().getString(R.string.is_shopping_list_notice);
		noticeInfo = StringUtils.replaceEach(noticeInfo, new String[] { "NUM", "ISSUE", "BERS" }, new String[] { lotterynumber.toString(), issuesnumbers.toString(), appnumbers.toString() });

		// Html.fromHtml(notice):将notice里面的内容转换
		moneyText.setText(Html.fromHtml(moneyInfo));
		noticeText.setText(Html.fromHtml(noticeInfo));
	}

	@Override
	public void clearMethod() {
		// TODO Auto-generated method stub
		ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).clear();
		adapter.notifyDataSetChanged();
		changeNotice();
	}

	/**
	 * 随机选择一注号码
	 */
	private void randomPickPane() {
		PlayMenu playmenuKey = ShoppingCart.getInstance().getPlaymenu();
		switch (ShoppingCart.getInstance().getLotterytype()) {
		case 0:
			if (playmenuKey.getJscode().equals("ZX3")) // 前三直选 后三直选
			{
				if (playmenuKey.getPlaymode().equals("AGO"))
					agoThreeRandom();
				else if (playmenuKey.getPlaymode().equals("AFTER"))
					afterThreeRandom();
			} else if (playmenuKey.getJscode().equals("ZX2")) // 前二直选 后二直选
			{
				if (playmenuKey.getPlaymode().equals("AGO"))
					agoTwoRandom();
				else if (playmenuKey.getPlaymode().equals("AFTER"))
					afterTwoRandom();
			} else if (playmenuKey.getJscode().equals("ZUS")) { // 前三组选_组三
																// 后三组选_组三
				groupThreeRandom();
			} else if (playmenuKey.getJscode().equals("ZUL")) { // 前三组选_组六
																// 后三组选_组六
				groupSixRandom();
			} else if (playmenuKey.getJscode().equals("ZU2")) { // 前二组选 后二组选
				group_2_Random();
			} else if (playmenuKey.getJscode().equals("BDW1")) { // 前三一码不定位
																	// 后三一码不定位
				group_1_Random();
			} else if (playmenuKey.getJscode().equals("BDW2")) { // 前三二码不定位
																	// 后三二码不定位
				group_2_Random();
			} else if (playmenuKey.getJscode().equals("DWD")) { // 定位单
				fiveRandom();
			}
			break;
		case 1:
			break;
		case 2:
			if (playmenuKey.getJscode().equals("LTZX3")) // 前三直选
			{
				AgoThreeSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTZU3")) // 前三组选
			{
				GroupThreeSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTZX2")) // 前二直选
			{
				AgoTwoSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTZU2")) // 前二组选
			{
				GroupTwoSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTDWD")) { // 第一位 二位 三位
				OneLocationSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTRX1")) { // 任选一中一
				OneToOneSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTRX2")) { // 任选二中二
				TwoToTwoSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTRX3")) { // 任选三中三
				ThreeToThreeSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTRX4")) { // 任选四中四
				FourToFourSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTRX5")) { // 任选五中五
				FivesToFivesSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTRX6")) { // 任选六中五
				SixToFivesSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTRX7")) { // 任选七中五
				SevenToFivesSyfiveRandom();
			} else if (playmenuKey.getJscode().equals("LTRX8")) { // 任选八中五
				EightToFivesSyfiveRandom();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 提交投注
	 */
	private void buyBetting() {
		String promptInfo = "";

		promptInfo += bundle.getString("lotteryname") + "\t-\t第"+ bundle.getString("issue") + "期\n";

		final BettingInfo bettinginfo = new BettingInfo();
		bettinginfo.setCellphone(1);
		bettinginfo.setLotteryid(ShoppingCart.getInstance().getLotteryid());
		bettinginfo.setIssuestartNo(ShoppingCart.getInstance().getIssue());

		List<ProgramBuyInfo> projectinfoList = new ArrayList<ProgramBuyInfo>();

		for (int i = 0; i < ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).size(); i++) {
			ProgramBuyInfo projectinfo = new ProgramBuyInfo();
			Ticket ticket = ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).get(i);
			PlayMenu playmenu = ticket.getSelectPlay();
			projectinfo.setMethodid(Integer.parseInt(playmenu.getMethodid()));
			projectinfo.setNums(ticket.getNum());
			projectinfo.setTimes(ShoppingCart.getInstance().getAppnumbers());

			double lottermode = ticket.getMoneyMode().getLucreReckon() * 2 * ticket.getNum();
			BigDecimal bd = new BigDecimal(lottermode * ShoppingCart.getInstance().getAppnumbers());
			Double lotteryvalue = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			projectinfo.setMoney(lotteryvalue);
			projectinfo.setMode(ticket.getMoneyMode().getLucreReckonId());

			projectinfo.setPoint(ShoppingCart.getInstance().isRetainRebate());

			switch (ticket.getLotterytype()) {
			case 0:
				if (playmenu.getJscode().equalsIgnoreCase("ZX3")) { // 前三 后三直选
					if (playmenu.getPlaymode().equals("AGO")) {
						projectinfo.setCodes(ticket.getWanNum().replaceAll(" ","&") + "|" + ticket.getQianNum().replaceAll(" ", "&") + "|" + ticket.getBaiNum().replaceAll(" ","&"));
					} else if (playmenu.getPlaymode().equals("AFTER")) {
						projectinfo.setCodes(ticket.getBaiNum().replaceAll(" ", "&") + "|" + ticket.getShiNum().replaceAll(" ", "&") + "|" + ticket.getGeNum().replaceAll(" ", "&"));
					}
				} else if (playmenu.getJscode().equalsIgnoreCase("ZUS")) { // 前三
																			// 后三组选
																			// 组三
																			// C(n,2)*2
					projectinfo.setCodes(ticket.getAssembleSscNum().replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equalsIgnoreCase("ZUL")) { // 前三
																			// 后三组选
																			// 组六
																			// C(n,3)
					projectinfo.setCodes(ticket.getAssembleSscNum().replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equalsIgnoreCase("ZX2")) { // 前二
																			// 后二直选
																			// n1*n2

					if (playmenu.getPlaymode().equals("AGO")) {
						projectinfo.setCodes(ticket.getWanNum().replaceAll(" ",
								"&")
								+ "|"
								+ ticket.getQianNum().replaceAll(" ", "&"));
					} else if (playmenu.getPlaymode().equals("AFTER")) {
						projectinfo.setCodes(ticket.getShiNum().replaceAll(" ", "&") + "|" + ticket.getGeNum().replaceAll(" ", "&"));
					}
				} else if (playmenu.getJscode().equalsIgnoreCase("ZU2")) { // 前二
																			// 后二组选
																			// C(n,2)
					projectinfo.setCodes(ticket.getAssembleSscNum().replaceAll( " ", "&"));
				} else if (playmenu.getJscode().equalsIgnoreCase("DWD")) { // 个、十、百、千、万
																			// C(n,1)
					projectinfo.setCodes(ticket.getWanNum().replaceAll(" ", "&") + "|" + ticket.getQianNum().replaceAll(" ", "&") + "|" + ticket.getBaiNum().replaceAll(" ", "&") + "|" + ticket.getShiNum().replaceAll(" ", "&") + "|" + ticket.getGeNum().replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equalsIgnoreCase("BDW1")) { // 后三一码不定位
																			// 前三一码不定位
																			// C(n,1)
					projectinfo.setCodes(ticket.getAssembleSscNum().replaceAll( " ", "&"));
				} else if (playmenu.getJscode().equalsIgnoreCase("BDW2")) { // 后三二码不定位
																			// 前三二码不定位
																			// C(n,1)
					projectinfo.setCodes(ticket.getAssembleSscNum().replaceAll( " ", "&"));
				}
				break;
			case 1:

				break;
			case 2:
				if (playmenu.getJscode().equals("LTZX3")) // 前三直选
				{
					projectinfo.setCodes(ticket.getOneNum()
							.replaceAll(" ", "&")
							+ "|"
							+ ticket.getTwoNum().replaceAll(" ", "&")
							+ "|"
							+ ticket.getThreeNum().replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTZU3")) // 前三组选
				{
					projectinfo.setCodes(ticket.getAssembleSyFiveNum()
							.replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTZX2")) // 前二直选
				{
					projectinfo.setCodes(ticket.getOneNum()
							.replaceAll(" ", "&")
							+ "|"
							+ ticket.getTwoNum().replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTZU2")) // 前二组选
				{
					projectinfo.setCodes(ticket.getAssembleSyFiveNum()
							.replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTDWD")) { // 第一位 二位 三位
					projectinfo.setCodes(ticket.getOneNum()
							.replaceAll(" ", "&")
							+ "|"
							+ ticket.getTwoNum().replaceAll(" ", "&")
							+ "|"
							+ ticket.getThreeNum().replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTRX1")) { // 任选一中一
					projectinfo.setCodes(ticket.getAssembleSyFiveNum()
							.replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTRX2")) { // 任选二中二
					projectinfo.setCodes(ticket.getAssembleSyFiveNum()
							.replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTRX3")) { // 任选三中三
					projectinfo.setCodes(ticket.getAssembleSyFiveNum()
							.replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTRX4")) { // 任选四中四
					projectinfo.setCodes(ticket.getAssembleSyFiveNum()
							.replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTRX5")) { // 任选五中五
					projectinfo.setCodes(ticket.getAssembleSyFiveNum()
							.replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTRX6")) { // 任选六中五
					projectinfo.setCodes(ticket.getAssembleSyFiveNum()
							.replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTRX7")) { // 任选七中五
					projectinfo.setCodes(ticket.getAssembleSyFiveNum()
							.replaceAll(" ", "&"));
				} else if (playmenu.getJscode().equals("LTRX8")) { // 任选八中五
					projectinfo.setCodes(ticket.getAssembleSyFiveNum()
							.replaceAll(" ", "&"));
				}
				break;
			}

			projectinfoList.add(projectinfo);
		}

		bettinginfo.setLtproject(projectinfoList);
		Double lotteryMoney = 0.00;
		Integer lotterynumber = ShoppingCart.getInstance().getLotterynumber();
		Integer appnumbers = ShoppingCart.getInstance().getAppnumbers();
		promptInfo += "总注数：" + lotterynumber + "\n";

		/**
		 * 追号信息
		 */
		String[] traceIssueMap = ConstantInformation.taskIssueMap.get(String.valueOf(bundle.getInt("lotteryid")));
		Map<String, Integer> tracetimesMap = new HashMap<String, Integer>(); // 追号期数倍数
		HashMap<String, Boolean> isSelected = new HashMap<String, Boolean>();
		
		if (ShoppingCart.getInstance().getIssuesnumbers() > 1) {
			bettinginfo.setTracestatus(ShoppingCart.getInstance().getIssuesnumbers() > 1 ? true : false);
			int startno = 0;
			List<String> taskString = Arrays.asList(traceIssueMap);
			for (int i = 0; i < taskString.size(); i++) {
				String taskIssue = taskString.get(i);
				if (taskIssue.equals(ShoppingCart.getInstance().getIssue()))
					startno = i;
			}
			List<String> planappList;
			if(ShoppingCart.getInstance().getIssuesnumbers()<=(taskString.size()-startno)){
				planappList = taskString.subList(startno, startno+ ShoppingCart.getInstance().getIssuesnumbers());
				promptInfo += "追号：" + planappList.size() + "期\n";
				ShoppingCart.getInstance().setIssuesnumbers(planappList.size());
				lotteryMoney=ShoppingCart.getInstance().getLotteryvalue();
				appendText.setText(planappList.size()+"");
				appendText.setSelection(appendText.getText().toString().length());
				changeNotice();
			}else{
				planappList = taskString.subList(startno, taskString.size());
				promptInfo += "追号最大：" + planappList.size() + "期\n";
				ShoppingCart.getInstance().setIssuesnumbers(planappList.size());
				lotteryMoney=ShoppingCart.getInstance().getLotteryvalue();
				appendText.setText(planappList.size()+"");
				appendText.setSelection(appendText.getText().toString().length());
				changeNotice();
			}
			
			String traceissues[] = new String[planappList.size()]; // 追号期数

			for (int pl = 0; pl < planappList.size(); pl++) {
				String task = planappList.get(pl);
				traceissues[pl] = task;
				isSelected.put(task, true);
				tracetimesMap.put(task, ShoppingCart.getInstance().getAppnumbers());
			}
			bettinginfo.setIsSelected(isSelected);
			bettinginfo.setTracetimesMap(tracetimesMap); // 倍数信息
			bettinginfo.setTraceissues(traceissues); // 期号信息
			bettinginfo.setTracestop(appendSettings.isChecked());
		}

		promptInfo += "投注倍数：" + appnumbers + "\n";
		BigDecimal bd = new BigDecimal(lotteryMoney);
		Double lotteryTotalValue = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		promptInfo += "总金额：" + lotteryTotalValue + "\n";

		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(promptInfo);
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				// 设置你的操作事项
				submitData(bettinginfo);
			}
		});

		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();

	}

	private void submitData(BettingInfo bettinginfo) {
		new MyHttpTask<BettingInfo>() {
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context, "");
				super.onPreExecute();
			}

			@Override
			protected Message doInBackground(BettingInfo... params) {

				BettingEngine engine = BeanFactory.getImpl(BettingEngine.class);
				Message betting = engine.bettingNumber(params[0]);

				if (betting != null) {
					Oelement oelement = betting.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						// 成功了获取余额
						UserEngine userengine = BeanFactory
								.getImpl(UserEngine.class);
						Message balance = userengine.getBalance();
						if (balance != null) {
							oelement = balance.getBody().getOelement();
							if (ConstantValue.SUCCESS.equals(oelement
									.getErrorcode())) {
								BalanceElement element = (BalanceElement) balance
										.getBody().getElements().get(0);
								String money = element.getInvestvalues();
								// 修改用户的余额信息
								GlobalParams.MONEY = Double.parseDouble(money);
								return balance;
							}
						}
					} else {
						return betting;
					}
				}
				return null;
			}

			@Override
			protected void onPostExecute(Message result) {
				PromptManager.closeProgressDialog();
				if (result != null) {
					// 界面跳转
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						// 清理返回键
						HallMiddleManager.getInstance().clear();
						// 跳转到购彩大厅，提示对话框
						ShoppingCart.getInstance().clear();
						CustomDialog.Builder builder = new CustomDialog.Builder(
								context);
						builder.setMessage("投注成功！");
						builder.setTitle("温馨提示");
						builder.setPositiveButton("知道了",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										switch (bundle.getInt("lotterytype")) {
										case 0:
											HallMiddleManager.getInstance()
													.changeUI(PlaySSC.class,
															bundle);
											break;
										case 1:
											break;
										case 2:
											HallMiddleManager.getInstance()
													.changeUI(PlaySYFive.class,
															bundle);
											break;
										}
										dialog.dismiss();
									}
								});
						builder.create().show();

					} else {
						if (oelement.getErrorcode().equals("255")) {
							PromptManager.showRelogin(context,
									oelement.getErrormsg(),
									oelement.getErrorcode());
						} else {
							PromptManager.showToast(context,
									oelement.getErrormsg());
						}
					}
					// HallMiddleManager.getInstance().goBack();
				} else {
					PromptManager.showToast(context, "网络状态差，投注失败！");
				}

				super.onPostExecute(result);
			}
		}.executeProxy(bettinginfo);
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
						if (oelement.getErrorcode().equals("255")) {
							PromptManager.showRelogin(context,
									oelement.getErrormsg(),
									oelement.getErrorcode());
						} else {
							PromptManager.showToast(context,
									oelement.getErrormsg());
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
		TaskIssueInfoElement element = (TaskIssueInfoElement) result.getBody()
				.getElements().get(0);
		IssueAll currentIssueAll = element.getIssueAllMap().get(
				String.valueOf(bundle.getInt("lotteryid")));
		if (currentIssueAll != null) {
			IssueSales currentIssueSales = currentIssueAll.getIssueSales();

			// 第ISSUE期 还有TIME停售
			String salesissuetext = context.getResources().getString(
					R.string.is_top_sales_issuelabel);
			salesissuetext = StringUtils.replaceEach(salesissuetext,
					new String[] { "ISSUE" },
					new String[] { currentIssueSales.getIssue() });

			RecordTime timeRecord = getLasttime(
					currentIssueSales.getCurrenttime(),
					currentIssueSales.getSaleend());
			if (timeRecord.getMinute() > 0 || timeRecord.getSecond() > 0) {
				// shopTimer.initTime(timeRecord.getHour()*60+timeRecord.getMinute(),timeRecord.getSecond());
				shopTimer.setTime(timeRecord.getHour(), timeRecord.getMinute(),
						timeRecord.getSecond());
				salesissue.setText(salesissuetext);
				PromptManager.showToast(context,
						"第" + currentIssueSales.getIssue() + "正在开奖");
			} else {
				timeRecord = getLasttime(currentIssueSales.getCurrenttime(),
						currentIssueSales.getOpentime());
				shopTimer.setTime(timeRecord.getHour(), timeRecord.getMinute(),
						timeRecord.getSecond());

				salesissue.setText(salesissuetext);
				PromptManager.showToast(context,
						"销售期已切换到第" + currentIssueSales.getIssue() + "期");
			}
			shopTimer.start();

			bundle = new Bundle();
			bundle.putInt("lotteryid", getLotteryId());
			bundle.putString("issue", currentIssueSales.getIssue());
			bundle.putInt("lotterytype", getLotteryType());
			bundle.putString("lotteryname", getLotteryName());

			ShoppingCart.getInstance().setIssue(currentIssueSales.getIssue()); // bundle.getString("issue")
		}
	}

	/**
	 * 将秒时间转换成日时分格式
	 * 
	 * @param lasttime
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private RecordTime getLasttime(String currenttime, String end) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		RecordTime recTime = new RecordTime();
		try {
			// Date currentTime = new Date();
			// String dateString = df.format(currentTime);
			Date d1 = df.parse(currenttime);
			Date d2 = df.parse(end);
			// Date d2 = new Date(System.currentTimeMillis());//你也可以获取当前时间
			long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
			long days = diff / (1000 * 60 * 60 * 24);
			long hours = (diff - days * (1000 * 60 * 60 * 24))
					/ (1000 * 60 * 60);
			long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
					* (1000 * 60 * 60))
					/ (1000 * 60);
			long seconds = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);
			int day = new Long(days).intValue();
			int hour = new Long(hours).intValue();
			int minute = new Long(minutes).intValue();
			int second = new Long(seconds).intValue();
			recTime.setDay(day);
			recTime.setHour(hour);
			recTime.setMinute(minute);
			recTime.setSecond(second);
			return recTime;
		} catch (Exception e) {
		}
		return recTime;
	}

	/************************************************ 时时彩 *********************************************************/
	/**
	 * 五星直选随机
	 */

	private void fiveRandom() {

		Random random = new Random();
		List<Integer> wanNums = new ArrayList<Integer>();
		List<Integer> qianNums = new ArrayList<Integer>();
		List<Integer> baiNums = new ArrayList<Integer>();
		List<Integer> shiNums = new ArrayList<Integer>();
		List<Integer> geNums = new ArrayList<Integer>();

		int wannum = random.nextInt(10);
		wanNums.add(wannum);

		int qiannum = random.nextInt(10);
		qianNums.add(qiannum);

		int bainum = random.nextInt(10);
		baiNums.add(bainum);

		int shinum = random.nextInt(10);
		shiNums.add(shinum);

		int genum = random.nextInt(10);
		geNums.add(genum);

		DecimalFormat decimalFormat = new DecimalFormat("0");
		StringBuffer wanBuffer = new StringBuffer();
		for (Integer item : wanNums) {
			wanBuffer.append(" ").append(decimalFormat.format(item));
		}

		StringBuffer qianBuffer = new StringBuffer();
		for (Integer item : qianNums) {
			qianBuffer.append(" ").append(decimalFormat.format(item));
		}

		StringBuffer baiBuffer = new StringBuffer();
		for (Integer item : baiNums) {
			baiBuffer.append(" ").append(decimalFormat.format(item));
		}

		StringBuffer shiBuffer = new StringBuffer();
		for (Integer item : shiNums) {
			shiBuffer.append(" ").append(decimalFormat.format(item));
		}

		StringBuffer geBuffer = new StringBuffer();
		for (Integer item : geNums) {
			geBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));

		boolean wanflag = false, qianflag = false, baiflag = false, shiflag = false, geflag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getWanNum().length() == 0) {
							wanflag = false;
						} else if (ticketRepeat.getWanNum().equals(
								wanBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(wanBuffer.substring(1) + "|"
									+ qianBuffer.substring(1) + "|"
									+ baiBuffer.substring(1) + "|"
									+ shiBuffer.substring(1) + "|"
									+ geBuffer.substring(1) + "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							wanflag = false;
						} else {
							wanflag = true;
						}

						if (ticketRepeat.getQianNum().length() == 0) {
							qianflag = false;
						} else if (ticketRepeat.getQianNum().equals(
								qianBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(wanBuffer.substring(1) + "|"
									+ qianBuffer.substring(1) + "|"
									+ baiBuffer.substring(1) + "|"
									+ shiBuffer.substring(1) + "|"
									+ geBuffer.substring(1) + "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							qianflag = false;
						} else {
							qianflag = true;
						}

						if (ticketRepeat.getBaiNum().length() == 0) {
							baiflag = false;
						} else if (ticketRepeat.getBaiNum().equals(
								baiBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(wanBuffer.substring(1) + "|"
									+ qianBuffer.substring(1) + "|"
									+ baiBuffer.substring(1) + "|"
									+ shiBuffer.substring(1) + "|"
									+ geBuffer.substring(1) + "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							baiflag = false;
						} else {
							baiflag = true;
						}

						if (ticketRepeat.getShiNum().length() == 0) {
							shiflag = false;
						} else if (ticketRepeat.getShiNum().equals(
								shiBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(wanBuffer.substring(1) + "|"
									+ qianBuffer.substring(1) + "|"
									+ baiBuffer.substring(1) + "|"
									+ shiBuffer.substring(1) + "|"
									+ geBuffer.substring(1) + "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							shiflag = false;
						} else {
							shiflag = true;
						}

						if (ticketRepeat.getGeNum().length() == 0) {
							geflag = false;
						} else if (ticketRepeat.getGeNum().equals(
								geBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(wanBuffer.substring(1) + "|"
									+ qianBuffer.substring(1) + "|"
									+ baiBuffer.substring(1) + "|"
									+ shiBuffer.substring(1) + "|"
									+ geBuffer.substring(1) + "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							geflag = false;
						} else {
							geflag = true;
						}
					}
				}
			} else
				wanflag = qianflag = baiflag = shiflag = geflag = true;
		} else
			wanflag = qianflag = baiflag = shiflag = geflag = true;

		if (wanflag || qianflag || baiflag || shiflag || geflag) {
			// 封装Ticket
			Ticket ticket = new Ticket();
			if (wanflag) {
				ticket.setWanNum(wanBuffer.substring(1));
			} else {
				wanNums.clear();
			}
			if (qianflag) {
				ticket.setQianNum(qianBuffer.substring(1));
			} else {
				qianNums.clear();
			}
			if (baiflag) {
				ticket.setBaiNum(baiBuffer.substring(1));
			} else {
				baiNums.clear();
			}
			if (shiflag) {
				ticket.setShiNum(shiBuffer.substring(1));
			} else {
				shiNums.clear();
			}
			if (geflag) {
				ticket.setGeNum(geBuffer.substring(1));
			} else {
				geNums.clear();
			}

			int betsdigit = Calculation.getInstance().calculationNote(wanNums,
					qianNums, baiNums, shiNums, geNums, null,
					ShoppingCart.getInstance().getPlaymenu());
			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 随机前三
	 */
	private void agoThreeRandom() {
		Random random = new Random();
		List<Integer> wanNums = new ArrayList<Integer>();
		List<Integer> qianNums = new ArrayList<Integer>();
		List<Integer> baiNums = new ArrayList<Integer>();

		int wannum = random.nextInt(10);
		int qiannum = random.nextInt(10);
		int bainum = random.nextInt(10);

		wanNums.add(wannum);
		qianNums.add(qiannum);
		baiNums.add(bainum);
		DecimalFormat decimalFormat = new DecimalFormat("0");
		StringBuffer wanBuffer = new StringBuffer();
		for (Integer item : wanNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			wanBuffer.append(" ").append(decimalFormat.format(item));
		}
		StringBuffer qianBuffer = new StringBuffer();
		for (Integer item : qianNums) {
			qianBuffer.append(" ").append(decimalFormat.format(item));
		}
		StringBuffer baiBuffer = new StringBuffer();
		for (Integer item : baiNums) {
			baiBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getWanNum().equals(
								wanBuffer.substring(1))
								&& ticketRepeat.getQianNum().equals(
										qianBuffer.substring(1))
								&& ticketRepeat.getBaiNum().equals(
										baiBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(wanBuffer.substring(1) + "|"
									+ qianBuffer.substring(1) + "|"
									+ baiBuffer.substring(1) + "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});

							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setWanNum(wanBuffer.substring(1));

			ticket.setQianNum(qianBuffer.substring(1));

			ticket.setBaiNum(baiBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(wanNums,
					qianNums, baiNums, null, null, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);
			ticket.setNum(betsdigit);
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 随机后三
	 */
	private void afterThreeRandom() {
		Random random = new Random();
		List<Integer> baiNums = new ArrayList<Integer>();
		List<Integer> shiNums = new ArrayList<Integer>();
		List<Integer> geNums = new ArrayList<Integer>();

		int bainum = random.nextInt(10);
		int shinum = random.nextInt(10);
		int genum = random.nextInt(10);

		baiNums.add(bainum);
		shiNums.add(shinum);
		geNums.add(genum);
		DecimalFormat decimalFormat = new DecimalFormat("0");

		StringBuffer baiBuffer = new StringBuffer();
		for (Integer item : baiNums) {
			baiBuffer.append(" ").append(decimalFormat.format(item));
		}

		StringBuffer shiBuffer = new StringBuffer();
		for (Integer item : shiNums) {
			shiBuffer.append(" ").append(decimalFormat.format(item));
		}
		StringBuffer geBuffer = new StringBuffer();
		for (Integer item : geNums) {
			geBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getBaiNum().equals(
								baiBuffer.substring(1))
								&& ticketRepeat.getShiNum().equals(
										shiBuffer.substring(1))
								&& ticketRepeat.getGeNum().equals(
										geBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(baiBuffer.substring(1) + "|"
									+ shiBuffer.substring(1) + "|"
									+ geBuffer.substring(1) + "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});

							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setBaiNum(baiBuffer.substring(1));
			ticket.setShiNum(shiBuffer.substring(1));
			ticket.setGeNum(geBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, baiNums, shiNums, geNums, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 随机组三
	 */
	private void groupThreeRandom() {

		Random random = new Random();
		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 2) {
			int num = random.nextInt(10);
			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		DecimalFormat decimalFormat = new DecimalFormat("0");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSscNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});

							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();
			ticket.setAssembleSscNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 随机组六
	 */
	private void groupSixRandom() {
		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 3) {
			int num = random.nextInt(10);

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}
		DecimalFormat decimalFormat = new DecimalFormat("0");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSscNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});

							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSscNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	private void group_2_Random() {

		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 2) {
			int num = random.nextInt(10);

			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}

		DecimalFormat decimalFormat = new DecimalFormat("0");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSscNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});

							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSscNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	private void group_1_Random() {

		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		int assemblenum = random.nextInt(10);
		assembleNums.add(assemblenum);

		DecimalFormat decimalFormat = new DecimalFormat("0");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSscNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSscNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 随机前二
	 */
	private void agoTwoRandom() {
		Random random = new Random();

		List<Integer> wanNums = new ArrayList<Integer>();
		List<Integer> qianNums = new ArrayList<Integer>();

		int wannum = random.nextInt(10);
		int qiannum = random.nextInt(10);

		wanNums.add(wannum);
		qianNums.add(qiannum);

		DecimalFormat decimalFormat = new DecimalFormat("0");
		StringBuffer wanBuffer = new StringBuffer();
		for (Integer item : wanNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			wanBuffer.append(" ").append(decimalFormat.format(item));
		}

		StringBuffer qianBuffer = new StringBuffer();
		for (Integer item : qianNums) {
			qianBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getWanNum().equals(
								wanBuffer.substring(1))
								&& ticketRepeat.getQianNum().equals(
										qianBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(wanBuffer.substring(1) + "|"
									+ qianBuffer.substring(1) + "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setWanNum(wanBuffer.substring(1));
			ticket.setQianNum(qianBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(wanNums,
					qianNums, null, null, null, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 随机后二
	 */
	private void afterTwoRandom() {
		Random random = new Random();
		List<Integer> shiNums = new ArrayList<Integer>();
		List<Integer> geNums = new ArrayList<Integer>();

		int shinum = random.nextInt(10);
		int genum = random.nextInt(10);
		shiNums.add(shinum);
		geNums.add(genum);

		DecimalFormat decimalFormat = new DecimalFormat("0");
		StringBuffer shiBuffer = new StringBuffer();
		for (Integer item : shiNums) {
			shiBuffer.append(" ").append(decimalFormat.format(item));
		}
		StringBuffer geBuffer = new StringBuffer();
		for (Integer item : geNums) {
			geBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getShiNum().equals(
								shiBuffer.substring(1))
								&& ticketRepeat.getGeNum().equals(
										geBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(shiBuffer.substring(1) + "|"
									+ geBuffer.substring(1) + "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});

							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();
			ticket.setShiNum(shiBuffer.substring(1));
			ticket.setGeNum(geBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, shiNums, geNums, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	private void wanRandom() {

		Random random = new Random();
		List<Integer> wanNums = new ArrayList<Integer>();

		int wannum = random.nextInt(10);
		wanNums.add(wannum);

		DecimalFormat decimalFormat = new DecimalFormat("0");

		StringBuffer wanBuffer = new StringBuffer();
		for (Integer item : wanNums) {
			wanBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getWanNum().equals(
								wanBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(wanBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setWanNum(wanBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(wanNums,
					null, null, null, null, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	private void qianRandom() {

		Random random = new Random();
		List<Integer> qianNums = new ArrayList<Integer>();

		int qiannum = random.nextInt(10);
		qianNums.add(qiannum);

		DecimalFormat decimalFormat = new DecimalFormat("0");

		StringBuffer qianBuffer = new StringBuffer();
		for (Integer item : qianNums) {
			qianBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getQianNum().equals(
								qianBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(qianBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setQianNum(qianBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					qianNums, null, null, null, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	private void baiRandom() {
		Random random = new Random();
		List<Integer> baiNums = new ArrayList<Integer>();

		int bainum = random.nextInt(10);
		baiNums.add(bainum);

		DecimalFormat decimalFormat = new DecimalFormat("0");

		StringBuffer baiBuffer = new StringBuffer();
		for (Integer item : baiNums) {
			baiBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getBaiNum().equals(
								baiBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(baiBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setBaiNum(baiBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, baiNums, null, null, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	private void shiRandom() {
		Random random = new Random();
		List<Integer> shiNums = new ArrayList<Integer>();

		int shinum = random.nextInt(10);
		shiNums.add(shinum);

		DecimalFormat decimalFormat = new DecimalFormat("0");
		StringBuffer shiBuffer = new StringBuffer();
		for (Integer item : shiNums) {
			shiBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getShiNum().equals(
								shiBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(shiBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();
			ticket.setShiNum(shiBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, shiNums, null, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	private void geRandom() {
		Random random = new Random();
		List<Integer> geNums = new ArrayList<Integer>();

		int genum = random.nextInt(10);
		geNums.add(genum);

		DecimalFormat decimalFormat = new DecimalFormat("0");
		StringBuffer geBuffer = new StringBuffer();
		for (Integer item : geNums) {
			geBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getGeNum().equals(
								geBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(geBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setGeNum(geBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, null, geNums, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	/************************************************ 十一选五 *********************************************************/
	/**
	 * 前三 直选
	 */
	private void AgoThreeSyfiveRandom() {
		Random random = new Random();
		List<Integer> oneNums = new ArrayList<Integer>();
		List<Integer> twoNums = new ArrayList<Integer>();
		List<Integer> threeNums = new ArrayList<Integer>();

		int onenum = random.nextInt(11) + 1;
		oneNums.add(onenum);

		int twonum = random.nextInt(11) + 1;
		twoNums.add(twonum);

		int threenum = random.nextInt(11) + 1;
		threeNums.add(threenum);

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer oneBuffer = new StringBuffer();
		for (Integer item : oneNums) {
			oneBuffer.append(" ").append(decimalFormat.format(item));
		}
		StringBuffer twoBuffer = new StringBuffer();
		for (Integer item : twoNums) {
			twoBuffer.append(" ").append(decimalFormat.format(item));
		}

		StringBuffer threeBuffer = new StringBuffer();
		for (Integer item : threeNums) {
			threeBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getOneNum().equals(
								oneBuffer.substring(1))
								&& ticketRepeat.getTwoNum().equals(
										twoBuffer.substring(1))
								&& ticketRepeat.getThreeNum().equals(
										threeBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(oneBuffer.substring(1) + "|"
									+ twoBuffer.substring(1) + "|"
									+ threeBuffer.substring(1) + "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setOneNum(oneBuffer.substring(1));
			ticket.setTwoNum(twoBuffer.substring(1));
			ticket.setThreeNum(threeBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(oneNums,
					twoNums, threeNums, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 前三 组选
	 */
	private void GroupThreeSyfiveRandom() {
		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 3) {
			int num = random.nextInt(11) + 1;
			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSyFiveNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 前二 直选
	 */
	private void AgoTwoSyfiveRandom() {
		Random random = new Random();
		List<Integer> oneNums = new ArrayList<Integer>();
		List<Integer> twoNums = new ArrayList<Integer>();

		int onenum = random.nextInt(11) + 1;
		oneNums.add(onenum);

		int twonum = random.nextInt(11) + 1;
		twoNums.add(twonum);

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer oneBuffer = new StringBuffer();
		for (Integer item : oneNums) {
			oneBuffer.append(" ").append(decimalFormat.format(item));
		}

		StringBuffer twoBuffer = new StringBuffer();
		for (Integer item : twoNums) {
			twoBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getOneNum().equals(
								oneBuffer.substring(1))
								&& ticketRepeat.getTwoNum().equals(
										twoBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(oneBuffer.substring(1) + "|"
									+ twoBuffer.substring(1) + "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();
			ticket.setOneNum(oneBuffer.substring(1));
			ticket.setTwoNum(twoBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(oneNums,
					twoNums, null, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 前二 组选
	 */
	private void GroupTwoSyfiveRandom() {
		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 2) {
			int num = random.nextInt(11) + 1;
			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSyFiveNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 定位 第一位
	 */
	private void OneLocationSyfiveRandom() {
		Random random = new Random();

		List<Integer> oneNums = new ArrayList<Integer>();
		List<Integer> twoNums = new ArrayList<Integer>();
		List<Integer> threeNums = new ArrayList<Integer>();

		int onenum = random.nextInt(11) + 1;
		oneNums.add(onenum);
		int twonum = random.nextInt(11) + 1;
		twoNums.add(twonum);
		int threenum = random.nextInt(11) + 1;
		threeNums.add(threenum);

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer oneBuffer = new StringBuffer();
		for (Integer item : oneNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			oneBuffer.append(" ").append(decimalFormat.format(item));
		}

		StringBuffer twoBuffer = new StringBuffer();
		for (Integer item : twoNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			twoBuffer.append(" ").append(decimalFormat.format(item));
		}

		StringBuffer threeBuffer = new StringBuffer();
		for (Integer item : threeNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			threeBuffer.append(" ").append(decimalFormat.format(item));
		}

		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean oneflag = false, twoflag = false, threeflag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getOneNum().length() == 0) {
							oneflag = false;
						} else if (ticketRepeat.getOneNum().equals(
								oneBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(oneBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							oneflag = false;
						} else {
							oneflag = true;
						}

						if (ticketRepeat.getTwoNum().length() == 0) {
							twoflag = false;
						} else if (ticketRepeat.getTwoNum().equals(
								twoBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(twoBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							twoflag = false;
						} else {
							twoflag = true;
						}

						if (ticketRepeat.getThreeNum().length() == 0) {
							threeflag = false;
						} else if (ticketRepeat.getThreeNum().equals(
								threeBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(threeBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							threeflag = false;
						} else {
							threeflag = true;
						}
					}
				}
			} else
				oneflag = twoflag = threeflag = true;
		} else
			oneflag = twoflag = threeflag = true;

		if (oneflag || twoflag || threeflag) {
			// 封装Ticket
			Ticket ticket = new Ticket();
			if (oneflag) {
				ticket.setOneNum(oneBuffer.substring(1));
			} else {
				oneNums.clear();
			}
			if (twoflag) {
				ticket.setTwoNum(twoBuffer.substring(1));
			} else {
				twoNums.clear();
			}
			if (threeflag) {
				ticket.setThreeNum(threeBuffer.substring(1));
			} else {
				threeNums.clear();
			}

			int betsdigit = Calculation.getInstance().calculationNote(oneNums,
					twoNums, threeNums, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			// ticket.setMultiple(multiple);
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 定位 第二位
	 */
	private void TwoLocationSyfiveRandom() {
		Random random = new Random();

		List<Integer> twoNums = new ArrayList<Integer>();

		int twonum = random.nextInt(11) + 1;
		twoNums.add(twonum);

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer twoBuffer = new StringBuffer();
		for (Integer item : twoNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			twoBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getTwoNum().equals(
								twoBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(twoBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setTwoNum(twoBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					twoNums, null, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 定位 第三位
	 */
	private void ThreeLocationSyfiveRandom() {
		Random random = new Random();

		List<Integer> threeNums = new ArrayList<Integer>();

		int threenum = random.nextInt(11) + 1;
		threeNums.add(threenum);

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer threeBuffer = new StringBuffer();
		for (Integer item : threeNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			threeBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getThreeNum().equals(
								threeBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(threeBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setTwoNum(threeBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, threeNums, null,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 选一中一
	 */
	private void OneToOneSyfiveRandom() {
		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 1) {
			int num = random.nextInt(11) + 1;
			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSyFiveNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else {
							flag = true;
						}
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 选二中二
	 */
	private void TwoToTwoSyfiveRandom() {
		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 2) {
			int num = random.nextInt(11) + 1;
			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSyFiveNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 选三中三
	 */
	private void ThreeToThreeSyfiveRandom() {
		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 3) {
			int num = random.nextInt(11) + 1;
			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSyFiveNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 选四中四
	 */
	private void FourToFourSyfiveRandom() {
		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 4) {
			int num = random.nextInt(11) + 1;
			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSyFiveNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 选五中五
	 */
	private void FivesToFivesSyfiveRandom() {
		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 5) {
			int num = random.nextInt(11) + 1;
			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSyFiveNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 选六中五
	 */
	private void SixToFivesSyfiveRandom() {
		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 6) {
			int num = random.nextInt(11) + 1;
			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSyFiveNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else {
							flag = true;
						}
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();
			ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	/**
	 * 选七中五
	 */
	private void SevenToFivesSyfiveRandom() {
		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 7) {
			int num = random.nextInt(11) + 1;
			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			// redBuffer.append(decimalFormat.format(item)).append(" ");
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSyFiveNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 选八中五
	 */
	private void EightToFivesSyfiveRandom() {
		Random random = new Random();

		List<Integer> assembleNums = new ArrayList<Integer>();

		while (assembleNums.size() < 8) {
			int num = random.nextInt(11) + 1;
			if (assembleNums.contains(num)) {
				continue;
			}
			assembleNums.add(num);
		}

		DecimalFormat decimalFormat = new DecimalFormat("00");
		StringBuffer assembleBuffer = new StringBuffer();
		for (Integer item : assembleNums) {
			assembleBuffer.append(" ").append(decimalFormat.format(item));
		}
		PlayMenu playmenu = ShoppingCart.getInstance().getPlaymenu();
		List<Ticket> ticketList = ShoppingCart.getInstance().getTicketsMap()
				.get(bundle.getInt("lotteryid"));
		boolean flag = false;
		if (ticketList != null) {
			if (ticketList.size() > 0) {
				for (int t = 0; t < ticketList.size(); t++) {
					Ticket ticketRepeat = ticketList.get(t);
					if (playmenu.getMethodid().equals(
							ticketRepeat.getSelectPlay().getMethodid())) {
						if (ticketRepeat.getAssembleSyFiveNum().equals(
								assembleBuffer.substring(1))) {
							CustomDialog.Builder builder = new CustomDialog.Builder(
									context);
							builder.setMessage(assembleBuffer.substring(1)
									+ "已存在，请重新选号。");
							builder.setTitle("温馨提示");
							builder.setPositiveButton("知道了",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											// 设置你的操作事项
										}
									});
							builder.create().show();
							flag = false;
						} else
							flag = true;
					}
				}
			} else
				flag = true;
		} else
			flag = true;

		if (flag) {
			// 封装Ticket
			Ticket ticket = new Ticket();

			ticket.setAssembleSyFiveNum(assembleBuffer.substring(1));

			int betsdigit = Calculation.getInstance().calculationNote(null,
					null, null, assembleNums,
					ShoppingCart.getInstance().getPlaymenu());

			BigDecimal b = new BigDecimal(betsdigit
					* 2
					* ShoppingCart.getInstance().getLucreMode()
							.getLucreReckon());
			double m = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			ticket.setNoteMoney(m);

			ticket.setNum(betsdigit);
			ticket.setMoneyMode(ShoppingCart.getInstance().getLucreMode());
			ticket.setSelectPlay(ShoppingCart.getInstance().getPlaymenu());
			ticket.setLotteryid(bundle.getInt("lotteryid"));
			ticket.setLotterytype(bundle.getInt("lotterytype"));
			ShoppingCart.getInstance().setPlaymenu(
					ShoppingCart.getInstance().getPlaymenu());
			// 添加到购物车
			if (ShoppingCart.getInstance().getTicketsMap()
					.get(bundle.getInt("lotteryid")) == null) {
				List<Ticket> ticketPlusList = new ArrayList<Ticket>();
				ticketPlusList.add(ticket);
				ShoppingCart.getInstance().getTicketsMap()
						.put(bundle.getInt("lotteryid"), ticketPlusList);
			} else {
				ShoppingCart.getInstance().getTicketsMap()
						.get(bundle.getInt("lotteryid")).add(ticket);
			}
			// 更新界面
			adapter.notifyDataSetChanged();
		}

	}

	private int lotteryid = 0;

	public int getLotteryId() {
		// TODO Auto-generated method stub
		return lotteryid;
	}

	public void setLotteryId(int id) {
		// TODO Auto-generated method stub
		this.lotteryid = id;
	}

	private int lotteryType = 0;

	public void setLotteryType(int type) {
		// TODO Auto-generated method stub
		this.lotteryType = type;
	}

	public int getLotteryType() {
		// TODO Auto-generated method stub
		return lotteryType;
	}

	private String lotteryName;

	public void setLotteryName(String name) {
		// TODO Auto-generated method stub
		this.lotteryName = name;
	}

	public String getLotteryName() {
		// TODO Auto-generated method stub
		return lotteryName;
	}
}