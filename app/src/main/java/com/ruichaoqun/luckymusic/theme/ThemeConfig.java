package com.ruichaoqun.luckymusic.theme;

import androidx.annotation.IntDef;

import com.ruichaoqun.luckymusic.App;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Rui Chaoqun
 * @date :2019/10/14 16:43
 * description:
 */
public class ThemeConfig {
    public static final int BEFORE_6_THEME_DEFAULT_COLOR = 0;
    public static final int COLOR_RED = -3261122;
    public static final int COLOR_RED_TOOLBAR_END = -2408651;
    public static final int COLOR_WHITE = -1;
    public static final String DIR = (App.sInstance.getFilesDir().getPath() + File.separator + "theme");
    public static final int NIGHT_ALPHA = 178;
    private static final String PREF_KEY_CURRENT_COLOR = "current_color";
    private static final String PREF_KEY_CURRENT_THEME = "current_theme";
    private static final String PREF_KEY_CUSTOM_BG_ALPHA = "custom_bg_alpha";
    private static final String PREF_KEY_CUSTOM_BG_BLUR = "custom_bg_blur";
    private static final String PREF_KEY_CUSTOM_BG_IMAGE = "custom_bg_image";
    private static final String PREF_KEY_CUSTOM_BG_THEMECOLOR = "custom_bg_themecolor";
    private static final String PREF_KEY_CUSTOM_BG_USED = "custom_bg_used";
    private static final String PREF_KEY_PREV_COLOR = "prev_color";
    private static final String PREF_KEY_PREV_NAME = "prev_name";
    private static final String PREF_KEY_PREV_THEME = "prev_theme";
    private static final String PREF_KEY_PREV_VIP = "prev_vip";
    private static final String PREF_KEY_SELECTED_COLOR = "selected_color";
    public static final int THEME_CUSTOM_BG = -4;
    private static final String THEME_CUSTOM_BG_IMAGE = (DIR + File.separator + "custom_bg.png");
    public static final int THEME_CUSTOM_COLOR = -2;
    public static final int THEME_DEFAULT_COLOR_ID = -1;
    public static final int THEME_INTERNAL_MAX_ID = -1;
    public static final int THEME_NIGHT = -3;
    public static final int THEME_RED = -5;
    public static final int THEME_WHITE = -1;

    @IntDef({
            THEME_WHITE,
            THEME_CUSTOM_COLOR,
            THEME_NIGHT,
            THEME_CUSTOM_BG,
            THEME_RED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ThemeType {}

    static {
        new File(DIR).mkdirs();
    }


    public static int getCurrentThemeId() {
        int i = getPrefer().getInt(a.c("LRAGFwQdERERHAAMFg=="), -1);
        if (i <= -1 || !getPrefer().getBoolean(a.c("PhcREz4FDD4="), false) || com.netease.cloudmusic.f.a.a().x()) {
            return i;
        }
        return -1;
    }

}
