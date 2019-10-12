package com.ruichaoqun.luckymusic.service.api;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 15:30
 * description:
 */
public interface IThemeService {
    ColorDrawable getBgMaskDrawable(int i);

    int getBgMaskDrawableColor(int i);

    Drawable getCacheBannerBgDrawable();

    Drawable getCacheBgBlurDrawable();

    Drawable getCacheNoTabBannerBgDrawable();

    Drawable getCachePlayerDrawable();

    Drawable getCacheStatusBarDrawable();

    Drawable getCacheTabDrawable();

    Drawable getCacheTabForTopDrawable();

    Drawable getCacheToolBarDrawable();

    int getColor(int i);

    int getColorByDefaultColor(int i);

    int getColorByDefaultColorJustNight(int i);

    int getCurrentColor();

    int getCustomBgThemeColor();

    Drawable getDrawable(int i);

    int getIconColorByDefaultColor(int i);

    int getIconCustomColor(int i);

    int getIconNightColor(int i);

    int getIconPressedColor(int i);

    int getIconUnableColor(int i);

    int getLineColor();

    int getNightColor(int i);

    int getPopupBackgroundColor();

    int getThemeColor();

    int[] getThemeColorBackgroundColorAndIconColor();

    int getThemeColorWithCustomBgWhiteColor();

    int getThemeColorWithDarken();

    int getThemeColorWithNight();

    int getThemeId();

    int getThemeNormalColor();

    int getToolbarIconColor(boolean z);

    boolean isCompatibleColor(int i);

    boolean isCustomBgOrDarkThemeWhiteColor();

    boolean isCustomBgTheme();

    boolean isCustomColorTheme();

    boolean isCustomDarkTheme();

    boolean isCustomLightTheme();

    boolean isDefaultTheme();

    boolean isGeneralRuleTheme();

    boolean isNightTheme();

    boolean isRedTheme();

    boolean isWhiteTheme();

    boolean needDark();

}
