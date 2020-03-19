package com.ruichaoqun.luckymusic.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * 歌单实体类
 */
@Entity
public class PlayListBean {
    @Id
    private Long id;                    //歌单id
    private String name;                //歌单名称
    private int songsCount;             //歌单歌曲数量
    private Date createTime;            //创建日期
    private Date lastUpdateTime;        //最近更新歌单时间
    private long lastPlaySongId;         //上次本歌单最后播放音乐id
    private long lastPlaySongPosition;  //上次本歌单最后播放音乐的播放位置

    @ToMany(referencedJoinProperty = "playListid")
    private List<PlayListSongBean> mPlayListSongBeans;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 908959112)
    private transient PlayListBeanDao myDao;

    @Generated(hash = 852860174)
    public PlayListBean(Long id, String name, int songsCount, Date createTime,
            Date lastUpdateTime, long lastPlaySongId, long lastPlaySongPosition) {
        this.id = id;
        this.name = name;
        this.songsCount = songsCount;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.lastPlaySongId = lastPlaySongId;
        this.lastPlaySongPosition = lastPlaySongPosition;
    }

    @Generated(hash = 1039443864)
    public PlayListBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSongsCount() {
        return this.songsCount;
    }

    public void setSongsCount(int songsCount) {
        this.songsCount = songsCount;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public long getLastPlaySongId() {
        return this.lastPlaySongId;
    }

    public void setLastPlaySongId(long lastPlaySongId) {
        this.lastPlaySongId = lastPlaySongId;
    }

    public long getLastPlaySongPosition() {
        return this.lastPlaySongPosition;
    }

    public void setLastPlaySongPosition(long lastPlaySongPosition) {
        this.lastPlaySongPosition = lastPlaySongPosition;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 317958556)
    public List<PlayListSongBean> getMPlayListSongBeans() {
        if (mPlayListSongBeans == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PlayListSongBeanDao targetDao = daoSession.getPlayListSongBeanDao();
            List<PlayListSongBean> mPlayListSongBeansNew = targetDao
                    ._queryPlayListBean_MPlayListSongBeans(id);
            synchronized (this) {
                if (mPlayListSongBeans == null) {
                    mPlayListSongBeans = mPlayListSongBeansNew;
                }
            }
        }
        return mPlayListSongBeans;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1294295714)
    public synchronized void resetMPlayListSongBeans() {
        mPlayListSongBeans = null;
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
    @Generated(hash = 1590829182)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPlayListBeanDao() : null;
    }

}
