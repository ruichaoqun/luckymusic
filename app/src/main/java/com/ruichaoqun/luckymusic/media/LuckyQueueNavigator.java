package com.ruichaoqun.luckymusic.media;

import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator;

public class LuckyQueueNavigator extends TimelineQueueNavigator {

    public LuckyQueueNavigator(MediaSessionCompat mediaSession) {
        super(mediaSession,Integer.MAX_VALUE);
    }

    @Override
    public MediaDescriptionCompat getMediaDescription(Player player, int windowIndex) {
        return (MediaDescriptionCompat) player.getCurrentTimeline().getWindow(windowIndex, new  Timeline.Window(), true).tag;
    }

    @Override
    public void onSkipToPrevious(Player player, ControlDispatcher controlDispatcher) {
        Log.w("sssss",player.getCurrentPosition()+"");
        super.onSkipToPrevious(player, controlDispatcher);
    }
}
