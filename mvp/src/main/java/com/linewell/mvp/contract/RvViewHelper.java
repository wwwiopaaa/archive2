package com.linewell.mvp.contract;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.linewell.core.common.BaseFragment;
import com.linewell.easyview.EasyRecyclerView;
import com.linewell.easyview.LoadMoreAdapter;

public class RvViewHelper {
    protected RvContract.V mRvView;
    protected RvContract.V.Config mConfig;
    protected EasyRecyclerView mEasyRecyclerView;
    protected Callback mLoadMoreCallback;
    protected Callback mRefreshCallback;

    public RvViewHelper(@NonNull RvContract.V rvView) {
        mRvView = rvView;
        mConfig = rvView.getConfig();
    }

    public void init(View view) {
        EasyRecyclerView easyRecyclerView = mConfig.mEasyRecyclerView;
        if (easyRecyclerView == null && mConfig.mRecyclerViewRes > 0) {
            easyRecyclerView = view.findViewById(mConfig.mRecyclerViewRes);
        }
        init(easyRecyclerView);
    }

    /**
     * 使用注意fragment 的getview()==null的情况
     */
    public void init(BaseFragment fragment) {
        EasyRecyclerView easyRecyclerView = mConfig.mEasyRecyclerView;
        View contentView = fragment.getContentView();
        if (easyRecyclerView == null && contentView != null && mConfig.mRecyclerViewRes > 0) {
            easyRecyclerView = contentView.findViewById(mConfig.mRecyclerViewRes);
        }
        init(easyRecyclerView);
    }

    public void init(Activity activity) {
        EasyRecyclerView easyRecyclerView = mConfig.mEasyRecyclerView;
        if (easyRecyclerView == null && mConfig.mRecyclerViewRes > 0) {
            easyRecyclerView = activity.findViewById(mConfig.mRecyclerViewRes);
        }
        init(easyRecyclerView);
    }

    public void init(EasyRecyclerView easyRecyclerView) {
        this.mEasyRecyclerView = easyRecyclerView;
        if (mEasyRecyclerView == null) {
            throw new RuntimeException("EasyRecycleView can't null !");
        }

        mEasyRecyclerView.setOnLoadMoreListener(mRvView);
        mEasyRecyclerView.setRefreshListener(mRvView);

        mEasyRecyclerView.setLayoutManager(new LinearLayoutManager(mRvView.getContext()));
        mEasyRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //空数据view
        if (mConfig.mEmptyView != null) {
            mEasyRecyclerView.setEmptyView(mConfig.mEmptyView);
        } else if (mConfig.mEmptyLayoutRes > 0) {
            mEasyRecyclerView.setEmptyView(mConfig.mEmptyLayoutRes);
        }
        //加载view
        if (mConfig.mProgressView != null) {
            mEasyRecyclerView.setProgressView(mConfig.mProgressView);
        } else if (mConfig.mProgressLayoutRes > 0) {
            mEasyRecyclerView.setProgressView(mConfig.mProgressLayoutRes);
        }
        //失败view
        if (mConfig.mErrorView != null) {
            mEasyRecyclerView.setErrorView(mConfig.mErrorView);
        } else if (mConfig.mErrorLayoutRes > 0) {
            mEasyRecyclerView.setErrorView(mConfig.mErrorLayoutRes);
        }

        mEasyRecyclerView.setManualRefreshEnable(mConfig.mManualRefreshAble);
        mEasyRecyclerView.setLoadMoreEnable(mConfig.mLoadMoreAble);

        mEasyRecyclerView.setAdapter(mRvView.getAdapter());

        if (mConfig.mLoadMoreViewFactory != null) {
            mEasyRecyclerView.setLoadMoreViewFactory(mConfig.mLoadMoreViewFactory);
        }
    }

    public void destroy() {

    }

    public RecyclerView getRecyclerView() {
        return mEasyRecyclerView.getRecyclerView();
    }

    public EasyRecyclerView getEasyRecyclerView() {
        return mEasyRecyclerView;
    }

    @MainThread
    public void doRefresh() {
        mEasyRecyclerView.showProgressView();
        mRvView.onRefresh();
    }

    @MainThread
    public void showTip(CharSequence message) {
        if (!TextUtils.isEmpty(message)) {
            mEasyRecyclerView.showTipAndDelayClose(message, 3000);
        }
    }

    @MainThread
    public void showError() {
        mEasyRecyclerView.showErrorView();
    }

    @MainThread
    public void showProgress() {
        mEasyRecyclerView.showProgressView();
    }

    @MainThread
    public void showContent() {
        mEasyRecyclerView.showContentView();
    }

