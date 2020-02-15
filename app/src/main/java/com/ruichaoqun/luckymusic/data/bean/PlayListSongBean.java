package com.ruichaoqun.luckymusic.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 歌单音乐表
 */
@Entity
public class PlayListSongBean {
    @Id
    private Long _id;                    //主键
    private long playListid;                    //歌单id
    private long playListSongId;            //歌单音乐id

    @ToOne(joinProperty = "playListSongId")
    private SongBean mSongBean;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1395073789)
    private transient PlayListSongBeanDao myDao;

    @Generated(hash = 1266152894)
    public PlayListSongBean(Long _id, long playListid, long playListSongId) {
        this._id = _id;
        this.playListid = playListid;
        this.playListSongId = playListSongId;
    }

    @Generated(hash = 429339769)
    public PlayListSongBean() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public long getPlayListid() {
        return this.playListid;
    }

    public void setPlayListid(long playListid) {
        this.playListid = playListid;
    }

    public long getPlayListSongId() {
        return this.playListSongId;
    }

    public void setPlayListSongId(long playListSongId) {
        this.playListSongId = playListSongId;
    }

    @Generated(hash = 1081970991)
    private transient Long mSongBean__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 179983034)
    public SongBean getMSongBean() {
        long __key = this.playListSongId;
        if (mSongBean__resolvedKey == null
                || !mSongBean__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SongBeanDao targetDao = daoSession.getSongBeanDao();
            SongBean mSongBeanNew = targetDao.load(__key);
            synchronized (this) {
                mSongBean = mSongBeanNew;
                mSongBean__resolvedKey = __key;
            }
        }
        return mSongBean;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2078183509)
    public void setMSongBean(@NotNull SongBean mSongBean) {
        if (mSongBean == null) {
            throw new DaoException(
                    "To-one property 'playListSongId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.mSongBean = mSongBean;
            playListSongId = mSongBean.getId();
            mSongBean__resolvedKey = playListSongId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1036555404)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlayListSongBeanDao() : null;
    }
}
