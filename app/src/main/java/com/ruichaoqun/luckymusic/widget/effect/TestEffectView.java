package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Pair;
import android.view.View;

import com.ruichaoqun.luckymusic.utils.UiUtils;

/**
 * @author Rui Chaoqun
 * @date :2020/3/30 11:34
 * description:
 */
public class TestEffectView extends View implements  DynamicEffectView {
    private float[] mFloats = new float[30];
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TestEffectView(Context context) {
        super(context);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void setColor(int color) {

    }

    @Override
    public void onFftDataCapture(byte[] fft, int samplingRate) {
        for (int i = 0; i < mFloats.length; i++) {
            int index = (19+3*i)*2;
            float magnitude = (float) (Math.log10(1.0d + Math.pow((double) fft[index], 2.0d) + Math.pow((double) fft[index + 1], 2.0d)) * 10.0d);
//            float magnitude = (float)Math.hypot(fft[(19+3*i)*2], fft[(19+3*i)*2+1]);
            mFloats[i] = magnitude;
        }
        invalidate();
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
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int step = (width - 100)/mFloats.length;
        mPaint.setStrokeWidth(step);

        int l = 50;
        for (int i = 0; i < mFloats.length; i++) {
            canvas.drawLine(l,height,l,height - mFloats[i],mPaint);
            l += step;
        }
    }
}
