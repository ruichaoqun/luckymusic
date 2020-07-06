package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;

/**
 * @author Rui Chaoqun
 * @date :2020/6/23 14:32
 * description:使用本地图片资源作为icon的imageview
 */
public class CustomThemeIconImageView extends CustomThemeImageView {
    private boolean mNeedApplyThemeColor;
    private ColorStateList normalDrawableColor;
    private boolean mNeedSelect;

    public CustomThemeIconImageView(Context context) {
        super(context);
        onThemeReset();
    }

    public CustomThemeIconImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CustomThemeIconImageView, 0, 0);
        mNeedApplyThemeColor = obtainStyledAttributes.getBoolean(R.styleable.CustomThemeIconImageView_needApplyThemeColor,true);
        mNeedSelect = obtainStyledAttributes.getBoolean(R.styleable.CustomThemeIconImageView_needSelected,false);
        normalDrawableColor = obtainStyledAttributes.getColorStateList(R.styleable.CustomThemeIconImageView_normalDrawableColor);
        obtainStyledAttributes.recycle();
        onThemeReset();
    }

    @Override
    public void onThemeReset() {
        super.onThemeReset();
        resetTheme(getDrawable());
    }

    private void resetTheme(Drawable drawable) {
        if(drawable != null){
            ResourceRouter router = ResourceRouter.getInstance();
            int color = router.getThemeColor();
            if(!mNeedApplyThemeColor){
                if(normalDrawableColor != null){
                    color = normalDrawableColor.getDefaultColor();
                }
                if(router.isNightTheme()){
                    color = router.getNightColor(color);
                }
                ThemeHelper.configDrawableTheme(drawable, color);
                return;
            }
            ThemeHelper.configDrawableTheme(drawable, color);
        }
        if(mNeedSelect){
            ThemeHelper.configBg(this, 0, false);
        }
    }
}

