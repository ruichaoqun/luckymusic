package com.ruichaoqun.luckymusic.widget.effect;

import android.annotation.SuppressLint;
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

/**
 * 宇宙尘埃动效
 */
public class CosmicDustEffectView extends View implements DynamicEffectView {
    private static final float w = (float) (Math.PI*2/30);
    private static final float t = -49.0f;
    private static final float n = 45.0f;
    private static final float s = 257.0f;
    private static final float r = 65.0f;





    private int mParticleRadius;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private EffectData<ParticleData> mData = new EffectData<>();

    private int mAlpha;
    private Random mRandom = new Random();

    private float[] mFfftRanges = new float[30];
    private int[] J = new int[30];
    private float[] K = new float[30];

    private int mColor = Color.WHITE;
    private float[] outHsl = new float[3];
    private int mEffectColor = ColorUtil.getEffectColor(this.mColor, this.outHsl);

    private int fftLength;
    private int samplingRate;

    private int k;
    private int mStep;
    private boolean o = true;
    private boolean p;



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getData(msg.what, msg.getWhen());
        }
    };


    public CosmicDustEffectView(Context context) {
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
                this.fftLength = length;//1024
                this.samplingRate = samplingRate;//44100000
                float f2 = (samplingRate / 1000.0f) / length;//43.066
                this.k = (int) Math.ceil((double) (800.0f / f2));//19
                int min = (Math.min((int) (4800.0f / f2), (length / 2) - 1) - this.k) + 1;//111
                this.mStep = min >= 30 ? min / 30 : -((int) Math.ceil((double) (30.0f / ((float) min))));//3
            }
            float maxRange = 0.0f;
            float sum = 0.0f;
            for (int i = 0; i < 30; i++) {
                float range = (float) getPhase(fft, (this.k + (mStep > 0 ? mStep * i : i / (-mStep))) * 2);//19+3*29
                this.mFfftRanges[i] = range;
                if (range > maxRange) {
                    maxRange = range;
                }
                sum += range;
            }
            for (int i = 0; i < 30; i++) {
                float range = this.mFfftRanges[i];
                float f6 = range / n;//range/45
                if (range < 5 && range == maxRange && maxRange > 0.0f) {
                    range = 5;
                }
                this.J[i] = (int) range;
                this.K[i] = (f6 * s) + r;
            }
            this.mAlpha = (int) (Math.min((sum / 30.0f) / 12.0f, 1.0f) * 127.0f);
            Log.w("SSSSS","mAlpha-->"+sum / 300);
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
        boolean isEmpty = mData.isEmpty();
        if (what == 0) {
            for (int i4 = 0; i4 < 70; i4++) {
                this.mData.addData(getData(mRandom.nextFloat() * 20.0f, 0.0f, (float) (((double) mRandom.nextFloat()) * Math.PI*2), Color.WHITE, 1500, when));
                this.mData.addData(getData(mRandom.nextFloat() * 20.0f+10.0f, 0.0f, (float) (((double) mRandom.nextFloat()) *Math.PI*2), mEffectColor, 1500, when));
            }
            this.mHandler.sendEmptyMessageDelayed(what, 70);
        } else {
            int i = 0;
            while (i < 30) {
                int j = 0;
                while (j < J[i]) {
                    float nextFloat = i* w + (this.mRandom.nextFloat() * w);
                    this.mData.addData(getData((1 - (mRandom.nextFloat() * 0.5f)) * K[i], t, nextFloat, mEffectColor, 1000, when));
                    j++;
                }
                i++;
            }
            this.mHandler.sendEmptyMessageDelayed(what, 50);
        }
        if (isEmpty) {
            invalidate();
        }
    }

    private ParticleData getData(float distance, float velocity, float angle, int color, int survivalTime, long when) {
        ParticleData data = this.mData.getCacheData();
        if (data == null) {
            return new ParticleData(distance, velocity, angle, color, survivalTime, when);
        }
        data.distance = distance;
        data.velocity = velocity;
        data.angle = angle;
        data.mColor = color;
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
        this.mAlpha = 0;
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
            canvas.translate(width / 2, height / 2);
            long uptimeMillis = SystemClock.uptimeMillis();
            Iterator<ParticleData> iterator = mData.iterator();
            while (iterator.hasNext()) {
                ParticleData next = iterator.next();
                long time = uptimeMillis - next.timeMillis;
                if (time >=  next.survivalTime) {
                    iterator.remove();
                } else {
                    if (next.velocity == 0.0f) {
                        f2 = next.distance * ((float) time);
                    } else {
                        f2 = Math.max(30.0f,next.distance /2)* ((float) time);
                    }
                    double length = (double) (mParticleRadius + (f2 / 1000.0f));
                    float sin = (float) ((length * Math.sin((double) next.angle)) + 0.5d);
                    int min = Math.min(this.mAlpha + 128, 255);
                    this.mPaint.setColor(ColorUtils.setAlphaComponent(next.mColor, (int) (((float) min) - ((((float) (((long) min) * time)) * 1.0f) / ((float) next.survivalTime)))));
                    canvas.drawPoint((float) ((Math.cos((double) next.angle) * length) + 0.5d), sin, this.mPaint);
                }
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
        float distance;

        float velocity;

        float angle;

        int mColor;


        long timeMillis;
        long survivalTime;

        ParticleData(float distance, float velocity, float angle, int mColor, int survivalTime, long timeMillis) {
            this.distance = distance;
            this.velocity = velocity;
            this.angle = angle;
            this.mColor = mColor;
            this.survivalTime = survivalTime;
            this.timeMillis = timeMillis;
        }

    }
}
