package com.ruichaoqun.luckymusic.ui.localmedia.fragment.album;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;
import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;

import java.util.List;

public interface AlbumListContact {
    interface View extends IBaseView {
        void onLoadAlbumSuccess(List<AlbumBean> list);
    }

    interface Presenter extends IBasePresenter<View> {
        void getLocalAlbum();
    }
}
