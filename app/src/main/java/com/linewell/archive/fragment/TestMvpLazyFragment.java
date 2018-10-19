package com.linewell.archive.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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
    public static final String ARG_TITLE = "TITLE";

    public static TestMvpLazyFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        TestMvpLazyFragment fragment = new TestMvpLazyFragment();
        fragment.setArguments(args);
        return fragment;
    }
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
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("lifecycle", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("lifecycle", "onCreate");
    }

    TextView title;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState, boolean preIsPrepared) {
        super.onCreateViewLazy(savedInstanceState, preIsPrepared);
        title = (TextView) findViewById(R.id.title);
        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_TITLE)) {
            title.setText(savedInstanceState.getString(ARG_TITLE));
        } else {
            title.setText(getArguments().getString(ARG_TITLE,"TestMvpFragment"));
        }
        Log.e("lifecycle", "onCreateViewLazy");
    }

    @Override
    public void onSaveInstanceStateLazy(Bundle outState) {
        outState.putString(ARG_TITLE, title.getText().toString());
        super.onSaveInstanceStateLazy(outState);
    }

    @Override
    public void onStartLazy() {
        super.onStartLazy();
        Log.e("lifecycle", "onStartLazy");
    }

    @Override
    public void onResumeLazy() {
        super.onResumeLazy();
        Log.e("lifecycle", "onResumeLazy");
    }

    @Override
    public void onPauseLazy() {
        Log.e("lifecycle", "onPauseLazy");
        super.onPauseLazy();
    }

    @Override
    public void onStopLazy() {
        Log.e("lifecycle", "onStopLazy");
        super.onStopLazy();
    }

    @Override
    public void onDestroyViewLazy() {
        Log.e("lifecycle", "onDestroyViewLazy");
        super.onDestroyViewLazy();
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
