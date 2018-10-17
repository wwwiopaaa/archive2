package com.linewell.core.common;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.linewell.core.utils.KeyboardUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/09/29
 *     desc   : Activity通用基类
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int layoutRes = getContentLayoutRes();
        if (layoutRes > 0) {
            setContentView(layoutRes);
        }
    }

    @Override
    protected void onDestroy() {
        KeyboardUtils.fixSoftInputLeaks(this);
        super.onDestroy();
    }

    protected int getContentLayoutRes(){
        return ContentLayoutHelper.getLayoutResByAnnotation(this.getClass());
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }
}
