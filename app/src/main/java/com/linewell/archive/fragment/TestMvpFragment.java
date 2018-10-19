package com.linewell.archive.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
public class TestMvpFragment extends MvpFragment<TestFragmentContract.V, TestFragmentContract.P>
        implements TestFragmentContract.V {

    public static final String ARG_TITLE = "TITLE";

    public static TestMvpFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        TestMvpFragment fragment = new TestMvpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected TestFragmentContract.P createPresenter(Bundle savedInstanceState) {
        return new TestFragmentPresenter(new TestFragmentContract.M() {
        });
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
    public void onCreateView(@Nullable Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        title = (TextView) findViewById(R.id.title);
        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_TITLE)) {
            title.setText(savedInstanceState.getString(ARG_TITLE));
        } else {
            title.setText(getArguments().getString(ARG_TITLE,"TestMvpFragment"));
        }
        Log.e("lifecycle", "onCreateView");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putString(ARG_TITLE, title.getText().toString());
        super.onSaveInstanceState(outState);
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
