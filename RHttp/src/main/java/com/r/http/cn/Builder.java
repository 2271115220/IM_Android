package com.r.http.cn;

import android.text.TextUtils;

import com.r.http.cn.retrofit.Method;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.File;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Builder
 * 构造Request所需参数，按需设置
 */
public class Builder {
    /*请求方式*/
    Method method;
    /*请求参数*/
    Map<String, Object> parameter;
    /*header*/
    Map<String, Object> header;
    /*LifecycleProvider*/
    LifecycleProvider lifecycle;
    /*ActivityEvent*/
    ActivityEvent activityEvent;
    /*FragmentEvent*/
    FragmentEvent fragmentEvent;
    /*标识请求的TAG*/
    String tag;
    /*文件map*/
    Map<String, File> fileMap;
    /*基础URL*/
    String baseUrl;
    /*apiUrl*/
    String apiUrl;
    /*String参数*/
    String bodyString;
    /*是否强制JSON格式*/
    boolean isJson;

    public Builder() {
    }

    /*GET*/
    public Builder get() {
        this.method = Method.GET;
        return this;
    }

    /*POST*/
    public Builder post() {
        this.method = Method.POST;
        return this;
    }

    /*DELETE*/
    public Builder delete() {
        this.method = Method.DELETE;
        return this;
    }

    /*PUT*/
    public Builder put() {
        this.method = Method.PUT;
        return this;
    }

    /*基础URL*/
    public Builder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /*API URL*/
    public Builder apiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    /* 增加 Parameter 不断叠加参数 包括基础参数 */
    public Builder addParameter(Map<String, Object> parameter) {
        if (this.parameter == null) {
            this.parameter = new TreeMap<>();
        }
        this.parameter.putAll(parameter);
        return this;
    }

    /*设置 Parameter 会覆盖 Parameter 包括基础参数*/
    public Builder setParameter(Map<String, Object> parameter) {
        this.parameter = parameter;
        return this;
    }

    /* 设置String 类型参数  覆盖之前设置  isJson:是否强制JSON格式    bodyString设置后Parameter则无效 */
    public Builder setBodyString(String bodyString, boolean isJson) {
        this.isJson = isJson;
        this.bodyString = bodyString;
        return this;
    }

    /* 增加 Header 不断叠加 Header 包括基础 Header */
    public Builder addHeader(Map<String, Object> header) {
        if (this.header == null) {
            this.header = new TreeMap<>();
        }
        this.header.putAll(header);
        return this;
    }

    /*设置 Header 会覆盖 Header 包括基础参数*/
    public Builder setHeader(Map<String, Object> header) {
        this.header = header;
        return this;
    }

    /*LifecycleProvider*/
    public Builder lifecycle(LifecycleProvider lifecycle) {
        this.lifecycle = lifecycle;
        return this;
    }

    /*ActivityEvent*/
    public Builder activityEvent(ActivityEvent activityEvent) {
        this.activityEvent = activityEvent;
        return this;
    }

    /*FragmentEvent*/
    public Builder fragmentEvent(FragmentEvent fragmentEvent) {
        this.fragmentEvent = fragmentEvent;
        return this;
    }

    /*tag*/
    public Builder tag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            this.tag = this.apiUrl;
        } else {
            this.tag = tag;
        }

        return this;
    }

    /*文件集合*/
    public Builder file(Map<String, File> file) {
        this.fileMap = file;
        return this;
    }

    /*一个Key对应多个文件*/
    public Builder file(String key, List<File> fileList) {
        if (fileMap == null) {
            fileMap = new IdentityHashMap();
        }
        if (fileList != null && fileList.size() > 0) {
            for (File file : fileList) {
                fileMap.put(new String(key), file);
            }
        }
        return this;
    }

    public RHttp build() {
        return new RHttp(this);
    }
}
