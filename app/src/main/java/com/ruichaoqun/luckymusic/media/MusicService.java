package com.ruichaoqun.luckymusic.media;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.media.MediaBrowserServiceCompat;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.C;
import androidx.media3.common.ForwardingSimpleBasePlayer;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.session.MediaLibraryService;
import androidx.media3.session.MediaSession;
import androidx.media3.session.MediaSessionService;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.ruichaoqun.luckymusic.Constants;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.data.bean.PlayListBean;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectProvider;
import com.ruichaoqun.luckymusic.utils.LogUtils;
import com.ruichaoqun.luckymusic.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DaggerService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author Rui Chaoqun
 * @date :2019/12/19 19:14
 * description:
 */
public class MusicService extends MediaSessionService {
    private static final int NOW_PLAYING_NOTIFICATION = 0xb339;
    public static final String METADATA_KEY_LUCKY_FLAGS = "com.ruichaoqun.luckymusic.media.METADATA_KEY_UAMP_FLAGS";
    public static final String CUSTOM_ACTION_EFFECT = "com.ruichaoqun.luckymusic.media.CUSTOM_ACTION_EFFECT";

    public String TAG = this.getClass().getSimpleName();

    private MediaSession mMediaSession;
    private NotificationBuilder mNotificationBuilder;
    private NotificationManagerCompat mNotificationManager;
    private boolean isForegroundService = false;
    private ExoPlayer mExoPlayer;
    private AudioAttributes uAmpAudioAttributes = new AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build();
    private CompositeDisposable mCompositeDisposable;

    @Inject
    protected DataRepository dataRepository;
    private MediaServiceCallback mediaServiceCallback = new MediaServiceCallback();


    @UnstableApi
    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        AndroidInjection.inject(this);
        super.onCreate();
        Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_IMMUTABLE);

        mExoPlayer = new ExoPlayer.Builder(this).build();
        mExoPlayer.setAudioAttributes(uAmpAudioAttributes, true);
        mExoPlayer.setHandleAudioBecomingNoisy(true);
        mExoPlayer.addListener(new Player.Listener() {
            @Override
            public void onAudioSessionIdChanged(int audioSessionId) {
                dataRepository.setAudioSessionId(audioSessionId);
                // TODO mediaSessionConnector.setCustomActionProviders(new AudioEffectProvider(audioSessionId,dataRepository,mediaSessionConnector));
            }

            @Override
            public void onEvents(Player player, Player.Events events) {
                Player.Listener.super.onEvents(player, events);
            }

            @Override
            public void onPlayerError(PlaybackException error) {
                Player.Listener.super.onPlayerError(error);
            }
        });
        mMediaSession = new MediaSession.Builder(this, mExoPlayer)
                .setCallback(mediaServiceCallback)
                .setSessionActivity(pendingIntent)
                .build();

        addSession(mMediaSession);
        mNotificationBuilder = new NotificationBuilder(this);
        mNotificationManager = NotificationManagerCompat.from(this);
        mCompositeDisposable = new CompositeDisposable();
        initPlayListData();
    }

    @Nullable
    @Override
    public MediaSession onGetSession(MediaSession.ControllerInfo controllerInfo) {
        Log.i(TAG, "onGetSession");
        return mMediaSession;
    }

    /**
     * 初始化播放列表
     */
    private void initPlayListData() {
//        dataRepository.rxGetCurrentPlayList()
//                .subscribe(new Consumer<PlayListBean>() {
//                    @Override
//                    public void accept(PlayListBean listBean) throws Exception {
//                        if(listBean != null && listBean.getMPlayListSongBeans() != null && listBean.getMPlayListSongBeans().size() > 0){
//                            mMediaController.getTransportControls().prepareFromMediaId(new MediaID(MediaDataType.CURRENT_PLAY_LIST , listBean.getLastPlaySongId()).asString(), null);
//                        }
//                    }
//                });
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        mExoPlayer.stop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaSession.release();
        mCompositeDisposable.dispose();
    }

    private void removeNowPlayingNotification() {
        stopForeground(true);
    }

