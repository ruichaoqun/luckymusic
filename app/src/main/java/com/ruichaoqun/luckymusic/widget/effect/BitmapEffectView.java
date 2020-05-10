package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.SystemClock;
import android.util.Pair;
import android.view.View;
import android.view.ViewParent;

import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Rui Chaoqun
 * @date :2020/4/3 16:04
 * description:
 */
public class BitmapEffectView extends View implements DynamicEffectView {

    /* renamed from: a  reason: collision with root package name */
    private static final int f9789a = 1000;

    /* renamed from: b  reason: collision with root package name */
    private static final int f9790b = 4800;

    /* renamed from: c  reason: collision with root package name */
    private static final int f9791c = 180;

    /* renamed from: d  reason: collision with root package name */
    private static final int f9792d = 45;

    /* renamed from: e  reason: collision with root package name */
    private static final int f9793e = UiUtils.dp2px(15.0f);

    /* renamed from: f  reason: collision with root package name */
    private static final int f9794f = UiUtils.dp2px(30.0f);

    /* renamed from: g  reason: collision with root package name */
    private static final int f9795g = UiUtils.dp2px(43.0f);

    /* renamed from: h  reason: collision with root package name */
    private static final int f9796h = 14;

    /* renamed from: i  reason: collision with root package name */
    private static final int f9797i = 10;
    private static final int j = 17;
    private static final float k = 1500.0f;
    private static final int l = 153;
    private static final int m = 0;
    private static final int n = UiUtils.dp2px(1.67f);
    private static final int q = UiUtils.dp2px(2.0f);
    private static final int r = UiUtils.dp2px(1.33f);
    private int A;
    private int B;
    private int C;
    private int D;
    private boolean E = true;
    private double[] F;
    private float[] G;
    private float[] H;
    private float[] I;
    private float[] J;
    private float[] K;
    private float[] L;
    private float[] M;
    private double N;
    private float O;
    private long P;
    private float Q;
    private Random R = new Random();
    private Bitmap[] S;
    private EffectData<a> mData = new EffectData<>();
    private Paint V = new Paint(1);
    private Paint W = new Paint(1);
    private Paint aa = new Paint(1);
    private Paint ab = new Paint(1);
    private boolean ac = true;
    private Path ad;
    private Path ae;
    private CornerPathEffect af;
    private int s = Color.WHITE;
    private float[] t = new float[3];
    private int u = ColorUtil.getEffectColor(this.s, this.t);
    private int v = ColorUtil.a(this.u, 10.0f, this.t);
    private int w;
    private int x;
    private int y;
    private int z;

    @Override
    public void prepareToDynamic() {
    }

