package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.util.Pair;
import android.view.View;

import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.Arrays;

public abstract class ParticleCommonEffectView extends View implements DynamicEffectView {
    protected static final int f11634a = UiUtils.dp2px(10.0f);
    private static final int q = 800;
    private static final int r = 4800;
    private static final int s = 4500;
    private static final int t = 6500;
    private static final int u = 45;
    private static final int v = 20;
    private static final int w = UiUtils.dp2px(85.0f);
    private int A;
    private int B;
    private int C;
    private boolean D = true;
    private float[] E;
    private float[] F;
    private float[] G;
    private float[] mOriginalPointsXY;
    //每条射线的弧度
    private double mSingleRadian;
    private float J;
    private long K;
    private float[] L;
    private float[] M;
    private float[] N;
    private double O;
    private Path P;

    //点宽度
    protected int mPointsWidth;

    protected boolean isShowCircleLine;

    protected int mImageMainColor = Color.WHITE;

    protected float[] outHsl = new float[3];

    protected int mPointColor = ColorUtil.getEffectColor(this.mImageMainColor, this.outHsl);

    protected boolean f11640g = true;

    //点集合半径
    protected int mPointCircleRadius;

    protected int mLineMaxWidth;
    //射线初始半径
    protected int mOriginalRadius;
    protected float[] pts;
    protected Paint l = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint m = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint n;
    private int mFftLength;
    private int mSamplingRate;
    private int z;

    public abstract void a(Canvas canvas);

    @Override
    public void onWaveFormDataCapture(byte[] obj, int i2) {
    }

    @Override
    public boolean isFft() {
        return true;
    }

    @Override
    public boolean isWaveform() {
        return false;
    }

    public abstract int getStartRadius();

    public ParticleCommonEffectView(Context context) {
        super(context);
        this.m.setStrokeCap(Paint.Cap.ROUND);
        this.m.setColor(this.mPointColor);
    }

    @Override
    public Pair<Integer, Integer> getArtViewSize() {
        int a2 = UiUtils.dp2px(203.0f);
        return new Pair<>(Integer.valueOf(a2), Integer.valueOf(a2));
    }

    @Override
    public int getCaptureSize(VisualizerEntity dVar) {
        return dVar.getCaptureSizeRange()[1];
    }

    @Override
    public int getRate(VisualizerEntity dVar) {
        int d2 = (int) (((double) dVar.getMaxCaptureRate()) * 0.85d);
        this.J = 1000000.0f / ((float) d2);
        return d2;
    }

    @Override
    public void setColor(int color) {
        if (this.mImageMainColor != color) {
            this.mImageMainColor = color;
            this.mPointColor = ColorUtil.getEffectColor(color, this.outHsl);
            this.m.setColor(this.mPointColor);
            if (this.n != null) {
                this.n.setColor(this.mPointColor);
            }
            this.f11640g = true;
            invalidate();
        }
    }

