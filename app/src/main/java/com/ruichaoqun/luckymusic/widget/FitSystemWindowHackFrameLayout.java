package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.FrameLayout;

public class FitSystemWindowHackFrameLayout extends FrameLayout {

    public FitSystemWindowHackFrameLayout(Context context) {
        this(context, null);
    }

    public FitSystemWindowHackFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (needHack()) {
            setFitsSystemWindows(true);
        }
    }


    public boolean needHack() {
        return true;
    }

    @Override
    public final boolean fitSystemWindows(Rect rect) {
        if (!needHack()) {
            return super.fitSystemWindows(rect);
        }
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            rect.left = 0;
            rect.top = 0;
            rect.right = 0;
        }
        return super.fitSystemWindows(rect);
    }

    @Override
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (!needHack()) {
            return super.onApplyWindowInsets(windowInsets);
        }
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT_WATCH) {
            return super.onApplyWindowInsets(windowInsets.replaceSystemWindowInsets(0, 0, 0, windowInsets.getSystemWindowInsetBottom()));
        }
        return windowInsets;
    }

}
