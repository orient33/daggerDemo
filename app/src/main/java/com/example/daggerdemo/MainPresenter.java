package com.example.daggerdemo;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaDescriptionCompat;

import com.example.daggerdemo.di.ActivityScoped;

import java.util.List;

import javax.inject.Inject;

@ActivityScoped
public class MainPresenter implements MainContract.Presenter {

    private final Model mModel;
    @Nullable
    private MainContract.View mView;
    @Nullable
    private AsyncTask<Void, Void, List<MediaDescriptionCompat>> mTask;

    @Inject
    MainPresenter(Model model) {
        mModel = model;
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        if (owner instanceof MainContract.View) {
            mView = (MainContract.View) owner;
        } else {
            throw new IllegalStateException("why owner not MainContract.View?");
        }
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        mView = null;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadData() {
        if (mTask != null) {
            mTask.cancel(false);
            mTask = null;
        }
        mTask = new AsyncTask<Void, Void, List<MediaDescriptionCompat>>() {
            @Override
            protected List<MediaDescriptionCompat> doInBackground(Void... voids) {
                return mModel.loadMediaItem();
            }

            @Override
            protected void onPostExecute(List<MediaDescriptionCompat> list) {
                if (isCancelled() || mView == null) {
                    return;
                }
                if (list.isEmpty()) {
                    mView.onLoadFail("empty!");
                } else {
                    mView.onLoadSuccess(list);
                    TopUtilKt.log(list);
                }
            }
        };
        mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
