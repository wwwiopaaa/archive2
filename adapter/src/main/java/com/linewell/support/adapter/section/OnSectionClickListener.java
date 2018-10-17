package com.linewell.support.adapter.section;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.linewell.support.adapter.ItemClickSupport;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/08
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class OnSectionClickListener implements ItemClickSupport.OnItemClickListener {

    private SectionAdapter sectionAdapter;

    public OnSectionClickListener(SectionAdapter sectionAdapter) {
        this.sectionAdapter = sectionAdapter;
    }

    @Override
    public final void onItemClicked(RecyclerView recyclerView, View v, int position) {
        if (position >= sectionAdapter.getCount()) {
            return;
        }
        Pair<Section, Integer> sectionForPosition = sectionAdapter.getSectionForPosition(position);
        Section section = sectionForPosition.first;
        int positionInSection = sectionForPosition.second;
        if (Section.isFooter(section, positionInSection)) {
            onFooterClicked(recyclerView, v, section);
            return;
        }

        if (Section.isHeader(section, positionInSection)) {
            onHeaderClicked(recyclerView, v, section);
            return;
        }
        onItemClicked(recyclerView, v, section, positionInSection - (section.hasHeader() ? 1 : 0));
    }

    public void onItemClicked(RecyclerView recyclerView, View v, Section section, int position) {

    }

    public void onHeaderClicked(RecyclerView recyclerView, View v, Section section) {

    }

    public void onFooterClicked(RecyclerView recyclerView, View v, Section section) {

    }
}
