package com.ruichaoqun.luckymusic.widget.effect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import android.view.View;

import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.Iterator;
import java.util.Random;

/**
 * 粒子动效
 */
public class ParticleEffectView extends View implements DynamicEffectView {
    private static final float w = 0.20943952f;
    private static final float t = -49.0f;
    private static final float n = 45.0f;
    private static final float s = 257.0f;
    private static final float r = 65.0f;





    private int mParticleRadius;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private EffectData<ParticleData> mData = new EffectData<>();

    private int l;
    private Random mRandom = new Random();

    private float[] I = new float[30];
    private int[] J = new int[30];
    private float[] K = new float[30];

    private int mColor = Color.WHITE;
    private float[] outHsl = new float[3];
    private int mEffectColor = ColorUtil.getEffectColor(this.mColor, this.outHsl);

    private int fftLength;
    private int samplingRate;

    private int k;
    private int m;
    private boolean o;
    private boolean p;



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getData(msg.what, msg.getWhen());
        }
    };


    public ParticleEffectView(Context context) {
        super(context);
        this.mPaint.setStrokeWidth(3.0f);
    }

    @Override
    public void setColor(int color) {
        if (this.mColor != color) {
            this.mColor = color;
            this.mEffectColor = ColorUtil.getEffectColor(this.mColor, this.outHsl);
        }

    }

    @Override
    public void onFftDataCapture(byte[] fft, int samplingRate) {
        int length = fft.length;
        if (length > 0) {
            if (!(this.fftLength == length && this.samplingRate == samplingRate)) {
                this.fftLength = length;
                this.samplingRate = samplingRate;
                float f2 = (samplingRate / 1000.0f) / length;
                this.k = (int) Math.ceil((double) (800.0f / f2));
                int min = (Math.min((int) (4800.0f / f2), (length / 2) - 1) - this.k) + 1;
                this.m = min >= 30 ? min / 30 : -((int) Math.ceil((double) (30.0f / ((float) min))));
            }
            float f3 = 0.0f;
            float f4 = 0.0f;
            for (int i3 = 0; i3 < 30; i3++) {
                int i4 = this.m;
                float a3 = (float) getPhase(fft, (this.k + (i4 > 0 ? i4 * i3 : i3 / (-i4))) * 2);
                this.I[i3] = a3;
                if (a3 > f3) {
                    f3 = a3;
                }
                f4 += a3;
            }
            for (int i5 = 0; i5 < 30; i5++) {
                float f5 = this.I[i5];
                float f6 = f5 / n;
                int i6 = (int) (n * f6);
                if (i6 < 5 && f5 == f3 && f3 > 0.0f) {
                    i6 = 5;
                }
                this.J[i5] = i6;
                this.K[i5] = (f6 * s) + r;
            }
            this.l = (int) (Math.min((f4 / 30.0f) / 10.0f, 1.0f) * 127.0f);
            if (this.o) {
                this.mHandler.sendEmptyMessage(1);
                this.o = false;
            }
        }

    }

    public double getPhase(byte[] fft, int index){
        return Math.log10(1.0d + Math.pow((double) fft[index], 2.0d) + Math.pow((double) fft[index + 1], 2.0d)) * 10.0d;
    }

    @Override
    public void onWaveFormDataCapture(byte[] waveform, int samplingRate) {

    }

    private void getData(int what, long when) {
        float f2 = 1.0f;
        boolean isEmpty = mData.isEmpty();
        if (what == 0) {
            for (int i4 = 0; i4 < 70; i4++) {
                this.mData.addData(getData((((mRandom.nextFloat() * 2.0f) - 1.0f) * 10.0f) + 10.0f, 0.0f, (float) (((double) mRandom.nextFloat()) * 6.283185307179586d), Color.WHITE, 1500, when));
                this.mData.addData(getData((((mRandom.nextFloat() * 2.0f) - 1.0f) * 10.0f) + 20.0f, 0.0f, (float) (((double) mRandom.nextFloat()) * 6.283185307179586d), mEffectColor, 1500, when));
            }
            this.mHandler.sendEmptyMessageDelayed(what, 70);
        } else {
            int i5 = 0;
            while (i5 < 30) {
                float f3 = ((float) i5) * w;
                int i6 = 0;
                while (i6 < this.J[i5]) {
                    float nextFloat = f3 + (this.mRandom.nextFloat() * w);
                    float f4 = this.K[i5];
                    this.mData.addData(getData((f2 - (mRandom.nextFloat() * 0.5f)) * f4, t, nextFloat, mEffectColor, 1000, when));
                    i6++;
                    f2 = 1.0f;
                }
                i5++;
                f2 = 1.0f;
            }
            this.mHandler.sendEmptyMessageDelayed(what, 50);
        }
        if (isEmpty) {
            invalidate();
        }
    }

    private ParticleData getData(float v, float v1, float v2, int color, int survivalTime, long when) {
        ParticleData data = this.mData.getCacheData();
        if (data == null) {
            return new ParticleData(v, v1, v2, color, survivalTime, when);
        }
        data.f11614b = v;
        data.f11615c = v1;
        data.f11616d = v2;
        data.f11617e = color;
        data.survivalTime = survivalTime;
        data.timeMillis = when;
        return data;

    }

    @Override
    public void reset(boolean close) {
        if (close) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mData.clear();
        } else {
            this.mHandler.removeMessages(1);
        }
        this.l = 0;
        this.o = true;
        this.p = true;
    }

    @Override
    public void prepareToDynamic() {
        if (this.p) {
            this.mHandler.sendEmptyMessage(0);
            this.p = false;
        }
    }

    @Override
    public boolean isFft() {
        return true;
    }

    @Override
    public boolean isWaveform() {
        return false;
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
        return visualizerEntity.getCaptureSizeRange()[1];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float f2;
        if (this.mParticleRadius == 0) {
            View artView = ((DynamicEffectCommonLayout) getParent()).getArtView();
            this.mParticleRadius = (artView != null ? Math.max(artView.getWidth(), artView.getHeight()) / 2 : 0) + UiUtils.dp2px(5.0f);
            this.mHandler.sendEmptyMessage(0);
        }
        if (!mData.isEmpty()) {
            int save = canvas.save();
            float f3 = 2.0f;
            canvas.translate(width / 2, height / 2);
            long uptimeMillis = SystemClock.uptimeMillis();
            Iterator<ParticleData> iterator = mData.iterator();
            while (iterator.hasNext()) {
                ParticleData next = iterator.next();
                long j2 = uptimeMillis - next.timeMillis;
                if (j2 >= ((long) next.survivalTime)) {
                    iterator.remove();
                } else {
                    float f5 = 1000.0f;
                    if (next.f11615c == 0.0f) {
                        f2 = next.f11614b * ((float) j2);
                    } else {
                        float max = Math.max(0.0f, next.f11614b + ((next.f11615c * ((float) j2)) / 1000.0f));
                        f2 = ((max * max) - (next.f11614b * next.f11614b)) / f3;
                        f5 = next.f11615c;
                    }
                    double d2 = (double) (mParticleRadius + (f2 / f5));
                    float sin = (float) ((d2 * Math.sin((double) next.f11616d)) + 0.5d);
                    int min = Math.min(this.l + 128, 255);
                    this.mPaint.setColor(ColorUtils.setAlphaComponent(next.f11617e, (int) (((float) min) - ((((float) (((long) min) * j2)) * 1.0f) / ((float) next.survivalTime)))));
                    canvas.drawPoint((float) ((Math.cos((double) next.f11616d) * d2) + 0.5d), sin, this.mPaint);
                }
                f3 = 2.0f;
            }
            canvas.restoreToCount(save);
            postInvalidateOnAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        this.mHandler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    class ParticleData extends ListNode<ParticleData> {
        float f11614b;

        float f11615c;

        float f11616d;

        int f11617e;


        long timeMillis;
        long survivalTime;

        ParticleData(float f2, float f3, float f4, int i2, int survivalTime, long timeMillis) {
            this.f11614b = f2;
            this.f11615c = f3;
            this.f11616d = f4;
            this.f11617e = i2;
            this.survivalTime = survivalTime;
            this.timeMillis = timeMillis;
        }

    }
}
