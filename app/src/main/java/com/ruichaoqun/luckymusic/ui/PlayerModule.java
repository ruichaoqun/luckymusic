package com.ruichaoqun.luckymusic.ui;

import dagger.Binds;
import dagger.Module;

/**
 * @author Rui Chaoqun
 * @date :2020-1-13 9:37:48
 * description:MVP 模板自动生成
 */
@Module
public abstract class PlayerModule {

    @Binds
    abstract PlayerContact.Presenter mPlayerPresenter(PlayerPresenter mPlayerPresenter);

}
