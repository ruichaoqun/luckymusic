package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.SystemClock;
import android.util.Pair;
import android.view.View;

import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.Arrays;

/**
 * @author Rui Chaoqun
 * @date :2020/4/3 15:10
 * description:音乐射线动效
 */
public class MusicRadialEffectView extends View implements DynamicEffectView {

    /* renamed from: a  reason: collision with root package name */
    private static final int f9756a = 800;

    /* renamed from: b  reason: collision with root package name */
    private static final int f9757b = 4800;

    /* renamed from: c  reason: collision with root package name */
    private static final int f9758c = 45;

    /* renamed from: d  reason: collision with root package name */
    private static final int f9759d = UiUtils.dp2px(85.0f);

    /* renamed from: e  reason: collision with root package name */
    private static final int f9760e = UiUtils.dp2px(3.0f);

    /* renamed from: f  reason: collision with root package name */
    private static final int f9761f = UiUtils.dp2px(30.0f);

    /* renamed from: g  reason: collision with root package name */
    private static final int f9762g = UiUtils.dp2px(2.33f);
    private float[] A;
    private double B;
    private float C;
    private long D;
    private Paint E = new Paint(1);
    private Paint F = new Paint(1);

    /* renamed from: h  reason: collision with root package name */
    private int f9763h = -1;

    /* renamed from: i  reason: collision with root package name */
    private float[] f9764i = new float[3];
    private int j = ColorUtil.getEffectColor(this.f9763h, this.f9764i);
    private boolean k = true;
    private int l;
    private int m;
    private int n;
    private int q;
    private int r;
    private int s;
    private int t;
    private boolean u = true;
    private float[] v;
    private float[] w;
    private float[] x;
    private float[] y;
    private float[] z;

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

    public MusicRadialEffectView(Context context) {
        super(context);
        this.E.setStrokeWidth((float) f9760e);
        this.E.setStrokeCap(Paint.Cap.ROUND);
        this.F.setStrokeWidth((float) f9762g);
        this.F.setStrokeCap(Paint.Cap.ROUND);
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
        this.C = 1000000.0f / ((float) d2);
        return d2;
    }

    @Override
    public void setColor(int i2) {
        if (this.f9763h != i2) {
            this.f9763h = i2;
            this.j = ColorUtil.getEffectColor(this.f9763h, this.f9764i);
            this.k = true;
            invalidate();
        }
    }

