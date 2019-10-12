package com.ruichaoqun.luckymusic.theme.core;

import com.ruichaoqun.luckymusic.theme.ThemeService;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 15:05
 * description:
 */
public class ThemeResetter {
    private int mCurrentCustomColor = 0;
    private int mCurrentThemeId = ThemeService.getInstance().getThemeId();
    private OnThemeResetListener mOnThemeResetListener;

    public ThemeResetter(OnThemeResetListener listener) {
        this.mOnThemeResetListener = listener;
        ThemeService proxy = ThemeService.getInstance();
        if (proxy.isCustomColorTheme()) {
            this.mCurrentCustomColor = proxy.getCurrentColor();
        } else if (proxy.isCustomBgTheme()) {
            this.mCurrentCustomColor = proxy.getCustomBgThemeColor();
        }
    }

    public void checkIfNeedResetTheme() {
        int themeId = ThemeService.getInstance().getThemeId();
        ThemeService proxy = ThemeService.getInstance();
        if (themeId != this.mCurrentThemeId || ((proxy.isCustomColorTheme() && this.mCurrentCustomColor != proxy.getCurrentColor()) || (proxy.isCustomBgTheme() && this.mCurrentCustomColor != proxy.getCustomBgThemeColor()))) {
            this.mOnThemeResetListener.onThemeReset();
            saveCurrentThemeInfo();
        }
    }

    public void saveCurrentThemeInfo() {
        ThemeService proxy = ThemeService.getInstance();
        this.mCurrentThemeId = ThemeService.getInstance().getThemeId();
        int i = proxy.isCustomColorTheme() ? proxy.getCurrentColor() : proxy.isCustomBgTheme() ? proxy.getCustomBgThemeColor() : 0;
        this.mCurrentCustomColor = i;
    }
}
