package com.ruichaoqun.luckymusic.ui.localmedia.fragment;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

import javax.inject.Inject;

public class LocalMediaPresenter extends BasePresenter<LocalMediaContact.View> implements LocalMediaContact.Presenter {

    @Inject
    public LocalMediaPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public List<SongBean> getLocalSongs() {

        return null;
    }
}
