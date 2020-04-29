package com.ruichaoqun.luckymusic.ui.equalizer;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectJsonPackage;

import javax.inject.Inject;

/**
 * @author Rui Chaoqun
 * @date :2020-4-12 21:11:55
 * description:MVP 模板自动生成
 */
public class EqualizerPresenter extends BasePresenter<EqualizerContact.View> implements EqualizerContact.Presenter {
    @Inject
    public EqualizerPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public boolean isEffectEnable() {
        return dataRepository.isEffectEnable();
    }

    @Override
    public void setEffectEnable(boolean enable) {
        dataRepository.setEffectEnable(enable);
    }

    @Override
    public AudioEffectJsonPackage getAudioEffectJsonPackage() {
        String gson = dataRepository.getEffectData();
        if(TextUtils.isEmpty(gson)){
            return new AudioEffectJsonPackage();
        }
        return new Gson().fromJson(gson,AudioEffectJsonPackage.class);
    }

    @Override
    public void setAudioEffectJsonPackage(AudioEffectJsonPackage jsonPackage) {
        dataRepository.setEffectData(new Gson().toJson(jsonPackage));
    }
}
