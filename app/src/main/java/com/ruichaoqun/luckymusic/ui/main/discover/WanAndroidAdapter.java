package com.ruichaoqun.luckymusic.ui.main.discover;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.HomePageItemType;

import java.util.List;

public class WanAndroidAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public WanAndroidAdapter(List data) {
        super(data);
        addItemType(HomePageItemType.BANNER, R.layout.);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Object item) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }
}
