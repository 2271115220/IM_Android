package rjw.net.baselibrary.widget.X5Webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rjw.net.baselibrary.utils.BitmapUtil;
import rjw.net.baselibrary.widget.ActionSheetDialog.ActionSheetDialog;


import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.os.Environment.DIRECTORY_DCIM;

/**
 * @author sty
 * @date 2019/06/17 9:52
 * @desc
 */
public class X5Webview extends WebView implements OnClickBackListener {

    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;
    private int mResultCode = RESULT_CANCELED;
    private Uri imageUri;

    private OnWebLoadListener mOnWebLoadlistener;
    private static final String TAG = "x5webview";
    int progressColor = 0xFFFF4081;
    ProgressView mProgressview;
    TextView title;
    Context context;


    public X5Webview(Context arg0, AttributeSet arg1){
        super(arg0, arg1);
        this.context = arg0;
    }

    /**
     * 初始化相关配置
     */
    public void init() {
        initWebViewSettings();
        this.setWebViewClient(client);
        this.setWebChromeClient(chromeClient);
        this.setDownloadListener(downloadListener);
        initProgressBar();
        this.getView().setClickable(true);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings(){
        if (getX5WebViewExtension() != null){
            getX5WebViewExtension().setHorizontalScrollBarEnabled(false);
            getX5WebViewExtension().setVerticalScrollBarEnabled(false);
        }
        addJavascriptInterface(new AndroidJs(context, this), "AndroidJs");
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            webSettings.setMixedContentMode(webSettings.getMixedContentMode());
        }
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccessFromFileURLs(true);

        webSettings.setAppCachePath(context.getDir("appcache", 0).getPath());
        webSettings.setDatabasePath(context.getDir("databases", 0).getPath());
        webSettings.setGeolocationDatabasePath(context.getDir("geolocation", 0).getPath());
        addJavascriptInterface(new  WebControl(context, this),"webCtrl");
        setWebChromeClient(chromeClient);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
    }

    private void initProgressBar(){
        mProgressview = new ProgressView(context);
        mProgressview.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 6));
        mProgressview.setDefaultColor(progressColor);
        addView(mProgressview);
    }

    public void setOnWebLoadlistener(OnWebLoadListener onWebLoadlistener){
        this.mOnWebLoadlistener=onWebLoadlistener;
    }
    private WebViewClient client = new WebViewClient(){

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            if (mOnWebLoadlistener!=null){
                mOnWebLoadlistener.onLoadStart();
            }
        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            String endCookie = cookieManager.getCookie(url);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                CookieSyncManager.getInstance().sync();
            }else {
                CookieManager.getInstance().flush();
            }
            super.onPageFinished(webView, url);
            if (mOnWebLoadlistener!=null){
                mOnWebLoadlistener.onLoadFinish();
            }
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
            webView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            webView.setVisibility(View.VISIBLE);
            if (mOnWebLoadlistener!=null){
                mOnWebLoadlistener.onLoadFinish();
            }
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            super.onReceivedSslError(webView, sslErrorHandler, sslError);
            if (sslError.getPrimaryError() == android.net.http.SslError.SSL_INVALID){
                sslErrorHandler.proceed();
            }else {
                sslErrorHandler.cancel();
            }
            if (mOnWebLoadlistener!=null){
                mOnWebLoadlistener.onLoadFinish();
            }
        }
    };

    private WebChromeClient chromeClient = new WebChromeClient(){

        // For Android  > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        }

        // For Android  >= 5.0
        public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         final WebChromeClient.FileChooserParams fileChooserParams) {
            uploadFiles = filePathCallback;

            new ActionSheetDialog(context)
                    .builder(uploadFile,uploadFiles)
                    .setCancelable(true)
                    .setCanceledOnTouchOutside(true)
                    .addSheetItem("上传照片",
                            ActionSheetDialog.SheetItemColor.Blue,
                            new  ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    take();
                                }
                            })
                    .addSheetItem("上传视频",
                            ActionSheetDialog.SheetItemColor.Blue,
                            new  ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    Toast.makeText(context, "调用视频", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                    //限制时长
                                    intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                                    //开启摄像机
//
                                    Activity mActivity= (Activity) context;
                                    mActivity.startActivityForResult(intent, 101);
                                }
                            })
