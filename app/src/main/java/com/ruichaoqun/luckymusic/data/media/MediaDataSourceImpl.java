package com.ruichaoqun.luckymusic.data.media;

import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import android.util.Log;

import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.PlayListBean;
import com.ruichaoqun.luckymusic.data.bean.PlayListSongBean;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.data.db.DbDataSource;
import com.ruichaoqun.luckymusic.media.MediaDataType;
import com.ruichaoqun.luckymusic.utils.RxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@Singleton
public class MediaDataSourceImpl implements MediaDataSource {
    private final DbDataSource mDbDataSource;
    private final ContentProviderSource mContentProviderSource;

    protected List<SongBean> localSongs;          //本地音乐
    protected List<SongBean> searchSongs;         //搜索音乐
    protected List<ArtistBean> artists;             //歌手列表
    protected List<AlbumBean> albumList;           //专辑列表
    protected List<SongBean> artistSongs;           //每个歌手对应歌曲列表
    protected List<SongBean> albumSongs;           //每个专辑对应歌曲列表

    protected PlayListBean mPlayListBean;         //从数据库获取的上次播放详情
    protected List<SongBean> mPlayListSongs;        ////从数据库获取的上次播放歌曲列表

    private int mAudioSessionId;


    @Inject
    public MediaDataSourceImpl(DbDataSource dbDataSource, ContentProviderSource contentProviderSource) {
        mDbDataSource = dbDataSource;
        mContentProviderSource = contentProviderSource;
    }


