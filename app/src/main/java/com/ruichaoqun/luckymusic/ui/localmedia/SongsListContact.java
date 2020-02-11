package com.ruichaoqun.luckymusic.ui.localmedia;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;
import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2020-2-11 16:55:56
 * description:MVP 模板自动生成
 */
public interface SongsListContact {
    interface View extends IBaseView {
        void onLoadSongsSuccess(List<SongBean> list);
    }

    interface Presenter extends IBasePresenter<View> {
        void getSongsByType(String type,String id);
    }
}
