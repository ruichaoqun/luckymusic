package com.ruichaoqun.luckymusic.utils.drawhelper;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.ColorInt;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;

public class ColorDrawableUtils {

    /**
     * 矩形+左上+右上圆角
     * @param argb 颜色
     * @param radii 圆角大小
     * @return drawable
     */
    public static Drawable getTopCornerColorDrawable(@ColorInt int argb, int radii) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[]{(float) radii, (float) radii, (float) radii, (float) radii, 0.0f, 0.0f, 0.0f, 0.0f});
        gradientDrawable.setColor(argb);
        return gradientDrawable;
    }

    public static Drawable getCircleColorDrawable(@ColorInt int argb) {
        GradientDrawable circleDrawable = new GradientDrawable();
        circleDrawable.setShape(GradientDrawable.OVAL);
        circleDrawable.setColor(argb);
        return circleDrawable;
    }

    public static Drawable getRoundCornerColorDrawable(@ColorInt int argb,float radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setColor(argb);
        return gradientDrawable;
    }
}
