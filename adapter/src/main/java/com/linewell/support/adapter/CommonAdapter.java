package com.linewell.support.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;

    public CommonAdapter(final Context context, final int layoutId) {
        super(context);
        mContext = context;
        mLayoutId = layoutId;

        addItemViewDelegate(new ItemViewDelegate<T>() {

            @Override
            public View getItemViewLayout(Context context, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(layoutId, parent, false);
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);


}
