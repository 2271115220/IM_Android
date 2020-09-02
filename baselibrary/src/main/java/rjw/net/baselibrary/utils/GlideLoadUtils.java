package rjw.net.baselibrary.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;

import rjw.net.baselibrary.R;

/**
 * Created by Administrator on 2018/4/27.
 */

public class GlideLoadUtils {
    public static GlideLoadUtils mGlideLoadUtils;
    private String TAG = "ImageLoader";

    /**
     * 借助内部类 实现线程安全的单例模式
     * 属于懒汉式单例，因为Java机制规定，内部类SingletonHolder只有在getInstance()
     * 方法第一次调用的时候才会被加载（实现了lazy），而且其加载过程是线程安全的。
     * 内部类加载的时候实例化一次instance。
     */
    public GlideLoadUtils() {
    }

    public static GlideLoadUtils getInstance() {
        if (mGlideLoadUtils == null) {
            mGlideLoadUtils = new GlideLoadUtils();
        }
        return mGlideLoadUtils;
    }

    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     *
     * @param context
     * @param url           加载图片的url地址  String
     * @param imageView     加载图片的ImageView 控件
     * @param defaultImage 图片展示错误的本地图片 id
     */
    public void glideLoad(Context context, String url, ImageView imageView, int defaultImage) {

        if (context != null) {
            Glide.with(context).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).error(defaultImage).placeholder(defaultImage).crossFade().into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }

    public void glideLoad(Context context, int imgRes, ImageView imageView, int defaultImage) {

        if (context != null) {
            Glide.with(context).load(imgRes).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().error(defaultImage).into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,context is null");
        }
    }
    /**
     * Glide 加载 简单判空封装 防止异步加载数据时调用Glide 抛出异常
     *
     * @param context
     * @param url       加载图片的url地址  String
     * @param imageView 加载图片的ImageView 控件
     */
    public void glideLoad(Context context, String url, ImageView imageView) {
        glideLoad(context, url, imageView, -1);
    }

    public void glideLoad(Context context, Bitmap photo, ImageView imageView) {


        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        photo.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] bytes=baos.toByteArray();
        Glide.with(context).load(bytes).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade
                ().into(imageView);
    }

    public void glideLoad(Context context, int res, ImageView imageView) {
        glideLoad(context, res, imageView, -1);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void glideLoad(Activity activity, String url, ImageView imageView, int default_image) {
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).error(default_image).placeholder(default_image).crossFade
                    ().into(imageView);
        } else {
            Log.i(TAG, "Picture loading failed,activity is Destroyed");
        }
    }

}
