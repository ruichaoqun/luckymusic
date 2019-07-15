package com.ruichaoqun.luckymusic.basic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.ruichaoqun.luckymusic.R;

import io.reactivex.disposables.CompositeDisposable;

public class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparentStatusBar(true);
        mCompositeDisposable = new CompositeDisposable();
    }

    public void initToolbar(){
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (this.mToolbar == null) {
//            this.mToolbar = (Toolbar) getLayoutInflater().inflate(R.layout.aam, null);
//        }
        this.mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseActivity.this.onToolBarClick();
            }
        });
        setSupportActionBar(this.mToolbar);

    }

    private void onToolBarClick() {

    }

    public void transparentStatusBar(boolean z) {
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (z) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                getWindow().addFlags(Integer.MIN_VALUE);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                getWindow().clearFlags(Integer.MIN_VALUE);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }
}
