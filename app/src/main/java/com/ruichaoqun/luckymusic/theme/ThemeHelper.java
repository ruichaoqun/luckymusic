package com.ruichaoqun.luckymusic.theme;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

public class ThemeHelper {
    public static int getColor700from500(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        fArr[2] = (i == -1 ? 0.8f : 0.85f) * fArr[2];
        return Color.HSVToColor(fArr);
    }

    public static Drawable configDrawableTheme(Drawable drawable, int i) {
        return configDrawableThemeUseTint(drawable, i);
    }

    public static Drawable configDrawableThemeUseTint(Drawable drawable, int i) {
        return configDrawableThemeUseTintList(drawable, ColorStateList.valueOf(i));
    }


    public static Drawable configDrawableThemeUseTintList(Drawable drawable, ColorStateList colorStateList) {
        if (drawable == null) {
            return null;
        }
        Drawable unwrap = DrawableCompat.unwrap(drawable);
        Drawable.Callback callback = unwrap.getCallback();
        Drawable wrap = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(wrap, colorStateList);
        if (callback == null || unwrap.getCallback() == callback) {
            return wrap;
        }
        unwrap.setCallback(callback);
        callback.invalidateDrawable(unwrap);
        return wrap;
    }
}
