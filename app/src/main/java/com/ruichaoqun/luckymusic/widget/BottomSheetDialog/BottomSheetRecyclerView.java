package com.ruichaoqun.luckymusic.widget.BottomSheetDialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class BottomSheetRecyclerView extends RecyclerView {
    private float screenHeigtRate = 0.618f;

    public BottomSheetRecyclerView(@NonNull Context context) {
        super(context);
    }

    public BottomSheetRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomSheetRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public float getScreenHeigtRate() {
        return this.screenHeigtRate;
    }

    public void setScreenHeigtRate(float f2) {
        this.screenHeigtRate = f2;
    }

    /* access modifiers changed from: protected */
    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        int measureSpecMode = MeasureSpec.EXACTLY;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getResources().getDisplayMetrics().widthPixels, MeasureSpec.EXACTLY);
        float f2 = this.screenHeigtRate;
        int i5 = (int) (((float) getResources().getDisplayMetrics().heightPixels) * f2);
        if (f2 == 0.618f) {
            measureSpecMode = MeasureSpec.AT_MOST;
        }
        super.onMeasure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(i5, measureSpecMode));
    }
}
