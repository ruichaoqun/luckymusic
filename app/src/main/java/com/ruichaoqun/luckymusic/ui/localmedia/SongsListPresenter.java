package com.ruichaoqun.luckymusic.ui.localmedia;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * @author Rui Chaoqun
 * @date :2020-2-11 16:55:56
 * description:MVP 模板自动生成
 */
public class SongsListPresenter extends BasePresenter<SongsListContact.View> implements SongsListContact.Presenter {
    @Inject
    public SongsListPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public void getSongsByType(String type, String id) {
        compositeDisposable.add(dataRepository.rxGetSongsFromType(type,id)
        .subscribe(new Consumer<List<SongBean>>() {
            @Override
            public void accept(List<SongBean> list) throws Exception {
                mView.onLoadSongsSuccess(list);
            }
        }));
    }
}
