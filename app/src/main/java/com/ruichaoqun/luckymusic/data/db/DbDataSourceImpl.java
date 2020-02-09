package com.ruichaoqun.luckymusic.data.db;

import android.support.v4.media.MediaMetadataCompat;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:47
 * description:
 */
@Singleton
public class DbDataSourceImpl implements DbDataSource{

    @Inject
    public DbDataSourceImpl() {

    }

    @Override
    public Observable<List<MediaMetadataCompat>> searchSongs(String searchKey) {
        return null;
    }
}
