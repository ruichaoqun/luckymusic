package com.ruichaoqun.luckymusic.data.bean;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;

import androidx.annotation.NonNull;

import com.ruichaoqun.luckymusic.media.MediaDataType;
import com.ruichaoqun.luckymusic.utils.UriUtils;

import static android.provider.MediaStore.Audio.Media._ID;
import static android.provider.MediaStore.Audio.Media.ALBUM_ID;
import static android.provider.MediaStore.Audio.Media.ALBUM;
import static android.provider.MediaStore.Audio.Media.ARTIST;
import static android.provider.MediaStore.Audio.Media.ARTIST_ID;
import static android.provider.MediaStore.Audio.Media.TITLE;
import static android.provider.MediaStore.Audio.Media.DURATION;
import static android.provider.MediaStore.Audio.Media.TRACK;
import static android.provider.MediaStore.Audio.Media.DATA;

public class SongBean implements Parcelable {
    private long id = 0;                        //歌曲ID  _ID
    private long albumId = 0;                   //歌曲专辑ID  ALBUM_ID
    private long artistId = 0;                  //歌手ID  ARTIST_ID
    private String title = "";                     //歌曲名称  TITLE
    private String artist = "";                    //歌手名称
    private String album = "";                     //专辑名称
    private long duration = 0;                  //时长
    private int trackNumber = 0;               //该歌曲在专辑中的编号
    private String data = "";                    //歌曲本地地址


    public SongBean() {
    }

    public SongBean(long id, long albumId, long artistId, String title, String artist, String album, long duration, int trackNumber, String data) {
        this.id = id;
        this.albumId = albumId;
        this.artistId = artistId;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.trackNumber = trackNumber;
        this.data = data;
    }

    public static SongBean createFromCursor(Cursor cursor){
        SongBean songBean = new SongBean(cursor.getLong(cursor.getColumnIndex(_ID)),
                cursor.getLong(cursor.getColumnIndex(ALBUM_ID)),
                cursor.getLong(cursor.getColumnIndex(ARTIST_ID)),
                cursor.getString(cursor.getColumnIndex(TITLE)),
                cursor.getString(cursor.getColumnIndex(ARTIST)),
                cursor.getString(cursor.getColumnIndex(ALBUM)),
                cursor.getLong(cursor.getColumnIndex(DURATION)),
                cursor.getInt(cursor.getColumnIndex(TRACK)),
                cursor.getString(cursor.getColumnIndex(DATA)));
        return songBean;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.albumId);
        dest.writeLong(this.artistId);
        dest.writeString(this.title);
        dest.writeString(this.artist);
        dest.writeString(this.album);
        dest.writeLong(this.duration);
        dest.writeInt(this.trackNumber);
        dest.writeString(this.data);
    }

    protected SongBean(Parcel in) {
        this.id = in.readLong();
        this.albumId = in.readLong();
        this.artistId = in.readLong();
        this.title = in.readString();
        this.artist = in.readString();
        this.album = in.readString();
        this.duration = in.readLong();
        this.trackNumber = in.readInt();
        this.data = in.readString();
    }

    public static final Parcelable.Creator<SongBean> CREATOR = new Parcelable.Creator<SongBean>() {
        @Override
        public SongBean createFromParcel(Parcel source) {
            return new SongBean(source);
        }

        @Override
        public SongBean[] newArray(int size) {
            return new SongBean[size];
        }
    };
}
