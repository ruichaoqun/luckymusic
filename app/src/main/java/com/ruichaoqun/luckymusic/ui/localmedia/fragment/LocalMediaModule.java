package com.ruichaoqun.luckymusic.ui.localmedia.fragment;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class LocalMediaModule {

    @Binds
    abstract LocalMediaContact.Presenter WanAndroidPresenter(LocalMediaPresenter presenter);
}
