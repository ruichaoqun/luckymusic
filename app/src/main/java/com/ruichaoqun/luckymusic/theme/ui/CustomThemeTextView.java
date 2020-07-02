package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;
import com.ruichaoqun.luckymusic.utils.ReflectUtils;

/**
 * 可随主题切换而变化的TextView，主要包含文字颜色，是否可点击，drawable颜色是否随主题变化
 */
public class CustomThemeTextView extends AppCompatTextView implements OnThemeResetListener {
    private boolean mNeedApplyDrawableColor;
    private boolean mNeedApplyTextColor;
    private boolean mNeedSelect;
    private ColorStateList normalDrawableColor;
    private ColorStateList mColorsOriginal;
    protected ThemeResetter mThemeResetter = new ThemeResetter(this);


    public CustomThemeTextView(Context context) {
        super(context);
    }

    public CustomThemeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mNeedApplyTextColor = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CustomThemeTextView, 0, 0);
        this.mNeedApplyDrawableColor = obtainStyledAttributes.getBoolean(R.styleable.CustomThemeTextView_needApplyDrawableColor,false);
        this.mNeedApplyTextColor = obtainStyledAttributes.getBoolean(R.styleable.CustomThemeTextView_needApplyTextColor,true);
        this.mNeedSelect = obtainStyledAttributes.getBoolean(R.styleable.CustomThemeTextView_needSelected,false);
        this.normalDrawableColor = obtainStyledAttributes.getColorStateList(R.styleable.CustomThemeTextView_normalDrawableColor);
        obtainStyledAttributes.recycle();
        setTextColorOriginal(getTextColors());
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mThemeResetter.checkIfNeedResetTheme();
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        this.mThemeResetter.checkIfNeedResetTheme();
    }


    public void setTextColorOriginal(int color) {
        setTextColorOriginal(ColorStateList.valueOf(color));
    }

    public void setTextColorOriginal(ColorStateList colorStateList) {
        this.mColorsOriginal = colorStateList;
        onThemeReset();
    }

    public void setBackgroundDrawableOriginal(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        onThemeReset();
    }

    public void setCompoundDrawablesWithIntrinsicBoundsOriginal(int i2, int i3, int i4, int i5) {
        super.setCompoundDrawablesWithIntrinsicBounds(i2, i3, i4, i5);
        onThemeReset();
    }

    public void setCompoundDrawablesWithIntrinsicBoundsOriginal(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        onThemeReset();
    }

    @Override
    public void onThemeReset() {
        if(mThemeResetter != null){
            mThemeResetter.saveCurrentThemeInfo();
        }
        ResourceRouter resourceRouter = ResourceRouter.getInstance();
        //文字适配主题，默认true
        if(mNeedApplyTextColor){
            if(resourceRouter.isNightTheme() || resourceRouter.isCustomBgTheme() || resourceRouter.isCustomDarkTheme()){
                //如果是夜晚模式或自定义背景模式或者暗黑模式
                int color = resourceRouter.getColorByDefaultColor(mColorsOriginal.getDefaultColor());
                setTextColor(color);
            }else{
                //否则，设置原来的颜色
                setTextColor(mColorsOriginal);
            }
        }

        //是否可点击，可点击需要设置background
        if(mNeedSelect){
            setBackgroundDrawable(ThemeHelper.getBgSelector(getContext(), -1));
        }


        int drawableColor = 0;
        //如果图片颜色需要适配主题
        if(mNeedApplyDrawableColor){
            if(normalDrawableColor != null){
                drawableColor = normalDrawableColor.getDefaultColor();
            }
            int color = drawableColor != 0?drawableColor:resourceRouter.getThemeColor();
            if(resourceRouter.isNightTheme()){//夜间模式，获取对应的夜间模式颜色
                drawableColor = resourceRouter.getIconNightColor(color);
            }
        }
        if(drawableColor != 0){
            Drawable[] drawables = getCompoundDrawables();
            for (int i = 0; i < drawables.length; i++) {
                Drawable d = drawables[i];
                if (d != null) {
                    ThemeHelper.configDrawableTheme(d, drawableColor);
                }
            }
        }
    }
}
