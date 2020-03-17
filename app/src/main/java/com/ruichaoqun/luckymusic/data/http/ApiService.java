package com.ruichaoqun.luckymusic.data.http;

import com.ruichaoqun.luckymusic.data.bean.BannerItemBean;
import com.ruichaoqun.luckymusic.data.bean.BaseResponse;
import com.ruichaoqun.luckymusic.data.bean.HomePageBean;
import com.ruichaoqun.luckymusic.data.bean.HomePageItemBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    /**
     * 获取banner列表
     * @return 返回数据
     */
    @GET(value = "banner/json")
    Observable<BaseResponse<List<BannerItemBean>>> getBannerList();

    @GET(value = "article/list/{page}/json")
    Observable<BaseResponse<HomePageBean>> getHomeList(@Path("page") int page);


    @GET(value = "article/top/json")
    Observable<BaseResponse<List<HomePageItemBean>>> getTopList();

    Call<BaseResponse<List<HomePageItemBean>>> uploadError();

}
