package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.ruichaoqun.luckymusic.utils.UiUtils;


/**
 * @author Rui Chaoqun
 * @date :2020/3/31 19:07
 * description:动感音阶动效
 */
public class DynamicScaleEffectView extends ParticleCommonEffectView {
    private static final int width = UiUtils.dp2px(3.0f);

    public DynamicScaleEffectView(Context context) {
        super(context);
        this.mPointsWidth = width;
        this.l.setStrokeWidth(width);
        this.l.setStrokeCap(Paint.Cap.ROUND);
        this.m.setStrokeWidth(this.mPointsWidth);

    }

    @Override
    public void a(Canvas canvas) {
        if (this.f11640g) {
            this.l.setColor(this.mPointColor);
            this.f11640g = false;
        }
        canvas.drawLines(this.pts, this.l);
    }

    @Override
    public int getStartRadius() {
        return this.mPointCircleRadius;
    }

    @Override
    public void reset(boolean close) {

    }

    @Override
    public void prepareToDynamic() {

    }
}