    @MainThread
    public void showEmpty() {
        mEasyRecyclerView.showContentView();
    }

    public Callback getLoadMoreCallback() {
        if (mLoadMoreCallback == null) {
            mLoadMoreCallback = new Callback() {
                @Override
                public void onStart() {
                    if (mEasyRecyclerView.isEnableLoadMore()) {
                        LoadMoreAdapter adapter = getLoadMoreAdapter();
                        if (adapter != null) {
                            adapter.showLoading();
                        }
                    }
                }

                @Override
                public void onSuccess(Object result) {
                    mRvView.onNotifyDataChange(false, result);
                    final boolean hasMore = mRvView.hasMore();
                    LoadMoreAdapter adapter = getLoadMoreAdapter();
                    if (adapter == null) {
                        return;
                    }
                    if (mEasyRecyclerView.isEnableLoadMore()) {
                        if (hasMore) {
                            adapter.showIdle();
                        } else {
                            adapter.showNoMore();
                        }
                    }
                    RecyclerView.Adapter wrapperAdapter = adapter.getWrapperAdapter();
                    final int count = wrapperAdapter != null ? wrapperAdapter.getItemCount() : 0;
                    if (count <= 0 && (!adapter.isLoadMoreEnable() || !hasMore)) {
                        mEasyRecyclerView.showEmptyView();
                    }
                }

                @Override
                public void onFail(CharSequence msg, Throwable t) {
                    LoadMoreAdapter adapter = getLoadMoreAdapter();
                    if (adapter != null) {
                        adapter.showError();
                    } else {
                        showTip(msg);
                    }
                }

                @Override
                public void onComplete() {
                    //do nothing
                }

                @Override
                public void onCancel() {
                    if (mEasyRecyclerView.isEnableLoadMore()) {
                        LoadMoreAdapter adapter = getLoadMoreAdapter();
                        if (adapter != null) {
                            if (mRvView.hasMore()) {
                                adapter.showIdle();
                            } else {
                                adapter.showNoMore();
                            }
                        }
                    }
                }
            };
        }
        return mLoadMoreCallback;
    }

    public Callback getRefreshCallback() {
        if (mRefreshCallback == null) {
            mRefreshCallback = new Callback() {
                @Override
                public void onStart() {
                    mEasyRecyclerView.hideTip(true);
                    if (!mEasyRecyclerView.currentViewIsContentView()) {
                        mEasyRecyclerView.showProgressView();
                    } else {
                        mEasyRecyclerView.setRefreshing(true);
                    }
                }

                @Override
                public void onSuccess(Object result) {
                    mRvView.onNotifyDataChange(true, result);
                    mEasyRecyclerView.setRefreshing(false);
                    boolean hasMore = mRvView.hasMore();
                    LoadMoreAdapter adapter = getLoadMoreAdapter();
                    if (adapter != null) {
                        if (mEasyRecyclerView.isEnableLoadMore()) {
                            if (hasMore) {
                                adapter.showIdle();
                            } else {
                                adapter.showNoMore();
                            }
                        }
                        RecyclerView.Adapter wrapperAdapter = adapter.getWrapperAdapter();
                        final int count = wrapperAdapter != null ? wrapperAdapter.getItemCount() : 0;
                        if (count <= 0 && (!adapter.isLoadMoreEnable() || !hasMore)) {
                            mEasyRecyclerView.showEmptyView();
                        } else {
                            if (!mEasyRecyclerView.currentViewIsContentView()) {
                                mEasyRecyclerView.showContentView();
                            }
                        }
                    } else {
                        if (!mEasyRecyclerView.currentViewIsContentView()) {
                            mEasyRecyclerView.showContentView();
                        }
                    }
                }

                @Override
                public void onFail(CharSequence msg, Throwable t) {
                    mEasyRecyclerView.setRefreshing(false);
                    if (mEasyRecyclerView.currentViewIsProgressView()) {
                        mEasyRecyclerView.showErrorView();
                    } else {
                        showTip(msg);
                    }
                }

                @Override
                public void onComplete() {
                    //do nothing
                }

                @Override
                public void onCancel() {
                    if (!mEasyRecyclerView.currentViewIsContentView()) {
                        mEasyRecyclerView.showEmptyView();
                    }
                    mEasyRecyclerView.setRefreshing(false);
                }
            };
        }
        return mRefreshCallback;
    }

    private LoadMoreAdapter getLoadMoreAdapter() {
        RecyclerView.Adapter adapter = getRecyclerView().getAdapter();
        if (adapter != null && adapter instanceof LoadMoreAdapter) {
            return ((LoadMoreAdapter) adapter);
        }
        return null;
    }

}