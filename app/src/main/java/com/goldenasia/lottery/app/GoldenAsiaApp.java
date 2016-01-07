package com.goldenasia.lottery.app;

import android.app.Application;

import com.goldenasia.lottery.base.CrashHandler;
import com.goldenasia.lottery.base.net.NetStateHelper;
import com.goldenasia.lottery.base.net.VolleyTrigger;
import com.goldenasia.lottery.base.thread.ThreadPool;

/**
 * Created by Alashi on 2015/12/22.
 */
public class GoldenAsiaApp extends Application{

    private static GoldenAsiaApp sApp;
    private ThreadPool mThreadPool;
    private NetStateHelper mNetStateHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        //运行时，出现Crash，将log写到sd卡；
        CrashHandler.getInstance().init(this);

        mThreadPool = new ThreadPool();
        mNetStateHelper = new NetStateHelper(this);
        mNetStateHelper.resume();

        initializeVolley();
    }

    private void initializeVolley() {
        //TODO:设置服务器url，实现offlineCallback
        //String baseUrl = "http://ssc.la/index.jsp?&a=login";
        String baseUrl = "http://xxx.com";
        VolleyTrigger.initialize(this, baseUrl, mNetStateHelper);
        //VolleyTrigger.setsOfflineCallback(offlineCallback);
    }

    public static GoldenAsiaApp getInstance() {
        return sApp;
    }

    public static ThreadPool getThreadPool() {
        return getInstance().mThreadPool;
    }

    public static NetStateHelper getNetStateHelper() {
        return getInstance().mNetStateHelper;
    }
}
