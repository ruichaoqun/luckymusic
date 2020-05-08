package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.savepreset;

import com.google.gson.Gson;
import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.CustomEqBean;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectJsonPackage;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Rui Chaoqun
 * @date :2020-5-8 0:41:09
 * description:MVP 模板自动生成
 */
public class EqualizerSavePresetPresenter extends BasePresenter<EqualizerSavePresetContact.View> implements EqualizerSavePresetContact.Presenter {
    @Inject
    public EqualizerSavePresetPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public List<CustomEqBean> getAllCustomEq() {
        return dataRepository.getAllCustomEq();
    }

    @Override
    public void saveOrUpdate(CustomEqBean customEqBean) {
        dataRepository.insertCustomEq(customEqBean);
    }

    @Override
    public void setAudioEffectJsonPackage(AudioEffectJsonPackage jsonPackage) {
        dataRepository.setEffectData(new Gson().toJson(jsonPackage));
    }
}
