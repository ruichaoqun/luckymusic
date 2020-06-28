package com.ruichaoqun.luckymusic.theme.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.graphics.drawable.DrawableWrapper;

import android.util.Log;
import android.util.SparseIntArray;


import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.ThemeInfo;
import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

/**
 * @author Rui Chaoqun
 * @date :2019/7/22 15:13
 * description:
 */
public class ResourceRouter {

    public static final int BANNER_BG_HEIGHT = UiUtils.dp2px(108.0f);
    private static final int BG_MASK_COLOR = 0xFF000000;
    private static final int CUSTOMBG_DEFAULT_THEMECOLOR = 0xFFDDDDDD;
//    private static final int CUSTOMBG_MIN_HEIGHT = ((((QueueData.pause(ApplicationWrapper.getInstance()) + QueueData.onColorGet(ApplicationWrapper.getInstance())) + ApplicationWrapper.getInstance().getResources().getDimensionPixelSize(R.dimen.nb)) + ApplicationWrapper.getInstance().getResources().getDimensionPixelSize(R.dimen.jd)) + BANNER_BG_HEIGHT);
    private static final float CUSTOMBG_THEMECOLOR_MIN_SATURATION = 0.15f;
    private static final float CUSTOMBG_THEMECOLOR_MIN_VALUE = 0.7f;
    private static final int PLAYLIST_BLUR_DELTA = 30;
    private static final String SKIN_PACKAGENAME = "com.netease.cloudmusic.skin";
    private static final int TOP_BOTTOM_BLUR_DELTA = 20;
    private static ResourceRouter sResourceRouter;
    private Drawable mCacheBannerBgDrawable;
    private Drawable mCacheBgBlurDrawable;
    private Drawable mCacheBgDrawable;
    private Drawable mCacheCardBannerBgDrawable;
    private Drawable mCacheDiscoveryRefreshBtnDrawable;
    private Drawable mCacheDrawerBgDrawable;
    private Drawable mCacheDrawerBottomDrawable;
    private Drawable mCacheMessageBarDrawable;
    private Drawable mCacheNoTabBannerBgDrawable;
    private Drawable mCacheOperationBottomDrawable;
    private Drawable mCachePlayerDrawable;
    private Drawable mCachePlaylistDrawable;
    private Drawable mCachePlaylistToolBarDrawable;
    private Drawable mCacheStatusBarDrawable;
    private Drawable mCacheTabDrawable;
    private Drawable mCacheTabForTopDrawable;
    private Drawable mCacheToolBarDrawable;
    private SparseIntArray mCompatibleColors = new SparseIntArray();
    private Context mContext;
    private SparseIntArray mCustomColors = new SparseIntArray();
    private SparseIntArray mIconCustomColors = new SparseIntArray();
    private SparseIntArray mIconNightColors = new SparseIntArray();
    private SparseIntArray mIconStatusPressedColors = new SparseIntArray();
    private SparseIntArray mIconStatusUnableColors = new SparseIntArray();
    private SparseIntArray mNightColors = new SparseIntArray();
    private SparseIntArray mNotWhiteNightColors = new SparseIntArray();
    private Integer mPopupBackgroundColor;
    private Resources mResources;
    private Integer mThemeCustomBgColor;
    private ThemeInfo mThemeInfo;
    private Boolean mThemeIsLightTheme = null;
    private SparseIntArray mThemeResourceIdentifiers = new SparseIntArray();
    private Resources mThemeResources;

    public static synchronized ResourceRouter getInstance() {
        ResourceRouter resourceRouter;
        synchronized (ResourceRouter.class) {
            if (sResourceRouter == null) {
                sResourceRouter = new ResourceRouter(LuckyMusicApp.sInstance);
                sResourceRouter.reset();
            }
            resourceRouter = sResourceRouter;
        }
        return resourceRouter;
    }

