package com.ruichaoqun.luckymusic.base.activity;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.common.WatchMiniPlayBarListener;
import com.ruichaoqun.luckymusic.media.MediaBrowserProvider;
import com.ruichaoqun.luckymusic.media.MediaControllerInterface;
import com.ruichaoqun.luckymusic.media.MusicService;
import com.ruichaoqun.luckymusic.utils.LogUtils;
import com.ruichaoqun.luckymusic.widget.BottomSheetDialog.PlaylistBottomSheet;
import com.ruichaoqun.luckymusic.widget.PlayPauseView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMediaBrowserActivity extends BaseToolBarActivity implements MediaBrowserProvider {
    protected MediaBrowserCompat mBrowserCompat;
    protected MediaControllerCompat mControllerCompat;
    private MediaControllerCallback mMediaControllerCallback;

    protected List<MediaSessionCompat.QueueItem> queueItems;
    protected MediaMetadataCompat mCurrentMetadata;
    protected PlaybackStateCompat mPlaybackState = new PlaybackStateCompat.Builder()
            .setState(PlaybackStateCompat.STATE_NONE, 0, 0f)
            .build();

    private PlaylistBottomSheet playListDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNeedMediaBrowser()) {
            mBrowserCompat = new MediaBrowserCompat(this, new ComponentName(this, MusicService.class), new ConnectionCallback(), null);
            mBrowserCompat.connect();
        }
    }

    public boolean isNeedMediaBrowser() {
        return true;
    }

    public void onPlaybackStateChanged(PlaybackStateCompat state) {
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> fragments = manager.getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) instanceof MediaControllerInterface) {
                ((MediaControllerInterface) fragments.get(i)).onPlaybackStateChanged(state);
            }
        }
    }

    public void onMetadataChanged(MediaMetadataCompat metadata) {
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> fragments = manager.getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) instanceof MediaControllerInterface) {
                ((MediaControllerInterface) fragments.get(i)).onMetadataChanged(metadata);
            }
        }
    }


    public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
    }

    public void onRepeatModeChanged(@PlaybackStateCompat.RepeatMode int repeatMode) {
    }

    public void onShuffleModeChanged(@PlaybackStateCompat.ShuffleMode int shuffleMode) {
    }

    public void onMediaServiceConnected() {

    }

    public void onMediaServiceFailed() {

    }

    public void onMediaServiceSuspended() {

    }

    @Override
    public MediaBrowserCompat getMediaBrowser() {
        return mBrowserCompat;
    }

    public void showPlayListDialog(){
        this.playListDialog = PlaylistBottomSheet.showMusicPlayList(this, null);
    }

    @Override
    protected void onDestroy() {
        if (mBrowserCompat != null && mBrowserCompat.isConnected()) {
            mControllerCompat.unregisterCallback(mMediaControllerCallback);
            mBrowserCompat.disconnect();
        }
        super.onDestroy();
    }

    class ConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
        @Override
        public void onConnected() {
            try {
                mControllerCompat = new MediaControllerCompat(getApplicationContext(), mBrowserCompat.getSessionToken());
                mMediaControllerCallback = new MediaControllerCallback();
                mControllerCompat.registerCallback(mMediaControllerCallback);
                MediaControllerCompat.setMediaController(BaseMediaBrowserActivity.this, mControllerCompat);
                BaseMediaBrowserActivity.this.queueItems = mControllerCompat.getQueue();
                BaseMediaBrowserActivity.this.mCurrentMetadata = mControllerCompat.getMetadata();
                BaseMediaBrowserActivity.this.mPlaybackState = mControllerCompat.getPlaybackState();
                BaseMediaBrowserActivity.this.onMediaServiceConnected();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConnectionFailed() {
            BaseMediaBrowserActivity.this.onMediaServiceFailed();
        }

        @Override
        public void onConnectionSuspended() {
            BaseMediaBrowserActivity.this.onMediaServiceSuspended();
        }
    }

    private class MediaControllerCallback extends MediaControllerCompat.Callback {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            BaseMediaBrowserActivity.this.mPlaybackState = state;
            BaseMediaBrowserActivity.this.onPlaybackStateChanged(state);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            BaseMediaBrowserActivity.this.mCurrentMetadata = metadata;
            BaseMediaBrowserActivity.this.onMetadataChanged(metadata);
        }

        @Override
        public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
            BaseMediaBrowserActivity.this.queueItems = queue;
            BaseMediaBrowserActivity.this.onQueueChanged(queue);
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            BaseMediaBrowserActivity.this.onRepeatModeChanged(repeatMode);
        }

        @Override
        public void onShuffleModeChanged(int shuffleMode) {
            BaseMediaBrowserActivity.this.onShuffleModeChanged(shuffleMode);
        }
    }
}
