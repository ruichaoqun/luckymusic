package com.ruichaoqun.luckymusic.utils;

import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;

public class TimeUtils {

    //时间转换成分：秒的格式
    public static String getCurrentPosition(long position){
        int totalSeconds = (int) Math.floor(position / 1E3);
        int minutes = totalSeconds / 60;
        int remainingSeconds = totalSeconds - (minutes * 60);
        if(position < 0){
            return LuckyMusicApp.getInstance().getString(R.string.duration_unknown);
        }
        return String.format(LuckyMusicApp.getInstance().getString(R.string.duration_format),minutes,remainingSeconds);
    }

    public static String getCurrentPositionFromSeekbar(int totalSeconds){
        int minutes = totalSeconds / 60;
        int remainingSeconds = totalSeconds - (minutes * 60);
        if(totalSeconds < 0){
            return LuckyMusicApp.getInstance().getString(R.string.duration_unknown);
        }
        return String.format(LuckyMusicApp.getInstance().getString(R.string.duration_format),minutes,remainingSeconds);
    }

    public static int formateToSeconds(long position){
        return (int) Math.floor(position / 1E3);
    }
}
