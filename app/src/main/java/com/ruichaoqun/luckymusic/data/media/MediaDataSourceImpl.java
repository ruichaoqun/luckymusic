package com.ruichaoqun.luckymusic.data.media;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;

import androidx.collection.ArrayMap;

import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.media.MediaDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


import static android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.Audio.Media._ID;
import static android.provider.MediaStore.Audio.Media.ALBUM_ID;
import static android.provider.MediaStore.Audio.Media.ALBUM;
import static android.provider.MediaStore.Audio.Media.ARTIST;
import static android.provider.MediaStore.Audio.Media.ARTIST_ID;
import static android.provider.MediaStore.Audio.Media.TITLE;
import static android.provider.MediaStore.Audio.Media.DURATION;
import static android.provider.MediaStore.Audio.Media.TRACK;
import static android.provider.MediaStore.Audio.Media.DATA;
import static android.provider.MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
import static com.ruichaoqun.luckymusic.media.MusicService.METADATA_KEY_LUCKY_FLAGS;

@Singleton
public class MediaDataSourceImpl implements MediaDataSource {
    private ContentResolver mContentResolver;
    protected List<MediaMetadataCompat> localSongs;          //本地音乐
    protected List<MediaMetadataCompat> searchSongs;         //搜索音乐
    protected List<MediaMetadataCompat> artists;             //歌手列表
    protected List<MediaMetadataCompat> albumList;           //专辑列表

    private Map<Long, List<MediaMetadataCompat>> songsByArtist;


    @Inject
    public MediaDataSourceImpl(Application context) {
        mContentResolver = context.getContentResolver();
    }

    @Override
    public List<MediaMetadataCompat> getAllSongsData() {
        return localSongs;
    }

    @Override
    public List<MediaMetadataCompat> getSearchSongsData() {
        return searchSongs;
    }

    @Override
    public Observable<List<MediaMetadataCompat>> getAllSongs() {
        return Observable.create(emitter -> {
            List<MediaMetadataCompat> metadataCompatList = makeSongCursor(null, null);
            localSongs = metadataCompatList;
            emitter.onNext(metadataCompatList);
            emitter.onComplete();
        });
    }


    @Override
    public Observable<List<MediaMetadataCompat>> searchSongs(String searchKey) {
        return Observable.create(emitter -> {
            List<MediaMetadataCompat> metadataCompatList = makeSongCursor("title LIKE ?", new String[]{searchKey});
            searchSongs = metadataCompatList;
            emitter.onNext(metadataCompatList);
            emitter.onComplete();
        });
    }

    public synchronized List<MediaMetadataCompat> makeSongCursor(String selection, String[] paramArrayOfString) {
        StringBuilder selectionBuilder = new StringBuilder("is_music=1 AND title != ''");
        if (!TextUtils.isEmpty(selection)) {
            selectionBuilder.append(" AND ");
            selectionBuilder.append(selection);
        }
        String[] projection = new String[]{_ID, TITLE, ARTIST, ARTIST_ID, ALBUM, ALBUM_ID, DURATION, TRACK, DATA};
        Cursor cursor = mContentResolver.query(EXTERNAL_CONTENT_URI, projection, selectionBuilder.toString(), paramArrayOfString, DEFAULT_SORT_ORDER);
        List<MediaMetadataCompat> metadataCompatList = new ArrayList<>();
        while (cursor.moveToNext()) {
//            String path = cursor.getString(cursor.getColumnIndex(DATA));
//            if(!TextUtils.isEmpty(path) && path.endsWith("flac")){
//                continue;
//            }
            MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, new MediaID(MediaDataType.TYPE_SONG , cursor.getLong(cursor.getColumnIndex(_ID))).asString())
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, cursor.getString(cursor.getColumnIndex(TITLE)))
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, cursor.getString(cursor.getColumnIndex(TITLE)))
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, cursor.getString(cursor.getColumnIndex(ALBUM)))
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, cursor.getString(cursor.getColumnIndex(ALBUM)))
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, cursor.getString(cursor.getColumnIndex(ARTIST)))
                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, cursor.getString(cursor.getColumnIndex(ARTIST)))
                    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, cursor.getLong(cursor.getColumnIndex(DURATION)))
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI,ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),cursor.getLong(cursor.getColumnIndex(ALBUM_ID))).toString())
                    .putLong(METADATA_KEY_LUCKY_FLAGS,  MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);
            metadataCompatList.add(builder.build());
        }
        cursor.close();
        return metadataCompatList;
    }
}
