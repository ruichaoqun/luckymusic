package com.ruichaoqun.luckymusic.ui.main.discover;


import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.fragment.BaseSwipeMoreTableFragment;
import com.ruichaoqun.luckymusic.ui.main.MainActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WanAndroidFragment extends BaseSwipeMoreTableFragment<MultiItemEntity, WanAndroidContact.Presenter> implements WanAndroidContact.View, MainActivity.DispatchResetThemeInterface{


    public WanAndroidFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wan_android;
    }

    @Override
    protected void initView() {
        bindRefreshLayout(R.id.refresh_layout);
        bindSwipeRecycler(R.id.recycler_view,new WanAndroidAdapter(arrayList));
    }

    @Override
    protected void initData() {
        startRefresh();
    }

    @Override
    public void onRefresh() {
        mPresenter.initData();
    }

    @Override
    public void loadMore() {

    }


    @Override
    public void setTotalData(List<MultiItemEntity> multiItemEntityList) {
        stopRefresh();
        arrayList.clear();
        arrayList.addAll(multiItemEntityList);
        notifyDataSetChanged();
    }

    @Override
    public void dispatchResetTheme() {
        applyCurrentTheme();
        notifyDataSetChanged();
    }
}
