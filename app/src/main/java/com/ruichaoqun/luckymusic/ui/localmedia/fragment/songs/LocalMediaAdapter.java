package com.ruichaoqun.luckymusic.ui.localmedia.fragment.songs;

import android.content.Context;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.ui.main.discover.BannerAdapter;
import com.ruichaoqun.luckymusic.utils.UiUtils;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

public class LocalMediaAdapter extends BaseQuickAdapter<SongBean, BaseViewHolder> {
    private int currentPosition = -1;
    private View miniPlayerBarStub;

    public LocalMediaAdapter(int layoutResId, @Nullable List<SongBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper,SongBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_subTitle,item.getArtist()+"-"+item.getAlbum());
        if(currentPosition != -1){
            helper.setGone(R.id.iv_playing,getData().get(currentPosition) == item);
        }
    }

    public void setMediaItem(int position) {
        int temp = currentPosition;
        currentPosition = position;
        if(temp != -1){
            notifyItemChanged(temp);
        }
        notifyItemChanged(currentPosition);
    }

    public void setMediaWithId(String id){
        if(!TextUtils.isEmpty(id)){
            long _id = Long.valueOf(id);
            for (int i = 0; i < getData().size(); i++) {
                if(getData().get(i).getId() == _id){
                    setMediaItem(i);
                    break;
                }
            }
        }
    }

    public void showMiniPlayerBarStub(Context context,boolean show){
        if(miniPlayerBarStub == null){
            miniPlayerBarStub = new View(context);
            addFooterView(miniPlayerBarStub);
        }
        int height = UiUtils.dp2px(49);
        if(!show){
            miniPlayerBarStub.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0));
            return;
        }
        if(show && (this.miniPlayerBarStub.getLayoutParams() == null || this.miniPlayerBarStub.getLayoutParams().height != height)){
            miniPlayerBarStub.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
        }
    }
}
