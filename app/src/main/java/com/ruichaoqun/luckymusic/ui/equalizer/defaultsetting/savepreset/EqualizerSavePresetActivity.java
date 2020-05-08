package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.savepreset;

import android.app.Activity;
import android.content.Intent;

import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.SimpleMVPActivity;

/**
 * @author Rui Chaoqun
 * @date :2020-5-8 0:41:09
 * description:EqualizerSavePresetActivity
 */
public class EqualizerSavePresetActivity extends SimpleMVPActivity<EqualizerSavePresetContact.Presenter> implements EqualizerSavePresetContact.View{

    public static void launchFrom(Activity activity){
        activity.startActivityForResult(new Intent(activity,EqualizerSavePresetActivity.class),100);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.equalizer_save_preset_activity;
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
