package com.linewell.mvp.contract;

import java.io.IOException;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface MobileCloseable {

    boolean isClosed();

    void close() /*throws RuntimeException*/;
}
