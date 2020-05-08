package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.savepreset;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;

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
}
