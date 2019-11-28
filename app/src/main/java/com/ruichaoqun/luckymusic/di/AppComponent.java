package com.ruichaoqun.luckymusic.di;

import android.app.Application;


import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.data.DataRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author Rui Chaoqun
 * @date :2019/11/27 17:41
 * description:
 */
@Singleton
@Component(modules = {
        DataRepositoryModule.class,
        ApplicationModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<LuckyMusicApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
