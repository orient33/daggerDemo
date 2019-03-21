package com.example.daggerdemo.di;


import com.example.daggerdemo.App;
import com.example.daggerdemo.Model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MP {

    @Singleton
    @Provides
    public Model provideModel() {
        return new Model(App.getContext());
    }
}
