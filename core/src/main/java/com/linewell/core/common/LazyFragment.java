package com.linewell.core.common;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * <pre>
 *     author : chenjl
 *     e-mail : iopaaa@126.com
 *     time   : 2018/09/29
 *     desc   : 延迟加载Fragment
 *     version: 1.0
 * </pre>
 */
public class LazyFragment extends BaseFragment {
    protected static final String DEBUG = "lazy_fragment_debug";
    protected static final String TAG = "lazy_fragment_tag";
    protected static final String IS_PREPARED = "isPrepared";
    protected static final String IS_VISIBLE_TO_USER = "isVisibleToUser";

    private boolean isDebug = false;

    private String mTag = "LazyFragment";

    private boolean isPrepared = false;

    private boolean isStartedLazy = false;

    private boolean isResumedLazy = false;

    private boolean isStarted = false;

    //25.1.1版本v4包 getUserVisibleHint()存在不准确情况此处使用本地变量
    private boolean isVisibleToUser = true;

    //这个标识用来识别fragment处于状态保存下时，是否被重新设置visibleToUser标识
    //又或者在onCreateView 之前是否被设置过visibleToUser
    private boolean visibleToUserResetFlagUnderOnSaveInstanceState = false;

    private boolean isResumed = false;

    protected Bundle mSavedInstanceState;

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    private void log(String message) {
        if (isDebug) {
            Log.d(mTag, message);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        visibleToUserResetFlagUnderOnSaveInstanceState = true;
        tryToTriggerStateChange(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        tryToTriggerStateChange(!hidden);
    }

    private void tryToTriggerStateChange(boolean visible) {
        if (!visible) {
            maybeNeedPauseLazy();
            return;
        }

        if (getView() != null) {
            maybeNeedCreateViewLazy();
            maybeNeedStartLazy();
            maybeNeedResumeLazy();
        }
    }

    @Override
    @Deprecated
    public final void onCreateView(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(DEBUG)) {
                isDebug = savedInstanceState.getBoolean(DEBUG);
            }
            if (!visibleToUserResetFlagUnderOnSaveInstanceState && savedInstanceState.containsKey(IS_VISIBLE_TO_USER)) {
                isVisibleToUser = savedInstanceState.getBoolean(IS_VISIBLE_TO_USER);
            }
            if (savedInstanceState.containsKey(TAG)) {
                mTag = savedInstanceState.getString(TAG);
            }
        }

        mSavedInstanceState = savedInstanceState;

        maybeNeedCreateViewLazy();
    }

    /*package*/
    final void maybeNeedCreateViewLazy() {
        if (isVisibleToUser && !isPrepared) {

            log("onCreateViewLazy");

            isPrepared = true;

            onCreateViewLazy(mSavedInstanceState,
                    mSavedInstanceState != null && mSavedInstanceState.getBoolean(IS_PREPARED, false));

            getLifecycleLazy().handleLifecycleEvent(Lifecycle.Event.ON_CREATE);

            mSavedInstanceState = null;
        }
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    @Override
    @Deprecated
    public final void onStart() {
        super.onStart();
        isStarted = true;
        maybeNeedStartLazy();
    }

    /*package*/
    final void maybeNeedStartLazy() {
        if (!isPrepared() || !isStarted) {
            return;
        }

        if (!isStartedLazy) {

            log("onStartLazy");

            isStartedLazy = true;

            onStartLazy();

            getLifecycleLazy().handleLifecycleEvent(Lifecycle.Event.ON_START);
        }
    }

    @Override
    @Deprecated
    public final void onResume() {
        super.onResume();
        isResumed = true;
        maybeNeedStartLazy();
        maybeNeedResumeLazy();
    }

    /*package*/
    final void maybeNeedResumeLazy() {
        if (!isPrepared() || !isResumed) {
            return;
        }

        if (!isResumedLazy && isFragmentVisible(this)) {

            log("onResumeLazy");

            isResumedLazy = true;

            onResumeLazy();

            getLifecycleLazy().handleLifecycleEvent(Lifecycle.Event.ON_RESUME);

            maybeNeedResumeLazyForChildren();
        }
    }

    @Override
    @Deprecated
    public final void onPause() {
        super.onPause();
        isResumed = false;
        maybeNeedPauseLazy();
    }

    /*package*/
    final void maybeNeedPauseLazy() {
        if (!isPrepared()) {
            return;
        }

        if (isResumedLazy) {

            log("onPauseLazy");

            isResumedLazy = false;

            maybeNeedPauseLazyForChildren();

            getLifecycleLazy().handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);

            onPauseLazy();
        }
    }

    @Override
    @Deprecated
    public final void onStop() {
        super.onStop();
        isStarted = false;
        maybeNeedPauseLazy();
        maybeNeedStopLazy();
    }

