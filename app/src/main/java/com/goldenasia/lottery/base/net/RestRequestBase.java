package com.goldenasia.lottery.base.net;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.ConnectException;

public class RestRequestBase implements Response.ErrorListener, Response.Listener<RestResponse>{
    private static final String TAG = RestRequestBase.class.getSimpleName();

    public enum RestState {
        IDLE, RUNNING, DONE, QUIT
    }

    protected RestState restState = RestState.IDLE;

    private int method = Request.Method.POST;

    /** 一般用于返回后的处理代码进switch操作 */
    private int id;

    /** 需要请求的接口名称 */
    private String api;

    private GsonRequest request;

    /** 请求返回正常时的结果 */
    private RestResponse restResponse;

    private Type typeOfResponse;

    /** 调用者处理的网络请求回调 */
    private RestCallback restCallback;

    /** 请求是否出现异常 */
    private boolean error;

    /** 错误值 */
    private int errCode;

    /** 错误描述 */
    private String errDesc;

    private final Context context;
    private final Object command;
    private final String token;

    public RestRequestBase(Context context, final Object command) {
        this.context = context;
        this.command = command;
        this.token = Preferences.getToken(context);
    }

    public RestRequestBase(Context context) {
        this.context = context;
        this.command = new Object();
        this.token = Preferences.getToken(context);
    }

    protected void notifyStateChanged(RestState state) {
        if (restState == state) {
            return;
        }
        restState = state;
        if (restCallback != null) {
            restCallback.onRestStateChanged(this, restState);
        }
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public <T> GsonRequest call(RestResponse<T> reference) {
        notifyStateChanged(RestState.RUNNING);
        typeOfResponse = new TypeToken<RestResponse<T>>(){}.getType();
        request = new GsonRequest(method, this);
        return request;
    }

    public <T> GsonRequest call(final Class<T> clz) {
        notifyStateChanged(RestState.RUNNING);
        typeOfResponse = new TypeToken<RestResponse<T>>(){}.getType();
        request = new GsonRequest(method, this);
        return request;
    }

    /** 实现{@link Response.Listener}，不允许子类修改，由{@link GsonRequest}回调 */
    @Override
    public final void onResponse(RestResponse response) {
        if (null == response) {
            notifyStateChanged(RestState.DONE);
            return;
        }

        restResponse = response;
        if ((restResponse.getErrorCode() != 0
                && restResponse.getErrorCode() != 200)) {
            errCode = restResponse.getErrorCode();
            errDesc = restResponse.getErrorDes();
            handleError();
            notifyStateChanged(RestState.DONE);
            return;
        }

        onBackgroundResult();

        if (restState != RestState.QUIT) {
            notifyStateChanged(RestState.DONE);
        }

        handleComplete();
    }

    /** 实现{@link Response.ErrorListener}， 不允许子类修改，由{@link GsonRequest}回调*/
    @Override
    public final void onErrorResponse(VolleyError error) {
        Throwable cause = error.getCause();
        if (cause == null) {
            errCode = -1;
            errDesc =  error.toString();
        } else if (cause instanceof ConnectException) {
            errCode = -3;
            errDesc = "网络连接错误";
        } else if (cause instanceof JsonSyntaxException) {
            errCode = -100;
            errDesc = "结果解析错误";
        } else if (cause instanceof TimeoutError) {
            errCode = -3;
            errDesc = "网络请求超时错误";
        } else {
            errCode = -1;
            errDesc = cause.getClass().getSimpleName();
        }

        Log.w(TAG, "onErrorResponse: " + error.getMessage());
        handleError();
        notifyStateChanged(RestState.QUIT);
    }

    /**
     * 若还在运行就cancel掉
     */
    public GsonRequest cancel() {
        if (restState == RestState.QUIT || restState == RestState.DONE) {
            return request;
        }
        request.cancel();
        notifyStateChanged(RestState.QUIT);
        return request;
    }

    /**
     * 运行在后台线程，子类可以进行一些结果的处理
     */
    protected void onBackgroundResult() {
    }

    private void handleComplete() {
        if (restCallback != null) {
            if (restCallback.onRestComplete(this, getRestResponse())) {
                //已经处理完成，不再下发
                return;
            }
        }

        if (restState == RestState.QUIT) {
            return;
        }

        onComplete();
    }

    private void handleError() {
        if (restState == RestState.QUIT) {
            return;
        }

        if (offline()) {
            try {
                VolleyTrigger.getsOfflineCallback().offlinewhileRequest(getContext(), (RestRequestBase) RestRequestBase.this.clone());
            } catch (CloneNotSupportedException e) {
                Log.e(TAG, e.toString());
            }

            return;
        }

        if (restState == RestState.QUIT) {
            return;
        }

        boolean hasHandled = false;
        if (restCallback != null) {
            hasHandled = restCallback.onRestError(this, errCode, errDesc);
            if (hasHandled) {
                //错误已经处理完成，不再下发
                return;
            }
        }

        if (restState == RestState.QUIT) {
            return;
        }

        hasHandled = onError();

        if (restState == RestState.QUIT) {
            return;
        }

        if (hasHandled) {
            //错误已经处理完成，不再下发
            return;
        }

        if (restState == RestState.QUIT) {
            return;
        }

        handleErrorDefault();
    }

    /**
     * 错误的默认处理
     */
    private void handleErrorDefault() {
        Toast.makeText(context, "出错了：[ " + errCode + ", " + errDesc + " ]",
                Toast.LENGTH_SHORT).show();
    }

    /** 子类覆盖此方法实现错误的拦截处理 */
    public boolean onError() {
        return false;
    }

    /** 子类覆盖此方法实现默认结果处理 */
    public void onComplete() {
    }

    // 重连并重试 errCode 401
    private boolean offline() {
        if (errCode != 401 || !VolleyTrigger.isSupportReconnect()) {
            return false;
        }

        Log.w(TAG, "offline...");
        restState = RestState.QUIT;
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public RestResponse getRestResponse() {
        return restResponse;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    public Context getContext() {
        return context;
    }

    public void setRestCallback(RestCallback restCallback) {
        this.restCallback = restCallback;
    }

    public Type getTypeOfResponse() {
        return typeOfResponse;
    }

    public Object getCommand() {
        return command;
    }

    public String getToken() {
        return token;
    }
}
