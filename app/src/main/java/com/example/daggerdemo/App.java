package com.example.daggerdemo;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.daggerdemo.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }
}
