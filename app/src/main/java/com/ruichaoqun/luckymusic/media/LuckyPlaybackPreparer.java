package com.ruichaoqun.luckymusic.media;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueEditor;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.DynamicConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.utils.LogUtils;

import java.util.List;

import static android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID;

public class LuckyPlaybackPreparer implements MediaSessionConnector.PlaybackPreparer {
    public static final String TAG = LuckyPlaybackPreparer.class.getSimpleName();

    private DataRepository dataRepository;

    private ExoPlayer exoPlayer;
    private DataSource.Factory dataSourceFactory;
    private MediaSessionConnector mediaSessionConnector;
    private MediaControllerCompat mControllerCompat;

    public LuckyPlaybackPreparer(MediaControllerCompat mControllerCompat,MediaSessionConnector mediaSessionConnector,DataRepository dataRepository, ExoPlayer exoPlayer, DataSource.Factory dataSourceFactory) {
        this.dataRepository = dataRepository;
        this.exoPlayer = exoPlayer;
        this.dataSourceFactory = dataSourceFactory;
        this.mediaSessionConnector = mediaSessionConnector;
        this.mControllerCompat = mControllerCompat;
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onPrepareFromMediaId(String mediaId, Bundle extras) {
        MediaID mediaID = MediaID.fromString(mediaId);
        List<SongBean> list = null;
        list = dataRepository.getSongsFromType(mediaID.getType(),null);
        long position = dataRepository.getLastPosition(mediaID.getType());
        int index = getCurrentIndex(list,mediaID.getMediaId());
        if(index == -1){
            LogUtils.e(TAG,"当前音乐列表为匹配到指定音乐id-->"+mediaId);
        }{
            ConcatenatingMediaSource mediaSource = toMediaSource(list);
            exoPlayer.prepare(mediaSource);
            exoPlayer.seekTo(index, position);
            mediaSessionConnector.setQueueEditor(new TimelineQueueEditor(mControllerCompat,mediaSource,
                    new DefaultQueueDataAdapter(),new DefaultMediaSourceFactory(dataSourceFactory)));
        }
        dataRepository.updatePlayList(list, mediaID.getMediaId(),position);
    }

    @Override
    public void onPrepareFromSearch(String query,  Bundle extras) {

    }

    @Override
    public void onPrepareFromUri(Uri uri, Bundle extras) {

    }



    @Override
    public long getSupportedPrepareActions() {
        return PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID |
        PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID |
        PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH |
        PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
    }

    @Override
    public boolean onCommand(Player player, ControlDispatcher controlDispatcher, String command, Bundle extras, ResultReceiver cb) {
        return false;
    }

    private int getCurrentIndex(List<SongBean> list, long mediaId) {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId() == mediaId){
                return i;
            }
        }
        return -1;
    }

    public ConcatenatingMediaSource toMediaSource(List<SongBean> list){
        ConcatenatingMediaSource mediaSource = new ConcatenatingMediaSource();
        for (SongBean songBean:list) {
            mediaSource.addMediaSource(new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .setTag(songBean.getDescription())
                    .createMediaSource(ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, songBean.getId())));
        }
        return mediaSource;
    }

    private class DefaultQueueDataAdapter implements TimelineQueueEditor.QueueDataAdapter {
        @Override
        public void add(int position, MediaDescriptionCompat description) {

        }

        @Override
        public void remove(int position) {

        }

        @Override
        public void move(int from, int to) {

        }
    }
}
