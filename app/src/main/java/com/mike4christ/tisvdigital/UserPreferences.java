package com.mike4christ.tisvdigital;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserPreferences {
    private Context _context;
    private Editor editor = this.sharedPreferences.edit();
    private SharedPreferences sharedPreferences;

    @SuppressLint({"CommitPrefEdits"})
    public UserPreferences(Context _context) {
        this._context = _context;
        this.sharedPreferences = _context.getSharedPreferences(Constant.USER_PREF, 0);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        this.editor.putBoolean(Constant.IS_FIRST_TIME_LAUNCH, isFirstTime);
        this.editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return this.sharedPreferences.getBoolean(Constant.IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUserLogged(boolean usLg) {
        this.editor.putBoolean(Constant.IS_USER_LOGGED, usLg);
        this.editor.commit();
    }

    public boolean isUserLogged() {
        return this.sharedPreferences.getBoolean(Constant.IS_USER_LOGGED, false);
    }

    public void setVideoIdWatch(String id, int value) {
        this.editor.putInt(id, value);
        this.editor.commit();
    }

    public int getVideoIdWatch(String id) {
        return this.sharedPreferences.getInt(id, 0);
    }

    public String getString(String key) {
        this.sharedPreferences = this._context.getSharedPreferences(Constant.USER_PREF, 0);
        return this.sharedPreferences.getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        this.sharedPreferences = this._context.getSharedPreferences(Constant.USER_PREF, 0);
        return this.sharedPreferences.getString(key, defaultValue);
    }

    public void saveString(String key, String value) {
        this.sharedPreferences = this._context.getSharedPreferences(Constant.USER_PREF, 0);
        this.editor = this.sharedPreferences.edit();
        this.editor.putString(key, value);
        this.editor.commit();
    }
}
