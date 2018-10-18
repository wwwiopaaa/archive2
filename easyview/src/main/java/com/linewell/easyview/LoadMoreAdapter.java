package com.linewell.easyview;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.linewell.support.recycler.WrapperAdapter;
import com.linewell.support.recycler.section.WrapperUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.linewell.easyview.LoadMoreAdapter.LoadMoreState.STATS_NONE;

/**
 * @describe </br>
 * Created by chenjl on 2017/4/17.
 */
public class LoadMoreAdapter extends WrapperAdapter {

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }

    }

    public interface OnLoadListener {
        void onRetry();

        void onLoadMore();
    }

    public static abstract class LoadMoreView {

        public interface Factory {
            LoadMoreView create(Context context);
        }

        private
        @LoadMoreAdapter.LoadMoreState
        int mState = LoadMoreAdapter.LoadMoreState.STATS_IDLE;

        protected OnLoadListener mLoadListener = null;
        protected Context mContext;

        public LoadMoreView(Context context) {
            mContext = context;
        }

        public void setListener(OnLoadListener listener) {
            mLoadListener = listener;
        }

        public abstract View getContentView();

        @CallSuper
        public void notifyState(@LoadMoreState int state) {
            mState = state;
        }

        @CallSuper
        public @LoadMoreState
        int getCurrentState() {
            return mState;
        }

        public final void refresh() {
            notifyState(mState);
        }
    }

    @IntDef({LoadMoreState.STATS_ERROR,
            LoadMoreState.STATS_NO_MORE,
            LoadMoreState.STATS_LOADING,
            LoadMoreState.STATS_IDLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMoreState {
        int STATS_NONE = 0;
        int STATS_ERROR = STATS_NONE + 1;
        int STATS_NO_MORE = STATS_ERROR + 1;
        int STATS_LOADING = STATS_NO_MORE + 1;
        int STATS_IDLE = STATS_LOADING + 1;
    }

    public static final int LOAD_MORE_VIEW_TYPE = 0x669699;

    private OnLoadListener mOnLoadListener;
    private LoadMoreView mLoadMoreView;
    private boolean isEnable = false;

    public LoadMoreAdapter(@NonNull LoadMoreView loadMoreView, @NonNull RecyclerView.Adapter adapter) {
        super(adapter);
        mLoadMoreView = loadMoreView;
        mLoadMoreView.setListener(mOnInnerLoadListener);
    }

    public void showLoading() {
        if (isLoadMoreEnable() && mLoadMoreView != null) {
            mLoadMoreView.notifyState(LoadMoreState.STATS_LOADING);
        }
    }

    public void showError() {
        if (isLoadMoreEnable() && mLoadMoreView != null) {
            mLoadMoreView.notifyState(LoadMoreState.STATS_ERROR);
        }
    }

    public void showNoMore() {
        if (isLoadMoreEnable() && mLoadMoreView != null) {
            mLoadMoreView.notifyState(LoadMoreState.STATS_NO_MORE);
        }
    }

    public void showIdle() {
        if (isLoadMoreEnable() && mLoadMoreView != null) {
            mLoadMoreView.notifyState(LoadMoreState.STATS_IDLE);
        }
    }

    public int getCurrentState() {
        if(!isLoadMoreEnable()) {
            return STATS_NONE;
        }
        return mLoadMoreView.getCurrentState();
    }

    public void setLoadMoreEnable(boolean enable) {
        final boolean preEnable = isEnable;
        this.isEnable = enable;
        if (preEnable != enable) {
            showIdle();
            notifyDataSetChanged();
        }
    }

    public boolean isLoadMoreEnable() {
        return isEnable;
    }

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
    }

    public boolean isLoadMorePosition(int position) {
        return isLoadMoreEnable() && position == getItemCount() - 1;
    }

    @Override
    public final int getItemViewType(int position) {
        if (isLoadMorePosition(position)) {
            return LOAD_MORE_VIEW_TYPE;
        }

        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        if (isLoadMorePosition(position)) {
            return RecyclerView.NO_ID;
        }
        return super.getItemId(position);
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOAD_MORE_VIEW_TYPE) {
            View contentView = mLoadMoreView.getContentView();
            return new LoadMoreViewHolder(contentView);
        }

        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreViewHolder) {
            if (mLoadMoreView != null) {
                mLoadMoreView.refresh();
            }
            return;
        }

        super.onBindViewHolder(holder, position);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof LoadMoreViewHolder) {
            return;
        }
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        if (holder instanceof LoadMoreViewHolder) {
            return super.onFailedToRecycleView(holder);
        }
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof LoadMoreViewHolder) {
            return;
        }
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof LoadMoreViewHolder) {
            WrapperUtils.setFullSpan(holder);
            return;
        }
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(loadMoreListener);
        recyclerView.setOnTouchListener(loadMoreListener);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(loadMoreListener);
        recyclerView.setOnTouchListener(null);
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (isLoadMoreEnable() ? 1 : 0);
    }

    private LoadMoreListener loadMoreListener = new LoadMoreListener() {
        @Override
        public void loadMore() {
            if (mOnInnerLoadListener != null && mLoadMoreView != null && isLoadMoreEnable()) {
                @LoadMoreState int currentStats = mLoadMoreView.getCurrentState();
                if (currentStats != LoadMoreState.STATS_LOADING
                        && currentStats != LoadMoreState.STATS_NO_MORE) {
                    mOnInnerLoadListener.onLoadMore();
                }
            }
        }
    };

    private OnLoadListener mOnInnerLoadListener = new OnLoadListener() {
        @Override
        public void onRetry() {
            showLoading();
            if (mOnLoadListener != null) {
                mOnLoadListener.onRetry();
            }
        }

        @Override
        public void onLoadMore() {
            showLoading();
            if (mOnLoadListener != null) {
                mOnLoadListener.onLoadMore();
            }
        }
    };
}
