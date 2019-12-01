package com.ruichaoqun.luckymusic.base.mvp;

import com.ruichaoqun.luckymusic.data.DataRepository;

public class BasePresenter<T extends IBaseView> implements IBasePresenter<T> {
    protected T mView;
    protected DataRepository dataRepository;

    public BasePresenter(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public void takeView(T view) {
        this.mView = view;
    }

    @Override
    public void dropView() {

    }
}
