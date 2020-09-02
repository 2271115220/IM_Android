package com.r.http.cn.callback;

import android.util.Log;

import com.r.http.cn.exception.ExceptionEngine;
import com.r.http.cn.helper.ParseHelper;

/**
 * Http请求回调
 *
 * @author ZhongDaFeng
 */
public abstract class HttpCallback<T> extends BaseCallback<T> implements ParseHelper<T> {

    /**
     * 是否回调成功函数,网络和解析都成功，修改此参数
     */
    private boolean callSuccess = true;

    /**
     * 数据回调成功
     *
     * @param value
     */
    @Override
    public void netSuccess(T value) {
        //解析数据去了
        T result = parse(value.toString());
        if (callSuccess && isBusinessOk()) {
            onSuccess(result);
        }
    }

    /**
     * 解析数据
     *
     * @param data
     * @return
     */
    @Override
    public T parse(String data) {
        T t = null;
        try {
            t = onConvert(data);
            callSuccess = true;
        } catch (Exception e) {
            callSuccess = false;
            e.printStackTrace();
            onBusinessError(ExceptionEngine.ANALYTIC_CLIENT_DATA_ERROR, "加载中");
        }
        return t;
    }

    @Override
    public void onNetError(int code, String desc) {
        Log.d(netTag, "netError: ");
    }

    @Override
    public void inCancel() {
        onCancel();
    }

    /**
     * 数据转换/解析数据
     *
     * @param data
     * @return
     */
    public abstract T onConvert(String data) throws Exception;

    /**
     * 成功回调
     *
     * @param value
     */
    public abstract void onSuccess(T value);

    /**
     * 业务失败回调
     *
     * @param code
     * @param desc
     */
    public abstract void onBusinessError(int code, String desc);

    /**
     * 取消回调
     */
    public abstract void onCancel();

    /**
     * 业务逻辑是否成功
     *
     * @return
     */
    public abstract boolean isBusinessOk();

}
