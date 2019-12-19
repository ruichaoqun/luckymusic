package com.ruichaoqun.luckymusic.data.bean;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef(value = {HomePageItemType.BANNER,
        HomePageItemType.DATA})
@Retention(RetentionPolicy.SOURCE)
public @interface HomePageItemType {
    int BANNER = 1;

    int DATA = 2;
}
