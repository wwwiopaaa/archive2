package com.linewell.support.adapter.section;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @describe </br>
 * Created by chenjl on 2017/8/21.
 */
public class SectionEx extends Section {

    private List<Pair<String, Section>> mSectionPairs = new ArrayList<>();

    public SectionEx(boolean hasHeader, boolean hasFooter) {
        super(hasHeader, hasFooter);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (Pair<String, Section> entry : mSectionPairs) {
            Section section = entry.second;
            count += section != null ? section.getSectionItemTotal() : 0;
        }
        return count;
    }

    @Override
    Object getItemByItemTotal(int position) {
        if (super.isHeader(position) || super.isFooter(position)) {
            return null;
        }

        int index = hasHeader() ? 1 : 0;
        for (Pair<String, Section> entry : mSectionPairs) {
            Section item = entry.second;

            final int sectionTotal = item.getSectionItemTotal();
            if (sectionTotal <= 0) {
                continue;
            }

            if (position >= index && position < (index + sectionTotal)) {
                return item.getItemByItemTotal(position - index);
            }
            index += sectionTotal;
        }

        throw new IndexOutOfBoundsException("Invalid position:" + position);
    }

    @Override
    public Object getItem(int position) {
        //do nothing
        return null;
    }

    @Override
    boolean isHeader(int position) {
        if (super.isHeader(position)) {
            return true;
        }
        if (super.isFooter(position)) {
            return false;
        }
        int index = hasHeader() ? 1 : 0;
        for (Pair<String, Section> entry : mSectionPairs) {
            Section item = entry.second;

            final int sectionTotal = item.getSectionItemTotal();
            if (sectionTotal <= 0) {
                continue;
            }

            if (position >= index && position < (index + sectionTotal)) {
                return item.isHeader(position - index);
            }
            index += sectionTotal;
        }

        throw new IndexOutOfBoundsException("Invalid position:" + position);
    }

    @Override
    boolean isFooter(int position) {
        if (super.isFooter(position)) {
            return true;
        }
        if (super.isHeader(position)) {
            return false;
        }
        int index = hasHeader() ? 1 : 0;
        for (Pair<String, Section> entry : mSectionPairs) {
            Section item = entry.second;

            final int sectionTotal = item.getSectionItemTotal();
            if (sectionTotal <= 0) {
                continue;
            }

            if (position >= index && position < (index + sectionTotal)) {
                return item.isFooter(position - index);
            }
            index += sectionTotal;
        }

        throw new IndexOutOfBoundsException("Invalid position:" + position);
    }

    public void add(@NonNull Section section) {
        add(generatedTag(section), section);
    }

    public void add(@NonNull final String tag, @NonNull Section section) {
        if (section == null) {
            throw new IllegalArgumentException("section can't null");
        }
        mSectionPairs.add(Pair.create(tag, section));
    }

    public void add(int index, @NonNull final String tag, @NonNull Section section) {
        if (section == null) {
            throw new IllegalArgumentException("section can't null");
        }
        mSectionPairs.add(index, Pair.create(tag, section));
    }

    public static String generatedTag(Section section) {
        return UUID.randomUUID().toString();
    }

    public boolean contains(@NonNull final String tag) {
        for (Pair<String, Section> entry : mSectionPairs) {
            String key = entry.first;
            if (TextUtils.equals(tag, key)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(@NonNull Section section) {
        for (Pair<String, Section> entry : mSectionPairs) {
            Section item = entry.second;
            if (item == section) {
                return true;
            }
        }
        return false;
    }

    public boolean remove(@NonNull Section section) {
        Iterator<Pair<String, Section>> iterator = mSectionPairs.iterator();
        while (iterator.hasNext()) {
            Pair<String, Section> next = iterator.next();
            Section item = next.second;
            if (item == section) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean remove(@NonNull final String tag) {
        Iterator<Pair<String, Section>> iterator = mSectionPairs.iterator();
        while (iterator.hasNext()) {
            Pair<String, Section> next = iterator.next();
            String key = next.first;
            if (TextUtils.equals(tag, key)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public void clear() {
        mSectionPairs.clear();
    }

    public Section get(@NonNull final String tag) {
        Iterator<Pair<String, Section>> iterator = mSectionPairs.iterator();
        while (iterator.hasNext()) {
            Pair<String, Section> next = iterator.next();
            String key = next.first;
            Section item = next.second;
            if (TextUtils.equals(tag, key)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 根据索引获取section
     */
    public Section get(int index) {
        if (index >= 0 && index < mSectionPairs.size()) {
            return mSectionPairs.get(index).second;
        }
        return null;
    }

    /**
     * 根据tag获取对应section的索引
     */
    public int index(@NonNull final String tag) {
        for (int i = 0; i < mSectionPairs.size(); i++) {
            if (TextUtils.equals(tag, mSectionPairs.get(i).first)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取对应section的索引
     */
    public int index(@NonNull Section section) {
        for (int i = 0; i < mSectionPairs.size(); i++) {
            if (section == mSectionPairs.get(i).second) {
                return i;
            }
        }
        return -1;
    }

    /*package*/
    static int findSectionPosition(SectionEx container, Section section) {
        if (section == container) {
            return 0;
        }
        int index = container.hasHeader() ? 1 : 0;
        for (Pair<String, Section> entry : container.mSectionPairs) {
            Section item = entry.second;
            if (section == item) {
                return index;
            }
            if (item instanceof SectionEx) {
                int sectionIndex = findSectionPosition((SectionEx) item, section);
                if (sectionIndex >= 0) {
                    return index + sectionIndex;
                }
            }
            index += item.getSectionItemTotal();
        }

        return -1;
    }

    /*package*/
    static Pair<Section, Integer> getSectionForPosition(Pair<Section, Integer> sectionIntegerPair) {
        final Integer position = sectionIntegerPair.second;
        final Section section = sectionIntegerPair.first;

        if (isHeader(section, position) || isFooter(section, position)) {
            return Pair.create(section, position);
        }
        if (!(section instanceof SectionEx)) {
            return Pair.create(section, position/* - (section.hasHeader() ? 1 : 0)*/);
        }
        int currentPos = section.hasHeader() ? 1 : 0;
        for (Pair<String, Section> entry : ((SectionEx) section).mSectionPairs) {
            Section item = entry.second;

            int sectionTotal = item.getSectionItemTotal();
            if (sectionTotal <= 0) {
                continue;
            }

            if (position >= currentPos && position < (currentPos + sectionTotal)) {
                final int positionInSection = position - currentPos;
                if (item instanceof SectionEx) {
                    return getSectionForPosition(Pair.create(item, positionInSection));
                }
                return Pair.create(item, positionInSection/* - (item.hasHeader() ? 1 : 0)*/);
            }

            currentPos += sectionTotal;
        }

        throw new IndexOutOfBoundsException("Invalid position:" + position);
    }
}