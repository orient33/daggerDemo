package com.example.daggerdemo.di;

import com.example.daggerdemo.MainContract;
import com.example.daggerdemo.MainPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainActivityModule {

    @ActivityScoped
    @Binds
    abstract MainContract.Presenter provideMainPresenter(MainPresenter mp);

}
