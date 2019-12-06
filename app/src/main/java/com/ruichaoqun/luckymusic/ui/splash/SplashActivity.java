package com.ruichaoqun.luckymusic.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ruichaoqun.luckymusic.ui.main.MainActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {
    private ImageView mSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        getWindow().addFlags(Integer.MIN_VALUE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSplash = (ImageView) findViewById(R.id.iv_splash);
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
