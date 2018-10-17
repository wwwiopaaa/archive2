package com.linewell.archive.fragment;

import com.linewell.mvp.contract.Contract;
import com.linewell.mvp.contract.EvContract;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface TestFragmentContract {

    interface V extends Contract.V<P> {


    }
    interface P extends Contract.P<V> {

    }
    interface M extends Contract.M {

    }
}
