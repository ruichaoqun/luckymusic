package com.ruichaoqun.luckymusic.data.db;

import android.support.v4.media.MediaMetadataCompat;

import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.CustomEqBean;
import com.ruichaoqun.luckymusic.data.bean.PlayListBean;
import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:33
 * description:
 */
public interface DbDataSource {
    List<SongBean> searchSongs(String searchKey);

    List<SongBean> getAllSongsFromDb();

    List<SongBean> getAllSongsByArtistFromDb(long id);

    List<SongBean> getAllSongsByAlbumFromDb(long id);

    List<SongBean> getAllSongsByTypeFromDb(String type,String id);

    List<ArtistBean> getAllArtist();

    List<AlbumBean> getAllAlbum();

    /**
     * 获取所有歌单，包含我创建的歌单以及  我喜欢的 和 播放列表
     * @return
     */
    List<PlayListBean> getAllPlayList();

    PlayListBean getPlayList(long id);

    PlayListBean getCurrentPlayListBean();

    void insertSongs(List<SongBean> list);

    void insertArtist(List<ArtistBean> list);

    void insertAlbum(List<AlbumBean> list);

    void insertOrReplacePlayList(PlayListBean playListBean);

    void insertOrReplacePlayListSongBean();

    void updatePlayList(List<SongBean> list,long lastPlaySongId,long lastPlaySongPosition);

    void updatePlayLastSong(long lastPlaySongId,long lastPlaySongPosition);

    void removePlayListItem(long id);

    List<CustomEqBean> getAllCustomEq();

    void insertCustomEq(CustomEqBean eqBean);

    void deleteCustomEq(String title);

    void renameCustomEq(String oldName,String name);

}
