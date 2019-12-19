package com.ruichaoqun.luckymusic.utils.drawhelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;


import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.utils.CommonUtils;
import com.ruichaoqun.luckymusic.utils.UiUtils;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 11:54
 * description:
 */
public class RoundedViewHelper {
    private static final int RADIUS = UiUtils.dp2px(CommonUtils.COMMON_RADIUS);
    final int finalRadius;
    final RectF mRectF;
    final Path path;

    public RoundedViewHelper(View view) {
        this(view, RADIUS);
    }

    public RoundedViewHelper(View view, int radius) {
        this.path = new Path();
        this.mRectF = new RectF();
        if (radius == 0) {
            radius = RADIUS;
        }
        this.finalRadius = radius;
        if (Build.VERSION.SDK_INT > 21) {
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                @TargetApi(24)
                @RequiresApi(api = 21)
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) RoundedViewHelper.this.finalRadius);
                }
            });
            view.setClipToOutline(true);
        } else if (Build.VERSION.SDK_INT < 18 && Build.VERSION.SDK_INT >= 11) {
            view.setLayerType(1, null);
        }
    }

    public void onDraw(Canvas canvas) {
        if (Build.VERSION.SDK_INT < 21 && Build.VERSION.SDK_INT >= 16) {
            this.mRectF.set(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight());
            this.path.addRoundRect(this.mRectF, (float) this.finalRadius, (float) this.finalRadius, Path.Direction.CW);
            canvas.clipPath(this.path);
        }
    }

    public static RoundedViewHelper onParseStyledAttributes(View view, Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CMRoundedCorner, 0, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CMRoundedCorner_cmRoundedCorner, 0);
        RoundedViewHelper roundedViewHelper = dimensionPixelSize > 0 ? new RoundedViewHelper(view, dimensionPixelSize) : null;
        obtainStyledAttributes.recycle();
        return roundedViewHelper;
    }

}
