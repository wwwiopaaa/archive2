package com.linewell.mvp;

import android.support.annotation.NonNull;

import com.linewell.mvp.contract.Callback;
import com.linewell.mvp.contract.MobileCloseable;
import com.linewell.mvp.contract.RvContract;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RvPresenter<V extends RvContract.V, M extends RvContract.M, DATA> extends Presenter<V, M>
        implements RvContract.P<V, DATA> {

    private boolean mHasMore = false;

    private MobileCloseable mRefreshCloseable;
    private MobileCloseable mLoadMoreCloseable;

    public RvPresenter(@NonNull M model) {
        super(model);
    }

    @Override
    public void refresh(final Callback<DATA> callback) {
        close(mRefreshCloseable);
        mRefreshCloseable = provideRequest(new Callback<DATA>() {
            @Override
            public void onStart() {
                close(mLoadMoreCloseable);
                onRefreshStart(callback);
            }

            @Override
            public void onSuccess(DATA result) {
                onRefreshSuccess(callback,result);
            }

            @Override
            public void onFail(CharSequence msg, Throwable t) {
                onRefreshFail(callback,msg,t);
            }

            @Override
            public void onComplete() {
                onRefreshComplete(callback);
            }

            @Override
            public void onCancel() {
                onRefreshCancel(callback);
            }
        }, true);
    }

    @Override
    public void loadMore(final Callback<DATA> callback) {
        close(mLoadMoreCloseable);
        mLoadMoreCloseable = provideRequest(new Callback<DATA>() {
            @Override
            public void onStart() {
                close(mRefreshCloseable);
                onLoadMoreStart(callback);
            }

            @Override
            public void onSuccess(DATA result) {
                onLoadMoreSuccess(callback, result);
            }

            @Override
            public void onFail(CharSequence msg, Throwable t) {
                onLoadMoreFail(callback, msg, t);
            }

            @Override
            public void onComplete() {
                onLoadMoreComplete(callback);
            }

            @Override
            public void onCancel() {
                onLoadMoreCancel(callback);
            }
        }, false);
    }

    @Override
    public void detach() {
        close(mRefreshCloseable);
        close(mLoadMoreCloseable);
        super.detach();
    }

    protected MobileCloseable provideRequest(Callback<DATA> callback, boolean isRefresh) {
        return getM().getData(callback, null, isRefresh);
    }

    public final void setHasMore(boolean hasMore) {
        mHasMore = hasMore;
    }

    @Override
    public final boolean hasMore() {
        return mHasMore;
    }

    protected void onRefreshCancel(Callback<DATA> callback) {
        callback.onCancel();
    }

    protected void onRefreshComplete(Callback<DATA> callback) {
        callback.onComplete();
        mRefreshCloseable = null;
    }

    protected void onRefreshFail(Callback<DATA> callback, CharSequence msg, Throwable t) {
        callback.onFail(msg, t);
    }

    protected void onRefreshSuccess(Callback<DATA> callback, DATA result) {
        callback.onSuccess(result);
    }

    protected void onLoadMoreStart(Callback<DATA> callback) {
        callback.onStart();
    }

    protected void onLoadMoreCancel(Callback<DATA> callback) {
        callback.onCancel();
    }

    protected void onLoadMoreComplete(Callback<DATA> callback) {
        callback.onComplete();
        mLoadMoreCloseable = null;
    }

    protected void onLoadMoreFail(Callback<DATA> callback, CharSequence msg, Throwable t) {
        callback.onFail(msg, t);
    }

    protected void onLoadMoreSuccess(Callback<DATA> callback, DATA result) {
        callback.onSuccess(result);
    }

    protected void onRefreshStart(Callback<DATA> callback) {
        callback.onStart();
    }

    private static final void close(MobileCloseable closeable) {
        if (closeable != null && !closeable.isClosed()) {
            closeable.close();
        }
    }
}
