package com.linewell.core.cache;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

public class MemoryCache implements Cache {

    public interface OnSizeOf {
        int sizeOf(@NonNull String key, @NonNull Object value);
    }

    private LruCache<String, Object> cache;

    public MemoryCache(int cacheSize, final OnSizeOf sizeOf) {
        cache = new LruCache<String, Object>(cacheSize) {
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Object value) {
                return sizeOf != null ? sizeOf.sizeOf(key, value) : super.sizeOf(key, value);
            }
        };
    }

    @Override
    public synchronized <T> T get(String key, T def) {
        Object object = cache.get(key);
        return object != null ? (T) object : def;
    }

    @Override
    public synchronized <T> void put(String key, T value) {
        if (TextUtils.isEmpty(key)) return;

        if (cache.get(key) != null) {
            cache.remove(key);
        }
        cache.put(key, value);
    }

    @Override
    public void remove(String key) {
        if (cache.get(key) != null) {
            cache.remove(key);
        }
    }

    @Override
    public boolean contains(String key) {
        return cache.get(key) != null;
    }

    @Override
    public void clear() {
        cache.evictAll();
    }
}
