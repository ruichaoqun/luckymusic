package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;
import com.ruichaoqun.luckymusic.utils.drawhelper.RoundedViewHelper;

/**
 * @author Rui Chaoqun
 * @date :2020/6/23 15:25
 * description:
 */
public class CustomThemeRelativeLayout extends RelativeLayout implements OnThemeResetListener {
    @Deprecated
    protected int bgPaddingLeft;
    protected int bgType;
    private boolean forCard;
    protected ThemeResetter mThemeResetter = new ThemeResetter(this);
    protected int newBgPaddingLeft;

    public CustomThemeRelativeLayout(Context context) {
        super(context);
    }

    public CustomThemeRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
//        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, c.o.CustomThemeContainer, 0, 0);
//        this.forCard = obtainStyledAttributes.getBoolean(c.o.CustomThemeContainer_forCard, false);
//        this.newBgPaddingLeft = obtainStyledAttributes.getDimensionPixelSize(c.o.CustomThemeContainer_newBgPaddingLeft, 0);
//        this.bgType = obtainStyledAttributes.getInteger(c.o.CustomThemeContainer_bgType, 0);
//        obtainStyledAttributes.recycle();
        onThemeReset();
    }

    @Deprecated
    public void setBgPaddingLeft(int i2, boolean z) {
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ThemeResetter themeResetter = this.mThemeResetter;
        if (themeResetter != null) {
            themeResetter.checkIfNeedResetTheme();
        }
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        this.mThemeResetter.checkIfNeedResetTheme();
    }

    public boolean needBackground() {
        return true;
    }


    public void setNewBgPaddingLeft(int i2, boolean z) {
        ThemeHelper.configPaddingBg(this, i2, z);
        this.newBgPaddingLeft = i2;
        this.forCard = z;
    }

    @Override
    public void onThemeReset() {
        ThemeResetter themeResetter = this.mThemeResetter;
        if (themeResetter != null) {
            themeResetter.saveCurrentThemeInfo();
        }
        if (needBackground()) {
            int i2 = this.newBgPaddingLeft;
            if (i2 > 0) {
                setNewBgPaddingLeft(i2, this.forCard);
            } else {
                ThemeHelper.configBg(this, this.bgType, this.forCard);
            }
        }
    }

    public int getBgType() {
        return this.bgType;
    }

    public void setBgType(int i2) {
        this.bgType = i2;
        this.newBgPaddingLeft = 0;
    }

}

