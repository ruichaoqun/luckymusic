package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;
import com.ruichaoqun.luckymusic.data.bean.EqualizerPresetBean;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectJsonPackage;

import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2020-4-29 22:30:54
 * description:MVP 模板自动生成
 */
public interface DefaultEffectContact {
    interface View extends IBaseView {
        void onLoadPresetDataSuccess(List<EqualizerPresetBean> data);
    }

    interface Presenter extends IBasePresenter<View> {
        void getPresetData(AudioEffectJsonPackage.Eq mEffectJsonPackage);

        AudioEffectJsonPackage getAudioEffectJsonPackage();

        void setAudioEffectJsonPackage(AudioEffectJsonPackage jsonPackage);

        void deleteEq(String title);

        void renameEq(String title, String toString);
    }
}
