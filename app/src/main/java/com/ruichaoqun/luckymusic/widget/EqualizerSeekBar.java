package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.utils.UiUtils;


/**
 * @author Rui Chaoqun
 * @date :2020/4/18 1:24
 * description:
 */
public class EqualizerSeekBar extends View {
    private float mLargeLineWidth;
    private float mSmallLineWidth;
    private float mLineStrokeWidth;
    private float mThumbRadio;
    private float mThumbShadowWidth;
    private float mThumbLineWidth;
    private float mThumbLineDividerWidth;
    private float mTextBackgroundHeight;
    private float mTextPadding;
    private float mMinTouchHeight;
    private float mMaxTouchHeight;
    private float mTopPadding;


    private Paint mBackgroundLinePaint;
    private Paint mProgressPaint;
    private Paint mThumbPaint;
    private Paint mThumbBacPaint;
    private Paint mThumbShadowPaint;
    private Paint mThumbLinePaint;
    private Paint mTextBackgroundPaint;
    private Paint mTextPaint;

    private boolean mTouched;
    private int progress = 1200;

    private int mProgressColor;
    private int mProgressScrollColor;

    private Path mPath;

    private float mTouchX;
    private float mTouchY;

    private String mTag;
    private OnProgressChangedListener mOnProgressChangedListener;
    private OnDragFinishListener mOnDragFinishListener;

    public EqualizerSeekBar(Context context) {
        super(context);
        init(context);
    }

    public EqualizerSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mTopPadding = UiUtils.dp2px(30.0f);
        mLineStrokeWidth = UiUtils.dp2px( 2.0f);
        mLargeLineWidth = UiUtils.dp2px( 15.0f);
        mSmallLineWidth = UiUtils.dp2px(7.5f);
        mThumbRadio = UiUtils.dp2px( 12.5f);
        mThumbLineWidth = UiUtils.dp2px(12.0f);
        mThumbLineDividerWidth = UiUtils.dp2px(4.5f);
        mThumbShadowWidth = UiUtils.dp2px(2.0f);
        mTextBackgroundHeight = UiUtils.dp2px(20.0f);
        mTextPadding = UiUtils.dp2px(3.0f);
        mMinTouchHeight = mTopPadding+mThumbRadio + mLineStrokeWidth;

        mBackgroundLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundLinePaint.setColor(Color.parseColor("#33000000"));
        mBackgroundLinePaint.setStrokeWidth(mLineStrokeWidth);
        mBackgroundLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setStrokeWidth(mLineStrokeWidth);
        mProgressPaint.setColor(Color.parseColor("#55000000"));


        mThumbShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mThumbShadowPaint.setStrokeWidth(mThumbShadowWidth);
        mThumbShadowPaint.setColor(Color.parseColor("#1A000000"));
        mThumbShadowPaint.setStyle(Paint.Style.STROKE);

        mThumbBacPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mThumbBacPaint.setColor(Color.WHITE);
        mThumbBacPaint.setStyle(Paint.Style.FILL);

        mThumbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mThumbPaint.setColor(Color.parseColor("#1A000000"));
        mThumbPaint.setStrokeWidth(1.0f);
        mThumbPaint.setStyle(Paint.Style.STROKE);

        mThumbLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mThumbLinePaint.setColor(Color.parseColor("#FFEEEEEE"));
        mThumbLinePaint.setStrokeWidth(UiUtils.dp2px( 1.3f));
        mThumbLinePaint.setStrokeCap(Paint.Cap.ROUND);

        mTextBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextBackgroundPaint.setStyle(Paint.Style.FILL);
        mTextBackgroundPaint.setColor(Color.RED);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(UiUtils.sp2px( 12.0f));

