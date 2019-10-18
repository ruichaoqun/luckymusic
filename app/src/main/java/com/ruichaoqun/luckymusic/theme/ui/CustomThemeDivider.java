package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;

public class CustomThemeDivider extends View implements OnThemeResetListener {
    private ThemeResetter mThemeResetter = new ThemeResetter(this);

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mThemeResetter != null) {
            this.mThemeResetter.checkIfNeedResetTheme();
        }
    }

    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        this.mThemeResetter.checkIfNeedResetTheme();
    }

    public CustomThemeDivider(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        onThemeReset();
    }

    public void onThemeReset() {
        if (this.mThemeResetter != null) {
            this.mThemeResetter.saveCurrentThemeInfo();
        }
        setBackgroundColor(ResourceRouter.getInstance().getDividerColor());
    }
}