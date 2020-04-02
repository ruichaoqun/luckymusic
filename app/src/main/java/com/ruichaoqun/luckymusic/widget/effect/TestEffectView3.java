package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.utils.UiUtils;

public class TestEffectView3 extends Test2EffectView {
    private static final int q = UiUtils.dp2px(2.0f);
    private static final int r = UiUtils.dp2px(1.0f);
    private int s = -1;

    public TestEffectView3(Context context) {
        super(context);
        this.f11635b = UiUtils.dp2px(4.0f);
        this.f11636c = true;
        this.l.setStrokeWidth((float) q);
        this.m.setStrokeWidth((float) this.f11635b);
        this.n = new Paint(1);
        this.n.setStyle(Paint.Style.STROKE);
        this.n.setStrokeWidth((float) r);
        this.n.setColor(this.f11639f);

    }

    @Override
    public void a(Canvas canvas) {
        if (this.f11640g) {
            float f2 = (float) (this.f11641h + this.f11642i);
            this.l.setShader(new RadialGradient(0.0f, 0.0f, f2, new int[]{0, ColorUtils.setAlphaComponent(this.f11639f, 0), ColorUtils.setAlphaComponent(this.f11639f, 102)}, new float[]{0.0f, ((float) getStartRadius()) / f2, 1.0f}, Shader.TileMode.CLAMP));
            this.f11640g = false;
        }
        canvas.drawLines(this.k, this.l);

    }

    @Override
    public int getStartRadius() {
        if (this.s == -1) {
            this.s = Math.max(this.j - f11634a, 0);
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
