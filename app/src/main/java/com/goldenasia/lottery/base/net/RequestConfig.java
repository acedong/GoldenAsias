package com.goldenasia.lottery.base.net;

import com.android.volley.Request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Alashi on 2015/12/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequestConfig {
    /** 网络请求类型，{@link Request.Method} */
    int method() default Request.Method.POST;
    /** 服务器API，不包含basePath */
    String api();
    /** 正常访问返回数据的bean类 */
    Class response();
}
