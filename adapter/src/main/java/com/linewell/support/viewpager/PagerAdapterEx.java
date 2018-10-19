package com.linewell.support.viewpager;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by chenjl on 2017/11/28.
 */
public abstract class PagerAdapterEx extends PagerAdapter {

    private Object mCurrentObject;
    private SparseArrayCompat<Object> mObjCache = new SparseArrayCompat<>();
    private List<Object> mFlushViews = new ArrayList<>();

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentObject = object;
        super.setPrimaryItem(container, position, object);
    }

    @NonNull
    @Override
    public final Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object obj = onInstantiateItem(container, position);
        mObjCache.put(position, obj);
        return obj;
    }

    @Override
    public final void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        mObjCache.remove(position);
        onDestroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (mFlushViews.contains(object)) {
            mFlushViews.remove(object);
            return POSITION_NONE;
        }

        if (mObjCache.isEmpty()) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    public Object getCurrent() {
        return mCurrentObject;
    }

    public Object findObjectByPosition(int position) {
        return mObjCache.get(position);
    }

    /**
     * 将重新执行destroyItem 和instantiateItem
     */
    public final void flush() {
        mFlushViews.clear();
        for(int i = 0; i < mObjCache.size(); i++) {
            Object obj = mObjCache.valueAt(i);
            if (!mFlushViews.contains(obj)) {
                mFlushViews.add(obj);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    public abstract Object onInstantiateItem(@NonNull ViewGroup container, int position);

    public abstract void onDestroyItem(@NonNull ViewGroup container, int position, @NonNull Object object);

}
