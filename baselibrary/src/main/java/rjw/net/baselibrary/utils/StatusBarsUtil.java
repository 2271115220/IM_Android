package rjw.net.baselibrary.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

/**
 * @author zhd: 好好写
 * @date 2020/5/14 17:11
 * @desc
 */
public class StatusBarsUtil {

    /**
     * 设置屏幕状态
     */
    public static void setScreenState(AppCompatActivity mActivity) {
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制横屏
        mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        mActivity.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏（AppCompatActivity）
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideBottomUIMenu(AppCompatActivity mActivity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = mActivity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = mActivity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void transparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * Android 6.0 以上设置状态栏颜色和显示等
     */
    public static void setStatusBar(AppCompatActivity mActivity, @ColorInt int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mActivity.getWindow().setStatusBarColor(color);
            Log.d("setcolor", "setStatusBar: ");
            // 如果亮色，设置状态栏文字为黑色
            if (isLightColor(color)) {
                mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                Log.d("liang", "setStatusBar: ");
            } else {
                mActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                Log.d("an", "setStatusBar: ");
            }
        }

    }

    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private static boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return
     */
    protected @ColorInt
    int getStatusBarColor() {
        return Color.WHITE;
    }

}
