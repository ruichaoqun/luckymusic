package com.ruichaoqun.luckymusic.base.mvp;

import com.ruichaoqun.luckymusic.data.DataRepository;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<T extends IBaseView> implements IBasePresenter<T> {
    protected static final String TAG = BasePresenter.class.getSimpleName();

    protected T mView;
    protected DataRepository dataRepository;
    protected CompositeDisposable compositeDisposable;

    public BasePresenter(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void takeView(T view) {
        this.mView = view;
    }

    @Override
    public void dropView() {
        compositeDisposable.dispose();
    }
}
