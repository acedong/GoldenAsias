package com.goldenasia.lottery.view;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.AppendInfo;
import com.goldenasia.lottery.bean.BettingInfo;
import com.goldenasia.lottery.bean.ConstantInformation;
import com.goldenasia.lottery.bean.IssueAll;
import com.goldenasia.lottery.bean.IssueSales;
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
import com.goldenasia.lottery.net.protocal.element.SpeciesListElements;
import com.goldenasia.lottery.net.protocal.element.TaskIssueInfoElement;
import com.goldenasia.lottery.util.BeanFactory;
import com.goldenasia.lottery.util.CustomDialog;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.adapter.AppendBettingAdapter;
import com.goldenasia.lottery.view.controls.HVListView;
import com.goldenasia.lottery.view.controls.RushBuyCountDownTimerView;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.HallMiddleManager;
import com.goldenasia.lottery.view.manager.TitleManager;

/**
 * 
 * 带滑动表头与固定列的ListView
 */
public class AppendBetting extends BaseUI implements OnClickListener{

	private TextView salesTitle;
//	private Anticlockwise salesTime;
	private RushBuyCountDownTimerView salesTime;
	private EditText chasenumber;
	private EditText multipleNumber;
//	private HVListView appendHallListView;
	private ListView appendHallListView;
	private AppendBettingAdapter mAdapter;
	private TextView appebdBottomMoney;
	private TextView appebdBottomNote;
	private Button appendGenerateBtn;
	private Button appendBuyBtn;
	private Button fivesButton;
	private Button tenButton;
	private Button fifteenButton;
	private Button twentyButton;
	private Button twentyfiveButton;
	private Button allButton;
	private Button numberlistBtn;
	private Button selectAllBtn;
	private Button deselectAllBtn;
	private Button cancelSelectAll;
	
	private CheckBox appendSettings;	// 追号设置	
	
	private List<Map<String, AppendInfo>> mData;
	public AppendBetting(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		showInMiddle = (ViewGroup) View.inflate(context,R.layout.nb_appendbetting_hall, null);
		
		RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.nb_append_area_content);
		mainLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (in.isActive()) {
					in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
				}
			}
		});
		
		salesTitle=(TextView) findViewById(R.id.nb_appendbetting_salestitle_label);
		salesTime=(RushBuyCountDownTimerView) findViewById(R.id.nb_appendbetting_salestime);
		
		chasenumber = (EditText) findViewById(R.id.nb_appendbetting_several_text);
		multipleNumber = (EditText) findViewById(R.id.nb_appendbetting_multiple_text);
		
//		appendHallListView = (HVListView) findViewById(R.id.nb_appendbetting_hall_listView);
		appendHallListView = (ListView) findViewById(R.id.nb_appendbetting_hall_listView);
		//设置列头
