package com.ruichaoqun.luckymusic.base.mvp;

public interface IBaseView {
    void showToast(String message);

    void showLoading(String message);

    void hideLoading();
}
