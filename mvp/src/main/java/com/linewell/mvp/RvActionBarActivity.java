package com.linewell.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.linewell.easyview.EasyRecyclerView;
import com.linewell.mvp.contract.RvContract;
import com.linewell.mvp.contract.RvViewHelper;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class RvActionBarActivity<V extends RvContract.V, P extends RvContract.P<V, DATA>, DATA> extends ActionBarActivity<V, P>
        implements RvContract.V<P, DATA> {

    private RvViewHelper viewHelper;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewHelper = new RvViewHelper(this);
        viewHelper.init(this);
    }

    @Override
    protected void onDestroy() {
        if (viewHelper != null) {
            viewHelper.destroy();
            viewHelper = null;
        }
        super.onDestroy();
    }

    @Override
    public void showTip(CharSequence message) {
        if (viewHelper != null) {
            viewHelper.showTip(message);
        }
    }

    @Override
    public void showError() {
        if (viewHelper != null) {
            viewHelper.showError();
        }
    }

    @Override
    public void showProgress() {
        if (viewHelper != null) {
            viewHelper.showProgress();
        }
    }

    @Override
    public void showContent() {
        if (viewHelper != null) {
            viewHelper.showContent();
        }
    }

    @Override
    public void showEmpty() {
        if (viewHelper != null) {
            viewHelper.showEmpty();
        }
    }

    @NonNull
    @Override
    public Config getConfig() {
        return new Config.Builder()
                .setRecyclerViewRes(R.id.easy_recycler_view)
                .build();
    }

    @Override
    public void doRefresh() {
        if (viewHelper != null) {
            viewHelper.doRefresh();
        }
    }

    @Override
    public void onRefresh() {
        if (viewHelper != null) {
            getPresenter().refresh(viewHelper.getRefreshCallback());
        }
    }

    @Override
    public void onRetry() {
        if (viewHelper != null) {
            getPresenter().loadMore(viewHelper.getLoadMoreCallback());
        }
    }

    @Override
    public void onLoadMore() {
        if (viewHelper != null) {
            getPresenter().loadMore(viewHelper.getLoadMoreCallback());
        }
    }

    @NonNull
    @Override
    public final RecyclerView.Adapter getAdapter() {
        if (adapter == null) {
            adapter = createAdapter();
        }
        return adapter;
    }

    @Override
    public boolean hasMore() {
        return getPresenter().hasMore();
    }

    public RvViewHelper getRvViewHelper() {
        return viewHelper;
    }

    public EasyRecyclerView getEasyRecyclerView() {
        return viewHelper.getEasyRecyclerView();
    }

    public RecyclerView getRecyclerView() {
        return viewHelper.getEasyRecyclerView().getRecyclerView();
    }

    @Override
    protected int getContentLayoutRes() {
        int contentLayoutRes = super.getContentLayoutRes();
        if (contentLayoutRes > 0) {
            return contentLayoutRes;
        }
        return R.layout.common_easy_recycler_view;
    }

    protected abstract RecyclerView.Adapter createAdapter();
}
