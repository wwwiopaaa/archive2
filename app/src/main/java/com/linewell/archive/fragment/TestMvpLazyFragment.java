package com.linewell.archive.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.linewell.archive.R;
import com.linewell.core.common.ContentLayout;
import com.linewell.mvp.MvpLazyFragment;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@ContentLayout(R.layout.fragment_item)
public class TestMvpLazyFragment extends MvpLazyFragment<TestFragmentContract.V, TestFragmentContract.P>
        implements TestFragmentContract.V {

    @Override
    protected TestFragmentContract.P createPresenter(Bundle savedInstanceState) {
        return new TestFragmentPresenter(new TestFragmentContract.M() {
        })/*{
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
        }*/;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.e("lifecycle", "onCreate");
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState, boolean preIsPrepared) {
        super.onCreateViewLazy(savedInstanceState, preIsPrepared);
        ((TextView) findViewById(R.id.title)).setText("TestMvpLazyFragment");
//        Log.e("lifecycle", "onCreateViewLazy");
    }

    @Override
    public void onStartLazy() {
        super.onStartLazy();
//        Log.e("lifecycle", "onStartLazy");
    }

    @Override
    public void onResumeLazy() {
        super.onResumeLazy();
//        Log.e("lifecycle", "onResumeLazy");
    }

    @Override
    public void onPauseLazy() {
//        Log.e("lifecycle", "onPauseLazy");
        super.onPauseLazy();
    }

    @Override
    public void onStopLazy() {
//        Log.e("lifecycle", "onStopLazy");
        super.onStopLazy();
    }

    @Override
    public void onDestroyViewLazy() {
//        Log.e("lifecycle", "onDestroyViewLazy");
        super.onDestroyViewLazy();
    }

    @Override
    public void onDestroy() {
//        Log.e("lifecycle", "onDestroy");
        super.onDestroy();
    }
}
