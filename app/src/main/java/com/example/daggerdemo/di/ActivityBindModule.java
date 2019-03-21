package com.example.daggerdemo.di;

import com.example.daggerdemo.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity provideMainActivity();
}
