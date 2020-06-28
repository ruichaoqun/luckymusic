package com.ruichaoqun.luckymusic.theme.core;

import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.IntDef;


import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.utils.SharedPreferencesUtils;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Rui Chaoqun
 * @date :2019/7/22 16:04
 * description:
 */
public class ThemeConfig {
    public static final int BEFORE_6_THEME_DEFAULT_COLOR = Color.TRANSPARENT;
    public static final int COLOR_RED = 0xFFCE3D3E;
    public static final int COLOR_RED_TOOLBAR_END = 0xFFDB3F35;
    public static final int COLOR_WHITE = Color.WHITE;
    public static final String DIR = (LuckyMusicApp.sInstance.getFilesDir().getPath() + File.separator + "theme");
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

    private static final String THEME_CUSTOM_BG_IMAGE = (DIR + File.separator + "custom_bg.png");

    public static final int THEME_DEFAULT_COLOR_ID = -1;
    public static final int THEME_INTERNAL_MAX_ID = -1;

    public static final int THEME_DEFAULT = -1;//默认，白色背景，透明toolbar
    public static final int THEME_CUSTOM_COLOR = -2;
    public static final int THEME_NIGHT = -3;
    public static final int THEME_CUSTOM_BG = -4;
    public static final int THEME_RED = -5;
    public static final int THEME_COOL_BLACK = -6;


    public static int getCurrentColor() {
        return getPrefer().getInt(PREF_KEY_CURRENT_COLOR, 0);
    }

    public static int getCustomBgThemeColor() {
        return getPrefer().getInt(PREF_KEY_CUSTOM_BG_ALPHA, 0);
    }



    @IntDef({
            THEME_DEFAULT,
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
        int currentThemeId = getPrefer().getInt(PREF_KEY_CURRENT_THEME,THEME_DEFAULT);
        return currentThemeId;
    }

    public static String getPrevThemeName() {
        return getPrefer().getString(PREF_KEY_PREV_NAME, LuckyMusicApp.getInstance().getResources().getString(R.string.default_theme));
    }

    public static int getPrevThemeId(){
        return getPrefer().getInt(PREF_KEY_PREV_THEME,-1);
    }

    /**
     * 保存当前主题并保存上一个主题
     * @param themeId 即将更新的主题id
     * @param preThemeId 当前主题id
     * @param preThemeName 当前主题名
     * @param preThemeColor 当前主题色
     */
    public static void updateCurrentThemeIdAndPrevThemeInfo(int themeId, int preThemeId, String preThemeName, int preThemeColor) {
        //保存即将设置的主题id到当前主题
        SharedPreferences.Editor putInt = getPrefer().getEditor().putInt(PREF_KEY_CURRENT_THEME, themeId);
        //如果当前主题不是夜间模式，保存到上一个主题
        if (preThemeId != -3) {
            putInt.putInt(PREF_KEY_PREV_THEME, preThemeId).putString(PREF_KEY_PREV_NAME, preThemeName).putInt(PREF_KEY_PREV_COLOR, preThemeColor);
        }
        putInt.apply();
    }

    public static int getSelectedColor() {
        return getPrefer().getInt(PREF_KEY_SELECTED_COLOR, -1);
    }

    public static void updateCurrentAndSelectedColor(int currentColor, int selectColor) {
        getPrefer().getEditor().putInt(PREF_KEY_CURRENT_COLOR, currentColor).putInt(PREF_KEY_SELECTED_COLOR, selectColor).commit();
    }



    public static SharedPreferencesUtils getPrefer(){
        return SharedPreferencesUtils.getInstance();
    }
}
