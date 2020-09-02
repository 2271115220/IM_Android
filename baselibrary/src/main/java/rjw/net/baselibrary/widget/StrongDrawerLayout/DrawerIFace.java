package rjw.net.baselibrary.widget.StrongDrawerLayout;

import android.view.View;

/**
 * @author zhd: 好好写
 * @date 2020/4/24 13:40
 * @desc
 */
public interface DrawerIFace {
    View getMyChildAt(int index);

    void setMFitsSystemWindows(boolean fitSystemWindows);
}
