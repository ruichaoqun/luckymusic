package com.ruichaoqun.luckymusic.media;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
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

    public NotificationBuilder(Context context) {
        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
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
        contentView.setTextViewText(R.id.tv_hint, description.getSubtitle());
        contentView.setImageViewUri(R.id.iv_cover, description.getIconUri());
        contentView.setOnClickPendingIntent(R.id.iv_next, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_SKIP_TO_NEXT));
        contentView.setOnClickPendingIntent(R.id.tv_lyric, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_REWIND));
        contentView.setOnClickPendingIntent(R.id.iv_close, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_STOP));
        switch (state.getState()) {
            case PlaybackStateCompat.STATE_BUFFERING:
            case PlaybackStateCompat.STATE_PLAYING:
                contentView.setImageViewResource(R.id.iv_playback, R.mipmap.ic_notification_playback_play);
                contentView.setOnClickPendingIntent(R.id.iv_playback, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_PAUSE));
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                contentView.setImageViewResource(R.id.iv_playback, R.mipmap.ic_notification_playback_pause);
                contentView.setOnClickPendingIntent(R.id.iv_playback, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_PLAY));
                break;
            default:
        }
        contentView.setOnClickPendingIntent(R.id.layout_root, controller.getSessionActivity());


        RemoteViews largeContentView = new RemoteViews(mContext.getPackageName(), R.layout.layout_notification_large_content_view);
        largeContentView.setTextViewText(R.id.tv_title, description.getTitle());
        largeContentView.setTextViewText(R.id.tv_hint, description.getSubtitle());
        largeContentView.setImageViewUri(R.id.iv_cover, description.getIconUri());
        Bundle bundle = description.getExtras();
        if(bundle != null && bundle.getBoolean(Constants.INTENT_EXTRA_LIKE,false)){
            largeContentView.setImageViewResource(R.id.iv_like,R.mipmap.ic_collect);
        }else{
            largeContentView.setImageViewResource(R.id.iv_like,R.mipmap.ic_uncollect);
        }
        largeContentView.setOnClickPendingIntent(R.id.iv_pre, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_SKIP_TO_PREVIOUS));
        largeContentView.setOnClickPendingIntent(R.id.iv_next, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_SKIP_TO_NEXT));
        largeContentView.setOnClickPendingIntent(R.id.tv_lyric, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_REWIND));
        largeContentView.setOnClickPendingIntent(R.id.iv_close, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_STOP));
        switch (state.getState()) {
            case PlaybackStateCompat.STATE_BUFFERING:
            case PlaybackStateCompat.STATE_PLAYING:
                largeContentView.setImageViewResource(R.id.iv_playback, R.mipmap.ic_notification_playback_play);
                largeContentView.setOnClickPendingIntent(R.id.iv_playback, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_PAUSE));
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                largeContentView.setImageViewResource(R.id.iv_playback, R.mipmap.ic_notification_playback_pause);
                largeContentView.setOnClickPendingIntent(R.id.iv_playback, MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_PLAY));
                break;
            default:
        }

        largeContentView.setOnClickPendingIntent(R.id.layout_root, controller.getSessionActivity());

        return builder.setCustomContentView(contentView)
                .setCustomBigContentView(largeContentView)
                .setSmallIcon(R.mipmap.icon_media)
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, ACTION_STOP))
                .setOnlyAlertOnce(true)
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
