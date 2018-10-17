package com.linewell.core.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.SharedPreferencesCompat;

public class SharedPreferenceCache implements Cache {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceCache(Context context) {
        this(PreferenceManager.getDefaultSharedPreferences(context));
    }

    public SharedPreferenceCache(SharedPreferences sharedPref) {
        sharedPreferences = sharedPref;
        editor = sharedPref.edit();
    }

    @Override
    public void remove(String key) {
        apply(editor.remove(key));
    }

    @Override
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    @Override
    public void clear() {
        apply(editor.clear());
    }

    @Override
    public <T> T get(String key, T def) {
        if (def == null) {
            Object o = sharedPreferences.getAll().get(key);
            return o != null ? (T) o : def;
        }

        if (def instanceof String) {
            return (T) sharedPreferences.getString(key, (String) def);
        } else if (def instanceof Integer) {
            return (T) (Integer.valueOf(sharedPreferences.getInt(key, (Integer) def)));
        } else if (def instanceof Boolean) {
            return (T) (Boolean.valueOf(sharedPreferences.getBoolean(key, (Boolean) def)));
        } else if (def instanceof Float) {
            return (T) (Float.valueOf(sharedPreferences.getFloat(key, (Float) def)));
        } else if (def instanceof Long) {
            return (T) (Long.valueOf(sharedPreferences.getLong(key, (Long) def)));
        }

        return def;
    }

    @Override
    public <T> void put(String key, T value) {
        if (value == null) {
            editor.remove(key);
            return;
        }

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        apply(editor);
    }

    private static void apply(SharedPreferences.Editor editor) {
        SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    }
}
