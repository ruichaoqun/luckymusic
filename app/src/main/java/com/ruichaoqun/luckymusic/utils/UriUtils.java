package com.ruichaoqun.luckymusic.utils;

import android.content.ContentUris;
import android.net.Uri;

/**
 * @author Rui Chaoqun
 * @date :2019/12/25 11:36
 * description:
 */
public class UriUtils {

    /**
     * 根据专辑id获取专辑封面图的uri
     * @param albumId
     * @return
     */
    public static Uri getAlbumArtUri(long albumId){
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }
}
