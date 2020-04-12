package com.ruichaoqun.luckymusic.ui.equalizer;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;

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
}
