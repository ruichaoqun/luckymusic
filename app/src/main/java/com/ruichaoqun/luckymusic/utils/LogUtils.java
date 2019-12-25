package com.ruichaoqun.luckymusic.utils;

import android.util.Log;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

public class LogUtils {
    public static void d(@Nullable Object object){
        Logger.d(object);
    }

    public static void d(String tag,@Nullable Object object){
        Logger.d(tag,object);
    }

    public static void e(String tag,@Nullable String message){
        Log.e(tag,message);
    }

    public static void w(@Nullable String message){
//        Log.w(message);
    }

    public static void w(String tag,@Nullable String message){
        Log.w(tag,message);
    }

    public static void v(@Nullable String message){
        Logger.v(message);
    }

    public static void i(String tag,@Nullable String message){
        Log.i(tag,message);
    }


    public static void wtf(@Nullable String message){
        Logger.wtf(message);
    }
}
