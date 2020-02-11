package com.ruichaoqun.luckymusic.ui.localmedia.fragment.artist;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class ArtistListPresenter extends BasePresenter<ArtistListContact.View> implements ArtistListContact.Presenter {

    @Inject
    public ArtistListPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public void getLocalArtist() {
        compositeDisposable.add(dataRepository.rxGetAllArtist()
                .subscribe(new Consumer<List<ArtistBean>>() {
                    @Override
                    public void accept(List<ArtistBean> list) throws Exception {
                        mView.onLoadArtistSuccess(list);
                    }
                }));
    }
}
