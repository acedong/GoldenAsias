package com.goldenasia.lottery.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import com.goldenasia.lottery.GlobalParams;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.bean.InfoList;
import com.goldenasia.lottery.view.UserLogin;
import com.goldenasia.lottery.view.manager.HallMiddleManager;

/**
 * 提示信息的管理
 * @author Ace
 *
 */

public class PromptManager {
	private static CustomProgress progress;
	public static void showProgressDialog(Context context,String msg) {
		progress = new CustomProgress(context);
		progress.setMsg(msg);
		progress.show();
	}

	public static void closeProgressDialog() {
		
		if (progress != null && progress.isShowing()) {
			progress.dismiss();
		}
	}

	/**
	 * 当判断当前手机没有网络时使用
	 * 
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage("当前无网络");
		builder.setTitle("提示");
		builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if(android.os.Build.VERSION.SDK_INT > 10 ){
				    //3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
					context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
				} else {
					context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				}
				android.os.Process.killProcess(android.os.Process.myPid());
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
		
	}

	/**
	 * 退出系统
	 * @param context
	 */
	public static void showExitSystem(Context context) {
		
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage("是否退出应用");
		builder.setTitle("提示");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				GlobalParams.clear();
            	GlobalParams.isLogin=false;
            	InfoList.getInstance().clear();
				HallMiddleManager.getInstance().clear();
        		android.os.Process.killProcess(android.os.Process.myPid());
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();

	}
	
	/**
	 * 重新登录
	 * @param context
	 */
	public static void showRelogin(Context context,String errMessage,final String errCode)
	{
		if(GlobalParams.isLogin){
			GlobalParams.isLogin=false;
			String errMes="";
			String sfdsf=errMessage;
			System.out.println(sfdsf);
			if(errMessage==null){
				errMes="由于长时间没有操作，请重新登录!";
			}else{
				errMes=errMessage;
			}
			
			CustomDialog.Builder builder = new CustomDialog.Builder(context);
			builder.setMessage(errMes);
			builder.setTitle("提示");
			builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if(errCode.equals("255")){
						GlobalParams.clear();
		            	GlobalParams.isLogin=false;
		            	InfoList.getInstance().clear();
						HallMiddleManager.getInstance().clear();
		        		HallMiddleManager.getInstance().changeUI(UserLogin.class);
					}
					dialog.dismiss();
				}
			});

			builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});

			builder.create().show();
		}
	}
	
	/**
	 * 显示无标题提示框
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showNotTitleDialog(Context context, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder//给builder set各种属性值
		    .setMessage(msg)
		    .setPositiveButton("确定", new OnClickListener() {//确定按钮
		 
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		        	
		        }
		    }).show();
	}



	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void showToast(Context context, int msgResId) {
		Toast.makeText(context, msgResId, Toast.LENGTH_LONG).show();
	}

	// 当测试阶段时true
	private static final boolean isShow = true;

	/**
	 * 测试用 在正式投入市场：删
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showToastTest(Context context, String msg) {
		if (isShow) {
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}

}
