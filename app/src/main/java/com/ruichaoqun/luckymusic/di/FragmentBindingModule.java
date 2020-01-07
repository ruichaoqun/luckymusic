package com.ruichaoqun.luckymusic.di;

import com.ruichaoqun.luckymusic.ui.localmedia.LocalMediaModule;
import com.ruichaoqun.luckymusic.ui.localmedia.fragment.LocalMediaFragment;
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
}
