package com.ruichaoqun.luckymusic.utils;

import android.os.Build;
import android.os.Build.VERSION;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 12:01
 * description:
 */
public class CommonUtils {
    //通用圆角dp值
    public static final int COMMON_RADIUS = 5;


    public static boolean versionAbove19() {
        return VERSION.SDK_INT >= 19;
    }

    public static boolean versionBelow21() {
        return VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean g() {
        return VERSION.SDK_INT >= 23;
    }

    public static boolean h() {
        return VERSION.SDK_INT >= 25;
    }

    public static boolean i() {
        return VERSION.SDK_INT >= 26;
    }

    public static boolean j() {
        return g();
    }

    public static boolean k() {
        return VERSION.SDK_INT < 11;
    }

    public static boolean versionAbove21() {
        return VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean m() {
        return VERSION.SDK_INT == 21 || VERSION.SDK_INT == 22;
    }

    public static boolean n() {
        return VERSION.SDK_INT >= 24;
    }

    public static boolean o() {
        return VERSION.SDK_INT < 21;
    }

    public static boolean p() {
        return VERSION.SDK_INT >= 14;
    }

    public static boolean q() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean r() {
        return VERSION.SDK_INT >= 18;
    }

    public static boolean s() {
        return VERSION.SDK_INT >= 28;
    }

    public static int getScrollAngle(float f, float f2, float f3, float f4) {
        float abs = Math.abs(f - f3);
        float abs2 = Math.abs(f2 - f4);
        return Math.round((float) ((Math.asin(((double) abs2) / Math.sqrt((double) ((abs * abs) + (abs2 * abs2)))) / 3.141592653589793d) * 180.0d));
    }



}
