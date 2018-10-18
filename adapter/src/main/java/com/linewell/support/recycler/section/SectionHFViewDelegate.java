package com.linewell.support.recycler.section;

import android.support.v4.util.Pair;

import com.linewell.support.recycler.ItemViewDelegate;
import com.linewell.support.recycler.ViewHolder;

public abstract class SectionHFViewDelegate<T> implements ItemViewDelegate<T> {

    /*package*/ SectionAdapter mSectionAdapter;

    @Override
    public final boolean isForViewType(T item, int position) {
        Pair<Section, Integer> sectionForPosition = mSectionAdapter.getSectionForPositionWithoutHeader(position);
        return isForViewType(sectionForPosition.first);
    }

    @Override
    public final void convert(ViewHolder holder, T o, int position) {
        Pair<Section, Integer> sectionForPosition = mSectionAdapter.getSectionForPositionWithoutHeader(position);
        convert(holder, sectionForPosition.first);
    }

    public abstract void convert(ViewHolder holder, Section section);

    public abstract boolean isForViewType(Section section);

}
