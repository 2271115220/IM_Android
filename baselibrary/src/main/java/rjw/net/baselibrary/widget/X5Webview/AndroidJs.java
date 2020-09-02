package rjw.net.baselibrary.widget.X5Webview;

import android.content.Context;
import android.webkit.JavascriptInterface;

import rjw.net.baselibrary.widget.X5Webview.OnClickBackListener;

public class AndroidJs {
    private OnClickBackListener onClickBackListener;
    private Context mContext;

    public AndroidJs(Context context, OnClickBackListener onClickBackListener) {
        this.mContext = context;
        this.onClickBackListener = onClickBackListener;
    }

    /**
     * h5点击了返回键
     */
    @JavascriptInterface
    public void goBack() {
        if (onClickBackListener != null) {
            onClickBackListener.onClickBack() ;
        }
    }

    @JavascriptInterface
    public void showToast() {

    }

}