package com.ruichaoqun.luckymusic.widget.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 16:46
 * description:
 */
public class PaddingLeftBackgroundDrawable extends Drawable {
    public static int DIRECTION_BOTTOM = 2;
    public static int DIRECTION_TOP = 1;
    /* access modifiers changed from: private */
    public Integer mBackgroundColor;
    private boolean mBothPadding;
    /* access modifiers changed from: private */
    public boolean mForCard;
    /* access modifiers changed from: private */
    public boolean mForPress;
    /* access modifiers changed from: private */
    public Integer mLineColor;
    private int mLineDirection;
    private int mLineHeight;
    private Paint mLinePaint;
    private MyConstantState mMyConstantState;
    /* access modifiers changed from: private */
    public int mPaddingLeft;

    /* compiled from: ProGuard */
    class MyConstantState extends ConstantState {
        MyConstantState() {
        }

        @Override
        @NonNull
        public Drawable newDrawable() {
            if (PaddingLeftBackgroundDrawable.this.mLineColor == null && PaddingLeftBackgroundDrawable.this.mBackgroundColor == null) {
                return new PaddingLeftBackgroundDrawable(PaddingLeftBackgroundDrawable.this.mPaddingLeft, PaddingLeftBackgroundDrawable.this.mForCard, PaddingLeftBackgroundDrawable.this.mForPress);
            }
            return new PaddingLeftBackgroundDrawable(PaddingLeftBackgroundDrawable.this.mPaddingLeft, PaddingLeftBackgroundDrawable.this.mLineColor.intValue(), PaddingLeftBackgroundDrawable.this.mBackgroundColor.intValue());
        }

        @Override
        public int getChangingConfigurations() {
            return 0;
        }
    }

    public PaddingLeftBackgroundDrawable(int paddingLeft, boolean forCard, boolean forPress) {
        this(paddingLeft, forCard, forPress, false);
    }

    public PaddingLeftBackgroundDrawable(int paddingLeft, boolean forCard, boolean forPress, boolean bothPadding) {
        this.mLinePaint = new Paint();
        this.mLineDirection = DIRECTION_BOTTOM;
        this.mPaddingLeft = paddingLeft;
        this.mForCard = false;
        this.mForPress = forPress;
        this.mBothPadding = bothPadding;
        init();
    }

    public PaddingLeftBackgroundDrawable(int paddingLeft, int lineColor, int backgroundColor) {
        this.mLinePaint = new Paint();
        this.mLineDirection = DIRECTION_BOTTOM;
        this.mLineColor = Integer.valueOf(lineColor);
        this.mBackgroundColor = Integer.valueOf(backgroundColor);
        this.mPaddingLeft = paddingLeft;
        init();
    }

    public PaddingLeftBackgroundDrawable(int paddingLeft, int lineColor, int backgroundColor, int lineDirection) {
        this(paddingLeft, lineColor, backgroundColor);
        setLineDirection(lineDirection);
    }

    private void init() {
        this.mLinePaint.setColor(getLineColor());
        this.mLineHeight = 1;
        this.mLinePaint.setStrokeWidth((float) this.mLineHeight);
    }

    private int getBackgroundColor() {
        if (this.mBackgroundColor != null) {
            return this.mBackgroundColor.intValue();
        }
        return getBackgroundColor(this.mForCard, this.mForPress);
    }

    public void setLineDirection(int lineDirection) {
        this.mLineDirection = lineDirection;
    }

    public static int getBackgroundColor(boolean mForCard, boolean mForPress) {
        if (!mForPress) {
            if (!mForCard) {
                return Color.TRANSPARENT;
            }
            if (ResourceRouter.getInstance().isNightTheme()) {
                return Color.parseColor("#7FFFFFFF");
            }
            if (ResourceRouter.getInstance().isGeneralRuleTheme()) {
                return Color.WHITE;
            }
            if (ResourceRouter.getInstance().isCustomLightTheme()) {
                return Color.parseColor("#33FFFFFF");
            }
            return Color.parseColor("#19FFFFFF");
        } else if (!ResourceRouter.getInstance().isNightTheme()) {
            return Color.parseColor("#19000000");
        } else {
            return Color.parseColor("#19FFFFFF");
        }
    }

    /* access modifiers changed from: protected */
    public int getLineColor() {
        if (this.mLineColor != null) {
            return this.mLineColor.intValue();
        }
        return ResourceRouter.getInstance().getLineColor();
    }

    @Override
    public void draw(Canvas canvas) {
        int backgroundColor = getBackgroundColor();
        if (backgroundColor != 0) {
            canvas.drawColor(backgroundColor);
        }
        if (this.mPaddingLeft < 0) {
            return;
        }
        if (this.mLineDirection == DIRECTION_TOP) {
            canvas.drawLine((float) this.mPaddingLeft, 0.0f, this.mBothPadding ? (float) (getBounds().width() - this.mPaddingLeft) : (float) getBounds().width(), 0.0f, this.mLinePaint);
        } else {
            canvas.drawLine((float) this.mPaddingLeft, (float) (getBounds().height() - this.mLineHeight), this.mBothPadding ? (float) (getBounds().width() - this.mPaddingLeft) : (float) getBounds().width(), (float) (getBounds().height() - this.mLineHeight), this.mLinePaint);
        }
    }

    @Override
    public void setAlpha(int i) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    @Nullable
    public ConstantState getConstantState() {
        if (this.mMyConstantState == null) {
            this.mMyConstantState = new MyConstantState();
        }
        return this.mMyConstantState;
    }
}
