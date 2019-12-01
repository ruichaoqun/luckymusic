package com.ruichaoqun.luckymusic.ui.main;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;

public interface MainContact {
    interface View extends IBaseView{

    }

    interface Presenter extends IBasePresenter<View>{
        void getBannerList();
    }
}
