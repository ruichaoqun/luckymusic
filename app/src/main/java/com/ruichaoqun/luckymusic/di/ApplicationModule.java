package com.ruichaoqun.luckymusic.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * @author Rui Chaoqun
 * @date :2019/11/27 17:40
 * description:
 */
@Module
public abstract class ApplicationModule {
    @Binds
    abstract Context bindContext(Application application);

}
