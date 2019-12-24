package com.ruichaoqun.luckymusic.data.media;

import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

import io.reactivex.Observable;

public interface MediaDataSource {
    Observable<List<SongBean>> getAllSongs();

    Observable<SongBean> getSongFromId(long id);

    Observable<List<SongBean>> getSongsFromIds(List<Long> ids);

    Observable<List<SongBean>> searchSongs(String searchKey);
}
