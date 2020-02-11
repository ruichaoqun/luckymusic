package com.ruichaoqun.luckymusic.media;

import android.content.ContentUris;
import android.support.v4.media.MediaDescriptionCompat;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ext.mediasession.TimelineQueueEditor;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.ruichaoqun.luckymusic.data.bean.MediaID;

import static android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
import static android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID;

public class DefaultMediaSourceFactory implements TimelineQueueEditor.MediaSourceFactory {
    DataSource.Factory dataSourceFactory;

    public DefaultMediaSourceFactory(DataSource.Factory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    @Nullable
    @Override
    public MediaSource createMediaSource(MediaDescriptionCompat description) {
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .setTag(description)
                .createMediaSource(ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, Long.valueOf(description.getMediaId())));
    }
}
