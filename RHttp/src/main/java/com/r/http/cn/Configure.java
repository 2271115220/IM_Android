package com.r.http.cn;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Configure配置
 */
public class Configure {
    
    /*请求基础路径*/
    String baseUrl;
    /*超时时长*/
    long timeout;
    /*时间单位*/
    TimeUnit timeUnit;
    /*全局上下文*/
    Context context;
    /*全局Handler*/
    Handler handler;
    /*请求参数*/
    Map<String, Object> parameter;
    /*header*/
    Map<String, Object> baseHeader;
    /*是否显示Log*/
    boolean showLog;

    //
    public static Configure get() {
        return Configure.Holder.holder;
    }

    private static class Holder {
        private static Configure holder = new Configure();
    }

    private Configure() {
        timeout = 60;//默认60秒
        timeUnit = TimeUnit.SECONDS;//默认秒
        showLog = true;//默认打印LOG
    }

    /*请求基础路径*/
    public Configure baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    /*基础参数*/
    public Configure baseParameter(Map<String, Object> parameter) {
        this.parameter = parameter;
        return this;
    }

    public Map<String, Object> getBaseParameter() {
        return parameter;
    }

    /*基础Header*/
    public Configure baseHeader(Map<String, Object> header) {
        this.baseHeader = header;
        return this;
    }

    public Map<String, Object> getBaseHeader() {
        return baseHeader;
    }

    /*超时时长*/
    public Configure timeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    /*是否显示LOG*/
    public Configure showLog(boolean showLog) {
        this.showLog = showLog;
        return this;
    }

    public boolean isShowLog() {
        return showLog;
    }

    /*时间单位*/
    public Configure timeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /*Handler*/
    public Handler getHandler() {
        return handler;
    }

    /*Context*/
    public Context getContext() {
        return context;
    }

    /*初始化全局上下文*/
    public Configure init(Application app) {
        this.context = app.getApplicationContext();
        this.handler = new Handler(Looper.getMainLooper());
        return this;
    }

}