package com.ruichaoqun.luckymusic.ui.main;

import android.support.v4.media.MediaMetadataCompat;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.BannerItemBean;
import com.ruichaoqun.luckymusic.data.bean.BaseResponse;
import com.ruichaoqun.luckymusic.utils.LogUtils;
import com.ruichaoqun.luckymusic.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainPresenter extends BasePresenter<MainContact.View> implements MainContact.Presenter {
    @Inject
    public MainPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public void getBannerList() {
    }
}
