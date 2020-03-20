package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.Iterator;
import java.util.Random;


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

    private int r;
    private long s;
    private LonglyHandler mHandler = new LonglyHandler();
    private Random mRandom = new Random();



    private Node<CircleRadius> v = new Node<>();
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
        int length = waveform.length;
        if (length > 0) {
            float sum = 0.0f;
            for (int i = 0; i < length; i++) {
                sum += (float)(waveform[i]+Byte.MIN_VALUE);
            }
            this.r = (int) (((sum / ((float) length)) / 256.0f) * 4.0f);
            Log.w("AAAAAAA","onWaveFormDataCapture-->"+r);
            if (this.r > 0 && !this.mHandler.hasMessages(0)) {
                long j2 = (long) (1000 / this.r);
                long uptimeMillis = SystemClock.uptimeMillis();
                long j3 = this.s;
                if (uptimeMillis - s < j2) {
                    uptimeMillis = j3 + j2;
                }
//                mHandler.sendEmptyMessageAtTime(0, uptimeMillis);
            }
        }

    }

    @Override
    public void reset(boolean close) {
//        if (this.f11628c > 0) {
//            boolean b2 = this.w.b();
//            com.netease.cloudmusic.module.ag.a.a<a> aVar = this.w;
//            int[] iArr = f11621b;
//            aVar.a(a(iArr[this.priviousData.nextInt(iArr.length)], this.priviousData.nextInt(360), j2));
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

    public void a(long uptimeMillis) {
        if (this.maxWidth > 0) {
            boolean b2 = this.data.b();
            int[] arr = mPointRadis;
            data.a(a(arr[this.mRandom.nextInt(arr.length)], this.mRandom.nextInt(360), uptimeMillis));
            this.s = uptimeMillis;
            if (b2) {
                invalidate();
            }
        }
        if (r > 0) {
            this.mHandler.sendEmptyMessageAtTime(0, uptimeMillis + ((long) (1000 / r)));
        }
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
                float uptimeMillis = (float) (SystemClock.uptimeMillis() - next.timeMillis);
                if (uptimeMillis >= mCircleLiveTime) {
                    it.remove();
                    a(next);
                } else {
                    float f2 = uptimeMillis / mCircleLiveTime;
                    int i2 = this.maxWidth;
                    int i3 = this.mArtRadius;
                    int i4 = (int) (((double) ((((float) (i2 - i3)) * f2) + ((float) i3))) + 0.5d);
                    int i5 = (int) (((double) ((((uptimeMillis * f11624f) / 1000.0f) + ((float) next.pointAngle)) % 360.0f)) + 0.5d);
                    float f3 = j;
                    float f4 = f11627i;
                    int alphaComponent = ColorUtils.setAlphaComponent(this.mEffectColor, (int) (((double) ((-102.0f * f2) + 102.0f)) + 0.5d));
                    this.mPaint.setColor(alphaComponent);
                    this.mPaint.setStrokeWidth((f2 * (f3 - f4)) + f4);
                    canvas.drawCircle(0.0f, 0.0f, (float) i4, this.mPaint);
                    double radians = Math.toRadians((double) i5);
                    double d2 = (double) i4;
                    this.mPointPaint.setColor(alphaComponent);
                    canvas.drawCircle((float) (Math.cos(radians) * d2), (float) (d2 * Math.sin(radians)), (float) next.pointRadius, this.mPointPaint);
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

    public CircleRadius a(int pointRadius, int pointAngle, long timeMillis) {
        CircleRadius circleRadius = this.v.a();
        if (circleRadius == null) {
            return new CircleRadius(pointRadius, pointAngle, timeMillis);
        }
        circleRadius.pointRadius = pointRadius;
        circleRadius.pointAngle = pointAngle;
        circleRadius.timeMillis = timeMillis;
        return circleRadius;
    }


    private class LonglyHandler extends Handler {
        private LonglyHandler() {
        }

        @Override
        public void handleMessage(Message message) {
            a(message.getWhen());
        }
    }



    class CircleRadius extends b<CircleRadius> {
        int pointRadius;
        int pointAngle;
        long timeMillis;

        CircleRadius(int pointRadius, int pointAngle, long timeMillis) {
            this.pointRadius = pointRadius;
            this.pointAngle = pointAngle;
            this.timeMillis = timeMillis;
        }
    }

}
