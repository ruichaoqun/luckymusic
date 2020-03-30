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

import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Rui Chaoqun
 * @date :2020/3/30 11:34
 * description:
 */
public class TestEffectView extends View implements  DynamicEffectView {
    private static final float[] f11692d = {-0.08571429f, 0.34285715f, 0.4857143f, 0.34285715f, -0.08571429f};

    /* renamed from: e  reason: collision with root package name */
    private static final float[] f11693e = {-0.0952381f, 0.14285715f, 0.2857143f, 0.33333334f, 0.2857143f, 0.14285715f, -0.0952381f};

    /* renamed from: f  reason: collision with root package name */
    private static final float[] f11694f = {-0.09090909f, 0.060606062f, 0.16883117f, 0.23376623f, 0.25541127f, 0.23376623f, 0.16883117f, 0.060606062f, -0.09090909f};

    /* renamed from: a  reason: collision with root package name */
    private static final int f11695a = 800;

    /* renamed from: b  reason: collision with root package name */
    private static final int f11696b = 4800;

    /* renamed from: c  reason: collision with root package name */
    private static final int f11697c = 45;

    /* renamed from: d  reason: collision with root package name */
    private static final int f11698d = 45;

    /* renamed from: e  reason: collision with root package name */
    private static final float f11699e = 0.2f;

    /* renamed from: f  reason: collision with root package name */
    private static final int f11700f = UiUtils.dp2px(5.0f);

    /* renamed from: g  reason: collision with root package name */
    private static final int f11701g = UiUtils.dp2px(82.0f);

    /* renamed from: h  reason: collision with root package name */
    private static final int f11702h = 12;

    /* renamed from: i  reason: collision with root package name */
    private static final int f11703i = 2;
    private static final int j = 26;
    private static final float k = 2000.0f;
    private static final int l =UiUtils.dp2px(5.0f);
    private static final int m = UiUtils.dp2px(1.0f);
    private static final int n = 55;
    private static final int q = 0;
    private int A;
    private int B;
    private int C;
    private boolean D = true;
    private float[] E;
    private float[] F;
    private float[] G;
    private double H;
    private float I;
    private long J;
    private float K;
    private Random L = new Random();
    private Paint O = new Paint(1);
    private Paint P = new Paint(1);
    private Paint Q = new Paint(1);
    private Path R;
    private int r = -1;
    private float[] s = new float[3];
    private int t;
    private int[] u = a(this.r);
    private int v;
    private int w;
    private int x = -1;
    private int y;
    private int z;

    private EffectData<a> a = new EffectData<>();




    private float[] mFloats = new float[30];
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TestEffectView(Context context) {
        super(context);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        this.Q.setStyle(Paint.Style.STROKE);
        this.Q.setStrokeWidth((float) f11700f);
        this.Q.setColor(this.u[2]);

    }

    @Override
    public void setColor(int i2) {
        if (this.r != i2) {
            this.r = i2;
            this.u = a(i2);
            this.Q.setColor(this.u[2]);
            invalidate();
        }

    }

    private int[] a(int i2) {
        this.t = ColorUtil.getEffectColor(i2, this.s);
        return new int[]{ColorUtils.setAlphaComponent(this.t, 77), ColorUtils.setAlphaComponent(ColorUtil.b(this.t, this.s), 51), ColorUtils.setAlphaComponent(this.t, 30)};
    }


    @Override
    public void onFftDataCapture(byte[] fft, int i2) {
        int a2 = fft.length;
        if (a2 > 0) {
            if (!(this.v == a2 && this.w == i2)) {
                this.v = a2;
                this.w = i2;
                float f2 = (((float) i2) / 1000.0f) / ((float) this.v);
                this.x = (int) Math.ceil((double) (800.0f / f2));
                int min = (Math.min((int) (4800.0f / f2), (a2 / 2) - 1) - this.x) + 1;
                this.y = min >= 45 ? min / 45 : -((int) Math.ceil((double) (45.0f / ((float) min))));
                this.E = new float[45];
                this.F = new float[45];
                this.G = new float[45];
                this.H = 0.13962634015954636d;
            }
            if (this.z > 0) {
                this.D = k(fft);
                if (!this.D) {
                    float[] fArr = this.F;
                    int i3 = 0;
                    System.arraycopy(fArr, 0, this.G, 0, fArr.length);
                    double d2 = 0.0d;
                    for (int i4 = 0; i4 < this.E.length; i4++) {
                        int i5 = this.y;
                        double a3 = a(fft, (this.x + (i5 > 0 ? i5 * i4 : i4 / (-i5))) * 2);
                        d2 += a3;
                        float[] fArr2 = this.E;
                        int i6 = this.A;
                        fArr2[i4] = (float) Math.min((a3 / 45.0d) * ((double) i6), (double) i6);
                    }
                    while (true) {
                        float[] fArr3 = this.F;
                        if (i3 >= fArr3.length) {
                            break;
                        }
                        fArr3[i3] = a(this.E, i3, 5);
                        i3++;
                    }
                    this.J = SystemClock.uptimeMillis();
                    this.K = (float) (((double) this.K) + ((Math.max(Math.min(((d2 / ((double) this.E.length)) / 12.0d) * 26.0d, 26.0d), 2.0d) * ((double) this.I)) / 1000.0d));
                    float f3 = this.K;
                    if (f3 >= 1.0f) {
                        int i7 = (int) f3;
                        b(i7);
                        this.K -= (float) i7;
                    }
                } else {
                    Arrays.fill(this.F, 0.0f);
                }
                invalidate();
            }
        }

    }

