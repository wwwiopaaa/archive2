package com.linewell.archive.rv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.linewell.archive.R;
import com.linewell.archive.bean.People;
import com.linewell.mvp.RvActionBarActivity;
import com.linewell.support.adapter.CommonAdapter;
import com.linewell.support.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestRvActionBarActivity extends RvActionBarActivity<TestRvContract.V, TestRvContract.P, List<People>>
        implements TestRvContract.V {

    public static void start(Context context) {
        Intent starter = new Intent(context, TestRvActionBarActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected TestRvContract.P createPresenter(Bundle savedInstanceState) {
        return new TestRvPresenter(new TestRvModel());
    }

    @NonNull
    @Override
    public Config getConfig() {
        return new Config.Builder(super.getConfig())
                .setManualRefreshAble(true)
                .setLoadMoreAble(true)
                .setRecyclerViewRes(R.id.easy_recycler_view)
                .setEmptyLayoutRes(R.layout.layout_empty)
                .setErrorLayoutRes(R.layout.layout_error)
                .setProgressLayoutRes(R.layout.layout_progress)
                .build();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doRefresh();
    }

    @Override
    public void onNotifyDataChange(boolean isRefresh, List<People> people) {
        if (isRefresh) {
            mdatas.clear();
        }
        mdatas.addAll(people);
        getAdapter().notifyDataSetChanged();
    }

    List<People> mdatas = new ArrayList<>();

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new CommonAdapter<People>(this, R.layout.item_content) {
            @Override
            protected void convert(ViewHolder holder, People people, int position) {
                holder.setText(R.id.title, position + " name:" + people.name + " age:" + people.age);
                holder.setVisible(R.id.button, false);
            }

            @Override
            public People getItem(int position) {
                return mdatas.get(position);
            }

            @Override
            public int getItemCount() {
                return mdatas.size();
            }
        };
    }
}
