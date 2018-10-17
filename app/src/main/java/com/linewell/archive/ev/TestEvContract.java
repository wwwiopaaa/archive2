package com.linewell.archive.ev;

import com.linewell.archive.bean.People;
import com.linewell.mvp.contract.EvContract;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface TestEvContract {
    interface V extends EvContract.V<P, People> {

    }

    interface P extends EvContract.P<V, People> {

    }

    interface M extends EvContract.M<People> {

    }



}
