package com.ruichaoqun.luckymusic.data.db;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;

import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.DaoMaster;
import com.ruichaoqun.luckymusic.data.bean.DaoSession;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.data.bean.SongBeanDao;
import com.ruichaoqun.luckymusic.media.MediaDataType;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:47
 * description:
 */
@Singleton
public class DbDataSourceImpl implements DbDataSource{
    private final String DB_NAME = "lucky_music";
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Inject
    public DbDataSourceImpl(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        mDaoMaster = new DaoMaster(helper.getWritableDb());
        mDaoSession = mDaoMaster.newSession();
    }

    @Override
    public List<SongBean> searchSongs(String searchKey) {
        return mDaoSession.getSongBeanDao().queryBuilder().where(SongBeanDao.Properties.Title.like(searchKey)).list();
    }

    @Override
    public List<SongBean> getAllSongsFromDb() {
        return mDaoSession.getSongBeanDao().queryBuilder().list();
    }

    @Override
    public List<SongBean> getAllSongsByArtistFromDb(long id) {
        return mDaoSession.getSongBeanDao().queryBuilder().where(SongBeanDao.Properties.ArtistId.eq(id)).list();
    }

    @Override
    public List<SongBean> getAllSongsByAlbumFromDb(long id) {
        return mDaoSession.getSongBeanDao().queryBuilder().where(SongBeanDao.Properties.AlbumId.eq(id)).list();
    }

    @Override
    public List<SongBean> getAllSongsByTypeFromDb(String type, String id) {
        switch (type){
            case MediaDataType.TYPE_SONG:
                return mDaoSession.getSongBeanDao().queryBuilder().list();
            case MediaDataType.TYPE_SEARCH:
                return mDaoSession.getSongBeanDao().queryBuilder().where(SongBeanDao.Properties.Title.like(id)).list();
            case MediaDataType.TYPE_ARTIST:
                return mDaoSession.getSongBeanDao().queryBuilder().where(SongBeanDao.Properties.ArtistId.eq(Long.valueOf(id))).list();
            case MediaDataType.TYPE_ALBUM:
                return mDaoSession.getSongBeanDao().queryBuilder().where(SongBeanDao.Properties.AlbumId.eq(Long.valueOf(id))).list();
            case MediaDataType.CURRENT_PLAY_LIST:
                break;
        }
        return null;
    }

    @Override
    public List<ArtistBean> getAllArtist() {
        return mDaoSession.getArtistBeanDao().queryBuilder().list();
    }

    @Override
    public List<AlbumBean> getAllAlbum() {
        return mDaoSession.getAlbumBeanDao().queryBuilder().list();
    }

    ;

    @Override
    public void insertSongs(List<SongBean> list) {
        mDaoSession.getSongBeanDao().insertOrReplaceInTx(list);
    }

    @Override
    public void insertArtist(List<ArtistBean> list) {
        mDaoSession.getArtistBeanDao().insertOrReplaceInTx(list);
    }

    @Override
    public void insertAlbum(List<AlbumBean> list) {
        mDaoSession.getAlbumBeanDao().insertOrReplaceInTx(list);
    }
}
