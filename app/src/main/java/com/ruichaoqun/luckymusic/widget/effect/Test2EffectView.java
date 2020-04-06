package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.util.Pair;
import android.view.View;

import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.Arrays;

public abstract class Test2EffectView extends View implements DynamicEffectView {
    /* renamed from: a  reason: collision with root package name */
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
    private float[] H;
    private double I;
    private float J;
    private long K;
    private float[] L;
    private float[] M;
    private float[] N;
    private double O;
    private Path P;

    /* renamed from: b  reason: collision with root package name */
    protected int f11635b;

    /* renamed from: c  reason: collision with root package name */
    protected boolean f11636c;

    /* renamed from: d  reason: collision with root package name */
    protected int f11637d = -1;

    /* renamed from: e  reason: collision with root package name */
    protected float[] f11638e = new float[3];

    /* renamed from: f  reason: collision with root package name */
    protected int f11639f = ColorUtil.getEffectColor(this.f11637d, this.f11638e);

    /* renamed from: g  reason: collision with root package name */
    protected boolean f11640g = true;

    /* renamed from: h  reason: collision with root package name */
    protected int f11641h;

    /* renamed from: i  reason: collision with root package name */
    protected int f11642i;
    protected int j;
    protected float[] k;
    protected Paint l = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected Paint m = new Paint(1);
    protected Paint n;
    private int x;
    private int y;
    private int z;

    /* access modifiers changed from: protected */
    public abstract void a(Canvas canvas);

    public void a(boolean z2) {
    }

    public void ad_() {
    }

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

    /* access modifiers changed from: protected */
    public abstract int getStartRadius();

