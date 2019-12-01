package com.ruichaoqun.luckymusic.utils;

import com.ruichaoqun.luckymusic.data.bean.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
}
