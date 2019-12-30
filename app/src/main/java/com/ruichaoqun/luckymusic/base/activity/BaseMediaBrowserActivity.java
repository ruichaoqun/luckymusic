package com.ruichaoqun.luckymusic.base.activity;

import android.content.ComponentName;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.media.MusicService;
import com.ruichaoqun.luckymusic.utils.LogUtils;
import com.ruichaoqun.luckymusic.widget.PlayPauseView;

import java.util.List;

public abstract class BaseMediaBrowserActivity extends BaseToolBarActivity {
    protected MediaBrowserCompat mBrowserCompat;
    protected MediaControllerCompat mControllerCompat;



    private MediaBrowserCompat.SubscriptionCallback mSubscriptionCallback = new MediaBrowserCompat.SubscriptionCallback() {
        @Override
        public void onChildrenLoaded(@NonNull String parentId, @NonNull List<MediaBrowserCompat.MediaItem> children) {
            LogUtils.w(TAG,parentId);
            LogUtils.w(TAG,children.size()+"");
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isNeedMediaBrowser()){
            mBrowserCompat = new MediaBrowserCompat(getApplicationContext(),new ComponentName(this, MusicService.class),new ConnectionCallback(),null);
            mBrowserCompat.connect();
        }
    }



    public boolean isNeedMediaBrowser(){
        return false;
    }

    public void onPlaybackStateChanged(PlaybackStateCompat state) {

    }

    public void onMetadataChanged(MediaMetadataCompat metadata) {

    }

    public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {

    }

    public void onSessionEvent(String event, Bundle extras) {

    }

    public void onSessionDestroyed() {

    }

    class ConnectionCallback extends MediaBrowserCompat.ConnectionCallback{
        @Override
        public void onConnected() {
            try {
                mControllerCompat = new MediaControllerCompat(getApplicationContext(),mBrowserCompat.getSessionToken());
                mControllerCompat.registerCallback(new MediaControllerCallback());
                MediaControllerCompat.setMediaController(BaseMediaBrowserActivity.this,mControllerCompat);
                LogUtils.w(TAG,"连接成功");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConnectionFailed() {
            super.onConnectionFailed();
        }

        @Override
        public void onConnectionSuspended() {
            super.onConnectionSuspended();
        }
    }

    private class MediaControllerCallback extends MediaControllerCompat.Callback{
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            BaseMediaBrowserActivity.this.onPlaybackStateChanged(state);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            BaseMediaBrowserActivity.this.onMetadataChanged(metadata);
        }

        @Override
        public void onQueueChanged(List<MediaSessionCompat.QueueItem> queue) {
            BaseMediaBrowserActivity.this.onQueueChanged(queue);
        }

        @Override
        public void onSessionEvent(String event, Bundle extras) {
            BaseMediaBrowserActivity.this.onSessionEvent(event,extras);
        }

        @Override
        public void onSessionDestroyed() {
            BaseMediaBrowserActivity.this.onSessionDestroyed();
        }
    }
}
