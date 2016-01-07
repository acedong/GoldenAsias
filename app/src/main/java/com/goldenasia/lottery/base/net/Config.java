package com.goldenasia.lottery.base.net;

/**
 * 网络参数配置.
 */
public interface Config {

    /**
     * 3G环境下网络请求超时时间
     */
    public static int SO_TIMEOUT_3G = 30 * 1000;

    /**
     * wifi环境下网络请求超时时间
     */
    public static int SO_TIMEOUT_WIFI = 30 * 1000;

    /**
     * TODO: 3G环境下文件上传超时时间
     */
    public static int SO_TIMEOUT_3G_UPLOAD = 30 * 1000;

    /**
     * TODO: wifi环境下文件上传超时时间
     */
    public static int SO_TIMEOUT_WIFI_UPLOAD = 15 * 1000;

    /**
     * 缓存大小
     */
    public static int DISK_BASED_CACHE_SIZE = 10 * 1024 * 1024;

}
