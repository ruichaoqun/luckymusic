package com.ruichaoqun.luckymusic.data.bean;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;

import androidx.annotation.NonNull;

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
    private Long _ID;                           //数据库主键
    private String id ;                        //歌曲ID  _ID
    private long albumId ;                   //歌曲专辑ID  ALBUM_ID
    private long artistId ;                  //歌手ID  ARTIST_ID
    private String title ;                  //歌曲名称  TITLE
    private String artist ;                 //歌手名称
    private String album ;                  //专辑名称
    private long duration ;                  //时长
    private int trackNumber ;                //该歌曲在专辑中的编号
    private String data ;                   //歌曲地址
    @Generated(hash = 1742162036)
    public SongBean(Long _ID, String id, long albumId, long artistId, String title,
            String artist, String album, long duration, int trackNumber,
            String data) {
        this._ID = _ID;
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
    @Generated(hash = 680878972)
    public SongBean() {
    }
    public Long get_ID() {
        return this._ID;
    }
    public void set_ID(Long _ID) {
        this._ID = _ID;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
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

}
