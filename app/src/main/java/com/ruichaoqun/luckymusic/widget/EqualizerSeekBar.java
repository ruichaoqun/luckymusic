package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.utils.UiUtils;

public class EqualizerSeekBar extends View {
    /* renamed from: a  reason: collision with root package name */
    public static final int f15010a = UiUtils.dp2px(1.33f);

    /* renamed from: b  reason: collision with root package name */
    public static final int f15011b = UiUtils.dp2px(2.0f);

    /* renamed from: c  reason: collision with root package name */
    public static final int f15012c = UiUtils.dp2px(1.0f);

    /* renamed from: d  reason: collision with root package name */
    public static final int f15013d = UiUtils.dp2px(12.5f);

    /* renamed from: e  reason: collision with root package name */
    public static final int f15014e = UiUtils.dp2px(16.0f);

    /* renamed from: f  reason: collision with root package name */
    public static final int f15015f = 100;

    /* renamed from: g  reason: collision with root package name */
    private static final int f15016g = UiUtils.dp2px(17.0f);

    /* renamed from: h  reason: collision with root package name */
    private static final int f15017h = UiUtils.dp2px(23.3f);

    /* renamed from: i  reason: collision with root package name */
    private static final int f15018i = UiUtils.dp2px(2.67f);
    private static final int j = UiUtils.dp2px(8.33f);
    private static final int k = UiUtils.dp2px(2.67f);
    private static final int l = UiUtils.dp2px(1.67f);
    private static final int m = UiUtils.dp2px(4.67f);
    private static final int n = UiUtils.dp2px(4.5f);
    private static final int o = UiUtils.dp2px(12.0f);
    private static final int p = 1;
    private static final int q = UiUtils.dp2px(2.0f);
    private static final int r = UiUtils.dp2px(1.0f);
    private static final int mLargeLineWidth = UiUtils.dp2px(14.0f);
    private static final int mSmallLineWidth = UiUtils.dp2px(7.0f);
    private static final int u = UiUtils.dp2px(30.0f);
    private static final int v = ((u + (f15013d * 2)) + r);
    private static final int w = 16;
    private static final int x = -1200;
    private static final int y = 1200;
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private int I;
    private Paint mBackgroundLinePaint;
    private Paint K;
    private Paint L;
    private Paint M;
    private Paint N;
    private Paint O;
    private Paint P;
    private Paint Q;
    private Path R = new Path();
    private int S = 0;
    private float mTouchX;
    private float mTouchY;
    private String V;
    private b W;
    private OnDragFinishListener mDragFinishListener;
    private boolean mScrollEnable = false;
    private boolean ac = true;
    private boolean ad = false;
    private int z;

    /* compiled from: ProGuard */
    public interface OnDragFinishListener {
        void OnDragFinish();
    }

    /* compiled from: ProGuard */
    public interface b {
        void a(String str, int i2);
    }

    private int getRange() {
        return 2400;
    }

    public EqualizerSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public void setEqualizerOpen(boolean z2) {
        this.ac = z2;
        b();
        invalidate();
    }

    private void init() {
        this.mBackgroundLinePaint = new Paint();
        this.mBackgroundLinePaint.setAntiAlias(true);
        this.mBackgroundLinePaint.setStrokeWidth((float) f15012c);
        this.mBackgroundLinePaint.setStyle(Paint.Style.FILL);
        this.mBackgroundLinePaint.setStrokeCap(Paint.Cap.ROUND);
        this.K = new Paint();
        this.K.setAntiAlias(true);
        this.K.setStrokeWidth((float) f15011b);
        this.K.setStyle(Paint.Style.FILL);
        this.K.setStrokeCap(Paint.Cap.ROUND);
        this.N = new Paint();
        this.N.setAntiAlias(true);
        this.N.setStrokeWidth((float) q);
        this.N.setStyle(Paint.Style.STROKE);
        this.O = new Paint();
        this.O.setAntiAlias(true);
        this.O.setStrokeWidth(1.0f);
        this.O.setStyle(Paint.Style.STROKE);
        this.L = new Paint();
        this.L.setAntiAlias(true);
        this.L.setStyle(Paint.Style.FILL);
        this.M = new Paint();
        this.M.setAntiAlias(true);
        this.M.setStrokeWidth((float) f15010a);
        this.M.setStyle(Paint.Style.FILL);
        this.M.setStrokeCap(Paint.Cap.ROUND);
        this.P = new Paint();
        this.P.setAntiAlias(true);
        this.P.setStyle(Paint.Style.FILL);
        this.Q = new Paint();
        this.Q.setTextSize((float) UiUtils.sp2px(11.0f));
        this.Q.setTextAlign(Paint.Align.CENTER);
        b();
    }

