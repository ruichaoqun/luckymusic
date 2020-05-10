package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import android.view.View;
import android.view.ViewParent;

import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Rui Chaoqun
 * @date :2020/4/3 16:26
 * description:
 */
public class PathEffectView extends View implements DynamicEffectView {

    /* renamed from: a  reason: collision with root package name */
    private static final int f9765a = 1000;

    /* renamed from: b  reason: collision with root package name */
    private static final int f9766b = 4800;

    /* renamed from: c  reason: collision with root package name */
    private static final int f9767c = 180;

    /* renamed from: d  reason: collision with root package name */
    private static final int f9768d = 45;

    /* renamed from: e  reason: collision with root package name */
    private static final int f9769e = UiUtils.dp2px(15.0f);

    /* renamed from: f  reason: collision with root package name */
    private static final int f9770f = UiUtils.dp2px(30.0f);

    /* renamed from: g  reason: collision with root package name */
    private static final int f9771g = UiUtils.dp2px(43.0f);

    /* renamed from: h  reason: collision with root package name */
    private static final int f9772h = 14;

    /* renamed from: i  reason: collision with root package name */
    private static final int f9773i = 16;
    private static final int j = 28;
    private static final double k = 0.17453292519943295d;
    private static final int l = UiUtils.dp2px(10.0f);
    private static final int m = UiUtils.dp2px(25.0f);
    private static final float n = 1500.0f;
    private static final int q = 31;
    private static final int r = 0;
    private static final int s = 153;
    private static final int t = 0;
    private static final int u = UiUtils.dp2px(1.67f);
    private static final int v = UiUtils.dp2px(2.0f);
    private static final int w = UiUtils.dp2px(1.33f);
    private static final int x = UiUtils.dp2px(1.0f);
    private int y = Color.WHITE;
    private float[] z = new float[3];
    private int A = ColorUtil.getEffectColor(this.y, this.z);
    private int B = ColorUtil.a(this.A, 10.0f, this.z);
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private int J;
    private boolean K = true;
    private double[] L;
    private float[] M;
    private float[] N;
    private float[] O;
    private float[] P;
    private float[] Q;
    private float[] R;
    private float[] S;
    private double T;
    private float U;
    private long V;
    private float W;
    private Random aa = new Random();
    private Paint ae = new Paint(1);
    private Paint af = new Paint(1);
    private Paint ag = new Paint(1);
    private Paint ah = new Paint(1);
    private boolean ai = true;
    private Path aj;
    private Path ak;
    private CornerPathEffect al;
    private Handler am;
    private Handler an;


    boolean f9776a;

    /* renamed from: b  reason: collision with root package name */
    float[] f9777b;

    /* renamed from: c  reason: collision with root package name */
    float[] f9778c;

    /* renamed from: d  reason: collision with root package name */
    EffectData<b> f9779d = new EffectData<>();

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

