package com.ruichaoqun.luckymusic.data.http;

import com.ruichaoqun.luckymusic.data.bean.BannerItemBean;
import com.ruichaoqun.luckymusic.data.bean.BaseResponse;
import com.ruichaoqun.luckymusic.data.bean.HomePageBean;
import com.ruichaoqun.luckymusic.data.bean.HomePageItemBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:32
 * description:
 */
public interface HttpDataSource {
    Observable<BaseResponse<List<BannerItemBean>>> getBannerList();

    Observable<BaseResponse<HomePageBean>> getHomeList(int page);

    Observable<BaseResponse<List<HomePageItemBean>>> getTopList();
}
