package com.ruichaoqun.luckymusic.view.splash;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ruichaoqun.luckymusic.MainActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.basic.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        Disposable subscribe = Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startAnimation();
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                });
        mCompositeDisposable.add(subscribe);
    }

    private void startAnimation() {
        ObjectAnimator animator = ObjectAnimator.
    }

}