    private void b() {
        ResourceRouter instance = ResourceRouter.getInstance();
        if (!instance.isNightTheme()) {
            this.E = -1;
            this.F = -1118482;
            if (instance.isCustomBgTheme() || !instance.isInternalTheme()) {
                this.z = 654311423;
            } else {
                this.z = com.netease.play.customui.b.a.au;
            }
            if (instance.isCustomBgOrDarkThemeWhiteColor()) {
                this.G = c.bn;
                this.A = com.netease.play.customui.b.a.N;
                this.B = -1;
                this.H = -1;
                this.I = -16777216;
            } else {
                this.G = instance.getColorByDefaultColor(c.f54806a);
                this.B = instance.getThemeColor();
                this.A = (this.B & 16777215) | com.netease.play.customui.b.a.ah;
                this.H = this.G;
                this.I = -1;
            }
            if (!this.ac) {
                this.A = instance.getColorByDefaultColor(com.netease.play.customui.b.a.at);
            }
        } else {
            this.E = -13093063;
            this.G = c.s;
            this.F = -14277080;
            this.B = c.s;
            this.z = 234881023;
            this.H = c.s;
            this.I = -1;
            if (this.ac) {
                this.A = -2136654281;
            } else {
                this.A = com.netease.play.customui.b.a.Z;
            }
        }
        this.D = com.netease.play.customui.b.a.at;
        this.C = com.netease.play.customui.b.a.at;
        if (instance.isGeneralRuleTheme()) {
            this.ad = true;
        } else {
            this.ad = false;
        }
        this.mBackgroundLinePaint.setColor(this.z);
        this.K.setColor(this.A);
        this.N.setColor(this.C);
        this.O.setColor(this.D);
        this.L.setColor(this.E);
        this.P.setColor(this.H);
        this.M.setColor(this.F);
        this.Q.setColor(this.I);
    }

    public void setOnDragFinishListener(OnDragFinishListener listener) {
        this.mDragFinishListener = listener;
    }

    public void a(String str, b bVar) {
        this.W = bVar;
        this.V = str;
    }

    public void setProgress(int i2) {
        this.S = i2;
        invalidate();
    }

    private boolean isTouchedInThumb(float f2, float f3) {
        float abs = Math.abs(getThumbCx() - f2);
        float abs2 = Math.abs(getThumbCy() - f3);
        return Math.sqrt((double) ((abs * abs) + (abs2 * abs2))) < ((double) f15014e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int height = getHeight();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            this.mTouchX = x;
            this.mTouchY = y;
            if (!isTouchedInThumb(x, y)) {
                this.mScrollEnable = false;
                getParent().requestDisallowInterceptTouchEvent(false);
                return super.onTouchEvent(motionEvent);
            }
            this.mScrollEnable = true;
        } else if (action == MotionEvent.ACTION_UP) {
            if (this.mScrollEnable && this.mDragFinishListener != null) {
                mDragFinishListener.OnDragFinish();
            }
            this.mScrollEnable = false;
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (getAngleY(this.mTouchX, this.mTouchY, x, y) >= 60 && Math.abs(this.mTouchY - y) >= ((float) ViewConfiguration.get(getContext()).getScaledTouchSlop())) {
                getParent().requestDisallowInterceptTouchEvent(true);
                this.mScrollEnable = true;
            }
            int i2 = v;
            if (y < ((float) i2)) {
                y = (float) i2;
            } else {
                float f2 = (float) height;
                if (y > f2) {
                    y = f2;
                }
            }
            int i3 = v;
            this.S = (int) (((1.0f - ((y - ((float) i3)) / ((float) (height - i3)))) * ((float) getRange())) - 0.0035705566f);
            b bVar = this.W;
            if (bVar != null) {
                bVar.a(this.V, this.S);
            }
        } else if (action == 3) {
            this.mScrollEnable = false;
        }
        invalidate();
        return true;
    }

