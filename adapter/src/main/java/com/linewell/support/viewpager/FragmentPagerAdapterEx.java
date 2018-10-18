package com.linewell.support.viewpager;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.text.TextUtils;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/09/11
 *     desc   : 增强版FragmentPagerAdapter
 *     version: 1.0
 * </pre>
 */
public abstract class FragmentPagerAdapterEx extends FragmentPagerAdapter {

    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;
    private SparseArrayCompat<String> mTagCache = new SparseArrayCompat();
    private List<String> mFlushTags = new ArrayList<>();

    public FragmentPagerAdapterEx(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentFragment = (Fragment) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mTagCache.put(position, fragment.getTag());
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mTagCache.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getCurrent() {
        return mCurrentFragment;
    }

    public Fragment getFragmentForPosition(int position) {
        String tag = (String) mTagCache.get(position);
        if (!TextUtils.isEmpty(tag)) {
            return mFragmentManager.findFragmentByTag(tag);
        }
        return null;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        final String tag = ((Fragment) object).getTag();
        if (mFlushTags.contains(tag)) {
            mFlushTags.remove(tag);
            return POSITION_NONE;
        }

        if (mTagCache.isEmpty()) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    public void flush() {
        mFlushTags.clear();
        for(int i = 0;i < mTagCache.size(); i++) {
            String tag = mTagCache.valueAt(i);
            if (!mFlushTags.contains(tag)) {
                mFlushTags.add(tag);
            }
        }
        notifyDataSetChanged();
    }
}