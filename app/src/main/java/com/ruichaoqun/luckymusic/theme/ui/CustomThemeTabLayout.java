package com.ruichaoqun.luckymusic.theme.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.google.android.material.tabs.TabLayout;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
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
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_SHOW_IN_TOOLBAR = 1;
    public static final int TYPE_SHOW_BELOW_TOOLBAR = 2;
    private boolean mNeedThemeResetWithOnAttachedToWindow = true;
    protected ThemeResetter mThemeResetter = new ThemeResetter(this);
    private int type;


    public void setNeedThemeResetWithOnAttachedToWindow(boolean z) {
        this.mNeedThemeResetWithOnAttachedToWindow = z;
    }


    public CustomThemeTabLayout(Context context) {
        super(context);
    }

    public CustomThemeTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomThemeTabLayout,0, 0);
        type = array.getInt(R.styleable.CustomThemeTabLayout_type,0);
        array.recycle();
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
        ResourceRouter resourceRouter = ResourceRouter.getInstance();
        boolean isNightTheme = ResourceRouter.getInstance().isNightTheme();
        ColorStateList textColors = getTabTextColors();

        //设置点击ripple
        ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(isNightTheme || resourceRouter.isCustomDarkTheme()? R.color.theme_ripple_dark : R.color.theme_ripple_light));
        setTabRippleColor(colorStateList);

        //默认状态，点击色随主题切换
        if(type == TYPE_NORMAL){
            //设置文字颜色
            int defaultColor = textColors.getDefaultColor();
            if(resourceRouter.isNightTheme() || resourceRouter.isCustomBgTheme() | resourceRouter.isCustomDarkTheme()){
                defaultColor = resourceRouter.getNightColor(defaultColor);
            }
            int selectColor = resourceRouter.getThemeColor();
            setTabTextColors(defaultColor,selectColor);
            //设置Indicator颜色
            setSelectedTabIndicatorColor(selectColor);
        }else if(type == TYPE_SHOW_IN_TOOLBAR){
            if(resourceRouter.isNightTheme() || resourceRouter.isCustomDarkTheme()){
                setTabTextColors(resourceRouter.getNightColor(getResources().getColor(R.color.normalC1)),resourceRouter.getNightColor(getResources().getColor(R.color.normalC1)));
            }else{
                setTabTextColors(getResources().getColor(R.color.normalC1),getResources().getColor(R.color.normalC1));
            }
        }else if(type == TYPE_SHOW_BELOW_TOOLBAR){
            int defaultColor = getResources().getColor(R.color.normalC1);
            if(resourceRouter.isNightTheme() || resourceRouter.isCustomBgTheme() | resourceRouter.isCustomDarkTheme()){
                defaultColor = resourceRouter.getNightColor(defaultColor);
            }
            int selectColor = resourceRouter.getThemeColor();
            if(resourceRouter.isRedTheme()){
                defaultColor = resourceRouter.getNightColor(getResources().getColor(R.color.normalC1));
                selectColor = Color.WHITE;
                setBackgroundColor(getResources().getColor(R.color.themeColor));
            }
            setTabTextColors(defaultColor,selectColor);
            //设置Indicator颜色
            setSelectedTabIndicatorColor(selectColor);
        }
    }
}
