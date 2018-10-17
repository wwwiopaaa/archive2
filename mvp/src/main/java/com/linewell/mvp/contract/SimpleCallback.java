package com.linewell.mvp.contract;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SimpleCallback<T> implements Callback<T> {
    @Override
    public void onStart() {

    }

    @Override
    public void onSuccess(T result) {

    }

    @Override
    public void onFail(CharSequence msg, Throwable t) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onCancel() {

    }
}
