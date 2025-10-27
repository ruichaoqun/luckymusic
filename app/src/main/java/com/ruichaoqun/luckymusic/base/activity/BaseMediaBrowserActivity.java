package com.ruichaoqun.luckymusic.base.activity;

import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ALBUM;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ARTIST;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_TITLE;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.ruichaoqun.luckymusic.media.MediaBrowserProvider;
import com.ruichaoqun.luckymusic.media.MediaControllerInterface;
import com.ruichaoqun.luckymusic.media.MusicService;
import com.ruichaoqun.luckymusic.utils.LogUtils;
import com.ruichaoqun.luckymusic.widget.BottomSheetDialog.PlaylistBottomSheet;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMediaBrowserActivity extends BaseToolBarActivity implements MediaBrowserProvider {
    public static final String TAG = "BaseMediaBrowser";
    protected MediaBrowserCompat mBrowserCompat;
    protected MediaControllerCompat mControllerCompat;

    private MediaControllerCallback mMediaControllerCallback;

    protected List<MediaSessionCompat.QueueItem> queueItems = new ArrayList<>();
    protected MediaMetadataCompat mCurrentMetadata = new MediaMetadataCompat.Builder().build();
    protected PlaybackStateCompat mPlaybackState = new PlaybackStateCompat.Builder()
            .setState(PlaybackStateCompat.STATE_NONE, 0, 0f)
            .build();

    private PlaylistBottomSheet playListDialog;
    protected int playMode = PlaybackStateCompat.REPEAT_MODE_ALL;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("BaseMediaBrowserActivity", "onCreate");
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
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> fragments = manager.getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) instanceof MediaControllerInterface) {
                ((MediaControllerInterface) fragments.get(i)).onQueueChanged(queue);
            }
        }
    }

    public void onRepeatModeChanged(@PlaybackStateCompat.RepeatMode int repeatMode) {
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
        playListDialog = PlaylistBottomSheet.showMusicPlayList(this, queueItems,mCurrentMetadata,playMode);
    }

    /**
     * 删除指定音乐
     * @param position 在播放列表中的index
     */
    public void deletePlayItem(int position){
        mControllerCompat.removeQueueItem(queueItems.get(position).getDescription());
    }

    /**
     * 移除整个播放列表
     */
    public void deleteAllPlaylist(){
//        mControllerCompat.getTransportControls().sendCustomAction(MediaCommonConstant.RELEASE_PLAYER,new Bundle());
        mControllerCompat.getTransportControls().stop();
    }

    /**
     * 播放列表中的指定音乐
     * @param position 指定的音乐在播放列表中的位置
     */
    public void playFromQueueIndex(int position){
        mControllerCompat.getTransportControls().skipToQueueItem(position);
    }

    /**
     * 切换播放模式
     */
    public void switchPlayMode(){
        //只支持3中循环模式，列表循环模式，单曲模式，随机播放模式
        //首先判断是否是随机模式
        if (mControllerCompat.getShuffleMode() == 3) {
            mControllerCompat.getTransportControls().setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_NONE);
            mControllerCompat.getTransportControls().setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ONE);
        } else if (mControllerCompat.getRepeatMode() == PlaybackStateCompat.REPEAT_MODE_ONE) {
            mControllerCompat.getTransportControls().setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ALL);
        } else {
            mControllerCompat.getTransportControls().setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_ALL);
        }
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
            BaseMediaBrowserActivity.this.mControllerCompat = new MediaControllerCompat(getApplicationContext(), mBrowserCompat.getSessionToken());
            BaseMediaBrowserActivity.this.mMediaControllerCallback = new MediaControllerCallback();
            BaseMediaBrowserActivity.this.mControllerCompat.registerCallback(mMediaControllerCallback);
            MediaControllerCompat.setMediaController(BaseMediaBrowserActivity.this, mControllerCompat);
            if (mControllerCompat.getQueue() != null) {
                BaseMediaBrowserActivity.this.queueItems = mControllerCompat.getQueue();
            }
            if (mControllerCompat.getMetadata() != null) {
                BaseMediaBrowserActivity.this.mCurrentMetadata = mControllerCompat.getMetadata();
            }
            if (mControllerCompat.getPlaybackState() != null) {
                BaseMediaBrowserActivity.this.mPlaybackState = mControllerCompat.getPlaybackState();
            }
            if(mControllerCompat.getShuffleMode() == PlaybackStateCompat.SHUFFLE_MODE_ALL){
                BaseMediaBrowserActivity.this.playMode = PlaybackStateCompat.SHUFFLE_MODE_ALL;
            }else{
                BaseMediaBrowserActivity.this.playMode = mControllerCompat.getRepeatMode();
            }
            BaseMediaBrowserActivity.this.onMediaServiceConnected();
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
            if(!TextUtils.equals(BaseMediaBrowserActivity.this.mCurrentMetadata.getDescription().getMediaId(),metadata.getDescription().getMediaId())){
                if(BaseMediaBrowserActivity.this.playListDialog != null && BaseMediaBrowserActivity.this.playListDialog.isShowing()){
                    BaseMediaBrowserActivity.this.playListDialog.setCurrentMetadata(metadata);
                }
            }
            BaseMediaBrowserActivity.this.mCurrentMetadata = metadata;
            BaseMediaBrowserActivity.this.onMetadataChanged(metadata);
        }

        @Override
        public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
            BaseMediaBrowserActivity.this.queueItems = queue;
            BaseMediaBrowserActivity.this.onQueueChanged(queue);
            if(BaseMediaBrowserActivity.this.playListDialog != null && BaseMediaBrowserActivity.this.playListDialog.isShowing()){
                BaseMediaBrowserActivity.this.playListDialog.setQueueItems(queueItems);
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            BaseMediaBrowserActivity.this.playMode = repeatMode;
            BaseMediaBrowserActivity.this.onRepeatModeChanged(repeatMode);
            if(BaseMediaBrowserActivity.this.playListDialog != null && BaseMediaBrowserActivity.this.playListDialog.isShowing()){
                BaseMediaBrowserActivity.this.playListDialog.setPlayMode(playMode);
            }
        }

        @Override
        public void onShuffleModeChanged(int shuffleMode) {
            if(shuffleMode == PlaybackStateCompat.SHUFFLE_MODE_ALL){
                BaseMediaBrowserActivity.this.playMode = 3;
                BaseMediaBrowserActivity.this.onRepeatModeChanged(playMode);
                if(BaseMediaBrowserActivity.this.playListDialog != null && BaseMediaBrowserActivity.this.playListDialog.isShowing()){
                    BaseMediaBrowserActivity.this.playListDialog.setPlayMode(playMode);
                }
            }
        }
    }
}
