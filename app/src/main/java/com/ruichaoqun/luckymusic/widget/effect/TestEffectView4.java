package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.ruichaoqun.luckymusic.utils.UiUtils;

public class TestEffectView4 extends Test2EffectView {
    private static final int q = UiUtils.dp2px(3.0f);

    public TestEffectView4(Context context) {
        super(context);
        this.f11635b = q;
        this.l.setStrokeWidth((float) q);
        this.l.setStrokeCap(Paint.Cap.ROUND);
        this.m.setStrokeWidth((float) this.f11635b);

    }

    @Override
    public void a(Canvas canvas) {
        if (this.f11640g) {
            this.l.setColor(this.f11639f);
            this.f11640g = false;
        }
        canvas.drawLines(this.k, this.l);

    }

    @Override
    public int getStartRadius() {
        return this.f11641h;

    }

    @Override
    public void reset(boolean close) {

    }

    @Override
    public void prepareToDynamic() {

    }
}
