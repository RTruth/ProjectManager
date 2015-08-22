package com.edu.prathm.mybim;

import android.app.Application;
import android.content.Context;

/**
 * Created by Prathm on 8/13/2015.
 */public class MyApplication extends Application {


    private static MyApplication sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this ;
    }
    public static MyApplication getInstance()
    {
        return sInstance;
    }
    public static Context getAppContext()
    {
        return sInstance.getApplicationContext();
    }
}
