package com.ruichaoqun.luckymusic.di.daggerandroidx;

import androidx.media.MediaBrowserServiceCompat;

import dagger.android.AndroidInjection;
import dagger.internal.Beta;

@Beta
public abstract class DaggerMediaBrowserServiceCompat extends MediaBrowserServiceCompat {

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }
}
