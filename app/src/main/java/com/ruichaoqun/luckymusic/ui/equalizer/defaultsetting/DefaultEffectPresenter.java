package com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;

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
}
