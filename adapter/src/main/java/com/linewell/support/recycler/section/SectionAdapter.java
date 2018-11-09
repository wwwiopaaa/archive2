package com.linewell.support.recycler.section;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.linewell.support.recycler.ItemViewDelegate;
import com.linewell.support.recycler.ItemViewDelegateManager;
import com.linewell.support.recycler.MultiItemTypeAdapter;
import com.linewell.support.recycler.ViewHolder;

/**
 * @describe </br>
 * Created by chenjl on 2017/4/19.
 */
public class SectionAdapter<T> extends MultiItemTypeAdapter<T> implements WrapperUtils.SpanSizeCallback{

    private static final int HEADER_TYPE_OFFSET = 10_000;
    private static final int FOOTER_TYPE_OFFSET = 100_000;

    public static class HeaderAndFooterViewHolder extends ViewHolder {
        public HeaderAndFooterViewHolder(Context context, View itemView) {
            super(context, itemView);
        }
    }

    public class HeaderAndFooterViewDelegateManager extends ItemViewDelegateManager {
        @Override
        public ViewHolder createViewHolder(Context context, View layout) {
            return new HeaderAndFooterViewHolder(context, layout);
        }
    }

    protected ItemViewDelegateManager mHeaderViewDelegateManager = new HeaderAndFooterViewDelegateManager();

    protected ItemViewDelegateManager mFooterViewDelegateManager = new HeaderAndFooterViewDelegateManager();

    private SectionEx mSectionEx = new SectionEx(false, false);

    public SectionAdapter(Context context) {
        super(context);
    }

    public MultiItemTypeAdapter addItemViewDelegate(SectionItemViewDelegate itemViewDelegate) {
        //itemViewDelegate.mSectionAdapter = this;
        return super.addItemViewDelegate(itemViewDelegate);
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, SectionItemViewDelegate itemViewDelegate) {
        //itemViewDelegate.mSectionAdapter = this;
        return super.addItemViewDelegate(viewType, itemViewDelegate);
    }

    public MultiItemTypeAdapter addHeaderViewDelegate(SectionHFViewDelegate itemViewDelegate) {
        final int type = HEADER_TYPE_OFFSET + mHeaderViewDelegateManager.getItemViewDelegateCount();
        mHeaderViewDelegateManager.addDelegate(type, itemViewDelegate);
        //itemViewDelegate.mSectionAdapter = this;
        return this;
    }

    public MultiItemTypeAdapter addFooterViewDelegate(SectionHFViewDelegate itemViewDelegate) {
        final int type = FOOTER_TYPE_OFFSET + mFooterViewDelegateManager.getItemViewDelegateCount();
        mFooterViewDelegateManager.addDelegate(type, itemViewDelegate);
        //itemViewDelegate.mSectionAdapter = this;
        return this;
    }

    public Pair<Section, Integer> getSectionForPositionWithoutHeader(int position) {
        Pair<Section, Integer> pair = SectionEx.getSectionForPosition(Pair.create((Section) mSectionEx, position));
        return Pair.create(pair.first, pair.second - (pair.first.hasHeader() ? 1 : 0));
    }

    public Pair<Section, Integer> getSectionForPosition(int position) {
        return SectionEx.getSectionForPosition(Pair.create((Section) mSectionEx, position));
    }

