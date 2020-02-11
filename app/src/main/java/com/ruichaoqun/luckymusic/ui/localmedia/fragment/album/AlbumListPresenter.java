package com.ruichaoqun.luckymusic.ui.localmedia.fragment.album;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class AlbumListPresenter extends BasePresenter<AlbumListContact.View> implements AlbumListContact.Presenter {

    @Inject
    public AlbumListPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public void getLocalAlbum() {
        compositeDisposable.add(dataRepository.rxGetAllAlbum()
                .subscribe(new Consumer<List<AlbumBean>>() {
                    @Override
                    public void accept(List<AlbumBean> list) throws Exception {
                        mView.onLoadAlbumSuccess(list);
                    }
                }));
    }
}
