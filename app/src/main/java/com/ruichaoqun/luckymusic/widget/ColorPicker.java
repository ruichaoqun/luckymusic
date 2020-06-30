package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.utils.UiUtils;

/**
 * @author Rui Chaoqun
 * @date :2020/6/29 10:13
 * description:
 */
public class ColorPicker extends View {
    private static final int[] COLORS = {Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.RED};
    private int mBarHeight;
    private int mBarWidth;
    private float[] mHSVColor = new float[3];
    private int mHeight;
    private float[] mHue = {0.0f, 1.0f, 1.0f};
    private Paint mHueBarPaint;
    private RectF mHueBarRectF;
    private Drawable mHueThumbDrawable;
    private Rect mHueThumbRect;
    private OnColorChangedListener mOnColorChangedListener;
    private int mThumbRadius;
    private Paint mValueBarPaint;
    private RectF mValueBarRectF;
    private Shader mValueBarShader;
    private Drawable mValueThumbDrawable;
    private Rect mValueThumbRect;

    public interface OnColorChangedListener {
        void onColorChanged(int color);
    }

    public ColorPicker(Context context) {
        super(context);
        init(context);
    }

    public ColorPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ColorPicker(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init(context);
    }

    private void init(Context context) {
        this.mHueThumbDrawable = context.getResources().getDrawable(R.drawable.ic_thumb);
        this.mValueThumbDrawable = this.mHueThumbDrawable.getConstantState().newDrawable();
        this.mThumbRadius = this.mHueThumbDrawable.getIntrinsicWidth() / 2;
        this.mHeight = (this.mThumbRadius * 4) + UiUtils.dp2px(17.0f);
        this.mBarHeight = UiUtils.dp2px(3.0f);
        this.mHueBarPaint = new Paint(1);
        this.mHueBarPaint.setStyle(Paint.Style.STROKE);
        this.mHueBarPaint.setStrokeWidth(mBarHeight);
        this.mValueBarPaint = new Paint(this.mHueBarPaint);

        for (int i = 0; i < COLORS.length; i++) {
            Color.colorToHSV(COLORS[i], this.mHSVColor);
            Log.w("AAAAA", mHSVColor[0] + "   " + mHSVColor[1] + "    " + mHSVColor[2]);
        }
    }

    public void setColor(int color) {
        //获取HSV模型
        Color.colorToHSV(color, this.mHSVColor);
        //饱和度设为1
        mHSVColor[1] = 1.0f;
        //色调
        this.mHue[0] = mHSVColor[0];
        if (mBarWidth > 0) {
            int round = Math.round(((float) mBarWidth) * (mHSVColor[0] / 360.0f)) + this.mThumbRadius;
            int round2 = Math.round(((float) this.mBarWidth) * this.mHSVColor[2]);
            this.mHueThumbRect = new Rect(round - mThumbRadius, 0, round + mThumbRadius, mThumbRadius * 2);
            this.mValueThumbRect = new Rect(round2, mHeight - (mThumbRadius * 2), round2 + mThumbRadius * 2, mHeight);
            this.mValueBarShader = new LinearGradient(this.mValueBarRectF.left, this.mValueBarRectF.top, this.mValueBarRectF.right, this.mValueBarRectF.bottom, new int[]{Color.BLACK, Color.HSVToColor(this.mHue)}, (float[]) null, Shader.TileMode.CLAMP);
            this.mValueBarPaint.setShader(this.mValueBarShader);
            invalidate();
        }
    }

    public void setOnColorChangedListener(OnColorChangedListener onColorChangedListener) {
        this.mOnColorChangedListener = onColorChangedListener;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), this.mHeight);
    }

    @Override
    public void onSizeChanged(int width, int height, int ow, int oh) {
        this.mHueBarRectF = new RectF(mThumbRadius, mThumbRadius - mBarHeight / 2, width - mThumbRadius, mThumbRadius + mBarHeight / 2);
        this.mValueBarRectF = new RectF(mThumbRadius, height - mThumbRadius - mBarHeight / 2, width - mThumbRadius, height - mThumbRadius + mBarHeight / 2);
        this.mBarWidth = (int) (this.mHueBarRectF.right - this.mHueBarRectF.left);
        int round = Math.round(((float) this.mBarWidth) * (this.mHSVColor[0] / 360.0f));
        int round2 = Math.round(((float) this.mBarWidth) * this.mHSVColor[2]);
        this.mHueThumbRect = new Rect(round, 0, round + mThumbRadius*2, mThumbRadius * 2);
        this.mValueThumbRect = new Rect(round2, height - (mThumbRadius * 2), round2 + mThumbRadius*2, height);
        LinearGradient linearGradient = new LinearGradient(this.mHueBarRectF.left, this.mHueBarRectF.top, this.mHueBarRectF.right, this.mHueBarRectF.bottom, COLORS, null, Shader.TileMode.CLAMP);
        this.mValueBarShader = new LinearGradient(mValueBarRectF.left, mValueBarRectF.top, mValueBarRectF.right, mValueBarRectF.bottom, new int[]{Color.BLACK, Color.HSVToColor(this.mHue)},  null, Shader.TileMode.CLAMP);
        this.mHueBarPaint.setShader(linearGradient);
        this.mValueBarPaint.setShader(this.mValueBarShader);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mHueBarRectF, (float) (mBarHeight / 2), (float) (mBarHeight / 2), this.mHueBarPaint);
        canvas.drawRoundRect(mValueBarRectF, (float) (mBarHeight / 2), (float) (mBarHeight / 2), this.mValueBarPaint);
        this.mHueThumbDrawable.setBounds(this.mHueThumbRect);
        this.mHueThumbDrawable.draw(canvas);
        this.mValueThumbDrawable.setBounds(this.mValueThumbRect);
        this.mValueThumbDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getActionMasked();
        if(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE){
            if(y <= mHueThumbRect.bottom){
                if(x >= mHueBarRectF.left && x <= mHueBarRectF.right){
                    mHueThumbRect.left = Math.round(x - mThumbRadius);
                    mHueThumbRect.right = Math.round(x + mThumbRadius);
                }
                mHue[0] = 360 * ((x-mHueBarRectF.left)/mBarWidth);
                mHSVColor[0] = mHue[0];
                this.mValueBarShader = new LinearGradient(mValueBarRectF.left, mValueBarRectF.top, mValueBarRectF.right, mValueBarRectF.bottom, new int[]{Color.BLACK, Color.HSVToColor(this.mHue)},  null, Shader.TileMode.CLAMP);
                this.mValueBarPaint.setShader(this.mValueBarShader);
            }else if(y >= mValueThumbRect.top){
                if(x >= mThumbRadius && x <= mBarWidth - mThumbRadius){
                    mValueThumbRect.left = Math.round(x - mThumbRadius);
                    mValueThumbRect.right = Math.round(x + mThumbRadius);
                }
                mHSVColor[2] = (x-mValueBarRectF.left)/mBarWidth;
            }
            if(mOnColorChangedListener != null){
                mOnColorChangedListener.onColorChanged(Color.HSVToColor(mHSVColor));
            }
            invalidate();
        }
        return true;
    }
}

