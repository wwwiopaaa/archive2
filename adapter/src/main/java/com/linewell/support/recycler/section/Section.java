package com.linewell.support.recycler.section;

/**
 * @describe </br>
 * Created by chenjl on 2017/8/21.
 */
public abstract class Section<T> {

    private boolean mHasHeader, mHasFooter;

    public Section() {
        this(false, false);
    }

    public Section(boolean hasHeader, boolean hasFooter) {
        mHasHeader = hasHeader;
        mHasFooter = hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        mHasFooter = hasFooter;
    }

    public void setHasHeader(boolean hasHeader) {
        mHasHeader = hasHeader;
    }

    public boolean hasFooter() {
        return mHasFooter;
    }

    public boolean hasHeader() {
        return mHasHeader;
    }

    public final int getSectionItemTotal() {
        return getItemCount() + (mHasHeader ? 1 : 0) + (mHasFooter ? 1 : 0);
    }

    T getItemByItemTotal(int position) {
        if (isHeader(position) || isFooter(position)) {
            return null;
        }
        return getItem(hasHeader() ? position - 1 : position);
    }

    public abstract int getItemCount();

    public abstract T getItem(int position);

    boolean isHeader(int position) {
        return isHeader(this, position);
    }

    boolean isFooter(int position) {
        return isFooter(this, position);
    }

    public static boolean isHeader(Section section, int position) {
        return section.hasHeader() && position == 0;
    }

    public static boolean isFooter(Section section, int position) {
        return section.hasFooter() && (section.getItemCount() + (section.hasHeader() ? 1 : 0)) == position;
    }
}