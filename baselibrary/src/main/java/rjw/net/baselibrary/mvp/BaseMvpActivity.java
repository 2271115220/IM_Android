package rjw.net.baselibrary.mvp;

import android.os.Bundle;

import rjw.net.baselibrary.base.BaseActivity;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = getPresenter();
        mPresenter.injectView(this);
        super.onCreate(savedInstanceState);
    }

    /**
     * 获取prensenter对象的实例
     *
     * @return
     */
    protected abstract P getPresenter();


    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.cancalNet();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //销毁
        if (mPresenter != null) {
            mPresenter.destoryView();
            mPresenter = null;
        }
    }
}