    @Override
    public Observable<List<SongBean>> rxGetAllSongs() {
        return Observable.create(new ObservableOnSubscribe<List<SongBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SongBean>> emitter) throws Exception {
                if(localSongs != null){
                    emitter.onNext(localSongs);
                    emitter.onComplete();
                }else{
                    List<SongBean> list = mDbDataSource.getAllSongsFromDb();
                    if(list != null && list.size() != 0){
                        localSongs = list;
                        emitter.onNext(list);
                        emitter.onComplete();
                    }else{
                        list = mContentProviderSource.getAllSongs();
                        mDbDataSource.insertSongs(list);
                        localSongs = list;
                        emitter.onNext(list);
                        emitter.onComplete();
                    }
                }
            }
        }).compose(RxUtils.transformerThread());
    }

    @Override
    public Observable<List<SongBean>> rxSearchSongs(String searchKey) {
        return Observable.create(new ObservableOnSubscribe<List<SongBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SongBean>> emitter) throws Exception {
                List<SongBean> list = mDbDataSource.searchSongs(searchKey);
                searchSongs = list;
                emitter.onNext(list);
                emitter.onComplete();
            }
        }).compose(RxUtils.transformerThread());
    }

    @Override
    public Observable<List<ArtistBean>> rxGetAllArtist() {
        return Observable.create(new ObservableOnSubscribe<List<ArtistBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ArtistBean>> emitter) throws Exception {
                if(artists != null){
                    emitter.onNext(artists);
                    emitter.onComplete();
                }else{
                    List<ArtistBean> list = mDbDataSource.getAllArtist();
                    if(list != null && list.size() != 0){
                        artists = list;
                        emitter.onNext(list);
                        emitter.onComplete();
                    }else{
                        list = mContentProviderSource.getAllArtist();
                        mDbDataSource.insertArtist(list);
                        artists = list;
                        emitter.onNext(list);
                        emitter.onComplete();
                    }
                }
            }
        });
    }

    @Override
    public Observable<List<AlbumBean>> rxGetAllAlbum() {
        return Observable.create(new ObservableOnSubscribe<List<AlbumBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AlbumBean>> emitter) throws Exception {
                if(albumList != null){
                    emitter.onNext(albumList);
                    emitter.onComplete();
                }else{
                    List<AlbumBean> list = mDbDataSource.getAllAlbum();
                    if(list != null && list.size() != 0){
                        albumList = list;
                        emitter.onNext(list);
                        emitter.onComplete();
                    }else{
                        list = mContentProviderSource.getAllAlbum();
                        mDbDataSource.insertAlbum(list);
                        albumList = list;
                        emitter.onNext(list);
                        emitter.onComplete();
                    }
                }
            }
        });
    }


    @Override
    public Observable<List<SongBean>> rxGetSongsFromArtist(long id) {

        return Observable.create(new ObservableOnSubscribe<List<SongBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SongBean>> emitter) throws Exception {
                List<SongBean> list = mDbDataSource.getAllSongsByArtistFromDb(id);
                artistSongs = list;
                emitter.onNext(list);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<List<SongBean>> rxGetSongsFromAlbum(long id) {
        return Observable.create(new ObservableOnSubscribe<List<SongBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SongBean>> emitter) throws Exception {
                List<SongBean> list = mDbDataSource.getAllSongsByAlbumFromDb(id);
                albumSongs = list;
                emitter.onNext(list);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<List<SongBean>> rxGetSongsFromType(String type, String id) {
        switch (type){
            case MediaDataType.TYPE_SONG:
                return rxGetAllSongs();
            case MediaDataType.TYPE_SEARCH:
                return  rxSearchSongs(id);
            case MediaDataType.TYPE_ARTIST:
                return rxGetSongsFromArtist(Long.valueOf(id));
            case MediaDataType.TYPE_ALBUM:
                return rxGetSongsFromAlbum(Long.valueOf(id));
            case MediaDataType.CURRENT_PLAY_LIST:
                break;
        }
        return null;
    }

    @Override
    public Observable<PlayListBean> rxGetCurrentPlayList() {
        return Observable.create(new ObservableOnSubscribe<PlayListBean>() {
            @Override
            public void subscribe(ObservableEmitter<PlayListBean> emitter) throws Exception {
                PlayListBean playListBean = mDbDataSource.getCurrentPlayListBean();
                if(playListBean != null){
                    mPlayListBean = playListBean;
                    List<PlayListSongBean> songBeanList = playListBean.getMPlayListSongBeans();
                    if(songBeanList != null){
                        mPlayListSongs = new ArrayList<>();
                        for (int i = 0; i < songBeanList.size(); i++) {
                            mPlayListSongs.add(songBeanList.get(i).getMSongBean());
                        }
                    }
                }
                emitter.onNext(playListBean);
                emitter.onComplete();
            }
        });
    }

    @Override
    public List<ArtistBean> getAllArtist() {
        return null;
    }

    @Override
    public List<SongBean> getAllSongs() {
        return localSongs;
    }

    @Override
    public List<SongBean> searchSongs(String searchKey) {
        return searchSongs;
    }

    @Override
    public List<SongBean> getSongsFromArtist(long id) {
        return artistSongs;
    }

    @Override
    public List<SongBean> getSongsFromAlbum(long id) {
        return albumSongs;
    }

    @Override
    public List<SongBean> getSongsFromType(String type, String id) {
        switch (type){
            case MediaDataType.TYPE_SONG:
                return localSongs;
            case MediaDataType.TYPE_SEARCH:
                return  searchSongs;
            case MediaDataType.TYPE_ARTIST:
                return artistSongs;
            case MediaDataType.TYPE_ALBUM:
                return albumSongs;
            case MediaDataType.CURRENT_PLAY_LIST:
                return mPlayListSongs;
        }
        return null;
    }

    @Override
    public List<AlbumBean> getAllAlbum() {
        return albumList;
    }

    @Override
    public long getLastPosition(String type) {
        if(TextUtils.equals(type,MediaDataType.CURRENT_PLAY_LIST ) && mPlayListBean != null){
            return mPlayListBean.getLastPlaySongPosition();
        }
        return 0;
    }

    @Override
    public void updatePlayList(List<SongBean> list, long lastPlaySongId, long lastPlaySongPosition) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                mDbDataSource.updatePlayList(list,lastPlaySongId,lastPlaySongPosition);
            }
        }).subscribe();
    }

    @Override
    public void updatePlayLastSong(long lastPlaySongId, long lastPlaySongPosition) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                mDbDataSource.updatePlayLastSong(lastPlaySongId,lastPlaySongPosition);
            }
        }).subscribe();
    }

    @Override
    public void removePlayListItem(long id) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                mDbDataSource.removePlayListItem(id);
            }
        }).subscribe();
    }

    @Override
    public int getAudioSessionId() {
        return mAudioSessionId;
    }

    @Override
    public void setAudioSessionId(int audioSessionId) {
        this.mAudioSessionId = audioSessionId;
    }


}
