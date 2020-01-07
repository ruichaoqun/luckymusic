package com.ruichaoqun.luckymusic;



import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.ruichaoqun.luckymusic.common.ServiceFacade;
import com.ruichaoqun.luckymusic.di.DaggerAppComponent;
import com.ruichaoqun.luckymusic.di.daggerandroidx.DaggerApplication;
import com.ruichaoqun.luckymusic.service.ServiceConst;
import com.ruichaoqun.luckymusic.service.impl.ThemeServiceImpl;

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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ServiceFacade.put(ServiceConst.THEME_SERVICE,new ThemeServiceImpl());
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
