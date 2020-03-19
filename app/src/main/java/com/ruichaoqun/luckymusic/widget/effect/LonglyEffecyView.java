package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.Iterator;

public class LonglyEffecyView extends View implements DynamicEffectView {
    static final int[] mPointRadis = {UiUtils.dp2px(2.0f), UiUtils.dp2px(3.0f),UiUtils.dp2px(4.0f),UiUtils.dp2px(5.0f)};
    private static final float mCircleLiveTime = 2000.0f;
    private static final float f11624f = -20.0f;
    private static final float j = UiUtils.dp2px(1.0f);
    private static final float f11627i = UiUtils.dp2px(2.0f);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPointPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int mStrokeWidth = UiUtils.dp2px(5.0f);
    private int mColor = Color.WHITE;
    private float[] outHsl = new float[3];
    private int mEffectColor = ColorUtil.getEffectColor(this.mColor, this.outHsl);
    private int maxWidth = -1;
    private int mArtRadius;
    private int mStrokeRadius;


    private c<CircleRadius> v = new c<>();
    private a<CircleRadius> data = new a<>();


    public LonglyEffecyView(Context context) {
        super(context);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setColor(Color.parseColor("#1AFFFFFF"));
        this.mStrokePaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    public void setColor(int color) {
        if (this.mColor != color) {
            this.mColor = color;
            this.mEffectColor = ColorUtil.getEffectColor(mColor, this.outHsl);
            invalidate();
        }
    }

    @Override
    public void onFftDataCapture(byte[] fft, int samplingRate) {

    }

    @Override
    public void onWaveFormDataCapture(byte[] waveform, int samplingRate) {

    }

    @Override
    public void reset(boolean close) {
//        if (this.f11628c > 0) {
//            boolean b2 = this.w.b();
//            com.netease.cloudmusic.module.ag.a.a<a> aVar = this.w;
//            int[] iArr = f11621b;
//            aVar.a(a(iArr[this.t.nextInt(iArr.length)], this.t.nextInt(360), j2));
//            this.s = j2;
//            if (b2) {
//                invalidate();
//            }
//        }
//        int i2 = this.r;
//        if (i2 > 0) {
//            this.u.sendEmptyMessageAtTime(0, j2 + ((long) (1000 / i2)));
//        }

    }

    @Override
    public void prepareToDynamic() {

    }

    @Override
    public boolean isFft() {
        return false;
    }

    @Override
    public boolean isWaveform() {
        return true;
    }

    @Override
    public int getRate(VisualizerEntity visualizerEntity) {
        return visualizerEntity.getMaxCaptureRate();
    }

    @Override
    public Pair<Integer, Integer> getArtViewSize() {
        int width = UiUtils.dp2px(225.0f);
        return new Pair<>(width, width);
    }

    @Override
    public int getCaptureSize(VisualizerEntity visualizerEntity) {
        return visualizerEntity.getCaptureSizeRange()[0];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        if (this.maxWidth == -1) {
            this.maxWidth = Math.min(width, height);
            View artView = ((DynamicEffectCommonLayout) getParent()).getArtView();
            if (artView != null) {
                this.mArtRadius = Math.max(artView.getWidth(), artView.getHeight()) / 2;
            } else {
                this.mArtRadius = mPointRadis[mPointRadis.length - 1];
            }
            this.mStrokeRadius = this.mArtRadius + (mStrokeWidth / 2);
        }
        boolean z2 = false;
        int save = canvas.save();
        canvas.translate((float) width, (float) height);
        if (!this.data.b()) {
            Iterator<CircleRadius> it = this.data.iterator();
            while (it.hasNext()) {
                CircleRadius next = it.next();
                float uptimeMillis = (float) (SystemClock.uptimeMillis() - next.f11631d);
                if (uptimeMillis >= mCircleLiveTime) {
                    it.remove();
                    a(next);
                } else {
                    float f2 = uptimeMillis / mCircleLiveTime;
                    int i2 = this.maxWidth;
                    int i3 = this.mArtRadius;
                    int i4 = (int) (((double) ((((float) (i2 - i3)) * f2) + ((float) i3))) + 0.5d);
                    int i5 = (int) (((double) ((((uptimeMillis * f11624f) / 1000.0f) + ((float) next.f11630c)) % 360.0f)) + 0.5d);
                    float f3 = j;
                    float f4 = f11627i;
                    int alphaComponent = ColorUtils.setAlphaComponent(this.mEffectColor, (int) (((double) ((-102.0f * f2) + 102.0f)) + 0.5d));
                    this.mPaint.setColor(alphaComponent);
                    this.mPaint.setStrokeWidth((f2 * (f3 - f4)) + f4);
                    canvas.drawCircle(0.0f, 0.0f, (float) i4, this.mPaint);
                    double radians = Math.toRadians((double) i5);
                    double d2 = (double) i4;
                    this.mPointPaint.setColor(alphaComponent);
                    canvas.drawCircle((float) (Math.cos(radians) * d2), (float) (d2 * Math.sin(radians)), (float) next.f11629b, this.mPointPaint);
                }
            }
            z2 = true;
        }
        canvas.drawCircle(0.0f, 0.0f, (float) this.mStrokeRadius, this.mStrokePaint);
        canvas.restoreToCount(save);
        if (z2) {
            postInvalidateOnAnimation();
        }

    }

    private void a(CircleRadius aVar) {
        this.v.a(aVar);
    }


    class CircleRadius extends b<CircleRadius> {

        /* renamed from: b  reason: collision with root package name */
        int f11629b;

        /* renamed from: c  reason: collision with root package name */
        int f11630c;

        /* renamed from: d  reason: collision with root package name */
        long f11631d;

        CircleRadius(int i2, int i3, long j) {
            this.f11629b = i2;
            this.f11630c = i3;
            this.f11631d = j;
        }
    }

}
