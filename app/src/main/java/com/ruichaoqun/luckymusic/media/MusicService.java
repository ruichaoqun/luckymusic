package com.ruichaoqun.luckymusic.media;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.media.MediaBrowserServiceCompat;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.utils.LogUtils;
import com.ruichaoqun.luckymusic.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DaggerService;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Rui Chaoqun
 * @date :2019/12/19 19:14
 * description:
 */
public class MusicService extends MediaBrowserServiceCompat {
    private static final int NOW_PLAYING_NOTIFICATION = 0xb339;
    public static final String METADATA_KEY_LUCKY_FLAGS = "com.ruichaoqun.luckymusic.media.METADATA_KEY_UAMP_FLAGS";

    public String TAG = this.getClass().getSimpleName();

    private BecomingNoisyReceiver mBecomingNoisyReceiver;
    private MediaSessionCompat mMediaSession;
    private MediaControllerCompat mMediaController;
    private NotificationBuilder mNotificationBuilder;
    private NotificationManagerCompat mNotificationManager;

    private boolean isForegroundService = false;


    private SimpleExoPlayer mExoPlayer;
    private LuckyPlaybackPreparer mPlaybackPreparer;
    private AudioAttributes uAmpAudioAttributes = new AudioAttributes.Builder()
            .setContentType(C.CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build();
    private CompositeDisposable mCompositeDisposable;

    @Inject
    protected DataRepository dataRepository;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mMediaSession = new MediaSessionCompat(this, "MusicService");
        mMediaSession.setSessionActivity(pendingIntent);
        mMediaSession.setActive(true);

        setSessionToken(mMediaSession.getSessionToken());

        mMediaController = new MediaControllerCompat(this, mMediaSession);
        mMediaController.registerCallback(new MediaControllerCallback());

        mNotificationBuilder = new NotificationBuilder(this);
        mNotificationManager = NotificationManagerCompat.from(this);
        mBecomingNoisyReceiver = new BecomingNoisyReceiver(this, mMediaSession.getSessionToken());

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
        mExoPlayer.setAudioAttributes(uAmpAudioAttributes, true);

        MediaSessionConnector mediaSessionConnector = new MediaSessionConnector(mMediaSession);
        mediaSessionConnector.setPlayer(mExoPlayer);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getApplication().getPackageName()), null);
        mPlaybackPreparer = new LuckyPlaybackPreparer(dataRepository, mExoPlayer, dataSourceFactory);
        mediaSessionConnector.setPlaybackPreparer(mPlaybackPreparer);
        mediaSessionConnector.setQueueNavigator(new LuckyQueueNavigator(mMediaSession));
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        mExoPlayer.stop();
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return new BrowserRoot("MusicService", new Bundle());
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        LogUtils.w(TAG, "parentId-->" + parentId);
        result.detach();
        MediaID mediaID = MediaID.fromString(parentId);
        switch (mediaID.getType()) {
            case MediaDataType.TYPE_SONG:
                mCompositeDisposable.add(dataRepository.getAllSongs()
                        .compose(RxUtils.transformerThread())
                        .map(mediaMetadataCompat -> {
                            List<MediaBrowserCompat.MediaItem> mediaItems = new ArrayList<>();
                            for (MediaMetadataCompat data : mediaMetadataCompat) {
                                mediaItems.add(new MediaBrowserCompat.MediaItem(data.getDescription(), (int) data.getLong(METADATA_KEY_LUCKY_FLAGS)));
                            }
                            return mediaItems;
                        })
                        .subscribe(mediaItems -> result.sendResult(mediaItems)));
                break;
            default:
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaSession.setActive(false);
        mMediaSession.release();
        mCompositeDisposable.dispose();
    }

    private void removeNowPlayingNotification() {
        stopForeground(true);
    }

    private class MediaControllerCallback extends MediaControllerCompat.Callback {

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            if (mMediaController.getPlaybackState() != null) {
                updateNotification(mMediaController.getPlaybackState());
            }
        }

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            updateNotification(state);
        }

        private void updateNotification(PlaybackStateCompat state) {
            int updatedState = state.getState();
            Notification notification = null;
            if (mMediaController.getMetadata() != null && updatedState != PlaybackStateCompat.STATE_NONE) {
                try {
                    notification = mNotificationBuilder.buildNotification(mMediaSession.getSessionToken());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            switch (updatedState) {
                case PlaybackStateCompat.STATE_BUFFERING:
                case PlaybackStateCompat.STATE_PLAYING:
                    if (notification != null) {
                        mNotificationManager.notify(NOW_PLAYING_NOTIFICATION, notification);

                        if (!isForegroundService) {
                            ContextCompat.startForegroundService(getApplicationContext(),
                                    new Intent(getApplicationContext(), MusicService.class));
                            startForeground(NOW_PLAYING_NOTIFICATION, notification);
                            isForegroundService = true;
                        }
                    }
                    break;
                default:
                    if (isForegroundService) {
                        stopForeground(false);
                        isForegroundService = false;

                        // If playback has ended, also stop the service.
                        if (updatedState == PlaybackStateCompat.STATE_NONE) {
                            stopSelf();
                        }

                        if (notification != null) {
                            mNotificationManager.notify(NOW_PLAYING_NOTIFICATION, notification);
                        } else {
                            removeNowPlayingNotification();
                        }
                    }
            }
        }
    }

    private class BecomingNoisyReceiver extends BroadcastReceiver {
        private Context context;
        private MediaSessionCompat.Token sessionToken;

        private IntentFilter noisyIntentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        private MediaControllerCompat controller;

        private boolean registered = false;

        public BecomingNoisyReceiver(Context context, MediaSessionCompat.Token sessionToken) {
            this.context = context;
            this.sessionToken = sessionToken;
            try {
                controller = new MediaControllerCompat(context, sessionToken);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }


        private void register() {
            if (!registered) {
                context.registerReceiver(this, noisyIntentFilter);
                registered = true;
            }
        }

        private void unregister() {
            if (registered) {
                context.unregisterReceiver(this);
                registered = false;
            }
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == AudioManager.ACTION_AUDIO_BECOMING_NOISY) {
                if (controller != null) {
                    controller.getTransportControls().pause();
                }
            }
        }
    }
}
