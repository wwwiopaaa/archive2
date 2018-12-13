package com.linewell.archive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.linewell.archive.adapter.TestViewPagerAdapterActivity;
import com.linewell.archive.adapter.TestSectionActivity;
import com.linewell.archive.ev.TestEvActionBarActivity;
import com.linewell.archive.ev.TestEvActivity;
import com.linewell.archive.fragment.TestFragmentActivity;
import com.linewell.archive.net.activity.NetMainActivity;
import com.linewell.archive.rv.TestRvActionBarActivity;
import com.linewell.core.common.BaseActivity;

//@ContentLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void evActivity(View view) {
        TestEvActivity.start(this);
    }

    public void evActionBarActivity(View view) {
        TestEvActionBarActivity.start(this);
    }

    public void rvActionBarActivity(View view) {
        TestRvActionBarActivity.start(this);
    }

    public void sectionAdapter(View view) {
        TestSectionActivity.start(this);
    }

    public void testFragment(View view) {
        TestFragmentActivity.start(this);
    }

    public void testViewpagerAdapter(View view) {
        TestViewPagerAdapterActivity.start(this);
    }

    public void testNetRequest(View view) {
        Intent intent = new Intent(MainActivity.this, NetMainActivity.class);
        startActivity(intent);
    }

}
