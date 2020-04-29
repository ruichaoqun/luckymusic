package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting;

import dagger.Binds;
import dagger.Module;

/**
 * @author Rui Chaoqun
 * @date :2020-4-29 22:30:54
 * description:MVP 模板自动生成
 */
@Module
public abstract class DefaultEffectModule {

    @Binds
    abstract DefaultEffectContact.Presenter mDefaultEffectPresenter(DefaultEffectPresenter mDefaultEffectPresenter);

}