        mProgressScrollColor = Color.RED;
        mProgressColor = ColorUtils.setAlphaComponent(mProgressScrollColor, 160);
    }

    public void setOnProgressChangedListener(String tag, OnProgressChangedListener onProgressChangedListener) {
        mTag = tag;
        mOnProgressChangedListener = onProgressChangedListener;
    }

    public void setOnDragFinishListener(OnDragFinishListener onDragFinishListener) {
        mOnDragFinishListener = onDragFinishListener;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = x;
                mTouchY = y;
                //如果触摸点不在thumb上，不再处理事件，交由父View处理
                if (!isTouchedInThumb(x, y)) {
                    this.mTouched = false;
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return super.onTouchEvent(event);
                }
                this.mTouched = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(mOnDragFinishListener != null){
                    mOnDragFinishListener.onDragFinish();
                }
                this.mTouched = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (getAngleY(this.mTouchX, this.mTouchY, x, y) >= 60 && Math.abs(this.mTouchY - y) >= ((float) ViewConfiguration.get(getContext()).getScaledTouchSlop())) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    this.mTouched = true;
                }
                if (y < mMinTouchHeight) {
                    y = mMinTouchHeight;
                }
                if (y > mMaxTouchHeight) {
                    y = mMaxTouchHeight;
                }
                progress = (int) (((mMaxTouchHeight - y) / (float) (mMaxTouchHeight - mMinTouchHeight)) * 2400);
                Log.w("AAAA",progress+"");
                if(mOnProgressChangedListener != null){
                    mOnProgressChangedListener.onProgressChanged(mTag,progress);
                }
                break;
        }
        invalidate();
        return true;
    }


    private boolean isTouchedInThumb(float x, float y) {
        float disX = Math.abs(getThumbCx() - x);
        float disY = Math.abs(getThumbCy() - y);
        return Math.sqrt(((disX * disX) + (disY * disY))) < mThumbRadio;
    }

    private int getAngleY(float touchX, float touchY, float x, float y) {
        float a = Math.abs(touchX - x);
        float b = Math.abs(touchY - y);
        return Math.round((float) (((Math.asin(b / (Math.sqrt(a * a + b * b)))) / Math.PI) * 180d));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //1.分割水平线
        drawBackgroundLine(canvas);

        //2.长度线 进度线
        drawLines(canvas);

        //3.thumb
        drawThumb(canvas);

        //4.如果在移动中，文字
        if (mTouched) {
            drawText(canvas);
        }
    }

    private void drawBackgroundLine(Canvas canvas) {
        int width = getWidth();
        float height = (getHeight()- mTopPadding) / 16.0f;
        for (int i = 1; i <= 15; i++) {
            float lineWidth = (float) (i % 2 == 0 ? mLargeLineWidth : mSmallLineWidth);
            float y = mTopPadding+i * height;
            float lineLeftX = (width - lineWidth) / 2.0f;
            canvas.drawLine(lineLeftX, y, lineLeftX + lineWidth, y, this.mBackgroundLinePaint);
        }
    }

    private void drawLines(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        mProgressPaint.setColor(Color.parseColor("#55000000"));
        canvas.drawLine(width / 2, mTopPadding+mLineStrokeWidth, width / 2, height, mProgressPaint);
        if (mTouched) {
            mProgressPaint.setColor(mProgressScrollColor);
        } else {
            mProgressPaint.setColor(mProgressColor);
        }
        float thumbCy = getThumbCy();
        canvas.drawLine(width / 2, thumbCy, width / 2, height, mProgressPaint);
    }


    private void drawThumb(Canvas canvas) {
        float thumbCx = getThumbCx();
        float thumbCy = getThumbCy();
        canvas.drawCircle(thumbCx, thumbCy + mThumbShadowWidth, mThumbRadio - mThumbShadowWidth / 2, mThumbShadowPaint);
        canvas.drawCircle(thumbCx, thumbCy, mThumbRadio, mThumbBacPaint);
        canvas.drawCircle(thumbCx, thumbCy, mThumbRadio, mThumbPaint);
        if (mTouched) {
            mThumbLinePaint.setColor(mProgressScrollColor);
        } else {
            mThumbLinePaint.setColor(Color.parseColor("#FFEEEEEE"));
        }
        float x1 = (getWidth() - mThumbLineWidth) / 2;
        float x2 = x1 + mThumbLineWidth;
        for (int i = -1; i < 2; i++) {
            float y = thumbCy + i * mThumbLineDividerWidth;
            canvas.drawLine(x1, y, x2, y, mThumbLinePaint);
        }
    }

    private float getThumbCx() {
        return getWidth() / 2;
    }

    private float getThumbCy() {
        int height = getHeight();
        if (mMaxTouchHeight == 0) {
            mMaxTouchHeight = height - mThumbRadio - mThumbShadowWidth;
        }
        float realProgressLength = mMaxTouchHeight - mMinTouchHeight;
        return (1 - (progress / 2400.0f)) * realProgressLength + mThumbRadio + mThumbShadowWidth / 2+mTopPadding;
    }

    private void drawText(Canvas canvas) {
        float thumbCx = getThumbCx();
        float thumbCy = getThumbCy();
        if (mPath == null) {
            mPath = new Path();
            mTextBackgroundPaint.setPathEffect(new CornerPathEffect(3.0f));
        }
        mPath.reset();
        mPath.moveTo(thumbCx - mThumbRadio + 5.0f, thumbCy - mThumbRadio - mTextBackgroundHeight - mTextPadding);
        mPath.lineTo(thumbCx + mThumbRadio - 5.0f, thumbCy - mThumbRadio - mTextBackgroundHeight - mTextPadding);
        mPath.lineTo(thumbCx + mThumbRadio - 5.0f, thumbCy - mThumbRadio - mTextPadding - 10.0f);
        mPath.lineTo(thumbCx + 15.0f, thumbCy - mThumbRadio - mTextPadding - 10.0f);
        mPath.lineTo(thumbCx, thumbCy - mThumbRadio - mTextPadding);
        mPath.lineTo(thumbCx - 15.0f, thumbCy - mThumbRadio - mTextPadding - 10.0f);
        mPath.lineTo(thumbCx - mThumbRadio + 5.0f, thumbCy - mThumbRadio - mTextPadding - 10.0f);
        mPath.close();
        canvas.drawPath(mPath, mTextBackgroundPaint);
        int value = (int) ((progress / 100.0f) - 12);
        canvas.drawText(String.valueOf(value), thumbCx, thumbCy - mThumbRadio - mTextPadding - 20.0f, mTextPaint);
    }

    public interface OnProgressChangedListener {
        void onProgressChanged(String tag, int progress);
    }

    public interface OnDragFinishListener{
        void onDragFinish();
    }

}
