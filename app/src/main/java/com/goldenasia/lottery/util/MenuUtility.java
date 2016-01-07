package com.goldenasia.lottery.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.view.custom.PulldownMenuView;

/**
 * PulldownMenuView基本操作类
 * @Description: PulldownMenuView基本操作类
 * @File: PulldownMenuUtility.java
 * @Package com.newbee.lottery.util
 * @Author Ace
 */
@SuppressLint("ResourceAsColor")
public class MenuUtility {
	private Context context = null;
	// 文字内容
	private String[] texts = null;
	private int width = 0;
	private int height = 0;
	private View anchorView = null;
	
	/**
	 * 带Context的构造器
	 * @param context
	 */
	public MenuUtility(Context context) {
		// TODO Auto-generated constructor stub
		this(context,null,0,null);
	}
	
	/**
	 * 带多参的构造器
	 * @param context
	 * @param imageRes
	 * @param texts
	 */
	public MenuUtility(Context context,String[] texts,int height,View anchorView){
		this.context = context;
		this.texts = texts;
		this.height = height;
		this.anchorView = anchorView;
	}
	
	/**
	 * 设置文字内容
	 * @param texts
	 */
	public void setTexts(String[] texts){
		this.texts = texts;
	}
	
	/**
	 * 设置高度
	 * @param height
	 */
	public void setHeight(int height){
		this.height = height;
	}
	
	/**
	 * 设置宽度
	 * @param width
	 */
	
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 设置显示的位置
	 * @param anchor
	 */
	public void setAnchorView(View anchor){
		anchorView = anchor;
	}
	
	/**
	 * 获取PulldownMenuView对象
	 * 以下拉的形式展现出来菜单
	 * @return
	 */
	public PulldownMenuView getPulldownMenuView(String currentItem){
		PulldownMenuView menu = new PulldownMenuView(context);
		menu.setMenuText(texts);
		menu.setWidth(width);
		menu.setHeight(height);
		menu.setAnchorView(anchorView);
		menu.setMenuTextColor(R.color.white);
		menu.setCurrentItem(currentItem);
		menu.setBackground(R.drawable.navigation_bg);
		
		return menu;
	}
	
	/**
	 * 获取PulldownMenuView对象
	 * 以向上弹出的方式展现出来菜单
	 * @return
	 */
	public PulldownMenuView getPopupMenuView(){
		PulldownMenuView menu = new PulldownMenuView(context);
		menu.setMenuText(texts);
		// menu.setLocation(Gravity.BOTTOM | Gravity.CENTER);
		menu.setAnimStyle(R.style.pulldown_in_out);
		menu.setBackground(R.drawable.navigation_bg);
		
		return menu;
	}
}

