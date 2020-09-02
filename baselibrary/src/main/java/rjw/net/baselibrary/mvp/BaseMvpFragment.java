package rjw.net.baselibrary.mvp;

import rjw.net.baselibrary.base.BaseFragment;

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {
    protected P mPresenter;

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 获取prensenter对象的实例
     *
     * @return
     */
    protected abstract P getPresenter();

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.cancalNet();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁
        if (mPresenter != null) {
            mPresenter.destoryView();
            mPresenter = null;
        }
    }
}
