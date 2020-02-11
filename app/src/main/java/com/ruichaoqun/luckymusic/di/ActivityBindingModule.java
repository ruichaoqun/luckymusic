package com.ruichaoqun.luckymusic.di;

import com.ruichaoqun.luckymusic.ui.PlayerActivity;
import com.ruichaoqun.luckymusic.ui.PlayerModule;
import com.ruichaoqun.luckymusic.ui.localmedia.LocalMediaActivity;
import com.ruichaoqun.luckymusic.ui.localmedia.LocalMediaModule;
import com.ruichaoqun.luckymusic.ui.localmedia.SongsListActivity;
import com.ruichaoqun.luckymusic.ui.localmedia.SongsListModule;
import com.ruichaoqun.luckymusic.ui.main.MainActivity;
import com.ruichaoqun.luckymusic.ui.main.MainModule;
import com.ruichaoqun.luckymusic.ui.splash.SplashModule;
import com.ruichaoqun.luckymusic.ui.splash.SplashActivity;
import com.ruichaoqun.luckymusic.ui.test.TestActivity;
import com.ruichaoqun.luckymusic.ui.test.TestModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity splashActivity();

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = TestModule.class)
    abstract TestActivity mTestActivity();

    @ContributesAndroidInjector(modules = LocalMediaModule.class)
    abstract LocalMediaActivity mLocalMediaActivity();

    @ContributesAndroidInjector(modules = PlayerModule.class)
    abstract PlayerActivity mPlayerActivity();

    @ContributesAndroidInjector(modules = SongsListModule.class)
    abstract SongsListActivity mSongsListActivity();

}
