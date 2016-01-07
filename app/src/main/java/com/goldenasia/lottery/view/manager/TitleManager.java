package com.goldenasia.lottery.view.manager;

import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldenasia.lottery.ConstantValue;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.ConstantInformation;
import com.goldenasia.lottery.bean.ShoppingCart;
import com.goldenasia.lottery.util.MenuUtility;
import com.goldenasia.lottery.view.LotteryHomeHall;
import com.goldenasia.lottery.view.controls.SelectPicPopupWindow;
import com.goldenasia.lottery.view.custom.PulldownMenuView;
import com.goldenasia.lottery.view.custom.PulldownMenuView.OnMenuItemClickListener;

/**
 * 管理标题容器的工具
 * 
 * @author Administrator
 * 
 */
public class TitleManager implements Observer {
	// 显示和隐藏
	private SelectPicPopupWindow menuWindow;
	private static TitleManager instance = new TitleManager();

	private Activity activity;

	private TitleManager() {

	}

	public static TitleManager getInstance() {
		return instance;
	}

	private RelativeLayout commonContainer; // 常见的容器
	private RelativeLayout loginContainer; // 未登陆称号
	private RelativeLayout playselectionContainer; // 购彩选号
	private RelativeLayout bettingContainer; // 返回按钮 标题
	private RelativeLayout myselfContainer; // 我的彩票
	private RelativeLayout orderDetailsContainer; // 订单详情
	private RelativeLayout titleChangeContainer;	//

	// 常见容器标题
	private Button gobackCommon; // 常用容器返回
	private TextView contentCommonTitle; // 常用容器标题
	private ImageView helpCommonTitle; // 常用容器帮助

	// 已经登录标题
	private TextView homeTitle; // 已登录邮件按钮

	// 购彩 标题
	private Button playGoBack; // 用户消息返回
	private LinearLayout linearLayoutTopic; // 用户消息标题
	// PulldownMenuView基本操作类
	private MenuUtility menuUtility = null;
	// PulldownMenuView基本操作类
	private PulldownMenuView pullDownMenu = null;
	private TextView tvTopic = null;
	private Button ivTopic = null;

	// 投注 消息 返回按钮 标题
	private Button bettingBackButton;
	private TextView bettingTitle;
	private Button bettingClear;

	// 我的彩票标题
	private TextView myselfTitle;
	private ImageView settingMyself;

	private Button orderDetailsBack;
	private TextView orderDetailsTitle;
	private Button orderDetailsCancel;
	
	//设置通用标题
	private Button goBackSettingbutton;
	private TextView settingCommonTitle;

	private Typeface fonts = null;
	
	public void init(Activity activityparam) {
		activity = activityparam;
		fonts=Typeface.createFromAsset(activity.getAssets(),"fontawesome-webfont.ttf");
		
		// 添加界面管理
		commonContainer = (RelativeLayout) activity.findViewById(R.id.nb_common_container); // 常见的标题
		loginContainer = (RelativeLayout) activity.findViewById(R.id.nb_login_title); // 已经登录
		playselectionContainer = (RelativeLayout) activity.findViewById(R.id.nb_play_selection_title); // 购彩选号 标题与玩法选择
		bettingContainer = (RelativeLayout) activity.findViewById(R.id.nb_betting_title); // 投注标题 返回按钮 文字
		myselfContainer = (RelativeLayout) activity.findViewById(R.id.nb_myself_title); // 我的彩票
		orderDetailsContainer = (RelativeLayout) activity.findViewById(R.id.nb_order_details_title); // 我的彩票
		titleChangeContainer =(RelativeLayout)activity.findViewById(R.id.nb_setting_change_title); 

		// 界面 通用容器
		gobackCommon = (Button) activity.findViewById(R.id.nb_common_title_goback);
		contentCommonTitle = (TextView) activity.findViewById(R.id.nb_common_title_content);
		helpCommonTitle = (ImageView) activity.findViewById(R.id.nb_common_title_help);

		// 大厅邮件信息
		homeTitle = (TextView) activity.findViewById(R.id.nb_home_title);

		// 购彩选号 标题 图标
		ivTopic = (Button) activity.findViewById(R.id.imageViewTopic);
		tvTopic = (TextView) activity.findViewById(R.id.textViewTopic);
		linearLayoutTopic = (LinearLayout) activity.findViewById(R.id.nb_play_select_title);
		playGoBack = (Button) activity.findViewById(R.id.nb_play_titlegoback);

		// 消息返回按钮
		bettingBackButton = (Button) activity.findViewById(R.id.nb_goback_button);
		bettingTitle = (TextView) activity.findViewById(R.id.nb_bettingback_title);
		bettingClear = (Button) activity.findViewById(R.id.nb_bettingtitle_list_clear);

		// 我的消息
		myselfTitle = (TextView) activity.findViewById(R.id.nb_myself_titlecontent);
		settingMyself = (ImageView) activity.findViewById(R.id.nb_myself_setting);

		// 订单详情标题
		orderDetailsBack = (Button) activity.findViewById(R.id.nb_order_details_back);
		orderDetailsTitle = (TextView) activity.findViewById(R.id.nb_order_details_titlecontent);
		orderDetailsCancel = (Button) activity.findViewById(R.id.nb_order_details_cancel);
		
		goBackSettingbutton=(Button) activity.findViewById(R.id.nb_setting_goback_button);
		settingCommonTitle = (TextView) activity.findViewById(R.id.nb_setting_change_titlecontent);
		

		setListener();

	}

