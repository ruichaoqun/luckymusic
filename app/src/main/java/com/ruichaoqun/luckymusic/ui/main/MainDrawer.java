package com.ruichaoqun.luckymusic.ui.main;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.ui.CustomThemeTextView;

public class MainDrawer implements View.OnClickListener {
    public final MainActivity mMainActivity;
    private ScrollView mScrollView;
    private CustomThemeTextView mThemeModeView;
    private CustomThemeTextView mSettingView;
    private CustomThemeTextView mQuitView;

    public MainDrawer(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    public void initView(){
        mThemeModeView = findViewById(R.id.tv_theme_mode);
        mThemeModeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mSettingView = findViewById(R.id.tv_setting);
        mQuitView = findViewById(R.id.tv_quit);
        applyCurrentTheme();
    }

    private void applyCurrentTheme() {
        Drawable cacheDrawerBgDrawable = ResourceRouter.getInstance().getCacheDrawerBgDrawable();
        Drawable cacheDrawerBottomDrawable = ResourceRouter.getInstance().getCacheDrawerBottomDrawable();

        findViewById(R.id.layout_bottom).setBackgroundDrawable(cacheDrawerBottomDrawable);
        mThemeModeView.setBackground(ThemeHelper.getBgSelector(this.mMainActivity, -1));
        mSettingView.setBackgroundDrawable(ThemeHelper.getBgSelector(this.mMainActivity, -1));
        mQuitView.setBackgroundDrawable(ThemeHelper.getBgSelector(this.mMainActivity, -1));
    }

    @Override
    public void onClick(View v) {

    }

    public <T extends View> T findViewById(int i2) {
        return this.mMainActivity.findViewById(i2);
    }

}
