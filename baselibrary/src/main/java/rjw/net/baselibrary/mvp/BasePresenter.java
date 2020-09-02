package rjw.net.baselibrary.mvp;

import com.r.http.cn.RHttp;

import java.util.ArrayList;
import java.util.List;

import rjw.net.baselibrary.iface.BaseIView;

public class BasePresenter<V extends BaseIView> {

    public V mView;
    private List<String> mTagList;

    public void injectView(V view) {
        this.mView = view;
    }

    /**
     * 取消这个页面的请求
     */
    public void cancalNet() {
        if (mTagList != null) {
            for (int i = 0; i < mTagList.size(); i++) {
                RHttp.cancelAll();
            }
        }
    }

    /**
     * 取消某个网络请求
     *
     * @param tag
     */
    public void cancalOneNet(String tag) {
        RHttp.cancel(tag);
    }

    /**
     * 给每一个网络请求添加tag，方便关闭
     *
     * @param netTag
     */
    protected void addNetTag(String netTag) {
        if (mTagList == null) {
            mTagList = new ArrayList<>();
        }
        mTagList.add(netTag);
    }

    /**
     * 销毁链接
     */
    public void destoryView() {
        this.mView = null;
    }
}
