package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.ruichaoqun.luckymusic.utils.UiUtils;

public class EqualizerChartView extends View {
    private Paint mLinePaint;
    private Paint mTextPaint;
    private Paint mVisibleRectPaint;
    private Paint mBackLinePaint;
    private Paint mArrowPaint;

    private float mTextMarginBottom;

    private float[] mFloatsX;
    private float[] mFloatsY;
    private int[] mProgress = new int[10];

    private Path mPath;



    public EqualizerChartView(Context context) {
        super(context);
        init();
    }

    public EqualizerChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mTextMarginBottom = UiUtils.dp2px(5.0f);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(UiUtils.dp2px(3.0f));
        mLinePaint.setColor(Color.RED);
        mLinePaint.setPathEffect(new CornerPathEffect(UiUtils.dp2px(20.0f)));
        mLinePaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(UiUtils.sp2px(8.0f));
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mVisibleRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mVisibleRectPaint.setColor(Color.parseColor("#22000000"));

        mBackLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackLinePaint.setStrokeWidth(UiUtils.dp2px(0.5f));
        mBackLinePaint.setColor(Color.parseColor("#33000000"));

        mArrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArrowPaint.setColor(Color.parseColor("#55000000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        canvas.drawColor(Color.RED);
        if(mFloatsX == null){
            mFloatsX = new float[10];
            mFloatsY = new float[7];
            float xStep = width/11.0f;
            float yStep = height/8;
            for (int i = 1; i <= mFloatsX.length; i++) {
                mFloatsX[i] = i*xStep;
            }
            for (int i = 1; i <= mFloatsY.length; i++) {
                mFloatsY[i] = i*yStep;
            }
        }
        if(mPath == null){
            mPath = new Path();
        }
        mPath.reset();
        mPath.moveTo(0,height/2);
        for (int i = 0; i < mFloatsX.length; i++) {
            canvas.drawLine(mFloatsX[i] - 2.0f,0,mFloatsX[i] - 2.0f,height,mBackLinePaint);
            canvas.drawLine(mFloatsX[i] + 2.0f,0,mFloatsX[i] + 2.0f,height,mBackLinePaint);
            int value = (int) ((mProgress[i]/100.0f) - 12);
            canvas.drawText(String.valueOf(value),mFloatsX[i],height-mTextMarginBottom,mTextPaint);
            mPath.lineTo(mFloatsX[i],(1-mProgress[i]/2400.0f)*height);
        }
        for (int i = 0; i < mFloatsY.length; i++) {
            canvas.drawLine(0,mFloatsY[i],width,mFloatsY[i],mBackLinePaint);
        }
        mPath.lineTo(width,height/2);
        canvas.drawPath(mPath,mLinePaint);
    }
}
