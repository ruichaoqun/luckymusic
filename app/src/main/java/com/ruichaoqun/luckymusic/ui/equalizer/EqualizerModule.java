package com.ruichaoqun.luckymusic.ui.equalizer;

import dagger.Binds;
import dagger.Module;

/**
 * @author Rui Chaoqun
 * @date :2020-4-12 21:11:55
 * description:MVP 模板自动生成
 */
@Module
public abstract class EqualizerModule {

    @Binds
    abstract EqualizerContact.Presenter mEqualizerPresenter(EqualizerPresenter mEqualizerPresenter);

}
