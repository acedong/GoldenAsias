package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by Alashi on 2015/12/29.
 */
@RequestConfig(api = "http://apis.wandoujia.com/five/v2/apps/categories/%E5%A4%A9%E6%B0%94/all",
        response = String.class, method = Request.Method.GET)
public class GetCommand {
    private String v;
    private int sdk;
    private String udid;
    private String channel;
    private boolean rippleSupported;
    private int vc;
    private int capacity;
    private int start;
    private int max;

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public int getSdk() {
        return sdk;
    }

    public void setSdk(int sdk) {
        this.sdk = sdk;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isRippleSupported() {
        return rippleSupported;
    }

    public void setRippleSupported(boolean rippleSupported) {
        this.rippleSupported = rippleSupported;
    }

    public int getVc() {
        return vc;
    }

    public void setVc(int vc) {
        this.vc = vc;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
