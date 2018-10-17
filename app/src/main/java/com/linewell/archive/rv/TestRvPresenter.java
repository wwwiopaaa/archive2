package com.linewell.archive.rv;

import android.support.annotation.NonNull;

import com.linewell.archive.bean.People;
import com.linewell.mvp.RvPresenter;
import com.linewell.mvp.contract.Callback;

import java.util.List;
import java.util.Random;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestRvPresenter extends RvPresenter<TestRvContract.V, TestRvContract.M, List<People>>
implements TestRvContract.P{
    public TestRvPresenter(@NonNull TestRvContract.M model) {
        super(model);
    }

    @Override
    protected void onRefreshSuccess(Callback<List<People>> callback, List<People> result) {
        setHasMore(new Random().nextBoolean() || new Random().nextBoolean());
        super.onRefreshSuccess(callback, result);
    }

    @Override
    protected void onLoadMoreSuccess(Callback<List<People>> callback, List<People> result) {
        setHasMore(new Random().nextBoolean());
        super.onLoadMoreSuccess(callback, result);
    }

}
