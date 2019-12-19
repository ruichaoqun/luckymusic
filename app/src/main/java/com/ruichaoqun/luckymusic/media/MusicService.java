package com.ruichaoqun.luckymusic.media;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media.MediaBrowserServiceCompat;

import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2019/12/19 19:14
 * description:
 */
public class MusicService extends MediaBrowserServiceCompat {
    private MediaSessionCompat mMediaSession;
    private MediaControllerCompat mMediaController;



    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        mMediaSession = new MediaSessionCompat(this,"MusicService");
        mMediaSession.setSessionActivity(pendingIntent);
        mMediaSession.setActive(true);

        setSessionToken(mMediaSession.getSessionToken());

        mMediaController = new MediaControllerCompat(this,mMediaSession);
        mMediaController.registerCallback();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }
}
