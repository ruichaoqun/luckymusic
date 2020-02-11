package com.ruichaoqun.luckymusic.media;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Rui Chaoqun
 * @date :2019/12/25 14:29
 * description:
 */
@StringDef(value = {MediaDataType.TYPE_SONG,
        MediaDataType.TYPE_SEARCH})
@Retention(RetentionPolicy.SOURCE)
public @interface MediaDataType {

    /**
     * 本地音乐
     */
    String TYPE_SONG = "0";

    /**
     * 搜索音乐
     */
    String TYPE_SEARCH = "1";

    /**
     * 通过歌手搜索
     */
    String TYPE_ARTIST = "2";

    /**
     * 搜索专辑搜索
     */
    String TYPE_ALBUM = "3";

    /**
     * 当前播放列表
     */
    String CURRENT_PLAY_LIST = "4";
}
