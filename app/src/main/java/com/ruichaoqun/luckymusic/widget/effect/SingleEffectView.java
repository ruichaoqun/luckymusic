package com.ruichaoqun.luckymusic.widget.effect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.ruichaoqun.luckymusic.utils.UiUtils;


public class SingleEffectView extends View implements DynamicEffectView {
    private int imageWidth;
    private float[] mCurrentData;
    private float[] mPreData;
    private float[] mOriginalData;
    private Handler mHandler;
    private Handler mUpdateHandler;
    private float[] mLines;

    private int fftLength;
    private float step;
    private Paint mPaint;


    @SuppressLint("HandlerLeak")
    public SingleEffectView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(UiUtils.dp2px(2.0f));
        mPaint.setColor(Color.RED);
        HandlerThread handlerThread = new HandlerThread("SingleEffectView");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                byte[] bytes = (byte[]) msg.obj;
                calculateData(bytes);
            }
        };
        mUpdateHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                updateData();
            }
        };
    }

    private void calculateData(byte[] bytes) {
        float max = 0;
        for (int i = 0; i < 128; i++) {
//            float f = (float) Math.hypot(bytes[50+2*i], bytes[50+2*i + 1]);
//            float f = (float) Math.log10(bytes[50+2*i]*bytes[50+2*i]+ bytes[50+2*i + 1]*bytes[50+2*i+1]+1)*10;
//            float y = (128*20 - 8*i)/128f;
//            float y = 20;
//            mOriginalData[i] = f > y?(f-y):0.0f;
            mOriginalData[i] = bytes[i];
            max = max>mOriginalData[i]?max:mOriginalData[i];
        }
        Log.w("QQQQQQ","max-->"+max);
        mUpdateHandler.sendEmptyMessage(0);
    }

    private void updateData() {
        System.arraycopy(this.mCurrentData,0,this.mPreData,0,mCurrentData.length);
        System.arraycopy(this.mOriginalData,0,this.mCurrentData,0,mCurrentData.length);
        invalidate();
    }

    @Override
    public void reset(boolean close) {

    }

    @Override
    public void prepareToDynamic() {

    }

    @Override
    public boolean isFft() {
        return false;
    }

    @Override
    public boolean isWaveform() {
        return true;
    }

    @Override
    public int getRate(VisualizerEntity visualizerEntity) {
        return (int) (visualizerEntity.getMaxCaptureRate()*0.5d);
    }

    @Override
    public Pair<Integer, Integer> getArtViewSize() {
        int width = UiUtils.dp2px(205.0f);
        return new Pair<>(width,width);
    }

    @Override
    public int getCaptureSize(VisualizerEntity visualizerEntity) {
        return visualizerEntity.getCaptureSizeRange()[0];
    }

    @Override
    public void onFftDataCapture(byte[] fft, int samplingRate) {
        if(fftLength == 0){
            fftLength = fft.length;
            mCurrentData = new float[128];
            mPreData = new float[128];
            mOriginalData = new float[128];
        }
        if(imageWidth != 0){
            mHandler.obtainMessage(0,fft).sendToTarget();
        }

    }

    @Override
    public void onWaveFormDataCapture(byte[] waveform, int samplingRate) {
        if(fftLength == 0){
            fftLength = waveform.length;
            mCurrentData = new float[128];
            mPreData = new float[128];
            mOriginalData = new float[128];
        }
        if(imageWidth != 0){
            mHandler.obtainMessage(0,waveform).sendToTarget();
        }
    }

    @Override
    public void setColor(int color) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        if(imageWidth == 0){
            View view = ((DynamicEffectCommonLayout)getParent()).getArtView();
            imageWidth = view.getWidth();
        }
        int y = centerY-imageWidth/2;
        if(mCurrentData != null){
            if(mLines == null){
                mLines = new float[mCurrentData.length*4];
                step = (float) (getWidth()-50)/(float)mCurrentData.length;
            }
            for (int i = 0; i < mCurrentData.length; i++) {
                mLines[4*i] = 25+step*i;
                mLines[4*i+1] = y;
                mLines[4*i+2] = 25+step*i;
                mLines[4*i+3] = y-mCurrentData[i];
            }
            canvas.drawLines(mLines,mPaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mHandler.getLooper().quit();
        mUpdateHandler.removeCallbacksAndMessages( null);
        super.onDetachedFromWindow();
    }
}