    @Override
    public void onFftDataCapture(byte[] fft, int samplingRate) {
        int fftLength = fft.length;
        if (fftLength > 0) {
            int i3 = 0;
            if (!(this.mFftLength == fftLength && this.mSamplingRate == samplingRate)) {
                //初始化数据集合
                this.mFftLength = fftLength;
                this.mSamplingRate = samplingRate;
                float f2 = (((float) samplingRate) / 1000.0f) / ((float) this.mFftLength);//43
                int maxCount = (fftLength / 2) - 1;
                this.z = (int) Math.ceil((double) (800.0f / f2));
                this.A = Math.min((int) (4800.0f / f2), maxCount);
                this.C = 0;
                if (this.isShowCircleLine) {
                    this.B = Math.min((int) Math.ceil((double) (4500.0f / f2)), maxCount);
                    int min = Math.min((int) (6500.0f / f2), maxCount) - this.B;
                    this.L = new float[min];
                    this.M = new float[min];
                    this.N = new float[min];
                    this.O = 6.283185307179586d / ((double) min);
                }
            }
            if (this.mPointCircleRadius > 0) {
                if (this.C == 0) {
                    int i5 = (this.A - this.z) + 1;
                    float[] fArr = this.E;
                    this.C = i5 >= E.length ? i5 / E.length : -((int) Math.ceil((double) (((float) E.length) / ((float) i5))));
                }
                this.D = c(fft);
                if (!this.D) {
                    float[] fArr2 = this.F;
                    System.arraycopy(fArr2, 0, this.G, 0, fArr2.length);
                    for (int i = 0; i < this.E.length; i++) {
                        double magnitude = getMagnitude(fft, (this.z + (this.C > 0 ? this.C * i : i / (-this.C))) * 2);
                        magnitude = magnitude > 22.0d ? (magnitude - 22d) : 0.0d;
                        this.E[i] = (float) Math.min((magnitude / 23.0d) * ((double) mLineMaxWidth), (double) mLineMaxWidth);
                    }
                    int i9 = 0;
                    while (true) {
                        float[] fArr4 = this.F;
                        if (i9 >= fArr4.length) {
                            break;
                        }
                        fArr4[i9] = q(this.E, i9, 9);
                        i9++;
                    }
                    this.K = SystemClock.uptimeMillis();
                } else {
                    Arrays.fill(this.F, 0.0f);
                }
                if (this.isShowCircleLine) {
                    if (!this.D) {
                        float[] fArr5 = this.M;
                        System.arraycopy(fArr5, 0, this.N, 0, fArr5.length);
                        for (int i10 = 0; i10 < this.L.length; i10++) {
                            double a4 = getMagnitude(fft, (this.B + i10) * 2);
                            float[] fArr6 = this.L;
                            int i11 = f11634a;
                            fArr6[i10] = (float) Math.min((a4 / 20.0d) * ((double) i11), (double) i11);
                        }
                        while (true) {
                            float[] fArr7 = this.M;
                            if (i3 >= fArr7.length) {
                                break;
                            }
                            fArr7[i3] = q(this.L, i3, 5);
                            i3++;
                        }
                    } else {
                        Arrays.fill(this.M, 0.0f);
                    }
                }
                invalidate();
            }
        }
    }

    private static final float[] f11694f = {-0.09090909f, 0.060606062f, 0.16883117f, 0.23376623f, 0.25541127f, 0.23376623f, 0.16883117f, 0.060606062f, -0.09090909f};
    private static final float[] f11693e = {-0.0952381f, 0.14285715f, 0.2857143f, 0.33333334f, 0.2857143f, 0.14285715f, -0.0952381f};
    private static final float[] f11692d = {-0.08571429f, 0.34285715f, 0.4857143f, 0.34285715f, -0.08571429f};


    static float q(float[] fArr, int i2, int i3) {
        float[] fArr2 = i3 == 5 ? f11692d : i3 == 7 ? f11693e : f11694f;
        int length = i2 - (fArr2.length / 2);
        int length2 = fArr2.length;
        int i4 = 0;
        float f2 = 0.0f;
        int i5 = 0;
        while (i4 < length2) {
            float f3 = fArr2[i4];
            int i6 = i5 + 1;
            int i7 = i5 + length;
            int length3 = fArr.length;
            if (i7 < 0) {
                i7 += length3;
            } else if (i7 > length3 - 1) {
                i7 -= fArr.length;
            }
            f2 += f3 * fArr[i7];
            i4++;
            i5 = i6;
        }
        return Math.max(f2, 0.0f);
    }


    static boolean c(Object obj) {
        if (obj instanceof byte[]) {
            for (byte b2 : (byte[]) obj) {
                if (b2 != 0) {
                    return false;
                }
            }
            return true;
        } else if (!(obj instanceof float[])) {
            return true;
        } else {
            for (float f2 : (float[]) obj) {
                if (f2 != 0.0f) {
                    return false;
                }
            }
            return true;
        }
    }


    /**
     * 获取对应index的振幅
     * @param fft
     * @param index
     * @return
     */
    static double getMagnitude(byte[] fft, int index) {
        double pow;
        double pow2;
        pow = Math.pow(fft[index], 2.0d);
        pow2 = Math.pow(fft[index + 1], 2.0d);
        return Math.log10(1.0d + pow + pow2) * 10.0d;
    }


