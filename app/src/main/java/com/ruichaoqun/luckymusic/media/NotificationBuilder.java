package com.ruichaoqun.luckymusic.media;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.media.app.NotificationCompat.MediaStyle;
import androidx.media.session.MediaButtonReceiver;

import com.ruichaoqun.luckymusic.Constants;
import com.ruichaoqun.luckymusic.R;

import static android.support.v4.media.session.PlaybackStateCompat.ACTION_PAUSE;
import static android.support.v4.media.session.PlaybackStateCompat.ACTION_PLAY;
import static android.support.v4.media.session.PlaybackStateCompat.ACTION_REWIND;
import static android.support.v4.media.session.PlaybackStateCompat.ACTION_SKIP_TO_NEXT;
import static android.support.v4.media.session.PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS;
import static android.support.v4.media.session.PlaybackStateCompat.ACTION_STOP;

public class NotificationBuilder {
    private static final String NOW_PLAYING_CHANNEL = "com.ruichaoqun.luckymusic.media.NOW_PLAYING";


    private Context mContext;
    private NotificationManager mNotificationManager;

    private PendingIntent skipToNext;
    private PendingIntent play;
    private PendingIntent pause;
    private PendingIntent skipToPre;
    private PendingIntent stop;

    public NotificationBuilder(Context context) {
        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        skipToNext = MediaButtonReceiver.buildMediaButtonPendingIntent(context, ACTION_SKIP_TO_NEXT);
        play = MediaButtonReceiver.buildMediaButtonPendingIntent(context, ACTION_PLAY);
        pause = MediaButtonReceiver.buildMediaButtonPendingIntent(context, ACTION_PAUSE);
        skipToPre = MediaButtonReceiver.buildMediaButtonPendingIntent(context, ACTION_SKIP_TO_PREVIOUS);
        stop = MediaButtonReceiver.buildMediaButtonPendingIntent(context, ACTION_STOP);
    }

    public Notification buildNotification(MediaSessionCompat.Token token) throws RemoteException {
        if (shouldCreateNowPlayingChannel()) {
            createNowPlayingChannel();
        }
        MediaControllerCompat controller = new MediaControllerCompat(mContext, token);
        MediaDescriptionCompat description = controller.getMetadata().getDescription();
        PlaybackStateCompat state = controller.getPlaybackState();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, NOW_PLAYING_CHANNEL);


        RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.layout_notification_content_view);
        contentView.setTextViewText(R.id.tv_title, description.getTitle());
        contentView.setTextViewText(R.id.tv_hint, description.getSubtitle()+"-"+description.getDescription());
        contentView.setImageViewUri(R.id.iv_cover, description.getIconUri());
        contentView.setOnClickPendingIntent(R.id.iv_next,skipToNext);
        contentView.setOnClickPendingIntent(R.id.tv_lyric, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_REWIND));
        contentView.setOnClickPendingIntent(R.id.iv_close, stop);
        switch (state.getState()) {
            case PlaybackStateCompat.STATE_BUFFERING:
            case PlaybackStateCompat.STATE_PLAYING:
                contentView.setImageViewResource(R.id.iv_playback, R.drawable.note_btn_pause_white);
                contentView.setOnClickPendingIntent(R.id.iv_playback, pause);
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                contentView.setImageViewResource(R.id.iv_playback, R.drawable.note_btn_play_white);
                contentView.setOnClickPendingIntent(R.id.iv_playback, play);
                break;
            default:
        }
        contentView.setOnClickPendingIntent(R.id.layout_root, controller.getSessionActivity());


        RemoteViews largeContentView = new RemoteViews(mContext.getPackageName(), R.layout.layout_notification_large_content_view);
        largeContentView.setTextViewText(R.id.tv_title, description.getTitle());
        largeContentView.setTextViewText(R.id.tv_hint,description.getSubtitle()+"-"+description.getDescription());
        largeContentView.setImageViewUri(R.id.iv_cover, description.getIconUri());
        Bundle bundle = description.getExtras();
        if(bundle != null ){
            largeContentView.setImageViewResource(R.id.iv_like,bundle.getLong(Constants.INTENT_EXTRA_LIKE,0) == 1? R.drawable.note_btn_loved:R.drawable.note_btn_love);
        }
        largeContentView.setOnClickPendingIntent(R.id.iv_pre, skipToPre);
        largeContentView.setOnClickPendingIntent(R.id.iv_next, skipToNext);
        largeContentView.setOnClickPendingIntent(R.id.tv_lyric, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_REWIND));
        largeContentView.setOnClickPendingIntent(R.id.iv_close, stop);
        switch (state.getState()) {
            case PlaybackStateCompat.STATE_BUFFERING:
            case PlaybackStateCompat.STATE_PLAYING:
                largeContentView.setImageViewResource(R.id.iv_playback, R.drawable.note_btn_pause_white);
                largeContentView.setOnClickPendingIntent(R.id.iv_playback, pause);
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                largeContentView.setImageViewResource(R.id.iv_playback, R.drawable.note_btn_play_white);
                largeContentView.setOnClickPendingIntent(R.id.iv_playback,play);
                break;
            default:
        }

        largeContentView.setOnClickPendingIntent(R.id.layout_root, controller.getSessionActivity());
        MediaStyle mediaStyle = new MediaStyle()
                .setMediaSession(token);

        return builder.setCustomContentView(contentView)
                .setCustomBigContentView(largeContentView)
                .setSmallIcon(R.mipmap.icon_media)
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_STOP))
                .setOnlyAlertOnce(true)
                .setStyle(mediaStyle)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();
    }


    private boolean shouldCreateNowPlayingChannel() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !nowPlayingChannelExists();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private boolean nowPlayingChannelExists() {
        return mNotificationManager.getNotificationChannel(NOW_PLAYING_CHANNEL) != null;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNowPlayingChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(NOW_PLAYING_CHANNEL, mContext.getString(R.string.notification_channel), NotificationManager.IMPORTANCE_LOW);
        notificationChannel.setDescription(mContext.getString(R.string.notification_channel_description));
        mNotificationManager.createNotificationChannel(notificationChannel);
    }
}
