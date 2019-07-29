package com.ruichaoqun.luckymusic.theme.core;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.MainThread;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.common.MyApplication;

/**
 * @author Rui Chaoqun
 * @date :2019/7/22 15:13
 * description:
 */
public class ResourceRouter {
    private static ResourceRouter sResourceRouter;
    private Context mContext;
    private Resources mResources;
    private Drawable mCacheStatusBarDrawable;
    private Drawable mCacheToolBarDrawable;

    private Integer mPopupBackgroundColor;



    public static synchronized ResourceRouter getInstance() {
        ResourceRouter resourceRouter;
        synchronized (ResourceRouter.class) {
            if (sResourceRouter == null) {
                sResourceRouter = new ResourceRouter(MyApplication.mCtx);
            }
            resourceRouter = sResourceRouter;
        }
        return resourceRouter;
    }


    private ResourceRouter(Context context) {
        this.mContext = context;
        this.mResources = context.getResources();
    }


    @MainThread
    public Drawable getCacheStatusBarDrawable() {
        if (this.mCacheStatusBarDrawable == null) {
            buildCache();
        }
        return this.mCacheStatusBarDrawable;
    }

    public void buildCache() {
        int i;
        Drawable colorDrawable;
        Context context = this.mContext;
        this.mCacheStatusBarDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#D33A31"), Color.parseColor("#D33A31")});
        this.mCacheToolBarDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#D33A31"),Color.parseColor("#DB3F35")});
        this.mPopupBackgroundColor = Integer.valueOf(context.getResources().getColor(R.color.t_dialogBackground));
    }

    @MainThread
    public Drawable getCacheToolBarDrawable() {
        if (this.mCacheToolBarDrawable == null) {
            buildCache();
        }
        return this.mCacheToolBarDrawable.getConstantState().newDrawable();
    }

    public int getToolbarIconColor() {
        return getToolbarIconColor(false);
    }

    public int getToolbarIconColor(boolean z) {
//        if (z) {
//            return getInstance().getColorByDefaultColor(com.netease.cloudmusic.b.l);
//        }
//        if (isWhiteTheme() || isCustomLightTheme() || isCustomColorTheme()) {
//            return com.netease.cloudmusic.b.f21073e;
//        }
//        if (isNightTheme()) {
//            return 0x99FFFFFF;
//        }
        return Color.WHITE;
    }


    @MainThread
    public int getPopupBackgroundColor() {
        if (this.mPopupBackgroundColor == null) {
            buildCache();
        }
        return this.mPopupBackgroundColor.intValue();
    }




//    public int getToolbarIconColor() {
//        return getToolbarIconColor(false);
//    }

//    public int getToolbarIconColor(boolean z) {
//        if (z) {
//            return getInstance().getColorByDefaultColor(com.netease.cloudmusic.b.l);
//        }
//        if (isWhiteTheme() || isCustomLightTheme() || isCustomColorTheme()) {
//            return com.netease.cloudmusic.b.f21073e;
//        }
//        if (isNightTheme()) {
//            return -1711276033;
//        }
//        return -1;
//    }


//    public int getTitleTextColor(boolean z) {
//        return getToolbarIconColor(z);
//    }
//
//
//    public int getToolbarIconColor(boolean z) {
//        if (z) {
//            return getInstance().getColorByDefaultColor(com.netease.cloudmusic.b.l);
//        }
//        if (isWhiteTheme() || isCustomLightTheme() || isCustomColorTheme()) {
//            return com.netease.cloudmusic.b.f21073e;
//        }
//        if (isNightTheme()) {
//            return -1711276033;
//        }
//        return -1;
//    }


//
//    @ColorInt
//    public int getColorByDefaultColor(int i) {
//        return getColorByDefaultColor(i, 0);
//    }

//    @ColorInt
//    private int getColorByDefaultColor(int i, int i2) {
//        if (!isInternalTheme()) {
//            if (i2 == 0) {
//                if (i == com.netease.cloudmusic.b.f21069a) {
//                    i2 = R.color.themeColor;
//                } else if (i == com.netease.cloudmusic.b.j) {
//                    i2 = R.color.kl;
//                }
//            }
//            if (i2 != 0) {
//                int skinColorByResId = getSkinColorByResId(i2);
//                if (skinColorByResId != 0) {
//                    return skinColorByResId;
//                }
//            }
//        }
//        if (i == 0 && i2 != 0) {
//            i = this.mResources.getColor(i2);
//        }
//        if (isNightTheme()) {
//            return getNightColor(i);
//        }
//        if (isWhiteTheme() || isCustomColorTheme()) {
//            if (i != com.netease.cloudmusic.b.f21069a || !isCustomColorTheme()) {
//                return i;
//            }
//            return ThemeConfig.getCurrentColor();
//        } else if (isRedTheme()) {
//            if (i == com.netease.cloudmusic.b.f21069a) {
//                return com.netease.cloudmusic.b.f21070b;
//            }
//            if (i == com.netease.cloudmusic.b.f21071c) {
//                return com.netease.cloudmusic.b.f21072d;
//            }
//            return i;
//        } else if (!isCustomLightTheme()) {
//            return getCustomColor(i);
//        } else {
//            int i3 = this.mCompatibleColors.get(i);
//            if (i3 != 0) {
//                return i3;
//            }
//            return i;
//        }
//    }





}
