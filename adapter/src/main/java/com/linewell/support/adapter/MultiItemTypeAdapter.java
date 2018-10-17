package com.linewell.support.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    protected ItemViewDelegateManager mItemViewDelegateManager;

    public MultiItemTypeAdapter(Context context) {
        this(context, new ItemViewDelegateManager());
    }

    public MultiItemTypeAdapter(@NonNull Context context, @NonNull ItemViewDelegateManager itemViewDelegateManager) {
        mContext = context;
        mItemViewDelegateManager = itemViewDelegateManager;
    }

    @Override
    public int getItemViewType(int position) {
        return mItemViewDelegateManager.getItemViewType(getItem(position), position);
    }

    public abstract T getItem(int position);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        View layout = itemViewDelegate.getItemViewLayout(mContext, parent);
        ViewHolder holder = mItemViewDelegateManager.createViewHolder(mContext, layout);
        onViewHolderCreated(holder, holder.getConvertView());
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    public void convertView(ViewHolder holder, T t, int position) {
        mItemViewDelegateManager.convert(holder, t, position);
    }

    //protected boolean isEnabled(int viewType) {
//        return true;
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convertView(holder, getItem(position), position);
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }
}
