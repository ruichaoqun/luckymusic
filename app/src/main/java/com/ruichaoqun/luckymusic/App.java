package com.ruichaoqun.luckymusic;

import android.app.Application;

import com.ruichaoqun.luckymusic.utils.SharedPreferencesUtils;

/**
 * @author Rui Chaoqun
 * @date :2019/10/11 11:06
 * description:
 */
public class App extends Application {
    public static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
