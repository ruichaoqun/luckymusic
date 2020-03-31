package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Pair;
import android.view.View;

import com.ruichaoqun.luckymusic.utils.ColorUtil;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.Random;

/**
 * @author Rui Chaoqun
 * @date :2020/3/31 19:07
 * description:
 */
public class RadialEffectView extends View implements DynamicEffectView {
    private Paint mPaint;
    private float mFrequency;

    private int fftLength;

    private float[] mOriginalData;
    private float[] mLastData;
    private float[] mCurrentData;


    private boolean tag = true;
    private int effectMinRadius;
    private int minWidth;
    private int mStrokeWidth = UiUtils.dp2px(5.0f);
    private Path mPath;
    private long dataChangeTime;

    private Random mRandom = new Random();

    private int color = Color.WHITE;
    private float[] outHsl = new float[3];
    private int effectColor;

    public RadialEffectView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        effectColor = ColorUtil.getEffectColor(color, this.outHsl);
        mPaint.setColor(effectColor);
        mPaint.setStrokeWidth(UiUtils.dp2px(3.0f));
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
        int rate = (int) (visualizerEntity.getMaxCaptureRate() * 0.85);
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
    public void onFftDataCapture(byte[] fft, int samplingRate) {
        if (fftLength != fft.length) {
            fftLength = fft.length;
            mOriginalData = new float[128];
            mLastData = new float[128];
            mCurrentData = new float[128];
        }
        if (effectMinRadius > 0) {
            tag = fftFataInvalidate(fft);
            if (!tag) {
                System.arraycopy(mCurrentData, 0, mLastData, 0, mOriginalData.length);
                for (int i = 0; i < mOriginalData.length; i++) {
                    int index = 30 + 4 * i;
                    mOriginalData[i] = (float) Math.log(1 + fft[index] * fft[index] + fft[index + 1] * fft[index + 1]) * 10f;
                }
                dataChangeTime = System.currentTimeMillis();
                filterData();
            }
            invalidate();
        }
    }

    public void filterData(){
        for (int i = 0; i < mOriginalData.length; i++) {
            mCurrentData[i] = a(mOriginalData,i);
//            if(i == 0){
//                mCurrentData[i] = (mOriginalData[mOriginalData.length-1]+mOriginalData[0]+mOriginalData[1])/3f;
//            }else if(i == mOriginalData.length-1){
//                mCurrentData[i] = (mOriginalData[i]+mOriginalData[0]+mOriginalData[i-1])/3f;
//            }else{
//                mCurrentData[i] = (mOriginalData[i]+mOriginalData[i+1]+mOriginalData[i-1])/3f;
//            }
        }
    }

    private static final float[] f9807f = {-0.09090909f, 0.060606062f, 0.16883117f, 0.23376623f, 0.25541127f, 0.23376623f, 0.16883117f, 0.060606062f, -0.09090909f};

    static float a(float[] fArr, int i2) {
        float[] fArr2 = f9807f;
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
    public void setColor(int color) {
        if (this.color != color) {
            this.color = color;
            effectColor = ColorUtil.getEffectColor(color, this.outHsl);
            mPaint.setColor(effectColor);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boolean needInvalidate = true;
        if (effectMinRadius == 0) {
            View artView = ((DynamicEffectCommonLayout) getParent()).getArtView();
            effectMinRadius = Math.max(artView.getWidth(), artView.getHeight()) / 2 + mStrokeWidth;
            minWidth = Math.min(getWidth() / 2, getHeight() / 2);
        }
        int save = canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        long uptimeMillis = System.currentTimeMillis();

        if (!tag) {
            long time = uptimeMillis - dataChangeTime;
            float t = time > mFrequency ? 1.0f : time / mFrequency;
            if (!needInvalidate) {
                if (t >= 1.0f) {
                    needInvalidate = false;
                } else {
                    needInvalidate = true;
                }
            }
            for (int j = 0; j < mCurrentData.length; j++) {
                float l = effectMinRadius + (mLastData[j] + (mCurrentData[j] - mLastData[j]) * t);
                double sin = Math.sin(Math.PI * 2 * j / mCurrentData.length);
                double cos = Math.cos(Math.PI * 2 * j / mCurrentData.length);
                float x = (float) (l * cos);
                float y = (float) (l * sin);
                float x1 = (float) (effectMinRadius * cos);
                float y1 = (float) (effectMinRadius * sin);
                canvas.drawLine(x1, y1, x, y, mPaint);
            }
        }
        canvas.restoreToCount(save);
        if (needInvalidate) {
            postInvalidateOnAnimation();
        }
    }
}
