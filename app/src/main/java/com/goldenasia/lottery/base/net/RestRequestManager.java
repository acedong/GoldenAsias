package com.goldenasia.lottery.base.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.goldenasia.lottery.app.GoldenAsiaApp;

import java.io.File;

public class RestRequestManager {
    private static final String TAG = RestRequestManager.class.getSimpleName();
    private static RequestQueue sRequestQueue = newRequestQueue();

    private RestRequestManager(){
    }

    private static Cache openCache() {
        File cacheRoot = new File(GoldenAsiaApp.getInstance().getExternalCacheDir().getAbsolutePath()
                + File.separator + "network");
        return new DiskBasedCache(cacheRoot, Config.DISK_BASED_CACHE_SIZE);
    }

    private static RequestQueue newRequestQueue() {
        RequestQueue requestQueue = new RequestQueue(openCache(), new BasicNetwork(new HurlStack()));
        requestQueue.start();
        return requestQueue;
    }

    public static void addRequest(Request request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        sRequestQueue.add(request);
    }

    public static GsonRequest executeCommand(Context context, Object command, RestCallback callback, int id, Object tag) {
        RequestConfig requestConfig = command.getClass().getAnnotation(RequestConfig.class);
        if (requestConfig == null) {
            Log.e(TAG, "executeCommand: can't find config of " + command.getClass() , new Throwable());
            return null;
        }

        RestRequestBase requestBase = new RestRequestBase(context, command);
        requestBase.setMethod(requestConfig.method());
        requestBase.setApi(requestConfig.api());
        requestBase.setRestCallback(callback);
        requestBase.setId(id);

        GsonRequest gsonRequest = requestBase.call(requestConfig.response());
        RestRequestManager.addRequest(gsonRequest, tag);
        return gsonRequest;
    }

    public static void cancelRequest(Request request, Object tag) {
        // todo
    }

    public static void cancelAll(Object tag) {
        sRequestQueue.cancelAll(tag);
    }


}
