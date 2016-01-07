package com.goldenasia.lottery.view.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.StringUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.LucreMode;
import com.goldenasia.lottery.bean.LucreModeList;
import com.goldenasia.lottery.bean.ShoppingCart;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.GameHistory;
import com.goldenasia.lottery.view.LotteryHistoryHall;
import com.goldenasia.lottery.view.LotteryHomeHall;
import com.goldenasia.lottery.view.MySelfHall;
import com.goldenasia.lottery.view.adapter.MoneyModeGroupAdapter;
import com.goldenasia.lottery.view.adapter.PopupAdapter;
import com.goldenasia.lottery.view.controls.SegmentedControlView;

/**
 * 控制底部导航容器
 * 
 * @author Administrator
 * 
 */
@SuppressLint("NewApi")
public class BottomManager implements Observer {
	protected static final String TAG = "BottomManager";
	/******************* 第一步：管理对象的创建(单例模式) **************/
	// 创建一个静态实例
	private static BottomManager instrance;

	private Activity activity;

	// 构造私有
	private BottomManager() {
	}

	// 提供统一的对外获取实例的入口
	public static BottomManager getInstrance() {
		if (instrance == null) {
			instrance = new BottomManager();
		}
		return instrance;
	}

	/******************* 第二步：初始化各个导航容器及相关控件设置监听 **********/

	// 底部菜单容器
	private RelativeLayout bottomMenuContainer;
	// 底部导航
	private LinearLayout commonBottom;// 购彩通用导航
	private LinearLayout playBottom;// 购彩
	// 导航按钮

	// 购彩导航底部按钮及提示信息
	private Button cleanButton;
	private Button randomButton;
	private PopupWindow popupWindow;
	private ListView mode_group;
	private View viewmode;
	private List<String> groups;
	private Button moneymodeButton;
	private Button addButton;
	
	// 通用导航底部按钮
	private LinearLayout homeButton;
	private LinearLayout historyLotteryButton;
	private LinearLayout gameHistoryButton;
	private LinearLayout myselfButton;
	
	private TextView homeViewIco;
	private TextView historyLotteryViewIco;
	private TextView gameHistoryViewIco;
	private TextView myselfViewIco;
	
	private TextView homeTextView;
	private TextView historyLotteryTextView;
	private TextView gameHistoryTextView;
	private TextView myselfTextView;
	
	private Typeface fonts = null;

