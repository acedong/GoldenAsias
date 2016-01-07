package com.goldenasia.lottery.net;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.goldenasia.lottery.GlobalParams;

/**
 * 
 * 检查手机网络
 * 
 * @author Ace
 *
 */

public class NetUtil {
	
	private static String LOG_TAG = "NetWorkHelper";  
	   
    public static Uri uri = Uri.parse("content://telephony/carriers");  
   
    /** 
     * 判断是否有网络连接 
     */ 
    public static boolean checkNet(Context context) {  
        ConnectivityManager connectivity = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
   
        if (connectivity == null) {  
            Log.w(LOG_TAG, "couldn't get connectivity manager");  
        } else {  
            NetworkInfo[] info = connectivity.getAllNetworkInfo();  
            if (info != null) {  
                for (int i = 0; i < info.length; i++) {  
                    if (info[i].isAvailable()) {  
                        Log.d(LOG_TAG, "network is available");  
                        return true;  
                    }  
                }  
            }  
        }  
        Log.d(LOG_TAG, "network is not available");  
        return false;  
    }  
       
    public static boolean checkNetState(Context context){  
        boolean netstate = false;  
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        if(connectivity != null)  
        {  
            NetworkInfo[] info = connectivity.getAllNetworkInfo();  
            if (info != null) {  
                for (int i = 0; i < info.length; i++)  
                {  
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)   
                    {  
                        netstate = true;  
                        break;  
                    }  
                }  
            }  
        }  
        return netstate;  
    }  
   
    /** 
     * 判断网络是否为漫游 
     */ 
    public static boolean isNetworkRoaming(Context context) {  
        ConnectivityManager connectivity = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (connectivity == null) {  
            Log.w(LOG_TAG, "couldn't get connectivity manager");  
        } else {  
            NetworkInfo info = connectivity.getActiveNetworkInfo();  
            if (info != null 
                    && info.getType() == ConnectivityManager.TYPE_MOBILE) {  
                TelephonyManager tm = (TelephonyManager) context  
                        .getSystemService(Context.TELEPHONY_SERVICE);  
                if (tm != null && tm.isNetworkRoaming()) {  
                    Log.d(LOG_TAG, "network is roaming");  
                    return true;  
                } else {  
                    Log.d(LOG_TAG, "network is not roaming");  
                }  
            } else {  
                Log.d(LOG_TAG, "not using mobile network");  
            }  
        }  
        return false;  
    }  
   
    /** 
     * 判断MOBILE网络是否可用 
     *  
     * @param context 
     * @return 
     * @throws Exception 
     */ 
    public static boolean isMobileDataEnable(Context context) throws Exception {  
        ConnectivityManager connectivityManager = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        boolean isMobileDataEnable = false;  
   
        isMobileDataEnable = connectivityManager.getNetworkInfo(  
                ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
   
        return isMobileDataEnable;  
    }  
   
       
    /** 
     * 判断wifi 是否可用 
     * @param context 
     * @return 
     * @throws Exception 
     */ 
    public static boolean isWifiDataEnable(Context context) throws Exception {  
        ConnectivityManager connectivityManager = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        boolean isWifiDataEnable = false;  
        isWifiDataEnable = connectivityManager.getNetworkInfo(  
                ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
        return isWifiDataEnable;  
    }  
   
	/**
	 * 检查用户的网络:是否有网络
	 */
	/*public static boolean checkNet(Context context) {
		// 判断：WIFI链接
		boolean isWIFI = isWIFIConnection(context);
		// 判断：Mobile链接
		boolean isMOBILE = isMOBILEConnection(context);

		// 如果Mobile在链接，判断是哪个APN被选中了
		if (!isWIFI && !isMOBILE) {
			return false;
		}
		return true;
	}*/

	/**
	 * APN被选中,的代理信息是否有内容，如果有wap方式
	 * 
	 * @param context
	 */
    /*private static void readAPN(Context context) {
		Uri PREFERRED_APN_URI = Uri
				.parse("content://telephony/carriers/preferapn");// 4.0模拟器屏蔽掉该权限

		// 操作联系人类似
		ContentResolver resolver = context.getContentResolver();
		// 判断是哪个APN被选中了
		Cursor cursor = resolver.query(PREFERRED_APN_URI, null, null, null,null);

		if (cursor != null && cursor.moveToFirst()) {
			GlobalParams.PROXY = cursor.getString(cursor.getColumnIndex("proxy"));
			GlobalParams.PORT = cursor.getInt(cursor.getColumnIndex("port"));
		}

	}*/

	/**
	 * 判断：Mobile链接
	 * 
	 * @param context
	 * @return
	 */
    /*private static boolean isMOBILEConnection(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}*/

	/**
	 * 判断：WIFI链接
	 * 
	 * @param context
	 * @return
	 */
	/*private static boolean isWIFIConnection(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}*/
}
