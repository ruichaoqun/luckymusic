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

    public static int b(int i2, float[] outHsl) {
        return a(i2, 50.0f, outHsl);
    }

    public  static int a(int i2, float f2, float[] outHsl) {
        int alpha = Color.alpha(i2);
        ColorUtils.colorToHSL(i2, outHsl);
        outHsl[0] = (outHsl[0] + f2) % 360.0f;
        return ColorUtils.setAlphaComponent(ColorUtils.HSLToColor(outHsl), alpha);
    }



}