	public void init(Activity activityparam) {
		this.activity = activityparam;
		fonts=Typeface.createFromAsset(activity.getAssets(),"fontawesome-webfont.ttf");
		
		bottomMenuContainer = (RelativeLayout) activity.findViewById(R.id.nb_bottom);
		// 底部容器
		commonBottom = (LinearLayout) activity.findViewById(R.id.nb_bottom_common);
		// 选号底部容
		playBottom = (LinearLayout) activity.findViewById(R.id.nb_bottom_game);

		// 通用底部 操作
		homeButton = (LinearLayout) activity.findViewById(R.id.nb_bottom_home_hall);
		historyLotteryButton = (LinearLayout) activity.findViewById(R.id.nb_bottom_history_lottery_hall);
		gameHistoryButton = (LinearLayout) activity.findViewById(R.id.nb_bottom_game_history_hall);
		myselfButton = (LinearLayout) activity.findViewById(R.id.nb_bottom_lottery_myself_hall);
		
		homeViewIco = (TextView) activity.findViewById(R.id.nb_bottom_home_hall_ico);
		historyLotteryViewIco = (TextView) activity.findViewById(R.id.nb_bottom_history_lottery_hall_ico);
		gameHistoryViewIco = (TextView) activity.findViewById(R.id.nb_bottom_game_history_hall_ico);
		myselfViewIco = (TextView) activity.findViewById(R.id.nb_bottom_lottery_myself_hall_ico);
		
		homeTextView = (TextView) activity.findViewById(R.id.nb_bottom_home_hall_text);
		historyLotteryTextView = (TextView) activity.findViewById(R.id.nb_bottom_history_lottery_hall_text);
		gameHistoryTextView = (TextView) activity.findViewById(R.id.nb_bottom_game_history_hall_text);
		myselfTextView = (TextView) activity.findViewById(R.id.nb_bottom_lottery_myself_hall_text);
		
		// 投注底部 操作
		cleanButton = (Button) activity.findViewById(R.id.nb_bottom_game_choose_clean);
		moneymodeButton = (Button) activity.findViewById(R.id.nb_bottom_game_choose_mode);
		randomButton = (Button) activity.findViewById(R.id.nb_bottom_game_choose_random);
		addButton = (Button) activity.findViewById(R.id.nb_bottom_game_choose_ok);

		// 设置监听
		setListener();
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		
		String cleanIco=activity.getResources().getString(R.string.fa_trash_o);
		cleanButton.setText(cleanIco);
		cleanButton.setTypeface(fonts);
		
		// 清空按钮
		cleanButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Log.i(TAG, "点击清空按钮");
				// 获取到当前正在展示的界面
				BaseUI current = HallMiddleManager.getInstance().getCurrentUI();

				/*
				 * if (current instanceof PlaySSQ) { ((PlaySSQ)
				 * current).clear(); } if (current instanceof Play3D) {
				 * ((Play3D) current).clear(); } if (current instanceof PlayQLC)
				 * { ((PlayQLC) current).clear(); }
				 */

				if (current instanceof PlayGame) {
					((PlayGame) current).clearMethod();
				}

			}
		});
		// 金币选择模式
		// 需要设置一下此参数，点击外边可消失
		moneymodeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showLeftMenu(v);
			}
		});

		String randomIco=activity.getResources().getString(R.string.fa_random);
		randomButton.setText(randomIco);
		randomButton.setTypeface(fonts);
		// 随机按钮
		randomButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Log.i(TAG, "点击清空按钮");
				// 获取到当前正在展示的界面
				BaseUI current = HallMiddleManager.getInstance().getCurrentUI();

				if (current instanceof PlayGame) {
					((PlayGame) current).randomMethod();
				}

			}
		});
		
		// 选好按钮
		addButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				BaseUI current = HallMiddleManager.getInstance().getCurrentUI();
				if (current instanceof PlayGame) {
					((PlayGame) current).done();
				}
			}
		});

		String homeIco=activity.getResources().getString(R.string.fa_shopping_cart);
		homeViewIco.setText(homeIco);
		homeViewIco.setTextColor(activity.getResources().getColor(R.color.red2));  
		homeViewIco.setTypeface(fonts);
		
		String homeText=activity.getResources().getString(R.string.is_home_ico_mune_label);
		homeTextView.setText(homeText);
		homeTextView.setTextColor(activity.getResources().getColor(R.color.red2));  
		homeTextView.setTypeface(fonts);
		
		// 购种大厅
		homeButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				selectSwitch(1);
				HallMiddleManager.getInstance().changeUI(LotteryHomeHall.class);
			}
		});

		String historyLotteryIco=activity.getResources().getString(R.string.fa_th_list);
		historyLotteryViewIco.setText(historyLotteryIco);
		historyLotteryViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
		historyLotteryViewIco.setTypeface(fonts);
		
		String historyLotteryText=activity.getResources().getString(R.string.is_newlottery_ico_mune_label);
		historyLotteryTextView.setText(historyLotteryText);
		historyLotteryTextView.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
		historyLotteryTextView.setTypeface(fonts);
		
		// 历史大厅
		historyLotteryButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				selectSwitch(2);
				HallMiddleManager.getInstance().changeUI(LotteryHistoryHall.class);
			}
		});

		String gameHistoryIco=activity.getResources().getString(R.string.fa_clock_o);
		gameHistoryViewIco.setText(gameHistoryIco);
		gameHistoryViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
		gameHistoryViewIco.setTypeface(fonts);
		
		String gameHistoryText=activity.getResources().getString(R.string.is_gamehistory_ico_mune_label);
		gameHistoryTextView.setText(gameHistoryText);
		gameHistoryTextView.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
		gameHistoryTextView.setTypeface(fonts);
		
		// 活动大厅
		gameHistoryButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				selectSwitch(3);
				HallMiddleManager.getInstance().changeUI(GameHistory.class);
			}
		});
		
		String myselfIco=activity.getResources().getString(R.string.fa_user);
		myselfViewIco.setText(myselfIco);
		myselfViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
		myselfViewIco.setTypeface(fonts);
		
		
		String myselfText=activity.getResources().getString(R.string.is_accountcenter_ico_mune_label);
		myselfTextView.setText(myselfText);
		myselfTextView.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
		myselfTextView.setTypeface(fonts);
		
		// 个人信息
		myselfButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(new DebugActivity(),"用户名或密码错误，请重新登录",
				selectSwitch(4);
				HallMiddleManager.getInstance().changeUI(MySelfHall.class);
			}
		});
		UserType();
	}
	
	public void UserType(){
		if(GlobalParams.PARENTID.equals("0")){
			homeButton.setClickable(false);
			historyLotteryButton.setClickable(false);
			gameHistoryButton.setClickable(true);
			myselfButton.setClickable(true);
		}else{
			homeButton.setClickable(true);
			historyLotteryButton.setClickable(true);
			gameHistoryButton.setClickable(true);
			myselfButton.setClickable(true);
		}
	}

	public void selectSwitch(int cutover){
		
		switch (cutover) {
			case 1:
				homeViewIco.setTextColor(activity.getResources().getColor(R.color.red2));  
				homeTextView.setTextColor(activity.getResources().getColor(R.color.red2)); 
				
				historyLotteryViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				historyLotteryTextView.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				
				gameHistoryViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				gameHistoryTextView.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
				
				myselfViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				myselfTextView.setTextColor(activity.getResources().getColor(R.color.lightgray));
				
				break;
			case 2:
				homeViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				homeTextView.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
				
				historyLotteryViewIco.setTextColor(activity.getResources().getColor(R.color.red2));  
				historyLotteryTextView.setTextColor(activity.getResources().getColor(R.color.red2));  
				
				gameHistoryViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				gameHistoryTextView.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
				
				myselfViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				myselfTextView.setTextColor(activity.getResources().getColor(R.color.lightgray));
				break;
			case 3:
				homeViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				homeTextView.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
				
				historyLotteryViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				historyLotteryTextView.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				
				gameHistoryViewIco.setTextColor(activity.getResources().getColor(R.color.red2));  
				gameHistoryTextView.setTextColor(activity.getResources().getColor(R.color.red2)); 
				
				myselfViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				myselfTextView.setTextColor(activity.getResources().getColor(R.color.lightgray));
				break;
			case 4:
				homeViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				homeTextView.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
				
				historyLotteryViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				historyLotteryTextView.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				
				gameHistoryViewIco.setTextColor(activity.getResources().getColor(R.color.lightgray));  
				gameHistoryTextView.setTextColor(activity.getResources().getColor(R.color.lightgray)); 
				
				myselfViewIco.setTextColor(activity.getResources().getColor(R.color.red2));  
				myselfTextView.setTextColor(activity.getResources().getColor(R.color.red2));
				break;
			default:
				break;
		}
	}
	
	/**
	 * 显示元角分模式
	 * 
	 * @param parent
	 */
	private void showLeftMenu(View parent) {
		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			viewmode = layoutInflater.inflate(R.layout.nb_play_moneymode_group_list, null);

			mode_group = (ListView) viewmode.findViewById(R.id.nb_play_moneymode_group);
			// 加载数据
			groups = new ArrayList<String>();
			for(int i=0; i<LucreModeList.getLucremode().size();i++)
			{
				LucreMode lucmode=LucreModeList.getLucremode().get(i);
				groups.add(lucmode.getLucreUnit());
			}
			
			MoneyModeGroupAdapter groupAdapter = new MoneyModeGroupAdapter(activity, groups);
			mode_group.setAdapter(groupAdapter);
			// 创建一个PopuWidow对象
			popupWindow = new PopupWindow(viewmode, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
		}

		// 使其聚集
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);  
		popupWindow.update(); 
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setWidth(moneymodeButton.getWidth());
//		int sf=moneymodeButton.getWidth()+cleanButton.getWidth();
		//popupWindow.showAtLocation(parent,Gravity.NO_GRAVITY, sf-30,moneymodeButton.getHeight());
		int[] location = new int[2];  
		parent.getLocationOnScreen(location);
