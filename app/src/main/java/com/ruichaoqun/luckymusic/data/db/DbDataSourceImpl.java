package com.ruichaoqun.luckymusic.data.db;

import android.support.v4.media.MediaMetadataCompat;

import com.ruichaoqun.luckymusic.data.bean.DaoMaster;

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
    private DaoMaster mDaoMaster;

    @Inject
    public DbDataSourceImpl(DaoMaster mDaoMaster) {
        this.mDaoMaster = mDaoMaster;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        mDaoMaster = new DaoMaster(helper)
    }

    @Override
    public Observable<List<MediaMetadataCompat>> searchSongs(String searchKey) {
        return null;
    }
}
