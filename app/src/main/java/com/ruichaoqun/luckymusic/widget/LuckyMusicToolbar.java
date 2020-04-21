package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.utils.ReflectUtils;
import com.ruichaoqun.luckymusic.utils.UiUtils;

/**
 * @author Rui Chaoqun
 * @date :2019/9/29 11:54
 * description:
 */
public class LuckyMusicToolbar extends Toolbar {
    private boolean reflected = false;
    private TextView title;

    public LuckyMusicToolbar(Context context) {
        super(context);
        setContentInsetStartWithNavigation(UiUtils.dp2px(56.0f));
    }

    public LuckyMusicToolbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setContentInsetStartWithNavigation(UiUtils.dp2px(56.0f));
    }

    public LuckyMusicToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setContentInsetStartWithNavigation(UiUtils.dp2px(56.0f));
    }

    @Override
    public void setTitle(CharSequence charSequence) {
        if (!this.reflected) {
            this.reflected = reflectTitle();
        }
        super.setTitle(charSequence);
        selectTitle();
    }

    @Override
    public void setSubtitle(CharSequence charSequence) {
        super.setSubtitle(charSequence);
        if (!this.reflected) {
            this.reflected = reflectTitle();
        }
        if (charSequence == null || charSequence.length() == 0) {
            this.title.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.toolbar_title_size));
        } else {
            this.title.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.toolbar_title_size_two_line));
        }
    }

    public void addCustomView(View child, int gravity, int leftMargin, int rightMargin, OnClickListener onClickListener) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,gravity);
        layoutParams.leftMargin = leftMargin;
        layoutParams.rightMargin = rightMargin;
        child.setLayoutParams(layoutParams);
        child.setOnClickListener(onClickListener);
        addView(child);
    }

    public View getNaviView() {
        return (View) ReflectUtils.getDeclaredField(Toolbar.class, this, "mNavButtonView");
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundDrawable(new ColorDrawable(color));
    }

    @Override
    public void setTitle(int res) {
        setTitle((CharSequence) getResources().getString(res));
    }


    public TextView getTitleTextView() {
        if (this.title == null) {
            this.title = (TextView) ReflectUtils.getDeclaredField(Toolbar.class, this, "mTitleTextView");
        }
        return this.title;
    }

    private boolean reflectTitle() {
        try {
            this.title = getTitleTextView();
            if (this.title == null) {
                return false;
            }
            this.title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            this.title.setSingleLine();
            this.title.setMarqueeRepeatLimit(-1);
            this.title.setHorizontalFadingEdgeEnabled(true);
            this.title.setFadingEdgeLength(50);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public void selectTitle() {
        if (this.title != null) {
            this.title.setSelected(true);
        }
    }



//    public ImageView getLogoView() {
//        Object a2 = cc.onColorGet(Toolbar.class, (Object) this, onColorGet.QueueData("IykbAg4lDCsS"));
//        if (a2 == null) {
//            cc.onColorGet(Toolbar.class, onColorGet.QueueData("KwsHEBMWKSECGzMIFhI="), (Class<?>[]) null, (Object) this, new Object[0]);
//            a2 = cc.onColorGet(Toolbar.class, (Object) this, onColorGet.QueueData("IykbAg4lDCsS"));
//        }
//        return (ImageView) a2;
//    }
}
