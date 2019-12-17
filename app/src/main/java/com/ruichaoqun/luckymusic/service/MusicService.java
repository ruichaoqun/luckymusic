package com.ruichaoqun.luckymusic.service;


import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;

import java.util.List;

public class MusicService extends MediaBrowserServiceCompat {

    private MediaSessionCompat mMediaSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaSession = new MediaSessionCompat(this,"MusicService");
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String s, int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String s, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }
}
