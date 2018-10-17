package com.linewell.archive.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.linewell.archive.R;
import com.linewell.core.common.ContentLayout;
import com.linewell.mvp.MvpFragment;

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
public class TestMvpFragment extends MvpFragment<TestFragmentContract.V,TestFragmentContract.P>
implements TestFragmentContract.V{

    @Override
    protected TestFragmentContract.P createPresenter(Bundle savedInstanceState) {
        return new TestFragmentPresenter(new TestFragmentContract.M() {
        }){
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
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("lifecycle", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("lifecycle", "onCreate");
    }

    @Override
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        ((TextView) findViewById(R.id.title)).setText("TestMvpFragment");
        Log.e("lifecycle", "onCreateView");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("lifecycle", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("lifecycle", "onResume");
    }

    @Override
    public void onPause() {
        Log.e("lifecycle", "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("lifecycle", "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e("lifecycle", "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e("lifecycle", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e("lifecycle", "onDetach");
        super.onDetach();
    }
}
