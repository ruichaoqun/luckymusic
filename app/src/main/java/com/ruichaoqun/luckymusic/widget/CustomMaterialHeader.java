package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeResetter;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;
import com.scwang.smartrefresh.header.MaterialHeader;

/**
 * @author Rui Chaoqun
 * @date :2020/7/1 16:27
 * description:
 */
public class CustomMaterialHeader extends MaterialHeader implements OnThemeResetListener {
    private ThemeResetter mThemeResetter = new ThemeResetter(this);

    public CustomMaterialHeader(Context context) {
        super(context);
        onThemeReset();
    }

    public CustomMaterialHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        onThemeReset();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mThemeResetter.checkIfNeedResetTheme();
    }


    @Override
    public void onThemeReset() {
        if (this.mThemeResetter != null) {
            this.mThemeResetter.saveCurrentThemeInfo();
        }
        if(ResourceRouter.getInstance().isNightTheme()){
            mProgress.setAlpha(30);
            mProgress.setBackgroundColor(0xFF555555);
        }else{
            mProgress.setAlpha(255);
            mProgress.setBackgroundColor(0xFFFAFAFA);
        }
        mProgress.setColorSchemeColors(ResourceRouter.getInstance().getThemeColor());
    }
}

