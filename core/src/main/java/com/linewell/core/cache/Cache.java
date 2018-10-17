package com.linewell.core.cache;

public interface Cache {
    <T> void put(String key, T value);

    <T> T get(String key, T def);

    void remove(String key);

    boolean contains(String key);

    void clear();

}