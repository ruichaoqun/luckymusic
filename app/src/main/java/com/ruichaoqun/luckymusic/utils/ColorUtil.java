package com.ruichaoqun.luckymusic.utils;

import android.graphics.Color;

import androidx.core.graphics.ColorUtils;

public class ColorUtil {
    public static int getEffectColor(int color, float[] outHsl) {
        int alpha = Color.alpha(color);
        //将argb颜色转换成颜色、饱和度、亮度三个数据
        ColorUtils.colorToHSL(color, outHsl);
        //修改图片亮度
        outHsl[2] = 0.7f;
        return ColorUtils.setAlphaComponent(ColorUtils.HSLToColor(outHsl), alpha);
    }
}