    @Override
    public void onDraw(Canvas canvas) {
        boolean z2;
        float f2;
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        if (this.mPointCircleRadius == 0) {
            View artView = ((DynamicEffectCommonLayout) getParent()).getArtView();
            this.mOriginalRadius = (artView != null ? Math.max(artView.getWidth(), artView.getHeight()) / 2 : 0) + f11634a;
            this.mPointCircleRadius = this.mOriginalRadius + UiUtils.dp2px(this.isShowCircleLine ? 7.0f : 3.0f);
            this.mLineMaxWidth = Math.min((Math.min(width, height) + UiUtils.dp2px(20.0f)) - this.mPointCircleRadius, w);
            int pointCounts = (int) (((this.mPointCircleRadius) * Math.PI * 2) / (this.mPointsWidth * 2));
            this.E = new float[pointCounts];
            this.F = new float[pointCounts];
            this.G = new float[pointCounts];
            this.mOriginalPointsXY = new float[(pointCounts * 2)];
            this.pts = new float[(pointCounts * 4)];
            this.mSingleRadian = Math.PI * 2 / pointCounts;
        }
        int saveCount = canvas.getSaveCount();
        canvas.translate(width, height);
        int i3 = 1;
        if (!this.D) {
            float uptimeMillis = (float) (SystemClock.uptimeMillis() - this.K);
            float f3 = this.J;
            f2 = uptimeMillis >= f3 ? 1.0f : uptimeMillis / f3;
            z2 = f2 < 1.0f;
            int i4 = 0;
            while (true) {
                float[] fArr = this.F;
                if (i4 >= fArr.length) {
                    break;
                }
                float[] fArr2 = this.G;
                double d2 = this.mSingleRadian * ((double) i4);
                double cos = Math.cos(d2);
                double sin = Math.sin(d2);
                double d3 = (double) (((float) this.mPointCircleRadius) + fArr2[i4] + ((fArr[i4] - fArr2[i4]) * f2));
                float f4 = (float) (d3 * cos);
                float f5 = (float) (d3 * sin);
                int i5 = i4 * 2;
                float[] fArr3 = this.mOriginalPointsXY;
                fArr3[i5] = f4;
                fArr3[i5 + i3] = f5;
                int i6 = i4 * 4;
                int startRadius = getStartRadius();
                float[] fArr4 = this.pts;
                double d4 = (double) startRadius;
                fArr4[i6] = (float) (cos * d4);
                fArr4[i6 + 1] = (float) (d4 * sin);
                fArr4[i6 + 2] = f4;
                fArr4[i6 + 3] = f5;
                i4++;
                i3 = 1;
            }
            a(canvas);
        } else {
            for (int i7 = 0; i7 < this.E.length; i7++) {
                int i8 = i7 * 2;
                double d5 = this.mSingleRadian * ((double) i7);
                this.mOriginalPointsXY[i8] = (float) (((double) this.mPointCircleRadius) * Math.cos(d5));
                this.mOriginalPointsXY[i8 + 1] = (float) (((double) this.mPointCircleRadius) * Math.sin(d5));
            }
            f2 = 1.0f;
            z2 = false;
        }
        canvas.drawPoints(this.mOriginalPointsXY, this.m);
        if (this.isShowCircleLine) {
            if (this.D || this.L.length == 0) {
                canvas.drawCircle(0.0f, 0.0f, (float) this.mOriginalRadius, this.n);
            } else {
                if (this.P == null) {
                    this.P = new Path();
                    this.n.setPathEffect(new CornerPathEffect((float) this.mOriginalRadius));
                }
                this.P.reset();
                int i9 = 0;
                while (true) {
                    float[] fArr5 = this.M;
                    if (i9 >= fArr5.length) {
                        break;
                    }
                    float[] fArr6 = this.N;
                    double d6 = this.O * ((double) i9);
                    double d7 = (double) (((float) this.mOriginalRadius) - (fArr6[i9] + ((fArr5[i9] - fArr6[i9]) * f2)));
                    float cos2 = (float) (Math.cos(d6) * d7);
                    float sin2 = (float) (d7 * Math.sin(d6));
                    if (i9 == 0) {
                        this.P.moveTo(cos2, sin2);
                    } else {
                        this.P.lineTo(cos2, sin2);
                    }
                    i9++;
                }
                this.P.close();
                canvas.drawPath(this.P, this.n);
            }
        }
        canvas.restoreToCount(saveCount);
        if (z2) {
            postInvalidateOnAnimation();
        }
    }

}
