package com.ruichaoqun.luckymusic.base.mvp;

public interface IBasePresenter<T extends IBaseView> {
    void takeView(T view);
    void dropView();
}
