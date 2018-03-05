package com.gzcbkj.chongbao.utils;

import android.content.SharedPreferences;
import com.gzcbkj.chongbao.BaseApplication;

/**
 * Created by gigabud on 17-5-4.
 */

public class Preferences {
    private static final String TAG = "Preferences";
    private static Preferences mConfig = null;
    private SharedPreferences mSettings;

    public static final String APP_BACKTOAPP = "BACKTOAPP";

    /**
     * APP是否处于打开状态
     */
    public static final String APP_IS_RUNNING = "APP_IS_RUNNING";

    public static Preferences getInstacne() {
        if (mConfig == null) {
            synchronized (TAG) {
                if (mConfig == null) {
                    mConfig = new Preferences();
                }
            }
        }
        return mConfig;
    }

    private Preferences() {
        mSettings = BaseApplication.getAppContext().getSharedPreferences("Config", 0);
    }

    public SharedPreferences getSettings() {
        if (mSettings == null) {
            mSettings = BaseApplication.getAppContext().getSharedPreferences("Config", 0);
        }
        return mSettings;
    }



    public void setValues(String key, String value) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setValues(String key, boolean value) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setValues(String key, long value) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void setValues(String key, int value) {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public String getValues(String key, String value) {
        return getSettings().getString(key, value);
    }

    public boolean getValues(String key, boolean defValue) {
        return getSettings().getBoolean(key, defValue);
    }

    public int getValues(String key, int defValue) {
        return getSettings().getInt(key, defValue);
    }

    public long getValues(String key, long defValue) {
        return getSettings().getLong(key, defValue);
    }

    public Integer getValues(String key, Integer defValue) {
        return getSettings().getInt(key, defValue);
    }

    public String getStringByKey(String key) {
        return getSettings().getString(key, "");
    }

    public boolean getBoolByKey(String key) {
        return getSettings().getBoolean(key, false);
    }

    public long getLongByKey(String key) {
        return getSettings().getLong(key, 0l);
    }

    public Integer getIntByKey(String key) {
        return getSettings().getInt(key, 0);
    }


    public void setLastCloseAppTime() {
        setValues(APP_BACKTOAPP, System.currentTimeMillis());
    }

    public long getLastCloseAppTime() {
        return getValues(APP_BACKTOAPP, System.currentTimeMillis());
    }


    /**
     * App是否正处于打开状态
     */
    public boolean isRunning() {
        return getValues(APP_IS_RUNNING, false);
    }

    public void setIsRunning(boolean isRunning) {
        setValues(APP_IS_RUNNING, isRunning);
    }

}