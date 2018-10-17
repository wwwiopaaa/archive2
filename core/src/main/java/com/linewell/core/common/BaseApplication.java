package com.linewell.core.common;

import android.app.Application;

import com.linewell.core.exception.MobileException;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/09/29
 *     desc   : Application通用基类
 *     version: 1.0
 * </pre>
 */
public class BaseApplication extends Application {

    private static Application sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }

    public static Application get() {
        if (sApp == null) {
            throw new RuntimeException(new MobileException("所使用的Application尚未继承BaseApplication"));
        }
        return sApp;
    }
}