//    private class MediaControllerCallback extends MediaControllerCompat.Callback {
//
//        @Override
//        public void onMetadataChanged(MediaMetadataCompat metadata) {
//            if (mMediaController.getPlaybackState() != null) {
//                updateNotification(mMediaController.getPlaybackState());
//                String id = metadata.getDescription().getMediaId();
//                if(!TextUtils.isEmpty(id)){
//                    dataRepository.updatePlayLastSong(Long.valueOf(id),0);
//                }
//            }
//        }
//
//        @Override
//        public void onPlaybackStateChanged(PlaybackStateCompat state) {
//            updateNotification(state);
//            switch (state.getState()){
//                case PlaybackStateCompat.STATE_PAUSED:
//                    dataRepository.updatePlayLastSong(Long.valueOf(mMediaController.getMetadata().getDescription().getMediaId()),state.getPosition());
//                    break;
//                case PlaybackStateCompat.STATE_STOPPED:
//                    dataRepository.updatePlayList(null,-1,0);
//                        break;
//            }
//        }
//
//        private void updateNotification(PlaybackStateCompat state) {
//            int updatedState = state.getState();
//            Notification notification = null;
//            if (mMediaController.getMetadata() != null && updatedState != PlaybackStateCompat.STATE_NONE) {
//                try {
//                    notification = mNotificationBuilder.buildNotification(mMediaSession.getSessionToken());
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//            switch (updatedState) {
//                case PlaybackStateCompat.STATE_BUFFERING:
//                case PlaybackStateCompat.STATE_PLAYING:
//                    if (notification != null) {
//                        mNotificationManager.notify(NOW_PLAYING_NOTIFICATION, notification);
//
//                        if (!isForegroundService) {
//                            ContextCompat.startForegroundService(getApplicationContext(),
//                                    new Intent(getApplicationContext(), MusicService.class));
//                            startForeground(NOW_PLAYING_NOTIFICATION, notification);
//                            isForegroundService = true;
//                        }
//                    }
//                    break;
//                default:
//                    if (isForegroundService) {
//                        stopForeground(false);
//                        isForegroundService = false;
//
//                        // If playback has ended, also stop the service.
//                        if (updatedState == PlaybackStateCompat.STATE_NONE) {
//                            stopSelf();
//                        }
//
//                        if (notification != null) {
//                            mNotificationManager.notify(NOW_PLAYING_NOTIFICATION, notification);
//                        } else {
//                            removeNowPlayingNotification();
//                        }
//                    }
//            }
//        }
//
//        @Override
//        public void onRepeatModeChanged(int repeatMode) {
//            dataRepository.setPlayMode(repeatMode);
//        }
//
//        @Override
//        public void onShuffleModeChanged(int shuffleMode) {
//            if(shuffleMode == PlaybackStateCompat.SHUFFLE_MODE_ALL){
//                dataRepository.setPlayMode(3);
//            }
//        }
//    }

    private class MediaServiceCallback implements MediaSession.Callback {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public ListenableFuture<List<MediaItem>> onAddMediaItems(MediaSession mediaSession, MediaSession.ControllerInfo controller, List<MediaItem> mediaItems) {
            LogUtils.i(TAG, "onAddMediaItems " + mediaItems.size());
            List<MediaItem> updatedMediaItems = new ArrayList<>();
            for (MediaItem mediaItem : mediaItems) {
                MediaID mediaID = MediaID.fromString(mediaItem.mediaId);
                LogUtils.i(TAG, "mediaId:  " + mediaItem.mediaId);
                List<SongBean> list = dataRepository.getSongsFromType(mediaID.getType(), null);
                long position = dataRepository.getLastPosition(mediaID.getType());
                int index = getCurrentIndex(list, mediaID.getMediaId());
                if (index == -1) {
                    LogUtils.e(TAG, "当前音乐列表未匹配到指定音乐id-->" + mediaItem.mediaId);
                }
                list.forEach(songBean -> {
                    if (songBean != null) {
                        Bundle bundle = new Bundle();
                        bundle.putLong(Constants.INTENT_EXTRA_LIKE,songBean.getIsLike());
                        MediaItem updatedItem = new MediaItem.Builder()
                                .setMediaId(String.valueOf(songBean.getId()))
                                .setUri(ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, songBean.getId()))
                                .setMediaMetadata(new MediaMetadata.Builder()
                                        .setTitle(songBean.getTitle())
                                        .setArtist(songBean.getArtist())
                                        .setAlbumTitle(songBean.getAlbum())
                                        .setArtworkUri(TextUtils.isEmpty(songBean.getAlbumArtUri())?null: Uri.parse(songBean.getAlbumArtUri()))
                                        .setExtras(bundle)
                                        .build())
                                .build();
                        updatedMediaItems.add(updatedItem);
                    } else {
                        updatedMediaItems.add(mediaItem);
                    }
                });
            }
            // 设置播放列表
            //mExoPlayer.setMediaItems(updatedMediaItems);
            return Futures.immediateFuture(updatedMediaItems);
        }

        private int getCurrentIndex(List<SongBean> list, long mediaId) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == mediaId) {
                    return i;
                }
            }
            return -1;
        }
    }
}