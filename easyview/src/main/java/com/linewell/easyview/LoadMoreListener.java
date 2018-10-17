package com.linewell.easyview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;

public abstract class LoadMoreListener extends RecyclerView.OnScrollListener implements View.OnTouchListener {

    private int previousTotal;
    private boolean isLoading = true;
    private LinearLayoutManager lm;
    private StaggeredGridLayoutManager sm;
    private int[] lastPositions;
    private int totalItemCount;
    private int lastVisibleItemPosition;

    private View.OnTouchListener oldOnTouchListener;

    public LoadMoreListener() {
    }

    public void setOldOnTouchListener(View.OnTouchListener oldOnTouchListener) {
        this.oldOnTouchListener = oldOnTouchListener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager)
            lm = (LinearLayoutManager) recyclerView.getLayoutManager();
        else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            sm = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            lastPositions = sm.findLastVisibleItemPositions(null);
        }

        int visibleItemCount = recyclerView.getChildCount();
        if (lm != null) {
            totalItemCount = lm.getItemCount();
            lastVisibleItemPosition = lm.findLastVisibleItemPosition();
        } else if (sm != null) {
            totalItemCount = sm.getItemCount();
            lastVisibleItemPosition = lastPositions[0];
        }

        if (isLoading) {
            if (totalItemCount > previousTotal) {//加载更多结束
                isLoading = false;
                previousTotal = totalItemCount;
            } else if (totalItemCount < previousTotal) {//用户刷新结束
                previousTotal = totalItemCount;
                isLoading = false;
            } else {//有可能是在第一页刷新也可能加是载完毕

            }
        }
        if (allowLoadMore() && !isLoading && visibleItemCount > 0 && totalItemCount - 1 == lastVisibleItemPosition
                && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            loadMore();
        }

    }

    private boolean allowLoadMore() {
        return mDurationMotionY < 0;
    }

    private float mLastMotionY = 0;
    private float mDurationMotionY = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int pointerIndex = event.findPointerIndex(event.getPointerId(0));
        if (pointerIndex < 0) {
            return oldOnTouchListener != null ? oldOnTouchListener.onTouch(v, event) : false;
        }
        float y = event.getY(pointerIndex);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastMotionY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                mDurationMotionY = y - mLastMotionY;
                mLastMotionY = y;
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                break;
        }
        return oldOnTouchListener != null ? oldOnTouchListener.onTouch(v, event) : false;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    public abstract void loadMore();
}