    private ResourceRouter(Context context) {
        this.mContext = context;
        this.mResources = context.getResources();

        this.mNightColors.put(getResourceColor(R.color.themeColor), getResourceColor(R.color.theme_color_PrimaryDark));
        this.mNightColors.put(getResourceColor(R.color.normalC1), 0x72FFFFFF);
        this.mNightColors.put(getResourceColor(R.color.normalC2), 0X4CFFFFFF);
        this.mNightColors.put(getResourceColor(R.color.normalC3), 0x33FFFFFF);
        this.mNightColors.put(getResourceColor(R.color.normalC4), 0x26FFFFFF);
        this.mNightColors.put(getResourceColor(R.color.normalC5), 0x11FFFFFF);
        this.mNightColors.put(0xFF333333, 0x72FFFFFF);
        this.mNightColors.put(0xFF666666, 0X4CFFFFFF);
        this.mNightColors.put(0xFF888888, 0x33FFFFFF);
        this.mNightColors.put(0xFF828282, 0x33FFFFFF);
        this.mNightColors.put(0xFF999999, 0x26FFFFFF);
        this.mNightColors.put(0xFFA2A4A6, 0x26FFFFFF);
        this.mNightColors.put(0xFFB2B2B2, 0x11FFFFFF);
        this.mNightColors.put(0xFFCCCCCC, 0x11FFFFFF);
        this.mNightColors.put(0xFFC9C9C9, 0x11FFFFFF);
        this.mNightColors.put(0xFFD9D9D9, 0x11FFFFFF);
        this.mNightColors.put(0x19000000, 0xDFFFFFFF);
        this.mNightColors.put(0xFFF2F4F5, 0X4CFFFFFF);
        this.mNightColors.put(0xFFE6E8E9, 0xFF151618);
        this.mNightColors.put(getResourceColor(R.color.normalCLink), 0xFF486F9A);
        this.mNightColors.put(0XFFFE672E, 0XFFFE672E);
        this.mNightColors.put(getResourceColor(R.color.normalCDownload), 0xFF2B85B5);
        this.mNightColors.put(getResourceColor(R.color.normalImageC1), 0X8CFFFFFF);
        this.mNightColors.put(getResourceColor(R.color.normalImageC2), 0x72FFFFFF);
        this.mNightColors.put(getResourceColor(R.color.normalImageC3), 0x59FFFFFF);
        this.mNightColors.put(getResourceColor(R.color.normalImageC4), 0X4CFFFFFF);
        this.mNightColors.put(getResourceColor(R.color.normalImageC5), 0x33FFFFFF);
        this.mNightColors.put(getResourceColor(R.color.normalImageC6), 0x26FFFFFF);

        this.mCustomColors.put(getResourceColor(R.color.normalC1), Color.WHITE);
        this.mCustomColors.put(getResourceColor(R.color.normalC2), 0xCCFFFFFF);
        this.mCustomColors.put(getResourceColor(R.color.normalC3), 0XA5FFFFFF);
        this.mCustomColors.put(getResourceColor(R.color.normalC4), 0X8CFFFFFF);
        this.mCustomColors.put(getResourceColor(R.color.normalC5), 0x59FFFFFF);
        this.mCustomColors.put(getResourceColor(R.color.normalC5), 0x33FFFFFF);
        this.mCustomColors.put(0xFF333333, Color.WHITE);
        this.mCustomColors.put(0xFF666666, 0xCCFFFFFF);
        this.mCustomColors.put(0xFF888888, 0XA5FFFFFF);
        this.mCustomColors.put(0xFF828282, 0XA5FFFFFF);
        this.mCustomColors.put(0xFF999999, 0X8CFFFFFF);
        this.mCustomColors.put(0xFFA2A4A6, 0X8CFFFFFF);
        this.mCustomColors.put(0xFFB2B2B2, 0x59FFFFFF);
        this.mCustomColors.put(0xFFCCCCCC, 0x59FFFFFF);
        this.mCustomColors.put(0xFFC9C9C9, 0x59FFFFFF);
        this.mCustomColors.put(0xFFD9D9D9, 0x33FFFFFF);
        this.mCustomColors.put(getResourceColor(R.color.normalCLink), 0xFF85B9E6);
        this.mCustomColors.put(0XFFFE672E, 0xCCFFFFFF);
        this.mCustomColors.put(getResourceColor(R.color.normalCDownload), 0xFF6AC4F5);
        this.mCustomColors.put(getResourceColor(R.color.normalImageC1), getResourceColor(R.color.normalImageC1));
        this.mCustomColors.put(getResourceColor(R.color.normalImageC2), getResourceColor(R.color.normalImageC2));
        this.mCustomColors.put(getResourceColor(R.color.normalImageC3), getResourceColor(R.color.normalImageC3));
        this.mCustomColors.put(getResourceColor(R.color.normalImageC4), getResourceColor(R.color.normalImageC4));
        this.mCustomColors.put(getResourceColor(R.color.normalImageC5), getResourceColor(R.color.normalImageC5));
        this.mCustomColors.put(getResourceColor(R.color.normalImageC6), getResourceColor(R.color.normalImageC6));

        this.mIconNightColors.put(getResourceColor(R.color.normalCLink), 0xFF486F9A);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorNormal1), 0X66FFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorNormal2), 0x3FFFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorNormal3), 0x33FFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorNormal4), 0X26FFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorNormal5), 0X19FFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorNormal6), 0X11FFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorPressed1), 0X99FFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorPressed2), 0X72FFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorPressed3), 0X66FFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorPressed4), 0X4CFFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorPressed5), 0X4CFFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorPressed6), 0X44FFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorUnable1), 0X33FFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorUnable2), 0X14FFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorUnable3), 0X0FFFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorUnable3), 0X0AFFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorUnable4), 0X0AFFFFFF);
        this.mIconNightColors.put(getResourceColor(R.color.iconColorUnable6), 0X0AFFFFFF);


        this.mIconCustomColors.put(getResourceColor(R.color.normalCLink), 0XFF85B9E6);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorNormal1), 0XD8FFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorNormal2), 0XBFFFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorNormal3), 0XA5FFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorNormal4), 0X8CFFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorNormal5), 0X66FFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorNormal6), 0X4CFFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorPressed1), Color.WHITE);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorPressed2), 0xF2FFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorPressed3), 0XD8FFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorPressed4), 0XBFFFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorPressed5), 0x99FFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorPressed6), 0x7FFFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorUnable1), 0x42FFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorUnable2), 0x3AFFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorUnable3), 0x33FFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorUnable4), 0x2BFFFFFF);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorUnable5), 520093695);
        this.mIconCustomColors.put(getResourceColor(R.color.iconColorUnable6), 520093695);

        this.mIconStatusUnableColors.put(getResourceColor(R.color.iconColorNormal1), getResourceColor(R.color.iconColorUnable1));
        this.mIconStatusUnableColors.put(getResourceColor(R.color.iconColorNormal2), getResourceColor(R.color.iconColorUnable2));
        this.mIconStatusUnableColors.put(getResourceColor(R.color.iconColorNormal3), getResourceColor(R.color.iconColorUnable3));
        this.mIconStatusUnableColors.put(getResourceColor(R.color.iconColorNormal4), getResourceColor(R.color.iconColorUnable4));
        this.mIconStatusUnableColors.put(getResourceColor(R.color.iconColorNormal5), getResourceColor(R.color.iconColorUnable5));
        this.mIconStatusUnableColors.put(getResourceColor(R.color.iconColorNormal6), getResourceColor(R.color.iconColorUnable6));

        this.mIconStatusPressedColors.put(getResourceColor(R.color.iconColorNormal1),getResourceColor(R.color.iconColorPressed1));
        this.mIconStatusPressedColors.put(getResourceColor(R.color.iconColorNormal2), getResourceColor(R.color.iconColorPressed2));
        this.mIconStatusPressedColors.put(getResourceColor(R.color.iconColorNormal3), getResourceColor(R.color.iconColorPressed3));
        this.mIconStatusPressedColors.put(getResourceColor(R.color.iconColorNormal4), getResourceColor(R.color.iconColorPressed4));
        this.mIconStatusPressedColors.put(getResourceColor(R.color.iconColorNormal5), getResourceColor(R.color.iconColorPressed5));
        this.mIconStatusPressedColors.put(getResourceColor(R.color.iconColorNormal6), getResourceColor(R.color.iconColorPressed6));

        this.mNotWhiteNightColors.put(Color.WHITE, 0x33FFFFFF);
        this.mCompatibleColors.put(0xFF333333, getResourceColor(R.color.normalC1));
        this.mCompatibleColors.put(0xFF666666, getResourceColor(R.color.normalC2));
        this.mCompatibleColors.put(0xFF888888, getResourceColor(R.color.normalC3));
        this.mCompatibleColors.put(0xFF999999, getResourceColor(R.color.normalC4));
        this.mCompatibleColors.put(0xFFCCCCCC, getResourceColor(R.color.normalC5));

    }

    private int getResourceColor(int colorRes){
        return LuckyMusicApp.getInstance().getResources().getColor(colorRes);
    }

    public void reset() {
        clearCache();
        this.mThemeResources = null;
        this.mThemeInfo = new ThemeInfo(ThemeConfig.getCurrentThemeId());
        int id = this.mThemeInfo.getId();
        if (id == ThemeConfig.THEME_DEFAULT) {
            this.mThemeInfo.setName(this.mContext.getString(R.string.default_theme));
        } else if (id == ThemeConfig.THEME_CUSTOM_COLOR) {
            this.mThemeInfo.setName(this.mContext.getString(R.string.custom_color));
        } else if (id == ThemeConfig.THEME_NIGHT) {
            this.mThemeInfo.setName(this.mContext.getString(R.string.night_mode));
        } else if (id == ThemeConfig.THEME_CUSTOM_BG) {
            this.mThemeInfo.setName(this.mContext.getString(R.string.custom_skin));
        } else if (id == ThemeConfig.THEME_RED) {
            this.mThemeInfo.setName(this.mContext.getString(R.string.classics_red));
        } else {
            //TODO 皮肤包
//            this.mThemeInfo.setName(ThemeCache.getInstance().getThemeNameById(id));
        }
//        if (!this.mThemeInfo.isInternal()) {
//            linkResourceFiles();
//        }
        this.mCustomColors.delete(getResourceColor(R.color.themeColor));

    }

    private void clearCache() {
        this.mCachePlaylistToolBarDrawable = null;
        this.mCachePlaylistDrawable = null;
        this.mCacheDrawerBottomDrawable = null;
        this.mCacheDrawerBgDrawable = null;
        this.mCacheMessageBarDrawable = null;
        this.mCacheOperationBottomDrawable = null;
        this.mCachePlayerDrawable = null;
        this.mCacheToolBarDrawable = null;
        this.mCacheStatusBarDrawable = null;
        this.mCacheTabDrawable = null;
        this.mCacheBgDrawable = null;
        this.mCacheBgBlurDrawable = null;
        this.mCacheTabForTopDrawable = null;
        this.mCacheBannerBgDrawable = null;
        this.mCacheCardBannerBgDrawable = null;
        this.mCacheTabForTopDrawable = null;
        this.mCacheBannerBgDrawable = null;
        this.mCacheNoTabBannerBgDrawable = null;
        this.mCacheDiscoveryRefreshBtnDrawable = null;
        this.mThemeCustomBgColor = null;
        this.mPopupBackgroundColor = null;
        this.mThemeResourceIdentifiers.clear();
        this.mThemeIsLightTheme = null;
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
        Drawable drawable;
        Drawable colorDrawable;
        Context context = this.mContext;
        boolean isRedTheme = isRedTheme();
        if(isGeneralRuleTheme()){
            this.mCacheBgDrawable = new ColorDrawable(context.getResources().getColor(R.color.white));
            this.mCacheBgBlurDrawable = this.mCacheBgDrawable.getConstantState().newDrawable();
            this.mCacheDrawerBgDrawable = this.mCacheBgDrawable.getConstantState().newDrawable();
            if (isRedTheme) {
                drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0xFFD33A31, ThemeConfig.COLOR_RED_TOOLBAR_END});
            } else {
                drawable = new ColorDrawable(Color.WHITE);
            }
            this.mCacheToolBarDrawable = drawable;
            this.mCacheStatusBarDrawable = isRedTheme ? new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0xffd33a31, 0xFFD33A31}) : this.mCacheToolBarDrawable.getConstantState().newDrawable();
            this.mPopupBackgroundColor = Integer.valueOf(context.getResources().getColor(R.color.t_dialogBackground));
            this.mCacheOperationBottomDrawable = ThemeHelper.getBgSelectorWithDrawalbe(context, new ColorDrawable(0XF9FFFFFF));
            this.mCacheDrawerBottomDrawable = this.mCacheOperationBottomDrawable.getConstantState().newDrawable();
            this.mCachePlayerDrawable = ThemeHelper.getRippleDrawable(context, new SizeExplicitDrawable(this.mCacheOperationBottomDrawable.getConstantState().newDrawable(), context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDimensionPixelOffset(R.dimen.mini_player_bar_height)));
            this.mThemeCustomBgColor = 0;
        }else if(isNightTheme()){
            this.mCacheBgDrawable = new ColorDrawable(context.getResources().getColor(R.color.night_theme_bg));
            this.mCacheBgBlurDrawable = this.mCacheBgDrawable.getConstantState().newDrawable();
            this.mCacheDrawerBgDrawable = this.mCacheBgDrawable.getConstantState().newDrawable();
            this.mCacheToolBarDrawable = new ColorDrawable(context.getResources().getColor(R.color.night_toolbar_drawable));
            this.mCacheStatusBarDrawable = this.mCacheToolBarDrawable.getConstantState().newDrawable();
            this.mPopupBackgroundColor = Integer.valueOf(context.getResources().getColor(R.color.night_dialog_background));
            this.mCacheOperationBottomDrawable = ThemeHelper.getBgSelectorWithDrawalbe(context, new ColorDrawable(context.getResources().getColor(R.color.night_toolbar_drawable)));
            this.mCacheDrawerBottomDrawable = this.mCacheOperationBottomDrawable.getConstantState().newDrawable();
            this.mCachePlayerDrawable = ThemeHelper.getRippleDrawable(context, new SizeExplicitDrawable(this.mCacheOperationBottomDrawable.getConstantState().newDrawable(), context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDimensionPixelOffset(R.dimen.dp_49)));
            this.mThemeCustomBgColor = 0;
        }
    }


    public Drawable getCacheDrawerBottomDrawable() {
        if (this.mCacheDrawerBottomDrawable == null) {
            buildCache();
        }
        return this.mCacheDrawerBottomDrawable;
    }

    public Drawable getCacheDrawerBgDrawable() {
        if (this.mCacheDrawerBgDrawable == null) {
            buildCache();
        }
        return this.mCacheDrawerBgDrawable;
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

    public int getToolbarIconColor(boolean isToolbarOnImage) {
        if (isToolbarOnImage) {
            return getInstance().getColorByDefaultColor(mResources.getColor(R.color.white));
        }
        if(isRedTheme()){
            return mResources.getColor(R.color.white);
        }
        if (isWhiteTheme() || isCustomLightTheme() || isCustomColorTheme()) {
            return mResources.getColor(R.color.color_cc000000);
        }
        return isNightTheme() ? 0x99FFFFFF : Color.WHITE;
    }


    public int getDividerColor() {
        if (isNightTheme()) {
            return 0x06FFFFFF;
        }
        if (isCustomDarkTheme() || isCustomBgTheme()) {
            return 0xDFFFFFF;
        }
        return 0x0A000000;
    }



    @MainThread
    public int getPopupBackgroundColor() {
        if (this.mPopupBackgroundColor == null) {
            buildCache();
        }
        return this.mPopupBackgroundColor.intValue();
    }


    public boolean isInternalTheme() {
        return this.mThemeInfo.getId() <= ThemeConfig.THEME_DEFAULT;
    }


    public boolean isNightTheme() {
        return this.mThemeInfo.getId() == ThemeConfig.THEME_NIGHT;
    }


    public boolean isCustomDarkTheme() {
        boolean z = true;
        if (isInternalTheme()) {
            return false;
        }
        if (this.mThemeIsLightTheme != null) {
            if (this.mThemeIsLightTheme.booleanValue()) {
                z = false;
            }
            return z;
        }
        try {
            int resourceIdentifier = getResourceIdentifier(R.bool.t_isLightTheme);
            if (resourceIdentifier <= 0) {
                return false;
            }
            this.mThemeIsLightTheme = Boolean.valueOf(this.mThemeResources.getBoolean(resourceIdentifier));
            if (this.mThemeIsLightTheme.booleanValue()) {
                z = false;
            }
            return z;
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
            return false;
        }

    }

    private int getResourceIdentifier(int i) {
        if (this.mThemeResourceIdentifiers.indexOfKey(i) >= 0) {
            return this.mThemeResourceIdentifiers.get(i);
        }
        int identifier = this.mThemeResources.getIdentifier(this.mResources.getResourceEntryName(i), this.mResources.getResourceTypeName(i),"com.ruichaoqun.luckymusic");
        if (identifier <= 0) {
            return identifier;
        }
        this.mThemeResourceIdentifiers.put(i, identifier);
        return identifier;
    }


    public boolean isCustomBgTheme() {
        return this.mThemeInfo.getId() == ThemeConfig.THEME_CUSTOM_BG;

    }

    public int getThemeColor() {
        return getColor(R.color.themeColor);
    }

    public int getColor(int colorRes) {
        return getColorByDefaultColor(0, colorRes);
    }

    public int getColorByDefaultColor(int colorInt) {
        return getColorByDefaultColor(colorInt, 0);
    }

    private int getColorByDefaultColor(int colorInt, int colorRes) {
        int skinColorByResId;
//        if (!isInternalTheme()) {
//            if (colorRes == 0) {
//                if (i2 == c.f59025a) {
//                    colorRes = R.color.themeColor;
//                } else if (i2 == getResourceColor(R.color.normalCLink)) {
//                    colorRes = R.color.oj;
//                }
//            }
//            if (!(colorRes == 0 || (skinColorByResId = getSkinColorByResId(colorRes)) == 0)) {
//                return skinColorByResId;
//            }
//        }
        if (colorInt == 0 && colorRes != 0) {
            colorInt = this.mResources.getColor(colorRes);
        }
        if (isNightTheme()) {
            return getNightColor(colorInt);
        }
        if (isWhiteTheme() || isCustomColorTheme()) {
            return (colorInt !=  mResources.getColor(R.color.themeColor) || !isCustomColorTheme()) ? colorInt : ThemeConfig.getCurrentColor();
        }
        if (isRedTheme()) {
            if (colorInt == mResources.getColor(R.color.themeColor)) {
                return colorInt;
            }
            return colorInt == mResources.getColor(R.color.themeRedColorForShader) ? mResources.getColor(R.color.old_theme_shader) : colorInt;
        } else if (!isCustomLightTheme()) {
            return getCustomColor(colorInt);
        } else {
            int i4 = this.mCompatibleColors.get(colorInt);
            return i4 != 0 ? i4 : colorInt;
        }

    }

    public int getCustomColor(int colorInt) {
        int i = mResources.getColor(R.color.themeColor);
        if (this.mCustomColors.indexOfKey(i) < 0) {
            this.mCustomColors.put(i, getThemeCustomBgColor());
        }
        int i3 = this.mCustomColors.get(colorInt);
        return i3 != 0 ? i3 : colorInt;
    }

    public int getThemeCustomBgColor() {
        if (this.mThemeCustomBgColor == null) {
            buildCache();
        }
        return this.mThemeCustomBgColor.intValue();
    }





    public int getThemeColorWithNight() {
        return 0;
    }

    public boolean isCustomLightTheme() {
        if (isInternalTheme()) {
            return false;
        }
        return !isCustomDarkTheme();
    }

    public Drawable getDrawable(int i) {
        return null;
    }

    public boolean isDefaultTheme() {
        return isWhiteTheme();
    }


    public boolean isWhiteTheme() {
        return mThemeInfo.getId() == ThemeConfig.THEME_DEFAULT;
    }

    public boolean isRedTheme() {
        return this.mThemeInfo.getId() == -5;
    }

    public boolean isGeneralRuleTheme() {
        return isWhiteTheme() || isRedTheme() || isCustomColorTheme();
    }

    public int getThemeId() {
        return mThemeInfo.getId();
    }

    public int getBgMaskDrawableColor(int i) {
        return 0;
    }

    public ColorDrawable getBgMaskDrawable(int i) {
        return null;
    }

    public int getIconUnableColor(int i) {
        return 0;
    }

    public int getIconPressedColor(int i) {
        return 0;
    }

    public int getNightColor(int colorInt) {
        int color = this.mNightColors.get(colorInt);
        return color != 0 ? color : colorInt;
    }

    /**
     * 获取分隔线颜色
     */
    public int getLineColor() {
        if (isNightTheme()) {
            return 0x0CFFFFFF;
        }
        if (isGeneralRuleTheme() || isCustomLightTheme()) {
            return 0x19000000;
        }
        return 0x26FFFFFF;
    }

    public boolean isCustomColorTheme() {
        return mThemeInfo.getId() == ThemeConfig.THEME_CUSTOM_COLOR;
    }

    public int getIconColorByDefaultColor(int i) {
        return 0;
    }

    /**
     * 获取自定义
     * @param i
     * @return
     */
    public int getIconCustomColor(int i) {
        return 0;
    }

    public int getIconNightColor(int color) {
        int nightColor = this.mIconNightColors.get(color);
        return nightColor != 0 ? nightColor : color;
    }

    public int getColorByDefaultColorJustNight(int i) {
        return 0;
    }

    public boolean isCompatibleColor(int i) {
        return false;
    }

    public String getName(boolean z) {
        return (!z || !isNightTheme()) ? this.mThemeInfo.getName() : ThemeConfig.getPrevThemeName();
    }


    public Drawable getCachePlayerDrawable() {
        if (this.mCachePlayerDrawable == null) {
            buildCache();
        }
        return ThemeHelper.wrapTopOrBottomLineBackground(this.mCachePlayerDrawable.getConstantState().newDrawable(), true);
    }

    public Drawable getCacheBgBlurDrawable() {
        if (this.mCacheBgBlurDrawable == null) {
            buildCache();
        }
        return this.mCacheBgBlurDrawable.getConstantState().newDrawable();

    }

    public int[] getThemeColorBackgroundColorAndIconColor() {
        if (isCustomBgOrDarkThemeWhiteColor()) {
            return new int[]{getThemeColor(), 0xFF333333};
        }
        return new int[]{getThemeColorWithDarken(), Color.WHITE};

    }

    public boolean isCustomBgOrDarkThemeWhiteColor() {
        return getThemeColor() == Color.WHITE;
    }

    public Drawable getCacheBannerBgDrawable() {
        return null;
    }

    public Drawable getCacheNoTabBannerBgDrawable() {
        return null;
    }

    public int getThemeColorWithCustomBgWhiteColor() {
        return 0;
    }

    public int getThemeColorWithDarken() {
        int themeColor = getThemeColor();
        return needDark() ? ColorUtil.a(themeColor,0.9f,new float[3]) : themeColor;
    }

    public Drawable getCacheTabDrawable() {
        return null;
    }

    public Drawable getCacheTabForTopDrawable() {
        return null;
    }

    public boolean needDark() {
        return isCustomDarkTheme() || isCustomBgTheme();
    }

    public int getThemeNormalColor() {
        return 0;
    }

    public int getTitleTextColor(boolean isToolbarOnImage) {
        return getToolbarIconColor(isToolbarOnImage);
    }

    public int getRippleColor() {
        if(isNightTheme() || isCustomDarkTheme()){
            return 0x1FFFFFFF;
        }
        return 0x1F000000;
    }


//    public int getToolbarIconColor() {
//        return getToolbarIconColor(false);
//    }

//    public int getToolbarIconColor(boolean z) {
//        if (z) {
//            return getInstance().getColorByDefaultColor(com.netease.cloudmusic.pause.l);
//        }
//        if (isWhiteTheme() || isCustomLightTheme() || isCustomColorTheme()) {
//            return com.netease.cloudmusic.pause.f21073e;
//        }
//        if (isNightTheme()) {
//            return 0x99FFFFFF;
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
//            return getInstance().getColorByDefaultColor(com.netease.cloudmusic.pause.l);
//        }
//        if (isWhiteTheme() || isCustomLightTheme() || isCustomColorTheme()) {
//            return com.netease.cloudmusic.pause.f21073e;
//        }
//        if (isNightTheme()) {
//            return 0x99FFFFFF;
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
//                if (i == com.netease.cloudmusic.pause.f21069a) {
//                    i2 = R.color.themeColor;
//                } else if (i == com.netease.cloudmusic.pause.mGestureListener) {
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
//            if (i != com.netease.cloudmusic.pause.f21069a || !isCustomColorTheme()) {
//                return i;
//            }
//            return ThemeConfig.getCurrentColor();
//        } else if (isRedTheme()) {
//            if (i == com.netease.cloudmusic.pause.f21069a) {
//                return com.netease.cloudmusic.pause.f21070b;
//            }
//            if (i == com.netease.cloudmusic.pause.f21071c) {
//                return com.netease.cloudmusic.pause.f21072d;
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




    @SuppressLint("RestrictedApi")
    class BannerBgDrawable extends DrawableWrapper {
        private MyConstantState mMyConstantState;

        class MyConstantState extends ConstantState {
            MyConstantState() {
            }

            @Override
            @NonNull
            public Drawable newDrawable() {
                return null;
//                return new BannerBgDrawable(BannerBgDrawable.this.getWrappedDrawable().getConstantState().newDrawable());
            }

            @Override
            public int getChangingConfigurations() {
                return 0;
            }
        }

        public BannerBgDrawable(Drawable drawable) {
            super(drawable);
        }

        /* access modifiers changed from: protected */
        @Override
        public void onBoundsChange(Rect rect) {
            rect.bottom = getIntrinsicHeight();
            super.onBoundsChange(rect);
        }

        @Override
        public int getIntrinsicHeight() {
            return ResourceRouter.BANNER_BG_HEIGHT;
        }

        @Override
        @Nullable
        public ConstantState getConstantState() {
            if (this.mMyConstantState == null) {
                this.mMyConstantState = new MyConstantState();
            }
            return this.mMyConstantState;
        }
    }

//    /* compiled from: ProGuard */
//    class CustomThemeMiniPlayBarDrawable extends DrawableWrapper {
//        /* access modifiers changed from: private */
//        public Drawable mLeftDecorate;
//        private MyConstantState mMyConstantState;
//
//        /* compiled from: ProGuard */
//        class MyConstantState extends ConstantState {
//            MyConstantState() {
//            }
//
//            @Override
//            @NonNull
//            public Drawable newDrawable() {
//                return new CustomThemeMiniPlayBarDrawable((pause) CustomThemeMiniPlayBarDrawable.this.getWrappedDrawable().getConstantState().newDrawable(), CustomThemeMiniPlayBarDrawable.this.mLeftDecorate == null ? null : CustomThemeMiniPlayBarDrawable.this.mLeftDecorate.getConstantState().newDrawable());
//            }
//
//            @Override
//            public int getChangingConfigurations() {
//                return 0;
//            }
//        }
//
////        public CustomThemeMiniPlayBarDrawable(pause bVar, Drawable drawable) {
////            super(bVar);
////            this.mLeftDecorate = drawable;
////            if (this.mLeftDecorate != null) {
////                this.mLeftDecorate.setBounds(0, bVar.getIntrinsicHeight() - this.mLeftDecorate.getIntrinsicHeight(), this.mLeftDecorate.getIntrinsicWidth(), bVar.getIntrinsicHeight());
////            }
////        }
//
//        public void setForPressed(boolean z) {
////            ((pause) getWrappedDrawable()).onColorGet(z);
//////            if (this.mLeftDecorate != null) {
//////                this.mLeftDecorate.mutate().setColorFilter(z ? new PorterDuffColorFilter(0x19000000, PorterDuff.Mode.SRC_ATOP) : null);
//////            }
//        }
//
////        public void draw(Canvas canvas) {
////            super.draw(canvas);
////            if (this.mLeftDecorate != null) {
////                this.mLeftDecorate.draw(canvas);
////            }
////        }
////
////        @Nullable
////        public ConstantState getConstantState() {
////            if (this.mMyConstantState == null) {
////                this.mMyConstantState = new MyConstantState();
////            }
////            return this.mMyConstantState;
////        }
//    }
//
//    /* compiled from: ProGuard */
//    class DrawerBgDrawable extends DrawableWrapper {
//        public DrawerBgDrawable(Drawable drawable) {
//            super(drawable);
//        }
//
//        /* access modifiers changed from: protected */
//        public void onBoundsChange(Rect rect) {
//            super.onBoundsChange(new Rect(0, 0, getWrappedDrawable().getIntrinsicWidth(), getWrappedDrawable().getIntrinsicHeight()));
//        }
//
//        public int getMinimumWidth() {
//            return aa.onColorGet();
//        }
//
//        public void draw(Canvas canvas) {
//            float f2;
//            float f3;
//            float f4 = 0.0f;
//            Drawable wrappedDrawable = getWrappedDrawable();
//            if (wrappedDrawable instanceof ColorDrawable) {
//                super.draw(canvas);
//                return;
//            }
//            int intrinsicWidth = wrappedDrawable.getIntrinsicWidth();
//            int intrinsicHeight = wrappedDrawable.getIntrinsicHeight();
//            int width = getBounds().width();
//            int height = getBounds().height();
//            if (intrinsicWidth * height > width * intrinsicHeight) {
//                f2 = ((float) height) / ((float) intrinsicHeight);
//                f3 = (((float) width) - (((float) intrinsicWidth) * f2)) * 0.5f;
//            } else {
//                f2 = ((float) width) / ((float) intrinsicWidth);
//                f3 = 0.0f;
//                f4 = (((float) height) - (((float) intrinsicHeight) * f2)) * 0.5f;
//            }
//            Matrix matrix = new Matrix();
//            matrix.setScale(f2, f2);
//            matrix.postTranslate(f3, f4);
//            int save = canvas.save();
//            canvas.concat(matrix);
//            wrappedDrawable.draw(canvas);
//            canvas.restoreToCount(save);
//        }
//    }
//
//    /* compiled from: ProGuard */
//    class ResourceIdentifier {
//        String resName;
//        String resType;
//
//        public ResourceIdentifier(String str, String str2) {
//            this.resName = str;
//            this.resType = str2;
//        }
//    }
//
//    /* compiled from: ProGuard */
//    class SizeExplicitDrawable extends DrawableWrapper {
//        /* access modifiers changed from: private */
//        public int mHeight;
//        private MyConstantState mMyConstantState;
//        /* access modifiers changed from: private */
//        public int mWidth;
//
//        /* compiled from: ProGuard */
//        class MyConstantState extends ConstantState {
//            MyConstantState() {
//            }
//
//            @NonNull
//            public Drawable newDrawable() {
//                return new SizeExplicitDrawable(SizeExplicitDrawable.this.getWrappedDrawable().getConstantState().newDrawable(), SizeExplicitDrawable.this.mWidth, SizeExplicitDrawable.this.mHeight);
//            }
//
//            public int getChangingConfigurations() {
//                return 0;
//            }
//        }
//
//        public SizeExplicitDrawable(Drawable drawable, int i, int i2) {
//            super(drawable);
//            this.mWidth = i;
//            this.mHeight = i2;
//        }
//
//        public int getIntrinsicWidth() {
//            return this.mWidth;
//        }
//
//        public int getIntrinsicHeight() {
//            return this.mHeight;
//        }
//
//        @Nullable
//        public ConstantState getConstantState() {
//            if (this.mMyConstantState == null) {
//                this.mMyConstantState = new MyConstantState();
//            }
//            return this.mMyConstantState;
//        }
//    }
//
//    /* compiled from: ProGuard */
//    class WithLineDrawable extends DrawableWrapper {
//        /* access modifiers changed from: private */
//        public boolean mForTop;
//        private Paint mLinePaint = new Paint();
//        private MyConstantState mMyConstantState;
//
//        /* compiled from: ProGuard */
//        class MyConstantState extends ConstantState {
//            MyConstantState() {
//            }
//
//            @NonNull
//            public Drawable newDrawable() {
//                return new WithLineDrawable(WithLineDrawable.this.getWrappedDrawable().getConstantState().newDrawable(), WithLineDrawable.this.mForTop);
//            }
//
//            public int getChangingConfigurations() {
//                return 0;
//            }
//        }
//
//        public WithLineDrawable(Drawable drawable, boolean z) {
//            super(drawable);
//            this.mLinePaint.setColor(ResourceRouter.this.getLineColor());
//            this.mForTop = z;
//        }
//
//        public void draw(Canvas canvas) {
//            super.draw(canvas);
//            if (this.mForTop) {
//                canvas.drawLine(0.0f, 0.0f, (float) getBounds().width(), 0.0f, this.mLinePaint);
//                return;
//            }
//            canvas.drawLine(0.0f, (float) getBounds().height(), (float) getBounds().width(), (float) getBounds().height(), this.mLinePaint);
//        }
//
//        @Nullable
//        public ConstantState getConstantState() {
//            if (this.mMyConstantState == null) {
//                this.mMyConstantState = new MyConstantState();
//            }
//            return this.mMyConstantState;
//        }
//    }
//

    @SuppressLint("RestrictedApi")
    class SizeExplicitDrawable extends DrawableWrapper {
        /* access modifiers changed from: private */
        public int mHeight;
        private MyConstantState mMyConstantState;
        /* access modifiers changed from: private */
        public int mWidth;

        public SizeExplicitDrawable(Drawable drawable, int i, int i2) {
            super(drawable);
            this.mWidth = i;
            this.mHeight = i2;
        }

        @Override
        public int getIntrinsicWidth() {
            return this.mWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return this.mHeight;
        }

        @Override
        @Nullable
        public Drawable.ConstantState getConstantState() {
            if (this.mMyConstantState == null) {
                this.mMyConstantState = new MyConstantState();
            }
            return this.mMyConstantState;
        }

        /* compiled from: ProGuard */
        class MyConstantState extends Drawable.ConstantState {
            MyConstantState() {
            }

            @Override
            @NonNull
            public Drawable newDrawable() {
                return new SizeExplicitDrawable(getWrappedDrawable().getConstantState().newDrawable(), SizeExplicitDrawable.this.mWidth, SizeExplicitDrawable.this.mHeight);
            }

            @Override
            public int getChangingConfigurations() {
                return 0;
            }
        }
    }

}
