package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;
import com.ruichaoqun.luckymusic.utils.drawhelper.RoundedViewHelper;

public class CustomThemeConstraintLayout extends ConstraintLayout implements OnThemeResetListener {
    protected int bgType;
    private boolean forCard;
    private boolean mNeedThemeResetWithOnAttachedToWindow = true;
    private RoundedViewHelper mRoundedViewHelper;
    protected ThemeResetter mThemeResetter = new ThemeResetter(this);
    protected int newBgPaddingLeft;

    public CustomThemeConstraintLayout(Context context) {
        super(context);
        onThemeReset();
    }

    public CustomThemeConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onThemeReset();
    }

    public CustomThemeConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onThemeReset();
    }



    public void setNeedThemeResetWithOnAttachedToWindow(boolean z) {
        this.mNeedThemeResetWithOnAttachedToWindow = z;
    }

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

    @Override
    public void onDraw(Canvas canvas) {
        if (this.mRoundedViewHelper != null) {
            this.mRoundedViewHelper.onDraw(canvas);
        }
        super.onDraw(canvas);
    }

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
