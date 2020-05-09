package com.ruichaoqun.luckymusic.utils;

import android.util.Log;

import com.ruichaoqun.luckymusic.data.bean.BaseResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {
    public static final int SUCCESS = 0;

    public static <T> ObservableTransformer<T,T> transformerThread(){
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Function<BaseResponse<T>, ObservableSource<T>> transformerResult(){
        return tBaseResponse -> {
            if(tBaseResponse.getErrorCode() == SUCCESS){
                return Observable.just(tBaseResponse.getData());
            }
            return Observable.error(new Throwable());
        };
    }

//    /**
//     * 统一线程处理
//     * @param <T> 指定的泛型类型
//     * @return ObservableTransformer
//     */
//    public static <T>  ObservableTransformer<BaseResponse<T>, BaseResponse<T>> tokenTimeoutHelper() {
//        return new ObservableTransformer<BaseResponse<T>, BaseResponse<T>>() {
//            @Override
//            public ObservableSource<BaseResponse<T>> apply(Observable<BaseResponse<T>> observable) {
//                return observable.flatMap(new Function<BaseResponse<T>, ObservableSource<BaseResponse<T>>>() {
//                    @Override
//                    public ObservableSource<BaseResponse<T>> apply(BaseResponse<T> t) throws Exception {
//                        if (t != null && t.getErrorCode() == 50000) {
//                            Log.w("QQQQQQ", "token失效");
//                            return Observable.error(new TokenExpiredException());
//                        }
//                        return Observable.just(t);
//                    }
//                })
//                        .retryWhen(throwableObservable -> throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
//                            @Override
//                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
//                                if (throwable instanceof TokenExpiredException) {
//                                    String macId = SharedPreUtil.getString(ApiConstant.ReqKey.COMM_MAC, "");
//                                    Map<String,String> macMap = new HashMap<>();
//                                    macMap.put("inquiryParam",macId);
//                                    return RetrofitJavaUtil.getRetrofitUtil().create(AnychatRequestApi.class)
//                                            .emcLogin(macMap)
//                                            .subscribeOn(Schedulers.io())
//                                            .doOnNext(new Consumer<BaseBean<LoginResultResp>>() {
//                                                @Override
//                                                public void accept(BaseBean<LoginResultResp> loginResultRespBaseBean) throws Exception {
//                                                    Log.w("QQQQQQ","重新获取token成功"+loginResultRespBaseBean.getData().getAccess_token());
//                                                    CacheData.setLoginResp(loginResultRespBaseBean.getData());
//                                                    CacheData.setLogin(true);
//                                                    EventBus.getDefault().post(new RefreshDocListEvent());
//                                                }
//                                            });
//                                }
//                                return  Observable.error(throwable);
//                            }
//                        }));
//            }
//        };
//    }

    static class TokenExpiredException extends Exception{

    }

}
