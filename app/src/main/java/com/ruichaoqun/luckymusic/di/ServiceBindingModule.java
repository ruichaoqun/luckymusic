package com.ruichaoqun.luckymusic.di;

import com.ruichaoqun.luckymusic.media.MusicModule;
import com.ruichaoqun.luckymusic.media.MusicService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author Rui Chaoqun
 * @date :2020/1/7 10:25
 * description:
 */
@Module
public abstract class ServiceBindingModule {
    @ContributesAndroidInjector(modules = MusicModule.class)
    abstract MusicService mMusicService();
}
