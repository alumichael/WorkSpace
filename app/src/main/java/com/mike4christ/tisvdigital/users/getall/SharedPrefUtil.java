package com.mike4christ.tisvdigital.users.getall;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefUtil {
    private static final String APP_PREFS = "application_preferences";
    private Context mContext;
    private Editor mEditor;
    private SharedPreferences mSharedPreferences;

    public SharedPrefUtil(Context mContext) {
        this.mContext = mContext;
    }

    public void saveString(String key, String value) {
        this.mSharedPreferences = this.mContext.getSharedPreferences(APP_PREFS, 0);
        this.mEditor = this.mSharedPreferences.edit();
        this.mEditor.putString(key, value);
        this.mEditor.commit();
    }

    public void saveInt(String key, int value) {
        this.mSharedPreferences = this.mContext.getSharedPreferences(APP_PREFS, 0);
        this.mEditor = this.mSharedPreferences.edit();
        this.mEditor.putInt(key, value);
        this.mEditor.commit();
    }

    public void saveBoolean(String key, boolean value) {
        this.mSharedPreferences = this.mContext.getSharedPreferences(APP_PREFS, 0);
        this.mEditor = this.mSharedPreferences.edit();
        this.mEditor.putBoolean(key, value);
        this.mEditor.commit();
    }

    public String getString(String key) {
        this.mSharedPreferences = this.mContext.getSharedPreferences(APP_PREFS, 0);
        return this.mSharedPreferences.getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        this.mSharedPreferences = this.mContext.getSharedPreferences(APP_PREFS, 0);
        return this.mSharedPreferences.getString(key, defaultValue);
    }

    public int getInt(String key) {
        this.mSharedPreferences = this.mContext.getSharedPreferences(APP_PREFS, 0);
        return this.mSharedPreferences.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        this.mSharedPreferences = this.mContext.getSharedPreferences(APP_PREFS, 0);
        return this.mSharedPreferences.getInt(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        this.mSharedPreferences = this.mContext.getSharedPreferences(APP_PREFS, 0);
        return this.mSharedPreferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        this.mSharedPreferences = this.mContext.getSharedPreferences(APP_PREFS, 0);
        return this.mSharedPreferences.getBoolean(key, defaultValue);
    }

    public void clear() {
        this.mSharedPreferences = this.mContext.getSharedPreferences(APP_PREFS, 0);
        this.mSharedPreferences.edit().clear().apply();
    }
}
