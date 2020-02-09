package com.ruichaoqun.luckymusic.data.db;

import android.support.v4.media.MediaMetadataCompat;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:33
 * description:
 */
public interface DbDataSource {
    Observable<List<MediaMetadataCompat>> searchSongs(String searchKey);
}
