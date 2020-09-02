package rjw.net.baselibrary.iface;

import android.content.Context;
import android.os.Bundle;

public interface BaseIView {

    /**
     * 绑定layout
     *
     * @return
     */
    int getLayoutId();

    /**
     * 初始化view
     */
    void initView();

    /**
     * 初始化类
     */
    void initData();
    /**
     * 注册监听
     */
    void setListener();

    /**
     * 请求数据
     */
    void getData();

    /**
     * 显示加载中动画
     *
     * @param msg
     */
    void showLoadDialog(String msg);

    /**
     * 关闭加载动画
     */
    void hideLoadDialog();

    void mStartActivity(Class<?> cls);

    void mStartActivity(Class<?> cls, Bundle bundle);
}
