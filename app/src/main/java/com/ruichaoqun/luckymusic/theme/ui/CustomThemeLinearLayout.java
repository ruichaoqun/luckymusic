package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;
import com.ruichaoqun.luckymusic.utils.drawhelper.RoundedViewHelper;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 10:51
 * description:
 */
public class CustomThemeLinearLayout extends LinearLayout implements OnThemeResetListener {
    protected int bgType;
    private boolean forCard;
    private boolean mNeedThemeResetWithOnAttachedToWindow = true;
    private RoundedViewHelper mRoundedViewHelper;
    protected ThemeResetter mThemeResetter = new ThemeResetter(this);
    protected int newBgPaddingLeft;

    public void setNeedThemeResetWithOnAttachedToWindow(boolean z) {
        this.mNeedThemeResetWithOnAttachedToWindow = z;
    }

    /* access modifiers changed from: protected */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mNeedThemeResetWithOnAttachedToWindow && this.mThemeResetter != null) {
            this.mThemeResetter.checkIfNeedResetTheme();
        }
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        if (this.mNeedThemeResetWithOnAttachedToWindow) {
            this.mThemeResetter.checkIfNeedResetTheme();
        }
    }

    public CustomThemeLinearLayout(Context context) {
        super(context);
    }

    public CustomThemeLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CustomThemeContainer, 0, 0);
        this.forCard = obtainStyledAttributes.getBoolean(R.styleable.CustomThemeContainer_forCard, false);
        this.newBgPaddingLeft = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CustomThemeContainer_newBgPaddingLeft, 0);
        this.bgType = obtainStyledAttributes.getInteger(R.styleable.CustomThemeContainer_bgType, 0);
        obtainStyledAttributes.recycle();
        this.mRoundedViewHelper = RoundedViewHelper.onParseStyledAttributes(this, context, attributeSet);
        onParseStyledAttributes(context, attributeSet);
        onThemeReset();
    }

    /* access modifiers changed from: protected */
    @Override
    public void onDraw(Canvas canvas) {
        if (this.mRoundedViewHelper != null) {
            this.mRoundedViewHelper.onDraw(canvas);
        }
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onParseStyledAttributes(Context context, AttributeSet attributeSet) {
    }

    public void setPaddingLeft(int padding, boolean forCard) {
        ThemeHelper.configPaddingBg(this, padding, forCard);
        this.newBgPaddingLeft = padding;
        this.forCard = forCard;
    }

    @Override
    public void onThemeReset() {
        if (this.mThemeResetter != null) {
            this.mThemeResetter.saveCurrentThemeInfo();
        }
        if (this.newBgPaddingLeft > 0) {
            setPaddingLeft(this.newBgPaddingLeft, this.forCard);
        } else {
            ThemeHelper.configBg(this, this.bgType, this.forCard);
        }
    }

    public void setBgType(int i) {
        this.bgType = i;
    }
}