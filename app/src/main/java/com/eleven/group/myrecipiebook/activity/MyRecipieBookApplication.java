package com.eleven.group.myrecipiebook.activity;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Jake on 10/2/2017.
 */

public class MyRecipieBookApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }

}
