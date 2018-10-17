package com.linewell.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.linewell.core.common.ContentLayoutHelper;
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
public abstract class EvActivity<V extends EvContract.V, P extends EvContract.P<V, DATA>, DATA> extends MvpActivity<V, P>
        implements EvContract.V<P, DATA> {

    private EvViewHelper mViewHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int layoutRes = getContentLayoutRes();
        if (layoutRes > 0) {
            setContentView(layoutRes);
        }
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

    @NonNull
    @Override
    public Config getConfig() {
        return new Config.Builder()
                .setEasyViewRes(R.id.easy_view)
                .build();
    }

    public EvViewHelper getViewHelper() {
        return mViewHelper;
    }

    @Override
    protected int getContentLayoutRes() {
        int layoutRes = super.getContentLayoutRes();
        if (layoutRes > 0) {
            return layoutRes;
        }
        return R.layout.common_easy_view;
    }
}
