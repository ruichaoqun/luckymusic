package com.ruichaoqun.luckymusic.di;

import com.ruichaoqun.luckymusic.ui.PlayerActivity;
import com.ruichaoqun.luckymusic.ui.PlayerModule;
import com.ruichaoqun.luckymusic.ui.equalizer.EqualizerActivity;
import com.ruichaoqun.luckymusic.ui.equalizer.EqualizerModule;
import com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.DefaultEffectActivity;
import com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.DefaultEffectModule;
import com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.savepreset.EqualizerSavePresetActivity;
import com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.savepreset.EqualizerSavePresetModule;
import com.ruichaoqun.luckymusic.ui.localmedia.LocalMediaActivity;
import com.ruichaoqun.luckymusic.ui.localmedia.LocalMediaModule;
import com.ruichaoqun.luckymusic.ui.localmedia.SongsListActivity;
import com.ruichaoqun.luckymusic.ui.localmedia.SongsListModule;
import com.ruichaoqun.luckymusic.ui.main.MainActivity;
import com.ruichaoqun.luckymusic.ui.main.MainModule;
import com.ruichaoqun.luckymusic.ui.profile.ProfileActivity;
import com.ruichaoqun.luckymusic.ui.profile.ProfileModule;
import com.ruichaoqun.luckymusic.ui.search.SearchActivity;
import com.ruichaoqun.luckymusic.ui.search.SearchModule;
import com.ruichaoqun.luckymusic.ui.splash.SplashModule;
import com.ruichaoqun.luckymusic.ui.splash.SplashActivity;
import com.ruichaoqun.luckymusic.ui.test.TestActivity;
import com.ruichaoqun.luckymusic.ui.test.TestModule;
import com.ruichaoqun.luckymusic.ui.theme.themecolor.ThemeColorDetailActivity;
import com.ruichaoqun.luckymusic.ui.theme.themecolor.ThemeColorDetailModule;
import com.ruichaoqun.luckymusic.ui.theme.themedetail.ThemeDetailActivity;
import com.ruichaoqun.luckymusic.ui.theme.themedetail.ThemeDetailModule;

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

    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchActivity mSearchActivity();

    @ContributesAndroidInjector(modules = ProfileModule.class)
    abstract ProfileActivity mProfileActivity();

    @ContributesAndroidInjector(modules = EqualizerModule.class)
    abstract EqualizerActivity mEqualizerActivity();

    @ContributesAndroidInjector(modules = DefaultEffectModule.class)
    abstract DefaultEffectActivity mDefaultEffectActivity();

    @ContributesAndroidInjector(modules = EqualizerSavePresetModule.class)
    abstract EqualizerSavePresetActivity mEqualizerSavePresetActivity();

    @ContributesAndroidInjector(modules = ThemeDetailModule.class)
    abstract ThemeDetailActivity mThemeDetailActivity();

    @ContributesAndroidInjector(modules = ThemeColorDetailModule.class)
    abstract ThemeColorDetailActivity mThemeColorDetailActivity();


}
