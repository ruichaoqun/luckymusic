package com.ruichaoqun.luckymusic.data.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 专辑实体类
 */
@Entity
public class AlbumBean {
    @Id
    private Long id;
    private int numsongs;
    private String album;
    private String artist;



    @Generated(hash = 297626165)
    public AlbumBean(Long id, int numsongs, String album, String artist) {
        this.id = id;
        this.numsongs = numsongs;
        this.album = album;
        this.artist = artist;
    }


    @Generated(hash = 2061143688)
    public AlbumBean() {
    }



    public static AlbumBean fromCursor(Cursor cursor) {
        AlbumBean albumBean = new AlbumBean();
        albumBean.id = Long.valueOf(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ID)));
        albumBean.album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM));
        albumBean.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ARTIST));
        albumBean.numsongs = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS));
        return albumBean;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public int getNumsongs() {
        return this.numsongs;
    }


    public void setNumsongs(int numsongs) {
        this.numsongs = numsongs;
    }


    public String getAlbum() {
        return this.album;
    }


    public void setAlbum(String album) {
        this.album = album;
    }


    public String getArtist() {
        return this.artist;
    }


    public void setArtist(String artist) {
        this.artist = artist;
    }
}
