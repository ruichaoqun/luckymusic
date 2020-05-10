package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;

import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.LogUtils;
import com.ruichaoqun.luckymusic.utils.UiUtils;

/**
 * 跳动旋律动效
 */
public class BounceMelodyEffectView extends View implements DynamicEffectView {

    private int length;
    private int samplingRate;
    private int circleRadius;
    private static final int maxBounceDist = UiUtils.dp2px(83.0f);
    private int bounceDist;

    private float typ;
    private int t;
    private int u;

    private double[] w;
    private float[] x;
    private float[] v;

    private float[] y;
    private float[] z;


    private double A;

    private Handler fftHandler;
    private Handler mHandler;
    private boolean showSimple = true;

    protected float[] outHsl = new float[3];

    private int originalColor = Color.WHITE;
    private Paint mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int[] colors;
    private Path mPath;

    private long lastUpdateTimes;


    public BounceMelodyEffectView(Context context) {
        super(context);
        colors = getColors(originalColor);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setColor(colors[0]);
        mPaint2.setStyle(Paint.Style.STROKE);
        this.mPaint1.setStrokeWidth(UiUtils.dp2px(1.5f));
        this.mPaint2.setStrokeWidth(UiUtils.dp2px(1.5f));


        HandlerThread handlerThread = new HandlerThread("CurveRender");
        handlerThread.start();
        this.fftHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                byte[] fft = (byte[]) msg.obj;
                curveRender(fft,msg.arg1,msg.arg2);
            }
        };

        this.mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                updateData();
            }
        };
    }

    private void updateData() {
        System.arraycopy(y,0,z,0,y.length);
        System.arraycopy(v,0,y,0,w.length);
        this.lastUpdateTimes = SystemClock.uptimeMillis();
        showSimple = false;
        invalidate();
    }

    @Override
    public void setColor(int color) {
        if (this.originalColor != color) {
            this.originalColor = color;
            this.colors = getColors(color);
            this.mPaint1.setColor(this.colors[0]);
            invalidate();
        }
    }

    public int[] getColors(int color){
        if (color == 0) {
            return new int[]{0, 0, 0, 0};
        }
        int a = ColorUtil.getEffectColor(color, this.outHsl);
        int b = ColorUtil.b(a, this.outHsl);
        return new int[]{ColorUtils.setAlphaComponent(a, 179), ColorUtils.setAlphaComponent(b, 128), ColorUtils.setAlphaComponent(a, 77), ColorUtils.setAlphaComponent(b, 51)};
    }


    @Override
    public void onFftDataCapture(byte[] fft, int samplingRate) {//1024  44100000
        int length = fft.length;
        if(length > 0){
            if(this.length != length || this.samplingRate != samplingRate){
                this.length = length;
                this.samplingRate = samplingRate;
                float f = (samplingRate / 1000.0f) / length;  //43
                this.t = (int) Math.ceil(1300.0f / f);  //30
                int min = (Math.min((int) (4800.0f / f), (length / 2) - 1) - this.t) + 1;//111
                this.u = min >= 55 ? min / 55 : -((int) Math.ceil((double) (55.0f / ((float) min))));// 2
            }
            if (this.w == null) {
                this.w = new double[55];
                this.x = new float[55];
                this.y = new float[55];
                this.z = new float[55];
                this.v = new float[55];
                this.A = 0.11423973285781065d;
            }
            if(circleRadius > 0){
                fftHandler.sendMessage(fftHandler.obtainMessage(1,this.t,this.u,fft));
            }
        }
    }

    private void curveRender(byte[] fft,int m,int n){
        LogUtils.w("m-->"+m+"   n-->"+n);
        int k;
        double d2 = 0.0d;
        StringBuilder builder = new StringBuilder();
        builder.append("原始数据");
        for (int i = 0; i < this.x.length; i++) {
            k = i*n;
            int d = (k+m)*2;
            double pow = Math.pow((double) fft[d], 2.0d);
            double pow2 = Math.pow((double) fft[d + 1], 2.0d);
            double a3 = Math.log10(1+pow+pow2)*5d;
            this.w[i] = (float) a3;
            if (a3 > d2) {
                d2 = a3;
            }
            builder.append(a3);
            builder.append("  ");
        }
        Log.w("SSSSS","d2-->"+d2);
//        Log.w("SSSSS",builder.toString());
        StringBuilder builder1 = new StringBuilder();
        builder1.append("转换数据");
        double d3 = d2 * 0.8d;
        for (int i6 = 0; i6 < this.x.length; i6++) {
            double d4 = w[i6] > d3 ? w[i6] : 0.0d;
            int i7 = this.bounceDist;
            x[i6] = (float) Math.min((d4 / 60.0d) * ((double) i7), (double) i7);

        }

        for (int i8 = 0; i8 < v.length; i8++) {
            float[] fArr2 = {-0.08571429f, 0.34285715f, 0.4857143f, 0.34285715f, -0.08571429f};
            int length = i8 - (fArr2.length / 2);
            int length2 = fArr2.length;
            int i4 = 0;
            float f2 = 0.0f;
            int i5 = 0;
            while (i4 < length2) {
                float f3 = fArr2[i4];
                int i6 = i5 + 1;
                int i7 = i5 + length;
                int length3 = x.length;
                if (i7 < 0) {
                    i7 += length3;
                } else if (i7 > length3 - 1) {
                    i7 -= x.length;
                }
                f2 += f3 * x[i7];
                i4++;
                i5 = i6;
            }
            v[i8] = Math.max(f2, 0.0f);
            builder1.append(v[i8]);
            builder1.append("  ");
        }

//        Log.w("SSSSS",builder1.toString());
        mHandler.sendEmptyMessage(0);
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
        int rate = (int) (visualizerEntity.getMaxCaptureRate() * 0.85d);
        this.typ = 1000000.0f /rate;
        return rate;
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
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        boolean needInvalidate = false;
        if (this.circleRadius == 0) {
            View artView = ((DynamicEffectCommonLayout) getParent()).getArtView();
            this.circleRadius = (artView != null ? Math.max(artView.getWidth(), artView.getHeight()) / 2 : 0) + UiUtils.dp2px(15.0f);
            this.bounceDist = Math.min((Math.min(width, height) + UiUtils.dp2px(30.0f)) - this.circleRadius, maxBounceDist);
        }
        int save = canvas.save();
        canvas.translate(width, height);
        if(this.showSimple){
            canvas.drawCircle(0,0,circleRadius,mPaint1);
        }else{
            if(mPath == null){
                mPath = new Path();
                mPaint2.setPathEffect(new CornerPathEffect(circleRadius));
            }
            float uptimeMillis = (float) (SystemClock.uptimeMillis() - this.lastUpdateTimes);
            float rate = uptimeMillis >= typ ? 1.0f : uptimeMillis / typ;
            needInvalidate = rate < 1.0f;
            for (int i2 = 0; i2 < 4; i2++) {
                this.mPath.reset();
                int i3 = 0;
                while (true) {
                    float[] fArr = this.y;
                    if (i3 >= fArr.length) {
                        break;
                    }
                    float[] fArr2 = this.z;
                    double d2 = this.A * ((double) i3);
                    double d3 = (double) (((float) this.circleRadius) + ((fArr2[i3] + ((fArr[i3] - fArr2[i3]) * rate)) * (1.0f - (((float) i2) * 0.2f))));
                    float cos = (float) (Math.cos(d2) * d3);
                    float sin = (float) (d3 * Math.sin(d2));
                    if (i3 == 0) {
                        this.mPath.moveTo(cos, sin);
                    } else {
                        this.mPath.lineTo(cos, sin);
                    }
                    i3++;
                }
                this.mPath.close();
                this.mPaint2.setColor(this.colors[i2]);
//                if (i2 == 0) {
//                    if (EffectCache(canvas)) {
//                        z3 = true;
//                    }
//                } else if (i2 == this.f9704a - 1) {
//                    canvas.rotate(2.0f);
//                }
                canvas.drawPath(this.mPath, this.mPaint2);
            }
        }
        canvas.restoreToCount(save);
        if(needInvalidate){
            postInvalidateOnAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        fftHandler.getLooper().quit();
        mHandler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }
}
