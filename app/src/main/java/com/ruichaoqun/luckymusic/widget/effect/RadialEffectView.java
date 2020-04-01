package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
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
    //最大射线长度
    private static final float maxRadialLength = UiUtils.dp2px(85.0f);
    private static final int mRadialWidth = UiUtils.dp2px(3.0f);

    private Paint mPaint;
    private float mFrequency;

    private int fftLength;

    private float[] mOriginalData;
    private float[] mLastData;
    private float[] mCurrentData;


    private boolean tag = true;
    private int effectMinRadius;
    private int minWidth;
    private int mStrokeWidth = UiUtils.dp2px(10.0f);
    private Path mPath;
    private long dataChangeTime;
    //射线长度
    private float mRadialLength;

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



    int t;
    int n;
    int q;
    @Override
    public void onFftDataCapture(byte[] fft, int samplingRate) {
        if (fftLength != fft.length) {
            fftLength = fft.length;


            float f2 = (((float) samplingRate) / 1000.0f) / ((float) this.fftLength);
            this.n = (int) Math.ceil((double) (800.0f / f2));
            this.q = Math.min((int) (4800.0f / f2), (fftLength / 2) - 1);
            this.t = 0;

        }
        if (effectMinRadius > 0) {
            if (this.t == 0) {
                int i4 = (this.q - this.n) + 1;
                this.t = i4 >= mCurrentData.length ? i4 / mCurrentData.length : -((int) Math.ceil((double) (((float) mCurrentData.length) / ((float) i4))));
            }


            tag = fftFataInvalidate(fft);
            if (!tag) {
                System.arraycopy(mCurrentData, 0, mLastData, 0, mOriginalData.length);
                //对fft数据进行转换
                for (int i = 0; i < mOriginalData.length; i++) {
                    int index = 38 + 2 * i;
                    float len = (float) Math.log10(1 + fft[index] * fft[index] + fft[index + 1] * fft[index + 1]) * 10f;
                    mOriginalData[i] = (float) Math.min((len / 45.0d) * mRadialLength, mRadialLength);
                }
                //滤波
                for (int i = 0; i < mOriginalData.length; i++) {
                    mCurrentData[i] = a(mOriginalData, i);
//            if(i == 0){
//                mCurrentData[i] = (mOriginalData[mOriginalData.length-1]+mOriginalData[0]+mOriginalData[1])/3f;
//            }else if(i == mOriginalData.length-1){
//                mCurrentData[i] = (mOriginalData[i]+mOriginalData[0]+mOriginalData[i-1])/3f;
//            }else{
//                mCurrentData[i] = (mOriginalData[i]+mOriginalData[i+1]+mOriginalData[i-1])/3f;
//            }
                }
                dataChangeTime = System.currentTimeMillis();
            }
            invalidate();
        }
    }

    private static final float[] f9807f = {-0.09090909f, 0.060606062f, 0.16883117f, 0.23376623f, 0.25541127f, 0.23376623f, 0.16883117f, 0.060606062f, -0.09090909f};

    static float a(float[] fArr, int index) {
        float[] fArr2 = f9807f;
        int length = index - (fArr2.length / 2);//index-4
        int length2 = fArr2.length;//9
        int i = 0;
        float f2 = 0.0f;
        int i5 = 0;
        while (i < length2) {//i<9
            float f3 = fArr2[i];
            int i6 = i5 + 1;//1
            int i7 = i5 + length;
            int length3 = fArr.length;
            if (i7 < 0) {
                i7 += length3;
            } else if (i7 > length3 - 1) {
                i7 -= fArr.length;
            }
            f2 += f3 * fArr[i7];
            i++;
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
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        if (effectMinRadius == 0) {
            View artView = ((DynamicEffectCommonLayout) getParent()).getArtView();
            effectMinRadius = Math.max(artView.getWidth(), artView.getHeight()) / 2 + mStrokeWidth;
            minWidth = Math.min(getWidth() / 2, getHeight() / 2);
            //最大射线长度 = 最大半径+20dp-图片半径-图片到射线距离
            mRadialLength = Math.min((Math.min(width, height) + UiUtils.dp2px(20.0f)) - effectMinRadius, maxRadialLength);
            int max = (int) ((effectMinRadius*2*Math.PI)/(mRadialWidth*2.0d));
            mOriginalData = new float[max];
            mLastData = new float[max];
            mCurrentData = new float[max];
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
                float s = (mLastData[j] + (mCurrentData[j] - mLastData[j]) * t);
                float l = effectMinRadius + Math.max(0.0f,s-UiUtils.dp2px(5.0f));
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
