package com.ruichaoqun.luckymusic.widget.drawable;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.appcompat.widget.AppCompatDrawableManager;

import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.utils.UiUtils;

/**
 * @author Rui Chaoqun
 * @date :2020/6/28 14:51
 * description:
 */
public class CheckedDrawable extends Drawable {
    private int mBorderWidth = UiUtils.dp2px(1.0f);
    private Paint mPaint = new Paint(1);
    @SuppressLint("RestrictedApi")
    private Drawable mTopDrawable = AppCompatDrawableManager.get().getDrawable(LuckyMusicApp.getInstance(), R.drawable.a4p);

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    @Override
    public void setAlpha(int i2) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public CheckedDrawable() {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        Drawable drawable = this.mTopDrawable;
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mTopDrawable.getIntrinsicHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        int[] themeColorBackgroundColorAndIconColor = ResourceRouter.getInstance().getThemeColorBackgroundColorAndIconColor();
        ThemeHelper.configDrawableTheme(this.mTopDrawable, themeColorBackgroundColorAndIconColor[1]);
        this.mPaint.setColor(themeColorBackgroundColorAndIconColor[0]);
        canvas.drawCircle((float) (getIntrinsicWidth() / 2), (float) (getIntrinsicHeight() / 2), (float) ((getIntrinsicWidth() / 2) - this.mBorderWidth), this.mPaint);
        this.mTopDrawable.draw(canvas);
    }

    @Override
    public int getIntrinsicHeight() {
        return this.mTopDrawable.getIntrinsicHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return getIntrinsicHeight();
    }
}

