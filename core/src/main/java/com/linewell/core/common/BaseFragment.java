package com.linewell.core.common;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.linewell.core.exception.MobileException;

import java.lang.reflect.Field;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/09/29
 *     desc   : Fragment通用基类
 *     version: 1.0
 * </pre>
 */
public class BaseFragment extends Fragment {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    private ViewGroup mContentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_SAVE_IS_HIDDEN)) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commitAllowingStateLoss();
        }
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = createContentView(inflater, container);
        onCreateView(savedInstanceState);
        return mContentView;
    }

    public void onCreateView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        mContentView = null;
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unSafeDetach(this);
    }

    protected ViewGroup createContentView(LayoutInflater inflater, ViewGroup container) {
        FrameLayout view = new FrameLayout(getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Resources.Theme theme = getActivity() != null ? getActivity().getTheme() : (getContext() != null ? getContext().getTheme() : null);
        if (theme != null) {
            TypedArray array = (theme).obtainStyledAttributes(new int[]{android.R.attr.colorBackground});
            view.setBackgroundColor(array.getColor(0, 0xFFFFFF));
            array.recycle();
        }
        return view;
    }

    public void setContentView(@LayoutRes int viewRes) {
        setContentView(getLayoutInflater().inflate(viewRes, mContentView, false));
    }

    public ViewGroup getContentView() {
        return mContentView;
    }

    public void setContentView(@Nullable View view) {
        if (mContentView == null) {
            throw new RuntimeException(new MobileException("setContentView 必须调用在onCreateView(Bundle) 之后"));
        }

        mContentView.removeAllViews();
        if (view == null) {
            return;
        }
        mContentView.addView(view);
    }

    public <T> T findViewById(@IdRes int id) {
        return mContentView != null ? (T) mContentView.findViewById(id) : null;
    }

    private static final void unSafeDetach(Fragment fragment) {
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(fragment, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
