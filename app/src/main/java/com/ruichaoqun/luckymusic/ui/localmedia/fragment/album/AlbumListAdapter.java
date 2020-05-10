package com.ruichaoqun.luckymusic.ui.localmedia.fragment.album;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.common.GlideApp;
import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.ui.PlayerActivity;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.List;

public class AlbumListAdapter extends BaseQuickAdapter<AlbumBean, BaseViewHolder> {
    private View miniPlayerBarStub;
    private String currentAlbum = "";

    public AlbumListAdapter(int layoutResId, @Nullable List<AlbumBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper,AlbumBean item) {
        helper.setText(R.id.tv_title,item.getAlbum());
        helper.setText(R.id.tv_subTitle,String.format(mContext.getResources().getString(R.string.artist_track_number),item.getNumsongs())+" "+item.getArtist());
        helper.setGone(R.id.iv_profile,!TextUtils.equals(currentAlbum,item.getAlbum()));
        helper.setGone(R.id.iv_playing,TextUtils.equals(currentAlbum,item.getAlbum()));
        helper.addOnClickListener(R.id.iv_profile);
        GlideApp.with(mContext)
                .load(ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),item.getId()))
                .transform(new RoundedCorners(10))
                .error(R.drawable.ic_disc_playhoder)
                .into((ImageView) helper.getView(R.id.iv_ablum));
    }

    public void setCurrentAlbum(String currentAlbum) {
        this.currentAlbum = currentAlbum;
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