//		appendHallListView.mListHead = (LinearLayout) findViewById(R.id.nb_append_betting_head);

		appendSettings = (CheckBox) findViewById(R.id.nb_appendsettings_append_settings);
		appebdBottomMoney=(TextView)findViewById(R.id.nb_appendsettings_money);
		appebdBottomNote=(TextView)findViewById(R.id.nb_appendsettings_notice);
		
		appendBuyBtn = (Button) findViewById(R.id.nb_appendsettings_shopping_buy);
		appendGenerateBtn = (Button) findViewById(R.id.nb_append_generate_button);
		
		fivesButton=(Button) findViewById(R.id.nb_append_generate_fives_button);
		tenButton=(Button) findViewById(R.id.nb_append_generate_ten_button);
		fifteenButton=(Button) findViewById(R.id.nb_append_generate_fifteen_button);
		twentyButton=(Button) findViewById(R.id.nb_append_generate_twenty_button);
		twentyfiveButton=(Button) findViewById(R.id.nb_append_generate_twentyfive_button);
		allButton=(Button) findViewById(R.id.nb_append_generate_all_button);
		
		numberlistBtn = (Button) findViewById(R.id.nb_appendsettings_numberlist_but);
		selectAllBtn = (Button) findViewById(R.id.btn_selectAll);
		deselectAllBtn = (Button) findViewById(R.id.btn_cancleselectall);
		cancelSelectAll = (Button) findViewById(R.id.btn_cancelAll);
		
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
//		salesTime.setOnTimeCompleteListener(new Anticlockwise.OnTimeCompleteListener()
//		{
//			@Override
//			public void onTimeComplete(View v) {
//	        	  getCurrentIssueInfo();
//	          }
//	    });
		
		salesTime.setOncountdown(new RushBuyCountDownTimerView.OnUpdateCountdownListener() {
			
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
		
		chasenumber.setText(String.valueOf(10));
		multipleNumber.setText(String.valueOf(1));
		chasenumber.setSelection(chasenumber.length());
		multipleNumber.setSelection(multipleNumber.length());
		
		selectAllBtn.setOnClickListener(this);
		deselectAllBtn.setOnClickListener(this);
		cancelSelectAll.setOnClickListener(this);
		appendGenerateBtn.setOnClickListener(this);
		appendBuyBtn.setOnClickListener(this);
		fivesButton.setOnClickListener(this);
		tenButton.setOnClickListener(this);
		fifteenButton.setOnClickListener(this);
		twentyButton.setOnClickListener(this);
		twentyfiveButton.setOnClickListener(this);
		allButton.setOnClickListener(this);
		numberlistBtn.setOnClickListener(this);
		
		appendSettings.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                // TODO Auto-generated method stub
                ShoppingCart.getInstance().setAppstate(arg1?true:false);
            }
        });
		appendSettings.setChecked(true);
		
		mAdapter = new AppendBettingAdapter(context);
		mAdapter.setOnRefreshData(new AppendBettingAdapter.OnRefreshDataChangeListener() {
			
			@Override
			public void refreshData() {
				// TODO Auto-generated method stub
				changeNotice();
			}
		});
		appendHallListView.setAdapter(mAdapter);
	}

	@Override
	public void onResume() {
		TitleManager.getInstance().changeSettingTitle(bundle.getString("lotteryname")+"-智能追号");
		salesTime.setTime(0,0,0);
		salesTime.start();
		ShoppingCart.getInstance().clearAppend();
		setLotteryId(bundle.getInt("lotteryid"));
		setLotteryType(bundle.getInt("lotterytype"));
		setLotteryName(bundle.getString("lotteryname"));
		String salesissuetext = context.getResources().getString(R.string.is_top_sales_issuelabel);
		salesissuetext = StringUtils.replaceEach(salesissuetext,new String[] { "ISSUE" }, new String[] { "-----" });
		salesTitle.setText(salesissuetext);
		appendSettings.setChecked(true);
		
		chasenumber.setText("5");
		multipleNumber.setText("1");
		generateUpdate();
		getCurrentIssueInfo();
		super.onResume();
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ConstantValue.VIEW_PREBET;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.nb_appendsettings_shopping_buy:	//投注购买
			// 购买
			// ①判断：购物车中是否有投注
			if (ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).size() >= 1) {
				//追号注数大于0
				if(ShoppingCart.getInstance().chaseNumberNote()>0){
					// ②判断：用户是否登录——被动登录
					if (GlobalParams.isLogin) {
						// ③判断：用户的余额是否满足投注需求
						if (ShoppingCart.getInstance().getLotteryvalue() <= GlobalParams.MONEY) {
							// ④界面跳转：跳转到追期和倍投的设置界面
							appendBetting();
							//HallMiddleManager.getInstance().changeUI(PreBet.class, bundle);
						}else{
							// 提示用户：充值去；界面跳转：用户充值界面
							PromptManager.showToast(context, "请充值");
						}
					} else {
						// 提示用户：登录去；界面跳转：用户登录界面
						PromptManager.showToast(context, "请重新登录");
						HallMiddleManager.getInstance().changeUI(UserLogin.class, bundle);
					}
				}
			} else {
				// 分支
				// 提示用户需要选择一注
				PromptManager.showToast(context, "需要选择一注");
			}
			break;
		case R.id.nb_append_generate_button:	//生成追号投注
			InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (in.isActive()) {
				in.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
			}
			generateUpdate();
			mAdapter.notifyDataSetChanged();
			break;
		case R.id.nb_append_generate_fives_button:
			chasenumber.setText(String.valueOf(5));
			multipleNumber.setText(String.valueOf(1));
			generateUpdate();
			break;
		case R.id.nb_append_generate_ten_button:
			chasenumber.setText(String.valueOf(10));
			multipleNumber.setText(String.valueOf(1));
			generateUpdate();
			break;
		case R.id.nb_append_generate_fifteen_button:
			chasenumber.setText(String.valueOf(15));
			multipleNumber.setText(String.valueOf(1));
			generateUpdate();
			break;
		case R.id.nb_append_generate_twenty_button:	
			chasenumber.setText(String.valueOf(20));
			multipleNumber.setText(String.valueOf(1));
			generateUpdate();
			break;
		case R.id.nb_append_generate_twentyfive_button:	
			chasenumber.setText(String.valueOf(25));
			multipleNumber.setText(String.valueOf(1));
			generateUpdate();
			break;
		case R.id.nb_append_generate_all_button:
			String[] taskIssue=ConstantInformation.taskIssueMap.get(String.valueOf(bundle.getInt("lotteryid")));
			chasenumber.setText(String.valueOf(taskIssue.length));
			multipleNumber.setText(String.valueOf(1));
			generateUpdate();
			break;
		case R.id.btn_selectAll:
			for (int i = 0; i < mData.size(); i++) {
				AppendInfo appendData=mData.get(i).get("list_item_inputvalue");
				mAdapter.getIsSelected().put(appendData.getIssue(), true);
			}
			mAdapter.notifyDataSetChanged();
			break;
		case R.id.nb_appendsettings_numberlist_but:
			HallMiddleManager.getInstance().changeUI(Shopping.class, bundle);
			break;
		case R.id.btn_cancleselectall:
			// 遍历list的长度，将已选的设为未选，未选的设为已选
			for (int i = 0; i < mData.size(); i++) {
				AppendInfo appendData=mData.get(i).get("list_item_inputvalue");
				if (mAdapter.getIsSelected().get(appendData.getIssue())) {
					mAdapter.getIsSelected().put(appendData.getIssue(), false);
				} else {
					mAdapter.getIsSelected().put(appendData.getIssue(), true);
				}
			}
			mAdapter.notifyDataSetChanged();
			break;
		case R.id.btn_cancelAll:
			for (int i = 0; i < mData.size(); i++) {
				AppendInfo appendData=mData.get(i).get("list_item_inputvalue");
				if (mAdapter.getIsSelected().get(appendData.getIssue())) {
					mAdapter.getIsSelected().put(appendData.getIssue(), false);
				}
			}
			mAdapter.notifyDataSetChanged();
			break;
		}
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
					getCurrentIssueInfo();
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
		
		IssueSales currentIssueSales = currentIssueAll.getIssueSales();
		
		// 第ISSUE期 还有TIME停售
		String salesissuetext = context.getResources().getString(R.string.is_top_sales_issuelabel);
		salesissuetext = StringUtils.replaceEach(salesissuetext,new String[] { "ISSUE" }, new String[] { currentIssueSales.getIssue() });
		
		RecordTime timeRecord = getLasttime( currentIssueSales.getCurrenttime(), currentIssueSales.getSaleend());
		if(timeRecord.getMinute()>0||timeRecord.getSecond()>0){
//			salesTime.initTime(timeRecord.getHour()*60+timeRecord.getMinute(),timeRecord.getSecond());
			salesTime.setTime(timeRecord.getHour(),timeRecord.getMinute(),timeRecord.getSecond());
			
			salesTitle.setText(salesissuetext);
			PromptManager.showToast(context, "第"+ currentIssueSales.getIssue()+ "正在开奖");
		}else{
			timeRecord = getLasttime( currentIssueSales.getCurrenttime(), currentIssueSales.getOpentime());
//			salesTime.initTime(timeRecord.getHour()*60+timeRecord.getMinute(),timeRecord.getSecond());
			salesTime.setTime(timeRecord.getHour(),timeRecord.getMinute(),timeRecord.getSecond());
			
			salesTitle.setText(salesissuetext);
			PromptManager.showToast(context, "销售期已切换到第"+ currentIssueSales.getIssue()+ "期");
		}
