package com.ruichaoqun.luckymusic.ui;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;

import javax.inject.Inject;

/**
 * @author Rui Chaoqun
 * @date :2020-1-13 9:37:48
 * description:MVP 模板自动生成
 */
public class PlayerPresenter extends BasePresenter<PlayerContact.View> implements PlayerContact.Presenter {
    @Inject
    public PlayerPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public int getSessionId() {
        return dataRepository.getAudioSessionId();
    }

    @Override
    public int getDynamicEffectType() {
        return dataRepository.getDynamicEffectType();
    }

    @Override
    public void setDynamicEffectType(int type) {
        dataRepository.setDynamicEffectType(type);
    }
}
