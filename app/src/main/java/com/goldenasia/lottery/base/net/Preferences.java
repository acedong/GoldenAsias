package com.goldenasia.lottery.base.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 本地数据缓存.
 */
public class Preferences {

    private static final String SANDBOX = "shared_prefs";

    public static final String PREF_KEY_SERVER_BASE = "pref_key_url_base";
    public static final String PREF_KEY_TOKEN = "pref_key_token";
    public static final String PREF_KEY_CONTENT_SERVER = "pref_key_content_server";
    public static final String PREF_KEY_ACCESS_POINTS = "pref_key_access_points";

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences pref = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE);
        return pref.getInt(key, defValue);
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static long getLong(Context context, String key, long defValue) {
        SharedPreferences pref = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE);
        return pref.getLong(key, defValue);
    }

    public static void saveLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static float getFloat(Context context, String key, float defValue) {
        SharedPreferences pref = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE);
        return pref.getFloat(key, defValue);
    }

    public static void saveFloat(Context context, String key, float value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences pref = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE);
        return pref.getString(key, defValue);
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void offLine(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_KEY_TOKEN, "");
        editor.putString(PREF_KEY_CONTENT_SERVER, "");
        editor.putString(PREF_KEY_ACCESS_POINTS, "");
        editor.commit();
    }

    /*public static void logonSuccess(Context context, LogonCommandResponse response) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_KEY_TOKEN, response.getLoginToken());
        editor.putString(PREF_KEY_CONTENT_SERVER, response.getContentServer());

        List<String> stringList = response.getAccessPoints();
        String[] strings = stringList.toArray(new String[stringList.size()]);
        editor.putString(PREF_KEY_ACCESS_POINTS, TextUtils.join("‚‗‚", strings));
        editor.commit();
    }*/

    public static String getToken(Context context) {
        return getString(context, PREF_KEY_TOKEN, "");
    }

    public static String getContentServer(Context context) {
        return getString(context, PREF_KEY_CONTENT_SERVER, "");
    }

    public static List<String> getAccessPoints(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE);
        return new ArrayList<>(Arrays.asList(TextUtils.split(pref.getString(PREF_KEY_ACCESS_POINTS, ""), "‚‗‚")));
    }

    public static void initialize(Context context, String serverBase) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SANDBOX, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_KEY_SERVER_BASE, serverBase);
        editor.commit();
    }

    public static String getUrl(String interfaceName) {
        if (interfaceName.startsWith("http://") || interfaceName.startsWith("https://")) {
            return interfaceName;
        }
        String serverBase = getString(VolleyTrigger.getContext(), PREF_KEY_SERVER_BASE, "");
        if (TextUtils.isEmpty(interfaceName)) {
            return serverBase;
        }
        StringBuffer sb = new StringBuffer(serverBase);
        if (!serverBase.endsWith("/")) {
            sb.append("/");
        }
        if (interfaceName.startsWith("/"))
            sb.append(interfaceName.substring(1));
        else
            sb.append(interfaceName);
        return sb.toString();
    }
}