    @Override
    public void onFftDataCapture(byte[] obj, int i2) {
        int a2 = obj.length;
        if (a2 > 0) {
            int i3 = 0;
            if (!(this.l == a2 && this.m == i2)) {
                this.l = a2;
                this.m = i2;
                float f2 = (((float) i2) / 1000.0f) / ((float) this.l);
                this.n = (int) Math.ceil((double) (800.0f / f2));
                this.q = Math.min((int) (4800.0f / f2), (a2 / 2) - 1);
                this.t = 0;
            }
            if (this.r > 0) {
                if (this.t == 0) {
                    int i4 = (this.q - this.n) + 1;
                    float[] fArr = this.v;
                    int length = fArr.length;
                    int length2 = fArr.length;
                    this.t = i4 >= length ? i4 / length2 : -((int) Math.ceil((double) (((float) length2) / ((float) i4))));
                }
                this.u = c(obj);
                if (!this.u) {
                    float[] fArr2 = this.w;
                    System.arraycopy(fArr2, 0, this.x, 0, fArr2.length);
                    for (int i5 = 0; i5 < this.v.length; i5++) {
                        int i6 = this.t;
                        double a3 = k(obj, (this.n + (i6 > 0 ? i6 * i5 : i5 / (-i6))) * 2);
                        float[] fArr3 = this.v;
                        int i7 = this.s;
                        fArr3[i5] = (float) Math.min((a3 / 45.0d) * ((double) i7), (double) i7);
                    }
                    while (true) {
                        float[] fArr4 = this.w;
                        if (i3 >= fArr4.length) {
                            break;
                        }
                        fArr4[i3] = q(this.v, i3, 9);
                        i3++;
                    }
                    this.D = SystemClock.uptimeMillis();
                } else {
                    Arrays.fill(this.w, 0.0f);
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

    static int a(int i2, float f2, float[] fArr) {
        int alpha = Color.alpha(i2);
        ColorUtils.colorToHSL(i2, fArr);
        fArr[0] = (fArr[0] + f2) % 360.0f;
        return ColorUtils.setAlphaComponent(ColorUtils.HSLToColor(fArr), alpha);
    }


    private void e() {
        int i2 = this.j;
        SweepGradient sweepGradient = new SweepGradient(0.0f, 0.0f, new int[]{i2, a(i2, 50.0f, this.f9764i), a(this.j, 100.0f, this.f9764i), this.j}, new float[]{0.0f, 0.5f, 0.75f, 1.0f});
        this.E.setShader(sweepGradient);
        float f2 = (float) (this.r + this.s);
        if (Build.VERSION.SDK_INT >= 28) {
            int[] iArr = {-1, ColorUtils.setAlphaComponent(-1, 127), ColorUtils.setAlphaComponent(-1, 25), ColorUtils.setAlphaComponent(-1, 0)};
            int i3 = this.r;
            RadialGradient radialGradient = new RadialGradient(0.0f, 0.0f, f2, iArr, new float[]{0.0f, ((float) i3) / f2, ((float) (i3 + f9761f)) / f2, 1.0f}, Shader.TileMode.CLAMP);
            this.F.setShader(new ComposeShader(radialGradient, sweepGradient, PorterDuff.Mode.MULTIPLY));
            return;
        }
        Paint paint = this.F;
        int i4 = this.j;
        int[] iArr2 = {i4, ColorUtils.setAlphaComponent(i4, 127), ColorUtils.setAlphaComponent(this.j, 25), ColorUtils.setAlphaComponent(this.j, 0)};
        int i5 = this.r;
        paint.setShader(new RadialGradient(0.0f, 0.0f, f2, iArr2, new float[]{0.0f, ((float) i5) / f2, ((float) (i5 + f9761f)) / f2, 1.0f}, Shader.TileMode.CLAMP));
    }

    /* access modifiers changed from: protected */
    @Override
    public void onDraw(Canvas canvas) {
        boolean z2;
        int i2;
        int i3;
        Canvas canvas2 = canvas;
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        int i4 = 0;
        if (this.r == 0) {
            View artView = ((DynamicEffectCommonLayout) getParent()).getArtView();
            if (artView == null) {
                i3 = 0;
            } else {
                i3 = Math.max(artView.getWidth(), artView.getHeight()) / 2;
            }
            this.r = i3 + UiUtils.dp2px(12.0f);
            this.s = Math.min((Math.min(width, height) + UiUtils.dp2px(20.0f)) - this.r, f9759d);
            int i5 = (int) ((((double) this.r) * 6.283185307179586d) / ((double) (f9760e * 2)));
            this.v = new float[i5];
            this.w = new float[i5];
            this.x = new float[i5];
            this.y = new float[(i5 * 2)];
            int i6 = i5 * 4;
            this.z = new float[i6];
            this.A = new float[i6];
            this.B = 6.283185307179586d / ((double) i5);
        }
        int saveCount = canvas.getSaveCount();
        canvas2.translate((float) width, (float) height);
        if (this.k) {
            e();
            this.k = false;
        }
        if (!this.u) {
            float uptimeMillis = (float) (SystemClock.uptimeMillis() - this.D);
            float f2 = this.C;
            float f3 = uptimeMillis >= f2 ? 1.0f : uptimeMillis / f2;
            boolean z3 = f3 < 1.0f;
            while (true) {
                float[] fArr = this.w;
                if (i4 >= fArr.length) {
                    break;
                }
                float[] fArr2 = this.x;
                float f4 = ((float) this.r) + fArr2[i4]/2 + ((fArr[i4]/2 - fArr2[i4]/2) * f3);
                double d2 = this.B * ((double) i4);
                double cos = Math.cos(d2);
                double sin = Math.sin(d2);
                double d3 = (double) f4;
                int i7 = saveCount;
                float f5 = (float) (d3 * cos);
                float f6 = (float) (d3 * sin);
                int i8 = i4 * 2;
                float[] fArr3 = this.y;
                fArr3[i8] = f5;
                fArr3[i8 + 1] = f6;
                int i9 = i4 * 4;
                int i10 = this.r;
                boolean z4 = z3;
                float f7 = (float) (((double) i10) * cos);
                float f8 = (float) (((double) i10) * sin);
                float[] fArr4 = this.z;
                fArr4[i9] = f7;
                int i11 = i9 + 1;
                fArr4[i11] = f8;
                int i12 = i9 + 2;
                fArr4[i12] = f5;
                int i13 = i9 + 3;
                fArr4[i13] = f6;
                float[] fArr5 = this.A;
                fArr5[i9] = f7;
                fArr5[i11] = f8;
                double d4 = (double) (f4 + ((float) f9761f));
                fArr5[i12] = (float) (cos * d4);
                fArr5[i13] = (float) (d4 * sin);
                i4++;
                z3 = z4;
                saveCount = i7;
            }
            z2 = z3;
            canvas2.drawLines(this.A, this.F);
            canvas2.drawLines(this.z, this.E);
            canvas2.drawPoints(this.y, this.E);
            i2 = saveCount;
        } else {
            int i14 = saveCount;
            for (int i15 = 0; i15 < this.v.length; i15++) {
                int i16 = i15 * 2;
                double d5 = this.B * ((double) i15);
                double cos2 = Math.cos(d5);
                double sin2 = Math.sin(d5);
                int i17 = this.r;
                float f9 = (float) (((double) i17) * cos2);
                float f10 = (float) (((double) i17) * sin2);
                float[] fArr6 = this.y;
                fArr6[i16] = f9;
                fArr6[i16 + 1] = f10;
                int i18 = i15 * 4;
                float[] fArr7 = this.A;
                fArr7[i18] = f9;
                fArr7[i18 + 1] = f10;
                double d6 = (double) ((float) (i17 + f9761f));
                fArr7[i18 + 2] = (float) (cos2 * d6);
                fArr7[i18 + 3] = (float) (d6 * sin2);
            }
            canvas2.drawLines(this.A, this.F);
            canvas2.drawPoints(this.y, this.E);
            i2 = i14;
            z2 = false;
        }
        canvas2.restoreToCount(i2);
        if (z2) {
            postInvalidateOnAnimation();
        }
    }

}