//		popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1]);
//		popupWindow.showAtLocation(parent,(Gravity.BOTTOM - moneymodeButton.getHeight()) | Gravity.LEFT, (moneymodeButton.getWidth() * 2)-(moneymodeButton.getWidth()/2), Gravity.BOTTOM-(moneymodeButton.getHeight()+moneymodeButton.getHeight()/2));
		
//		popupWindow.showAsDropDown(parent);
		popupWindow.showAtLocation(parent, Gravity.LEFT | Gravity.BOTTOM, location[0],160);
		
		mode_group.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,int position, long id) {
				// 修改模式标题title
				moneymodeButton.setText(groups.get(position));
				//playBottomModeUnit.setText(groups.get(position));
				// 传递选号界面
				BaseUI current = HallMiddleManager.getInstance().getCurrentUI();
				if (current instanceof PlayGame) {
					((PlayGame) current).setMoneyModeMethod(LucreModeList.getLucremode().get(position));
				}

				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}
		});
	}
	
	private void initBotton() {
		bottomMenuContainer.setVisibility(View.GONE);
	}

	/****************** 第三步：控制各个导航容器的显示和隐藏 *****************************************/

	/**
	 * 转换到隐藏
	 */
	public void showHideAllBottom() {
		initBotton();
	}
	
	/**
	 * 转换到通用导航
	 */
	public void showCommonBottom() {
		if (bottomMenuContainer.getVisibility() == View.GONE || bottomMenuContainer.getVisibility() == View.INVISIBLE) {
			bottomMenuContainer.setVisibility(View.VISIBLE);
		}
		commonBottom.setVisibility(View.VISIBLE);
		playBottom.setVisibility(View.INVISIBLE);
	}

	/**
	 * 转换到购彩
	 */
	public void showGameBottom() {
		if (bottomMenuContainer.getVisibility() == View.GONE || bottomMenuContainer.getVisibility() == View.INVISIBLE) {
			bottomMenuContainer.setVisibility(View.VISIBLE);
		}
		commonBottom.setVisibility(View.INVISIBLE);
		playBottom.setVisibility(View.VISIBLE);
	}

	/**
	 * 改变底部导航容器显示情况
	 */
	public void changeBottomVisiblity(int type) {
		if (bottomMenuContainer.getVisibility() != type)
			bottomMenuContainer.setVisibility(type);
	}

	/*********************** 第四步：控制玩法导航内容显示 ********************************************/
	/**
	 * 设置玩法底部 Bets提示信息
	 * @param notice
	 * @param betsdigit 
	 */
