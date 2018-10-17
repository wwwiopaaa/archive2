package com.linewell.archive.ev;

import android.os.AsyncTask;

import com.linewell.archive.bean.People;
import com.linewell.mvp.contract.Callback;
import com.linewell.mvp.contract.MobileCloseable;

import java.io.IOException;
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
public class TestEvModel implements TestEvContract.M {
    @Override
    public MobileCloseable getData(final Callback<People> callback, Map<String, Object> args) {
        final AsyncTask<Void, Void, People> task = new AsyncTask<Void, Void, People>() {

            @Override
            protected void onPreExecute() {
                callback.onStart();
            }

            @Override
            protected People doInBackground(Void... voids) {
                if (new Random().nextBoolean() && new Random().nextBoolean()) {
                    return null;
                }
                try {
                    Thread.sleep(1000);
                    return new People("张三", new Random().nextInt(100));
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
            protected void onPostExecute(People dto) {
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
