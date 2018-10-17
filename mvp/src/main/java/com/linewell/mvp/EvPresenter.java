package com.linewell.mvp;

import android.support.annotation.NonNull;

import com.linewell.mvp.contract.Callback;
import com.linewell.mvp.contract.EvContract;
import com.linewell.mvp.contract.MobileCloseable;


/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class EvPresenter<V extends EvContract.V, M extends EvContract.M, DATA> extends Presenter<V, M>
        implements EvContract.P<V, DATA> {

    private MobileCloseable mCloseable;

    public EvPresenter(@NonNull M model) {
        super(model);
    }

    @Override
    public void refresh(final Callback<DATA> callback) {
        if (mCloseable != null && !mCloseable.isClosed()) {
            mCloseable.close();
        }
        mCloseable = provideRequest(new Callback<DATA>() {
            @Override
            public void onStart() {
                onLoadStart(callback);
            }

            @Override
            public void onSuccess(DATA result) {
                onLoadSuccess(callback, result);
            }

            @Override
            public void onFail(CharSequence msg, Throwable t) {
                onLoadFail(callback, msg, t);
            }

            @Override
            public void onComplete() {
                onLoadComplete(callback);
            }

            @Override
            public void onCancel() {
                onLoadCancel(callback);
            }
        });
    }

    protected MobileCloseable provideRequest(Callback<DATA> callback) {
        return getM().getData(callback, null);
    }

    @Override
    public void detach() {
        if (mCloseable != null && !mCloseable.isClosed()) {
            mCloseable.close();
            mCloseable = null;
        }
        super.detach();
    }

    protected void onLoadStart(Callback<DATA> callback) {
        callback.onStart();
    }

    protected void onLoadComplete(Callback<DATA> callback) {
        callback.onComplete();
        mCloseable = null;
    }

    protected void onLoadFail(Callback<DATA> callback, CharSequence msg, Throwable t) {
        callback.onFail(msg, t);
    }

    protected void onLoadSuccess(Callback<DATA> callback, DATA t) {
        callback.onSuccess(t);
    }

    protected void onLoadCancel(Callback<DATA> callback) {
        callback.onCancel();
    }

}
