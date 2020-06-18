package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.tabs.TabLayout;
import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;

/**
 * @author Rui Chaoqun
 * @date :2020/6/15 16:53
 * description:bacgroundColor 透明
 * 首页模式：
 * 其他模式
 */
public class CustomThemeTabLayout extends TabLayout implements OnThemeResetListener {
    private boolean mNeedThemeResetWithOnAttachedToWindow = true;
    protected ThemeResetter mThemeResetter = new ThemeResetter(this);

    public void setNeedThemeResetWithOnAttachedToWindow(boolean z) {
        this.mNeedThemeResetWithOnAttachedToWindow = z;
    }


    public CustomThemeTabLayout(Context context) {
        super(context);
    }

    public CustomThemeTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onThemeReset();
    }

    @Override
    protected void onAttachedToWindow() {
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
    public void onThemeReset() {
        if (this.mThemeResetter != null) {
            this.mThemeResetter.saveCurrentThemeInfo();
        }

    }
}
