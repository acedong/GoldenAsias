package com.goldenasia.lottery.base.net;

/**
 * Created by Alashi on 2015/12/24.
 */
public class RestResponse<T> {
    private T response;
    private int errorCode;
    private String errorDes;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDes() {
        return errorDes;
    }

    public void setErrorDes(String errorDes) {
        this.errorDes = errorDes;
    }
}
