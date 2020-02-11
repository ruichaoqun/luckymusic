package com.ruichaoqun.luckymusic.di;

import com.ruichaoqun.luckymusic.ui.localmedia.LocalMediaModule;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.album.AlbumListFragment;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.album.AlbumListModule;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.artist.ArtistListFragment;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.artist.ArtistListModule;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.songs.LocalMediaFragment;
import com.ruichaoqun.luckymusic.ui.main.discover.WanAndroidFragment;
import com.ruichaoqun.luckymusic.ui.main.discover.WanAndroidModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBindingModule {
    @ContributesAndroidInjector(modules = WanAndroidModule.class)
    abstract WanAndroidFragment wanAndroidFragment();

    @ContributesAndroidInjector(modules = LocalMediaModule.class)
    abstract LocalMediaFragment mLocalMediaFragment();

    @ContributesAndroidInjector(modules = AlbumListModule.class)
    abstract AlbumListFragment albumListFragment();


    @ContributesAndroidInjector(modules = ArtistListModule.class)
    abstract ArtistListFragment artistListFragment();
}
