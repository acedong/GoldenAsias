package com.goldenasia.lottery;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.goldenasia.lottery.util.FadeUtil;
import com.goldenasia.lottery.util.PromptManager;
import com.goldenasia.lottery.view.UserLogin;
import com.goldenasia.lottery.view.WelcomeUI;
import com.goldenasia.lottery.view.manager.BaseUI;
import com.goldenasia.lottery.view.manager.BottomManager;
import com.goldenasia.lottery.view.manager.HallMiddleManager;
import com.goldenasia.lottery.view.manager.KeyboardListenRelativeLayout;
import com.goldenasia.lottery.view.manager.TitleManager;
import com.goldenasia.lottery.view.manager.KeyboardListenRelativeLayout.IOnKeyboardStateChangedListener;

public class MainActivity extends Activity {
	private RelativeLayout middle;// 中间占着位置的容器
	private KeyboardListenRelativeLayout relativeLayout;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// changeUI();
			changeUI(new UserLogin(MainActivity.this));
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nb_main);

		// // commons-codec.jar:加密用——MD5
		// DigestUtils.md5Hex("");
		// // commons-lang3-3.0-beta.jar:字符串操作
		// // 字符串非空判断:null "" "     "
		// StringUtils.isBlank("");//true
		// StringUtils.isNotBlank("");//false
		// // 字符串替换
		// String info="ppppNUM1ppppNUM2ppppp";
		// info=StringUtils.replaceEach(info, new String[]{"NUM1","NUM2"}, new
		// String[]{"1","2"});
		// // 字符截取
		// info="<body>.......</body>";
		// StringUtils.substringBetween(info, "<body>", "</body>");

		// 获取屏幕的宽度

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		GlobalParams.WIN_WIDTH = metrics.widthPixels;

		relativeLayout = (KeyboardListenRelativeLayout) findViewById(R.id.keyboardRelativeLayout);  
          
        relativeLayout.setOnKeyboardStateChangedListener(new IOnKeyboardStateChangedListener() {  
              
            public void onKeyboardStateChanged(int state) {  
                switch (state) {  
                case KeyboardListenRelativeLayout.KEYBOARD_STATE_HIDE://软键盘隐藏
                	((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); 
                    break;  
                case KeyboardListenRelativeLayout.KEYBOARD_STATE_SHOW://软键盘显示   
                    break;  
                default:  
                    break;  
                }  
            }  
        });  
		
		init();

	}

	private void init() {
		TitleManager manager = TitleManager.getInstance();
		manager.init(this);
		manager.showWelcomeTitle();

		BottomManager.getInstrance().init(this);
		BottomManager.getInstrance().showHideAllBottom();

		middle = (RelativeLayout) findViewById(R.id.nb_middle);
		HallMiddleManager.getInstance().setMiddle(middle);

		// 建立观察者和被观察者之间的关系（标题和底部导航添加到观察者的容器里面）
		HallMiddleManager.getInstance().addObserver(TitleManager.getInstance());
		HallMiddleManager.getInstance().addObserver(BottomManager.getInstrance());

		// loadLoginUI();
		loadSecondUI();
		// MiddleManager.getInstance().changeUI(FirstUI.class);
		// HallMiddleManager.getInstance().changeUI(Welcome.class);

		// 当第一个界面加载完2秒钟后，第二个界面显示
		// handler.sendEmptyMessageDelayed(10, 2000);
	}

	private View childview;

	protected void loadSecondUI() {
		WelcomeUI welcomeui = new WelcomeUI(this);

		View child = welcomeui.getChild();
		// 切换界面的核心方法二
		middle.addView(child);

		// 执行切换动画
		// child.startAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.ia_view_change));
		FadeUtil.fadeIn(child, 2000, 1000);
	}

	/**
	 * 切换界面
	 * 
	 * @param ui
	 */
	protected void changeUI(BaseUI ui) {
		// 切换界面的核心代码
		middle.removeAllViews();
		// FadeUtil.fadeOut(child1, 2000);
		View child = ui.getChild();
		middle.addView(child);
		child.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.ia_view_change));
		// FadeUtil.fadeIn(child, 2000, 1000);
	}
	
	/**
	 * 切换界面
	 */
	protected void changeUI() {
		// 1、切换界面时清理上一个显示内容

		// 切换界面的核心方法一
		// middle.removeAllViews();
		FadeUtil.fadeOut(childview, 2000);
		// middle.removeView(child1);
		loadSecondUI();
	}

	// 1、切换界面时清理上一个显示内容
	// 2、处理切换动画：简单动画——复杂动画（淡入淡出）
	// 3、切换界面通用处理——增加一个参数（明确切换的目标界面,通用）
	// 4、不使用Handler，任意点击按钮切换

	// a、用户返回键操作捕捉
	// b、响应返回键——切换到历史界面

	// LinkedList<String>——AndroidStack

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			boolean result = HallMiddleManager.getInstance().goBack();
			// 返回键操作失败
			if (!result) {
				// Toast.makeText(DebugActivity.this, "是否退出系统", 1).show();
				PromptManager.showExitSystem(this);
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
