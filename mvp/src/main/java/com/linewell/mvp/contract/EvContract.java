package com.linewell.mvp.contract;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.linewell.easyview.EasyView;
import com.linewell.mvp.R;

import java.io.Closeable;
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
public interface EvContract {
    interface V<Presenter extends P, DATA> extends Contract.V<Presenter>, EasyView.OnRefreshListener {

        void showTip(CharSequence message);

        void showError();

        void showProgress();

        void showContent();

        void showEmpty();

        void onNotifyDataChange(DATA data);

        @NonNull
        Config getConfig();

        void doRefresh();

        class Config implements Cloneable {
            public static final Config DEFAULT = new Config.Builder().build();

            boolean mManualRefreshAble;

            @IdRes
            int mEasyViewRes;

            EasyView mEasyView;

            android.view.View mContentView;
            @LayoutRes
            int mContentLayoutRes;

            android.view.View mEmptyView;
            @LayoutRes
            int mEmptyLayoutRes;

            android.view.View mProgressView;
            @LayoutRes
            int mProgressLayoutRes;

            android.view.View mErrorView;
            @LayoutRes
            int mErrorLayoutRes;

            private Config() {

            }

            public static final class Builder {
                private Config mConfig = new Config();

                public Builder() {
                    mConfig.mManualRefreshAble = false;
                }

                public Builder(Config config) {
                    mConfig.mManualRefreshAble = config.mManualRefreshAble;
                    mConfig.mEasyViewRes = config.mEasyViewRes;
                    mConfig.mContentView = config.mContentView;
                    mConfig.mContentLayoutRes = config.mContentLayoutRes;
                    mConfig.mEmptyView = config.mEmptyView;
                    mConfig.mEmptyLayoutRes = config.mEmptyLayoutRes;
                    mConfig.mProgressView = config.mProgressView;
                    mConfig.mProgressLayoutRes = config.mProgressLayoutRes;
                    mConfig.mErrorView = config.mErrorView;
                    mConfig.mErrorLayoutRes = config.mErrorLayoutRes;
                    mConfig.mEasyView = config.mEasyView;
                }

                public Config.Builder setManualRefreshAble(boolean manualRefreshAble) {
                    mConfig.mManualRefreshAble = manualRefreshAble;
                    return this;
                }

                public Config.Builder setProgressView(android.view.View progressView) {
                    mConfig.mProgressView = progressView;
                    return this;
                }

                public Config.Builder setEasyView(EasyView easyView) {
                    mConfig.mEasyView = easyView;
                    return this;
                }

                public Config.Builder setProgressLayoutRes(int progressRes) {
                    mConfig.mProgressLayoutRes = progressRes;
                    return this;
                }

                public Config.Builder setErrorLayoutView(android.view.View errorView) {
                    mConfig.mErrorView = errorView;
                    return this;
                }

                public Config.Builder setErrorLayoutRes(int errorRes) {
                    mConfig.mErrorLayoutRes = errorRes;
                    return this;
                }

                public Config.Builder setEasyViewRes(@IdRes int easyViewRes) {
                    mConfig.mEasyViewRes = easyViewRes;
                    return this;
                }

                public Config.Builder setContentView(android.view.View contentView) {
                    mConfig.mContentView = contentView;
                    return this;
                }

                public Config.Builder setContentLayoutRes(int contentRes) {
                    mConfig.mContentLayoutRes = contentRes;
                    return this;
                }

                public Config.Builder setEmptyView(android.view.View contentView) {
                    mConfig.mEmptyView = contentView;
                    return this;
                }

                public Config.Builder setEmptyLayoutRes(int emptyRes) {
                    mConfig.mEmptyLayoutRes = emptyRes;
                    return this;
                }

                public Config build() {
                    return mConfig;
                }
            }
        }
    }

    interface P<View extends V, DATA> extends Contract.P<View> {
        void refresh(Callback<DATA> callback);
    }

    interface M<DATA> extends Contract.M {
        MobileCloseable getData(Callback<DATA> callback, Map<String, Object> args);
    }
}