    public Test2EffectView(Context context) {
        super(context);
        this.m.setStrokeCap(Paint.Cap.ROUND);
        this.m.setColor(this.f11639f);
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
    public void setColor(int i2) {
        if (this.f11637d != i2) {
            this.f11637d = i2;
            this.f11639f = ColorUtil.getEffectColor(i2, this.f11638e);
            this.m.setColor(this.f11639f);
            Paint paint = this.n;
            if (paint != null) {
                paint.setColor(this.f11639f);
            }
            this.f11640g = true;
            invalidate();
        }
    }

    @Override
    public void onFftDataCapture(byte[] obj, int i2) {
        int a2 = obj.length;
        if (a2 > 0) {
            int i3 = 0;
            if (!(this.x == a2 && this.y == i2)) {
                this.x = a2;
                this.y = i2;
                float f2 = (((float) i2) / 1000.0f) / ((float) this.x);
                int i4 = (a2 / 2) - 1;
                this.z = (int) Math.ceil((double) (800.0f / f2));
                this.A = Math.min((int) (4800.0f / f2), i4);
                this.C = 0;
                if (this.f11636c) {
                    this.B = Math.min((int) Math.ceil((double) (4500.0f / f2)), i4);
                    int min = Math.min((int) (6500.0f / f2), i4) - this.B;
                    this.L = new float[min];
                    this.M = new float[min];
                    this.N = new float[min];
                    this.O = 6.283185307179586d / ((double) min);
                }
            }
            if (this.f11641h > 0) {
                if (this.C == 0) {
                    int i5 = (this.A - this.z) + 1;
                    float[] fArr = this.E;
                    int length = fArr.length;
                    int length2 = fArr.length;
                    this.C = i5 >= length ? i5 / length2 : -((int) Math.ceil((double) (((float) length2) / ((float) i5))));
                }
                this.D = c(obj);
                if (!this.D) {
                    float[] fArr2 = this.F;
                    System.arraycopy(fArr2, 0, this.G, 0, fArr2.length);
                    for (int i6 = 0; i6 < this.E.length; i6++) {
                        int i7 = this.C;
                        double a3 = k(obj, (this.z + (i7 > 0 ? i7 * i6 : i6 / (-i7))) * 2);
                        float[] fArr3 = this.E;
                        int i8 = this.f11642i;
                        a3 = a3 > 22.0d?(a3-22d):0.0d;
                        fArr3[i6] = (float) Math.min((a3 / 23.0d) * ((double) i8), (double) i8);
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
                if (this.f11636c) {
                    if (!this.D) {
                        float[] fArr5 = this.M;
                        System.arraycopy(fArr5, 0, this.N, 0, fArr5.length);
                        for (int i10 = 0; i10 < this.L.length; i10++) {
                            double a4 = k(obj, (this.B + i10) * 2);
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

    static double k(Object obj, int i2) {
        double pow;
        double pow2;
        double d2 = 1.0d;
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            pow = Math.pow((double) bArr[i2], 2.0d);
            pow2 = Math.pow((double) bArr[i2 + 1], 2.0d);
        } else {
            if (obj instanceof float[]) {
                float[] fArr = (float[]) obj;
                pow = Math.pow((double) fArr[i2], 2.0d);
                pow2 = Math.pow((double) fArr[i2 + 1], 2.0d);
            }
            return Math.log10(d2) * 10.0d;
        }
        d2 = 1.0d + pow + pow2;
        return Math.log10(d2) * 10.0d;
    }



    /* access modifiers changed from: protected */
    @Override
    public void onDraw(Canvas canvas) {
        boolean z2;
        float f2;
        Canvas canvas2 = canvas;
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        if (this.f11641h == 0) {
            View artView = ((DynamicEffectCommonLayout) getParent()).getArtView();
            this.j = (artView != null ? Math.max(artView.getWidth(), artView.getHeight()) / 2 : 0) + f11634a;
            this.f11641h = this.j + UiUtils.dp2px(this.f11636c ? 7.0f : 3.0f);
            this.f11642i = Math.min((Math.min(width, height) + UiUtils.dp2px(20.0f)) - this.f11641h, w);
            int i2 = (int) ((((double) this.f11641h) * 6.283185307179586d) / ((double) (this.f11635b * 2)));
            this.E = new float[i2];
            this.F = new float[i2];
            this.G = new float[i2];
            this.H = new float[(i2 * 2)];
            this.k = new float[(i2 * 4)];
            this.I = 6.283185307179586d / ((double) i2);
        }
        int saveCount = canvas.getSaveCount();
        canvas2.translate((float) width, (float) height);
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
                double d2 = this.I * ((double) i4);
                double cos = Math.cos(d2);
                double sin = Math.sin(d2);
                double d3 = (double) (((float) this.f11641h) + fArr2[i4] + ((fArr[i4] - fArr2[i4]) * f2));
                float f4 = (float) (d3 * cos);
                float f5 = (float) (d3 * sin);
                int i5 = i4 * 2;
                float[] fArr3 = this.H;
                fArr3[i5] = f4;
                fArr3[i5 + i3] = f5;
                int i6 = i4 * 4;
                int startRadius = getStartRadius();
                float[] fArr4 = this.k;
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
                double d5 = this.I * ((double) i7);
                this.H[i8] = (float) (((double) this.f11641h) * Math.cos(d5));
                this.H[i8 + 1] = (float) (((double) this.f11641h) * Math.sin(d5));
            }
            f2 = 1.0f;
            z2 = false;
        }
        canvas2.drawPoints(this.H, this.m);
        if (this.f11636c) {
            if (this.D || this.L.length == 0) {
                canvas2.drawCircle(0.0f, 0.0f, (float) this.j, this.n);
            } else {
                if (this.P == null) {
                    this.P = new Path();
                    this.n.setPathEffect(new CornerPathEffect((float) this.j));
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
                    double d7 = (double) (((float) this.j) - (fArr6[i9] + ((fArr5[i9] - fArr6[i9]) * f2)));
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
                canvas2.drawPath(this.P, this.n);
            }
        }
        canvas2.restoreToCount(saveCount);
        if (z2) {
            postInvalidateOnAnimation();
        }
    }

}
