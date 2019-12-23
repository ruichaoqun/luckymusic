package com.ruichaoqun.luckymusic;



import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.ruichaoqun.luckymusic.di.DaggerAppComponent;
import com.ruichaoqun.luckymusic.di.daggerandroidx.DaggerApplication;

import dagger.android.AndroidInjector;

/**
 * @author Rui Chaoqun
 * @date :2019/10/11 11:06
 * description:
 */
public class LuckyMusicApp extends DaggerApplication {
    public static LuckyMusicApp sInstance;

    public static LuckyMusicApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