	private void setListener() {
		
		String chevrondown=activity.getResources().getString(R.string.fa_caret_down);
		ivTopic.setText(chevrondown);
		ivTopic.setTypeface(fonts);
		linearLayoutTopic.setOnClickListener(TopicOnClickListener);
		
		String resGoBackIco=activity.getResources().getString(R.string.fa_chevron_left);
		gobackCommon.setText(resGoBackIco);
		gobackCommon.setTypeface(fonts);
		// 通用标题
		gobackCommon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideKeyboard(v);
				HallMiddleManager.getInstance().goBack();
			}
		});


		helpCommonTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("help");
			}
		});

		// 玩法选号 界面标题
		playGoBack.setText(resGoBackIco);
		playGoBack.setTypeface(fonts);
		playGoBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HallMiddleManager.getInstance().goBack();
			}
		});

		bettingBackButton.setText(resGoBackIco);
		bettingBackButton.setTypeface(fonts);
		// Simple信息
		bettingBackButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * BaseUI current =
				 * HallMiddleManager.getInstance().getCurrentUI(); if (current
				 * instanceof ShopManage) { ((ShopManage)
				 * current).clearMethod(); }
				 * HallMiddleManager.getInstance().changeUI
				 * (LotteryHomeHall.class);
				 */
				hideKeyboard(v);
//				if (ShoppingCart.getInstance().getLotterynumber() >= 1) {
//					menuWindow = new SelectPicPopupWindow(activity, itemsOnClick);
//					// 显示窗口
//					menuWindow.showAtLocation(activity.findViewById(R.id.keyboardRelativeLayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
//				}else 
//				{
					HallMiddleManager.getInstance().goBack();
