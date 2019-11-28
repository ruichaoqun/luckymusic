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
        mSharedPreferences.getBoolean(Constants.SHARE_PREFERENCE,false);
    }

    @Override
    public void setFirstUse() {
        mSharedPreferences.edit().putBoolean(Constants.SHARE_PREFERENCE,true).apply();
    }
}
