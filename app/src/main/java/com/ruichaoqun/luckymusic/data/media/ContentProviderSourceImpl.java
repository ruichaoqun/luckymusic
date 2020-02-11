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

import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.MediaID;
import com.ruichaoqun.luckymusic.data.bean.SongBean;
import com.ruichaoqun.luckymusic.media.MediaDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


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
public class ContentProviderSourceImpl implements ContentProviderSource {
    private ContentResolver mContentResolver;


    @Inject
    public ContentProviderSourceImpl(Application context) {
        mContentResolver = context.getContentResolver();
    }

    @Override
    public List<SongBean> getAllSongs() {
        return makeSongCursor(null, null);
    }


    @Override
    public List<SongBean> searchSongs(String searchKey) {
        return makeSongCursor("title LIKE ?", new String[]{searchKey});
    }

    @Override
    public List<ArtistBean> getAllArtist() {
        return getAllArtistFromResolver(null,null);
    }

    @Override
    public List<AlbumBean> getAllAlbum() {
        return getAllAlbumFromResolver(null,null);
    }

    private synchronized List<SongBean> makeSongCursor(String selection, String[] paramArrayOfString) {
        StringBuilder selectionBuilder = new StringBuilder("is_music=1 AND title != ''");
        if (!TextUtils.isEmpty(selection)) {
            selectionBuilder.append(" AND ");
            selectionBuilder.append(selection);
        }
        String[] projection = new String[]{_ID, TITLE, ARTIST, ARTIST_ID, ALBUM, ALBUM_ID, DURATION, TRACK, DATA};
        Cursor cursor = mContentResolver.query(EXTERNAL_CONTENT_URI, projection, selectionBuilder.toString(), paramArrayOfString, DEFAULT_SORT_ORDER);
        List<SongBean> songBeans = new ArrayList<>();
        while (cursor.moveToNext()) {
//            MediaMetadataCompat.Builder builder = new MediaMetadataCompat.Builder()
//                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, new MediaID(MediaDataType.TYPE_SONG , cursor.getLong(cursor.getColumnIndex(_ID))).asString())
//                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, cursor.getString(cursor.getColumnIndex(TITLE)))
//                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, cursor.getString(cursor.getColumnIndex(TITLE)))
//                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, cursor.getString(cursor.getColumnIndex(ALBUM)))
//                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, cursor.getString(cursor.getColumnIndex(ALBUM)))
//                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, cursor.getString(cursor.getColumnIndex(ARTIST)))
//                    .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, cursor.getString(cursor.getColumnIndex(ARTIST)))
//                    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, cursor.getLong(cursor.getColumnIndex(DURATION)))
//                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI,ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),cursor.getLong(cursor.getColumnIndex(ALBUM_ID))).toString())
//                    .putLong(METADATA_KEY_LUCKY_FLAGS,  MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);
            songBeans.add(SongBean.fromCursor(cursor));
        }
        cursor.close();
        return songBeans;
    }

    private synchronized List<ArtistBean> getAllArtistFromResolver(String selection, String[] paramArrayOfString){
        String artistSortOrder = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER;
        String[] projection = new String[]{"_id", "artist", "number_of_albums", "number_of_tracks"};
        Cursor cursor = mContentResolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, projection, selection, paramArrayOfString, artistSortOrder);
        List<ArtistBean> artistBeans = new ArrayList<>();
        while (cursor.moveToNext()) {
            artistBeans.add(ArtistBean.fromCursor(cursor));
        }
        cursor.close();
        return artistBeans;
    }


    private synchronized List<AlbumBean> getAllAlbumFromResolver(String selection, String[] paramArrayOfString){
        String artistSortOrder = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER;
        String[] projection = new String[]{"album_id", "artist", "album", "numsongs"};
        Cursor cursor = mContentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, selection, paramArrayOfString, artistSortOrder);
        List<AlbumBean> artistBeans = new ArrayList<>();
        while (cursor.moveToNext()) {
            artistBeans.add(AlbumBean.fromCursor(cursor));
        }
        cursor.close();
        return artistBeans;
    }
}
