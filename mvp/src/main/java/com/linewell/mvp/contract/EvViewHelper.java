package com.linewell.mvp.contract;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.linewell.easyview.EasyView;

public class EvViewHelper<T> implements Callback<T> {

    protected EvContract.V.Config mConfig;
    protected EvContract.V mEvView;
    protected EasyView mEasyView;

    public EvViewHelper(@NonNull EvContract.V evView) {
        mEvView = evView;
        mConfig = evView.getConfig();
    }

    public void init(View contentView) {
        EasyView easyView = mConfig.mEasyView;
        if (easyView == null && mConfig.mEasyViewRes > 0) {
            easyView = contentView.findViewById(mConfig.mEasyViewRes);
        }
        init(easyView);
    }

    /**
     * 使用注意fragment 的getView()==null的情况,在onCreateView之后调用
     */
    public void init(Fragment fragment) {
        EasyView easyView = mConfig.mEasyView;
        View contentView = fragment.getView();
        if (easyView == null && contentView != null && mConfig.mEasyViewRes > 0) {
            easyView = contentView.findViewById(mConfig.mEasyViewRes);
        }
        init(easyView);
    }

    public void init(Activity activity) {
        EasyView easyView = mConfig.mEasyView;
        if (easyView == null && mConfig.mEasyViewRes > 0) {
            easyView = activity.findViewById(mConfig.mEasyViewRes);
        }
        init(easyView);
    }

    public void init(EasyView easyView) {
        mEasyView = easyView;
        if (mEasyView == null) {
            throw new RuntimeException("mEasyView can't null !");
        }

        mEasyView.setManualRefreshEnable(mConfig.mManualRefreshAble);
        mEasyView.setRefreshListener(mEvView);

        //content
        if (mConfig.mContentView != null) {
            mEasyView.setContentView(mConfig.mContentView);
        } else if (mConfig.mContentLayoutRes > 0) {
            mEasyView.setContentView(mConfig.mContentLayoutRes);
        }
        //加载view
        if (mConfig.mProgressView != null) {
            mEasyView.setProgressView(mConfig.mProgressView);
        } else if (mConfig.mProgressLayoutRes > 0) {
            mEasyView.setProgressView(mConfig.mProgressLayoutRes);
        }
        //失败view
        if (mConfig.mErrorView != null) {
            mEasyView.setErrorView(mConfig.mErrorView);
        } else if (mConfig.mErrorLayoutRes > 0) {
            mEasyView.setErrorView(mConfig.mErrorLayoutRes);
        }
    }

    public void destroy() {

    }

    @Override
    public void onStart() {
        mEasyView.hideTip(true);
        if (!mEasyView.currentViewIsContentView()) {
            mEasyView.showProgressView();
        } else {
            mEasyView.setRefreshing(true);
        }
    }

    @Override
    public void onSuccess(T result) {
        mEvView.onNotifyDataChange(result);
        mEasyView.setRefreshing(false);
        if (!mEasyView.currentViewIsContentView()) {
            mEasyView.showContentView();
        }
    }

    @Override
    public void onFail(CharSequence msg, Throwable t) {
        mEasyView.setRefreshing(false);
        if (mEasyView.currentViewIsProgressView()) {
            mEasyView.showErrorView();
        } else {
            showTip(msg);
        }
    }

    @Override
    public void onCancel() {
        if (!mEasyView.currentViewIsContentView()) {
            mEasyView.hideAllView();
        }
        mEasyView.setRefreshing(false);
    }

    @Override
    public void onComplete() {
        //do nothing
    }

    @MainThread
    public void showTip(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            mEasyView.showTipAndDelayClose(message, 3000);
        }
    }

    @MainThread
    public void showError() {
        mEasyView.showErrorView();
    }

    @MainThread
    public void showProgress() {
        mEasyView.showProgressView();
    }

    @MainThread
    public void showContent() {
        mEasyView.showContentView();
    }

    @MainThread
    public void showEmpty() {
        mEasyView.showEmptyView();
    }

    @MainThread
    public void doRefresh() {
        mEasyView.showProgressView();
        mEvView.onRefresh();
    }
}