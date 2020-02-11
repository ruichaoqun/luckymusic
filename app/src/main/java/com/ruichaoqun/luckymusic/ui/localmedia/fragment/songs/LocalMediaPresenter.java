package com.ruichaoqun.luckymusic.ui.localmedia.fragment.songs;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class LocalMediaPresenter extends BasePresenter<LocalMediaContact.View> implements LocalMediaContact.Presenter {

    @Inject
    public LocalMediaPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public void getLocalSongs() {
        compositeDisposable.add(dataRepository.rxGetAllSongs()
                .subscribe(new Consumer<List<SongBean>>() {
                    @Override
                    public void accept(List<SongBean> list) throws Exception {
                        mView.onLoadSongsSuccess(list);
                    }
                }));
    }
}
