package com.ruichaoqun.luckymusic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.common.GlideApp;
import com.ruichaoqun.luckymusic.widget.PlayPauseView;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Rui Chaoqun
 * @date :2019/12/26 11:20
 * description:
 */
public class BottomMediaDisplayFragment extends Fragment {
    private static final long PROGRESS_UPDATE_INTERNAL = 1000;
    private static final long PROGRESS_UPDATE_INITIAL_INTERVAL = 100;

    private ImageView cover;
    private TextView title, artist;
    private ImageView playList;
    private ImageLoader imageLoader;
    private PlayPauseView playPauseView;
    private View baseUi;

    private final ScheduledExecutorService mExecutorService =
            Executors.newSingleThreadScheduledExecutor();
    private final Handler mHandler = new Handler();
    private ScheduledFuture<?> mScheduleFuture;
    private PlaybackStateCompat mLastPlaybackState;

    private final Runnable mUpdateProgressTask = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_media_display, container, false);
        cover =  view.findViewById(R.id.cover);
        playPauseView =  view.findViewById(R.id.play);
        playList =  view.findViewById(R.id.play_list);
        title =  view.findViewById(R.id.title);
        artist =  view.findViewById(R.id.artist);
        baseUi = view.findViewById(R.id.base_ui);
        title.setSelected(true);
        imageLoader = new ImageLoader(getActivity());
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MediaControllerCompat controllerCompat = MediaControllerCompat.getMediaController(Objects.requireNonNull(getActivity()));
        if (controllerCompat != null) {
            onConnected();
        }
    }

    public void onConnected() {

        MediaControllerCompat controller = MediaControllerCompat.getMediaController(Objects.requireNonNull(getActivity()));
        if (controller != null) {
            onMetadataChanged(controller.getMetadata());
            onPlaybackStateChanged(controller.getPlaybackState());
            controller.registerCallback(mCallback);
        }
    }

    private void onMetadataChanged(MediaMetadataCompat metadata) {
        if (getActivity() == null || metadata == null) {
            return;
        }
        GlideApp.with(this).load(metadata.getDescription().getMediaUri()).transform(new CircleCrop()).into(cover);
        title.setText(metadata.getDescription().getTitle());
        artist.setText(metadata.getDescription().getDescription());
        long duration = metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
        playPauseView.setMax((int) duration);
    }

    private void onPlaybackStateChanged(PlaybackStateCompat state) {
        if (getActivity() == null) {
            return;
        }
        if (state == null) {
            return;
        }
        mLastPlaybackState = state;
        switch (state.getState()) {
            case PlaybackStateCompat.STATE_PLAYING:
                playPauseView.setState(PlayPauseView.PLAY_STATE_PLAYING);
                scheduleSeekbarUpdate();
                break;
            case PlaybackStateCompat.STATE_BUFFERING:
                playPauseView.setState(PlayPauseView.PLAY_STATE_PLAYING);
                break;
            case PlaybackStateCompat.STATE_PAUSED:
            case PlaybackStateCompat.STATE_STOPPED:
            case PlaybackStateCompat.STATE_NONE:
                playPauseView.setState(PlayPauseView.PLAY_STATE_PAUSE);
                break;
            default:
        }
    }

    @OnClick({R.id.play_list, R.id.play, R.id.card_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play_list:
                break;
            case R.id.play:
                MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
                if (controller == null) {
                    onConnected();
                    return;
                }
                PlaybackStateCompat state = controller.getPlaybackState();
                if (state != null) {
                    MediaControllerCompat.TransportControls controls =
                            controller.getTransportControls();
                    switch (state.getState()) {
                        case PlaybackStateCompat.STATE_PLAYING:
                        case PlaybackStateCompat.STATE_BUFFERING:
                            controls.pause();
                            stopSeekbarUpdate();
                            break;
                        case PlaybackStateCompat.STATE_PAUSED:
                        case PlaybackStateCompat.STATE_STOPPED:
                            controls.play();
                            scheduleSeekbarUpdate();
                            break;
                        default:
                            Log.d("PlayControllFragment", "onClick with state " + state.getState());
                    }
                }
                break;
            case R.id.card_view:
                Intent intent = new Intent(getActivity(), FullPlayActivity.class);
                startActivity(intent);
                break;
                default:
        }
    }

    private MediaControllerCompat.Callback mCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            BottomMediaDisplayFragment.this.onPlaybackStateChanged(state);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            Log.w("AAA", "onMetadataChanged");
            BottomMediaDisplayFragment.this.onMetadataChanged(metadata);
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            super.onRepeatModeChanged(repeatMode);
        }
    };


    private void scheduleSeekbarUpdate() {
        stopSeekbarUpdate();
        if (!mExecutorService.isShutdown()) {
            mScheduleFuture = mExecutorService.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            mHandler.post(mUpdateProgressTask);
                        }
                    }, PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS);
        }
    }

    private void stopSeekbarUpdate() {
        if (mScheduleFuture != null) {
            mScheduleFuture.cancel(false);
        }
    }

    private void updateProgress() {
        if (mLastPlaybackState == null) {
            return;
        }
        long currentPosition = mLastPlaybackState.getPosition();
        if (mLastPlaybackState.getState() != PlaybackStateCompat.STATE_PAUSED) {
            // Calculate the elapsed time between the last position update and now and unless
            // paused, we can assume (delta * speed) + current position is approximately the
            // latest position. This ensure that we do not repeatedly call the getPlaybackState()
            // on MediaControllerCompat.
            long timeDelta = SystemClock.elapsedRealtime() -
                    mLastPlaybackState.getLastPositionUpdateTime();
            currentPosition += (int) timeDelta * mLastPlaybackState.getPlaybackSpeed();
        }
        playPauseView.setProgress((int) currentPosition);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSeekbarUpdate();
        mExecutorService.shutdown();
    }

}
