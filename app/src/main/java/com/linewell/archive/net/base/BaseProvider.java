package com.linewell.archive.net.base;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;

/**
 * Author : zhousf
 * Description : 采用占位方式初始化第三方组件（onCreate比Application的onCreate先执行）
 * Date : 2017/7/20.
 */
public class BaseProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        //获取Application上下文
        initOkHttp(getContext());
        return false;
    }

    /**
     * 初始化网络请求框架
     */
    void initOkHttp(Context context){
        String downloadFileDir = Environment.getExternalStorageDirectory().getPath()+"/okHttp_download/";
        String cacheDir = Environment.getExternalStorageDirectory().getPath()+"/okHttp_cache";
    }



    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
