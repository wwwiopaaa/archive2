package com.linewell.support.recycler.section;

import android.support.v4.util.Pair;

import com.linewell.support.recycler.ItemViewDelegate;
import com.linewell.support.recycler.ViewHolder;

public abstract class SectionItemViewDelegate<T> implements ItemViewDelegate<T> {

    private SectionAdapter mSectionAdapter;

    public SectionItemViewDelegate(SectionAdapter sectionAdapter) {
        this.mSectionAdapter = sectionAdapter;
    }

    @Override
    public final boolean isForViewType(T item, int position) {
        Pair<Section, Integer> sectionForPosition = mSectionAdapter.getSectionForPositionWithoutHeader(position);
        return isForViewType(item, sectionForPosition.first, sectionForPosition.second);
    }

    @Override
    public final void convert(ViewHolder holder, T o, int position) {
        Pair<Section, Integer> sectionForPosition = mSectionAdapter.getSectionForPositionWithoutHeader(position);
        convert(holder, sectionForPosition.first, sectionForPosition.second);
    }

    public abstract void convert(ViewHolder holder, Section<T> section, int positionInSection);

    public abstract boolean isForViewType(T item, Section<T> section, int positionInSection);

}
