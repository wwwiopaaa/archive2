package com.linewell.support.viewpager;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
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
public abstract class FragmentStatePagerAdapterEx extends FragmentStatePagerAdapter {

    private Fragment mCurrentFragment;
    private SparseArrayCompat<Fragment> mFragmentCache = new SparseArrayCompat();
    private List<Fragment> mFlushFragments = new ArrayList<>();

    public FragmentStatePagerAdapterEx(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentFragment = (Fragment) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragmentCache.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        mFragmentCache.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getCurrent() {
        return mCurrentFragment;
    }

    public Fragment findFragmentByPosition(int position) {
        return mFragmentCache.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        final Fragment tag = ((Fragment) object);
        if (mFlushFragments.contains(tag)) {
            mFlushFragments.remove(tag);
            return POSITION_NONE;
        }

        if (mFragmentCache.isEmpty()) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    /**
     * 已实例化的Fragment将会被重新创建
     */
    public final void flush() {
        mFlushFragments.clear();
        for (int i = 0; i < mFragmentCache.size(); i++) {
            Fragment fragment = mFragmentCache.valueAt(i);
            if (!mFlushFragments.contains(fragment)) {
                mFlushFragments.add(fragment);
            }
        }

        notifyDataSetChanged();
    }
}