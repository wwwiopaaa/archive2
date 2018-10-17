package com.linewell.core.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.SparseArrayCompat;
import android.text.TextUtils;
import android.view.ViewGroup;

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
    private SparseArrayCompat mTagCache = new SparseArrayCompat();

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
        super.destroyItem(container, position, object);
        mTagCache.remove(position);
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public Fragment getFragmentForPosition(int position) {
        String tag = (String) mTagCache.get(position);
        if (!TextUtils.isEmpty(tag)) {
            return mFragmentManager.findFragmentByTag(tag);
        }
        return null;
    }

//    public void flush() {
//        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//        final int count = getCount();
//        for (int i = 0; i < count; i++) {
//            Fragment fragment = getFragmentForPosition(i);
//            if (fragment != null) {
//                fragmentTransaction.remove(fragment);
//            }
//        }
//        fragmentTransaction.commit();
////        fragmentTransaction.commitNow();
//        mFragmentManager.executePendingTransactions();
//        notifyDataSetChanged();
//    }
}