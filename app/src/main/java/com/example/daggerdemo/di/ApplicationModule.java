package com.example.daggerdemo.di;

import android.app.Application;
import android.content.Context;

import com.example.daggerdemo.Model;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class ApplicationModule {
    //expose Application as an injectable context
    @Singleton
    @Binds
    abstract Context bindContext(Application application);
    //  这个Context是用的Application类实现的

    @Singleton
    @Provides
    static Model provideModel(Context c) {
        return new Model(c);
    }
}

