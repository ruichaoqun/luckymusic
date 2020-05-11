package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.utils.UiUtils;

/**
 * 爆炸粒子动效
 */
public class ExplosiveParticleEffectView extends ParticleCommonEffectView {
    private static final int q = UiUtils.dp2px(2.0f);
    private static final int r = UiUtils.dp2px(1.0f);
    private int s = -1;

    public ExplosiveParticleEffectView(Context context) {
        super(context);
        this.mPointsWidth = UiUtils.dp2px(4.0f);
        this.isShowCircleLine = true;
        this.l.setStrokeWidth((float) q);
        this.m.setStrokeWidth((float) this.mPointsWidth);
        this.n = new Paint(1);
        this.n.setStyle(Paint.Style.STROKE);
        this.n.setStrokeWidth((float) r);
        this.n.setColor(this.mPointColor);

    }

    @Override
    public void a(Canvas canvas) {
        if (this.f11640g) {
            float f2 = (float) (this.mPointCircleRadius + this.mLineMaxWidth);
            this.l.setShader(new RadialGradient(0.0f, 0.0f, f2, new int[]{0, ColorUtils.setAlphaComponent(this.mPointColor, 0), ColorUtils.setAlphaComponent(this.mPointColor, 102)}, new float[]{0.0f, ((float) getStartRadius()) / f2, 1.0f}, Shader.TileMode.CLAMP));
            this.f11640g = false;
        }
        canvas.drawLines(this.pts, this.l);
    }

    @Override
    public int getStartRadius() {
        if (this.s == -1) {
            this.s = Math.max(this.mOriginalRadius - f11634a, 0);
        }
        return this.s;

    }

    @Override
    public void reset(boolean close) {

    }

    @Override
    public void prepareToDynamic() {

    }
}
