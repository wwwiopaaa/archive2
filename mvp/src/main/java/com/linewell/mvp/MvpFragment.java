package com.linewell.mvp;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.linewell.core.common.BaseFragment;
import com.linewell.core.common.ContentLayoutHelper;
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
public abstract class MvpFragment<V extends Contract.V, P extends Contract.P<V>> extends BaseFragment
        implements Contract.V<P> {

    private P mPresenter;

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        attachPresenter(savedInstanceState);
        final int layoutRes = getContentLayoutRes();
        if (layoutRes > 0) {
            setContentView(layoutRes);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        if (mPresenter instanceof LifecycleObserver) {
//            getViewLifecycleOwner().getLifecycle().addObserver(((LifecycleObserver) mPresenter));
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        detachPresenter();
        super.onDestroyView();
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
            getViewLifecycleOwner().getLifecycle().addObserver(((LifecycleObserver) mPresenter));
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
