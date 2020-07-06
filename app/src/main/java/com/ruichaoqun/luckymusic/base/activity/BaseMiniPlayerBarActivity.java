package com.ruichaoqun.luckymusic.base.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.Toolbar;
import androidx.transition.Fade;

import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.common.GlideApp;
import com.ruichaoqun.luckymusic.common.WatchMiniPlayBarListener;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.ui.CustomThemeIconImageView;
import com.ruichaoqun.luckymusic.theme.ui.CustomThemeTextView;
import com.ruichaoqun.luckymusic.ui.PlayerActivity;
import com.ruichaoqun.luckymusic.widget.PlayPauseView;

import java.util.List;

import static com.ruichaoqun.luckymusic.media.MusicService.METADATA_KEY_LUCKY_FLAGS;

/**
 * @author Rui Chaoqun
 * @date :2019/12/30 19:43
 * description:
 */
public abstract class BaseMiniPlayerBarActivity extends BaseMediaBrowserActivity  {
    private static final long POSITION_UPDATE_INTERVAL_MILLIS = 100L;

    private ViewGroup mMusicContainer;
    protected RelativeLayout mPlayBarContainer;
    private ImageView mPlayBarCover;
    private CustomThemeTextView mPlayBarTitle;
    private CustomThemeTextView mPlayBarArtist;
    private ImageView mPlayBarList;
    private PlayPauseView mPlayPauseView;
    private boolean updatePosition = false;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void setContentView(int layoutResID) {
        if (isNeedMediaBrowser() && isNeedMiniPlayerBar()) {
            if (needToolBar()) {
                super.setContentView(layoutResID);
            } else {
                super.setContentView(getLayoutInflater().inflate(R.layout.layout_play_bar, null));
                addChildContentView(layoutResID, 0);
            }
            findViews();
        } else {
            super.setContentView(layoutResID);
        }
    }

    @Override
    public void doSetContentViewWithToolBar(int i) {
        doSetContentViewWithToolBar(getLayoutInflater().inflate(i, (ViewGroup) null));
    }

    @Override
    public void doSetContentViewWithToolBar(View view) {
        if (isNeedMediaBrowser() && isNeedMiniPlayerBar()) {
            super.setContentView(getLayoutInflater().inflate(R.layout.activity_mini_playerbar, (ViewGroup) null));
            this.toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            if (this.toolbar == null) {
                ((ViewStub) findViewById(R.id.view_stub)).inflate();
            }
            initToolBar();
            addChildContentView(view, 1);
        } else {
            super.doSetContentViewWithToolBar(view);
        }
    }

