package com.linewell.archive.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.linewell.archive.R;
import com.linewell.archive.bean.People;
import com.linewell.archive.rv.TestRvContract;
import com.linewell.archive.rv.TestRvModel;
import com.linewell.archive.rv.TestRvPresenter;
import com.linewell.mvp.RvFragment;
import com.linewell.support.recycler.CommonAdapter;
import com.linewell.support.recycler.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestRvFragment extends RvFragment<TestRvContract.V, TestRvContract.P, List<People>>
        implements TestRvContract.V {
    public static TestRvFragment newInstance() {

        Bundle args = new Bundle();

        TestRvFragment fragment = new TestRvFragment();
        fragment.setArguments(args);
        return fragment;
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
    protected void onCreateViewLazy(Bundle savedInstanceState, boolean preIsPrepared) {
        super.onCreateViewLazy(savedInstanceState, preIsPrepared);
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
        return new CommonAdapter<People>(getContext(), R.layout.item_content) {
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
