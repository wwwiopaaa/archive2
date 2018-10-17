package com.linewell.core.cache;

import android.content.Context;
import android.text.TextUtils;

import com.linewell.core.utils.Codec;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiskCache implements Cache {

    private static final String MICRO_CREATE_TIME = "createTime_v";
    private static final String MICRO_EXPIRE_MILLS = "expireMills_v";

    public static final String TAG_CACHE = "=====createTime{"+MICRO_CREATE_TIME+"}expireMills{"+MICRO_EXPIRE_MILLS+"}";
    public static final String REGEX = "=====createTime\\{(\\d{1,})\\}expireMills\\{(-?\\d{1,})\\}";

    private Pattern compile;

    private DiskLruCache cache;

    public static final long NO_CACHE = -1L;

    public DiskCache(DiskLruCache diskLruCache) {
        compile = Pattern.compile(REGEX);
        cache = diskLruCache;
    }

    public void put(String key, String value) {
        put(key, value, NO_CACHE);
    }

    @Override
    public <T> void put(String key, T value) {
        put(key, value != null ? value.toString() : null, NO_CACHE);
    }

    public void put(String key, String value, long expireMills) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) return;

        String name = getMd5Key(key);
        try {
            if (!TextUtils.isEmpty(get(name))) {     //如果存在，先删除
                cache.remove(name);
            }

            DiskLruCache.Editor editor = cache.edit(name);
            StringBuilder content = new StringBuilder(value);
            content.append(TAG_CACHE.replace(MICRO_CREATE_TIME, "" + Calendar.getInstance().getTimeInMillis()).replace(MICRO_EXPIRE_MILLS, "" + expireMills));
            editor.set(0, content.toString());
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return get(key, null);
    }

    @Override
    public <T> T get(String key, T def) {
        try {
            String md5Key = getMd5Key(key);
            DiskLruCache.Snapshot snapshot = cache.get(md5Key);
            if (snapshot != null) {
                String content = snapshot.getString(0);

                if (!TextUtils.isEmpty(content)) {
                    Matcher matcher = compile.matcher(content);
                    long createTime = 0;
                    long expireMills = 0;
                    while (matcher.find()) {
                        createTime = Long.parseLong(matcher.group(1));
                        expireMills = Long.parseLong(matcher.group(2));
                    }
                    int index = content.indexOf("=====createTime");

                    if ((createTime + expireMills > Calendar.getInstance().getTimeInMillis())
                            || expireMills == NO_CACHE) {
                        return (T) content.substring(0, index);
                    } else {//过期
                        cache.remove(md5Key);       //删除
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void remove(String key) {
        try {
            cache.remove(getMd5Key(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean contains(String key) {
        try {
            DiskLruCache.Snapshot snapshot = cache.get(getMd5Key(key));
            return snapshot != null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void clear() {
        try {
            cache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMd5Key(String key) {
        return Codec.MD5.getMessageDigest(key.getBytes());
    }
}
