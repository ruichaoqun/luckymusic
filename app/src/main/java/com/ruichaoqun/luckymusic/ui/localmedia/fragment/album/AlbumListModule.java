package com.ruichaoqun.luckymusic.ui.localmedia.fragment.album;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AlbumListModule {

    @Binds
    abstract AlbumListContact.Presenter albumListPresenter(AlbumListPresenter presenter);
}
