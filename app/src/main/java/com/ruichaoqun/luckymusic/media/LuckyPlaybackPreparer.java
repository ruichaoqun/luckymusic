package com.ruichaoqun.luckymusic.media;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.utils.LogUtils;

import java.util.List;

import static android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID;

public class LuckyPlaybackPreparer implements MediaSessionConnector.PlaybackPreparer {
    public static final String TAG = LuckyPlaybackPreparer.class.getSimpleName();

    private DataRepository dataRepository;

    @Override
    public void onPrepare(boolean playWhenReady) {

    }

    @Override
    public void onPrepareFromMediaId(String mediaId, boolean playWhenReady, Bundle extras) {
        MediaID mediaID = MediaID.fromString(mediaId);
        Log.w("AAAAAAA",ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, mediaID.getMediaId()).toString());
        List<MediaMetadataCompat> list;
        switch (mediaID.getType()){
            case MediaDataType.TYPE_SONG:
                list = dataRepository.getMediaDataSource().getAllSongsData();
                break;
            case MediaDataType.TYPE_SEARCH:
                list = dataRepository.getMediaDataSource().getSearchSongsData();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mediaID.getType());
        }
        int index = getCurrentIndex(list,mediaId);
        if(index == -1){
            LogUtils.e(TAG,"当前音乐列表为匹配到指定音乐id-->"+mediaId);
        }{
            ConcatenatingMediaSource mediaSource = toMediaSource(list);
            exoPlayer.prepare(mediaSource);
            exoPlayer.seekTo(index, 0);
        }
    }

    @Override
    public void onPrepareFromSearch(String query, boolean playWhenReady, Bundle extras) {

    }

    @Override
    public void onPrepareFromUri(Uri uri, boolean playWhenReady, Bundle extras) {

    }

    private ExoPlayer exoPlayer;
    private DataSource.Factory dataSourceFactory;

    public LuckyPlaybackPreparer(DataRepository dataRepository, ExoPlayer exoPlayer, DataSource.Factory dataSourceFactory) {
        this.dataRepository = dataRepository;
        this.exoPlayer = exoPlayer;
        this.dataSourceFactory = dataSourceFactory;
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

    private int getCurrentIndex(List<MediaMetadataCompat> list, String mediaId) {
        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i).getString(METADATA_KEY_MEDIA_ID);
            if(TextUtils.equals(id,mediaId)){
                return i;
            }
        }
        return -1;
    }

    public ConcatenatingMediaSource toMediaSource(List<MediaMetadataCompat> list){
        ConcatenatingMediaSource mediaSource = new ConcatenatingMediaSource();
        for (MediaMetadataCompat mediaMetadataCompat:list) {
            MediaID mediaID = MediaID.fromString(mediaMetadataCompat.getString(METADATA_KEY_MEDIA_ID));
            mediaSource.addMediaSource(new ProgressiveMediaSource.Factory(dataSourceFactory)
                    .setTag(mediaMetadataCompat.getDescription())
                    .createMediaSource(ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, mediaID.getMediaId())));
        }
        return mediaSource;
    }
}
