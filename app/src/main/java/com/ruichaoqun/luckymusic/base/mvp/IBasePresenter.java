package com.ruichaoqun.luckymusic.base.mvp;

public interface IBasePresenter<T> {
    void takeView(T view);
    void dropView();
}
