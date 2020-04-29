package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class EqualizerChartView extends View implements EqualizerSeekBar.OnProgressChangedListener {
    private Paint mLinePaint;
    private Paint mTextPaint;
    private Paint mVisibleRectPaint;
    private Paint mBackLinePaint;
    private Paint mArrowPaint;
    private int mBacgroundColor = Color.TRANSPARENT;

    private float mTextMarginBottom;

    private float[] mFloatsX;
    private float[] mFloatsY;
    private int[] mProgress = new int[10];
    private String[] mFrequency;

    private Path mPath;

    private float mRectRatio = 0.5f;
    private float mScrollRatio = 0.0f;
    private float mRatio;

    private Path mArrowPath;
    private float mArrowLength;

    private float mTouchedX;

    private OnChartViewScrollListener mOnChartViewScrollListener;


    public EqualizerChartView(Context context) {
        super(context);
        init(context);
    }

    public EqualizerChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mFrequency = context.getResources().getStringArray(R.array.frequency_band);
        mTextMarginBottom = UiUtils.dp2px( 5.0f);
        mArrowLength = UiUtils.dp2px( 8.0f);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(UiUtils.dp2px( 2.0f));
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setPathEffect(new CornerPathEffect(UiUtils.dp2px( 20.0f)));
        mLinePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(UiUtils.sp2px( 8.0f));
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mVisibleRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mVisibleRectPaint.setColor(Color.parseColor("#44ffffff"));

        mBackLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackLinePaint.setStrokeWidth(UiUtils.dp2px( 0.5f));
        mBackLinePaint.setColor(Color.parseColor("#55ffffff"));

        mArrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArrowPaint.setColor(Color.parseColor("#44ffffff"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float width = getWidth();
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchedX = x;
                mRatio = mScrollRatio;
                if (!isInRect(mTouchedX)) {
                    return super.onTouchEvent(event);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = x - mTouchedX;
                float ratio = distanceX / ((1 - mRectRatio) * width);
                mScrollRatio = mRatio + ratio;
                if (mScrollRatio < 0) {
                    mScrollRatio = 0;
                }
                if (mScrollRatio > 1) {
                    mScrollRatio = 1;
                }
                if(mOnChartViewScrollListener != null){
                    mOnChartViewScrollListener.onChartViewScroll(mScrollRatio);
                }
                break;
            default:
        }
        invalidate();
        return true;
    }

    private boolean isInRect(float mTouchedX) {
        float x1 = mScrollRatio * (1 - mRectRatio) * getWidth();
        float x2 = x1 + mRectRatio * getWidth();
        return mTouchedX > x1 && mTouchedX < x2;
    }

    public void setEffectEnabled(boolean enabled) {
        if(enabled){
            mBacgroundColor = ContextCompat.getColor(getContext(),R.color.colorPrimary);
        }else{
            mBacgroundColor = ContextCompat.getColor(getContext(),R.color.color_66000000);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        canvas.drawColor(mBacgroundColor);
        if (mFloatsX == null) {
            mFloatsX = new float[10];
            mFloatsY = new float[7];
            float xStep = width / 11.0f;
            float yStep = height / 8;
            for (int i = 1; i <= mFloatsX.length; i++) {
                mFloatsX[i - 1] = i * xStep;
            }
            for (int i = 1; i <= mFloatsY.length; i++) {
                mFloatsY[i - 1] = i * yStep;
            }
        }
        if (mPath == null) {
            mPath = new Path();
            mArrowPath = new Path();
        }
        mPath.reset();
        mPath.moveTo(0, height / 2);
        for (int i = 0; i < mFloatsX.length; i++) {
            canvas.drawLine(mFloatsX[i] - 4.0f, 0, mFloatsX[i] - 4.0f, height, mBackLinePaint);
            canvas.drawLine(mFloatsX[i] + 4.0f, 0, mFloatsX[i] + 4.0f, height, mBackLinePaint);
            int value = (int) ((mProgress[i] / 100.0f) - 12);
            canvas.drawText(String.valueOf(value), mFloatsX[i], height - mTextMarginBottom, mTextPaint);
            mPath.lineTo(mFloatsX[i], (1 - mProgress[i] / 2400.0f) * height);
        }
        for (int i = 0; i < mFloatsY.length; i++) {
            canvas.drawLine(0, mFloatsY[i], width, mFloatsY[i], mBackLinePaint);
        }
        mPath.lineTo(width, height / 2);
        canvas.drawPath(mPath, mLinePaint);
        float left = mScrollRatio * (width * (1 - mRectRatio));
        float right = left + width * mRectRatio;
        canvas.drawRect(left, 0, right, height, mVisibleRectPaint);
        mArrowPath.reset();
        float x = right - 8.0f;
        float y = mArrowLength / 2 + 8.0f;
        float k = (float) (mArrowLength * Math.sin(60 * Math.PI / 180));
        mArrowPath.moveTo(x, y);
        mArrowPath.lineTo(x - k, 8.0f);
        mArrowPath.lineTo(x - k, mArrowLength + 8.0f);
        mArrowPath.close();
        canvas.drawPath(mArrowPath, mArrowPaint);
        mArrowPath.reset();
        mArrowPath.moveTo(x - k - 8.0f, 8.0f);
        mArrowPath.lineTo(x - k - 8.0f, mArrowLength + 8.0f);
        mArrowPath.lineTo(x - k - 8.0f - k, mArrowLength / 2 + 8.0f);
        mArrowPath.close();
        canvas.drawPath(mArrowPath, mArrowPaint);
    }

    public void setOnChartViewScrollListener(OnChartViewScrollListener listener){
        this.mOnChartViewScrollListener = listener;
    }

    @Override
    public void onProgressChanged(String tag, int progress) {
        mProgress[getIndex(tag)] = progress;
        invalidate();
    }

    public void setData(List<Float> list){
        for (int i = 0; i < mProgress.length; i++) {
            mProgress[i] = (int) ((list.get(i)+12.0f)*100);
        }
        invalidate();
    }

    public List<Float> getData(){
        List<Float> list = new ArrayList<>();
        for (int i = 0; i < mProgress.length; i++) {
            list.add(mProgress[i]/100.0f-12.0f);
        }
        return list;
    }

    private int getIndex(String tag){
        for (int i = 0; i < mFrequency.length; i++) {
            if(TextUtils.equals(tag,mFrequency[i])){
                return i;
            }
        }
        return 0;
    }

    public void setRectRatio(float ratio){
        this.mRectRatio = ratio;
        invalidate();
    }

    public void setScrollRatio(float scrollRatio){
        this.mScrollRatio = scrollRatio;
        invalidate();
    }

    public interface OnChartViewScrollListener{
        void onChartViewScroll(float offset);
    }
}
