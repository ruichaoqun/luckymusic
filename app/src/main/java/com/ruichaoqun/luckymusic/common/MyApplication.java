package com.ruichaoqun.luckymusic.common;

import android.app.Application;
import android.content.Context;

/**
 * @author Rui Chaoqun
 * @date :2019/7/22 15:20
 * description:
 */
public class MyApplication extends Application {
    public static Context mCtx = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mCtx = getApplicationContext();
    }
}