    private int getAngleY(float x1, float y1, float x2, float y2) {
        float a = Math.abs(x1 - x2);
        float b = Math.abs(y1 - y2);
        return Math.round((float) (((Math.asin(b / (Math.sqrt(a * a + b * b)))) / Math.PI) * 180d));
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(0.0f, (float) u);
        drawBackgroundLine(canvas);
        c(canvas);
        canvas.restore();
        b(canvas);
        if (this.mScrollEnable) {
            a(canvas);
        }
    }

    private void a(Canvas canvas) {
        this.R.reset();
        float thumbCx = getThumbCx();
        float thumbCy = (getThumbCy() - ((float) f15013d)) - ((float) k);
        int i2 = j;
        float f2 = thumbCx - ((float) (i2 / 2));
        float f3 = thumbCy - ((float) f15018i);
        int i3 = f15017h;
        float f4 = f3 - ((float) f15016g);
        Path path = this.R;
        RectF rectF = new RectF(thumbCx - ((float) (i3 / 2)), f4, ((float) (i3 / 2)) + thumbCx, f3);
        int i4 = l;
        path.addRoundRect(rectF, (float) i4, (float) i4, Path.Direction.CCW);
        this.R.moveTo(thumbCx, thumbCy);
        this.R.lineTo(f2, f3);
        this.R.lineTo(((float) i2) + f2, f3);
        this.R.lineTo(thumbCx, thumbCy);
        canvas.drawPath(this.R, this.P);
        canvas.drawText((this.S / 100) + "", thumbCx, f3 - ((float) m), this.Q);
    }

    private void b(Canvas canvas) {
        int width = getWidth();
        float thumbCx = getThumbCx();
        float thumbCy = getThumbCy();
        canvas.drawCircle(thumbCx, ((float) r) + thumbCy, (float) (f15013d - (q / 2)), this.N);
        canvas.drawCircle(thumbCx, thumbCy, (float) f15013d, this.L);
        if (this.ad) {
            canvas.drawCircle(thumbCx, thumbCy, (float) f15013d, this.O);
        }
        int i2 = o;
        float f2 = (float) ((width - i2) / 2);
        float f3 = ((float) i2) + f2;
        if (this.mScrollEnable) {
            this.M.setColor(this.G);
        } else {
            this.M.setColor(this.F);
        }
        for (int i3 = -1; i3 < 2; i3++) {
            float f4 = thumbCy + ((float) (n * i3));
            canvas.drawLine(f2, f4, f3, f4, this.M);
        }
    }

    private float getThumbCy() {
        float ratio = getRatio();
        int height = getHeight();
        int i2 = v;
        return (float) (((int) (((((float) (height - i2)) * ratio) + ((float) i2)) - ((float) r))) - f15013d);
    }

    private float getThumbCx() {
        return (float) (getWidth() / 2);
    }

    private void c(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float strokeWidth = this.K.getStrokeWidth() + (((float) (height - v)) * getRatio());
        this.K.setColor(this.z);
        float f2 = (float) (width / 2);
        canvas.drawLine(f2, this.K.getStrokeWidth(), f2, strokeWidth, this.K);
        if (this.mScrollEnable) {
            this.K.setColor(this.B);
        } else {
            this.K.setColor(this.A);
        }
        canvas.drawLine(f2, strokeWidth, f2, (float) (height - 85), this.K);
    }

    private float getRatio() {
        return 1.0f - (((float) (this.S + 1200)) / ((float) getRange()));
    }

    private void drawBackgroundLine(Canvas canvas) {
        int width = getWidth();
        float height = getHeight() / 16.0f;
        for (int i = 1; i <= 15; i++) {
            float lineWidth = (float) (i % 2 == 0 ? mLargeLineWidth : mSmallLineWidth);
            float y = i * height;
            float lineLeftX = (width - lineWidth) / 2.0f;
            canvas.drawLine(lineLeftX, y, lineLeftX + lineWidth, y, this.mBackgroundLinePaint);
        }
    }
}
