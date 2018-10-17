package com.linewell.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import com.linewell.core.exception.MobileException;
import com.linewell.mvp.contract.Contract;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <pre>
 *     author : chenjl
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/09
 *     desc   : Presenter
 *     version: 1.0
 * </pre>
 */
public abstract class Presenter<V extends Contract.V, M extends Contract.M>
        implements Contract.P<V>, LifecycleObserver {

    private WeakReference<V> mRefView;
    private V proxyView;

    private M model;

    public Presenter(@NonNull M model) {
        this.model = model;
    }

    @Override
    public void attach(V view) {
        mRefView = new WeakReference<>(view);
        Class<? extends Contract.V> aClass = view.getClass();
        ClassLoader classLoader = aClass.getClassLoader();
        Class<?>[] interfaces = aClass.getInterfaces();
        proxyView = (V) Proxy.newProxyInstance(classLoader, interfaces,
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        V v = mRefView != null ? mRefView.get() : null;
                        if (v != null) {
                            return method.invoke(v, args);
                        }
                        return null;
                    }
                });
        Proxy.getProxyClass(classLoader, interfaces);
    }

    @Override
    public void detach() {
        if (mRefView != null) {
            mRefView.clear();
            mRefView = null;
        }
    }

    public V getV() {
        if (proxyView == null) {
            throw new RuntimeException(new MobileException("Presenter还未Attach过"));
        }
        return proxyView;
    }

    protected M getM() {
        return model;
    }

    public boolean isAttached() {
        return mRefView != null ? mRefView.get() != null : false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void doOnCreate() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void doOnStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void doOnResume() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void doOnPause() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void doOnStop() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void doOnDestroy() {

    }
}
