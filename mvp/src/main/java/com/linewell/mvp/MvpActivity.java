package com.linewell.mvp;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.os.Bundle;

import com.linewell.core.common.BaseActivity;
import com.linewell.mvp.contract.Contract;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class MvpActivity<V extends Contract.V, P extends Contract.P<V>> extends BaseActivity
        implements Contract.V<P> {

    private P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attachPresenter(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        detachPresenter();
        super.onDestroy();
    }

    private void attachPresenter(Bundle savedInstanceState) {
        mPresenter = createPresenter(savedInstanceState);
        if (mPresenter == null) {
            throw new IllegalArgumentException("mPresenter can't null");
        }
        final V viewController = getViewController();
        if (viewController == null) {
            throw new IllegalStateException("viewController can't null");
        }

        mPresenter.attach(viewController);
        if (mPresenter instanceof LifecycleObserver) {
            getLifecycle().addObserver(((LifecycleObserver) mPresenter));
        }
    }

    private void detachPresenter() {
        if (mPresenter != null) {
            mPresenter.detach();
            mPresenter = null;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    public P getPresenter() {
        return mPresenter;
    }

    protected abstract P createPresenter(Bundle savedInstanceState);

    protected V getViewController() {
        return (V) this;
    }

}
