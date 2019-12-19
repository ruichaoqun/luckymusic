package com.ruichaoqun.luckymusic.base.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ruichaoqun.luckymusic.di.daggerandroidx.DaggerFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Rui Chaoqun
 * @date :2019/12/2 9:26
 * description:
 */
public abstract class BaseFragment extends DaggerFragment {
    private Unbinder mUnbinder;


    protected abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this,view);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
