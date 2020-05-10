package com.ruichaoqun.luckymusic.widget.effect;

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
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


/**
 * 孤独星球动效
 */
public class LonelyPlanetEffectView extends View implements DynamicEffectView {
    static final int[] mPointRadis = {UiUtils.dp2px(2.0f), UiUtils.dp2px(3.0f),UiUtils.dp2px(4.0f),UiUtils.dp2px(5.0f)};
    private static final float mCircleLiveTime = 2000.0f;
    private static final float f11624f = -20.0f;
    private static final float j = UiUtils.dp2px(1.0f);
    private static final float f11627i = UiUtils.dp2px(2.0f);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPointPaint  = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int mStrokeWidth = UiUtils.dp2px(5.0f);
    private int mColor = Color.WHITE;
    private float[] outHsl = new float[3];
    private int mEffectColor = ColorUtil.getEffectColor(this.mColor, this.outHsl);
    private int maxWidth = -1;
    private int mArtRadius;
    private int mStrokeRadius;

    private LonglyHandler mHandler = new LonglyHandler();
    private Random mRandom = new Random();
    private int mCurrentWave;
    private long mLastMessageTime;


    private EffectData<LonglyEffectData> mDatas = new EffectData<>();


    public LonelyPlanetEffectView(Context context) {
        super(context);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setColor(Color.parseColor("#1AFFFFFF"));
        this.mStrokePaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    public void setColor(int color) {
        Log.w("bbbbbb","color-->"+color);
        if (this.mColor != color) {
            this.mColor = color;
            this.mEffectColor = ColorUtil.getEffectColor(mColor, this.outHsl);
            invalidate();
        }
    }

    @Override
    public void onFftDataCapture(byte[] fft, int samplingRate) {
    }

    @Override
    public void onWaveFormDataCapture(byte[] waveform, int samplingRate) {
        int length = waveform.length;
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum+=waveform[i]+128;
        }
//        this.mCurrentWave = (int) Math.ceil(((sum/length)/64)/2f);
        this.mCurrentWave = (sum/length)/64;
        Log.w("RRRRR",mCurrentWave+"");
        if (mCurrentWave > 0 && !mHandler.hasMessages(0)) {
            long time = 1000/mCurrentWave;  //250-1000
            long uptimeMillis = SystemClock.uptimeMillis();
            if(uptimeMillis - mLastMessageTime < time){
                uptimeMillis = mLastMessageTime+time;
            }
            mHandler.sendEmptyMessageAtTime(0, uptimeMillis);
        }
    }

    public void updateTime(long uptimeMillis) {
        Log.w("AAAA",(uptimeMillis-mLastMessageTime)+"   ");
        if (this.maxWidth > 0) {
            boolean isEmpty = this.mDatas.isEmpty();
            int[] arr = mPointRadis;
            mDatas.addData(getData(arr[this.mRandom.nextInt(arr.length)], this.mRandom.nextInt(360), uptimeMillis));
            this.mLastMessageTime = uptimeMillis;
            if (isEmpty) {
                invalidate();
            }
        }
//        if(this.mCurrentWave > 0){
//            Log.w("AAAA",mCurrentWave+"   mCurrentWave   ");
//            this.mHandler.sendEmptyMessageAtTime(0, uptimeMillis + 1000/mCurrentWave);
//        }
    }

    @Override
    public void reset(boolean close) {
        this.mHandler.removeCallbacksAndMessages(null);
        if (close) {
            this.mDatas.clear();
        }
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
        return visualizerEntity.getMaxCaptureRate();
    }

    @Override
    public Pair<Integer, Integer> getArtViewSize() {
        int width = UiUtils.dp2px(225.0f);
        return new Pair<>(width, width);
    }

    @Override
    public int getCaptureSize(VisualizerEntity visualizerEntity) {
        return visualizerEntity.getCaptureSizeRange()[0];
    }




    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        if (this.maxWidth == -1) {
            this.maxWidth = Math.min(width, height);
            View artView = ((DynamicEffectCommonLayout) getParent()).getArtView();
            if (artView != null) {
                this.mArtRadius = Math.max(artView.getWidth(), artView.getHeight()) / 2;
            } else {
                this.mArtRadius = mPointRadis[mPointRadis.length - 1];
            }
            this.mStrokeRadius = this.mArtRadius + (mStrokeWidth / 2);
        }
        boolean z2 = false;
        int save = canvas.save();
        canvas.translate((float) width, (float) height);
        if (!this.mDatas.isEmpty()) {
            Iterator<LonglyEffectData> it = this.mDatas.iterator();
            while (it.hasNext()) {
                LonglyEffectData next = it.next();
                float uptimeMillis = (float) (SystemClock.uptimeMillis() - next.timeMillis);
                if (uptimeMillis >= mCircleLiveTime) {
                    it.remove();
                } else {
                    float f2 = uptimeMillis / mCircleLiveTime;
                    int i2 = this.maxWidth;
                    int i3 = this.mArtRadius;
                    int i4 = (int) (((double) ((((float) (i2 - i3)) * f2) + ((float) i3))) + 0.5d);
                    int i5 = (int) (((double) ((((uptimeMillis * f11624f) / 1000.0f) + ((float) next.pointAngle)) % 360.0f)) + 0.5d);
                    float f3 = j;
                    float f4 = f11627i;
                    int alphaComponent = ColorUtils.setAlphaComponent(this.mEffectColor, (int) (((double) ((-102.0f * f2) + 102.0f)) + 0.5d));
//                    Log.w("AAAAAA","alphaComponent-->"+alphaComponent);
                    this.mPaint.setColor(alphaComponent);
                    this.mPaint.setStrokeWidth((f2 * (f3 - f4)) + f4);
                    canvas.drawCircle(0.0f, 0.0f, (float) i4, this.mPaint);
                    double radians = Math.toRadians((double) i5);
                    double d2 = (double) i4;
                    this.mPointPaint.setColor(alphaComponent);
                    canvas.drawCircle((float) (Math.cos(radians) * d2), (float) (d2 * Math.sin(radians)), (float) next.pointRadius, this.mPointPaint);
                }
            }
            z2 = true;
        }
        canvas.drawCircle(0.0f, 0.0f, (float) this.mStrokeRadius, this.mStrokePaint);
        canvas.restoreToCount(save);
        if (z2) {
            postInvalidateOnAnimation();
        }
    }



    public LonglyEffectData getData(int pointRadius, int pointAngle, long timeMillis){
        LonglyEffectData data = mDatas.getCacheData();
        if(data == null){
            return new LonglyEffectData(pointRadius,pointAngle,timeMillis);
        }
        data.pointAngle = pointAngle;
        data.pointRadius = pointRadius;
        data.timeMillis = timeMillis;
        return data;
    }

    private class LonglyHandler extends Handler {
        private LonglyHandler() {
        }

        @Override
        public void handleMessage(Message message) {
            updateTime(message.getWhen());
        }
    }

    class LonglyEffectData extends ListNode<LonglyEffectData>{
        int pointRadius;
        int pointAngle;
        long timeMillis;

        LonglyEffectData(int pointRadius, int pointAngle, long timeMillis) {
            this.pointRadius = pointRadius;
            this.pointAngle = pointAngle;
            this.timeMillis = timeMillis;
        }
    }
}