    @Deprecated
    @Override
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate itemViewDelegate) {
        return super.addItemViewDelegate(itemViewDelegate);
    }

    @Deprecated
    @Override
    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate itemViewDelegate) {
        return super.addItemViewDelegate(viewType, itemViewDelegate);
    }

    /**
     * 插入尾部
     */
    public void addSection(@NonNull final String tag, Section section, boolean notify) {
        mSectionEx.add(tag, section);
        if (notify) {
            int sectionItemsTotal = section.getSectionItemTotal();
            int sectionIndex = SectionEx.findSectionPosition(mSectionEx, section);
            notifyItemRangeInserted(sectionIndex, sectionItemsTotal);
        }
    }

    /**
     * 插入头部
     */
    public void insertSection(int index, @NonNull final String tag, Section section, boolean notify) {
        mSectionEx.add(index, tag, section);
        if (notify) {
            int sectionItemsTotal = section.getSectionItemTotal();
            int sectionStart = SectionEx.findSectionPosition(mSectionEx, section);
            notifyItemRangeInserted(sectionStart, sectionItemsTotal);
        }
    }

    public void addSection(Section section, boolean notify) {
        mSectionEx.add(section);
        if (notify) {
            int sectionItemsTotal = section.getSectionItemTotal();
            int sectionIndex = SectionEx.findSectionPosition(mSectionEx, section);
            notifyItemRangeInserted(sectionIndex, sectionItemsTotal);
        }
    }

    public void clearSection(boolean notify) {
        int total = mSectionEx.getSectionItemTotal();
        mSectionEx.clear();
        if (notify && total > 0) {
            notifyDataSetChanged();
        }
    }

    public Section getSection(@NonNull final String tag) {
        return mSectionEx.get(tag);
    }

    public boolean containsSection(Section section) {
        return mSectionEx.contains(section);
    }

    public boolean containsSection(@NonNull final String tag) {
        return mSectionEx.contains(tag);
    }

    public void removeSection(Section section, boolean notify) {
        int sectionIndex = SectionEx.findSectionPosition(mSectionEx, section);
        int sectionItemsTotal = section.getSectionItemTotal();
        mSectionEx.remove(section);
        if (notify) {
            notifyItemRangeRemoved(sectionIndex, sectionItemsTotal);
        }
    }

    public void removeSection(@NonNull final String tag, boolean notify) {
        Section section = mSectionEx.get(tag);
        if (section == null) {
            return;
        }
        int sectionIndex = SectionEx.findSectionPosition(mSectionEx, section);
        int sectionItemsTotal = section.getSectionItemTotal();
        mSectionEx.remove(section);
        if (notify) {
            notifyItemRangeRemoved(sectionIndex, sectionItemsTotal);
        }
    }

    public final int getCount() {
        return mSectionEx.getSectionItemTotal();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return mHeaderViewDelegateManager.getItemViewType(null, position);
        }
        if (isFooter(position)) {
            return mFooterViewDelegateManager.getItemViewType(null, position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate headerViewDelegate = mHeaderViewDelegateManager.getItemViewDelegate(viewType);
        if (headerViewDelegate != null) {
            View layout = headerViewDelegate.getItemViewLayout(mContext, parent);
            ViewHolder viewHolder = mHeaderViewDelegateManager.createViewHolder(mContext, layout);
            return viewHolder;
        }

        ItemViewDelegate footerViewDelegate = mFooterViewDelegateManager.getItemViewDelegate(viewType);
        if (footerViewDelegate != null) {
            View layout = footerViewDelegate.getItemViewLayout(mContext, parent);
            ViewHolder viewHolder = mFooterViewDelegateManager.createViewHolder(mContext, layout);
            return viewHolder;
        }

        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isHeader(position)) {
            mHeaderViewDelegateManager.convert(holder, null, position);
            return;
        } else if (isFooter(position)) {
            mFooterViewDelegateManager.convert(holder, null, position);
            return;
        }

        super.onBindViewHolder(holder, position);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        if (holder instanceof HeaderAndFooterViewHolder) {
            WrapperUtils.setFullSpan(holder);
            return;
        }
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        WrapperUtils.onDetachedToRecyclerView(recyclerView);
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        WrapperUtils.onAttachedToRecyclerView(recyclerView, this);
    }

    @Override
    public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
        if (position < 0 || position >= getItemCount()) {
            return oldLookup != null ? oldLookup.getSpanSize(position) : 1;
        }

        if (isHeader(position) || isFooter(position)) {
            return layoutManager.getSpanCount();
        }

        return oldLookup != null ? oldLookup.getSpanSize(position) : 1;
    }

    @Override
    public final T getItem(int position) {
        return (T) mSectionEx.getItemByItemTotal(position);
    }

    @Override
    public final int getItemCount() {
        return mSectionEx.getSectionItemTotal();
    }

    public final boolean isHeader(int position) {
        return mSectionEx.isHeader(position);
    }

    public final boolean isFooter(int position) {
        return mSectionEx.isFooter(position);
    }
}
