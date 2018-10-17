package com.linewell.mvp.contract;

public interface Callback<T> {
    void onStart();

    void onSuccess(T result);

    void onFail(CharSequence msg, Throwable t);

    void onComplete();

    void onCancel();
}