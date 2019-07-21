package com.ruichaoqun.luckymusic.theme;

import android.graphics.Color;

public class ThemeHelper {
    public static int getColor700from500(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        fArr[2] = (i == -1 ? 0.8f : 0.85f) * fArr[2];
        return Color.HSVToColor(fArr);
    }
}
