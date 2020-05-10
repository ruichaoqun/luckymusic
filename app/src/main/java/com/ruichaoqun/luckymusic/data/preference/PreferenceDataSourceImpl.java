package com.ruichaoqun.luckymusic.data.preference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import com.ruichaoqun.luckymusic.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:48
 * description:
 */
@Singleton
public class PreferenceDataSourceImpl implements PreferenceDataSource{
    private SharedPreferences mSharedPreferences;

    @Inject
    public PreferenceDataSourceImpl(Application context) {
        mSharedPreferences = context.getSharedPreferences(Constants.SHARE_PREFERENCE,Context.MODE_PRIVATE);
    }

    @Override
    public void isFirstUse() {
        mSharedPreferences.getBoolean(Constants.SHARE_PREFERENCE_IS_FIRST_USE,false);
    }

    @Override
    public void setFirstUse() {
        mSharedPreferences.edit().putBoolean(Constants.SHARE_PREFERENCE_IS_FIRST_USE,true).apply();
    }

    @Override
    public void setPlayMode(int mode) {
        mSharedPreferences.edit().putInt(Constants.SHARE_PREFERENCE_PALY_MODE,mode).apply();
    }

    @Override
    public int getPlayMode() {
        return mSharedPreferences.getInt(Constants.SHARE_PREFERENCE_PALY_MODE,2);
    }

    @Override
    public boolean isEffectEnable() {
        return mSharedPreferences.getBoolean(Constants.SHARE_PREFERENCE_EFFECT_ENABLE,false);
    }

    @Override
    public void setEffectEnable(boolean enable) {
        mSharedPreferences.edit().putBoolean(Constants.SHARE_PREFERENCE_EFFECT_ENABLE,enable).apply();
    }

    @Override
    public void setEffectData(String effectData) {
        mSharedPreferences.edit().putString(Constants.SHARE_PREFERENCE_EFFECT_DATA,effectData).apply();
    }

    @Override
    public String getEffectData() {
        return mSharedPreferences.getString(Constants.SHARE_PREFERENCE_EFFECT_DATA,"");
    }

    @Override
    public void setDynamicEffectType(int type) {
        mSharedPreferences.edit().putInt(Constants.SHARE_PREFERENCE_DYNAMIC_EFFECT_TYPE,type).apply();
    }

    @Override
    public int getDynamicEffectType() {
        return mSharedPreferences.getInt(Constants.SHARE_PREFERENCE_DYNAMIC_EFFECT_TYPE,0);
    }
}
