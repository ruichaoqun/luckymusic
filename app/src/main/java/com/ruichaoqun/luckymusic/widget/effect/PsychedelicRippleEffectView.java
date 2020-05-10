package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.Iterator;
import java.util.Random;

/**
 * @author Rui Chaoqun
 * @date :2020/3/31 9:18
 * description:迷幻水波动效
 */
public class PsychedelicRippleEffectView extends View implements DynamicEffectView {
    private static final int circleWidth = UiUtils.dp2px(5.0f);
    private Paint mCirclePaint;
    private Paint mWavePaint;

    private int step;
    private float[] mOriginalData;
    private float[] mShiftData;
    private float[] mLastData;
    private float[] mCurrentData;

    private int color = Color.WHITE;
    private float[] outHsl = new float[3];
    private int effectColor;
    private int[] lineColors;

    private int fftLength;

    private boolean tag = true;
    private int effectMinRadius;
    private int minWidth;
    private int mStrokeWidth = UiUtils.dp2px(5.0f);
    private Path mPath;
    private float mFrequency;
    private long dataChangeTime;

    private EffectData<PsychedelicEffectData> mData = new EffectData<>();
    private Random mRandom = new Random();


    public PsychedelicRippleEffectView(Context context) {
        super(context);
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(Color.WHITE);
        lineColors = transferColor(color);
    }

    @Override
    public void setColor(int color) {
        if (this.color != color) {
            this.color = color;
            this.lineColors = transferColor(color);
        }
    }

    private int[] transferColor(int color) {
        this.effectColor = ColorUtil.getEffectColor(color, this.outHsl);
        return new int[]{ColorUtils.setAlphaComponent(this.effectColor, 77), ColorUtils.setAlphaComponent(ColorUtil.b(this.effectColor, this.outHsl), 51), ColorUtils.setAlphaComponent(this.effectColor, 30)};
    }

    @Override
    public void onFftDataCapture(byte[] fft, int samplingRate) {
        if (fftLength != fft.length) {
            fftLength = fft.length;
            mOriginalData = new float[45];
            mShiftData = new float[45];
            mLastData = new float[45];
            mCurrentData = new float[45];
            step = (fftLength / 2) / 128;
        }
        if(effectMinRadius > 0){
            tag = fftFataInvalidate(fft);
            if (!tag) {
                System.arraycopy(mOriginalData, 0, mLastData, 0, mOriginalData.length);
                float sum = 0;
                for (int i = 0; i < mOriginalData.length; i++) {
                    int index = 30 + 2 * i * step;
                    mOriginalData[i] = (float) Math.log(1 + fft[index] * fft[index] + fft[index + 1] * fft[index + 1]) * 10f;
                    sum += mOriginalData[i];
                }
                Log.w("yyyyy", "sum-->" + sum / 45);
                dataChangeTime = System.currentTimeMillis();
                setPointData((int) (sum / 90));
            }
            invalidate();
        }
    }

    private void setPointData(int count) {
        long t = System.currentTimeMillis();
        mData.addData(new PsychedelicEffectData(t, mRandom.nextDouble() * Math.PI * 2));
    }

    public boolean fftFataInvalidate(byte[] fft) {
        for (int i = 0; i < fft.length; i++) {
            if (fft[i] > 0) {
                return false;
            }
        }
        return true;
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
        int rate = (int) (visualizerEntity.getMaxCaptureRate() * 0.5d);
        this.mFrequency = 1000000.0f / ((float) rate);
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
        boolean needInvalidate;
        if (effectMinRadius == 0) {
            View artView = ((DynamicEffectCommonLayout) getParent()).getArtView();
            effectMinRadius = Math.max(artView.getWidth(), artView.getHeight()) / 2 + mStrokeWidth;
            minWidth = Math.min(getWidth() / 2, getHeight() / 2);
        }
        int save = canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        long uptimeMillis = System.currentTimeMillis();

        if (!mData.isEmpty()) {
            Iterator<PsychedelicEffectData> iterator = mData.iterator();
            while (iterator.hasNext()) {
                PsychedelicEffectData data = iterator.next();
                float delta = uptimeMillis - data.times;
                if (delta > 2000.0f) {
                    iterator.remove();
                } else {
                    float s = delta / 2000.0f;
                    float d = effectMinRadius + (minWidth - effectMinRadius) * s;
                    float radius = circleWidth + (UiUtils.dp2px(1.0f) - circleWidth) * s;
                    mCirclePaint.setColor(ColorUtils.setAlphaComponent(effectColor, (int)((s * -55.0f) + 55.0f + 0.5f)));
                    canvas.drawCircle((float) (d * Math.cos(data.angle)), (float) (d * Math.sin(data.angle)), radius, mCirclePaint);
                }
            }
            needInvalidate = true;
        }else{
            needInvalidate = false;
        }

        if (!tag) {
            if (mPath == null) {
                mPath = new Path();
                mWavePaint.setPathEffect(new CornerPathEffect(effectMinRadius));
            }
            long time = uptimeMillis - dataChangeTime;
            float t = time > mFrequency ? 1.0f : time / mFrequency;
            if (!needInvalidate) {
                if (t >= 1.0f) {
                    needInvalidate = false;
                } else {
                    needInvalidate = true;
                }
            }
            for (int i = 0; i < 3; i++) {
                mPath.reset();
                for (int j = 0; j < mOriginalData.length; j++) {
                    float l = effectMinRadius + (mLastData[j] + (mOriginalData[j] - mLastData[j]) * t) * (1.0f - (((float) i) * 0.2f));
                    float x = (float) (l * Math.cos((Math.PI * 2 * j) / mOriginalData.length));
                    float y = (float) (l * Math.sin((Math.PI * 2 * j) / mOriginalData.length));
                    if (j == 0) {
                        mPath.moveTo(x, y);
                    } else {
                        mPath.lineTo(x, y);
                    }
                }
                mPath.close();
                mWavePaint.setColor(lineColors[2 - i]);
                canvas.drawPath(mPath, mWavePaint);
                canvas.rotate(120);
            }
        }
        canvas.restoreToCount(save);
        if (needInvalidate) {
            postInvalidateOnAnimation();
        }

    }

    private class PsychedelicEffectData extends ListNode<PsychedelicEffectData> {
        private long times;
        private double angle;

        public PsychedelicEffectData(long times, double angle) {
            this.times = times;
            this.angle = angle;
        }
    }
}
