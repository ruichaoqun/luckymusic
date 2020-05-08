package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.savepreset;

import dagger.Binds;
import dagger.Module;

/**
 * @author Rui Chaoqun
 * @date :2020-5-8 0:41:09
 * description:MVP 模板自动生成
 */
@Module
public abstract class EqualizerSavePresetModule {

    @Binds
    abstract EqualizerSavePresetContact.Presenter mEqualizerSavePresetPresenter(EqualizerSavePresetPresenter mEqualizerSavePresetPresenter);

}
