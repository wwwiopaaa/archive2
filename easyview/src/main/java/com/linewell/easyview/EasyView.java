package com.linewell.easyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * @describe </br>
 * Created by chenjl on 2017/4/15.
 */
public class EasyView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {

    protected ViewGroup mContentView;
    protected ViewGroup mProgressView;
    protected ViewGroup mEmptyView;
    protected ViewGroup mErrorView;
    protected TextView mTipView;

    protected OnRefreshListener mRefreshListener;

    public EasyView(Context context) {
        this(context, null);
    }

    public EasyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EasyView);
        try {
            boolean enableSwipeRefresh = a.getBoolean(R.styleable.EasyView_enableSwipeRefresh, false);
            setEnabled(enableSwipeRefresh);
            //tip
            int tipBackgroundColor = a.getColor(R.styleable.EasyView_tip_background, Color.BLACK);
            int tipColor = a.getColor(R.styleable.EasyView_tip_textColor, Color.WHITE);
            mTipView = createTipView();
            setTipViewBackgroundColor(tipBackgroundColor);
            setTipViewTextColor(tipColor);
            addView(mTipView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //content
            mContentView = createContentView();
            addView(mContentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            int contentLayoutId = a.getResourceId(R.styleable.EasyView_layout_content, 0);
            if (contentLayoutId > 0) {
                LayoutInflater.from(getContext()).inflate(contentLayoutId, mContentView);
            }

            //progress
            mProgressView = createProgressView();
            addView(mProgressView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            int progressLayoutId = a.getResourceId(R.styleable.EasyView_layout_progress, 0);
            if (progressLayoutId > 0) {
                LayoutInflater.from(getContext()).inflate(progressLayoutId, mProgressView);
            }
            //error
            mErrorView = createErrorView();
            addView(mErrorView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            int errorLayoutId = a.getResourceId(R.styleable.EasyView_layout_error, 0);
            if (errorLayoutId > 0) {
                LayoutInflater.from(getContext()).inflate(errorLayoutId, mErrorView);
            }
            //empty
            mEmptyView = createEmptyView();
            addView(mEmptyView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            int emptyLayoutId = a.getResourceId(R.styleable.EasyView_layout_empty, 0);
            if (emptyLayoutId > 0) {
                LayoutInflater.from(getContext()).inflate(emptyLayoutId, mEmptyView);
            }
        } finally {
            a.recycle();
        }

        setOnRefreshListener(this);
        hideAllView();
    }

    @Override
    public void onRefresh() {
        if (mRefreshListener != null) {
            mRefreshListener.onRefresh();
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mContentView != null && mContentView.getVisibility() != GONE) {
            measureChild(mContentView, widthMeasureSpec, heightMeasureSpec);
        }
        if (mProgressView != null && mProgressView.getVisibility() != GONE) {
            measureChild(mProgressView, widthMeasureSpec, heightMeasureSpec);
        }
        if (mErrorView != null && mErrorView.getVisibility() != GONE) {
            measureChild(mErrorView, widthMeasureSpec, heightMeasureSpec);
        }
        if (mEmptyView != null && mEmptyView.getVisibility() != GONE) {
            measureChild(mEmptyView, widthMeasureSpec, heightMeasureSpec);
        }
        if (mTipView != null && mTipView.getVisibility() != GONE) {
            measureChild(mTipView, widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        if (mContentView != null && mContentView.getVisibility() != GONE) {
            mContentView.layout(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
        }
        if (mProgressView != null && mProgressView.getVisibility() != GONE) {
            mProgressView.layout(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
        }
        if (mErrorView != null && mErrorView.getVisibility() != GONE) {
            mErrorView.layout(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
        }
        if (mEmptyView != null && mEmptyView.getVisibility() != GONE) {
            mEmptyView.layout(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
        }
        if (mTipView != null && mTipView.getVisibility() != GONE) {
            mTipView.layout(paddingLeft, paddingTop, width - paddingRight, paddingTop + mTipView.getMeasuredHeight());
        }
    }

    protected ViewGroup createContentView() {
        NestedScrollView nestedScrollView = new NestedScrollView(getContext());
        nestedScrollView.setFillViewport(true);
        return nestedScrollView;
    }

    protected ViewGroup createProgressView() {
        NestedScrollView nestedScrollView = new NestedScrollView(getContext());
        nestedScrollView.setFillViewport(true);
        return nestedScrollView;
    }

    protected ViewGroup createErrorView() {
        NestedScrollView nestedScrollView = new NestedScrollView(getContext());
        nestedScrollView.setFillViewport(true);
        return nestedScrollView;
    }

    protected ViewGroup createEmptyView() {
        NestedScrollView nestedScrollView = new NestedScrollView(getContext());
        nestedScrollView.setFillViewport(true);
        return nestedScrollView;
    }

    protected TextView createTipView() {
        TextView tipView = new TextView(getContext());
        int padding = dp2px(getContext(), 6);
        tipView.setPadding(padding, padding, padding, padding);
        tipView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        if (Build.VERSION.SDK_INT >= 17) {
            tipView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        }
        tipView.setGravity(Gravity.CENTER);
        tipView.setVisibility(GONE);
        return tipView;
    }

    protected void setTipViewTextColor(@ColorInt int mTipColor) {
        mTipView.setTextColor(mTipColor);
    }

    protected void setTipViewBackgroundColor(@ColorInt int backgroundColor) {
        mTipView.setBackgroundColor(backgroundColor);
    }

    public void hideAllView() {
        mProgressView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
        mContentView.setVisibility(INVISIBLE);
        setRefreshing(false);
    }

    private void showTipView(boolean anim) {
        if (anim) {
            Animation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
            mShowAction.setDuration(300);
            mTipView.startAnimation(mShowAction);
        }
        mTipView.setVisibility(View.VISIBLE);
        mTipView.bringToFront();
    }

    private void hidTipView(boolean anim) {
        if (anim) {
            Animation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    -1.0f);
            mHiddenAction.setDuration(300);
            mTipView.startAnimation(mHiddenAction);
        }
        mTipView.setVisibility(View.GONE);
    }

    private boolean isVisibleTipView() {
        return mTipView.getVisibility() == View.VISIBLE;
    }

    public void showTip(CharSequence msg, boolean anim) {
        mTipView.removeCallbacks(mTipDelayCloseAction);
        mTipView.setText(msg);
        if (!isVisibleTipView()) {
            mTipView.clearAnimation();
            showTipView(anim);
        } else {
            mTipView.bringToFront();
        }
    }

    public void hideTip(boolean anim) {
        mTipView.removeCallbacks(mTipDelayCloseAction);
        mTipView.setText("");
        if (isVisibleTipView()) {
            mTipView.clearAnimation();
            hidTipView(anim);
        }
    }

    public void showTipAndDelayClose(CharSequence msg, long delayMillis) {
        showTip(msg, true);

        mTipView.removeCallbacks(mTipDelayCloseAction);
        mTipView.postDelayed(mTipDelayCloseAction, delayMillis);
    }

    private Runnable mTipDelayCloseAction = new Runnable() {
        @Override
        public void run() {
            hideTip(true);
        }
    };

    public void showProgressView() {
        if (mProgressView.getChildCount() > 0) {
            hideAllView();
            mProgressView.setVisibility(VISIBLE);
            mProgressView.bringToFront();
            setTarget(this, mProgressView);
            setOnChildScrollUpCallback(new OnChildScrollUpCallback() {
                @Override
                public boolean canChildScrollUp(@NonNull SwipeRefreshLayout swipeRefreshLayout, @Nullable View view) {
                    return true;//在progress页面时不可下拉刷新
                }
            });
        } else {
            showContentView();
        }
    }

    public boolean currentViewIsProgressView() {
        return mProgressView.getVisibility() == VISIBLE;
    }

    public void showErrorView() {
        if (mErrorView.getChildCount() > 0) {
            hideAllView();
            mErrorView.setVisibility(VISIBLE);
            mErrorView.bringToFront();
            setTarget(this, mErrorView);
            setOnChildScrollUpCallback(null);
        } else {
            showContentView();
        }
    }

    public boolean currentViewIsErrorView() {
        return mErrorView.getVisibility() == VISIBLE;
    }

    public void showContentView() {
        hideAllView();
        mContentView.setVisibility(VISIBLE);
        mContentView.bringToFront();
        setTarget(this, mContentView);
        setOnChildScrollUpCallback(null);
    }

    public boolean currentViewIsContentView() {
        return mContentView.getVisibility() == VISIBLE;
    }

    public void showEmptyView() {
        hideAllView();
        mEmptyView.setVisibility(VISIBLE);
        mEmptyView.bringToFront();
        setTarget(this, mEmptyView);
        setOnChildScrollUpCallback(null);
    }

    public void setEmptyView(View view) {
        mEmptyView.removeAllViews();
        mEmptyView.addView(view);
    }

    public void setEmptyView(@LayoutRes int layoutRes) {
        mEmptyView.removeAllViews();
        LayoutInflater.from(getContext()).inflate(layoutRes, mEmptyView);
    }

    public void setProgressView(View view) {
        mProgressView.removeAllViews();
        mProgressView.addView(view);
    }

    public void setProgressView(@LayoutRes int layoutRes) {
        mProgressView.removeAllViews();
        LayoutInflater.from(getContext()).inflate(layoutRes, mProgressView);
    }

    public View getProgressView() {
        return mProgressView;
    }

    public void setErrorView(View view) {
        mErrorView.removeAllViews();
        mErrorView.addView(view);
    }

    public void setErrorView(@LayoutRes int layoutRes) {
        mErrorView.removeAllViews();
        LayoutInflater.from(getContext()).inflate(layoutRes, mErrorView);
    }

    public View getErrorView() {
        return mErrorView;
    }

    public void setContentView(View view) {
        mContentView.removeAllViews();
        mContentView.addView(view);
    }

    public void setContentView(@LayoutRes int layoutRes) {
        mContentView.removeAllViews();
        LayoutInflater.from(getContext()).inflate(layoutRes, mContentView);
    }

    public View getContentView() {
        return mContentView;
    }

    public void setRefreshListener(OnRefreshListener listener) {
        mRefreshListener = listener;
    }

    public void setManualRefreshEnable(boolean enable) {
        setEnabled(enable);
        setRefreshing(false);
    }

    public static int dp2px(Context context, float dpValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return (int) (dpValue * dm.density + 0.5f);
    }

    protected void clearTarget(SwipeRefreshLayout swipeRefreshLayout) {
        setTarget(swipeRefreshLayout, null);
    }

    protected void setTarget(SwipeRefreshLayout swipeRefreshLayout, View target) {
        try {
            Field targetField = SwipeRefreshLayout.class.getDeclaredField("mTarget");
            targetField.setAccessible(true);
            targetField.set(swipeRefreshLayout, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
