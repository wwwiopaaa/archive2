package com.linewell.archive.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.linewell.archive.R;
import com.linewell.archive.bean.People;
import com.linewell.archive.ev.TestEvContract;
import com.linewell.archive.ev.TestEvModel;
import com.linewell.archive.ev.TestEvPresenter;
import com.linewell.mvp.EvFragment;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestEvFragment extends EvFragment<TestEvContract.V, TestEvContract.P, People>
        implements TestEvContract.V {

    public static TestEvFragment newInstance() {

        Bundle args = new Bundle();

        TestEvFragment fragment = new TestEvFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView tVResult;
    @Override
    protected TestEvContract.P createPresenter(Bundle savedInstanceState) {
        return new TestEvPresenter(new TestEvModel());
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState, boolean preIsPrepared) {
        super.onCreateViewLazy(savedInstanceState, preIsPrepared);
        tVResult = findViewById(R.id.result);
        doRefresh();
    }

    @Override
    public void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }

    @Override
    public void onNotifyDataChange(People people) {
        tVResult.setText("name:" + people.name + " age:" + people.age);
    }

    @NonNull
    @Override
    public Config getConfig() {
        return new Config.Builder(super.getConfig())
                .setContentLayoutRes(R.layout.activity_test_ev_content)
                .setEmptyLayoutRes(R.layout.layout_empty)
                .setManualRefreshAble(true)
                .setErrorLayoutRes(R.layout.layout_error)
                .setProgressLayoutRes(R.layout.layout_progress)
                .build();
    }
}
