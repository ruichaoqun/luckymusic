package com.ruichaoqun.luckymusic.media;

import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.media.session.MediaButtonReceiver;

import com.ruichaoqun.luckymusic.R;

import static android.support.v4.media.session.PlaybackStateCompat.ACTION_PAUSE;
import static android.support.v4.media.session.PlaybackStateCompat.ACTION_PLAY;
import static android.support.v4.media.session.PlaybackStateCompat.ACTION_SKIP_TO_NEXT;
import static android.support.v4.media.session.PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS;
import static android.support.v4.media.session.PlaybackStateCompat.ACTION_STOP;

public class NotificationBuilder {
    private Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Action skipToPreviousAction;

    public NotificationBuilder(Context context) {
        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        skipToPreviousAction = new NotificationCompat.Action(R.drawable.exo_controls_previous,
                context.getString(R.string.notification_skip_to_previous),
                MediaButtonReceiver.buildMediaButtonPendingIntent(context, ACTION_SKIP_TO_PREVIOUS));
    }
}
