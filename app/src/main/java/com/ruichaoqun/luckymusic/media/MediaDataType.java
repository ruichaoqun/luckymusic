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

    String TYPE_SONG = "0";

    String TYPE_SEARCH = "1";
}
