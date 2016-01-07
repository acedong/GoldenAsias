package com.goldenasia.lottery.view.controls;

import com.goldenasia.lottery.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 下拉导航选择菜单项
 * @Description: 下拉导航选择菜单项
 * @File: PopupMenuItem.java
 * @Package com.newbee.lottery.view.controls
 * @Author Ace
 */
public class PulldownMenuItem {
	// 文字内容
	private String menuText = null;
	// 文字颜色
	private int menuTextColor = 0;
	// 文字大小
	private float menuTextSize = 0;
	
	private ShapeDrawable mDrawables;
	// Context
	private Context mContext = null;
	// 菜单项LinearLayout
	private LinearLayout linearLayout = null;
	// 默认的情况下文字显示在图片的下方  
	private PulldownMenuAlign menuAlign = PulldownMenuAlign.TEXT_BOTTOM;
	
	/**
	 * 默认的构造器
	 */
	public PulldownMenuItem() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 带Context的构造器
	 * @param context
	 */
	public PulldownMenuItem(Context context){
		this(context, null, 0, 0, PulldownMenuAlign.TEXT_BOTTOM);
	}
	
	/**
	 * 带多参的构造器
	 * @param context
	 * @param imgRes
	 * @param text
	 * @param textColor
	 * @param textSize
	 * @param align
	 */
	public PulldownMenuItem(Context context, String text,
							int textColor, float textSize, 
							PulldownMenuAlign align){
		menuText = text;
		menuTextColor = textColor;
		menuTextSize = textSize;
		menuAlign = align;
		mContext = context;
	}
	
	/**
	 * 构造器
	 * @param context
	 * @param item
	 */
	public PulldownMenuItem(Context context, PulldownMenuItem item){
		this(context, null, item.getMenuTextColor(),  item.getMenuTextSize(), item.getMenuAlign());
	}
	
	/**
	 * 初始化菜单项
	 */
	@SuppressWarnings("deprecation")
	private void initLayout(){
		Context context = mContext;
		linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		linearLayout.setGravity(Gravity.CENTER);
		linearLayout.setPadding(6, 3, 6, 15);
		
		TextView textView = getTextView(context);
		// 设置文字Padding
		textView.setBackgroundDrawable(getmDrawables());
		textView.setTextColor(Color.WHITE);
		textView.setPadding(3, 5, 3, 5);
		
		if (null != textView){
			switch (menuAlign){
			case TEXT_BOTTOM: // 文字显示在图片的下方
				linearLayout.setOrientation(LinearLayout.VERTICAL);
				linearLayout.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.MATCH_PARENT));
				break;
			case TEXT_TOP:// 文字显示在图片的上方
				linearLayout.setOrientation(LinearLayout.VERTICAL);
				linearLayout.addView(textView, new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.MATCH_PARENT));
				break;
			case TEXT_LEFT:// 文字显示在图片的左方
				linearLayout.setOrientation(LinearLayout.HORIZONTAL);
				linearLayout.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
				break;
			case TEXT_RIGHT:// 文字显示在图片的右方
				linearLayout.setOrientation(LinearLayout.HORIZONTAL);
				linearLayout.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
				break;
			}
		}else{
			if (null != textView){
				linearLayout.addView(textView, new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.MATCH_PARENT));
			} 
		}
	}
	
	
	/**
	 * 设置文字大小
	 * @param size
	 */
	public void setMenuTextSize(float size){
		menuTextSize = size;
	}
	
	/**
	 * 设置文字颜色
	 * @param color
	 */
	public void setMenuTextColor(int color){
		menuTextColor = color;
	}
	
	/**
	 * 设置文字内容
	 * @param text
	 */
	public void setMenuText(String text){
		menuText = text;
	}
	
	/**
	 * 设置文字排列方式
	 * @param align
	 */
	public void setMenuAlign(PulldownMenuAlign align){
		menuAlign = align;
	}
	
	/**
	 * 获取文字内容
	 * @return
	 */
	public String getMenuText(){
		return menuText;
	}
	
	/**
	 * 获取文字的颜色
	 * @return
	 */
	public int getMenuTextColor(){
		return menuTextColor;
	}
	
	/**
	 * 获取文字的大小
	 * @return
	 */
	public float getMenuTextSize(){
		return menuTextSize;
	}
	
	public ShapeDrawable getmDrawables() {
		return mDrawables;
	}

	public void setmDrawables(ShapeDrawable mDrawables) {
		this.mDrawables = mDrawables;
	}

	/**
	 * 获取新建的TextView
	 * @param context
	 * @return
	 */
	private TextView getTextView(Context context){
		if (TextUtils.isEmpty(menuText)){
			return null;
		}
		
		TextView txtView = new TextView(context);
		
		if (menuTextColor != 0){
			txtView.setTextColor(menuTextColor);
		}
		
		if (menuTextSize != 0){
			txtView.setTextSize(menuTextSize);
		}
		
		txtView.setText(menuText);
		txtView.setGravity(Gravity.CENTER);
		
		return txtView;
	}
	
	/**
	 * 获取文字排列方式
	 * @return
	 */
	public PulldownMenuAlign getMenuAlign(){
		return menuAlign;
	}
	
	/**
	 * 初始化菜单项并获取菜单项的内容
	 * @return
	 */
	public View getView(){
		initLayout();
		return linearLayout;
	}
	
}
