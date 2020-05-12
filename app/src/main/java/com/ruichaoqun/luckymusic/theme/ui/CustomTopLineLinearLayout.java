package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;

/**
 * @author Rui Chaoqun
 * @date :2020/5/12 17:57
 * description:顶部带分割线的LinearLayout
 */
public class CustomTopLineLinearLayout extends LinearLayout implements OnThemeResetListener {

    private Paint mPaint = new Paint();

    private int mLineHeight;

    private ThemeResetter mThemeResetter = new ThemeResetter(this);

    public CustomTopLineLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaint.setColor(getLineColor());
        this.mLineHeight = 2;
        this.mPaint.setStrokeWidth((float) this.mLineHeight);
        onThemeReset();
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mThemeResetter != null) {
            mThemeResetter.checkIfNeedResetTheme();
        }
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        this.mThemeResetter.checkIfNeedResetTheme();
    }


    public void setTopDividerLineHeight(int height) {
        this.mLineHeight = height;
        this.mPaint.setStrokeWidth((float) this.mLineHeight);
        //ViewGroup默认setWillNotDraw(true),不执行onDraw方法，如果我们想重写onDraw方法，setWillNotDraw(false)
        setWillNotDraw(mLineHeight == 0);
        invalidate();
        //默认line绘制在paddingTop上
        setPadding(getPaddingLeft(), this.mLineHeight, getPaddingRight(), getPaddingBottom());
    }

    public int getLineColor() {
        return ResourceRouter.getInstance().getDividerColor();
    }

    @Override
    public void onThemeReset() {
        if (mThemeResetter != null) {
            mThemeResetter.saveCurrentThemeInfo();
        }
        this.mPaint.setColor(getLineColor());
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0.0f, this.mLineHeight / 2, getWidth(), this.mLineHeight / 2, this.mPaint);
    }
}
