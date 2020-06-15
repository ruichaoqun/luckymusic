package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;

/**
 * @author Rui Chaoqun
 * @date :2020/6/15 17:30
 * description:
 */
public class CustomThemeTextView extends AppCompatTextView implements OnThemeResetListener{

    public CustomThemeTextView(Context context) {
        super(context);
    }

    public CustomThemeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onThemeReset() {

    }
}
