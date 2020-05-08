package com.ruichaoqun.luckymusic.base.activity;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;


import com.ruichaoqun.luckymusic.di.daggerandroidx.DaggerAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    protected String TAG = this.getClass().getSimpleName();


    protected CompositeDisposable mCompositeDisposable;
    private Unbinder mUnbinder;

    protected abstract @LayoutRes int getLayoutResId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mUnbinder = ButterKnife.bind(this);
        mCompositeDisposable = new CompositeDisposable();
        initPresenter();
        initParams();
        initView();
        initData();
    }

    /**
     * 设置presenter
     */
    protected  void initPresenter(){};

    /**
     * 传递参数解析
     */
    protected abstract void initParams();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 加载以及初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        mCompositeDisposable.dispose();
        super.onDestroy();
    }
}
