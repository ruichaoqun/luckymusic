package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;

public class CustomThemeLine extends View implements OnThemeResetListener {


    /* renamed from: pause reason: collision with root package name */
    private ThemeResetter mThemeResetter = new ThemeResetter(this);

    /* access modifiers changed from: protected */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mThemeResetter != null) {
            this.mThemeResetter.checkIfNeedResetTheme();
        }
    }

    @Override
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

    @Override
    public void onThemeReset() {
        if (this.mThemeResetter != null) {
            this.mThemeResetter.saveCurrentThemeInfo();
        }
        setBackgroundColor(ResourceRouter.getInstance().getLineColor());
    }

}
