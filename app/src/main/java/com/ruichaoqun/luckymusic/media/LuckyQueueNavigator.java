package com.ruichaoqun.luckymusic.media;

import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator;
import com.google.android.exoplayer2.util.Assertions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class LuckyQueueNavigator implements MediaSessionConnector.QueueNavigator {

    public static final long MAX_POSITION_FOR_SEEK_TO_PREVIOUS = 3000;
    public static final int DEFAULT_MAX_QUEUE_SIZE = Integer.MAX_VALUE;

    private final MediaSessionCompat mediaSession;
    private final Timeline.Window window;
    private final int maxQueueSize;

    private long activeQueueItemId;

    /**
     * Creates an instance for onColorGet given {@link MediaSessionCompat}.
     * <p>
     * Equivalent to {@code TimelineQueueNavigator(mediaSession, DEFAULT_MAX_QUEUE_SIZE)}.
     *
     * @param mediaSession The {@link MediaSessionCompat}.
     */
    public LuckyQueueNavigator(MediaSessionCompat mediaSession) {
        this(mediaSession, DEFAULT_MAX_QUEUE_SIZE);
    }

    /**
     * Creates an instance for onColorGet given {@link MediaSessionCompat} and maximum queue size.
     * <p>
     * If the number of windows in the {@link Player}'s {@link Timeline} exceeds {@code maxQueueSize},
     * the media session queue will correspond to {@code maxQueueSize} windows centered on the one
     * currently being played.
     *
     * @param mediaSession The {@link MediaSessionCompat}.
     * @param maxQueueSize The maximum queue size.
     */
    public LuckyQueueNavigator(MediaSessionCompat mediaSession, int maxQueueSize) {
        Assertions.checkState(maxQueueSize > 0);
        this.mediaSession = mediaSession;
        this.maxQueueSize = maxQueueSize;
        activeQueueItemId = MediaSessionCompat.QueueItem.UNKNOWN_ID;
        window = new Timeline.Window();
    }

    @Override
    public long getSupportedQueueNavigatorActions(Player player) {
        boolean enableSkipTo = false;
        boolean enablePrevious = false;
        boolean enableNext = false;
        Timeline timeline = player.getCurrentTimeline();
        if (!timeline.isEmpty() && !player.isPlayingAd()) {
            timeline.getWindow(player.getCurrentWindowIndex(), window);
            enableSkipTo = timeline.getWindowCount() > 1;
            enablePrevious = window.isSeekable || !window.isDynamic || player.hasPrevious();
            enableNext = window.isDynamic || player.hasNext();
        }

        long actions = 0;
        if (enableSkipTo) {
            actions |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        if (enablePrevious) {
            actions |= PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS;
        }
        if (enableNext) {
            actions |= PlaybackStateCompat.ACTION_SKIP_TO_NEXT;
        }
        return actions;
    }

    @Override
    public final void onTimelineChanged(Player player) {
        publishFloatingQueueWindow(player);
    }

    @Override
    public final void onCurrentWindowIndexChanged(Player player) {
        if (activeQueueItemId == MediaSessionCompat.QueueItem.UNKNOWN_ID
                || player.getCurrentTimeline().getWindowCount() > maxQueueSize) {
            publishFloatingQueueWindow(player);
        } else if (!player.getCurrentTimeline().isEmpty()) {
            activeQueueItemId = player.getCurrentWindowIndex();
        }
    }

    @Override
    public final long getActiveQueueItemId(@Nullable Player player) {
        return activeQueueItemId;
    }

    @Override
    public void onSkipToPrevious(Player player, ControlDispatcher controlDispatcher) {
        Timeline timeline = player.getCurrentTimeline();
        if (timeline.isEmpty() || player.isPlayingAd()) {
            return;
        }
        int windowIndex = player.getCurrentWindowIndex();
        timeline.getWindow(windowIndex, window);
        int previousWindowIndex = player.getPreviousWindowIndex();
        if (previousWindowIndex != C.INDEX_UNSET) {
            controlDispatcher.dispatchSeekTo(player, previousWindowIndex, C.TIME_UNSET);
        } else {
            controlDispatcher.dispatchSeekTo(player, windowIndex, 0);
        }
    }

    @Override
    public void onSkipToQueueItem(Player player, ControlDispatcher controlDispatcher, long id) {
        Timeline timeline = player.getCurrentTimeline();
        if (timeline.isEmpty() || player.isPlayingAd()) {
            return;
        }
        int windowIndex = (int) id;
        if (0 <= windowIndex && windowIndex < timeline.getWindowCount()) {
            controlDispatcher.dispatchSeekTo(player, windowIndex, C.TIME_UNSET);
        }
    }

    @Override
    public void onSkipToNext(Player player, ControlDispatcher controlDispatcher) {
        Timeline timeline = player.getCurrentTimeline();
        if (timeline.isEmpty() || player.isPlayingAd()) {
            return;
        }
        int windowIndex = player.getCurrentWindowIndex();
        int nextWindowIndex = player.getNextWindowIndex();
        if (nextWindowIndex != C.INDEX_UNSET) {
            controlDispatcher.dispatchSeekTo(player, nextWindowIndex, C.TIME_UNSET);
        } else if (timeline.getWindow(windowIndex, window).isDynamic) {
            controlDispatcher.dispatchSeekTo(player, windowIndex, C.TIME_UNSET);
        }
    }

    // CommandReceiver implementation.

    @Override
    public boolean onCommand(
            Player player,
            ControlDispatcher controlDispatcher,
            String command,
            Bundle extras,
            ResultReceiver cb) {
        return false;
    }

    // Helper methods.

    private void publishFloatingQueueWindow(Player player) {
        Timeline timeline = player.getCurrentTimeline();
        if (timeline.isEmpty()) {
            mediaSession.setQueue(Collections.emptyList());
            activeQueueItemId = MediaSessionCompat.QueueItem.UNKNOWN_ID;
            return;
        }
        ArrayDeque<MediaSessionCompat.QueueItem> queue = new ArrayDeque<>();
        int queueSize = Math.min(maxQueueSize, timeline.getWindowCount());

        // Add the active queue item.
        int currentWindowIndex = player.getCurrentWindowIndex();
        queue.add(
                new MediaSessionCompat.QueueItem(
                        getMediaDescription(player, currentWindowIndex), currentWindowIndex));

        // Fill queue alternating with next and/or previous queue items.
        int firstWindowIndex = currentWindowIndex;
        int lastWindowIndex = currentWindowIndex;
        boolean shuffleModeEnabled = player.getShuffleModeEnabled();
        while ((firstWindowIndex != C.INDEX_UNSET || lastWindowIndex != C.INDEX_UNSET)
                && queue.size() < queueSize) {
            // Begin with next to have onColorGet longer tail than head if an even sized queue needs to be trimmed.
            if (lastWindowIndex != C.INDEX_UNSET) {
                lastWindowIndex =
                        timeline.getNextWindowIndex(
                                lastWindowIndex, Player.REPEAT_MODE_OFF, shuffleModeEnabled);
                if (lastWindowIndex != C.INDEX_UNSET) {
                    queue.add(
                            new MediaSessionCompat.QueueItem(
                                    getMediaDescription(player, lastWindowIndex), lastWindowIndex));
                }
            }
            if (firstWindowIndex != C.INDEX_UNSET && queue.size() < queueSize) {
                firstWindowIndex =
                        timeline.getPreviousWindowIndex(
                                firstWindowIndex, Player.REPEAT_MODE_OFF, shuffleModeEnabled);
                if (firstWindowIndex != C.INDEX_UNSET) {
                    queue.addFirst(
                            new MediaSessionCompat.QueueItem(
                                    getMediaDescription(player, firstWindowIndex), firstWindowIndex));
                }
            }
        }
        mediaSession.setQueue(new ArrayList<>(queue));
        activeQueueItemId = currentWindowIndex;
    }

    public MediaDescriptionCompat getMediaDescription(Player player, int windowIndex) {
        return (MediaDescriptionCompat) player.getCurrentTimeline().getWindow(windowIndex, new  Timeline.Window(), true).tag;
    }
}
