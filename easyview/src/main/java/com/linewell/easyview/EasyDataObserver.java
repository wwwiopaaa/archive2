package com.linewell.easyview;

import android.support.v7.widget.RecyclerView;

public class EasyDataObserver extends RecyclerView.AdapterDataObserver {
    private RecyclerView.Adapter mAdapter;
    private EasyRecyclerView mEasyRecyclerView;

    public EasyDataObserver(EasyRecyclerView easyRecyclerView, RecyclerView.Adapter adapter) {
        mEasyRecyclerView = easyRecyclerView;
        this.mAdapter = adapter;
    }

    private boolean isLoadMorePosition(int position) {
        if (mAdapter != null && mAdapter instanceof LoadMoreAdapter) {
            LoadMoreAdapter loadMoreAdapter = (LoadMoreAdapter) mAdapter;
            return loadMoreAdapter.isLoadMorePosition(position);
        }
        return false;
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        if (!isLoadMorePosition(positionStart)) {
            update();
        }
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        if (!isLoadMorePosition(positionStart)) {
            update();
        }
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        if (!isLoadMorePosition(positionStart)) {
            update();
        }
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        update();
    }

    @Override
    public void onChanged() {
        super.onChanged();
        update();
    }

    private void update() {
        if (mAdapter != null && mAdapter instanceof LoadMoreAdapter) {
            LoadMoreAdapter loadMoreAdapter = (LoadMoreAdapter) this.mAdapter;
            RecyclerView.Adapter wrapperAdapter = loadMoreAdapter.getWrapperAdapter();
            int count = wrapperAdapter != null ? wrapperAdapter.getItemCount() : 0;
            if (count <= 0 && (!loadMoreAdapter.isLoadMoreEnable() || loadMoreAdapter.getCurrentState() == LoadMoreAdapter.LoadMoreState.STATS_NO_MORE)) {
                mEasyRecyclerView.showEmptyView();
            } else {
                mEasyRecyclerView.showContentView();
            }
        } else {
            int count = mAdapter != null ? mAdapter.getItemCount() : 0;
            if (count <= 0) {
                mEasyRecyclerView.showEmptyView();
            } else {
                mEasyRecyclerView.showContentView();
            }
        }


    }
}