package com.goldenasia.lottery.base.net;

import android.content.Context;

/**
 * 初始化.
 */
public class VolleyTrigger {

    private static Context sContext;
    private static NetStateHelper sNetHelper;
    private static OfflineCallback sOfflineCallback;
    private static boolean supportReconnect = true;

    public static void initialize(Context context, String serverBase, NetStateHelper netHelper) {
        supportReconnect = true;
        sContext = context;
        sNetHelper = netHelper;
        Preferences.initialize(context, serverBase);
    }

    public static Context getContext() {
        return sContext;
    }

    public static NetStateHelper getNetHelper() {
        return sNetHelper;
    }

    public static OfflineCallback getsOfflineCallback() {
        return sOfflineCallback;
    }

    public static void setsOfflineCallback(OfflineCallback sOfflineCallback) {
        VolleyTrigger.sOfflineCallback = sOfflineCallback;
    }

    public static boolean isSupportReconnect() {
        return supportReconnect;
    }

    public static void setSupportReconnect(boolean supportReconnect) {
        VolleyTrigger.supportReconnect = supportReconnect;
    }
}
