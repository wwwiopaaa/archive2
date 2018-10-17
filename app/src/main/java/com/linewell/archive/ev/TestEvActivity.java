package com.linewell.archive.ev;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.linewell.archive.R;
import com.linewell.archive.bean.People;
import com.linewell.mvp.EvActivity;
import com.linewell.mvp.EvPresenter;
import com.linewell.mvp.contract.Callback;
import com.linewell.mvp.contract.MobileCloseable;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestEvActivity extends EvActivity<TestEvContract.V, TestEvContract.P, People>
        implements TestEvContract.V {

    public static void start(Context context) {
        Intent starter = new Intent(context, TestEvActivity.class);
        context.startActivity(starter);
    }

    private TextView tVResult;

    @Override
    protected TestEvContract.P createPresenter(Bundle savedInstanceState) {
        return new TestEvPresenter(new TestEvModel());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tVResult = findViewById(R.id.result);
        doRefresh();
        Log.e("lifecycle", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("lifecycle", "onStart");
    }

    @Override
    protected void onStop() {
        Log.e("lifecycle", "onStop");
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("lifecycle", "onResume");
    }

    @Override
    protected void onPause() {
        Log.e("lifecycle", "onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.e("lifecycle", "onDestroy");
        super.onDestroy();
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
