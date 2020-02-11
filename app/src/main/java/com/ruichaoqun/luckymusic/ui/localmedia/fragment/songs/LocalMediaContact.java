package com.ruichaoqun.luckymusic.ui.localmedia.fragment.songs;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;
import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

public interface LocalMediaContact {
    interface View extends IBaseView {
        void onLoadSongsSuccess(List<SongBean> list);
    }

    interface Presenter extends IBasePresenter<View> {
        void getLocalSongs();
    }
}
