package com.linewell.archive.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linewell.archive.R;
import com.linewell.archive.fragment.TestFragmentContract;
import com.linewell.archive.fragment.TestFragmentPresenter;
import com.linewell.archive.fragment.TestMvpFragment;
import com.linewell.core.common.ContentLayout;
import com.linewell.mvp.ActionBarActivity;
import com.linewell.support.viewpager.FragmentPagerAdapterEx;
import com.linewell.support.viewpager.FragmentStatePagerAdapterEx;
import com.linewell.support.viewpager.PagerAdapterEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@ContentLayout(R.layout.activity_viewpager_adapter)
public class TestViewPagerAdapterActivity extends ActionBarActivity<TestFragmentContract.V, TestFragmentContract.P>
        implements TestFragmentContract.V {

    public static void start(Context context) {
        Intent starter = new Intent(context, TestViewPagerAdapterActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected TestFragmentContract.P createPresenter(Bundle savedInstanceState) {
        return new TestFragmentPresenter(new TestFragmentContract.M() {
        });
    }

    private TabLayout tab_layout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tab_layout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);
        final ViewAdapter adapter = new ViewAdapter();
//        final Adapter adapter = new Adapter(getSupportFragmentManager());
//        final StateAdapter adapter = new StateAdapter(getSupportFragmentManager());
        adapter.setCount(1);
        viewPager.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewPager);

        findViewById(R.id.flush).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setCount(1/*new Random().nextInt(5)*/);
                adapter.flush();
            }
        });
        findViewById(R.id.notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setCount(new Random().nextInt(10));
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class Adapter extends FragmentPagerAdapterEx {
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        private List<String> titles = new ArrayList<>();
        private int count;

        public void setCount(int count) {
            this.count = count;
            titles.clear();
            Random random = new Random(System.currentTimeMillis());
            for (int i = 0; i < count; i++) {
                titles.add("title" + random.nextInt(20));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return TestMvpFragment.newInstance((String) getPageTitle(position));
//            return TestMvpLazyFragment.newInstance((String) getPageTitle(position));
        }

        @Override
        public int getCount() {
            return count;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    private class StateAdapter extends FragmentStatePagerAdapterEx {
        public StateAdapter(FragmentManager fm) {
            super(fm);
        }

        private List<String> titles = new ArrayList<>();
        private int count;

        public void setCount(int count) {
            this.count = count;
            titles.clear();
            Random random = new Random(System.currentTimeMillis());
            for (int i = 0; i < count; i++) {
                titles.add("title" + random.nextInt(20));
            }
        }

        @Override
        public Fragment getItem(int position) {
            return TestMvpFragment.newInstance((String) getPageTitle(position));
//            return TestMvpLazyFragment.newInstance((String) getPageTitle(position));
        }

        @Override
        public int getCount() {
            return count;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    private class ViewAdapter extends PagerAdapterEx {
        private List<String> titles = new ArrayList<>();
        private int count;

        public ViewAdapter() {
        }

        public void setCount(int count) {
            this.count = count;
            titles.clear();
            Random random = new Random(System.currentTimeMillis());
            for (int i = 0; i < count; i++) {
                titles.add("title" + random.nextInt(20));
            }
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

        @NonNull
        @Override
        public Object onInstantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(TestViewPagerAdapterActivity.this);
            View itemView = layoutInflater.inflate(R.layout.item_header, container, false);
            TextView title = ((TextView) itemView.findViewById(R.id.title));
            title.setText(getPageTitle(position));
            ((ViewGroup) container).addView(itemView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            return itemView;
        }

        @Override
        public void onDestroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return count;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
