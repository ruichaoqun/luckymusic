package com.ruichaoqun.luckymusic.ui.localmedia.fragment.artist;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ArtistListModule {

    @Binds
    abstract ArtistListContact.Presenter artistListPresenter(ArtistListPresenter presenter);
}
