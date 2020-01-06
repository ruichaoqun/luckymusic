package com.ruichaoqun.luckymusic.ui.localmedia.fragment;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;

import java.util.List;

public interface LocalMediaContact {
    interface View extends IBaseView {
        void setTotalData(List<MultiItemEntity> multiItemEntityList);
    }

    interface Presenter extends IBasePresenter<View> {
        void initData();
        void refreshData();
        void loadMoreData();
    }
}
