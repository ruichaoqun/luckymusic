package com.ruichaoqun.luckymusic.data.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class ArtistBean {
    @Id
    private Long _ID;                         //数据库主键
    private long id;                       //歌手id
    private String artistName;              //歌手名称
    private int tracksNumber;           //歌曲数量
    private int albumsNumber;           //专辑数量

    public static ArtistBean fromCursor(Cursor cursor) {
        ArtistBean artistBean = new ArtistBean();
        artistBean.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        artistBean.artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        artistBean.tracksNumber = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS));
        artistBean.albumsNumber = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.ArtistColumns.NUMBER_OF_ALBUMS));
        return artistBean;
    }
}
