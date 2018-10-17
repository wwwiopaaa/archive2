package com.linewell.archive.rv;

import android.os.AsyncTask;

import com.linewell.archive.bean.People;
import com.linewell.mvp.contract.Callback;
import com.linewell.mvp.contract.MobileCloseable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class TestRvModel implements TestRvContract.M {
    @Override
    public MobileCloseable getData(final Callback<List<People>> callback, Map<String, Object> args, final boolean isRefresh) {
        final AsyncTask<Void, Void, List<People>> task = new AsyncTask<Void, Void, List<People>>() {

            @Override
            protected void onPreExecute() {
                callback.onStart();
            }

            @Override
            protected List<People> doInBackground(Void... voids) {
                if (new Random().nextBoolean() && new Random().nextBoolean()) {
                    return null;
                }
                try {
                    Thread.sleep(1000);
                    List<People> result = new ArrayList<>();
                    final int count = new Random().nextInt(isRefresh ? 5 : 20);
                    for (int i = 0; i < count; i++) {
                        result.add(new People("张三" + i, new Random().nextInt(100)));
                    }
                    return result;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onCancelled() {
                callback.onCancel();
                callback.onComplete();
            }

            @Override
            protected void onPostExecute(List<People> dto) {
                if (dto != null) {
                    callback.onSuccess(dto);
                } else {
                    callback.onFail("失败了", new IOException());
                }
                callback.onComplete();
            }
        }.execute();
        return new MobileCloseable() {
            @Override
            public boolean isClosed() {
                return task.isCancelled() || task.getStatus() == AsyncTask.Status.FINISHED;
            }

            @Override
            public void close() {
                task.cancel(true);
            }
        };
    }
}