//				}
			}
		});

		// 我的个人界面
		myselfTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		settingMyself.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});
		
		String resTrashIco=activity.getResources().getString(R.string.fa_trash_o);
		bettingClear.setText(resTrashIco+"清空");
		bettingClear.setTypeface(fonts);
		bettingClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideKeyboard(v);
				BaseUI current =HallMiddleManager.getInstance().getCurrentUI(); 
				if (current instanceof ShopManage) { 
					((ShopManage) current).clearMethod(); 
				}
			}
		});

		// 订单详情
		orderDetailsBack.setText(resGoBackIco);
		orderDetailsBack.setTypeface(fonts);
		orderDetailsBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideKeyboard(v);
				HallMiddleManager.getInstance().goBack();
			}
		});
		
		orderDetailsTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("help");

			}
		});
		orderDetailsCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				BaseUI current = HallMiddleManager.getInstance().getCurrentUI();
				if (current instanceof OrderOperation) {
					((OrderOperation) current).rescind();
				}
			}
		});
		
		goBackSettingbutton.setText(resGoBackIco);
		goBackSettingbutton.setTypeface(fonts);
		
		goBackSettingbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				hideKeyboard(v);
				HallMiddleManager.getInstance().goBack();
			}
		});
	}

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {

		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
				case R.id.btn_return:
					HallMiddleManager.getInstance().goBack();
					break;
				case R.id.btn_take_clear:
					ShoppingCart.getInstance().clear();
					HallMiddleManager.getInstance().changeUI(LotteryHomeHall.class);
					break;
				default:
					break;
			}
		}

	};

	/**
	 * 显示PulldownMenuView
	 */
	protected void showPulldownMenu() {
		pullDownMenu = menuUtility.getPulldownMenuView((String) tvTopic.getText());
		String menuTrangleUp=activity.getResources().getString(R.string.fa_caret_up);
		ivTopic.setText(menuTrangleUp);
		ivTopic.setTypeface(fonts);
	}

	/**
	 * 隐藏PulldownMenuView
	 */
	protected void hidePulldownMenu() {
		pullDownMenu.releasePopupMenuView();
		String menuTrangleDown=activity.getResources().getString(R.string.fa_caret_down);
		ivTopic.setText(menuTrangleDown);
		ivTopic.setTypeface(fonts);
	}

	// 顶部菜单事件监听器
	private OnClickListener TopicOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 开始显示下拉菜单
			showPulldownMenu();

			// TODO Auto-generated method stub
			pullDownMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public void onMenuItemClick(AdapterView<?> parent, View view, int position) {
					// TODO Auto-generated method stub
					BaseUI current = HallMiddleManager.getInstance().getCurrentUI();
					if (current instanceof PlayGame) {
						if (current instanceof PlayGameMethod) {
							((PlayGame) current).clearMethod();
							menuTitle(current,((PlayGame) current).getLotteryId(),position);
						}
					}
					// layoutBody.setBackgroundResource(ConstantCategoryMenu.newsPlayRes[position]);
				}

				@Override
				public void hideMenu() {
					// TODO Auto-generated method stub
					hidePulldownMenu();
				}
			});

			pullDownMenu.show();
		}
	};

	public void setTitle(String text) {
		tvTopic.setText(text);
	}

	/**
	 * 初始化菜单数据
	 * 
	 * @param notice
	 */
	public void getInitMenu(int lotteryid, String textMune) {
		String[] notice = ConstantInformation.playmuneMap.get(String.valueOf(lotteryid));

		if (notice != null) {
			menuUtility = new MenuUtility(activity, notice, 0, playselectionContainer);
			String name = null;
			for (int m = 0; m < notice.length; m++) {
				if (textMune.equals(notice[m]))
					name = notice[m];
			}
			setTitle(name);
			pullDownMenu = menuUtility.getPulldownMenuView(name);

		}
	}

	/**
	 * 标题菜单显示内容
	 */
	public void menuTitle(BaseUI current, int lotteryId, int position) {

		String[] notice =ConstantInformation.playmuneMap.get(String.valueOf(lotteryId));
		
		tvTopic.setText(notice[position]);
		((PlayGameMethod) current).formulateMethod(notice[position]);
	}

	private void initTitle() {
		commonContainer.setVisibility(View.GONE);
		loginContainer.setVisibility(View.GONE);
		myselfContainer.setVisibility(View.GONE);
		playselectionContainer.setVisibility(View.GONE);
		bettingContainer.setVisibility(View.GONE);
		orderDetailsContainer.setVisibility(View.GONE);
		titleChangeContainer.setVisibility(View.GONE);
	}

	public void hideKeyboard(View v){
		InputMethodManager in = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (in.isActive()) {
			in.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}
	/**
	 * 欢迎标题
	 */

	public void showWelcomeTitle() {
		initTitle();
	}

	/**
	 * 显示通用标题
	 */
	public void showCommonTitle() {
		initTitle();
		commonContainer.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示未登录的标题
	 */
	public void showUnLoginTitle() {
		initTitle();
	}

	/**
	 * 显示登陆的标题
	 */
	public void showLoginTitle() {
		initTitle();
		loginContainer.setVisibility(View.VISIBLE);

	}

	/**
	 * 显示我的彩票标题
	 */
	public void showMySelfTitle() {
		initTitle();
		//myselfContainer.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示选号标题
	 */
	public void showPickTitle() {
		initTitle();
		playselectionContainer.setVisibility(View.VISIBLE);

	}
	
	/**
	 * 显示设置标题
	 */
	public void showSettingTitle() {
		initTitle();
		playselectionContainer.setVisibility(View.VISIBLE);

	}

	/**
	 * 投注信息标题
	 * @param title
	 */
	public void showBettingTitle() {
		initTitle();
		bettingContainer.setVisibility(View.VISIBLE);
	}

	public void showOrderDetailsTitle() {
		initTitle();
		orderDetailsContainer.setVisibility(View.VISIBLE);
	}
	
	public void showChangeContainerTitle() {
		initTitle();
		titleChangeContainer.setVisibility(View.VISIBLE);
	}
	
	
	
	/**
	 * 修改首页标题
	 * 
	 * @param title
	 */
	public void changeHomeTitle(String title) {
		homeTitle.setText(title);
	}

	/**
	 * 修改通用标题
	 * 
	 * @param title
	 */
	public void changeCommonTitle(String title) {
		contentCommonTitle.setText(title);
	}

	/**
	 * 修改投注标题
	 */
	public void changeBettingTitle(String title) {
		bettingTitle.setText(title);
	}
	
	/**
	 * 修改设置标题
	 */
	public void changeSettingTitle(String title) {
		settingCommonTitle.setText(title);
	}

	/**
	 * 修改订单标题
	 */
	public void changeOrderCancelTitleInhibit() {
		orderDetailsCancel.setEnabled(true); //开启
		orderDetailsCancel.setTextColor(activity.getResources().getColor(R.color.golden));
	}

	public void changeOrderCancelTitleEnable() {
		orderDetailsCancel.setEnabled(false); //关闭
		orderDetailsCancel.setTextColor(activity.getResources().getColor(R.color.gray));
	}

	@Override
	public void update(Observable observable, Object data) {
		if (data != null && StringUtils.isNumeric(data.toString())) {
			int id = Integer.parseInt(data.toString());
			switch (id) {
			case ConstantValue.VIEW_WELCOME:
				showWelcomeTitle();
				break;
			case ConstantValue.VIEW_LOGIN: 				// 登录
				showWelcomeTitle();						//showUnLoginTitle();
				break;
			case ConstantValue.VIEW_HOME_HALL: 			// 首页
				showLoginTitle();
				/*if (GlobalParams.isLogin) {
					showLoginTitle();
					// String info = "用户名：" + GlobalParams.USERNAME + "\r\n" +
					// "余额:" + GlobalParams.MONEY;
					// userInfo.setText(info);
				} else {
					showUnLoginTitle();
				}*/
				break;
			case ConstantValue.VIEW_HISTORY: 			// 历史开奖
				showLoginTitle();
				break;
			case ConstantValue.VIEW_CAMPAIGN_HALL: 		// 活动大厅
				showChangeContainerTitle();
				break;
			case ConstantValue.VIEW_GAMEHISTORY:		//游戏记录
				showLoginTitle();
				break;
			case ConstantValue.VIEW_MYSELF_HALL: 		// 我的彩票
				showMySelfTitle();
				break;
			case ConstantValue.VIEW_SECOND:
				
			case ConstantValue.VIEW_SSC: 				// 时时彩选号标题
				showPickTitle();
				break;
			case ConstantValue.VIEW_PLAYSYFIVE:			//十一选五
				showPickTitle();
				break;
			case ConstantValue.VIEW_SSQ: 				// 双色球选号标题
				showPickTitle();
				break;
			case ConstantValue.VIEW_SHOPPING: 			// 投注标题
				showBettingTitle();
				break;
			case ConstantValue.VIEW_MESSAGE: 			// 简单标题
				showWelcomeTitle();
				break;
			case ConstantValue.VIEW_ORDERDETAILS: 		// 订单详情
				showOrderDetailsTitle();
				break;
			case ConstantValue.VIEW_HISTORY_DETAIL: 	// 历史开奖详情
				showCommonTitle();
				break;
			case ConstantValue.VIEW_INFO_BACK: 			// 绑定信息
				showChangeContainerTitle();
				break;
			case ConstantValue.VIEW_INFO_WITHDRAWALS: 	// 取款信息
				showChangeContainerTitle();
				break;
			case ConstantValue.VIEW_ACCOUNT_PASSWORD:	//帐号密码
				showChangeContainerTitle();
				break;
			case ConstantValue.VIEW_FUNDS_PASSWORD:		//资金密码
				showChangeContainerTitle();
				break;
			case ConstantValue.VIEW_ADD_USER:			//添加用户
				showChangeContainerTitle();
				break;
			case ConstantValue.VIEW_SETTINGS: 			// 设置
				showCommonTitle();
				break;
			case ConstantValue.VIEW_PREBET:				//追号
				showChangeContainerTitle();
				break;
			case ConstantValue.VIEW_MESSAGE_ONESELF_DETAIL:	//个人消息详情
				showChangeContainerTitle();
				break;
			case ConstantValue.VIEW_MESSAGE_SYSTEM_DETAIL:	//系统消息详情
				showChangeContainerTitle();
				break;
			case ConstantValue.VIEW_APPENDORDERDETAILS:		//追号详情
				showChangeContainerTitle();
				break;
			}
		}

	}
}
