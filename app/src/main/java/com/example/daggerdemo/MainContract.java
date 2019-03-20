package com.example.daggerdemo;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.annotation.NonNull;
import android.support.v4.media.MediaDescriptionCompat;

import java.util.List;

public interface MainContract {
    interface View {
        void onLoadSuccess(@NonNull List<MediaDescriptionCompat> list);

        void onLoadFail(String msg);
    }

    interface Presenter extends DefaultLifecycleObserver {

        void loadData();
    }
}
