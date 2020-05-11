package com.ruichaoqun.luckymusic.ui.main.discover;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.BannerItemBean;
import com.ruichaoqun.luckymusic.data.bean.BannerListBean;
import com.ruichaoqun.luckymusic.data.bean.BaseResponse;
import com.ruichaoqun.luckymusic.data.bean.HomePageBean;
import com.ruichaoqun.luckymusic.data.bean.HomePageItemBean;
import com.ruichaoqun.luckymusic.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import okhttp3.Request;
import retrofit2.Call;

public class WanAndroidPresenter extends BasePresenter<WanAndroidContact.View> implements WanAndroidContact.Presenter {

    @Inject
    public WanAndroidPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

    @Override
    public void initData() {
        Observable.zip(dataRepository.getBannerList().compose(RxUtils.tokenTimeoutHelper()).flatMap(RxUtils.transformerResult()), dataRepository.getHomeList(0).compose(RxUtils.tokenTimeoutHelper()).flatMap(RxUtils.transformerResult()), dataRepository.getTopList().compose(RxUtils.tokenTimeoutHelper()).flatMap(RxUtils.transformerResult()),
                new Function3<List<BannerItemBean>, HomePageBean, List<HomePageItemBean>, List<MultiItemEntity>>() {
                    @Override
                    public List<MultiItemEntity> apply(List<BannerItemBean> bannerItemBeans, HomePageBean homePageBean, List<HomePageItemBean> homePageItemBeans) throws Exception {
                        List<MultiItemEntity> list = new ArrayList<>();
                        BannerListBean bannerListBean = new BannerListBean();
                        bannerListBean.setList(bannerItemBeans);
                        list.add(bannerListBean);
                        for (int i = 0; i < homePageItemBeans.size(); i++) {
                            list.add(homePageItemBeans.get(i));
                        }
                        for (int i = 0; i < homePageBean.getDatas().size(); i++) {
                            list.add(homePageBean.getDatas().get(i));
                        }
                        return list;
                    }
                }).compose(RxUtils.transformerThread())
                .subscribe(new Consumer<List<MultiItemEntity>>() {
                    @Override
                    public void accept(List<MultiItemEntity> multiItemEntities) throws Exception {
                        mView.setTotalData(multiItemEntities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }

    @Override
    public void refreshData() {

    }

    @Override
    public void loadMoreData() {

    }
}
