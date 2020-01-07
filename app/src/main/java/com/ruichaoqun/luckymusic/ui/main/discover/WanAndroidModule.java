package com.ruichaoqun.luckymusic.ui.main.discover;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class WanAndroidModule {

    @Binds
    abstract WanAndroidContact.Presenter wanAndroidPresenter(WanAndroidPresenter presenter);
}
