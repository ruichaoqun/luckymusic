package com.ruichaoqun.luckymusic.data.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ArtistBean {
    @Id
    private Long id;                       //歌手id
    private String artistName;              //歌手名称
    private int tracksNumber;           //歌曲数量
    private int albumsNumber;           //专辑数量

    @Generated(hash = 334854927)
    public ArtistBean(Long id, String artistName, int tracksNumber, int albumsNumber) {
        this.id = id;
        this.artistName = artistName;
        this.tracksNumber = tracksNumber;
        this.albumsNumber = albumsNumber;
    }

    @Generated(hash = 1235128726)
    public ArtistBean() {
    }

    public static ArtistBean fromCursor(Cursor cursor) {
        ArtistBean artistBean = new ArtistBean();
        artistBean.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        artistBean.artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        artistBean.tracksNumber = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS));
        artistBean.albumsNumber = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS));
        return artistBean;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtistName() {
        return this.artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getTracksNumber() {
        return this.tracksNumber;
    }

    public void setTracksNumber(int tracksNumber) {
        this.tracksNumber = tracksNumber;
    }

    public int getAlbumsNumber() {
        return this.albumsNumber;
    }

    public void setAlbumsNumber(int albumsNumber) {
        this.albumsNumber = albumsNumber;
    }


}
