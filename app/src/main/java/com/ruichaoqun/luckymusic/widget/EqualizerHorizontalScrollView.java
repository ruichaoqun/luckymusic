package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class EqualizerHorizontalScrollView  extends HorizontalScrollView implements EqualizerChartView.OnChartViewScrollListener {
    private OnEqualizerScrollViewScrollListener mOnEqualizerScrollViewScrollListener;

    public EqualizerHorizontalScrollView(Context context) {
        super(context);
    }

    public EqualizerHorizontalScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EqualizerHorizontalScrollView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    public void setOnEqualizerScrollViewScrollListener(OnEqualizerScrollViewScrollListener listener) {
        this.mOnEqualizerScrollViewScrollListener = listener;
    }

    public void onChartViewScroll(float f2) {
        scrollTo((int) (((float) (getChildAt(0).getWidth() - getWidth())) * f2), 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        motionEvent.getAction();
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.mOnEqualizerScrollViewScrollListener != null) {
            this.mOnEqualizerScrollViewScrollListener.onEqualizerScrollViewScroll(((float) getScrollX()) / (((float) getChildAt(0).getWidth()) - ((float) getWidth())));
        }
    }

    public interface OnEqualizerScrollViewScrollListener{
        void onEqualizerScrollViewScroll(float ratio);
    }
}