    public PathEffectView(Context context) {
        super(context);
        this.af.setStrokeWidth((float) x);
        this.ag.setStrokeWidth((float) w);
        this.ah.setStyle(Paint.Style.STROKE);
        HandlerThread handlerThread = new HandlerThread("StretchRender");
        handlerThread.start();
        this.am = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message message) {
                byte[] bytes = (byte[]) message.obj;
                PathEffectView.this.a(bytes, message.arg1, message.arg2);
            }
        };
        this.an = new Handler() {
            @Override
            public void handleMessage(Message message) {
                PathEffectView.this.e();
            }
        };
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
        this.U = 1000000.0f / ((float) d2);
        return d2;
    }

    @Override
    public void setColor(int i2) {
        if (this.y != i2) {
            this.y = i2;
            this.A = ColorUtil.getEffectColor(this.y, this.z);
            this.B = ColorUtil.a(this.A, 10.0f, this.z);
            this.ai = true;
            invalidate();
        }
    }

    @Override
    public void onFftDataCapture(byte[] obj, int i2) {
        int a2 = obj.length;
        if (a2 > 0) {
            if (!(this.C == a2 && this.D == i2)) {
                this.C = a2;
                this.D = i2;
                float f2 = (((float) i2) / 1000.0f) / ((float) this.C);
                this.E = (int) Math.ceil((double) (1000.0f / f2));
                int min = (Math.min((int) (4800.0f / f2), (a2 / 2) - 1) - this.E) + 1;
                this.F = min >= 180 ? min / 180 : -((int) Math.ceil((double) (180.0f / ((float) min))));
            }
            if (this.L == null) {
                this.L = new double[180];
                this.M = new float[180];
                this.N = new float[180];
                this.O = new float[180];
                this.P = new float[180];
                this.Q = new float[180];
                this.R = new float[180];
                this.S = new float[720];
                this.T = 0.03490658503988659d;
                f9777b = new float[180];
                f9778c = new float[180];
            }
            if (this.G > 0) {
                Handler handler = this.am;
                handler.sendMessage(handler.obtainMessage(1, this.E, this.F,obj ));
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(byte[] obj, int i2, int i3) {
        int i4;
        int i5 = i3;
        f9776a = c(obj);
        if (!f9776a) {
            double d2 = 0.0d;
            double d3 = 0.0d;
            for (int i6 = 0; i6 < this.M.length; i6++) {
                if (i5 > 0) {
                    i4 = i6 * i5;
                } else {
                    i4 = i6 / (-i5);
                }
                double a3 = k(obj, (i2 + i4) * 2);
                this.L[i6] = a3;
                if (a3 > d2) {
                    d2 = a3;
                }
                d3 += a3;
            }
            double d4 = d2 * 0.6d;
            int i7 = 0;
            while (i7 < this.M.length) {
                double[] dArr = this.L;
                double d5 = (dArr[i7] > d4 ? dArr[i7] : 0.0d) / 45.0d;
                float[] fArr2 = this.M;
                int i8 = this.H;
                fArr2[i7] = (float) Math.min(((double) i8) * d5, (double) i8);
                float[] fArr3 = this.P;
                int i9 = f9770f;
                fArr3[i7] = (float) Math.min(d5 * ((double) i9), (double) i9);
                i7++;
                d4 = d4;
            }
            for (int i10 = 0; i10 < f9777b.length; i10++) {
                f9777b[i10] = q(this.M, i10, 9);
                f9778c[i10] = q(this.P, i10, 9);
            }
            this.W = (float) (((double) this.W) + ((Math.max(Math.min(((d3 / ((double) this.M.length)) / 14.0d) * 28.0d, 28.0d), 16.0d) * ((double) this.U)) / 1000.0d));
            float f2 = this.W;
            if (f2 >= 1.0f) {
                int i11 = (int) f2;
                a(i11, f9779d);
                this.W -= (float) i11;
            }
        } else {
            Arrays.fill(f9777b, 0.0f);
            Arrays.fill(f9778c, 0.0f);
        }
        a();
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

    private void a(int i2, EffectData<b> cVar) {
        int i3 = i2;
        int i4 = 0;
        while (i4 < i3) {
            double nextDouble = aa.nextDouble() * 6.283185307179586d;
            long uptimeMillis = SystemClock.uptimeMillis();
            double nextDouble2 =aa.nextDouble() * 2.9670597283903604d;
            double d2 = 1.5707963267948966d + nextDouble;
            double d3 = nextDouble2 + d2;
            double nextDouble3 = nextDouble2 + k + (((3.141592653589793d - nextDouble2) - k) * aa.nextDouble()) + d2;
            int i5 = m;
            int i6 = l;
            int i7 = i5 - i6;
            double nextInt = (double) (i6 + aa.nextInt(i7));
            float sin = (float) ((nextInt * Math.sin(d3)) + 0.5d);
            double nextInt2 = (double) (l + aa.nextInt(i7));
            cVar.addData(a(nextDouble, uptimeMillis, (float) ((Math.cos(d3) * nextInt) + 0.5d), sin, (float) ((Math.cos(nextDouble3) * nextInt2) + 0.5d), (float) ((nextInt2 * Math.sin(nextDouble3)) + 0.5d)));
            i4++;
            i3 = i2;
        }
    }

    private b a(double d2, long j2, float f2, float f3, float f4, float f5) {
        b a2= this.f9779d.getCacheData();
        if (a2 != null) {
            a2.f9780b = d2;
            a2.f9781c = j2;
            a2.f9782d = f2;
            a2.f9783e = f3;
            a2.f9784f = f4;
            a2.f9785g = f5;
            return a2;
        }
        return new b(d2, j2, f2, f3, f4, f5);
    }


    public void a() {
        this.an.sendEmptyMessage(1);
    }

    /* access modifiers changed from: private */
    public void e() {
        this.K = f9776a;
        if (!this.K) {
            float[] fArr = this.N;
            System.arraycopy(fArr, 0, this.O, 0, fArr.length);
            float[] fArr2 = this.Q;
            System.arraycopy(fArr2, 0, this.R, 0, fArr2.length);
        }
        this.N = f9777b;
        this.Q = f9778c;
        this.V = SystemClock.uptimeMillis();
        invalidate();
    }

    /* access modifiers changed from: protected */
    @Override
    public void onDraw(Canvas canvas) {
        boolean z2;
        float f2;
        Canvas canvas2 = canvas;
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        if (width > 0 && height > 0) {
            if (this.G == 0) {
                ViewParent parent = getParent();
                if (!(parent instanceof DynamicEffectCommonLayout)) {
                    parent = parent.getParent();
                }
                View artView = ((DynamicEffectCommonLayout) parent).getArtView();
                this.G = (artView != null ? Math.max(artView.getWidth(), artView.getHeight()) / 2 : 0) + f9769e;
                this.J = Math.min(width, height);
                this.H = Math.min((this.J - this.G) - UiUtils.dp2px(10.0f), f9771g);
                this.I = this.G + UiUtils.dp2px(10.0f);
            }
            int save = canvas.save();
            canvas2.translate((float) width, (float) height);
            if (this.ai) {
                Paint paint = this.ae;
                float f3 = (float) this.I;
                int i2 = this.A;
                RadialGradient radialGradient = new RadialGradient(0.0f, 0.0f, f3, new int[]{i2, ColorUtils.setAlphaComponent(i2, 102), ColorUtils.setAlphaComponent(this.A, 0)}, new float[]{0.0f, (((float) (this.G - f9769e)) * 1.0f) / ((float) this.I), 1.0f}, Shader.TileMode.CLAMP);
                paint.setShader(radialGradient);
                int i3 = this.G;
                float f4 = (float) (this.H + i3);
                if (f4 <= 0.0f) {
                    this.G = 0;
                    return;
                }
                Paint paint2 = this.ag;
                RadialGradient radialGradient3 = new RadialGradient(0.0f, 0.0f, f4, new int[]{0, this.B, this.A}, new float[]{0.0f, ((float) (i3 - f9769e)) / f4, 1.0f}, Shader.TileMode.CLAMP);
                paint2.setShader(radialGradient3);
                this.ai = false;
            }
            canvas2.drawCircle(0.0f, 0.0f, (float) this.I, this.ae);
            long uptimeMillis = SystemClock.uptimeMillis();
            if (!this.f9779d.isEmpty()) {
                if (this.aj == null) {
                    this.aj = new Path();
                }
                Iterator<b> it = this.f9779d.iterator();
                float f5 = (float) (this.G - f9769e);
                float f6 = (float) (this.J + m);
                while (it.hasNext()) {
                    b next = it.next();
                    float f7 = (float) (uptimeMillis - next.f9781c);
                    if (f7 >= n) {
                        it.remove();
                        f2 = f5;
                    } else {
                        float f8 = f7 / n;
                        double d2 = (double) (((f6 - f5) * f8) + f5);
                        f2 = f5;
                        float cos = (float) ((Math.cos(next.f9780b) * d2) + 0.5d);
                        float sin = (float) ((d2 * Math.sin(next.f9780b)) + 0.5d);
                        this.aj.reset();
                        this.aj.moveTo(cos, sin);
                        this.aj.lineTo(next.f9782d + cos, next.f9783e + sin);
                        this.aj.lineTo(cos + next.f9784f, sin + next.f9785g);
                        this.aj.close();
                        this.af.setColor(ColorUtils.setAlphaComponent(this.A, (int) (((double) ((-31.0f * f8) + 31.0f)) + 0.5d)));
                        this.af.setStyle(Paint.Style.FILL);
                        canvas2.drawPath(this.aj, this.af);
                        this.af.setColor(ColorUtils.setAlphaComponent(this.A, (int) (((double) ((f8 * -153.0f) + 153.0f)) + 0.5d)));
                        this.af.setStyle(Paint.Style.STROKE);
                        canvas2.drawPath(this.aj, this.af);
                    }
                    f5 = f2;
                }
                z2 = true;
            } else {
                z2 = false;
            }
            if (this.K) {
                this.ah.setStrokeWidth((float) v);
                this.ah.setColor(this.A);
                canvas2.drawCircle(0.0f, 0.0f, (float) this.G, this.ah);
            } else {
                if (this.aj == null) {
                    this.aj = new Path();
                }
                if (this.ak == null) {
                    this.ak = new Path();
                }
                if (this.al == null) {
                    this.al = new CornerPathEffect((float) this.G);
                }
                this.ah.setPathEffect(this.al);
                float uptimeMillis2 = (float) (SystemClock.uptimeMillis() - this.V);
                float f9 = this.U;
                float f10 = uptimeMillis2 >= f9 ? 1.0f : uptimeMillis2 / f9;
                if (!z2) {
                    z2 = f10 < 1.0f;
                }
                this.ak.reset();
                int i4 = 0;
                while (true) {
                    float[] fArr = this.Q;
                    if (i4 >= fArr.length) {
                        break;
                    }
                    float[] fArr2 = this.R;
                    float max = Math.max(((float) this.G) - (fArr2[i4]/2 + ((fArr[i4]/2 - fArr2[i4]/2) * f10)), 0.0f);
                    double d3 = this.T * ((double) i4);
                    double d4 = (double) max;
                    float cos2 = (float) (Math.cos(d3) * d4);
                    float sin2 = (float) (d4 * Math.sin(d3));
                    if (i4 == 0) {
                        this.ak.moveTo(cos2, sin2);
                    } else {
                        this.ak.lineTo(cos2, sin2);
                    }
                    int i5 = i4 * 4;
                    float[] fArr3 = this.S;
                    fArr3[i5] = cos2;
                    fArr3[i5 + 1] = sin2;
                    i4++;
                }
                this.ak.close();
                this.aj.reset();
                int i6 = 0;
                while (true) {
                    float[] fArr4 = this.N;
                    if (i6 >= fArr4.length) {
                        break;
                    }
                    float[] fArr5 = this.O;
                    double d5 = this.T * ((double) i6);
                    double d6 = (double) (((float) this.G) + fArr5[i6]/2 + ((fArr4[i6]/2 - fArr5[i6]/2) * f10));
                    float cos3 = (float) (Math.cos(d5) * d6);
                    float sin3 = (float) (d6 * Math.sin(d5));
                    if (i6 == 0) {
                        this.aj.moveTo(cos3, sin3);
                    } else {
                        this.aj.lineTo(cos3, sin3);
                    }
                    int i7 = (i6 * 4) + 2;
                    float[] fArr6 = this.S;
                    fArr6[i7] = cos3;
                    fArr6[i7 + 1] = sin3;
                    i6++;
                }
                this.aj.close();
                canvas2.drawLines(this.S, this.ag);
                this.ah.setStrokeWidth((float) u);
                this.ah.setColor(this.B);
                canvas2.drawPath(this.ak, this.ah);
                this.ah.setColor(this.A);
                canvas2.drawPath(this.aj, this.ah);
            }
            canvas2.restoreToCount(save);
            if (z2) {
                postInvalidateOnAnimation();
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override
    public void onDetachedFromWindow() {
        this.am.getLooper().quit();
        this.an.removeCallbacksAndMessages((Object) null);
        super.onDetachedFromWindow();
    }

    /* compiled from: ProGuard */
    private static class b extends ListNode<b> {

        /* renamed from: b  reason: collision with root package name */
        double f9780b;

        /* renamed from: c  reason: collision with root package name */
        long f9781c;

        /* renamed from: d  reason: collision with root package name */
        float f9782d;

        /* renamed from: e  reason: collision with root package name */
        float f9783e;

        /* renamed from: f  reason: collision with root package name */
        float f9784f;

        /* renamed from: g  reason: collision with root package name */
        float f9785g;

        b(double d2, long j, float f2, float f3, float f4, float f5) {
            this.f9780b = d2;
            this.f9781c = j;
            this.f9782d = f2;
            this.f9783e = f3;
            this.f9784f = f4;
            this.f9785g = f5;
        }
    }



}
