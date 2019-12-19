package com.ruichaoqun.luckymusic.di;

import com.ruichaoqun.luckymusic.ui.main.MainActivity;
import com.ruichaoqun.luckymusic.ui.main.MainModule;
import com.ruichaoqun.luckymusic.ui.splash.SplashModule;
import com.ruichaoqun.luckymusic.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity splashActivity();

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();
}
