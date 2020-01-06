package com.ruichaoqun.luckymusic.ui.localmedia.fragment;

import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.BannerListBean;
import com.ruichaoqun.luckymusic.data.bean.HomePageItemBean;
import com.ruichaoqun.luckymusic.data.bean.HomePageItemType;
import com.ruichaoqun.luckymusic.ui.main.discover.BannerAdapter;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

public class LocalMediaAdapter extends BaseQuickAdapter<MediaBrowserCompat.MediaItem, BaseViewHolder> {
    private MediaBrowserCompat.MediaItem mMediaItem;

    public LocalMediaAdapter(int layoutResId, @Nullable List<MediaBrowserCompat.MediaItem> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, MediaBrowserCompat.MediaItem item) {
        helper.setText(R.id.tv_title,item.getDescription().getTitle());
        helper.setText(R.id.tv_subTitle,item.getDescription().getSubtitle()+"-"+item.getDescription().getDescription());
        helper.setVisible(R.id.iv_playing,mMediaItem == item);
    }

    public void setMediaItem(MediaBrowserCompat.MediaItem mediaItem) {
        int position = -1;
        int currentPosition = -1;
        for (int i = 0; i < getData().size(); i++) {
            if(mMediaItem == getData().get(i)){
                position = i;
            }
            if(mediaItem == getData().get(i)){
                currentPosition = i;
            }
        }
        if(position != -1){
            notifyItemChanged(position);
        }
        notifyItemChanged(currentPosition);
    }
}
