package com.linewell.easyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * @describe </br>
 * Created by chenjl on 2017/4/16.
 */
public class EasyRecyclerView extends EasyView {

    protected RecyclerView mRecycler;

    private LoadMoreAdapter.OnLoadListener mOnInnerLoadListener;

    protected ArrayList<RecyclerView.OnScrollListener> mExternalOnScrollListeners = new ArrayList<>();

    protected boolean mEnableLoadMore;

    public EasyRecyclerView(Context context) {
        this(context, null);
    }

    public EasyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EasyRecyclerView);
        try {
            mEnableLoadMore = a.getBoolean(R.styleable.EasyRecyclerView_enableLoadMore, false);
            boolean mClipToPadding = a.getBoolean(R.styleable.EasyRecyclerView_recyclerClipToPadding, false);
            mRecycler.setClipToPadding(mClipToPadding);
            int mPadding = (int) a.getDimension(R.styleable.EasyRecyclerView_recyclerPadding, -1.0f);
            int mPaddingTop = (int) a.getDimension(R.styleable.EasyRecyclerView_recyclerPaddingTop, 0.0f);
            int mPaddingBottom = (int) a.getDimension(R.styleable.EasyRecyclerView_recyclerPaddingBottom, 0.0f);
            int mPaddingLeft = (int) a.getDimension(R.styleable.EasyRecyclerView_recyclerPaddingLeft, 0.0f);
            int mPaddingRight = (int) a.getDimension(R.styleable.EasyRecyclerView_recyclerPaddingRight, 0.0f);
            if (mPadding != -1.0f) {
                setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }
            int mScrollbarStyle = a.getInteger(R.styleable.EasyRecyclerView_scrollbarStyle, -1);
            if (mScrollbarStyle != -1) {
                mRecycler.setScrollBarStyle(mScrollbarStyle);
            }
            int mScrollbar = a.getInteger(R.styleable.EasyRecyclerView_scrollbars, -1);
            switch (mScrollbar) {
                case 0:
                    setVerticalScrollBarEnabled(false);
                    break;
                case 1:
                    setHorizontalScrollBarEnabled(false);
                    break;
                case 2:
                    setVerticalScrollBarEnabled(false);
                    setHorizontalScrollBarEnabled(false);
                    break;
            }
        } finally {
            a.recycle();
        }
        //setItemAnimator(null);
        mRecycler.setHasFixedSize(true);
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                for (RecyclerView.OnScrollListener listener : mExternalOnScrollListeners) {
                    listener.onScrollStateChanged(recyclerView, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                for (RecyclerView.OnScrollListener listener : mExternalOnScrollListeners) {
                    listener.onScrolled(recyclerView, dx, dy);
                }
            }
        });
    }

    @Override
    protected ViewGroup createContentView() {
        ViewGroup contentView = new FrameLayout(getContext());
        RecyclerView recyclerView = new RecyclerView(getContext());
        contentView.addView(recyclerView, MATCH_PARENT, MATCH_PARENT);
        mRecycler = recyclerView;
        return contentView;
    }

    @Override
    public void setContentView(View view) {
        throw new UnsupportedOperationException("EasyRecyclerView can't support operation setContentView()");
    }

    /**
     * 设置适配器，关闭所有副view。展示recyclerView
     * 适配器有更新，自动关闭所有副view。根据条数判断是否展示EmptyView
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (!(adapter instanceof LoadMoreAdapter)) {
            adapter = new LoadMoreAdapter(mFactory.create(getContext()), adapter);
        }
        LoadMoreAdapter loadMoreWrapper = (LoadMoreAdapter) adapter;
        loadMoreWrapper.setLoadMoreEnable(mEnableLoadMore);
        loadMoreWrapper.setOnLoadListener(mInnerLoadMoreListener);
        loadMoreWrapper.registerAdapterDataObserver(new EasyDataObserver(this, adapter));
        mRecycler.setAdapter(loadMoreWrapper);
        loadMoreWrapper.notifyDataSetChanged();
        showContentView();
    }

    private LoadMoreAdapter.LoadMoreView.Factory mFactory = new LoadMoreAdapter.LoadMoreView.Factory() {
        @Override
        public LoadMoreAdapter.LoadMoreView create(Context context) {
            return new DefaultLoadMoreView(context);
        }
    };

    public void setLoadMoreViewFactory(@NonNull LoadMoreAdapter.LoadMoreView.Factory factory) {
        mFactory = factory;
    }

    public void setLoadMoreEnable(boolean enable) {
        mEnableLoadMore = enable;
        RecyclerView.Adapter adapter = mRecycler.getAdapter();
        if (adapter != null && adapter instanceof LoadMoreAdapter) {
            ((LoadMoreAdapter) adapter).setLoadMoreEnable(enable);
        }
    }

    public boolean isEnableLoadMore() {
        return mEnableLoadMore;
    }

    LoadMoreAdapter.OnLoadListener mInnerLoadMoreListener = new LoadMoreAdapter.OnLoadListener() {
        @Override
        public void onRetry() {
            if (mOnInnerLoadListener != null) {
                mOnInnerLoadListener.onRetry();
            }
        }

        @Override
        public void onLoadMore() {
            if (mOnInnerLoadListener != null) {
                mOnInnerLoadListener.onLoadMore();
            }
        }
    };

    public void setOnLoadMoreListener(LoadMoreAdapter.OnLoadListener listener) {
        mOnInnerLoadListener = listener;
    }

    public void scrollToPosition(int position) {
        getRecyclerView().scrollToPosition(position);
    }

    /**
     * Set the layout manager to the recycler
     *
     * @param manager
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    RecyclerView.Adapter adapter = getRecyclerView().getAdapter();
                    if (adapter != null && adapter instanceof LoadMoreAdapter) {
                        if (((LoadMoreAdapter) adapter).isLoadMorePosition(position)) {
                            return gridLayoutManager.getSpanCount();
                        }
                    }
                    return 1;
                }
            });
        }
        mRecycler.setLayoutManager(manager);
    }

    @Override
    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        mRecycler.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
    }

    @Override
    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        mRecycler.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
    }

    /**
     * Add the onItemTouchListener for the recycler
     *
     * @param listener
     */
    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.addOnItemTouchListener(listener);
    }

    /**
     * Remove the onItemTouchListener for the recycler
     *
     * @param listener
     */
    public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.removeOnItemTouchListener(listener);
    }

    /**
     * Add scroll listener to the recycler
     *
     * @param listener
     */
    public void addOnScrollListener(RecyclerView.OnScrollListener listener) {
        if (!mExternalOnScrollListeners.contains(listener)) {
            mExternalOnScrollListeners.add(listener);
        }
    }

    /**
     * Remove scroll listener from the recycler
     *
     * @param listener
     */
    public void removeOnScrollListener(RecyclerView.OnScrollListener listener) {
        if (mExternalOnScrollListeners.contains(listener)) {
            mExternalOnScrollListeners.remove(listener);
        }
    }

    public RecyclerView getRecyclerView() {
        return mRecycler;
    }

    public void setOnTouchListener(OnTouchListener listener) {
        mRecycler.setOnTouchListener(listener);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecycler.setItemAnimator(animator);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecycler.addItemDecoration(itemDecoration, index);
    }

    public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.removeItemDecoration(itemDecoration);
    }

    public static class DefaultLoadMoreView extends LoadMoreAdapter.LoadMoreView {

        private TextView mTvContent;

        public DefaultLoadMoreView(Context context) {
            super(context);
            mTvContent = new TextView(mContext);
            mTvContent.setPadding(20, 20, 20, 20);
            mTvContent.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            mTvContent.setGravity(Gravity.CENTER);
            mTvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    @LoadMoreAdapter.LoadMoreState int currentStats = getCurrentState();
                    if (mLoadListener != null && currentStats != LoadMoreAdapter.LoadMoreState.STATS_LOADING
                            && currentStats != LoadMoreAdapter.LoadMoreState.STATS_NO_MORE) {
                        if (currentStats == LoadMoreAdapter.LoadMoreState.STATS_ERROR) {
                            mLoadListener.onRetry();
                        } else if (currentStats == LoadMoreAdapter.LoadMoreState.STATS_IDLE) {
                            mLoadListener.onLoadMore();
                        }
                    }
                }
            });
        }

        @Override
        public View getContentView() {
            return mTvContent;
        }

        @Override
        public void notifyState(@LoadMoreAdapter.LoadMoreState int state) {
            super.notifyState(state);
            switch (state) {
                case LoadMoreAdapter.LoadMoreState.STATS_IDLE:
                    mTvContent.setText(R.string.load_more_idle);
                    break;
                case LoadMoreAdapter.LoadMoreState.STATS_ERROR:
                    mTvContent.setText(R.string.load_more_err);
                    break;
                case LoadMoreAdapter.LoadMoreState.STATS_LOADING:
                    mTvContent.setText(R.string.load_more_loading);
                    break;
                case LoadMoreAdapter.LoadMoreState.STATS_NO_MORE:
                    mTvContent.setText(R.string.load_more_no_more);
                    break;
            }
        }

    }

}
