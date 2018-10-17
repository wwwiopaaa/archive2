package com.linewell.archive.rv;

import com.linewell.archive.bean.People;
import com.linewell.mvp.contract.EvContract;
import com.linewell.mvp.contract.RvContract;

import java.util.List;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface TestRvContract {
    interface V extends RvContract.V<P, List<People>> {

    }

    interface P extends RvContract.P<V, List<People>> {

    }

    interface M extends RvContract.M<List<People>> {

    }


}
