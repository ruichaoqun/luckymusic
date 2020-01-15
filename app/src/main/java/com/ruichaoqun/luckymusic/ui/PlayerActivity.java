package com.ruichaoqun.luckymusic.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.media.MediaDataType;
import com.ruichaoqun.luckymusic.utils.CommonUtils;
import com.ruichaoqun.luckymusic.utils.StylusAnimation;
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
    @BindView(R.id.iv_stylus)
    ImageView mStylus;
    private RotationRelativeLayout mCurrentDiscLayout;
    private List<MediaBrowserCompat.MediaItem> mCurrentPlayList;

    private Handler clientHandler = new Handler();

    //唱针移开唱片动画
    public StylusAnimation mStylusRemoveAnimation;
    //唱针返回唱片动画
    public StylusAnimation mStylusReturnAnimation;

    public int mStylusAnimationType = 2;


    private Runnable mStylusRemoveRunnable = new Runnable() {
        @Override
        public void run() {
            if (PlayerActivity.this.mStylusAnimationType == 1 || PlayerActivity.this.mStylusAnimationType == 3) {
                PlayerActivity.this.clientHandler.postDelayed(this, 50);
            } else if (PlayerActivity.this.mStylusAnimationType != 4) {
                PlayerActivity.this.mStylus.clearAnimation();
                PlayerActivity.this.mStylus.startAnimation(PlayerActivity.this.mStylusRemoveAnimation);
            }
        }
    };

    private Runnable mStylusReturnRunnable = new Runnable() {
        @Override
        public void run() {
            if (PlayerActivity.this.mStylusAnimationType == 1 || PlayerActivity.this.mStylusAnimationType == 3) {
                PlayerActivity.this.clientHandler.postDelayed(this, 50);
            } else if (PlayerActivity.this.mStylusAnimationType != 2) {
                PlayerActivity.this.mStylus.clearAnimation();
                PlayerActivity.this.mStylus.startAnimation(PlayerActivity.this.mStylusReturnAnimation);
            }
        }
    };

    public void startStylusRemove(){
        this.clientHandler.removeCallbacks(this.mStylusRemoveRunnable);
        this.clientHandler.removeCallbacks(this.mStylusReturnRunnable);
        this.clientHandler.post(this.mStylusRemoveRunnable);
    }

    public void startStylusReturn(){
        this.clientHandler.removeCallbacks(this.mStylusRemoveRunnable);
        this.clientHandler.removeCallbacks(this.mStylusReturnRunnable);
        this.clientHandler.post(this.mStylusReturnRunnable);
    }



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
        this.mCurrentDiscLayout = (RotationRelativeLayout) this.mViewFlipper.getCurrentView();
    }

    @Override
    protected void initData() {
        //唱针旋转动画X轴焦点
        int StylusPivotX = UiUtils.dp2px(24.888f);
        //唱针旋转动画Y轴焦点
        int StylusPivotY = UiUtils.dp2px(42.222f);
        this.mStylusRemoveAnimation = new StylusAnimation(0.0f,-25.0f,StylusPivotX,StylusPivotY);
        this.mStylusRemoveAnimation.setDuration(300);
        this.mStylusRemoveAnimation.setRepeatCount(0);
        this.mStylusRemoveAnimation.setFillAfter(true);
        this.mStylusRemoveAnimation.setFillEnabled(true);
        this.mStylusRemoveAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                PlayerActivity.this.mStylusAnimationType = 3;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (PlayerActivity.this.mStylusRemoveAnimation.getInterpolatedTime() >= 1.0f) {
                    PlayerActivity.this.mStylusAnimationType = 4;
                    PlayerActivity.this.mCurrentDiscLayout.pause();
                    PlayerActivity.this.mStylusReturnAnimation.setDegrees(Integer.MIN_VALUE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        this.mStylusReturnAnimation = new StylusAnimation(-25.0f,0.0f,StylusPivotX,StylusPivotY);
        this.mStylusReturnAnimation.setDuration(300);
        this.mStylusReturnAnimation.setRepeatCount(0);
        this.mStylusReturnAnimation.setFillAfter(true);
        this.mStylusReturnAnimation.setFillEnabled(true);
        this.mStylusReturnAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                PlayerActivity.this.mStylusAnimationType = 2;
                PlayerActivity.this.a(PlayerActivity.this.ak.getAnimationHolder());
                PlayerActivity.this.mStylusRemoveAnimation.setDegrees(Integer.MIN_VALUE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        this.mViewFlipper.setOnPlayerDiscListener(new PlayerDiscViewFlipper.OnPlayerDiscListener() {
            @Override
            public void onScrolled(boolean z) {
                //滑动回调，此处可以处理唱针的切换以及当前封面动画的暂停
                PlayerActivity.this.startStylusRemove();
                PlayerActivity.this.mCurrentDiscLayout.pause();
            }


            @Override
            public void onDiscDirectionChange(Boolean bool) {
                Log.w(TAG,"onDiscDirectionChange-->"+bool);
            }

            @Override
            public void onDiscSwitchComplete(boolean z, boolean z2, boolean z3) {
                Log.w(TAG,"onDiscSwitchComplete-->"+z+"  "+z2+"   "+z3);
                PlayerActivity.this.startStylusReturn();
            }

            @Override
            public void onDiscSwitchHalf(Boolean bool) {
                Log.w(TAG,"onDiscSwitchHalf-->"+bool);
            }


        });
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
