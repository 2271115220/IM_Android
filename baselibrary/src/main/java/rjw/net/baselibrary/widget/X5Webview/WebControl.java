package rjw.net.baselibrary.widget.X5Webview;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * @author sty
 * @date 2019/06/17 9:52
 * @desc
 */
public class WebControl {

    private Context context;
    X5Webview mWebView;
    public WebControl(Context context, X5Webview webView) {
        this.context=context;
        this.mWebView = webView;
    }

    @JavascriptInterface
    public void finish(){
        ((Activity)context).finish();
    }

    @JavascriptInterface
    public void toastMessage(String message) {
        Log.i("toastMessage" , "传递过来的值是： "+message);
    }
    @JavascriptInterface
    public String getMessage(String s){
        return s+"world !";
    }

}
