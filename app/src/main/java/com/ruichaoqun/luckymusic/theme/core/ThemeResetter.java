package com.ruichaoqun.luckymusic.theme.core;

import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 15:05
 * description:
 */
public class ThemeResetter {
    private int mCurrentCustomColor = 0;
    private int mCurrentThemeId = ResourceRouter.getInstance().getThemeId();
    private OnThemeResetListener mOnThemeResetListener;

    public ThemeResetter(OnThemeResetListener listener) {
        this.mOnThemeResetListener = listener;
        if (ResourceRouter.getInstance().isCustomColorTheme()) {
            this.mCurrentCustomColor = ThemeConfig.getCurrentColor();
        } else if (ResourceRouter.getInstance().isCustomBgTheme()) {
            this.mCurrentCustomColor = ThemeConfig.getCustomBgThemeColor();
        }
    }

    public void checkIfNeedResetTheme() {
        int themeId = ResourceRouter.getInstance().getThemeId();
        if (themeId != this.mCurrentThemeId || ((ResourceRouter.getInstance().isCustomColorTheme() && this.mCurrentCustomColor != ThemeConfig.getCurrentColor()) || (ResourceRouter.getInstance().isCustomBgTheme() && this.mCurrentCustomColor != ThemeConfig.getCustomBgThemeColor()))) {
            this.mOnThemeResetListener.onThemeReset();
            saveCurrentThemeInfo();
        }
    }

    public void saveCurrentThemeInfo() {
        this.mCurrentThemeId = ResourceRouter.getInstance().getThemeId();
        int color = ResourceRouter.getInstance().isCustomColorTheme() ? ThemeConfig.getCurrentColor() : ResourceRouter.getInstance().isCustomBgTheme() ? ThemeConfig.getCustomBgThemeColor() : 0;
        this.mCurrentCustomColor = color;
    }
}
