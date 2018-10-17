package com.linewell.archive.fragment;

import android.support.annotation.NonNull;
import android.util.Log;

import com.linewell.mvp.Presenter;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestFragmentPresenter extends Presenter<TestFragmentContract.V, TestFragmentContract.M>
        implements TestFragmentContract.P {
    public TestFragmentPresenter(@NonNull TestFragmentContract.M model) {
        super(model);
    }


}
