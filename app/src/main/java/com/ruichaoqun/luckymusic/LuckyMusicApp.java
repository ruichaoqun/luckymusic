package com.ruichaoqun.luckymusic;



import android.app.Instrumentation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.ruichaoqun.luckymusic.di.DaggerAppComponent;
import com.ruichaoqun.luckymusic.di.daggerandroidx.DaggerApplication;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeAgent;
import com.ruichaoqun.luckymusic.theme.core.ThemeConfig;

import dagger.android.AndroidInjector;

import static com.ruichaoqun.luckymusic.Constants.CHANGED_THEME;
import static com.ruichaoqun.luckymusic.Constants.CHANGE_THEME;

/**
 * @author Rui Chaoqun
 * @date :2019/10/11 11:06
 * description:
 */
public class LuckyMusicApp extends DaggerApplication {
    public static LuckyMusicApp sInstance;
    private Handler mHandler;

    public static LuckyMusicApp getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        HandlerThread handlerThread = new HandlerThread("BroadCastReceiver");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
        Logger.addLogAdapter(new AndroidLogAdapter());
        mHandler.post(initSkinBroadCast());
    }

    private Runnable initSkinBroadCast() {
        return new Runnable() {
            @Override
            public void run() {
                LocalBroadcastManager.getInstance(LuckyMusicApp.getInstance()).registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        int preThemeColor;
                        String preThemeName;
                        int intExtra = intent.getIntExtra(ThemeAgent.EXTRA_THEME_ID, -1);
                        ResourceRouter instance = ResourceRouter.getInstance();
                        int preThemeId = ThemeConfig.THEME_NIGHT;
                        if (intExtra == preThemeId) {
                            preThemeId = instance.getThemeId();
                            preThemeName = instance.getName(false);
                            preThemeColor = instance.getThemeColor();
                        } else {
                            preThemeName = "";
                            preThemeColor = Color.WHITE;
                        }
                        //保存到pref中
                        ThemeConfig.updateCurrentThemeIdAndPrevThemeInfo(intExtra, preThemeId, preThemeName, preThemeColor);
                        //更新主题
                        instance.reset();
                        context.sendBroadcast(new Intent(CHANGED_THEME));
                    }
                }, new IntentFilter(CHANGE_THEME));

            }
        };
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
