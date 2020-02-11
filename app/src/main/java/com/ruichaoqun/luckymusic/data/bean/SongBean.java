package com.ruichaoqun.luckymusic.data.bean;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.ruichaoqun.luckymusic.Constants;
import com.ruichaoqun.luckymusic.media.MediaDataType;
import com.ruichaoqun.luckymusic.utils.UriUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import static android.provider.MediaStore.Audio.Media._ID;
import static android.provider.MediaStore.Audio.Media.ALBUM_ID;
import static android.provider.MediaStore.Audio.Media.ALBUM;
import static android.provider.MediaStore.Audio.Media.ARTIST;
import static android.provider.MediaStore.Audio.Media.ARTIST_ID;
import static android.provider.MediaStore.Audio.Media.TITLE;
import static android.provider.MediaStore.Audio.Media.DURATION;
import static android.provider.MediaStore.Audio.Media.TRACK;
import static android.provider.MediaStore.Audio.Media.DATA;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SongBean {
    @Id
    private Long id;                           //数据库主键 歌曲ID
    private long albumId ;                   //歌曲专辑ID  ALBUM_ID
    private long artistId ;                  //歌手ID  ARTIST_ID
    private String title ;                  //歌曲名称  TITLE
    private String artist ;                 //歌手名称
    private String album ;                  //专辑名称
    private long duration ;                  //时长
    private int trackNumber ;                //该歌曲在专辑中的编号
    private String data ;                   //歌曲地址
    private int isLike;              //是否我喜欢 0 不喜欢  1 喜欢
    private String albumArtUri;                 //专辑封面uri

    @Generated(hash = 1777063248)
    public SongBean(Long id, long albumId, long artistId, String title, String artist, String album, long duration, int trackNumber,
            String data, int isLike, String albumArtUri) {
        this.id = id;
        this.albumId = albumId;
        this.artistId = artistId;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.trackNumber = trackNumber;
        this.data = data;
        this.isLike = isLike;
        this.albumArtUri = albumArtUri;
    }

    @Generated(hash = 680878972)
    public SongBean() {
    }

    public static SongBean fromCursor(Cursor cursor) {
        SongBean songBean = new SongBean();
        songBean.id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
        songBean.title = cursor.getString(cursor.getColumnIndex(TITLE));
        songBean.artist = cursor.getString(cursor.getColumnIndex(ARTIST));
        songBean.album = cursor.getString(cursor.getColumnIndex(ALBUM));
        songBean.albumId = cursor.getLong(cursor.getColumnIndex(ALBUM_ID));
        songBean.artistId = cursor.getLong(cursor.getColumnIndex(ARTIST_ID));
        songBean.duration = cursor.getLong(cursor.getColumnIndex(DURATION));
        songBean.data = cursor.getString(cursor.getColumnIndex(DATA));
        songBean.trackNumber = cursor.getInt(cursor.getColumnIndex(TRACK));
        songBean.isLike = 0;
        songBean.albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),songBean.albumId).toString();
        return songBean;
    }

    public MediaDescriptionCompat getDescription() {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.INTENT_EXTRA_LIKE,this.isLike);
        return new MediaDescriptionCompat.Builder()
                .setMediaId(String.valueOf(this.id))
                .setTitle(this.title)
                .setSubtitle(this.artist)
                .setDescription(this.album)
                .setIconUri(TextUtils.isEmpty(this.albumArtUri)?null:Uri.parse(this.albumArtUri))
                .setExtras(bundle)
                .build();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getArtistId() {
        return this.artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getTrackNumber() {
        return this.trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIsLike() {
        return this.isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public String getAlbumArtUri() {
        return this.albumArtUri;
    }

    public void setAlbumArtUri(String albumArtUri) {
        this.albumArtUri = albumArtUri;
    }
}
