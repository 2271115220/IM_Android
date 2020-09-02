package rjw.net.baselibrary.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rjw.net.baselibrary.iface.BaseIView;
import rjw.net.baselibrary.iface.OnFragmentAnimListener;
import rjw.net.baselibrary.widget.LoadingDialog;

public abstract class BaseFragment extends Fragment implements BaseIView, Animation.AnimationListener {

    //fragment加载动画监听
    private static OnFragmentAnimListener mOnFragmentAnimListener;

    private boolean isFirst = true;
    private boolean isPrepared;
    //加载框
    private Dialog mLoadingDialog;
    public View mView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        hideBottomUIMenu();
    }

    @Override
    public void showLoadDialog(String msg) {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.getInstance(getActivity());
        }
        mLoadingDialog.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //视图准备完毕
        isPrepared = true;
        init();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isLazyLoad() && getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    protected boolean isLazyLoad() {
        return false;
    }

    public void init() {
        initView();
        initData();
        setListener();
        getData();
    }

    /**
     * 目标activity
     *
     * @param cls
     */
    @Override
    public void mStartActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    /**
     * @param cls    目标activity
     * @param bundle 参数
     */
    @Override
    public void mStartActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void hideLoadDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        resetState();
    }

    private void lazyLoad() {
        if (isFirst && isPrepared) {
            init();
            isFirst = false;
            return;
        }
    }

    private void resetState() {
        isFirst = true;
        isPrepared = false;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (nextAnim == 0) {
            return super.onCreateAnimation(transit, enter, nextAnim);
        } else {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
            animation.setAnimationListener(this);
            return animation;
        }
    }

    public static void setOnFragmentAnimListener(OnFragmentAnimListener onFragmentAnimListener) {
        mOnFragmentAnimListener = onFragmentAnimListener;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (mOnFragmentAnimListener != null) {
            mOnFragmentAnimListener.onAnimStart();
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (mOnFragmentAnimListener != null) {
            mOnFragmentAnimListener.onAnimEnd();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

}
