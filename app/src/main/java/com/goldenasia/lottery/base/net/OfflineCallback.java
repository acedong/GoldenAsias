package com.goldenasia.lottery.base.net;

import android.content.Context;

/**
 * 请求时发现掉线.
 */
public interface OfflineCallback {

    /**
     * 重连并重新请求
     */
    void offlinewhileRequest(Context context, final RestRequestBase requestBase);
}
