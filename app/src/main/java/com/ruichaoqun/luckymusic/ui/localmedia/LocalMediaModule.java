package com.ruichaoqun.luckymusic.ui.localmedia;

import dagger.Binds;
import dagger.Module;

/**
 * @author Rui Chaoqun
 * @date :2019-12-26 9:42:11
 * description:MVP 模板自动生成
 */
@Module
public abstract class LocalMediaModule {

    @Binds
    abstract LocalMediaContact.Presenter mLocalMediaPresenter(LocalMediaPresenter mLocalMediaPresenter);

}
