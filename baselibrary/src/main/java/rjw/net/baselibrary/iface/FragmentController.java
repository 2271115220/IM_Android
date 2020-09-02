package rjw.net.baselibrary.iface;

import androidx.fragment.app.Fragment;

import rjw.net.baselibrary.base.BaseFragment;

/**
 * @author zhd: 好好写
 * @date 2020/4/15 8:12
 * @desc
 */
public interface FragmentController {

    /**
     * 跳转到下一个fragment
     *
     * @param fragment 目标fragment
     */
    void startFragment(BaseFragment fragment);

    /**
     * @param fragment               目标fragment
     * @param isFirstSlidingFragment 是否是侧边栏滑动之后第一个出现的fragment
     */
    void startFragment(BaseFragment fragment, boolean isFirstSlidingFragment);

    void finishFragment(Fragment fragment);

    void finishFragment();


}
