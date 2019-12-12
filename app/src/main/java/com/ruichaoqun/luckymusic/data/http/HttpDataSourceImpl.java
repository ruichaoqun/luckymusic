package com.ruichaoqun.luckymusic.data.http;

import com.ruichaoqun.luckymusic.data.bean.BannerItemBean;
import com.ruichaoqun.luckymusic.data.bean.BaseResponse;
import com.ruichaoqun.luckymusic.data.bean.HomePageBean;
import com.ruichaoqun.luckymusic.data.bean.HomePageItemBean;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:48
 * description:
 */
@Singleton
public class HttpDataSourceImpl implements HttpDataSource {
    private ApiService mApiService;

    @Inject
    public HttpDataSourceImpl(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    @Override
    public Observable<BaseResponse<List<BannerItemBean>>> getBannerList() {
        return mApiService.getBannerList();
    }

    @Override
    public Observable<BaseResponse<HomePageBean>> getHomeList(int page) {
        return mApiService.getHomeList(page);
    }

    @Override
    public Observable<BaseResponse<List<HomePageItemBean>>> getTopList() {
        return mApiService.getTopList();
    }
}
