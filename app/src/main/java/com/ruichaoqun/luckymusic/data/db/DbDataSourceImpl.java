package com.ruichaoqun.luckymusic.data.db;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;

import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.CustomEqBean;
import com.ruichaoqun.luckymusic.data.bean.CustomEqBeanDao;
import com.ruichaoqun.luckymusic.data.bean.DaoMaster;
import com.ruichaoqun.luckymusic.data.bean.DaoSession;
import com.ruichaoqun.luckymusic.data.bean.PlayListBean;
import com.ruichaoqun.luckymusic.data.bean.PlayListBeanDao;
import com.ruichaoqun.luckymusic.data.bean.PlayListSongBean;
import com.ruichaoqun.luckymusic.data.bean.PlayListSongBeanDao;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.data.bean.SongBeanDao;
import com.ruichaoqun.luckymusic.media.MediaDataType;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public List<PlayListBean> getAllPlayList() {
        return mDaoSession.getPlayListBeanDao().queryBuilder().list();
    }

    @Override
    public PlayListBean getPlayList(long id) {
        return mDaoSession.getPlayListBeanDao().load(id);
    }

    @Override
    public PlayListBean getCurrentPlayListBean() {
        List<PlayListBean> playListBean = mDaoSession.getPlayListBeanDao().queryBuilder().where(PlayListBeanDao.Properties.Name.eq("当前播放")).list();
        if(playListBean == null || playListBean.size() == 0){
            PlayListBean playListBean1 = new PlayListBean();
            playListBean1.setSongsCount(0);
            playListBean1.setName("当前播放");
            playListBean1.setCreateTime(new Date());
            playListBean1.setLastUpdateTime(new Date());
            mDaoSession.getPlayListBeanDao().insert(playListBean1);
            return playListBean1;
        }
        return playListBean.get(0);
    }


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

    @Override
    public void insertOrReplacePlayList(PlayListBean playListBean) {
        mDaoSession.getPlayListBeanDao().insertOrReplace(playListBean);
    }

    @Override
    public void insertOrReplacePlayListSongBean() {
//        mDaoSession.getPlayListSongBeanDao().insertOrReplace()
    }

    @Override
    public void updatePlayList(List<SongBean> list,long lastPlaySongId,long lastPlaySongPosition) {
        List<PlayListBean> playListBean = mDaoSession.getPlayListBeanDao().queryBuilder().where(PlayListBeanDao.Properties.Name.eq("当前播放")).list();
        if(playListBean != null && playListBean.size() == 1){
            PlayListBean playListBean1 = playListBean.get(0);
            List<PlayListSongBean> listSongBeans = playListBean1.getMPlayListSongBeans();
            //先执行删除操作
            if(listSongBeans != null){
                mDaoSession.getPlayListSongBeanDao().deleteInTx(listSongBeans);
//                for (int i = 0; i < listSongBeans.size(); i++) {
//                    mDaoSession.getPlayListSongBeanDao().deleteByKey(listSongBeans.get(i).get_id());
//                }
            }
            //删除完毕再进行添加
            listSongBeans = new ArrayList<>();
            if(list == null){
                playListBean1.setSongsCount(0);
                playListBean1.setLastPlaySongId(-1);
                playListBean1.setLastPlaySongPosition(0);
                //更新播放列表
                mDaoSession.getPlayListBeanDao().update(playListBean1);
                //调用detach后下次再从数据库获取实体时会直接访问数据库，而不是从缓存获取，这样避免其关联的list还是原先的
                mDaoSession.getPlayListBeanDao().detach(playListBean1);
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                PlayListSongBean bean = new PlayListSongBean();
                bean.setPlayListid(playListBean1.getId());
                bean.setPlayListSongId(list.get(i).getId());
                listSongBeans.add(bean);
            }
            //添加到数据库
            mDaoSession.getPlayListSongBeanDao().insertInTx(listSongBeans);
            playListBean1.setLastUpdateTime(new Date());
            playListBean1.setSongsCount(list.size());
            playListBean1.setLastPlaySongId(lastPlaySongId);
            playListBean1.setLastPlaySongPosition(lastPlaySongPosition);
            //更新播放列表
            mDaoSession.getPlayListBeanDao().update(playListBean1);
            //调用detach后下次再从数据库获取实体时会直接访问数据库，而不是从缓存获取，这样避免其关联的list还是原先的
            mDaoSession.getPlayListBeanDao().detach(playListBean1);
        }else{
            PlayListBean playBean = new PlayListBean();
            playBean.setName("当前播放");
            playBean.setCreateTime(new Date());
            playBean.setLastUpdateTime(new Date());
            playBean.setSongsCount(list.size());
            playBean.setLastPlaySongId(lastPlaySongId);
            playBean.setLastPlaySongPosition(lastPlaySongPosition);
            //插入数据库
            long id = mDaoSession.getPlayListBeanDao().insert(playBean);
            List<PlayListSongBean> listSongBeans = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                PlayListSongBean bean = new PlayListSongBean();
                bean.setPlayListid(id);
                bean.setPlayListSongId(list.get(i).getId());
                listSongBeans.add(bean);
            }
            //歌曲添加到数据库
            mDaoSession.getPlayListSongBeanDao().insertInTx(listSongBeans);
        }
    }

    @Override
    public void updatePlayLastSong(long lastPlaySongId,long lastPlaySongPosition) {
        List<PlayListBean> playListBean = mDaoSession.getPlayListBeanDao().queryBuilder().where(PlayListBeanDao.Properties.Name.eq("当前播放")).list();
        if(playListBean != null && playListBean.size() == 1){
            PlayListBean playListBean1 = playListBean.get(0);
            playListBean1.setLastUpdateTime(new Date());
            if(lastPlaySongId != -1){
                playListBean1.setLastPlaySongId(lastPlaySongId);
            }
            playListBean1.setLastPlaySongPosition(lastPlaySongPosition);
            //更新播放列表
            mDaoSession.getPlayListBeanDao().update(playListBean1);
            //调用detach后下次再从数据库获取实体时会直接访问数据库，而不是从缓存获取，这样避免其关联的list还是原先的
            mDaoSession.getPlayListBeanDao().detach(playListBean1);
        }
    }

    @Override
    public void removePlayListItem(long id){
        List<PlayListBean> playListBean = mDaoSession.getPlayListBeanDao().queryBuilder().where(PlayListBeanDao.Properties.Name.eq("当前播放")).list();
        if(playListBean != null && playListBean.size() == 1){
            PlayListBean playListBean1 = playListBean.get(0);
            List<PlayListSongBean> listSongBeans = playListBean1.getMPlayListSongBeans();
            for (int i = 0; i < listSongBeans.size(); i++) {
                if(listSongBeans.get(i).getPlayListSongId() == id){
                    mDaoSession.getPlayListSongBeanDao().deleteByKey(listSongBeans.get(i).get_id());
                    break;
                }
            }
            playListBean1.setLastUpdateTime(new Date());
            playListBean1.setSongsCount(playListBean1.getSongsCount()-1);
            //更新播放列表
            mDaoSession.getPlayListBeanDao().update(playListBean1);
            //调用detach后下次再从数据库获取实体时会直接访问数据库，而不是从缓存获取，这样避免其关联的list还是原先的
            mDaoSession.getPlayListBeanDao().detach(playListBean1);
        }
    }

    @Override
    public List<CustomEqBean> getAllCustomEq() {
        return mDaoSession.getCustomEqBeanDao().queryBuilder().list();
    }

    @Override
    public void insertCustomEq(CustomEqBean eqBean) {
        mDaoSession.getCustomEqBeanDao().insertOrReplace(eqBean);
    }

    @Override
    public void deleteCustomEq(String title) {
        CustomEqBean eqBean = mDaoSession.getCustomEqBeanDao().queryBuilder().where(CustomEqBeanDao.Properties.EqTitle.eq(title)).unique();
        if(eqBean != null){
            mDaoSession.getCustomEqBeanDao().delete(eqBean);
        }
    }

    @Override
    public void renameCustomEq(String oldName, String name) {
        CustomEqBean eqBean = mDaoSession.getCustomEqBeanDao().queryBuilder().where(CustomEqBeanDao.Properties.EqTitle.eq(oldName)).unique();
        if(eqBean != null){
            eqBean.setEqTitle(name);
            mDaoSession.getCustomEqBeanDao().update(eqBean);
        }
    }
}
