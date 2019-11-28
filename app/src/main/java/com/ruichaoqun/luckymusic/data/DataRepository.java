package com.ruichaoqun.luckymusic.data;


import com.ruichaoqun.luckymusic.data.db.DbDataSource;
import com.ruichaoqun.luckymusic.data.http.HttpDataSource;
import com.ruichaoqun.luckymusic.data.preference.PreferenceDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:31
 * description:数据仓库
 */
@Singleton
public class DataRepository implements HttpDataSource, PreferenceDataSource, DbDataSource {
    private final HttpDataSource mHttpDataSource;
    private final PreferenceDataSource mPreferenceDataSource;
    private final DbDataSource mDbDataSource;

    @Inject
    public DataRepository(HttpDataSource httpDataSource, PreferenceDataSource preferenceDataSource, DbDataSource dbDataSource) {
        mHttpDataSource = httpDataSource;
        mPreferenceDataSource = preferenceDataSource;
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
}
