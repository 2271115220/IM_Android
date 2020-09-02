package com.r.http.cn;

import android.app.Application;
import android.text.TextUtils;

import com.r.http.cn.api.Api;
import com.r.http.cn.callback.HttpCallback;
import com.r.http.cn.callback.UploadCallback;
import com.r.http.cn.cancel.RequestManagerImpl;
import com.r.http.cn.load.upload.UploadRequestBody;
import com.r.http.cn.observer.HttpObservable;
import com.r.http.cn.retrofit.Method;
import com.r.http.cn.retrofit.RetrofitUtils;
import com.r.http.cn.utils.RequestUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Http请求类
 *
 * @author ZhongDaFeng
 */
public class RHttp {

    /*请求方式*/
    private Method method;
    /*请求参数*/
    private Map<String, Object> allParameter;
    /*header*/
    private Map<String, Object> allHeader;
    /*LifecycleProvider*/
    private LifecycleProvider lifecycle;
    /*ActivityEvent*/
    private ActivityEvent activityEvent;
    /*FragmentEvent*/
    private FragmentEvent fragmentEvent;
    /*HttpCallback*/
    private HttpCallback httpCallback;
    /*标识请求的TAG*/
    private String tag;
    /*文件map*/
    private Map<String, File> fileMap;
    /*上传文件回调*/
    private UploadCallback uploadCallback;
    /*基础URL*/
    private String baseUrl;
    /*apiUrl*/
    private String apiUrl;
    /*String参数*/
    String bodyString;
    /*是否强制JSON格式*/
    boolean isJson;

    public static void init(Application application, String baseUrl) {
        Configure.get()
                .baseUrl(baseUrl)//基础URL
                .baseHeader(buildHeader())            //全局Header
                .baseParameter(buildParameter())    //全局参数
                .timeout(10)                    //连接&读&写超时时长
                .timeUnit(TimeUnit.SECONDS)        //超时时长单位
                .showLog(true)                    //是否显示log
                .init(application);                    //初始化
    }

    /*构造函数*/
    public RHttp(Builder builder) {
        //将业务参数加入
        this.allParameter = builder.parameter;
        this.allHeader = builder.header;
        this.lifecycle = builder.lifecycle;
        this.activityEvent = builder.activityEvent;
        this.fragmentEvent = builder.fragmentEvent;
        this.tag =TextUtils.isEmpty(builder.tag)?builder.apiUrl:builder.tag;
        this.fileMap = builder.fileMap;
        this.baseUrl = builder.baseUrl;
        this.apiUrl = builder.apiUrl;
        this.isJson = builder.isJson;
        this.bodyString = builder.bodyString;
        this.method = builder.method;
    }

    /**
     * 构造默认header
     *
     * @return
     */
    private static Map<String, Object> buildHeader() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    /**
     * 构造默认参数
     *
     * @return
     */
    private static Map<String, Object> buildParameter() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    /*普通Http请求*/
    public void request(HttpCallback httpCallback) {
        this.httpCallback = httpCallback;
        if (httpCallback == null) {
            throw new NullPointerException("HttpObserver must not null!");
        } else {
            doRequest();
        }
    }

    /*上传文件请求*/
    public void upload(UploadCallback uploadCallback) {
        this.uploadCallback = uploadCallback;
        if (uploadCallback == null) {
            throw new NullPointerException("UploadCallback must not null!");
        } else {
            doUpload();
        }
    }

    /*取消网络请求*/
    public void cancel() {
        if (httpCallback != null) {
            httpCallback.cancel();
        }
        if (uploadCallback != null) {
            uploadCallback.cancel();
        }
    }

    /*请求是否已经取消*/
    public  boolean isCanceled() {
        boolean isCanceled = true;
        if (httpCallback != null) {
            isCanceled = httpCallback.isDisposed();
        }
        if (uploadCallback != null) {
            isCanceled = uploadCallback.isDisposed();
        }
        return isCanceled;
    }

    /**
     * 根据tag取消请求
     *
     * @param tag
     */
    public static void cancel(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        RequestManagerImpl.getInstance().cancel(tag);
    }

    /**
     * 取消全部请求
     */
    public static void cancelAll() {
        RequestManagerImpl.getInstance().cancelAll();
    }


    /*执行请求*/
    private void doRequest() {

        /*设置请求唯一标识*/
        httpCallback.setTag(TextUtils.isEmpty(tag) ? String.valueOf(System.currentTimeMillis()) : tag);

        /*header处理*/
        disposeHeader();

        /*Parameter处理*/
        disposeParameter();

        /*请求方式处理*/
        Observable apiObservable = disposeApiObservable();

        /* 被观察者 httpObservable */
        HttpObservable httpObservable = new HttpObservable.Builder(apiObservable)
                .httpObserver(httpCallback)
                .lifecycleProvider(lifecycle)
                .activityEvent(activityEvent)
                .fragmentEvent(fragmentEvent)
                .build();
        /* 观察者  httpObserver */
        /*设置监听*/
        httpObservable.observe().subscribe(httpCallback);

    }

