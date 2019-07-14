package com.ruichaoqun.luckymusic.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.ruichaoqun.luckymusic.MainActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.basic.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {
    private ImageView mSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSplash = findViewById(R.id.iv_splash);
        Disposable subscribe = Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startAnimation();
                    }
                });
        mCompositeDisposable.add(subscribe);
    }

    private void startAnimation() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_main,R.anim.anim_splash);
    }
}