    private void b(int i2) {
        long uptimeMillis = SystemClock.uptimeMillis();
        for (int i3 = 0; i3 < i2; i3++) {
            this.a.addData(a(this.L.nextDouble() * 6.283185307179586d, uptimeMillis));
        }
    }

    private a a(double d2, long j2) {
        a a2 = this.a.getCacheData();
        if (a2 == null) {
            return new a(d2, j2);
        }
        a2.f11704b = d2;
        a2.f11705c = j2;
        return a2;
    }



    boolean k(byte[] obj){
        for (byte b2 : obj) {
            if (b2 != 0) {
                return false;
            }
        }
        return true;

    }

    static double a(Object obj, int i2) {
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

    static float a(float[] fArr, int i2, int i3) {
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



    @Override
    public void onWaveFormDataCapture(byte[] waveform, int samplingRate) {

    }

    @Override
    public void reset(boolean close) {

    }

    @Override
    public void prepareToDynamic() {

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
        int d2 = (int) (((double) visualizerEntity.getMaxCaptureRate()) * 0.65d);
        this.I = 1000000.0f / ((float) d2);
        return d2;
    }

    @Override
    public Pair<Integer, Integer> getArtViewSize() {
        int width = UiUtils.dp2px(225.0f);
        return new Pair<>(width, width);
    }

    @Override
    public int getCaptureSize(VisualizerEntity visualizerEntity) {
        int[] c2 = visualizerEntity.getCaptureSizeRange();
        return Math.max(c2[0], c2[1] / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boolean z2;
        Iterator<a> it;
        Canvas canvas2 = canvas;
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        if (this.z == 0) {
            View artView = ((DynamicEffectCommonLayout) getParent()).getArtView();
            this.z = (artView != null ? Math.max(artView.getWidth(), artView.getHeight()) / 2 : 0) + f11700f;
            this.B = Math.min(width, height);
            this.A = Math.min((this.B + UiUtils.dp2px(20.0f)) - this.z, f11701g);
            this.C = this.z - (f11700f / 2);
        }
        int save = canvas.save();
        canvas2.translate((float) width, (float) height);
        long uptimeMillis = SystemClock.uptimeMillis();
        boolean z3 = true;
        if (!this.a.isEmpty()) {
            Iterator<a> it2 = this.a.iterator();
            while (it2.hasNext()) {
                a next = it2.next();
                float f2 = (float) (uptimeMillis - next.f11705c);
                if (f2 >= k) {
                    it2.remove();
                    it = it2;
                } else {
                    float f3 = f2 / k;
                    int i2 = (this.z - f11700f) - l;
                    double d2 = (double) (((float) i2) + (((float) (this.B - i2)) * f3));
                    it = it2;
                    float sin = (float) ((d2 * Math.sin(next.f11704b)) + 0.5d);
                    int i3 = l;
                    float f4 = ((float) i3) + (((float) (m - i3)) * f3);
                    this.O.setColor(ColorUtils.setAlphaComponent(this.t, (int) (((double) ((f3 * -55.0f) + 55.0f)) + 0.5d)));
                    canvas2.drawCircle((float) ((Math.cos(next.f11704b) * d2) + 0.5d), sin, f4, this.O);
                }
                it2 = it;
            }
            z2 = true;
        } else {
            z2 = false;
        }
        if (!this.D) {
            if (this.R == null) {
                this.R = new Path();
                this.P.setPathEffect(new CornerPathEffect((float) this.z));
            }
            float f5 = (float) (uptimeMillis - this.J);
            float f6 = this.I;
            float f7 = f5 >= f6 ? 1.0f : f5 / f6;
            if (!z2) {
                if (f7 >= 1.0f) {
                    z3 = false;
                }
                z2 = z3;
            }
            for (int i4 = 0; i4 < 3; i4++) {
                this.R.reset();
                int i5 = 0;
                while (true) {
                    float[] fArr = this.F;
                    if (i5 >= fArr.length) {
                        break;
                    }
                    float[] fArr2 = this.G;
                    double d3 = this.H * ((double) i5);
                    double d4 = (double) (((float) this.z) + ((fArr2[i5] + ((fArr[i5] - fArr2[i5]) * f7)) * (1.0f - (((float) i4) * 0.2f))));
                    float cos = (float) (Math.cos(d3) * d4);
                    float sin2 = (float) (d4 * Math.sin(d3));
                    if (i5 == 0) {
                        this.R.moveTo(cos, sin2);
                    } else {
                        this.R.lineTo(cos, sin2);
                    }
                    i5++;
                }
                this.R.close();
                this.P.setColor(this.u[2 - i4]);
                canvas2.drawPath(this.R, this.P);
                canvas2.rotate(120.0f);
            }
        } else {
            canvas2.drawCircle(0.0f, 0.0f, (float) this.C, this.Q);
        }
        canvas2.restoreToCount(save);
        if (z2) {
            postInvalidateOnAnimation();
        }

    }


    class a extends ListNode<a> {

        /* renamed from: b  reason: collision with root package name */
        double f11704b;

        /* renamed from: c  reason: collision with root package name */
        long f11705c;

        a(double d2, long j) {
            this.f11704b = d2;
            this.f11705c = j;
        }
    }

}
