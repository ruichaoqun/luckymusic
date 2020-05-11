package com.ruichaoqun.luckymusic.utils;

import android.util.Log;

import com.ruichaoqun.luckymusic.Constants;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * @author Rui Chaoqun
 * @date :2020/5/11 14:40
 * description:
 */
public class TokenLoader {
    private static final String TAG = "QQQQQQ";

    private AtomicBoolean mRefreshing = new AtomicBoolean(false);
    private PublishSubject<String> mPublishSubject;
    private Observable<String> mTokenObservable;

    private TokenLoader() {
        mPublishSubject = PublishSubject.create();
        mTokenObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Thread.sleep(1000);
                Log.w(TAG, "发送Token");
                e.onNext(String.valueOf(System.currentTimeMillis()));
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String token) throws Exception {
                Log.w(TAG, "存储Token=" + token);
                Constants.testToken = 2;
                Constants.needRefreshToken = false;
                mRefreshing.set(false);
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mRefreshing.set(false);
            }
        }).subscribeOn(Schedulers.io());
    }

    public static TokenLoader getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final TokenLoader INSTANCE = new TokenLoader();
    }

    public Observable<String> getNetTokenLocked() {
        if (mRefreshing.compareAndSet(false, true)) {
            Log.w(TAG, "没有请求，发起一次新的Token请求");
            startTokenRequest();
        } else {
            Log.w(TAG, "已经有请求，直接返回等待");
        }
        return mPublishSubject;
    }

    private void startTokenRequest() {
        mTokenObservable.subscribe(mPublishSubject);
    }

}
