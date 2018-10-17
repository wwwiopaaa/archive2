package com.linewell.mvp.contract;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.linewell.easyview.EasyRecyclerView;
import com.linewell.easyview.EasyView;
import com.linewell.easyview.LoadMoreAdapter;

import java.util.Map;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface RvContract {
    interface V<Presenter extends P, DATA> extends Contract.V<Presenter>,
            EasyView.OnRefreshListener, LoadMoreAdapter.OnLoadListener, MoreListener {
        void showTip(CharSequence message);

        void showError();

        void showProgress();

        void showContent();

        void showEmpty();

        void onNotifyDataChange(boolean isRefresh, DATA data);

        @NonNull
        Config getConfig();

        void doRefresh();

        @NonNull
        RecyclerView.Adapter getAdapter();

        class Config implements Cloneable {
            public static final Config DEFAULT = new Builder().build();

            boolean mManualRefreshAble;

            boolean mLoadMoreAble;

            LoadMoreAdapter.LoadMoreView.Factory mLoadMoreViewFactory;

            @IdRes
            int mRecyclerViewRes;

            EasyRecyclerView mEasyRecyclerView;

            View mEmptyView;
            @LayoutRes
            int mEmptyLayoutRes;

            View mProgressView;
            @LayoutRes
            int mProgressLayoutRes;

            View mErrorView;
            @LayoutRes
            int mErrorLayoutRes;

            private Config() {
            }

            public static class Builder {

                private Config mConfig = new Config();

                public Builder() {
                    mConfig.mLoadMoreAble = false;
                    mConfig.mManualRefreshAble = false;
                }

                public Builder(Config config) {
                    mConfig.mLoadMoreAble = config.mLoadMoreAble;
                    mConfig.mManualRefreshAble = config.mManualRefreshAble;
                    mConfig.mEmptyView = config.mEmptyView;
                    mConfig.mEmptyLayoutRes = config.mEmptyLayoutRes;
                    mConfig.mErrorView = config.mErrorView;
                    mConfig.mErrorLayoutRes = config.mErrorLayoutRes;
                    mConfig.mProgressView = config.mProgressView;
                    mConfig.mProgressLayoutRes = config.mProgressLayoutRes;
                    mConfig.mLoadMoreViewFactory = config.mLoadMoreViewFactory;
                    mConfig.mEasyRecyclerView = config.mEasyRecyclerView;
                }

                public Config.Builder setManualRefreshAble(boolean manualRefreshAble) {
                    mConfig.mManualRefreshAble = manualRefreshAble;
                    return this;
                }

                public Builder setLoadMoreAble(boolean loadMoreAble) {
                    mConfig.mLoadMoreAble = loadMoreAble;
                    return this;
                }

                public Builder setLoadMoreViewFactory(LoadMoreAdapter.LoadMoreView.Factory factory) {
                    mConfig.mLoadMoreViewFactory = factory;
                    return this;
                }

                public Builder setEmptyView(View emptyView) {
                    mConfig.mEmptyView = emptyView;
                    return this;
                }

                public Builder setEmptyLayoutRes(int emptyRes) {
                    mConfig.mEmptyLayoutRes = emptyRes;
                    return this;
                }

                public Builder setProgressView(View progressView) {
                    mConfig.mProgressView = progressView;
                    return this;
                }

                public Builder setProgressLayoutRes(int progressRes) {
                    mConfig.mProgressLayoutRes = progressRes;
                    return this;
                }

                public Builder setErrorView(View errorView) {
                    mConfig.mErrorView = errorView;
                    return this;
                }

                public Builder setErrorLayoutRes(int errorRes) {
                    mConfig.mErrorLayoutRes = errorRes;
                    return this;
                }

                public Builder setRecyclerViewRes(@IdRes int recyclerViewRes) {
                    mConfig.mRecyclerViewRes = recyclerViewRes;
                    return this;
                }

                public Builder setRecyclerViewRes(EasyRecyclerView recyclerView) {
                    mConfig.mEasyRecyclerView = recyclerView;
                    return this;
                }

                public Config build() {
                    return mConfig;
                }
            }

        }
    }

    interface P<View extends V, T> extends Contract.P<View>, MoreListener {
        void refresh(Callback<T> callback);

        void loadMore(Callback<T> callback);
    }

    interface M<DATA> extends Contract.M {
        MobileCloseable getData(Callback<DATA> callback, Map<String, Object> args, boolean isRefresh);
    }

    interface MoreListener {
        boolean hasMore();
    }

}