//                    .addSheetItem("调用相册",
//                            ActionSheetDialog.SheetItemColor.Blue,
//                            new ActionSheetDialog.OnSheetItemClickListener() {
//                                @Override
//                                public void onClick(int which) {
//                                    Toast.makeText(X5TestActivity.this, "调用相册", Toast.LENGTH_SHORT).show();
//                                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                                    i.addCategory(Intent.CATEGORY_OPENABLE);
//                                    i.setType("image/*");
//                                    startActivityForResult(Intent.createChooser(i, "选择相册"), 102);
//                                }
//                    })
                    .show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
            return super.onJsConfirm(webView, s, s1, jsResult);
        }

        View myVideoView;
        View myNormalView;
        IX5WebChromeClient.CustomViewCallback callback;

        /**
         * 全屏播放配置
         */
        @Override
        public void onShowCustomView(View view,
                                     IX5WebChromeClient.CustomViewCallback customViewCallback) {
//            if (!isShowCustomView){
//                super.onShowCustomView(view,customViewCallback);
//                return;
//            }
            FrameLayout normalView = null;
            ViewGroup viewGroup = (ViewGroup) normalView.getParent();
            viewGroup.removeView(normalView);
            viewGroup.addView(view);
            myVideoView = view;
            myNormalView = normalView;
            callback = customViewCallback;
        }

        @Override
        public void onHideCustomView() {
            if (callback != null) {
                callback.onCustomViewHidden();
                callback = null;
            }
            if (myVideoView != null) {
                ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                viewGroup.removeView(myVideoView);
                viewGroup.addView(myNormalView);
            }
        }

        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            mProgressview.setProgress(i);
        }

        @Override
        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
            return super.onJsAlert(webView, s, s1, jsResult);
        }
    };

    public boolean flag = true;
    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != 100
                || uploadFiles == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if(results!=null){
            uploadFiles.onReceiveValue(results);
            uploadFiles = null;
        }else{
            results = new Uri[]{imageUri};
            uploadFiles.onReceiveValue(results);
            uploadFiles = null;
        }

        return;
    }

    private void take(){
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (! imageStorageDir.exists()){
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager =context.getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i,"请选择相册或者拍照");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        Activity mActivity = (Activity) context;
        mActivity.startActivityForResult(chooserIntent,  100);
    }

    Uri result;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100://相片 拍照片
                    if (null == uploadFile && null == uploadFiles) return;
                    Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                    if (uploadFiles != null) {
                        onActivityResultAboveL(requestCode, resultCode, data);
                    }
                    else  if (uploadFile != null) {
                        Log.e("result",result+"");
                        if(result==null){
                            uploadFile.onReceiveValue(imageUri);
                            uploadFile = null;
                            Log.e("imageUri",imageUri+"");
                        }else {
                            uploadFile.onReceiveValue(result);
                            uploadFile = null;
                        }


                    }
                    flag =  true;
                    break;
                case 101://相机 拍摄视频
                    if (null == uploadFile && null == uploadFiles) {
                        Log.d(TAG, "onActivityResult null");
                        return;
                    }
                    result = data == null || resultCode != RESULT_OK ? null : data.getData();
                    Log.d(TAG, "onActivityResult path=" + result.getPath());
                    if (uploadFiles != null) {
                        if (resultCode == RESULT_OK) {
                            Log.d(TAG, "onActivityResult 1");
                            uploadFiles.onReceiveValue(new Uri[]{result});
                        } else {
                            Log.d(TAG, "onActivityResult 2");
                            uploadFiles.onReceiveValue(new Uri[]{});
                            uploadFiles = null;
                        }
                    } else if (uploadFile != null) {
                        if (resultCode == RESULT_OK) {
                            uploadFile.onReceiveValue(result);
                            uploadFile = null;
                        } else {
                            Log.d(TAG, "onActivityResult 4");
                            uploadFile.onReceiveValue(Uri.EMPTY);
                            uploadFile = null;
                        }
                    }
                    break;
                case 102:
                    if (null != uploadFile) {
                        result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (null != uploadFiles) {
                        result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }
    }

    DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onDownloadStart(String arg0, String arg1, String arg2,
                                    String arg3, long arg4) {
            new AlertDialog.Builder(context)
                    .setTitle("allow to download？")
                    .setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Toast.makeText(
                                            context,
                                            "fake message: i'll download...",
                                            Toast.LENGTH_LONG).show();
                                }
                            })
                    .setNegativeButton("no",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(
                                            context,
                                            "fake message: refuse download...",
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                    .setOnCancelListener(
                            new DialogInterface.OnCancelListener() {

                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(
                                            context,
                                            "fake message: refuse download...",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }).show();
        }
    };

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean ret = super.drawChild(canvas, child, drawingTime);
        canvas.save();
        Paint paint = new Paint();
        paint.setColor(0x7fff0000);
        paint.setTextSize(24.f);
        paint.setAntiAlias(true);
        canvas.restore();
        return ret;
    }

    public X5Webview(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 取消选择时需要回调onReceiveValue，否则网页会挂住，不会再响应点击事件
        if (mResultCode == RESULT_CANCELED) {
            try {
                if (uploadFiles != null) {
                    uploadFiles.onReceiveValue(null);
                }
                if (uploadFile != null) {
                    uploadFile.onReceiveValue(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void setListener(){
        setOnLongClickListener(v -> {
            final WebView.HitTestResult hitTestResult = getHitTestResult();
            // 如果是图片类型或者是带有图片链接的类型
            if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                    hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                // 弹出保存图片的对话框
                String url = getUrl();
                if (!url.contains("zuoye")) {
                    return true;
                }
                new AlertDialog.Builder(context)
                        .setItems(new String[]{"保存作业", "分享作业"}, (dialog, which) -> {
                            String pic = hitTestResult.getExtra();//获取图片
                            switch (which) {
                                case 0:
                                    //保存图片到相册
                                    new Thread(() -> saveImage(pic)).start();
                                    break;
                                case 1:

                                    break;
                            }
                        })
                        .show();
                return true;
            }
            return true;//保持长按可以复制文字
        });
    }

    public void saveImage(String data) {
        try {
            Bitmap bitmap = webData2bitmap(data);
            if (bitmap != null) {
                save2Album(bitmap, new SimpleDateFormat("SXS_yyyyMMddHHmmss", Locale.getDefault()).format(new Date()) + ".jpg");
                Toast.makeText(context,"保存成功 ",Toast.LENGTH_SHORT).show();
            } else {
//                    runOnUiThread(() -> Toast.makeText(NewRjwWebActivity.this, "保存失败", Toast.LENGTH_SHORT).show());
            }
        } catch (Exception e) {
//                runOnUiThread(() -> Toast.makeText(NewRjwWebActivity.this, "保存失败", Toast.LENGTH_SHORT).show());
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongThread")
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public Bitmap webData2bitmap(String data) {
        if (data.contains("http")) {
            return BitmapUtil.returnBitMap(data);
        } else {
            byte[] imageBytes = Base64.decode(data.split(",")[1], Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }

    }

    @SuppressLint("WrongThread")
    private void save2Album(Bitmap bitmap, String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM), fileName);
        FileOutputStream fos = null;
        Activity mActivity = (Activity) context;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            mActivity.runOnUiThread(() -> {
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                Toast.makeText(context,"已保存到图库",Toast.LENGTH_SHORT).show();
//                    Util.makeText(NewRjwWebActivity.this, "保存成功", Toast.LENGTH_SHORT);
            });
        } catch (Exception e) {
//                runOnUiThread(() -> Util.makeText(NewRjwWebActivity.this, "保存失败", Toast.LENGTH_SHORT));
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void onClickBack() {
        if (canGoBack()) {
            goBack();
        } else {
            if (mOnClickBackListener != null) {
                mOnClickBackListener.onClickBack();
            }
        }
    }


    private OnClickBackListener mOnClickBackListener;

    public void setOnClickBackListener(OnClickBackListener mOnClickBackListener) {
        this.mOnClickBackListener = mOnClickBackListener;
    }
}
