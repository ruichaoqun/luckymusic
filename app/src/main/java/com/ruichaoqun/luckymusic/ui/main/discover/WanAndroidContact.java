package com.ruichaoqun.luckymusic.ui.main.discover;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;

public interface WanAndroidContact {
    interface View extends IBaseView {

    }

    interface Presenter extends IBasePresenter<View> {
        void getBannerList();
    }
}
