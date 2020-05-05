package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.CustomEqBean;
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
    public void getPresetData(AudioEffectJsonPackage.Eq eq) {
        List<EqualizerPresetBean> mList = new ArrayList<>();
        mList.add(EqualizerPresetBean.newEmptyData());
        List<CustomEqBean> customEqBeans = dataRepository.getAllCustomEq();
        if(customEqBeans != null){
            for (int i = 0; i < customEqBeans.size(); i++) {
                EqualizerPresetBean presetBean = new EqualizerPresetBean();
                presetBean.setType(1);
                presetBean.setTitle(customEqBeans.get(i).getEqTitle());
                String[] strings = customEqBeans.get(i).getEqJson().split(",");
                List<Float> floats = new ArrayList<>();
                for (int j = 0; j < strings.length; j++) {
                    floats.add(Float.valueOf(strings[i]));
                }
                presetBean.setmDatas(floats);
                mList.add(presetBean);
            }
        }
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
        if(!eq.isOn()){
            mList.get(0).setChecked(true);
        }else if(!TextUtils.isEmpty(eq.getFileName())){
            for (int i = 0; i < mList.size(); i++) {
                if(TextUtils.equals(eq.getFileName(),mList.get(i).getTitle())){
                    mList.get(i).setChecked(true);
                    break;
                }
            }
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

    @Override
    public void deleteEq(String title) {
        dataRepository.deleteCustomEq(title);
    }

    @Override
    public void renameEq(String title, String toString) {
        dataRepository.renameCustomEq(title,toString);
    }
}
