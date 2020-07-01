package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;

/**
 * @author Rui Chaoqun
 * @date :2020/6/23 14:32
 * description:
 */
public class CustomThemeIconImageView extends CustomThemeImageView {
    public CustomThemeIconImageView(Context context) {
        super(context);
        onThemeReset();
    }

    public CustomThemeIconImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onThemeReset();
        Log.w("AAAAAA","CustomThemeIconImageView");
    }

    @Override
    public void onThemeReset() {
        super.onThemeReset();
        resetTheme(getDrawable());
    }

    private void resetTheme(Drawable drawable) {
        if(drawable != null){
            ResourceRouter router = ResourceRouter.getInstance();
            int themeColor = router.getThemeColor();
            ThemeHelper.configDrawableTheme(drawable, themeColor);
        }
    }
}

