package com.ruichaoqun.luckymusic.ui.main.discover;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;

import javax.inject.Inject;

public class WanAndroidPresenter extends BasePresenter<WanAndroidContact.View> implements WanAndroidContact.Presenter {

    @Inject
    public WanAndroidPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public void getBannerList() {

    }
}
