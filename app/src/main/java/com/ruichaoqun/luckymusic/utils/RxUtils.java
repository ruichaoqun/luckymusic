package com.ruichaoqun.luckymusic.utils;

import android.util.Log;

import com.ruichaoqun.luckymusic.Constants;
import com.ruichaoqun.luckymusic.data.DataRepository;
import com.ruichaoqun.luckymusic.data.bean.BaseResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class RxUtils {
    public static final int SUCCESS = 0;

    public static <T> ObservableTransformer<T,T> transformerThread(){
        return upstream -> upstream.subscribeOn(Schedulers.newThread())
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

    /**
     * 统一线程处理
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */
    public static <T>  ObservableTransformer<BaseResponse<T>, BaseResponse<T>> tokenTimeoutHelper() {
        return new ObservableTransformer<BaseResponse<T>, BaseResponse<T>>() {
            @Override
            public ObservableSource<BaseResponse<T>> apply(Observable<BaseResponse<T>> observable) {
                return observable.flatMap(new Function<BaseResponse<T>, ObservableSource<BaseResponse<T>>>() {
                    @Override
                    public ObservableSource<BaseResponse<T>> apply(BaseResponse<T> t) throws Exception {
                        if (Constants.needRefreshToken) {
                            Log.w("QQQQQQ", Thread.currentThread().getName()+"token失效");
                            return Observable.error(new TokenExpiredException());
                        }
                        Log.w("QQQQQQ", Thread.currentThread().getName()+"获取数据成功");
                        return Observable.just(t);
                    }
                })
                        .retryWhen(throwableObservable -> throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                if (throwable instanceof TokenExpiredException) {
                                    return TokenLoader.getInstance().getNetTokenLocked();
//                                    //模拟刷新token
//                                    if(Constants.isRefreshToken){
//                                        Log.w("QQQQQQ", Thread.currentThread().getName()+"token正在被获取，重新请求");
//                                        return Observable.timer(500,TimeUnit.MILLISECONDS);
//                                    }else{
//                                        Log.w("QQQQQQ", Thread.currentThread().getName()+"开始获取token");
//                                        Constants.isRefreshToken = true;
//                                        PublishSubject mPublishSubject = PublishSubject.create();
//                                        Observable.timer(5000, TimeUnit.MILLISECONDS)
//                                                .doOnNext(new Consumer<Long>() {
//                                                    @Override
//                                                    public void accept(Long aLong) throws Exception {
//                                                        Log.w("QQQQQQ", Thread.currentThread().getName()+"token获取成功，刷新token");
//                                                        Constants.testToken = 2;
//                                                        Constants.needRefreshToken = false;
//                                                    }
//                                                })
//                                                .subscribe(mPublishSubject);
//                                        return mPublishSubject;
//                                    }
                                }
                                return  Observable.error(throwable);
                            }
                        }));
            }
        };
    }

    static class TokenExpiredException extends Exception{

    }

    static class TokenHasInvalidateException extends Exception{

    }

}
