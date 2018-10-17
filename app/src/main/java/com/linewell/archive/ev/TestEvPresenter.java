package com.linewell.archive.ev;

import android.support.annotation.NonNull;
import android.util.Log;

import com.linewell.archive.bean.People;
import com.linewell.mvp.EvPresenter;

public class TestEvPresenter extends EvPresenter<TestEvContract.V, TestEvContract.M, People>
        implements TestEvContract.P {
    public TestEvPresenter(@NonNull TestEvContract.M model) {
        super(model);
    }

    @Override
    public void doOnCreate() {
        Log.e("lifecycle", "doOnCreate");
    }

    @Override
    public void doOnStart() {
        Log.e("lifecycle", "doOnStart");
    }

    @Override
    public void doOnResume() {
        Log.e("lifecycle", "doOnResume");
    }

    @Override
    public void doOnPause() {
        Log.e("lifecycle", "doOnPause");
    }

    @Override
    public void doOnStop() {
        Log.e("lifecycle", "doOnStop");
    }

    @Override
    public void doOnDestroy() {
        Log.e("lifecycle", "doOnDestroy");
    }
}