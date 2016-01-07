package com.goldenasia.lottery;


public class GlobalParams {
	
	private static GlobalParams instance = new GlobalParams();
	
	private GlobalParams() {
	}

	public static GlobalParams getInstance() {
		return instance;
	}
	
	public static String aipurl="";
	/**
	 * 代理的ip
	 */
	public static String PROXY="";
	/**
	 * 代理的端口
	 */
	public static int PORT=0;
	/**
	 * 屏幕的宽度
	 */
	public static int WIN_WIDTH=0;
	/**
	 * 记录用户的登录状态
	 */
	public static boolean isLogin=false;
	
	/**
	 * 记住密码
	 */
	public static boolean isRemPassword=false;
	/**
	 * 用户的余额
	 */
	public static double MONEY=0.00;
	
	/**
	 * 用户密码
	 */
	public static String PASSWORD=""; 
	
	/**
	 * 用户类型 总代
	 */
	public static String PARENTID="";
	
	/**
	 * 用户类型
	 */
	public static String USERTYPE="";
	
	/**
	 * 用户是否冰结
	 */
	public static String ISFROZEN= "";
	
	/**
	 * 记录资金密码状态
	 */
	public static boolean ISSECURITYPWD=false;
	
	/**
	 * 用户名
	 */
	public static String USERNAME="";
	
	/**
	 * 昵称
	 */
	public static String NICKNAME="";
	
	/**
	 * 银行信息绑定
	 */
	public static boolean BINDINGINFOBANK=false;
	
	/**
	 * 返点
	 */
	public static float POINT = (float) 0.00;
	
	/**
	 * 模式 元角分
	 */
	public static int mode=1;
	
	/**
	 * 清除数据
	 */
	public static void clear()
	{
		GlobalParams.isLogin=false;
		GlobalParams.isRemPassword=false;
		GlobalParams.MONEY=0.00;
		GlobalParams.PASSWORD="";
		GlobalParams.ISSECURITYPWD=false;
		GlobalParams.USERNAME="";
		GlobalParams.USERTYPE="";
		GlobalParams.ISFROZEN="";
		GlobalParams.NICKNAME="";
		GlobalParams.BINDINGINFOBANK=false;
		GlobalParams.POINT = (float) 0.00;
		GlobalParams.mode=1;
	}
	
}
