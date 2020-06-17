package com.ruichaoqun.luckymusic;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 15:08
 * description:
 */
public class Constants {
    public static final String BASE_URL = "https://www.wanandroid.com/";


    /**
     * sharePreference key
     */
    public static final String SHARE_PREFERENCE = "wanandroid_preference";

    public static final String SHARE_PREFERENCE_IS_FIRST_USE = "first_use";

    public static final String SHARE_PREFERENCE_PALY_MODE = "play_mode";

    public static final String SHARE_PREFERENCE_EFFECT_ENABLE = "effect_enable";

    public static final String SHARE_PREFERENCE_EFFECT_DATA = "effect_data";

    public static final String SHARE_PREFERENCE_DYNAMIC_EFFECT_TYPE = "dynamic_effect_type";


    public static final String INTENT_EXTRA_LIKE = "like";

    public static int testToken = 1;

    public static boolean needRefreshToken = true;
    public static boolean isRefreshToken = false;

    public static String CHANGE_THEME = "com.ruichaoqun.luckymusic.action.CHANGE_THEME";
    public static String CHANGED_THEME = "com.ruichaoqun.luckymusic.action.CHANGED_THEME";
}