    @Override
    public void reset(boolean z2) {
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

    public BitmapEffectView(Context context) {
        super(context);
        Resources resources = context.getResources();
        this.S = new Bitmap[]{((BitmapDrawable) resources.getDrawable(com.ruichaoqun.luckymusic.R.drawable.c3l)).getBitmap(), ((BitmapDrawable) resources.getDrawable(com.ruichaoqun.luckymusic.R.drawable.c3m)).getBitmap(), ((BitmapDrawable) resources.getDrawable(com.ruichaoqun.luckymusic.R.drawable.c3n)).getBitmap()};
        this.W.setColorFilter(new PorterDuffColorFilter(this.u, PorterDuff.Mode.SRC_ATOP));
        this.aa.setStrokeWidth((float) r);
        this.ab.setStyle(Paint.Style.STROKE);
    }

    @Override
    public Pair<Integer, Integer> getArtViewSize() {
        int a2 = UiUtils.dp2px(225.0f);
        return new Pair<>(Integer.valueOf(a2), Integer.valueOf(a2));
    }

    @Override
    public int getCaptureSize(VisualizerEntity dVar) {
        return dVar.getCaptureSizeRange()[1];
    }

    @Override
    public int getRate(VisualizerEntity dVar) {
        int d2 = (int) (((double) dVar.getMaxCaptureRate()) * 0.6d);
        this.O = 1000000.0f / ((float) d2);
        return d2;
    }

    @Override
    public void setColor(int i2) {
        if (this.s != i2) {
            this.s = i2;
            this.u = ColorUtil.getEffectColor(this.s, this.t);
            this.v = ColorUtil.a(this.u, 10.0f, this.t);
            this.W.setColorFilter(new PorterDuffColorFilter(this.u, PorterDuff.Mode.SRC_ATOP));
            this.ac = true;
            invalidate();
        }
    }

    @Override
    public void onFftDataCapture(byte[] obj, int i2) {
        int i3 = i2;
        int a2 = obj.length;
        if (a2 > 0) {
            if (!(this.w == a2 && this.x == i3)) {
                this.w = a2;
                this.x = i3;
                float f2 = (((float) i3) / 1000.0f) / ((float) this.w);
                this.y = (int) Math.ceil((double) (1000.0f / f2));
                int min = (Math.min((int) (4800.0f / f2), (a2 / 2) - 1) - this.y) + 1;
                this.z = min >= 180 ? min / 180 : -((int) Math.ceil((double) (180.0f / ((float) min))));
                this.F = new double[180];
                this.G = new float[180];
                this.H = new float[180];
                this.I = new float[180];
                this.J = new float[180];
                this.K = new float[180];
                this.L = new float[180];
                this.M = new float[720];
                this.N = 0.03490658503988659d;
            }
            if (this.A > 0) {
                this.E = c(obj);
                if (!this.E) {
                    float[] fArr = this.H;
                    System.arraycopy(fArr, 0, this.I, 0, fArr.length);
                    float[] fArr2 = this.K;
                    System.arraycopy(fArr2, 0, this.L, 0, fArr2.length);
                    double d2 = 0.0d;
                    double d3 = 0.0d;
                    for (int i4 = 0; i4 < this.G.length; i4++) {
                        int i5 = this.z;
                        double a3 = k(obj, (this.y + (i5 > 0 ? i5 * i4 : i4 / (-i5))) * 2);
                        this.F[i4] = a3;
                        if (a3 > d2) {
                            d2 = a3;
                        }
                        d3 += a3;
                    }
                    double d4 = d2 * 0.6d;
                    for (int i6 = 0; i6 < this.G.length; i6++) {
                        double[] dArr = this.F;
                        double d5 = (dArr[i6] > d4 ? dArr[i6] : 0.0d) / 45.0d;
                        float[] fArr3 = this.G;
                        int i7 = this.B;
                        fArr3[i6] = (float) Math.min(((double) i7) * d5, (double) i7);
                        float[] fArr4 = this.J;
                        int i8 = f9794f;
                        fArr4[i6] = (float) Math.min(d5 * ((double) i8), (double) i8);
                    }
                    for (int i9 = 0; i9 < this.K.length; i9++) {
                        this.H[i9] = q(this.G, i9, 9);
                        this.K[i9] = q(this.J, i9, 9);
                    }
                    this.P = SystemClock.uptimeMillis();
                    this.Q = (float) (((double) this.Q) + ((Math.max(Math.min(((d3 / ((double) this.G.length)) / 14.0d) * 17.0d, 17.0d), 10.0d) * ((double) this.O)) / 1000.0d));
                    float f3 = this.Q;
                    if (f3 >= 1.0f) {
                        int i10 = (int) f3;
                        a(i10);
                        this.Q -= (float) i10;
                    }
                } else {
                    Arrays.fill(this.H, 0.0f);
                    Arrays.fill(this.K, 0.0f);
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


    private void a(int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            double nextDouble = this.R.nextDouble() * 6.283185307179586d;
            long uptimeMillis = SystemClock.uptimeMillis();
            Bitmap[] bitmapArr = this.S;
            this.mData.addData(a(nextDouble, uptimeMillis, bitmapArr[this.R.nextInt(bitmapArr.length)]));
        }
    }

    private a a(double d2, long j2, Bitmap bitmap) {
        a a2 = this.mData.getCacheData();
        if (a2 == null) {
            return new a(d2, j2, bitmap);
        }
        a2.f9798b = d2;
        a2.f9799c = j2;
        a2.f9800d = bitmap;
        return a2;
    }

    @Override
    public void onDraw(Canvas canvas) {
        boolean z2;
        float f2;
        float f3;
        Canvas canvas2 = canvas;
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        if (this.A == 0) {
            ViewParent parent = getParent();
            if (!(parent instanceof DynamicEffectCommonLayout)) {
                parent = parent.getParent();
            }
            View artView = ((DynamicEffectCommonLayout) parent).getArtView();
            this.A = (artView != null ? Math.max(artView.getWidth(), artView.getHeight()) / 2 : 0) + f9793e;
            this.D = Math.min(width, height);
            this.B = Math.min((this.D - this.A) - UiUtils.dp2px(10.0f), f9795g);
            this.C = this.A + UiUtils.dp2px(10.0f);
        }
        int save = canvas.save();
        canvas2.translate((float) width, (float) height);
        if (this.ac) {
            Paint paint = this.V;
            float f4 = (float) this.C;
            int i2 = this.u;
            RadialGradient radialGradient = new RadialGradient(0.0f, 0.0f, f4, new int[]{i2, ColorUtils.setAlphaComponent(i2, 102), ColorUtils.setAlphaComponent(this.u, 0)}, new float[]{0.0f, (((float) (this.A - f9793e)) * 1.0f) / ((float) this.C), 1.0f}, Shader.TileMode.CLAMP);
            paint.setShader(radialGradient);
            int i3 = this.A;
            float f5 = (float) (this.B + i3);
            Paint paint2 = this.aa;
            RadialGradient radialGradient3 = new RadialGradient(0.0f, 0.0f, f5, new int[]{0, this.v, this.u}, new float[]{0.0f, ((float) (i3 - f9793e)) / f5, 1.0f}, Shader.TileMode.CLAMP);
            paint2.setShader(radialGradient3);
            this.ac = false;
        }
        //画渐变圆环
        canvas2.drawCircle(0.0f, 0.0f, (float) this.C, this.V);

        long uptimeMillis = SystemClock.uptimeMillis();
        if (!this.mData.isEmpty()) {
            Iterator<a> it = this.mData.iterator();
            float width2 = (float) ((this.A - f9793e) - this.S[0].getWidth());
            float f6 = (float) this.D;
            while (it.hasNext()) {
                a next = it.next();
                float f7 = (float) (uptimeMillis - next.f9799c);
                if (f7 >= k) {
                    it.remove();
                    f2 = width2;
                    f3 = f6;
                } else {
                    float f8 = f7 / k;
                    double d2 = (double) (((f6 - width2) * f8) + width2);
                    f3 = f6;
                    f2 = width2;
                    this.W.setAlpha((int) (((double) ((f8 * -153.0f) + 153.0f)) + 0.5d));
                    //画背景图片
                    canvas2.drawBitmap(next.f9800d, (float) ((Math.cos(next.f9798b) * d2) + 0.5d), (float) ((d2 * Math.sin(next.f9798b)) + 0.5d), this.W);
                }
                f6 = f3;
                width2 = f2;
            }
            z2 = true;
        } else {
            z2 = false;
        }
        if (this.E) {
            this.ab.setStrokeWidth((float) q);
            this.ab.setColor(this.u);
            //无数据画圆线
            canvas2.drawCircle(0.0f, 0.0f, (float) this.A, this.ab);
        } else {
            if (this.ad == null) {
                this.ad = new Path();
            }
            if (this.ae == null) {
                this.ae = new Path();
            }
            if (this.af == null) {
                this.af = new CornerPathEffect((float) (this.A + this.B));
            }
            this.ab.setPathEffect(this.af);
            float uptimeMillis2 = (float) (SystemClock.uptimeMillis() - this.P);
            float f9 = this.O;
            float f10 = uptimeMillis2 >= f9 ? 1.0f : uptimeMillis2 / f9;
            if (!z2) {
                z2 = f10 < 1.0f;
            }
            this.ae.reset();
            int i4 = 0;
            while (true) {
                float[] fArr = this.K;
                if (i4 >= fArr.length) {
                    break;
                }
                float[] fArr2 = this.L;
                float max = Math.max(((float) this.A) - (fArr2[i4]/2 + ((fArr[i4]/2 - fArr2[i4]/2) * f10)), 0.0f);
                double d3 = this.N * ((double) i4);
                double d4 = (double) max;
                float cos = (float) (d4 * Math.cos(d3));
                float sin = (float) (d4 * Math.sin(d3));
                if (i4 == 0) {
                    this.ae.moveTo(cos, sin);
                } else {
                    this.ae.lineTo(cos, sin);
                }
                int i5 = i4 * 4;
                float[] fArr3 = this.M;
                fArr3[i5] = cos;
                fArr3[i5 + 1] = sin;
                i4++;
            }
            this.ae.close();
            this.ad.reset();
            int i6 = 0;
            while (true) {
                float[] fArr4 = this.H;
                if (i6 >= fArr4.length) {
                    break;
                }
                float[] fArr5 = this.I;
                double d5 = this.N * ((double) i6);
                double d6 = (double) (((float) this.A) + fArr5[i6]/2 + ((fArr4[i6]/2 - fArr5[i6]/2) * f10));
                float cos2 = (float) (Math.cos(d5) * d6);
                float sin2 = (float) (d6 * Math.sin(d5));
                if (i6 == 0) {
                    this.ad.moveTo(cos2, sin2);
                } else {
                    this.ad.lineTo(cos2, sin2);
                }
                int i7 = (i6 * 4) + 2;
                float[] fArr6 = this.M;
                fArr6[i7] = cos2;
                fArr6[i7 + 1] = sin2;
                i6++;
            }
            this.ad.close();
            canvas2.drawLines(this.M, this.aa);
            this.ab.setStrokeWidth((float) n);
            this.ab.setColor(this.v);
            canvas2.drawPath(this.ae, this.ab);
            this.ab.setColor(this.u);
            canvas2.drawPath(this.ad, this.ab);
        }
        canvas2.restoreToCount(save);
        if (z2) {
            postInvalidateOnAnimation();
        }
    }

    /* compiled from: ProGuard */
    class a extends ListNode<a> {

        /* renamed from: b  reason: collision with root package name */
        double f9798b;

        /* renamed from: c  reason: collision with root package name */
        long f9799c;

        /* renamed from: d  reason: collision with root package name */
        Bitmap f9800d;

        a(double d2, long j, Bitmap bitmap) {
            this.f9798b = d2;
            this.f9799c = j;
            this.f9800d = bitmap;
        }
    }

}
