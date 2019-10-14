package com.ruichaoqun.luckymusic.service.impl;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.ruichaoqun.luckymusic.service.api.IThemeService;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeConfig;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 15:31
 * description:
 */
public class ThemeServiceImpl implements IThemeService {
    @Override
    public boolean isNightTheme() {
        return ResourceRouter.getInstance().isNightTheme();
    }

    @Override
    public boolean isCustomDarkTheme() {
        return ResourceRouter.getInstance().isCustomDarkTheme();
    }

    @Override
    public boolean isCustomBgTheme() {
        return ResourceRouter.getInstance().isCustomBgTheme();
    }

    @Override
    public int getThemeColor() {
        return ResourceRouter.getInstance().getThemeColor();
    }

    @Override
    public int getColorByDefaultColor(int i) {
        return ResourceRouter.getInstance().getColorByDefaultColor(i);
    }

    @Override
    public int getPopupBackgroundColor() {
        return ResourceRouter.getInstance().getPopupBackgroundColor();
    }

    @Override
    public int getThemeColorWithNight() {
        return ResourceRouter.getInstance().getThemeColorWithNight();
    }

    @Override
    public boolean isCustomLightTheme() {
        return ResourceRouter.getInstance().isCustomLightTheme();
    }

    @Override
    public Drawable getDrawable(int i) {
        return ResourceRouter.getInstance().getDrawable(i);
    }

    @Override
    public int getToolbarIconColor(boolean z) {
        return ResourceRouter.getInstance().getToolbarIconColor(z);
    }

    @Override
    public boolean isWhiteTheme() {
        return ResourceRouter.getInstance().isWhiteTheme();
    }

    @Override
    public boolean isRedTheme() {
        return ResourceRouter.getInstance().isRedTheme();
    }

    @Override
    public boolean isGeneralRuleTheme() {
        return ResourceRouter.getInstance().isGeneralRuleTheme();
    }

    @Override
    public int getColor(int i) {
        return ResourceRouter.getInstance().getColorByDefaultColor(i);
    }

    @Override
    public int getThemeId() {
        return ResourceRouter.getInstance().getThemeId();
    }

    @Override
    public int getBgMaskDrawableColor(int i) {
        return ResourceRouter.getInstance().getBgMaskDrawableColor(i);
    }

    @Override
    public ColorDrawable getBgMaskDrawable(int i) {
        return ResourceRouter.getInstance().getBgMaskDrawable(i);
    }

    @Override
    public int getIconUnableColor(int i) {
        return ResourceRouter.getInstance().getIconUnableColor(i);
    }

    @Override
    public int getIconPressedColor(int i) {
        return ResourceRouter.getInstance().getIconPressedColor(i);
    }

    @Override
    public int getNightColor(int i) {
        return ResourceRouter.getInstance().getNightColor(i);
    }

    @Override
    public int getLineColor() {
        return ResourceRouter.getInstance().getLineColor();
    }

    @Override
    public boolean isCustomColorTheme() {
        return ResourceRouter.getInstance().isCustomColorTheme();
    }

    @Override
    public boolean isDefaultTheme() {
        return ResourceRouter.getInstance().isDefaultTheme();
    }

    @Override
    public int getIconColorByDefaultColor(int i) {
        return ResourceRouter.getInstance().getIconColorByDefaultColor(i);
    }

    @Override
    public int getIconCustomColor(int i) {
        return ResourceRouter.getInstance().getIconCustomColor(i);
    }

    @Override
    public int getIconNightColor(int i) {
        return ResourceRouter.getInstance().getIconNightColor(i);
    }

    @Override
    public int getColorByDefaultColorJustNight(int i) {
        return ResourceRouter.getInstance().getColorByDefaultColorJustNight(i);
    }

    @Override
    public boolean isCompatibleColor(int i) {
        return ResourceRouter.getInstance().isCompatibleColor(i);
    }

    @Override
    public int getCurrentColor() {
        return ThemeConfig.getCurrentColor();
    }

    @Override
    public int getCustomBgThemeColor() {
        return ThemeConfig.getCustomBgThemeColor();
    }

    @Override
    public Drawable getCachePlayerDrawable() {
        return ResourceRouter.getInstance().getCachePlayerDrawable();
    }

    @Override
    public Drawable getCacheToolBarDrawable() {
        return ResourceRouter.getInstance().getCacheToolBarDrawable();
    }

    @Override
    public Drawable getCacheStatusBarDrawable() {
        return ResourceRouter.getInstance().getCacheStatusBarDrawable();
    }

    @Override
    public Drawable getCacheBgBlurDrawable() {
        return ResourceRouter.getInstance().getCacheBgBlurDrawable();
    }

    @Override
    public int[] getThemeColorBackgroundColorAndIconColor() {
        return ResourceRouter.getInstance().getThemeColorBackgroundColorAndIconColor();
    }

    @Override
    public boolean isCustomBgOrDarkThemeWhiteColor() {
        return ResourceRouter.getInstance().isCustomBgOrDarkThemeWhiteColor();
    }

    @Override
    public Drawable getCacheBannerBgDrawable() {
        return ResourceRouter.getInstance().getCacheBannerBgDrawable();
    }

    @Override
    public Drawable getCacheNoTabBannerBgDrawable() {
        return ResourceRouter.getInstance().getCacheNoTabBannerBgDrawable();
    }

    @Override
    public int getThemeColorWithCustomBgWhiteColor() {
        return ResourceRouter.getInstance().getThemeColorWithCustomBgWhiteColor();
    }

    @Override
    public int getThemeColorWithDarken() {
        return ResourceRouter.getInstance().getThemeColorWithDarken();
    }

    @Override
    public Drawable getCacheTabDrawable() {
        return ResourceRouter.getInstance().getCacheTabDrawable();
    }

    @Override
    public Drawable getCacheTabForTopDrawable() {
        return ResourceRouter.getInstance().getCacheTabForTopDrawable();
    }

    @Override
    public boolean needDark() {
        return ResourceRouter.getInstance().needDark();
    }

    @Override
    public int getThemeNormalColor() {
        return ResourceRouter.getInstance().getThemeNormalColor();
    }
}