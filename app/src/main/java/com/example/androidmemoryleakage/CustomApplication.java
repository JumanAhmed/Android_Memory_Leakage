package com.example.androidmemoryleakage;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class CustomApplication extends Application {

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcherInstance(Context context){
        CustomApplication customApplication = (CustomApplication) context.getApplicationContext();
        return  customApplication.refWatcher;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        refWatcher = LeakCanary.install(this);

    }

}
