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
        int idIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        if (idIndex != -1) {
            artistBean.id = cursor.getLong(idIndex);
        }
        
        int artistNameIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        if (artistNameIndex != -1) {
            artistBean.artistName = cursor.getString(artistNameIndex);
        }
        
        int tracksNumberIndex = cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS);
        if (tracksNumberIndex != -1) {
            artistBean.tracksNumber = cursor.getInt(tracksNumberIndex);
        }
        
        int albumsNumberIndex = cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS);
        if (albumsNumberIndex != -1) {
            artistBean.albumsNumber = cursor.getInt(albumsNumberIndex);
        }
        
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
