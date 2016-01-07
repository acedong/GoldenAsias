package com.goldenasia.lottery.net;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class APNManager {
	private static String TAG = "APNManager";  
    private static final Uri APN_TABLE_URI = Uri  
            .parse("content://telephony/carriers");// 所有的APN配配置信息位置  
    private static final Uri PREFERRED_APN_URI = Uri  
            .parse("content://telephony/carriers/preferapn");// 当前的APN  
    private static String[] projection = { "_id", "apn", "type", "current",  
            "proxy", "port" };  
    private static String APN_NET_ID = null;  
  
    //切换成NETAPN  
    public static boolean ChangeNetApn(final Context context) {  
        final String wapId = getWapApnId(context);  
        String apnId = getCurApnId(context);  
        // 若当前apn是wap，则切换至net  
        if (wapId.equals(apnId)) {  
            APN_NET_ID = getNetApnId(context);  
            setApn(context, APN_NET_ID);  
            // 切换apn需要一定时间，先让等待几秒，与机子性能有关  
            try {  
                Thread.sleep(3000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            Log.d("xml", "setApn");  
            return true;  
        }  
        return true;  
    }  
  
    //获取当前APN  
    public static String getCurApnId(Context context) {  
        ContentResolver resoler = context.getContentResolver();  
        // String[] projection = new String[] { "_id" };  
        Cursor cur = resoler.query(PREFERRED_APN_URI, projection, null, null,  
                null);  
        String apnId = null;  
        if (cur != null && cur.moveToFirst()) {  
            apnId = cur.getString(cur.getColumnIndex("_id"));  
        }  
        Log.i("xml","getCurApnId:"+apnId);  
        return apnId;  
    }  
  
    public static APN getCurApnInfo(final Context context) {  
        ContentResolver resoler = context.getContentResolver();  
        // String[] projection = new String[] { "_id" };  
        Cursor cur = resoler.query(PREFERRED_APN_URI, projection, null, null,  
                null);  
        APN apn = new APN();  
        if (cur != null && cur.moveToFirst()) {  
            apn.id = cur.getString(cur.getColumnIndex("_id"));  
            apn.apn = cur.getString(cur.getColumnIndex("apn"));  
            apn.type = cur.getString(cur.getColumnIndex("type"));  
  
        }  
        return apn;  
    }  
  
      
    public static void setApn(Context context, String id) {  
        ContentResolver resolver = context.getContentResolver();  
        ContentValues values = new ContentValues();  
        values.put("apn_id", id);  
        resolver.update(PREFERRED_APN_URI, values, null, null);  
        Log.d("xml", "setApn");  
    }  
  
    //获取WAP APN  
    public static String getWapApnId(Context context) {  
        ContentResolver contentResolver = context.getContentResolver();  
        // 查询cmwapAPN  
        Cursor cur = contentResolver.query(APN_TABLE_URI, projection,  
                "apn = \'cmwap\' and current = 1", null, null);  
        // wap APN 端口不为空  
        if (cur != null && cur.moveToFirst()) {  
            do {  
                String id = cur.getString(cur.getColumnIndex("_id"));  
                String proxy = cur.getString(cur.getColumnIndex("proxy"));  
                if (!TextUtils.isEmpty(proxy)) {  
                    Log.i("xml","getWapApnId"+id);  
                    return id;  
                }  
            } while (cur.moveToNext());  
        }  
        return null;  
    }  
  
      
    public static String getNetApnId(Context context) {  
        ContentResolver contentResolver = context.getContentResolver();  
        Cursor cur = contentResolver.query(APN_TABLE_URI, projection,  
                "apn = \'cmnet\' and current = 1", null, null);  
        if (cur != null && cur.moveToFirst()) {  
            return cur.getString(cur.getColumnIndex("_id"));  
        }  
        return null;  
    }  
  
    //获取所有APN  
       public static ArrayList<APN> getAPNList(final Context context) {  
  
        ContentResolver contentResolver = context.getContentResolver();  
        Cursor cr = contentResolver.query(APN_TABLE_URI, projection, null,  
                null, null);  
  
        ArrayList<APN> apnList = new ArrayList<APN>();  
  
        if (cr != null && cr.moveToFirst()) {  
            do{  
                Log.d(TAG,  
                        cr.getString(cr.getColumnIndex("_id")) + ";"  
                                + cr.getString(cr.getColumnIndex("apn")) + ";"  
                                + cr.getString(cr.getColumnIndex("type")) + ";"  
                                + cr.getString(cr.getColumnIndex("current"))+ ";"  
                                + cr.getString(cr.getColumnIndex("proxy")));  
                APN apn = new APN();  
                apn.id = cr.getString(cr.getColumnIndex("_id"));  
                apn.apn = cr.getString(cr.getColumnIndex("apn"));  
                apn.type = cr.getString(cr.getColumnIndex("type"));  
                apnList.add(apn);  
            }while(cr.moveToNext());  
             
            cr.close();  
        }  
        return apnList;  
    }  
  
    //获取可用的APN  
      public static ArrayList<APN> getAvailableAPNList(final Context context) {  
        // current不为空表示可以使用的APN  
        ContentResolver contentResolver = context.getContentResolver();  
        Cursor cr = contentResolver.query(APN_TABLE_URI, projection,  
                "current is not null" , null, null);  
        ArrayList<APN> apnList = new ArrayList<APN>();  
        if (cr != null && cr.moveToFirst()) {  
            do{  
                Log.d(TAG,  
                        cr.getString(cr.getColumnIndex("_id")) + ";"  
                                + cr.getString(cr.getColumnIndex("apn")) + ";"  
                                + cr.getString(cr.getColumnIndex("type")) + ";"  
                                + cr.getString(cr.getColumnIndex("current"))+ ";"  
                                + cr.getString(cr.getColumnIndex("proxy")));  
                APN apn = new APN();  
                apn.id = cr.getString(cr.getColumnIndex("_id"));  
                apn.apn = cr.getString(cr.getColumnIndex("apn"));  
                apn.type = cr.getString(cr.getColumnIndex("type"));  
                apnList.add(apn);  
            }while (cr.moveToNext());  
             
            cr.close();  
        }  
        return apnList;  
  
    }  
   //自定义APN包装类  
    static class APN {  
  
        String id;  
  
        String apn;  
  
        String type;  
  
        public String toString() {  
            return "id=" + id + ",apn=" + apn + ";type=" + type;  
        }  
    }  

}
