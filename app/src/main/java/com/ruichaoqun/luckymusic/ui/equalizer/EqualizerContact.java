package com.ruichaoqun.luckymusic.ui.equalizer;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectJsonPackage;

import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2020-4-12 21:11:55
 * description:MVP 模板自动生成
 */
public interface EqualizerContact {
    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter<View> {
        boolean isEffectEnable();

        void setEffectEnable(boolean enable);

        AudioEffectJsonPackage getAudioEffectJsonPackage();

        void setAudioEffectJsonPackage(AudioEffectJsonPackage jsonPackage);
    }
}
