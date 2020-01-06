package com.ruichaoqun.luckymusic.base.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.support.v4.media.MediaBrowserCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ruichaoqun.luckymusic.base.activity.BaseMediaBrowserActivity;
import com.ruichaoqun.luckymusic.di.daggerandroidx.DaggerFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Rui Chaoqun
 * @date :2019/12/2 9:26
 * description:
 */
public abstract class BaseFragment extends DaggerFragment {
    public static String TAG = BaseFragment.class.getSimpleName();
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

    public MediaBrowserCompat getMediaBrowser(){
        if(isAdded() && getActivity() instanceof BaseMediaBrowserActivity){
            return ((BaseMediaBrowserActivity)getActivity()).getMediaBrowser();
        }
        return null;
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