//	public void changeGameBottomBetsNotice(String notice, int betsdigit) {
//		playBottomNotice.setText(Html.fromHtml(notice));
//	}

	public void changeGameBottomMoneyMode(LucreMode notice) {
		moneymodeButton.setText(notice.getLucreUnit());
	}
	/**
	 * 设置选好按钮状态
	 * @param notice
	 */
	public void changeChooseButtomFalse() {
		addButton.setFocusable(false);
	}
	
	public void changeChooseButtomTrue() {
		addButton.setFocusable(true);
	}
	
	@Override
	public void update(Observable observable, Object data) {

		if (data != null && StringUtils.isNumeric(data.toString())) {
			int id = Integer.parseInt(data.toString());
			switch (id) {
			case ConstantValue.VIEW_WELCOME:
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_LOGIN: 				// 登录 隐藏
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_HOME_HALL: 			// 首页
				showCommonBottom();
				break;
			case ConstantValue.VIEW_HISTORY: 			// 历史开奖
				showCommonBottom();
				break;
			case ConstantValue.VIEW_GAMEHISTORY: 		// 游戏记录
				showCommonBottom();
				break;
			case ConstantValue.VIEW_MYSELF_HALL: 		// 我的彩票
				showCommonBottom();
				break;
			case ConstantValue.VIEW_SSC: 				// 时时彩选号界面
				showGameBottom();
				break;
			case ConstantValue.VIEW_PLAYSYFIVE:			//十一选五
				showGameBottom();
				break;
			case ConstantValue.VIEW_SSQ: 				// 双色球选号界面
				showGameBottom();
				break;
			case ConstantValue.VIEW_SHOPPING: 			// 购彩
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_SECOND:	
				
			case ConstantValue.VIEW_SETTINGS:			//设置
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_MESSAGE: 			// 个人消息与系统消息
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_ORDERDETAILS: 		// 订单详细
				showHideAllBottom();
				break;	
			case ConstantValue.VIEW_HISTORY_DETAIL: 	// 历史开奖详情
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_INFO_BACK: 			// 绑定信息
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_INFO_WITHDRAWALS:	// 取款信息
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_ACCOUNT_PASSWORD:	//帐号密码
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_FUNDS_PASSWORD:		//资金密码
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_ADD_USER:			//添加用户
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_PREBET:				//追号
				changeBottomVisiblity(View.GONE);
				break;
			case ConstantValue.VIEW_CAMPAIGN_HALL:		//活动
				showHideAllBottom();
				break;
			case ConstantValue.VIEW_APPENDORDERDETAILS:		//追号详情
				showHideAllBottom();
				break;
			}
		}

	}

}
