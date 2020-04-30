package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;
import com.ruichaoqun.luckymusic.data.bean.EqualizerPresetBean;

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
        void getPresetData();
    }
}
