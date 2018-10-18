package com.linewell.support.recycler;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface ItemViewDelegate<T> {

    View getItemViewLayout(Context context, ViewGroup parent);

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
