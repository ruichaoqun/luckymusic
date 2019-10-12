package com.ruichaoqun.luckymusic.theme;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

import com.ruichaoqun.luckymusic.common.ServiceFacade;
import com.ruichaoqun.luckymusic.service.ServiceConst;
import com.ruichaoqun.luckymusic.service.api.IThemeService;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 15:35
 * description:
 */
public class ThemeService implements IThemeService {
    private static ThemeService mServiceProxy;

    private IThemeService mIThemeService = ((IThemeService) ServiceFacade.get(ServiceConst.THEME_SERVICE));


    private ThemeService() {
    }


    public static synchronized ThemeService getInstance() {
        ThemeService aVar;
        synchronized (ThemeService.class) {
            if (mServiceProxy == null) {
                mServiceProxy = new ThemeService();
            }
            aVar = mServiceProxy;
        }
        return aVar;
    }


    @Override
    public boolean isNightTheme() {
        return this.mIThemeService.isNightTheme();
    }

    @Override
    public boolean isCustomDarkTheme() {
        return this.mIThemeService.isCustomDarkTheme();
    }

    @Override
    public boolean isCustomBgTheme() {
        return this.mIThemeService.isCustomBgTheme();
    }

    @Override
    public int getThemeColor() {
        return this.mIThemeService.getThemeColor();
    }

    @Override
    public int getColorByDefaultColor(int i) {
        return this.mIThemeService.getColorByDefaultColor(i);
    }

    @Override
    public int getPopupBackgroundColor() {
        return this.mIThemeService.getPopupBackgroundColor();
    }

    @Override
    public int getThemeColorWithNight() {
        return this.mIThemeService.getThemeColorWithNight();
    }

    @Override
    public boolean isCustomLightTheme() {
        return this.mIThemeService.isCustomLightTheme();
    }

    @Override
    public Drawable getDrawable(int i) {
        return this.mIThemeService.getDrawable(i);
    }

    @Override
    public int getToolbarIconColor(boolean z) {
        return this.mIThemeService.getToolbarIconColor(z);
    }

    public int b() {
        return getToolbarIconColor(false);
    }

    @Override
    public boolean isWhiteTheme() {
        return this.mIThemeService.isWhiteTheme();
    }

    @Override
    public boolean isRedTheme() {
        return this.mIThemeService.isRedTheme();
    }

    @Override
    public boolean isGeneralRuleTheme() {
        return this.mIThemeService.isGeneralRuleTheme();
    }

    @Override
    @ColorInt
    public int getColor(int i) {
        return this.mIThemeService.getColor(i);
    }

    @Override
    public int getThemeId() {
        return this.mIThemeService.getThemeId();
    }

    @Override
    public int getBgMaskDrawableColor(int i) {
        return this.mIThemeService.getBgMaskDrawableColor(i);
    }

    @Override
    public ColorDrawable getBgMaskDrawable(int i) {
        return this.mIThemeService.getBgMaskDrawable(i);
    }

    @Override
    public int getIconUnableColor(int i) {
        return this.mIThemeService.getIconUnableColor(i);
    }

    @Override
    public int getIconPressedColor(int i) {
        return this.mIThemeService.getIconPressedColor(i);
    }

    @Override
    public int getNightColor(int i) {
        return this.mIThemeService.getNightColor(i);
    }

    @Override
    public int getLineColor() {
        return this.mIThemeService.getLineColor();
    }

    @Override
    public boolean isCustomColorTheme() {
        return this.mIThemeService.isCustomColorTheme();
    }

    @Override
    public boolean isDefaultTheme() {
        return this.mIThemeService.isDefaultTheme();
    }

    @Override
    public int getIconColorByDefaultColor(int i) {
        return this.mIThemeService.getIconColorByDefaultColor(i);
    }

    @Override
    public int getIconCustomColor(int i) {
        return this.mIThemeService.getIconCustomColor(i);
    }

    @Override
    public int getIconNightColor(int i) {
        return this.mIThemeService.getIconNightColor(i);
    }

    @Override
    public int getColorByDefaultColorJustNight(int i) {
        return this.mIThemeService.getColorByDefaultColorJustNight(i);
    }

    @Override
    public boolean isCompatibleColor(int i) {
        return this.mIThemeService.isCompatibleColor(i);
    }

    @Override
    public int getCurrentColor() {
        return this.mIThemeService.getCurrentColor();
    }

    @Override
    public int getCustomBgThemeColor() {
        return this.mIThemeService.getCustomBgThemeColor();
    }

    @Override
    public Drawable getCachePlayerDrawable() {
        return this.mIThemeService.getCachePlayerDrawable();
    }

    @Override
    public Drawable getCacheToolBarDrawable() {
        return this.mIThemeService.getCacheToolBarDrawable();
    }

    @Override
    public Drawable getCacheStatusBarDrawable() {
        return this.mIThemeService.getCacheStatusBarDrawable();
    }

    @Override
    public Drawable getCacheBgBlurDrawable() {
        return this.mIThemeService.getCacheBgBlurDrawable();
    }

    @Override
    public int[] getThemeColorBackgroundColorAndIconColor() {
        return this.mIThemeService.getThemeColorBackgroundColorAndIconColor();
    }

    @Override
    public boolean isCustomBgOrDarkThemeWhiteColor() {
        return this.mIThemeService.isCustomBgOrDarkThemeWhiteColor();
    }

    @Override
    public Drawable getCacheBannerBgDrawable() {
        return this.mIThemeService.getCacheBannerBgDrawable();
    }

    @Override
    public Drawable getCacheNoTabBannerBgDrawable() {
        return this.mIThemeService.getCacheNoTabBannerBgDrawable();
    }

    @Override
    public int getThemeColorWithCustomBgWhiteColor() {
        return this.mIThemeService.getThemeColorWithCustomBgWhiteColor();
    }

    @Override
    public int getThemeColorWithDarken() {
        return this.mIThemeService.getThemeColorWithDarken();
    }

    @Override
    public Drawable getCacheTabDrawable() {
        return this.mIThemeService.getCacheTabDrawable();
    }

    @Override
    public Drawable getCacheTabForTopDrawable() {
        return this.mIThemeService.getCacheTabForTopDrawable();
    }

    @Override
    public boolean needDark() {
        return this.mIThemeService.needDark();
    }

    @Override
    public int getThemeNormalColor() {
        return this.mIThemeService.getThemeNormalColor();
    }

}
