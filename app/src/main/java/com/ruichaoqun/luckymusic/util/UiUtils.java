package com.ruichaoqun.luckymusic.util;

import android.content.Context;
import android.util.TypedValue;

public class UiUtils {
    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int i = 0;
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            i = context.getResources().getDimensionPixelSize(identifier);
        }
        if (i == 0) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, context.getResources().getDisplayMetrics());
        }
        return i;
    }

}
