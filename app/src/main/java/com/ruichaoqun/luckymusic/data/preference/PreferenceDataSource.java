package com.ruichaoqun.luckymusic.data.preference;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:34
 * description:
 */
public interface PreferenceDataSource {

    void isFirstUse();

    void setFirstUse();

    /**
     * 设置播放模式
     * @param mode
     */
    void setPlayMode(int mode);

    /**
     * 获取播放模式
     * @return
     */
    int getPlayMode();

    boolean isEffectEnable();

    void setEffectEnable(boolean enable);

    void setEffectData(String effectData);

    String getEffectData();

    /**
     * 获保存当前动效类型
     * @param type
     */
    void setDynamicEffectType(int type);

    /**
     * 获取保存的动效类型
     * @return
     */
    int getDynamicEffectType();
}
