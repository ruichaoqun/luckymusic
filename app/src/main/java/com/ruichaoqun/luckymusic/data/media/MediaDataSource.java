package com.ruichaoqun.luckymusic.data.media;

import android.support.v4.media.MediaMetadataCompat;

import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.PlayListBean;
import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

import io.reactivex.Observable;

public interface MediaDataSource {
    Observable<List<SongBean>> rxGetAllSongs();

    Observable<List<SongBean>> rxSearchSongs(String searchKey);

    Observable<List<ArtistBean>> rxGetAllArtist();

    Observable<List<AlbumBean>> rxGetAllAlbum();

    Observable<List<SongBean>> rxGetSongsFromArtist(long id);

    Observable<List<SongBean>> rxGetSongsFromAlbum(long id);

    Observable<List<SongBean>> rxGetSongsFromType(String type ,String id);

    //获取本次播放列表
    Observable<PlayListBean> rxGetCurrentPlayList();

    List<ArtistBean> getAllArtist();

    List<SongBean> getAllSongs();

    List<SongBean> searchSongs(String searchKey);

    List<SongBean> getSongsFromArtist(long id);

    List<SongBean> getSongsFromAlbum(long id);

    List<SongBean> getSongsFromType(String type,String id);

    List<AlbumBean> getAllAlbum();

    long getLastPosition(String type);

    void updatePlayList(List<SongBean> list,long lastPlaySongId,long lastPlaySongPosition);

    void updatePlayLastSong(long lastPlaySongId,long lastPlaySongPosition);

    void removePlayListItem(long id);

    int getAudioSessionId();

    void setAudioSessionId(int audioSessionId);

}
