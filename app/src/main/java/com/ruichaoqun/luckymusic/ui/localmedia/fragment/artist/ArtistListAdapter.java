package com.ruichaoqun.luckymusic.ui.localmedia.fragment.artist;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.List;

public class ArtistListAdapter extends BaseQuickAdapter<ArtistBean, BaseViewHolder> {
    private View miniPlayerBarStub;
    private String currentArtist = "";

    public ArtistListAdapter(int layoutResId, @Nullable List<ArtistBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper,ArtistBean item) {
        helper.setText(R.id.tv_title,item.getArtistName());
        helper.setText(R.id.tv_subTitle,String.format(mContext.getResources().getString(R.string.artist_track_number),item.getTracksNumber()));
        helper.setGone(R.id.iv_profile,!TextUtils.equals(currentArtist,item.getArtistName()));
        helper.setGone(R.id.iv_playing,TextUtils.equals(currentArtist,item.getArtistName()));
        helper.addOnClickListener(R.id.iv_profile);
    }

    public void setCurrentArtist(String currentArtist) {
        this.currentArtist = currentArtist;
    }

    public void showMiniPlayerBarStub(Context context, boolean show){
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
