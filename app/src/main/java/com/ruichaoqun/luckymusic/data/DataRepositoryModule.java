package com.ruichaoqun.luckymusic.data;


import com.ruichaoqun.luckymusic.data.db.DbDataSource;
import com.ruichaoqun.luckymusic.data.db.DbDataSourceImpl;
import com.ruichaoqun.luckymusic.data.http.HttpDataSource;
import com.ruichaoqun.luckymusic.data.http.HttpDataSourceImpl;
import com.ruichaoqun.luckymusic.data.media.ContentProviderSource;
import com.ruichaoqun.luckymusic.data.media.ContentProviderSourceImpl;
import com.ruichaoqun.luckymusic.data.media.MediaDataSource;
import com.ruichaoqun.luckymusic.data.media.MediaDataSourceImpl;
import com.ruichaoqun.luckymusic.data.preference.PreferenceDataSource;
import com.ruichaoqun.luckymusic.data.preference.PreferenceDataSourceImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:36
 * description:
 */
@Module
public abstract class DataRepositoryModule {

    @Singleton
    @Binds
    abstract HttpDataSource bindHttpDataSource(HttpDataSourceImpl dataSource);

    @Singleton
    @Binds
    abstract PreferenceDataSource bindPreferenceDataSource(PreferenceDataSourceImpl dataSource);

    @Singleton
    @Binds
    abstract DbDataSource bindDbDataSource(DbDataSourceImpl dataSource);

    @Singleton
    @Binds
    abstract ContentProviderSource bindContentProviderSource(ContentProviderSourceImpl dataSource);

    @Singleton
    @Binds
    abstract MediaDataSource bindMediaDataSource(MediaDataSourceImpl dataSource);
}
