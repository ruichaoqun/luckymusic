package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;

import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;

/**
 * @author Rui Chaoqun
 * @date :2020/6/23 14:28
 * description:
 */
public class CustomThemeImageView extends AppCompatImageView implements OnThemeResetListener {
    private boolean mNeedThemeResetWithOnAttachedToWindow = true;
    private ThemeResetter mThemeResetter = new ThemeResetter(this);


    public CustomThemeImageView(Context context) {
        super(context);
    }

    public CustomThemeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNeedThemeResetWithOnAttachedToWindow(boolean need) {
        this.mNeedThemeResetWithOnAttachedToWindow = need;
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        Log.w("AAAAAA","onFinishTemporaryDetach");
        if (this.mNeedThemeResetWithOnAttachedToWindow) {
            this.mThemeResetter.checkIfNeedResetTheme();
        }
    }

    @Override
    public void onAttachedToWindow() {
        ThemeResetter themeResetter;
        super.onAttachedToWindow();
        Log.w("AAAAAA","onAttachedToWindow");
        if (this.mNeedThemeResetWithOnAttachedToWindow && (themeResetter = this.mThemeResetter) != null) {
            themeResetter.checkIfNeedResetTheme();
        }
    }



    @Override
    public void onThemeReset() {
        ThemeResetter themeResetter = this.mThemeResetter;
        if (themeResetter != null) {
            themeResetter.saveCurrentThemeInfo();
        }
    }
}

