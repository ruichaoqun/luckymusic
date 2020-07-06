package com.ruichaoqun.luckymusic.widget.BottomSheetDialog;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMediaBrowserActivity;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.List;

public class PlaylistBottomSheet extends BaseBottomSheet {
    private static final float rate = 0.521f;

    private PlaylistAdapter mPlaylistAdapter;
    private TextView mTvheaderCount;
    private TextView mTvPlayMode;
    private TextView mTvCollect;
    private ImageView mIvDeleteAll;
    private BottomSheetRecyclerView mRvPlayList;

    private List<MediaSessionCompat.QueueItem> mQueueItems;
    private MediaMetadataCompat mCurrentMetadata;
    private int mPlayMode;

    public PlaylistBottomSheet(Context context,  @StyleRes int themeResId,List<MediaSessionCompat.QueueItem> mQueueItems,MediaMetadataCompat mCurrentMetadata,int playMode) {
        super(context, themeResId);
        this.mCurrentMetadata = mCurrentMetadata;
        this.mQueueItems = mQueueItems;
        this.mPlayMode = playMode;
    }

    @Override
    public void initCustomView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_play_list,  null);
        this.mDialogView.addView(inflate);
        this.mTvheaderCount = inflate.findViewById(R.id.tv_header_count);
        this.mTvPlayMode = inflate.findViewById(R.id.tv_play_mode);
        this.mTvCollect = inflate.findViewById(R.id.tv_collect);
        this.mIvDeleteAll = inflate.findViewById(R.id.iv_delete_all);
        this.mRvPlayList = inflate.findViewById(R.id.rv_play_list);
        this.mRvPlayList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRvPlayList.setScreenHeigtRate(rate);
        this.mDialogView.mTarget = this.mRvPlayList;
        this.mDialogView.addIgnoreScrollView(inflate.findViewById(R.id.tv_header_title));
        setContentView(this.mDialogView);
        this.mPlaylistAdapter = new PlaylistAdapter(R.layout.item_adapter_play_list,mQueueItems);
        this.mRvPlayList.setAdapter(this.mPlaylistAdapter);
        this.mTvheaderCount.setText("("+this.mQueueItems.size()+")");
        this.setPlayMode(mPlayMode);
        this.mPlaylistAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Activity activity = getActivity();
                if(activity != null && activity instanceof BaseMediaBrowserActivity && !activity.isFinishing()){
                    ((BaseMediaBrowserActivity)activity).deletePlayItem(position);
                }
            }
        });
        this.mPlaylistAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Activity activity = getActivity();
                if(activity != null && activity instanceof BaseMediaBrowserActivity && !activity.isFinishing()){
                    ((BaseMediaBrowserActivity)activity).playFromQueueIndex(position);
                }
            }
        });

        this.mTvPlayMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if(activity != null && activity instanceof BaseMediaBrowserActivity && !activity.isFinishing()){
                    ((BaseMediaBrowserActivity)activity).switchPlayMode();
                }
            }
        });

        this.mTvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.mIvDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if(activity != null && activity instanceof BaseMediaBrowserActivity && !activity.isFinishing()){
                    ((BaseMediaBrowserActivity)activity).deleteAllPlaylist();
                }
            }
        });
    }

    public void setPlayMode(int playMode) {
        if(playMode == PlaybackStateCompat.SHUFFLE_MODE_ALL){
            this.mTvPlayMode.setText(getContext().getString(R.string.play_mode_shuffle_play));
        }else if(playMode == PlaybackStateCompat.REPEAT_MODE_ALL){
            this.mTvPlayMode.setText(getContext().getString(R.string.play_mode_list_circulation));
        }else{
            this.mTvPlayMode.setText(getContext().getString(R.string.play_mode_single_cycle));
        }
    }

    public void setCurrentMetadata(MediaMetadataCompat currentMetadata) {
        this.mCurrentMetadata = currentMetadata;
        this.mPlaylistAdapter.notifyDataSetChanged();
        scrollToTargetMusic(false);
    }

    public void setQueueItems(List<MediaSessionCompat.QueueItem> queueItems) {
        if(queueItems.size() == 0){
            dismiss();
            return;
        }
        this.mQueueItems = queueItems;
        this.mTvheaderCount.setText("("+queueItems.size()+")");
        this.mPlaylistAdapter.setNewData(queueItems);
        scrollToTargetMusic(false);
    }

    @Override
    public void onBottomSheetDismiss() {
    }

    @Override
    public void onBottomSheetShow() {
        scrollToTargetMusic(false);
    }

    private void scrollToTargetMusic(boolean b) {
        for (int i = 0; i < mQueueItems.size(); i++) {
            if(TextUtils.equals(mCurrentMetadata.getDescription().getMediaId(),mQueueItems.get(i).getDescription().getMediaId())){
                mRvPlayList.scrollToPosition(i);
                return;
            }
        }
    }

    public static PlaylistBottomSheet showMusicPlayList(Context context, List<MediaSessionCompat.QueueItem> mQueueItems,MediaMetadataCompat metadataCompat,int playMode) {
        PlaylistBottomSheet playlistBottomSheet = new PlaylistBottomSheet(context, R.style.f3, mQueueItems,metadataCompat,playMode);
        playlistBottomSheet.show();
        return playlistBottomSheet;
    }

    private class PlaylistAdapter extends BaseQuickAdapter<MediaSessionCompat.QueueItem, BaseViewHolder>{
        private int color;

        public PlaylistAdapter(int layoutResId, @Nullable List<MediaSessionCompat.QueueItem> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, MediaSessionCompat.QueueItem item) {
            helper.setText(R.id.tv_name,item.getDescription().getTitle());
            helper.setText(R.id.tv_author," - "+item.getDescription().getSubtitle());
            helper.setGone(R.id.iv_currentView, TextUtils.equals(item.getDescription().getMediaId(),mCurrentMetadata.getDescription().getMediaId()));
            helper.addOnClickListener(R.id.iv_delete);
        }
    }
}
