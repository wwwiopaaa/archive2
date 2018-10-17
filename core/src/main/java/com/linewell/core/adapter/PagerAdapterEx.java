package com.linewell.core.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

/**
 * Description:
 * Created by chenjl on 2017/11/28.
 */
public abstract class PagerAdapterEx extends PagerAdapter {

    private Object mCurrentObject;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentObject = object;
        super.setPrimaryItem(container, position, object);
    }

    public Object getCurrentObject() {
        return mCurrentObject;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

}
