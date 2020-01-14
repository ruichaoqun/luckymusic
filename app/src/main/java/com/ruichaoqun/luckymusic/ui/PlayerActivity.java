package com.ruichaoqun.luckymusic.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.media.MediaDataType;
import com.ruichaoqun.luckymusic.utils.CommonUtils;
import com.ruichaoqun.luckymusic.utils.UiUtils;
import com.ruichaoqun.luckymusic.widget.PlayerDiscViewFlipper;
import com.ruichaoqun.luckymusic.widget.RotationRelativeLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @author Rui Chaoqun
 * @date :2020-1-13 9:37:48
 * description:PlayerActivity
 */
public class PlayerActivity extends BaseMVPActivity<PlayerContact.Presenter> {

    @BindView(R.id.view_flipper)
    PlayerDiscViewFlipper mViewFlipper;
    private RotationRelativeLayout mRelativeLayout;
    private List<MediaBrowserCompat.MediaItem> mCurrentPlayList;


    public static void launchFrom(Activity activity){
        activity.startActivity(new Intent(activity,PlayerActivity.class));
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.player_activity;
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {
        this.mRelativeLayout = (RotationRelativeLayout) this.mViewFlipper.getCurrentView();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onMediaServiceConnected() {
        getCurrentPlayList();
    }

    /**
     *  获取当前播放音乐列表
     */
    private void getCurrentPlayList() {
        List<MediaSessionCompat.QueueItem> queueItems= this.mControllerCompat.getQueue();
        if(queueItems != null){
            Log.w(TAG,"queueItems-->"+queueItems.size());
            for (int i = 0; i < queueItems.size(); i++) {
                Log.w(TAG,"queueItem-->"+queueItems.get(i).getDescription().getMediaId());
            }
        }else{
            Log.w(TAG,"queueItems-->"+null);
        }

        MediaMetadataCompat mediaMetadataCompat = this.mControllerCompat.getMetadata();
        if(mediaMetadataCompat != null){
            Log.w(TAG,"mediaMetadataCompat-->"+mediaMetadataCompat.getDescription().getMediaId());
        }else{
            Log.w(TAG,"mediaMetadataCompat-->"+"空");
        }

        PlaybackStateCompat playbackStateCompat = this.mControllerCompat.getPlaybackState();
        if(playbackStateCompat != null){
            Log.w(TAG,"playbackStateCompat-->"+playbackStateCompat.getState());
        }else{
            Log.w(TAG,"playbackStateCompat-->"+null);
        }
    }

    /**
     * 当前音乐改变
     * @param metadata
     */
    @Override
    public void onMetadataChanged(MediaMetadataCompat metadata) {
        super.onMetadataChanged(metadata);

    }

    /**
     * 当前播放状态改变
     * @param state
     */
    @Override
    public void onPlaybackStateChanged(PlaybackStateCompat state) {
        super.onPlaybackStateChanged(state);

    }

    @Override
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
        super.onQueueChanged(queue);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initToolBar();
        transparentStatusBar(true);
    }

    @Override
    public boolean needToolBar() {
        return false;
    }

    private int getToolbarHeight() {
        return (CommonUtils.versionAbove19()?UiUtils.getStatusBarHeight(this):0)+UiUtils.getToolbarHeight();
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        this.toolbar.setPadding(0, UiUtils.getStatusBarHeight(this), 0, 0);
        ((RelativeLayout.LayoutParams) this.toolbar.getLayoutParams()).height = getToolbarHeight();
        this.toolbar.setBackgroundColor(Color.TRANSPARENT);
        applyToolbarCurrentThemeWithViewColor(this.toolbar);
        setTitle(R.string.is_playing);
    }

    @Override
    public boolean isToolbarOnImage() {
        return false;
    }

    @Override
    public boolean isNeedMiniPlayerBar() {
        return false;
    }
}
