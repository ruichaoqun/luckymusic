package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;
import com.ruichaoqun.luckymusic.utils.ReflectUtils;

public class CustomThemeTextView extends AppCompatTextView implements OnThemeResetListener {
    private boolean mNeedApplyDrawableColor;
    private boolean mNeedApplyTextColor;
    private ColorStateList mColorsOriginal;
    protected ThemeResetter mThemeResetter = new ThemeResetter(this);


    public CustomThemeTextView(Context context) {
        super(context);
    }

    public CustomThemeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mNeedApplyTextColor = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CustomTheme, 0, 0);
        this.mNeedApplyDrawableColor = obtainStyledAttributes.getBoolean(R.styleable.CustomTheme_needApplyDrawableColor,false);
        obtainStyledAttributes.recycle();
        setTextColorOriginal(getTextColors());
    }

    public void setTextColorOriginal(int color) {
        setTextColorOriginal(ColorStateList.valueOf(color));
    }

    public void setTextColorOriginal(ColorStateList colorStateList) {
        this.mColorsOriginal = colorStateList;
        onThemeReset();
    }


    @Override
    public void onThemeReset() {
        if(mThemeResetter != null){
            mThemeResetter.saveCurrentThemeInfo();
        }
        ResourceRouter resourceRouter = ResourceRouter.getInstance();
        if(mNeedApplyTextColor){
            if(resourceRouter.isNightTheme() || resourceRouter.isCustomBgTheme() || resourceRouter.isCustomDarkTheme()){
                int color = mColorsOriginal.getDefaultColor();
                int color1 = Color.argb(Color.alpha(color),255-Color.red(color),255-Color.blue(color),255-Color.green(color));
                setTextColor(color1);
            }
        }
    }
}
