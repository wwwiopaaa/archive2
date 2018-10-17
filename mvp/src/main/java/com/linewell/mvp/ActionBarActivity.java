package com.linewell.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.linewell.core.utils.ObjectUtils;
import com.linewell.mvp.contract.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/09/30
 *     desc   : 带toolbar的activity
 *     version: 1.0
 * </pre>
 */
public abstract class ActionBarActivity<V extends Contract.V, P extends Contract.P<V>> extends MvpActivity<V, P> {

    private Toolbar mToolbar;
    private ViewGroup mContentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    private void setupToolbar() {
        if(mContentContainer == null) {
            super.setContentView(R.layout.common_activity_actionbar);
            mContentContainer = findViewById(R.id.ll_content_container);
        }

        if (mToolbar == null) {
            mToolbar = findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
    }

    @Override
    public void setContentView(int layoutResID) {
        this.setContentView(LayoutInflater.from(this).inflate(layoutResID, mContentContainer, false));
    }

    @Override
    public void setContentView(View view) {
        this.setContentView(view, null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        setupToolbar();
        //移除其他View除toolbar
        List<View> temp = new ArrayList<>();
        final int childCount = mContentContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = mContentContainer.getChildAt(i);
            if (child == mToolbar) {
                continue;
            }
            temp.add(child);
        }
        if (!ObjectUtils.isEmpty(temp)) {
            for (View itemView : temp) {
                mContentContainer.removeView(itemView);
            }
        }
        //添加新view
        if (params != null) {
            mContentContainer.addView(view, params);
        } else {
            mContentContainer.addView(view);
        }
        getWindow().getCallback().onContentChanged();
    }

    protected ViewGroup getContentContainer() {
        return mContentContainer;
    }

    protected Toolbar getSupportToolbar() {
        return mToolbar;
    }

    @Override
    protected void onDestroy() {
        //mContentContainer.removeAllViews();
        super.onDestroy();
    }
}