    /*package*/
    final void maybeNeedStopLazy() {
        if (!isPrepared() || isStarted) {
            return;
        }

        if (isStartedLazy) {

            log("onStopLazy");

            isStartedLazy = false;

            getLifecycleLazy().handleLifecycleEvent(Lifecycle.Event.ON_STOP);

            onStopLazy();
        }
    }

    @Override
    @Deprecated
    public final void onDestroyView() {
        super.onDestroyView();
        maybeNeedDestroyViewLazy();
    }

    /*package*/
    final void maybeNeedDestroyViewLazy() {
        if (!isPrepared()) {
            return;
        }

        maybeNeedPauseLazy();

        maybeNeedStopLazy();

        log("onDestroyViewLazy");

        getLifecycleLazy().handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);

        onDestroyViewLazy();

        isPrepared = false;
    }

    private void maybeNeedResumeLazyForChildren() {
        List<Fragment> fragments = getChildFragments(this);
        if (fragments == null || fragments.isEmpty()) {
            return;
        }

        for (Fragment f : fragments) {
            if (f != null && f instanceof LazyFragment) {
                LazyFragment lazyFragment = (LazyFragment) f;
                lazyFragment.maybeNeedStartLazy();
                lazyFragment.maybeNeedResumeLazy();
            }
        }
    }

    private void maybeNeedPauseLazyForChildren() {
        List<Fragment> fragments = getChildFragments(this);
        if (fragments == null || fragments.isEmpty()) {
            return;
        }

        for (Fragment f : fragments) {
            if (f != null && f instanceof LazyFragment) {
                ((LazyFragment) f).maybeNeedPauseLazy();
            }
        }
    }

    @CallSuper
    @Deprecated
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        visibleToUserResetFlagUnderOnSaveInstanceState = false;
        if (mSavedInstanceState != null) {
            outState.putAll(mSavedInstanceState);
        }
        outState.putBoolean(IS_VISIBLE_TO_USER, isVisibleToUser);
        outState.putBoolean(DEBUG, isDebug);
        outState.putString(TAG, mTag);
        if (isPrepared()) {
            outState.putBoolean(IS_PREPARED, true);
            onSaveInstanceStateLazy(outState);
        }
    }

    @Deprecated
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getBoolean(IS_PREPARED, false)) {
            onViewStateRestoredLazy(savedInstanceState);
        }
    }

    public void onSaveInstanceStateLazy(Bundle outState) {
    }

    public void onViewStateRestoredLazy(@Nullable Bundle savedInstanceState) {
    }

    protected void onCreateViewLazy(Bundle savedInstanceState, boolean preIsPrepared) {
    }


    public void onStartLazy() {

    }

    /**
     * Lazy生命周期内： 可见状态下调用,当不可见时调用{@link #onPauseLazy()}
     */
    public void onResumeLazy() {

    }

    /**
     * Lazy生命周期内： 可见状态{@link #onResumeLazy()}转为不可见状态时调用
     */
    public void onPauseLazy() {

    }

    public void onStopLazy() {

    }

    public void onDestroyViewLazy() {

    }

    private LifecycleRegistry lifecycleRegistry;

    private LifecycleOwner LazyLifecycleOwner = new LifecycleOwner() {
        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            ensureLifecycleRegistry();
            return lifecycleRegistry;
        }
    };

    public LifecycleRegistry getLifecycleLazy() {
        ensureLifecycleRegistry();
        return lifecycleRegistry;
    }

    private void ensureLifecycleRegistry() {
        if (lifecycleRegistry == null) {
            lifecycleRegistry = new LifecycleRegistry(LazyLifecycleOwner);
        }
    }

    private static boolean isFragmentVisible(Fragment fragment) {
        if ((fragment instanceof LazyFragment ? ((LazyFragment) fragment).isVisibleToUser
                : fragment.getUserVisibleHint()) && isVisible(fragment)) {
            Fragment parent = fragment.getParentFragment();
            return parent == null || isFragmentVisible(parent);
        }
        return false;
    }

    private static boolean isVisible(Fragment fragment) {
//        return fragment.isVisible();//在activity oncreate add fragment，fragmeng.mView.windowtoken 可能为 null
        return fragment.isAdded() && !fragment.isHidden() && fragment.getView() != null && fragment.getView().getVisibility() == View.VISIBLE;
    }

    //private static Method sGetFragmentsMethod = null;

    /**
     * 新版v4包getFragments方法只有包内可见，此处使用反射调用
     */
    private static List<Fragment> getChildFragments(Fragment fragment) {
        FragmentManager childFragmentManager = fragment.getChildFragmentManager();
        return childFragmentManager.getFragments();
//        if (sGetFragmentsMethod == null) {
//            try {
//                sGetFragmentsMethod = childFragmentManager.getClass().getMethod("getFragments");
//                sGetFragmentsMethod.setAccessible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (sGetFragmentsMethod != null) {
//            try {
//                return (List<Fragment>) sGetFragmentsMethod.invoke(childFragmentManager);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
    }
}
