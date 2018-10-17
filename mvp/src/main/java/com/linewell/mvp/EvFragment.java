package com.linewell.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.linewell.mvp.contract.EvContract;
import com.linewell.mvp.contract.EvViewHelper;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class EvFragment<V extends EvContract.V, P extends EvContract.P<V, DATA>, DATA> extends MvpLazyFragment<V, P>
        implements EvContract.V<P, DATA> {

    private EvViewHelper mViewHelper;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState, boolean preIsPrepared) {
        super.onCreateViewLazy(savedInstanceState, preIsPrepared);
        mViewHelper = new EvViewHelper(this);
        mViewHelper.init(this);
    }

    @Override
    public void onDestroyViewLazy() {
        if (mViewHelper != null) {
            mViewHelper.destroy();
            mViewHelper = null;
        }
        super.onDestroyViewLazy();
    }

    @Override
    public void showTip(CharSequence message) {
        if (mViewHelper != null) {
            mViewHelper.showTip(message);
        }
    }

    @Override
    public void showError() {
        if (mViewHelper != null) {
            mViewHelper.showError();
        }
    }

    @Override
    public void showProgress() {
        if (mViewHelper != null) {
            mViewHelper.showProgress();
        }
    }

    @Override
    public void showContent() {
        if (mViewHelper != null) {
            mViewHelper.showContent();
        }
    }

    @Override
    public void showEmpty() {
        if (mViewHelper != null) {
            mViewHelper.showEmpty();
        }
    }

    @NonNull
    @Override
    public Config getConfig() {
        return new Config.Builder()
                .setEasyViewRes(R.id.easy_view)
                .build();
    }

    @Override
    public void doRefresh() {
        if (mViewHelper != null) {
            mViewHelper.doRefresh();
        }
    }

    @Override
    public void onRefresh() {
        if (mViewHelper != null) {
            getPresenter().refresh(mViewHelper);
        }
    }

    public EvViewHelper getViewHelper() {
        return mViewHelper;
    }

    @Override
    protected int getContentLayoutRes() {
        final int contentLayoutRes = super.getContentLayoutRes();
        if (contentLayoutRes > 0) {
            return contentLayoutRes;
        }

        return R.layout.common_easy_view;
    }
}
