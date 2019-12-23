package com.ruichaoqun.luckymusic.media;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.media.MediaBrowserServiceCompat;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.ruichaoqun.luckymusic.utils.LogUtils;
import com.ruichaoqun.luckymusic.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author Rui Chaoqun
 * @date :2019/12/19 19:14
 * description:
 */
public class MusicService extends MediaBrowserServiceCompat {
    public String TAG = this.getClass().getSimpleName();

    private MediaSessionCompat mMediaSession;
    private MediaControllerCompat mMediaController;
    private NotificationBuilder  mNotificationBuilder;
    private NotificationManagerCompat mNotificationManager;


    private SimpleExoPlayer mExoPlayer;
    private MediaSessionConnector mMediaSessionConnector;
    private AudioAttributes uAmpAudioAttributes = new AudioAttributes.Builder()
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build();



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
        mNotificationBuilder = new NotificationBuilder(this);
        mNotificationManager = NotificationManagerCompat.from(this);

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
        mExoPlayer.setAudioAttributes(uAmpAudioAttributes,true);

        mMediaSessionConnector = new MediaSessionConnector(mMediaSession);
        mMediaSessionConnector.setPlayer(mExoPlayer);
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return new BrowserRoot("MusicService",new Bundle());
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        LogUtils.w(TAG,parentId);
        List<MediaBrowserCompat.MediaItem> list = new ArrayList<>();

        MediaMetadataCompat mediaMetadataCompat = new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, "545456").build();
        list.add(new MediaBrowserCompat.MediaItem(mediaMetadataCompat.getDescription(),MediaBrowserCompat.MediaItem.FLAG_PLAYABLE));
        result.detach();
        Observable.timer(5*1000, TimeUnit.MILLISECONDS)
                .compose(RxUtils.transformerThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        result.sendResult(list);
                    }
                });
    }

    @Override
    public void onSearch(@NonNull String query, Bundle extras, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        super.onSearch(query, extras, result);
    }

    private class MediaControllerCallback extends MediaControllerCompat.Callback {

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            updateNotification(mMediaController.getPlaybackState());
        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            updateNotification(state);
        }

        private void updateNotification(PlaybackStateCompat state) {
            int updatedState = state.getState();
            Notification notification = null;
            if (mMediaController.getMetadata() != null
                    && updatedState != PlaybackStateCompat.STATE_NONE) {
            }
        }
    }
}
