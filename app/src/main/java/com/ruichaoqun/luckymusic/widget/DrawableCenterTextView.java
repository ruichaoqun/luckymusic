package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ui.CustomThemeTextView;
import com.ruichaoqun.luckymusic.utils.UiUtils;

public class DrawableCenterTextView  extends CustomThemeTextView {
    private Rect mBounds;
    private float mFontHeight;
    private float mHintLeftMargin;
    private Paint mHintPaint;
    private float mHintRadius;
    private String mHintText;
    private float mHintTextSize;
    private boolean mNeedRightHint;

    public DrawableCenterTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public DrawableCenterTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBounds = new Rect();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DrawableCenterTextView, 0, 0);
        this.mNeedRightHint = obtainStyledAttributes.getBoolean(R.styleable.DrawableCenterTextView_needRightHint, false);
        this.mHintLeftMargin = obtainStyledAttributes.getDimension(R.styleable.DrawableCenterTextView_hintLeftMargin, (float) UiUtils.dp2px(4.0f));
        this.mHintRadius = obtainStyledAttributes.getDimension(R.styleable.DrawableCenterTextView_hintRadius, (float) UiUtils.dp2px(6.0f));
        this.mHintTextSize = obtainStyledAttributes.getDimension(R.styleable.DrawableCenterTextView_hintTextSize, (float) UiUtils.dp2px(5.0f));
        this.mHintText = obtainStyledAttributes.getString(R.styleable.DrawableCenterTextView_hintText);
        initHintParams();
        obtainStyledAttributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        if (getCompoundDrawables()[0] != null || getCompoundDrawables()[2] != null) {
            getPaint().getTextBounds(getText().toString(), 0, getText().length(), this.mBounds);
        }
    }

    public void setNeedRightHint(boolean z) {
        if (z != this.mNeedRightHint) {
            this.mNeedRightHint = z;
            initHintParams();
            invalidate();
        }
    }

    private void initHintParams() {
        if (this.mNeedRightHint) {
            this.mHintPaint = new Paint(1);
            this.mHintPaint.setColor(getContext().getResources().getColor(R.color.color_ff3a3a));
            this.mHintPaint.setTextAlign(Paint.Align.CENTER);
            this.mHintPaint.setTextSize(this.mHintTextSize);
            Paint.FontMetrics fontMetrics = this.mHintPaint.getFontMetrics();
            this.mFontHeight = (float) Math.ceil((double) (fontMetrics.descent - fontMetrics.top));
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i2;
        Drawable[] compoundDrawables = getCompoundDrawables();
        int i3 = 0;
        Drawable drawable = compoundDrawables[0];
        Drawable drawable2 = compoundDrawables[2];
        if (drawable == null && drawable2 == null) {
            super.onDraw(canvas);
            return;
        }
        getPaint().setColor(getCurrentTextColor());
        int compoundDrawablePadding = getCompoundDrawablePadding();
        if (drawable == null) {
            i2 = 0;
        } else {
            i2 = drawable.getIntrinsicWidth();
        }
        int width = this.mBounds.width() + i2 + (drawable2 == null ? 0 : drawable2.getIntrinsicWidth()) + (drawable != null ? compoundDrawablePadding : 0);
        if (drawable2 != null) {
            i3 = compoundDrawablePadding;
        }
        float f2 = (float) (width + i3);
        canvas.save();
        if (drawable != null) {
            canvas.translate((((float) getWidth()) - f2) / 2.0f, (float) ((getHeight() - drawable.getIntrinsicHeight()) / 2));
            drawable.draw(canvas);
            canvas.translate((float) (drawable.getIntrinsicWidth() + compoundDrawablePadding), ((((float) getHeight()) - getTextSize()) / 2.0f) - ((float) ((getHeight() - drawable.getIntrinsicHeight()) / 2)));
        } else {
            canvas.translate((((float) getWidth()) - f2) / 2.0f, (((float) getHeight()) - getTextSize()) / 2.0f);
        }
        canvas.drawText(getText(), 0, getText().length(), 0.0f, (getTextSize() / 2.0f) - ((getPaint().descent() + getPaint().ascent()) / 2.0f), getPaint());
        if (drawable2 != null) {
            canvas.translate((float) (this.mBounds.width() + compoundDrawablePadding), ((float) ((getHeight() - drawable2.getIntrinsicHeight()) / 2)) - ((((float) getHeight()) - getTextSize()) / 2.0f));
            drawable2.draw(canvas);
        }
        canvas.restore();
        if (this.mNeedRightHint) {
            canvas.translate(((float) (getWidth() / 2)) + (f2 / 2.0f) + this.mHintLeftMargin + this.mHintRadius, ((float) (getHeight() / 2)) - this.mHintRadius);
            canvas.drawCircle(0.0f, 0.0f, this.mHintRadius, this.mHintPaint);
            this.mHintPaint.setColor(getCurrentTextColor());
            canvas.translate(0.0f, this.mFontHeight / 4.0f);
            canvas.drawText(this.mHintText, 0.0f, 0.0f, this.mHintPaint);
            this.mHintPaint.setColor(getContext().getResources().getColor(R.color.color_ff3a3a));
        }
    }
}