package com.mike4christ.tisvdigital;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserPreferences {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context _context;

    @SuppressLint({"CommitPrefEdits"})
    public UserPreferences(Context _context) {
        this._context = _context;
        sharedPreferences = _context.getSharedPreferences(Constant.USER_PREF, Constant.PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

   

    public void setUserLogged(boolean usLg) {
        editor.putBoolean(Constant.IS_USER_LOGGED, usLg);
        editor.commit();
    }

    public boolean isUserLogged() {
        return sharedPreferences.getBoolean(Constant.IS_USER_LOGGED, false);
    }
    

    public String getString(String key, String defaultValue) {
        sharedPreferences = _context.getSharedPreferences(Constant.USER_PREF, 0);
        return sharedPreferences.getString(key, defaultValue);
    }
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(Constant.IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(Constant.IS_FIRST_TIME_LAUNCH, true);
    }

    public void setSentSuccess(boolean isSentSuccess) {
        editor.putBoolean(Constant.IS_SENT_SUCCESS, isSentSuccess);
        editor.commit();
    }

    public boolean isSentSuccess() {
        return sharedPreferences.getBoolean(Constant.IS_SENT_SUCCESS, false);
    }

    public void saveString(String key, String value) {
        sharedPreferences = _context.getSharedPreferences(Constant.USER_PREF, 0);
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
