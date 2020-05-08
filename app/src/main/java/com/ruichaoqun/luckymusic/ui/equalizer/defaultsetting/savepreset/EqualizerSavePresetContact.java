package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.savepreset;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;
import com.ruichaoqun.luckymusic.data.bean.CustomEqBean;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectJsonPackage;

import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2020-5-8 0:41:09
 * description:MVP 模板自动生成
 */
public interface EqualizerSavePresetContact {
    interface View extends IBaseView {
    }

    interface Presenter extends IBasePresenter<View> {
        List<CustomEqBean> getAllCustomEq();

        void saveOrUpdate(CustomEqBean customEqBean);

        void setAudioEffectJsonPackage(AudioEffectJsonPackage jsonPackage);
    }
}
