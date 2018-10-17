package com.linewell.core.exception;

import android.annotation.TargetApi;
import android.os.Build;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/09/29
 *     desc   : 内部定义异常
 *     version: 1.0
 * </pre>
 */
public class MobileException extends Exception {
    public MobileException() {
    }

    public MobileException(String message) {
        super(message);
    }

    public MobileException(String message, Throwable cause) {
        super(message, cause);
    }

    public MobileException(Throwable cause) {
        super(cause);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public MobileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
