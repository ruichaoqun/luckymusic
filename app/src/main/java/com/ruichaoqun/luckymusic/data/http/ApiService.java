package com.ruichaoqun.luckymusic.data.http;

import com.ruichaoqun.luckymusic.data.bean.BannerItemBean;
import com.ruichaoqun.luckymusic.data.bean.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    /**
     * 获取banner列表
     * @return 返回数据
     */
    @GET(value = "banner/json")
    Observable<BaseResponse<List<BannerItemBean>>> getBannerList();

}
