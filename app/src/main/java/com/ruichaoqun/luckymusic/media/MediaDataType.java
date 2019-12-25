package com.ruichaoqun.luckymusic.media;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Rui Chaoqun
 * @date :2019/12/25 14:29
 * description:
 */
@IntDef(value = {MediaDataType.TYPE_SONG})
@Retention(RetentionPolicy.SOURCE)
public @interface MediaDataType {
    public int TYPE_SONG = 0;
}
