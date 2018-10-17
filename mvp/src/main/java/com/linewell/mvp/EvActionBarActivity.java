package com.linewell.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.linewell.mvp.contract.EvContract;
import com.linewell.mvp.contract.EvViewHelper;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class EvActionBarActivity<V extends EvContract.V, P extends EvContract.P<V, DATA>, DATA> extends ActionBarActivity<V, P>
        implements EvContract.V<P, DATA> {

    private EvViewHelper mViewHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewHelper = new EvViewHelper(this);
        mViewHelper.init(this);
    }

    @Override
    protected void onDestroy() {
        if (mViewHelper != null) {
            mViewHelper.destroy();
            mViewHelper = null;
        }
        super.onDestroy();
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
        int contentLayoutRes = super.getContentLayoutRes();
        if (contentLayoutRes > 0) {
            return contentLayoutRes;
        }
        return R.layout.common_easy_view;
    }
}
