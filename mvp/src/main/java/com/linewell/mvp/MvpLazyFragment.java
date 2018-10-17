package com.linewell.mvp;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.linewell.core.common.ContentLayoutHelper;
import com.linewell.core.common.LazyFragment;
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
public abstract class MvpLazyFragment<V extends Contract.V, P extends Contract.P<V>> extends LazyFragment
        implements Contract.V<P> {

    private P mPresenter;

    @CallSuper
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState, boolean preIsPrepared) {
        attachPresenter(savedInstanceState);
        final int layoutRes = getContentLayoutRes();
        if (layoutRes > 0) {
            setContentView(layoutRes);
        }
    }

    @CallSuper
    public void onStartLazy() {

    }

    @CallSuper
    @Override
    public void onResumeLazy() {

    }

    @CallSuper
    @Override
    public void onPauseLazy() {

    }

    @CallSuper
    @Override
    public void onStopLazy() {

    }

    @CallSuper
    @Override
    public void onDestroyViewLazy() {
        detachPresenter();
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
            getLifecycleLazy().addObserver(((LifecycleObserver) mPresenter));
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
        return super.getContext();
    }

    public P getPresenter() {
        return mPresenter;
    }

    protected abstract P createPresenter(Bundle savedInstanceState);

    protected V getViewController() {
        return (V) this;
    }

    protected int getContentLayoutRes() {
        int contentLayoutRes = ContentLayoutHelper.getLayoutResByAnnotation(this.getClass());
        if (contentLayoutRes > 0) {
            return contentLayoutRes;
        }
        return -1;
    }
}
