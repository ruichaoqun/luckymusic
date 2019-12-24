package com.ruichaoqun.luckymusic.data.bean;

import android.database.Cursor;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;

import androidx.annotation.NonNull;

import static android.provider.MediaStore.Audio.Media._ID;
import static android.provider.MediaStore.Audio.Media.ALBUM_ID;
import static android.provider.MediaStore.Audio.Media.ALBUM;
import static android.provider.MediaStore.Audio.Media.ARTIST;
import static android.provider.MediaStore.Audio.Media.ARTIST_ID;
import static android.provider.MediaStore.Audio.Media.TITLE;
import static android.provider.MediaStore.Audio.Media.DURATION;
import static android.provider.MediaStore.Audio.Media.TRACK;
import static android.provider.MediaStore.Audio.Media.DATA;

public class SongBean extends MediaBrowserCompat.MediaItem{
    private long id;                        //歌曲ID  _ID
    private long albumId;                   //歌曲专辑ID  ALBUM_ID
    private long artistId;                  //歌手ID  ARTIST_ID
    private String title;                     //歌曲名称  TITLE
    private String artist;                    //歌手名称
    private String album;                     //专辑名称
    private long duration;                  //时长
    private int trackNumber;               //该歌曲在专辑中的编号
    private String data;                    //歌曲本地地址


    public SongBean(long id,long albumId,long artistId,String title,String artist,String album,long duration,int trackNumber,String data) {
        super(new MediaDescriptionCompat.Builder()
                .setMediaId(MediaID("$TYPE_SONG", "$id").asString())
                .setTitle(title)
                .setIconUri(getAlbumArtUri(albumId))
                .setSubtitle(artist)
                .build(), FLAG_PLAYABLE);
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

    public static SongBean createFromCursor(Cursor cursor){
        SongBean songBean = new SongBean();
        songBean.id = cursor.getLong(cursor.getColumnIndex(_ID));
        songBean.albumId = cursor.getLong(cursor.getColumnIndex(ALBUM_ID));
        songBean.album = cursor.getString(cursor.getColumnIndex(ALBUM));
        songBean.artistId = cursor.getLong(cursor.getColumnIndex(ARTIST_ID));
        songBean.artist = cursor.getString(cursor.getColumnIndex(ARTIST));
        songBean.title = cursor.getString(cursor.getColumnIndex(TITLE));
        songBean.duration = cursor.getLong(cursor.getColumnIndex(DURATION));
        songBean.trackNumber = cursor.getInt(cursor.getColumnIndex(TRACK));
        songBean.data = cursor.getString(cursor.getColumnIndex(DATA));
        return songBean;
    }
}
