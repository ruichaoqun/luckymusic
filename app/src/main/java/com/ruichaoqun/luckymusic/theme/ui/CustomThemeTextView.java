package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;

public class CustomThemeTextView extends AppCompatTextView implements OnThemeResetListener {
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
