package com.ruichaoqun.luckymusic.ui.localmedia.fragment.songs;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class LocalMediaModule {

    @Binds
    abstract LocalMediaContact.Presenter localMediaPresenter(LocalMediaPresenter presenter);
}
