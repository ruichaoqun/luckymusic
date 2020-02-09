package com.ruichaoqun.luckymusic.media;

import android.media.session.PlaybackState;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;

import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;

public class LuckyCustomActionProvider implements MediaSessionConnector.CustomActionProvider {
    @Override
    public void onCustomAction(Player player, ControlDispatcher controlDispatcher, String action, Bundle extras) {
        player.stop(true);
    }

    @Override
    public PlaybackStateCompat.CustomAction getCustomAction(Player player) {
        return new PlaybackStateCompat.CustomAction.Builder(MediaCommonConstant.RELEASE_PLAYER,"1",1).build();
    }
}
