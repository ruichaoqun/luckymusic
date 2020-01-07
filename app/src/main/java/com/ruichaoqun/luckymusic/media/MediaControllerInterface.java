package com.ruichaoqun.luckymusic.media;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;

/**
 * @author Rui Chaoqun
 * @date :2020/1/7 9:24
 * description:
 */
public interface MediaControllerInterface {

    void onMetadataChanged(MediaMetadataCompat metadata);

    void onPlaybackStateChanged(PlaybackStateCompat state) ;

}
