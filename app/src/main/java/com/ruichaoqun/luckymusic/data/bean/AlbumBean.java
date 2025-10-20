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
        int idIndex = cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM_ID);
        if (idIndex != -1) {
            albumBean.id = (long) cursor.getInt(idIndex);
        }

        int albumIndex = cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM);
        if (albumIndex != -1) {
            albumBean.album = cursor.getString(albumIndex);
        }

        int artistIndex = cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ARTIST);
        if (artistIndex != -1) {
            albumBean.artist = cursor.getString(artistIndex);
        }

        int numSongsIndex = cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS);
        if (numSongsIndex != -1) {
            albumBean.numsongs = cursor.getInt(numSongsIndex);
        }
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
