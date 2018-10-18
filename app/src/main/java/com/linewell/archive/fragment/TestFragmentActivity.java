package com.linewell.archive.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.linewell.archive.R;
import com.linewell.support.viewpager.FragmentPagerAdapterEx;
import com.linewell.core.common.ContentLayout;
import com.linewell.mvp.ActionBarActivity;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@ContentLayout(R.layout.activity_fragment)
public class TestFragmentActivity extends ActionBarActivity<TestFragmentContract.V, TestFragmentContract.P>
        implements TestFragmentContract.V {

    public static void start(Context context) {
        Intent starter = new Intent(context, TestFragmentActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected TestFragmentContract.P createPresenter(Bundle savedInstanceState) {
        return new TestFragmentPresenter(new TestFragmentContract.M() {
        }) {
            @Override
            public void doOnCreate() {
                Log.e("lifecycle", "doOnCreate");
            }

            @Override
            public void doOnStart() {
                Log.e("lifecycle", "doOnStart");
            }

            @Override
            public void doOnResume() {
                Log.e("lifecycle", "doOnResume");
            }

            @Override
            public void doOnPause() {
                Log.e("lifecycle", "doOnPause");
            }

            @Override
            public void doOnStop() {
                Log.e("lifecycle", "doOnStop");
            }

            @Override
            public void doOnDestroy() {
                Log.e("lifecycle", "doOnDestroy");
            }
        };
    }

    private TabLayout tab_layout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tab_layout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);

        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        tab_layout.setupWithViewPager(viewPager);
        Log.e("lifecycle", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("lifecycle", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("lifecycle", "onResume");
    }

    @Override
    protected void onPause() {
        Log.e("lifecycle", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e("lifecycle", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("lifecycle", "onDestroy");
        super.onDestroy();
    }

    private class Adapter extends FragmentPagerAdapterEx {
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TestMvpFragment();
                case 1:
                    return new TestMvpLazyFragment();
                case 2:
                    return TestEvFragment.newInstance();
                case 3:
                    return TestRvFragment.newInstance();
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Mvp";
                case 1:
                    return "MvpLazy";
                case 2:
                    return "Ev";
                case 3:
                    return "Rv";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

}
