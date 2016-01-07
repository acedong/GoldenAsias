package com.goldenasia.lottery.base.net;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * JSON请求的Volley适配器，返回结果使用Gson格式化为指定对象。
 * <p/>
 * Notice: 大文件上传下载不适用该请求
 */
public final class GsonRequest extends Request<RestResponse> {
    private final static boolean DEBUG = true;

    private final RestRequestBase request;

    private long startAtTime;

    /**
     * 发起请求
     *
     * @param method        参考{@link Method#POST}、{@link Method#GET}等
     * @param request       请求参数封装对象
     */
    public GsonRequest(int method, RestRequestBase request) {
        super(method, convertUrl(method, request), request);
        this.request = request;

        setRetryPolicy(initRetryPolicy());

        if (DEBUG) {
            startAtTime = System.currentTimeMillis();
            String urlTag;
            if (method == Request.Method.GET) {
                urlTag = request.getApi();
            } else {
                urlTag = getUrl();
            }
            Formater2.outJsonObject("GsonRequest_REQUEST", "", urlTag, GsonHelper.toJson(request.getCommand()));
        }
    }

    /**
     * 由api拼上服务器host，若是GET模式，将参数拼到url
     */
    private static final String convertUrl(int method, RestRequestBase request) {
        if (method != Method.GET) {
            return Preferences.getUrl(request.getApi());
        }

        Map<String, String> params = GsonHelper.convert2Map(GsonHelper.convert2gson(request.getCommand()));
        if (!TextUtils.isEmpty(request.getToken())) {
            params.put("token", request.getToken());
        }
        String baseUrl = Preferences.getUrl(request.getApi());
        StringBuilder encodedParams = new StringBuilder(baseUrl);
        if (!baseUrl.contains("?")) {
            encodedParams.append("?");
        }
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (null == entry.getValue()) {
                    continue;
                }
                encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                encodedParams.append('&');
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedParams.toString();
    }

    /*@Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return GsonHelper.convert2Map(GsonHelper.convert2gson(request.getCommand()));
    }*/

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return GsonHelper.toJson(request.getCommand()).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return super.getBody();
    }

    @Override
    protected void deliverResponse(RestResponse response) {
        request.onResponse(response);
    }

    /**
     * 网络请求结果解析
     *
     * @param response Response from the network
     * @return
     */
    @Override
    protected Response<RestResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));

            if (DEBUG) {
                String urlTag;
                if (getMethod() == Request.Method.GET) {
                    urlTag = request.getApi();
                } else {
                    urlTag = getUrl();
                }
                String name = String.format("call use %dms for %s",
                        System.currentTimeMillis() - startAtTime, urlTag);
                Formater2.outJsonObject("GsonRequest_RESPONSE", "", name, json);
            }

            RestResponse restResponse = GsonHelper.newGson().fromJson(json, request.getTypeOfResponse());
            return Response.success(restResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.d("", e.toString());
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            Log.d("", e.toString());
            return Response.error(new ParseError(e));
        }
    }

    /**
     * 超时设置 （wifi下和3G下设置不同超时时间）
     */
    public RetryPolicy initRetryPolicy() {
        int timeout;

        if (VolleyTrigger.getNetHelper().isWifiConnected(VolleyTrigger.getContext())) {
            timeout = Config.SO_TIMEOUT_WIFI;
        } else {
            timeout = Config.SO_TIMEOUT_3G;
        }

        RetryPolicy retryPolicy = new DefaultRetryPolicy(timeout, 0, 0);
        return retryPolicy;
    }
}