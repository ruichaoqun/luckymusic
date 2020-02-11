package com.ruichaoqun.luckymusic.ui.localmedia;

import dagger.Binds;
import dagger.Module;

/**
 * @author Rui Chaoqun
 * @date :2020-2-11 16:55:56
 * description:MVP 模板自动生成
 */
@Module
public abstract class SongsListModule {

    @Binds
    abstract SongsListContact.Presenter mSongsListPresenter(SongsListPresenter mSongsListPresenter);

}
