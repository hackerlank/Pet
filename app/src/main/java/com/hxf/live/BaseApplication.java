package com.hxf.live;

import android.app.Application;
import android.content.Context;

import com.hxf.live.fragment.BaseFragment;


public class BaseApplication extends Application {


    private static Context appContext = null;
    private static BaseFragment curFragment = null;

    public BaseApplication() {
        super();
    }

    public static void setAppContext(Context context) {
        appContext = context;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static void setCurFragment(BaseFragment baseFragment) {
        curFragment = baseFragment;
    }

    public static BaseFragment getCurFragment() {
        return curFragment;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.setAppContext(getApplicationContext());
    }


}

