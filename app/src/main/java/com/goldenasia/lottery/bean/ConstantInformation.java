package com.goldenasia.lottery.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 菜单项文字类
 * @Description: 菜单项文字类
 * @File: ConstantCategoryMenu.java
 * @Package com.newbee.lottery.view.controls
 * @Author Ace
 */
public class ConstantInformation {
	
	/**
	 * 玩法菜单项
	 */
	public static Map<String, String[]> playmuneMap=new HashMap<String, String[]>(); 
	
	/**
	 * 完整菜单玩法信息
	 */
	public static Map<String, List<PlayMenu>> playMap=new HashMap<String, List<PlayMenu>>();
	
	/**
	 * 任务奖期
	 */
	public static Map<String, String[]> taskIssueMap=new HashMap<String, String[]>();
	
	/**
	 * 记录已选玩法
	 */
	public static Map<String, PlayMenu> recordPlayMenu=new HashMap<String, PlayMenu>();
}