    /*执行文件上传*/
    private void doUpload() {

        /*设置请求唯一标识*/
        uploadCallback.setTag(TextUtils.isEmpty(tag) ? String.valueOf(System.currentTimeMillis()) : tag);

        /*header处理*/
        disposeHeader();

        /*Parameter处理*/
        disposeParameter();

        /*处理文件集合*/
        List<MultipartBody.Part> fileList = new ArrayList<>();
        if (fileMap != null && fileMap.size() > 0) {
            int size = fileMap.size();
            int index = 1;
            File file;
            RequestBody requestBody;
            for (String key : fileMap.keySet()) {
                file = fileMap.get(key);
                requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), new UploadRequestBody(requestBody, file, index, size, uploadCallback));
                fileList.add(part);
                index++;
            }
        }

        /*请求处理*/
        Observable apiObservable = RetrofitUtils.get().getRetrofit(getBaseUrl()).create(Api.class).upload(disposeApiUrl(), allParameter, allHeader, fileList);

        /* 被观察者 httpObservable */
        HttpObservable httpObservable = new HttpObservable.Builder(apiObservable)
                .httpObserver(uploadCallback)
                .lifecycleProvider(lifecycle)
                .activityEvent(activityEvent)
                .fragmentEvent(fragmentEvent)
                .build();
        /* 观察者  uploadCallback */
        /*设置监听*/
        httpObservable.observe().subscribe(uploadCallback);

    }

    /*获取基础URL*/
    private String getBaseUrl() {
        //如果没有重新指定URL则是用默认配置
        return TextUtils.isEmpty(baseUrl) ? Configure.get().getBaseUrl() : baseUrl;
    }

    /*ApiUrl处理*/
    private String disposeApiUrl() {
        return TextUtils.isEmpty(apiUrl) ? "" : apiUrl;
    }

    /*处理Header*/
    private void disposeHeader() {

        /*header空处理*/
        if (allHeader == null) {
            allHeader = new TreeMap<>();
        }

        //添加基础 Header
        Map<String, Object> baseHeader = Configure.get().getBaseHeader();
        if (baseHeader != null && baseHeader.size() > 0) {
            allHeader.putAll(baseHeader);
        }


        if (!allHeader.isEmpty()) {
            //处理header中文或者换行符出错问题
            for (String key : allHeader.keySet()) {
                allHeader.put(key, RequestUtils.getHeaderValueEncoded(allHeader.get(key)));
            }
        }

    }

    /*处理 Parameter,将业务参数和基础参数拼接起来*/
    private void disposeParameter() {

        /*空处理*/
        if (allParameter == null) {
            allParameter = new TreeMap<>();
        }

        //添加基础 Parameter
        Map<String, Object> baseParameter = Configure.get().getBaseParameter();
        if (baseParameter != null && baseParameter.size() > 0) {
            //此时已经有了业务参数，现在将默认参数加入
            allParameter.putAll(baseParameter);
        }
    }

    /*处理ApiObservable*/
    private Observable disposeApiObservable() {

        Observable apiObservable = null;

        /*是否JSON格式提交参数*/
        boolean hasBodyString = !TextUtils.isEmpty(bodyString);
        RequestBody requestBody = null;
        if (hasBodyString) {
            String mediaType = isJson ? "application/json; charset=utf-8" : "text/plain;charset=utf-8";
            requestBody = RequestBody.create(okhttp3.MediaType.parse(mediaType), bodyString);
        }

        /*Api接口*/
        String baseUrl = getBaseUrl();
        Api apiService = RetrofitUtils.get().getRetrofit(baseUrl).create(Api.class);
        /*未指定默认POST*/
        if (method == null) {
            method = Method.POST;
        }
        switch (method) {
            case GET:
                apiObservable = apiService.get(disposeApiUrl(), allParameter, allHeader);
                break;
            case POST:
                if (hasBodyString)
                    apiObservable = apiService.post(disposeApiUrl(), requestBody, allHeader);
                else
                    apiObservable = apiService.post(disposeApiUrl(), allParameter, allHeader);
                break;
            case DELETE:
                apiObservable = apiService.delete(disposeApiUrl(), allParameter, allHeader);
                break;
            case PUT:
                apiObservable = apiService.put(disposeApiUrl(), allParameter, allHeader);
                break;
        }
        return apiObservable;
    }



}
