package com.ruichaoqun.luckymusic.ui.localmedia.fragment.artist;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

public interface ArtistListContact {
    interface View extends IBaseView {
        void onLoadArtistSuccess(List<ArtistBean> list);
    }

    interface Presenter extends IBasePresenter<View> {
        void getLocalArtist();
    }
}