//		salesTime.setTimeFormat("hh:mm:ss");
		salesTime.start();
		
		bundle = new Bundle();
		bundle.putInt("lotteryid", getLotteryId());
		bundle.putString("issue", currentIssueSales.getIssue());
		bundle.putInt("lotterytype", getLotteryType());
		bundle.putString("lotteryname", getLotteryName());
		
		ShoppingCart.getInstance().setIssue(currentIssueSales.getIssue());
		generateUpdate();
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
	
	private void changeNotice() {
		
		double totaldelivery=0.00;
		int noteNum=0;
		for(int i = 0; i < ShoppingCart.getInstance().getAppendList().size(); i++) {
			AppendInfo infoUpdata=ShoppingCart.getInstance().getAppendList().get(i).get("list_item_inputvalue");
			boolean selected=ShoppingCart.getInstance().getIsSelected().get(infoUpdata.getIssue());
			//注数，金额，倍数
			if(selected){
				noteNum+=1;
				int multiple=Integer.valueOf(infoUpdata.getMultiple());
				totaldelivery+=ShoppingCart.getInstance().getLotteryvalue()*multiple;
			}
		}
		
		String moneyInfo = context.getResources().getString(R.string.is_shopping_list_money);
		BigDecimal bPutin=new BigDecimal(totaldelivery);  
		double totalde=bPutin.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		moneyInfo = StringUtils.replaceEach(moneyInfo, new String[] {"MONEY"}, new String[] { String.valueOf(totalde)});

		String noticeInfo = context.getResources().getString(R.string.is_shopping_list_Append);
		noticeInfo = StringUtils.replaceEach(noticeInfo, new String[] { "PERIOD" }, new String[] {String.valueOf(noteNum)});
		
		appebdBottomMoney.setText(Html.fromHtml(moneyInfo));
		appebdBottomNote.setText(Html.fromHtml(noticeInfo));
	}
	
	private void generateUpdate(){
		mData = new ArrayList<Map<String,AppendInfo>>();
		
		int chase=Integer.parseInt(chasenumber.getText().toString());
		int multiple=Integer.parseInt(multipleNumber.getText().toString());
		
		String[] taskIssue=ConstantInformation.taskIssueMap.get(String.valueOf(bundle.getInt("lotteryid")));
		List<String> taskIssueList = Arrays.asList(taskIssue);
		
		int array = 0;
		for(int i=0; i<taskIssueList.size(); i++){
			String issue=taskIssueList.get(i);
			if(bundle.getString("issue").equals(issue)){
				array=i;
			}
		}
		
		List<String> taskMetelist=taskIssueList.subList(array, taskIssueList.size()-array);
		if(taskMetelist.size()>=chase){
			List<String> tasklist=taskIssueList.subList(array, array+chase);
			
			for(int i = 0; i < tasklist.size(); i++) {
				String issue=tasklist.get(i);
				Map<String, AppendInfo> item = new HashMap<String, AppendInfo>();
				AppendInfo appendInfo=new AppendInfo();
				appendInfo.setIssue(issue);
				appendInfo.setMultiple(multiple);
				//注数，金额，倍数
				int to=i+1;
				double totaldelivery=0.00;
				if(multiple>1)
					totaldelivery=ShoppingCart.getInstance().getLotteryvalue()*to+ShoppingCart.getInstance().getLotteryvalue()*multiple;
				else
					totaldelivery=ShoppingCart.getInstance().getLotteryvalue()*to;
				
				BigDecimal bPutin=new BigDecimal(totaldelivery);  
				double   totalde=bPutin.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				appendInfo.setPutin(totalde+"");
				item.put("list_item_inputvalue", appendInfo);
				mData.add(item);
			}
			mAdapter.setData(mData);
			mAdapter.notifyDataSetChanged();
		}else{
			getTicketDataRequest();
		}
		
		
	}
	
	private void appendBetting(){
		String promptInfo="";
		promptInfo+=bundle.getString("lotteryname")+"\t第"+bundle.getString("issue")+"期\n";
		final BettingInfo bettinginfo=new BettingInfo();
		bettinginfo.setCellphone(1);
		bettinginfo.setLotteryid(ShoppingCart.getInstance().getLotteryid());
		bettinginfo.setIssuestartNo(ShoppingCart.getInstance().getIssue());
		
		List<ProgramBuyInfo> projectinfoList =new ArrayList<ProgramBuyInfo>();
		
		for(int i=0; i<ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).size(); i++)
		{
			ProgramBuyInfo projectinfo=new ProgramBuyInfo();
			Ticket ticket = ShoppingCart.getInstance().getTicketsMap().get(bundle.getInt("lotteryid")).get(i);
			
			Integer lotterytype=ShoppingCart.getInstance().getLotterytype();
			PlayMenu playmenu=ticket.getSelectPlay();
			projectinfo.setMethodid(Integer.parseInt(playmenu.getMethodid()));
			projectinfo.setNums(ticket.getNum());
			projectinfo.setTimes(ShoppingCart.getInstance().getAppnumbers());
			
			double lottermode = ticket.getMoneyMode().getLucreReckon() * 2 * ticket.getNum();
			BigDecimal bd = new BigDecimal(lottermode * ShoppingCart.getInstance().getAppnumbers());
			Double lotteryvalue = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			
			projectinfo.setMoney(lotteryvalue);
			projectinfo.setMode(ticket.getMoneyMode().getLucreReckonId());
				
			projectinfo.setPoint(ShoppingCart.getInstance().isRetainRebate());
			switch (lotterytype) {
				case 0:
					if(playmenu.getJscode().equalsIgnoreCase("ZX3")){	//前三 后三直选
						if(playmenu.getPlaymode().equals("AGO")){
							projectinfo.setCodes(ticket.getWanNum().replaceAll(" ","&")+"|"+ticket.getQianNum().replaceAll(" ","&")+"|"+ticket.getBaiNum().replaceAll(" ","&"));
						}else if(playmenu.getPlaymode().equals("AFTER")){
							projectinfo.setCodes(ticket.getBaiNum().replaceAll(" ","&")+"|"+ticket.getShiNum().replaceAll(" ","&")+"|"+ticket.getGeNum().replaceAll(" ","&"));
						}
					}		
					else if(playmenu.getJscode().equalsIgnoreCase("ZUS")){	//前三 后三组选 组三 C(n,2)*2
						projectinfo.setCodes(ticket.getAssembleSscNum().replaceAll(" ","&"));
					}		
					else if(playmenu.getJscode().equalsIgnoreCase("ZUL")){	//前三 后三组选 组六 C(n,3)
						projectinfo.setCodes(ticket.getAssembleSscNum().replaceAll(" ","&"));
					}		
					else if(playmenu.getJscode().equalsIgnoreCase("ZX2")){	//前二 后二直选 n1*n2
						
						if(playmenu.getPlaymode().equals("AGO")){
							projectinfo.setCodes(ticket.getWanNum().replaceAll(" ","&")+"|"+ticket.getQianNum().replaceAll(" ","&"));
						}else if(playmenu.getPlaymode().equals("AFTER")){
							projectinfo.setCodes(ticket.getShiNum().replaceAll(" ","&")+"|"+ticket.getGeNum().replaceAll(" ","&"));
						}
					}		
					else if(playmenu.getJscode().equalsIgnoreCase("ZU2")){	//前二 后二组选 C(n,2)
						projectinfo.setCodes( ticket.getAssembleSscNum().replaceAll(" ","&"));
					}		
					else if(playmenu.getJscode().equalsIgnoreCase("DWD")){	//个、十、百、千、万 C(n,1)
						projectinfo.setCodes(ticket.getWanNum().replaceAll(" ","&")+"|"+ticket.getQianNum().replaceAll(" ","&")+"|"+ticket.getBaiNum().replaceAll(" ","&")+"|"+ticket.getShiNum().replaceAll(" ","&")+"|"+ticket.getGeNum().replaceAll(" ","&"));
					}		
					else if(playmenu.getJscode().equalsIgnoreCase("BDW1")){	//后三一码不定位 前三一码不定位 C(n,1)
						projectinfo.setCodes(ticket.getAssembleSscNum().replaceAll(" ","&"));
					}else if(playmenu.getJscode().equalsIgnoreCase("BDW2")){	//后三二码不定位 前三二码不定位 C(n,1)
						projectinfo.setCodes(ticket.getAssembleSscNum().replaceAll(" ","&"));
					}
					break;
				case 1:
					
					break;
				case 2:
					if (playmenu.getJscode().equals("LTZX3")) // 前三直选
					{
						projectinfo.setCodes(ticket.getOneNum().replaceAll(" ","&")+"|"+ticket.getTwoNum().replaceAll(" ","&")+"|"+ticket.getThreeNum().replaceAll(" ","&"));
					} else if (playmenu.getJscode().equals("LTZU3")) // 前三组选
					{
						projectinfo.setCodes(ticket.getAssembleSyFiveNum().replaceAll(" ","&"));
					} else if (playmenu.getJscode().equals("LTZX2")) // 前二直选
					{
						projectinfo.setCodes(ticket.getOneNum().replaceAll(" ","&")+"|"+ticket.getTwoNum().replaceAll(" ","&"));
					} else if (playmenu.getJscode().equals("LTZU2")) // 前二组选
					{
						projectinfo.setCodes(ticket.getAssembleSyFiveNum().replaceAll(" ","&"));
					} else if (playmenu.getJscode().equals("LTDWD")){ // 第一位 二位 三位
						projectinfo.setCodes(ticket.getOneNum().replaceAll(" ","&")+"|"+ticket.getTwoNum().replaceAll(" ","&")+"|"+ticket.getThreeNum().replaceAll(" ","&"));
					} else if (playmenu.getJscode().equals("LTRX1")){ 	// 任选一中一
						projectinfo.setCodes(ticket.getAssembleSyFiveNum().replaceAll(" ","&"));
					} else if (playmenu.getJscode().equals("LTRX2")){ 	// 任选二中二
						projectinfo.setCodes(ticket.getAssembleSyFiveNum().replaceAll(" ","&"));
					} else if (playmenu.getJscode().equals("LTRX3")){ 	// 任选三中三
						projectinfo.setCodes(ticket.getAssembleSyFiveNum().replaceAll(" ","&"));
					} else if (playmenu.getJscode().equals("LTRX4")){	//任选四中四
						projectinfo.setCodes(ticket.getAssembleSyFiveNum().replaceAll(" ","&"));
					}else if (playmenu.getJscode().equals("LTRX5")){	//任选五中五
						projectinfo.setCodes(ticket.getAssembleSyFiveNum().replaceAll(" ","&"));
					}else if (playmenu.getJscode().equals("LTRX6")){	//任选六中五
						projectinfo.setCodes(ticket.getAssembleSyFiveNum().replaceAll(" ","&"));
					}else if (playmenu.getJscode().equals("LTRX7")){	//任选七中五
						projectinfo.setCodes(ticket.getAssembleSyFiveNum().replaceAll(" ","&"));
					}else if (playmenu.getJscode().equals("LTRX8")){	//任选八中五
						projectinfo.setCodes(ticket.getAssembleSyFiveNum().replaceAll(" ","&"));
					}
					break;
			}
			
			projectinfoList.add(projectinfo);
		}
		
		bettinginfo.setLtproject(projectinfoList);
		
		Integer lotterynumber = ShoppingCart.getInstance().getLotterynumber();
		Integer issuesnumbers = 0;
		promptInfo+="总注数："+lotterynumber+"\n";
		
		for(Map.Entry<String, Boolean> entry:ShoppingCart.getInstance().getIsSelected().entrySet()){    
		     if(entry.getValue()){
		    	 issuesnumbers=issuesnumbers+1;
		     }
		}   
		
		promptInfo+="追号："+issuesnumbers+"期\n";
		
		double totalMoney=0.00;
		
		List<Map<String, AppendInfo>> appendList=ShoppingCart.getInstance().getAppendList();
		bettinginfo.setTracestatus(ShoppingCart.getInstance().chaseNumberNote()>1?true:false);
		Map<String,Integer> tracetimesMap=new HashMap<String,Integer>();	//追号倍数
		String traceissues[]=new String[appendList.size()];	//追号期数
		for(int i=0; i<appendList.size();i++){
			AppendInfo appendInfo=appendList.get(i).get("list_item_inputvalue");
			traceissues[i]=appendInfo.getIssue();
			totalMoney+=ShoppingCart.getInstance().getLotteryvalue()*appendInfo.getMultiple() ;
			tracetimesMap.put(appendInfo.getIssue(), appendInfo.getMultiple());
		}
		BigDecimal bPutin=new BigDecimal(totalMoney);  
		double   totalde=bPutin.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		promptInfo+="总金额："+totalde+"\n ";
		
		bettinginfo.setIsSelected(ShoppingCart.getInstance().getIsSelected());
		bettinginfo.setTracetimesMap(tracetimesMap);	//倍数信息
		bettinginfo.setTraceissues(traceissues);	//期号信息
		bettinginfo.setTracestop(appendSettings.isChecked());
		
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(promptInfo);
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//设置你的操作事项
				submitData(bettinginfo);
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
		
	}
	
	private void submitData(BettingInfo bettinginfo){
		new MyHttpTask<BettingInfo>() {
			@Override
			protected void onPreExecute() {
				PromptManager.showProgressDialog(context,"");
				super.onPreExecute();
			}

			@Override
			protected Message doInBackground(BettingInfo... params) {

				BettingEngine engine=BeanFactory.getImpl(BettingEngine.class);
				Message betting = engine.bettingNumber(params[0]);

				if (betting != null) {
					Oelement oelement = betting.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						// 成功了获取余额
						UserEngine userengine = BeanFactory.getImpl(UserEngine.class);
						Message balance = userengine.getBalance();
						if (balance != null) {
							oelement = balance.getBody().getOelement();
							if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
								BalanceElement element = (BalanceElement) balance.getBody().getElements().get(0);
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
						
						CustomDialog.Builder builder = new CustomDialog.Builder(context);
						builder.setMessage("投注成功！");
						builder.setTitle("温馨提示");
						builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
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
								dialog.dismiss();
							}
						});
						builder.create().show();
						
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
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
	 * 获取请求列表信息 
	 */
	private int r=0;
	private void getTicketDataRequest() {
		new MyHttpTask<Integer>() {
			
			@Override
			protected com.goldenasia.lottery.net.protocal.Message doInBackground(Integer... params) {
				// 获取数据——业务的调用
				CommonInfoEngine engine = BeanFactory.getImpl(CommonInfoEngine.class);
				return engine.getSpeciesListInfo(params[0]);
			}

			@Override
			protected void onPostExecute(com.goldenasia.lottery.net.protocal.Message result) {
				PromptManager.closeProgressDialog();
				// 更新界面
				if (result != null) {
					r=0;
					Oelement oelement = result.getBody().getOelement();
					if (ConstantValue.SUCCESS.equals(oelement.getErrorcode())) {
						SpeciesListElements speciesElement = (SpeciesListElements) result.getBody().getElements().get(0);
						issueAllMap=speciesElement.getIssueAllMap();
						appendIssueInfo();
						generateUpdate();
					} else {
						if(oelement.getErrorcode().equals("255")){
							PromptManager.showRelogin(context,oelement.getErrormsg(),oelement.getErrorcode());
						}else{
							PromptManager.showToast(context, oelement.getErrormsg());
						}
					}
				} else {
					// 可能：网络不通、权限、服务器出错、非法数据……
					r=r+1;
					if(r<3){
						getTicketDataRequest();
					}else{
						PromptManager.showToast(context, "网络状态差，请重试！");
					}
				}
				super.onPostExecute(result);
			}
		}.executeProxy(0);
		
	}
	
	private int lotteryid=0;
	
	public int getLotteryId() {
		// TODO Auto-generated method stub
		return lotteryid;
	}

	public void setLotteryId(int id) {
		// TODO Auto-generated method stub
		this.lotteryid=id;
	}
	
	private int lotteryType=0;
	
	public void setLotteryType(int type) {
		// TODO Auto-generated method stub
		this.lotteryType=type;
	}

	public int getLotteryType() {
		// TODO Auto-generated method stub
		return lotteryType;
	}
	
	private String lotteryName;
	
	public void setLotteryName(String name) {
		// TODO Auto-generated method stub
		this.lotteryName=name;
	}
	
	public String getLotteryName() {
		// TODO Auto-generated method stub
		return lotteryName;
	}
}
