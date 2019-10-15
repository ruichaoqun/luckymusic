package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.utils.UiUtils;

public class StatusBarHolderView extends View {
    private boolean isColorDrawable;
    private boolean isTranslucent;

    public StatusBarHolderView(Context context) {
        this(context, null);
    }

    public StatusBarHolderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isTranslucent = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(UiUtils.getStatusBarHeight(getContext()), MeasureSpec.getMode(heightMeasureSpec)));
    }

    /**
     * 状态栏是否透明
     * @param isTranslucent 是否透明
     */
    public void setStatusBarTranslucent(boolean isTranslucent) {
        this.isTranslucent = isTranslucent;
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable) {
        this.isColorDrawable = drawable instanceof ColorDrawable;
        if (this.isColorDrawable && !this.isTranslucent) {
            drawable = new ColorDrawable(ThemeHelper.getColor700from500(((ColorDrawable) drawable).getColor()));
        }
        super.setBackgroundDrawable(drawable);
    }

    @Override
    public void setBackgroundColor(int i) {
        setBackgroundDrawable(new ColorDrawable(i));
    }

    @Override
    public void setBackgroundResource(int i) {
        setBackgroundDrawable(getContext().getResources().getDrawable(i));
    }

    /* access modifiers changed from: protected */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.isColorDrawable && !this.isTranslucent) {
            canvas.drawARGB(33, 0, 0, 0);
        }
    }

}
