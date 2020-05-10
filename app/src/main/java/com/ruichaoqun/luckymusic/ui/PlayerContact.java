package com.ruichaoqun.luckymusic.ui;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;

/**
 * @author Rui Chaoqun
 * @date :2020-1-13 9:37:48
 * description:MVP 模板自动生成
 */
public interface PlayerContact {
    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter<View> {
        int getSessionId();

        int getDynamicEffectType();

        void setDynamicEffectType(int type);
    }
}
