package com.goldenasia.lottery.base.net;

public interface RestCallback<T> {
    /**
     * 若返回false，做默认处理；若返回true，表示已经处理
     */
    public boolean onRestComplete(RestRequestBase request, RestResponse<T> response);

    /**
     * 若返回false，做默认处理；若返回true，表示已经处理
     */
    public boolean onRestError(RestRequestBase request, int errCode, String errDesc);

    public void onRestStateChanged(RestRequestBase request, RestRequestBase.RestState state);
}