    private void findViews() {
        this.mPlayBarContainer = findViewById(R.id.play_bar_parent);
        this.mPlayBarContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerActivity.launchFrom(BaseMiniPlayerBarActivity.this);
            }
        });
        this.mPlayBarCover = findViewById(R.id.cover);
        this.mPlayBarTitle = findViewById(R.id.title);
        this.mPlayBarArtist = findViewById(R.id.artist);
        this.mPlayBarList = findViewById(R.id.play_list);
        this.mPlayPauseView = findViewById(R.id.playPause);
        this.mPlayPauseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mControllerCompat.getPlaybackState().getState()) {
                    case PlaybackStateCompat.STATE_PAUSED:
                    case PlaybackStateCompat.STATE_STOPPED:
                        mControllerCompat.getTransportControls().play();
                        break;
                    case PlaybackStateCompat.STATE_PLAYING:
                    case PlaybackStateCompat.STATE_BUFFERING:
                        mControllerCompat.getTransportControls().pause();
                        break;
                    default:
                }
            }
        });
        this.mPlayBarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPlayListDialog();
            }
        });
        applyMiniPlaybarCurrentTheme();
    }

    @SuppressLint("RestrictedApi")
    public void applyMiniPlaybarCurrentTheme() {
        mPlayBarContainer.setBackgroundDrawable(ResourceRouter.getInstance().getCachePlayerDrawable());
        mPlayPauseView.setColor(ResourceRouter.getInstance().getThemeColor());
        mPlayBarTitle.onThemeReset();
        mPlayBarArtist.onThemeReset();
        mPlayBarList.setImageDrawable(AppCompatDrawableManager.get().getDrawable(this, R.drawable.ic_playlist));
        ThemeHelper.configDrawableThemeUseTint(mPlayBarList.getDrawable(), ResourceRouter.getInstance().isNightTheme()? 0x99FFFFFF:0xCC000000);
    }

    private void addChildContentView(int layoutResID, int index) {
        addChildContentView(getLayoutInflater().inflate(layoutResID, null), index);
    }

    private void addChildContentView(View view, int index) {
        this.mMusicContainer = findViewById(R.id.layout_container);
        this.mMusicContainer.addView(view, index, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onMediaServiceConnected() {
        super.onMediaServiceConnected();
        if(isNeedMiniPlayerBar()){
            showMiniPlayerBarStub(mControllerCompat.getQueue()!= null  && mControllerCompat.getQueue().size() > 0);
            setCurrentMedia(mCurrentMetadata);
        }
    }

    private void setCurrentMedia(MediaMetadataCompat metadata) {
        if (metadata != null && !TextUtils.isEmpty(metadata.getDescription().getMediaId()) && isNeedMiniPlayerBar()) {
            this.mPlayBarTitle.setText(metadata.getDescription().getTitle());
            this.mPlayBarArtist.setText(metadata.getDescription().getSubtitle());
            GlideApp.with(this)
                    .load(Uri.parse(metadata.getString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI)))
                    .centerCrop()
                    .transform(new CircleCrop())
                    .into(this.mPlayBarCover);
            this.mPlayPauseView.setState(PlayPauseView.PLAY_STATE_PAUSE);
            this.mPlayPauseView.setMax(metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION));
        }
    }

    /**
     * 是否需要展示底部音乐播放栏
     *
     * @return
     */
    public boolean isNeedMiniPlayerBar() {
        return true;
    }

    /**
     * 显示或隐藏底部音乐栏
     *
     * @param show
     */
    protected void showMiniPlayerBarStub(boolean show) {
        if (isNeedMiniPlayerBar()) {
            mPlayBarContainer.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 当前资源改变
     *
     * @param metadata
     */
    @Override
    public void onMetadataChanged(MediaMetadataCompat metadata) {
        super.onMetadataChanged(metadata);
        setCurrentMedia(metadata);

    }

    /**
     * 当前播放状态改变
     *
     * @param state
     */
    @Override
    public void onPlaybackStateChanged(PlaybackStateCompat state) {
        super.onPlaybackStateChanged(state);
        if (isNeedMiniPlayerBar()) {
            switch (state.getState()) {
                case PlaybackStateCompat.STATE_PAUSED:
                case PlaybackStateCompat.STATE_BUFFERING:
                    this.mPlayPauseView.setState(PlayPauseView.PLAY_STATE_PAUSE);
                    this.mPlayPauseView.setProgress(state.getPosition());
                    updatePosition = false;
                    break;
                case PlaybackStateCompat.STATE_PLAYING:
                    this.mPlayPauseView.setState(PlayPauseView.PLAY_STATE_PLAYING);
                    updatePosition = true;
                    this.mPlayPauseView.setProgress(state.getPosition());
                    checkPlaybackPosition();
                    break;
                case PlaybackStateCompat.STATE_STOPPED:
                    this.mPlayPauseView.setState(PlayPauseView.PLAY_STATE_PAUSE);
                    this.mPlayPauseView.setProgress(0);
                    updatePosition = false;
                    break;
                default:
            }
        }
    }

    @Override
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
        super.onQueueChanged(queue);
        showMiniPlayerBarStub(queue.size() > 0);
    }

    private void checkPlaybackPosition() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (updatePosition) {
                    long position = (long) (mControllerCompat.getPlaybackState().getPosition() + mControllerCompat.getPlaybackState().getPlaybackSpeed() * (SystemClock.elapsedRealtime() - mControllerCompat.getPlaybackState().getLastPositionUpdateTime()));
                    BaseMiniPlayerBarActivity.this.mPlayPauseView.setProgress(position);
                    checkPlaybackPosition();
                }
            }
        }, POSITION_UPDATE_INTERVAL_MILLIS);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(0);
        super.onDestroy();
    }
}
