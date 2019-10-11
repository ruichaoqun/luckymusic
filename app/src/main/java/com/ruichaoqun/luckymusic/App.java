package com.ruichaoqun.luckymusic;

import android.app.Application;

/**
 * @author Rui Chaoqun
 * @date :2019/10/11 11:06
 * description:
 */
public class App extends Application {
    public static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
