package com.ruichaoqun.luckymusic.data.media;

import android.support.v4.media.MediaMetadataCompat;

import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

import io.reactivex.Observable;

public interface MediaDataSource {
    List<MediaMetadataCompat> getAllSongsData();

    List<MediaMetadataCompat> getSearchSongsData();


    Observable<List<MediaMetadataCompat>> getAllSongs();

    Observable<List<MediaMetadataCompat>> searchSongs(String searchKey);


}
