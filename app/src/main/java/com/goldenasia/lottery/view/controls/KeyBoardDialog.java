package com.goldenasia.lottery.view.controls;

import com.goldenasia.lottery.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 鎻愮ずDialog
 * 
 * @author LanYan
 *
 */
public class KeyBoardDialog extends Dialog {
	Activity activity;
	private View view;
	private boolean isOutSideTouch = true;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public boolean isOutSideTouch() {
		return isOutSideTouch;
	}

	public void setOutSideTouch(boolean isOutSideTouch) {
		this.isOutSideTouch = isOutSideTouch;
	}

	public KeyBoardDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public KeyBoardDialog(Context context) {
		this(context, 0);
		// TODO Auto-generated constructor stub
	}

	public KeyBoardDialog(Activity activity, View view) {
		super(activity, R.style.MyDialog);
		this.activity = activity;
		this.view = view;
	}

	public KeyBoardDialog(Activity activity, View view, int theme) {
		super(activity, theme);
		this.activity = activity;
		this.view = view;
	}

	public KeyBoardDialog(Activity activity, View view, int theme, boolean isOutSide) {
		super(activity, theme);
		this.activity = activity;
		this.view = view;
		this.isOutSideTouch = isOutSide;
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(view);
		setCanceledOnTouchOutside(isOutSideTouch);

		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int screenWidth = dm.widthPixels;
		WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
		layoutParams.width = screenWidth;
		Window window = this.getWindow();  
		window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置  
		window.setWindowAnimations(R.style.mykeyBoardDialogAnimation);  //添加动画  
		window.setAttributes(layoutParams);
	}

}
