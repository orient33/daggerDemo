package com.example.daggerdemo.di;

import android.app.Application;

import com.example.daggerdemo.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {ApplicationModule.class,
        ActivityBindModule.class,
        AndroidInjectionModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

}
