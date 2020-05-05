package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.EqualizerPresetBean;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectJsonPackage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Rui Chaoqun
 * @date :2020-4-29 22:30:54
 * description:MVP 模板自动生成
 */
public class DefaultEffectPresenter extends BasePresenter<DefaultEffectContact.View> implements DefaultEffectContact.Presenter {
    @Inject
    public DefaultEffectPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public void getPresetData() {
        List<EqualizerPresetBean> mList = new ArrayList<>();
        mList.add(EqualizerPresetBean.newEmptyData());
        String[] strings = LuckyMusicApp.getInstance().getResources().getStringArray(R.array.local_eq_file_name);
        String[] strings1 = LuckyMusicApp.getInstance().getResources().getStringArray(R.array.local_eq_title);
        for (int i = 0; i < strings1.length; i++) {
            EqualizerPresetBean presetBean = new EqualizerPresetBean();
            presetBean.setType(2);
            presetBean.setTitle(strings1[i]);
            presetBean.setResource(strings[i]);
            presetBean.setPresetIndex(i);
            mList.add(presetBean);
        }
        mView.onLoadPresetDataSuccess(mList);
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
