package com.ruichaoqun.luckymusic.data;


import android.support.v4.media.MediaMetadataCompat;

import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.BannerItemBean;
import com.ruichaoqun.luckymusic.data.bean.BaseResponse;
import com.ruichaoqun.luckymusic.data.bean.CustomEqBean;
import com.ruichaoqun.luckymusic.data.bean.HomePageBean;
import com.ruichaoqun.luckymusic.data.bean.HomePageItemBean;
import com.ruichaoqun.luckymusic.data.bean.PlayListBean;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.data.db.DbDataSource;
import com.ruichaoqun.luckymusic.data.http.HttpDataSource;
import com.ruichaoqun.luckymusic.data.media.ContentProviderSource;
import com.ruichaoqun.luckymusic.data.media.MediaDataSource;
import com.ruichaoqun.luckymusic.data.preference.PreferenceDataSource;
import com.ruichaoqun.luckymusic.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:31
 * description:数据仓库
 */
@Singleton
public class DataRepository implements HttpDataSource, PreferenceDataSource, MediaDataSource {
    private final HttpDataSource mHttpDataSource;
    private final PreferenceDataSource mPreferenceDataSource;
    private final MediaDataSource mMediaDataSource;
    private final DbDataSource mDbDataSource;


    @Inject
    public DataRepository(HttpDataSource httpDataSource, PreferenceDataSource preferenceDataSource,MediaDataSource mediaDataSource,DbDataSource dbDataSource) {
        mHttpDataSource = httpDataSource;
        mPreferenceDataSource = preferenceDataSource;
        mMediaDataSource = mediaDataSource;
        mDbDataSource = dbDataSource;
    }

    @Override
    public void isFirstUse() {
        mPreferenceDataSource.isFirstUse();
    }

    @Override
    public void setFirstUse() {
        mPreferenceDataSource.setFirstUse();
    }

    @Override
    public void setPlayMode(int mode) {
        mPreferenceDataSource.setPlayMode(mode);
    }

    @Override
    public int getPlayMode() {
        return mPreferenceDataSource.getPlayMode();
    }

    @Override
    public boolean isEffectEnable() {
        return mPreferenceDataSource.isEffectEnable();
    }

    @Override
    public void setEffectEnable(boolean enable) {
        mPreferenceDataSource.setEffectEnable(enable);
    }

    @Override
    public void setEffectData(String effectData) {
        mPreferenceDataSource.setEffectData(effectData);
    }

    @Override
    public String getEffectData() {
        return mPreferenceDataSource.getEffectData();
    }

    @Override
    public void setDynamicEffectType(int type) {
        mPreferenceDataSource.setDynamicEffectType(type);
    }

    @Override
    public int getDynamicEffectType() {
        return mPreferenceDataSource.getDynamicEffectType();
    }

    @Override
    public Observable<BaseResponse<List<BannerItemBean>>> getBannerList() {
        return mHttpDataSource.getBannerList();
    }

    @Override
    public Observable<BaseResponse<HomePageBean>> getHomeList(int page) {
        return mHttpDataSource.getHomeList(page);
    }

    @Override
    public Observable<BaseResponse<List<HomePageItemBean>>> getTopList() {
        return mHttpDataSource.getTopList();
    }

    @Override
    public Observable<List<SongBean>> rxGetAllSongs() {
        return mMediaDataSource.rxGetAllSongs();
    }

    @Override
    public Observable<List<SongBean>> rxSearchSongs(String searchKey) {
        return mMediaDataSource.rxSearchSongs(searchKey);
    }

    @Override
    public Observable<List<ArtistBean>> rxGetAllArtist() {
        return mMediaDataSource.rxGetAllArtist();
    }

    @Override
    public Observable<List<AlbumBean>> rxGetAllAlbum() {
        return mMediaDataSource.rxGetAllAlbum();
    }

    @Override
    public Observable<List<SongBean>> rxGetSongsFromArtist(long id) {
        return mMediaDataSource.rxGetSongsFromArtist(id);
    }

    @Override
    public Observable<List<SongBean>> rxGetSongsFromAlbum(long id) {
        return mMediaDataSource.rxGetSongsFromAlbum(id);
    }

    @Override
    public Observable<List<SongBean>> rxGetSongsFromType(String type, String id) {
        return mMediaDataSource.rxGetSongsFromType(type,id);
    }

    @Override
    public Observable<PlayListBean> rxGetCurrentPlayList() {
        return mMediaDataSource.rxGetCurrentPlayList();
    }

    @Override
    public List<ArtistBean> getAllArtist() {
        return mMediaDataSource.getAllArtist();
    }

    @Override
    public List<SongBean> getAllSongs() {
        return mMediaDataSource.getAllSongs();
    }

    @Override
    public List<SongBean> searchSongs(String searchKey) {
        return mMediaDataSource.searchSongs(searchKey);
    }

    @Override
    public List<SongBean> getSongsFromArtist(long id) {
        return mMediaDataSource.getSongsFromArtist(id);
    }

    @Override
    public List<SongBean> getSongsFromAlbum(long id) {
        return mMediaDataSource.getSongsFromAlbum(id);
    }

    @Override
    public List<SongBean> getSongsFromType(String type, String id) {
        return mMediaDataSource.getSongsFromType(type,id);
    }

    @Override
    public List<AlbumBean> getAllAlbum() {
        return mMediaDataSource.getAllAlbum();
    }

    @Override
    public long getLastPosition(String type) {
        return mMediaDataSource.getLastPosition(type);
    }

    @Override
    public void updatePlayList(List<SongBean> list, long lastPlaySongId, long lastPlaySongPosition) {
        mMediaDataSource.updatePlayList(list,lastPlaySongId,lastPlaySongPosition);
    }

    @Override
    public void updatePlayLastSong(long lastPlaySongId, long lastPlaySongPosition) {
        mMediaDataSource.updatePlayLastSong(lastPlaySongId,lastPlaySongPosition);
    }

    @Override
    public void removePlayListItem(long id) {
        mMediaDataSource.removePlayListItem(id);
    }

    @Override
    public int getAudioSessionId() {
        return mMediaDataSource.getAudioSessionId();
    }

    @Override
    public void setAudioSessionId(int audioSessionId) {
        mMediaDataSource.setAudioSessionId(audioSessionId);
    }

    public List<CustomEqBean> getAllCustomEq(){
        return mDbDataSource.getAllCustomEq();
    }

    public void insertCustomEq(CustomEqBean eqBean){
        mDbDataSource.insertCustomEq(eqBean);
    }

    public void deleteCustomEq(String title){
        mDbDataSource.deleteCustomEq(title);
    }

    public void renameCustomEq(String oldName,String name){
        mDbDataSource.renameCustomEq(oldName,name);
    }
}
