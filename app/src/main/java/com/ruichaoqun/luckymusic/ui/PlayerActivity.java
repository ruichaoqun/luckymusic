package com.ruichaoqun.luckymusic.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.common.GlideApp;
import com.ruichaoqun.luckymusic.utils.CommonUtils;
import com.ruichaoqun.luckymusic.utils.RenderScriptTransformation;
import com.ruichaoqun.luckymusic.utils.StylusAnimation;
import com.ruichaoqun.luckymusic.utils.TimeUtils;
import com.ruichaoqun.luckymusic.utils.UiUtils;
import com.ruichaoqun.luckymusic.widget.BottomSheetDialog.PlaylistBottomSheet;
import com.ruichaoqun.luckymusic.widget.PlayerDiscViewFlipper;
import com.ruichaoqun.luckymusic.widget.RotationRelativeLayout;
import com.ruichaoqun.luckymusic.utils.ViewSwitcherTarget;

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

    @BindView(R.id.iv_play_mode)
    ImageView mPlayMode;
    @BindView(R.id.iv_play_previous)
    ImageView mPlayPrevious;
    @BindView(R.id.iv_play_pause)
    ImageView mPlayPause;
    @BindView(R.id.iv_play_next)
    ImageView mPlayNext;
    @BindView(R.id.iv_play_list)
    ImageView mPlayList;
    @BindView(R.id.tv_current_time)
    TextView mCurrentPosition;
    @BindView(R.id.tv_total_time)
    TextView mTotalPosition;
    @BindView(R.id.player_seek_bar)
    SeekBar mPlayerSeekBar;
    @BindView(R.id.vs_bacground)
    ViewSwitcher mVsBacground;

    private ViewSwitcherTarget mViewSwitcherTarget;

    private RotationRelativeLayout mCurrentDiscLayout;

    private Handler clientHandler = new Handler();

    //唱针移开唱片动画
    public StylusAnimation mStylusRemoveAnimation;
    //唱针返回唱片动画
    public StylusAnimation mStylusReturnAnimation;

    public int mStylusAnimationType = STYLUS_ON;

    public static final int STYLUS_ON = 2;
    public static final int STYLUS_OFF = 4;
    public static final int STYLUS_ON_TO_OFF = 3;
    public static final int STYLUS_OFF_TO_ON = 1;

    //当前的播放音乐在播放队列中的index，注意不是QueueItem的queueId，在顺序播放但单曲循环中这两个值是相等的，但是在随机播放中这两个值是不相等的
    private int currentDataPosition = -1;
    private MediaSessionCompat.QueueItem nextQueueItem;
    private boolean updatePosition = true;
    private boolean mSeekbarInTouch = false;
    //是否是后台自动切换下一首
    private boolean isBacgroundAutoNext = false;


    private Runnable mStylusRemoveRunnable = new Runnable() {
        @Override
        public void run() {
            if (PlayerActivity.this.mStylusAnimationType == STYLUS_OFF_TO_ON || PlayerActivity.this.mStylusAnimationType == STYLUS_ON_TO_OFF) {
                //当前动画正在进行中，持续回调
                PlayerActivity.this.clientHandler.postDelayed(this, 50);
            } else if (PlayerActivity.this.mStylusAnimationType == STYLUS_ON) {
                //此时唱针ON状态，开启动画
                PlayerActivity.this.mStylus.clearAnimation();
                PlayerActivity.this.mStylus.startAnimation(PlayerActivity.this.mStylusRemoveAnimation);
            }
        }
    };

    private Runnable mStylusReturnRunnable = new Runnable() {
        @Override
        public void run() {
            if (PlayerActivity.this.mStylusAnimationType == STYLUS_OFF_TO_ON || PlayerActivity.this.mStylusAnimationType == STYLUS_ON_TO_OFF) {
                //当前动画正在进行中，持续回调
                PlayerActivity.this.clientHandler.postDelayed(this, 50);
            } else if (PlayerActivity.this.mStylusAnimationType == STYLUS_OFF) {
                //此时唱针OFF状态，开启动画
                PlayerActivity.this.mStylus.clearAnimation();
                PlayerActivity.this.mStylus.startAnimation(PlayerActivity.this.mStylusReturnAnimation);
            }
        }
    };

    private Runnable mCheckPlaybackPositionRunnable = new Runnable() {
        @Override
        public void run() {
            long currentPosition;
            if (mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING) {
                long timeDelta = SystemClock.elapsedRealtime() - mPlaybackState.getLastPositionUpdateTime();
                currentPosition = (long) (mPlaybackState.getPosition() + (timeDelta * mPlaybackState.getPlaybackSpeed()));
            } else {
                currentPosition = mPlaybackState.getPosition();
            }
            if (updatePosition) {
                if (!mSeekbarInTouch) {
                    mCurrentPosition.setText(TimeUtils.getCurrentPosition(currentPosition));
                    mPlayerSeekBar.setProgress(TimeUtils.formateToSeconds(currentPosition));
                }
                checkPlaybackPosition();
            }
        }
    };

    public void startStylusRemove() {
        //清空消息中执行的动画
        this.clientHandler.removeCallbacks(this.mStylusRemoveRunnable);
        this.clientHandler.removeCallbacks(this.mStylusReturnRunnable);
        //开始OFF动画
        this.clientHandler.post(this.mStylusRemoveRunnable);
    }

    public void startStylusReturn() {
        //清空消息中执行的动画
        this.clientHandler.removeCallbacks(this.mStylusRemoveRunnable);
        this.clientHandler.removeCallbacks(this.mStylusReturnRunnable);
        //开始ON动画
        this.clientHandler.post(this.mStylusReturnRunnable);
    }


    public static void launchFrom(Activity activity) {
        activity.startActivity(new Intent(activity, PlayerActivity.class));
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
        this.mCurrentDiscLayout.prepareAnimation();
        this.mCurrentDiscLayout.start();
        mPlayerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mCurrentPosition.setText(TimeUtils.getCurrentPositionFromSeekbar(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mSeekbarInTouch = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSeekbarInTouch = false;
                startSeekPlayer(seekBar);
            }
        });
        mVsBacground.setInAnimation(this, android.R.anim.fade_in);
        mVsBacground.setOutAnimation(this, android.R.anim.fade_out);
        mViewSwitcherTarget = new ViewSwitcherTarget(mVsBacground);
    }

    private void startSeekPlayer(SeekBar seekBar) {
        int duration = seekBar.getProgress();
        mCurrentPosition.setText(TimeUtils.getCurrentPositionFromSeekbar(duration));
        this.mControllerCompat.getTransportControls().seekTo((long) (duration * 1E3));
    }

    @Override
    protected void initData() {
        //唱针旋转动画X轴焦点
        int StylusPivotX = UiUtils.dp2px(24.888f);
        //唱针旋转动画Y轴焦点
        int StylusPivotY = UiUtils.dp2px(42.222f);
        this.mStylusRemoveAnimation = new StylusAnimation(0.0f, -25.0f, StylusPivotX, StylusPivotY);
        this.mStylusRemoveAnimation.setDuration(300);
        this.mStylusRemoveAnimation.setRepeatCount(0);
        this.mStylusRemoveAnimation.setFillAfter(true);
        this.mStylusRemoveAnimation.setFillEnabled(true);
        this.mStylusRemoveAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                PlayerActivity.this.mStylusAnimationType = STYLUS_ON_TO_OFF;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (PlayerActivity.this.mStylusRemoveAnimation.getInterpolatedTime() >= 1.0f) {
                    PlayerActivity.this.mStylusAnimationType = STYLUS_OFF;
                    PlayerActivity.this.mCurrentDiscLayout.pause();
                    PlayerActivity.this.mStylusReturnAnimation.setDegrees(Integer.MIN_VALUE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        this.mStylusReturnAnimation = new StylusAnimation(-25.0f, 0.0f, StylusPivotX, StylusPivotY);
        this.mStylusReturnAnimation.setDuration(300);
        this.mStylusReturnAnimation.setRepeatCount(0);
        this.mStylusReturnAnimation.setFillAfter(true);
        this.mStylusReturnAnimation.setFillEnabled(true);
        this.mStylusReturnAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                PlayerActivity.this.mStylusAnimationType = STYLUS_OFF_TO_ON;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                PlayerActivity.this.mStylusAnimationType = STYLUS_ON;
                PlayerActivity.this.mStylusRemoveAnimation.setDegrees(Integer.MIN_VALUE);
                PlayerActivity.this.mCurrentDiscLayout.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        this.mViewFlipper.setOnPlayerDiscListener(new PlayerDiscViewFlipper.OnPlayerDiscListener() {
            @Override
            public void onScrolled(boolean z) {
                //滑动回调，此处可以处理唱针的切换以及当前封面动画的暂停
                Log.w(TAG, "onScrolled-->");
                PlayerActivity.this.startStylusRemove();
                PlayerActivity.this.mCurrentDiscLayout.pause();
            }


            /**
             * 此回调为flipperView的nextView赋值
             * @param bool false表示下一首，true表示上一首
             */
            @Override
            public void onDiscDirectionChange(Boolean bool) {
                if (PlayerActivity.this.mPlaybackState != null && PlayerActivity.this.queueItems != null) {
                    if (bool) {
                        PlayerActivity.this.nextQueueItem = PlayerActivity.this.queueItems.get(currentDataPosition == 0 ? PlayerActivity.this.queueItems.size() - 1 : (int) (currentDataPosition - 1));
                    } else {
                        PlayerActivity.this.nextQueueItem = PlayerActivity.this.queueItems.get(currentDataPosition == PlayerActivity.this.queueItems.size() - 1 ? 0 : (int) (currentDataPosition + 1));
                    }
                    GlideApp.with(PlayerActivity.this)
                            .load(nextQueueItem.getDescription().getIconUri())
                            .transform(new CircleCrop())
                            .centerCrop()
                            .error(R.drawable.ic_disc_playhoder)
                            .into((ImageView) ((ViewGroup) PlayerActivity.this.mViewFlipper.getNextView()).getChildAt(0));
                }
            }


            /**
             *
             * @param z 是否已切换disc
             * @param justSwitchDisc 是否直接调用切换disc
             * @param isScrollingLeft  是否是向左滑动
             */
            @Override
            public void onDiscSwitchComplete(boolean z, boolean justSwitchDisc, boolean isScrollingLeft) {
                Log.w(TAG, "onDiscSwitchComplete-->" + z + "    " + justSwitchDisc + "    " + isScrollingLeft);
                //如果是外部直接调用切换歌曲，在回调时要设置title和subtitle，还有是否已收藏歌曲

                //如果是滑动的
                if (!z && !isBacgroundAutoNext) {
                    //如果音乐切换成功，开始播放下一首
                    if (isScrollingLeft) {
                        //播放下一首
                        PlayerActivity.this.mControllerCompat.getTransportControls().skipToNext();
                    } else {
                        //播放上一首
                        PlayerActivity.this.mControllerCompat.getTransportControls().skipToPrevious();
                    }
                    switchBacground(nextQueueItem.getDescription().getIconUri());
                } else {
                    //未滑动到下一首，不改变
                    PlayerActivity.this.startStylusReturn();
                }
                isBacgroundAutoNext = false;
                PlayerActivity.this.mCurrentDiscLayout = (RotationRelativeLayout) PlayerActivity.this.mViewFlipper.getCurrentView();
                for (int i = 0; i < queueItems.size(); i++) {
                    if (nextQueueItem.getQueueId() == queueItems.get(i).getQueueId()) {
                        PlayerActivity.this.currentDataPosition = i;
                        break;
                    }
                }
                ((RotationRelativeLayout) PlayerActivity.this.mViewFlipper.getNextView()).stopAndRest();
                PlayerActivity.this.mCurrentDiscLayout.prepareAnimation();
            }

            @Override
            public void onDiscSwitchHalf(Boolean bool) {
                Log.w(TAG, "onDiscSwitchHalf-->" + bool);
                if (bool == null) {
                    PlayerActivity.this.setTitle(PlayerActivity.this.mCurrentMetadata.getDescription().getTitle());
                    PlayerActivity.this.setSubTitle(PlayerActivity.this.mCurrentMetadata.getDescription().getSubtitle());
                    //TODO 添加是否收藏
                } else {
                    PlayerActivity.this.setTitle(nextQueueItem.getDescription().getTitle());
                    PlayerActivity.this.setSubTitle(nextQueueItem.getDescription().getSubtitle());
                    //TODO 添加是否收藏
                }
            }
        });

        this.mPlayMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //只支持3中循环模式，列表循环模式，单曲模式，随机播放模式
                //首先判断是否是随机模式
                if (PlayerActivity.this.mControllerCompat.getShuffleMode() == PlaybackStateCompat.SHUFFLE_MODE_ALL) {
                    PlayerActivity.this.mControllerCompat.getTransportControls().setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_NONE);
                    PlayerActivity.this.mControllerCompat.getTransportControls().setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ONE);
                } else if (PlayerActivity.this.mControllerCompat.getRepeatMode() == PlaybackStateCompat.REPEAT_MODE_ONE) {
                    PlayerActivity.this.mControllerCompat.getTransportControls().setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ALL);
                } else {
                    PlayerActivity.this.mControllerCompat.getTransportControls().setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_ALL);
                }
            }
        });

        this.mPlayPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PlayerActivity.this.mControllerCompat.getTransportControls().skipToPrevious();
                //滑动回调，此处可以处理唱针的切换以及当前封面动画的暂停
                PlayerActivity.this.mViewFlipper.switchDisc(false);
            }
        });

        this.mPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayerActivity.this.mCurrentMetadata != null && !TextUtils.isEmpty(PlayerActivity.this.mCurrentMetadata.getDescription().getMediaId())) {
                    if (PlayerActivity.this.mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING ||
                            PlayerActivity.this.mPlaybackState.getState() == PlaybackStateCompat.STATE_BUFFERING ||
                            PlayerActivity.this.mPlaybackState.getState() == PlaybackStateCompat.STATE_CONNECTING) {
                        PlayerActivity.this.mControllerCompat.getTransportControls().pause();
                    } else {
                        PlayerActivity.this.mControllerCompat.getTransportControls().play();
                    }
                }
            }
        });

        this.mPlayNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerActivity.this.mViewFlipper.switchDisc(true);
            }
        });

        this.mPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlayListDialog();
            }
        });
    }

    private void switchBacground(Uri iconUri) {
        GlideApp.with(PlayerActivity.this)
                .load(iconUri)
                .transform(new RenderScriptTransformation())
                .error(R.drawable.bg_playing)
                .into(this.mViewSwitcherTarget);
    }

    @Override
    public void onMediaServiceConnected() {
        //当前指定的音乐资源
        if (this.mCurrentMetadata != null && !TextUtils.isEmpty(this.mCurrentMetadata.getDescription().getMediaId())) {
            this.setTitle(this.mCurrentMetadata.getDescription().getTitle());
            this.setSubTitle(this.mCurrentMetadata.getDescription().getSubtitle());
            currentDataPosition = getCurrentMusicPosition();
            this.mTotalPosition.setText(TimeUtils.getCurrentPosition(mCurrentMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)));
            this.mPlayerSeekBar.setMax(TimeUtils.formateToSeconds(mCurrentMetadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)));
            GlideApp.with(this).load(mCurrentMetadata.getDescription().getIconUri()).transform(new CircleCrop()).centerCrop().transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.ic_disc_playhoder).into((ImageView) this.mCurrentDiscLayout.getChildAt(0));
            //TODO 设置是否收藏
            switchBacground(mCurrentMetadata.getDescription().getIconUri());
        }
        switch (this.mPlaybackState.getState()) {
            case PlaybackStateCompat.STATE_PLAYING:
                if (!this.mCurrentDiscLayout.isRunning()) {
                    this.mCurrentDiscLayout.start();
                }
                if (this.mStylusAnimationType != STYLUS_ON) {
                    this.mStylusReturnAnimation.setDegrees(0);
                    this.startStylusReturn();
                }
                this.mPlayPause.setImageResource(R.drawable.selector_player_pause);
                break;
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_STOPPED:
            case PlaybackStateCompat.STATE_PAUSED:
                this.mStylusReturnAnimation.setDegrees(-25);
                this.startStylusRemove();
                this.mPlayPause.setImageResource(R.drawable.selector_player_play);
                break;
            default:
        }
        checkPlaybackPosition();
    }

    private int getCurrentMusicPosition() {
        //如果不是随机播放，播放列表index和每个item的queueId是一致的
        for (int i = 0; i < queueItems.size(); i++) {
            if (TextUtils.equals(mCurrentMetadata.getDescription().getMediaId(),queueItems.get(i).getDescription().getMediaId())) {
                return i;
            }
        }
        return -1;
    }

    private void checkPlaybackPosition() {
        this.clientHandler.postDelayed(mCheckPlaybackPositionRunnable, 100l);
    }

    /**
     * 当前音乐改变
     *
     * @param metadata
     */
    @Override
    public void onMetadataChanged(MediaMetadataCompat metadata) {
        super.onMetadataChanged(metadata);
        PlayerActivity.this.setTitle(metadata.getDescription().getTitle());
        PlayerActivity.this.setSubTitle(metadata.getDescription().getSubtitle());
        this.mTotalPosition.setText(TimeUtils.getCurrentPosition(metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)));
        this.mPlayerSeekBar.setMax(TimeUtils.formateToSeconds(metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)));
        //判断是否是后台自动切换的
        if (currentDataPosition == -1) {
            GlideApp.with(this).load(mCurrentMetadata.getDescription().getIconUri()).transform(new CircleCrop()).centerCrop().transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.ic_disc_playhoder).into((ImageView) this.mCurrentDiscLayout.getChildAt(0));
            //TODO 设置是否收藏
            switchBacground(mCurrentMetadata.getDescription().getIconUri());
            currentDataPosition = getCurrentMusicPosition();
             return;
        }

        int position = getCurrentMusicPosition();
        if(((currentDataPosition == queueItems.size()-1 && position == 0)||position == currentDataPosition+1)&& !isBacgroundAutoNext ){
            if (!TextUtils.equals(queueItems.get(currentDataPosition).getDescription().getMediaId(), metadata.getDescription().getMediaId()) && !isBacgroundAutoNext) {
                isBacgroundAutoNext = true;
                PlayerActivity.this.mViewFlipper.switchDisc(true);
            }
        }else{
            GlideApp.with(this).load(mCurrentMetadata.getDescription().getIconUri()).transform(new CircleCrop()).centerCrop().transition(DrawableTransitionOptions.withCrossFade()).placeholder(R.drawable.ic_disc_playhoder).into((ImageView) this.mCurrentDiscLayout.getChildAt(0));
            //TODO 设置是否收藏
            switchBacground(mCurrentMetadata.getDescription().getIconUri());
            currentDataPosition = position;
        }
    }

    /**
     * 当前播放状态改变
     *
     * @param state
     */
    @Override
    public void onPlaybackStateChanged(PlaybackStateCompat state) {
        super.onPlaybackStateChanged(state);
        switch (state.getState()) {
            case PlaybackStateCompat.STATE_PLAYING:
            case PlaybackStateCompat.STATE_BUFFERING:
            case PlaybackStateCompat.STATE_CONNECTING:
                this.startStylusReturn();
                if (!this.mCurrentDiscLayout.isRunning()) {
                    this.mCurrentDiscLayout.start();
                }
                this.mPlayPause.setImageResource(R.drawable.selector_player_pause);
                break;
            case PlaybackStateCompat.STATE_STOPPED:
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_PAUSED:
            case PlaybackStateCompat.STATE_ERROR:
                this.startStylusRemove();
                this.mPlayPause.setImageResource(R.drawable.selector_player_play);
                break;
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
        if (repeatMode == PlaybackStateCompat.SHUFFLE_MODE_ALL) {
            this.mPlayMode.setImageResource(R.drawable.selector_player_mode_shuffer);
        } else if (repeatMode == PlaybackStateCompat.REPEAT_MODE_ONE) {
            this.mPlayMode.setImageResource(R.drawable.selector_player_mode_single);
        } else if (repeatMode == PlaybackStateCompat.REPEAT_MODE_ALL) {
            this.mPlayMode.setImageResource(R.drawable.selector_player_mode_list_circulation);
        }
    }

    @Override
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
        super.onQueueChanged(queue);
        currentDataPosition = getCurrentMusicPosition();
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
        return (CommonUtils.versionAbove19() ? UiUtils.getStatusBarHeight(this) : 0) + UiUtils.getToolbarHeight();
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

    @Override
    protected void onDestroy() {
        updatePosition = false;
        super.onDestroy();
    }
}
