package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.ruichaoqun.luckymusic.theme.ThemeService;
import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;

public class CustomThemeLine extends View implements OnThemeResetListener {


    /* renamed from: b reason: collision with root package name */
    private ThemeResetter mThemeResetter = new ThemeResetter(this);

    /* access modifiers changed from: protected */
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

    public CustomThemeLine(Context context, int i) {
        super(context);
        setMinimumHeight(i);
        onThemeReset();
    }

    public CustomThemeLine(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        onThemeReset();
    }

    public void onThemeReset() {
        if (this.mThemeResetter != null) {
            this.mThemeResetter.saveCurrentThemeInfo();
        }
        setBackgroundColor(ThemeService.getInstance().getLineColor());
    }

}
