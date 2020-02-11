package com.ruichaoqun.luckymusic.data.media;

import android.support.v4.media.MediaMetadataCompat;

import com.ruichaoqun.luckymusic.data.bean.AlbumBean;
import com.ruichaoqun.luckymusic.data.bean.ArtistBean;
import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.List;

import io.reactivex.Observable;

public interface ContentProviderSource {

    List<SongBean> getAllSongs();

    List<SongBean> searchSongs(String searchKey);

    List<ArtistBean> getAllArtist();

    List<AlbumBean> getAllAlbum();
}
