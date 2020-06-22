package com.ruichaoqun.luckymusic.ui.main;

import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.ThemeInfo;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeAgent;
import com.ruichaoqun.luckymusic.theme.core.ThemeConfig;
import com.ruichaoqun.luckymusic.theme.ui.CustomThemeTextView;

public class MainDrawer implements View.OnClickListener {
    public final MainActivity mMainActivity;
    private ScrollView mScrollView;
    private CustomThemeTextView mThemeModeView;
    private CustomThemeTextView mSettingView;
    private CustomThemeTextView mQuitView;
    private LinearLayout mLayoutContent;
    private TextView mTvMyMessage;

    public MainDrawer(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    public void initView(){
        mScrollView = findViewById(R.id.scroll_view);
        mLayoutContent = findViewById(R.id.drawer_content);
        mTvMyMessage = findViewById(R.id.tv_my_message);
        mThemeModeView = findViewById(R.id.tv_theme_mode);
        mThemeModeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getResourceRouter().isNightTheme()){
                    ThemeAgent.getInstance().switchTheme(mMainActivity, new ThemeInfo(ThemeConfig.THEME_NIGHT), true);
                    return;
                }
                int prevThemeId = ThemeConfig.getPrevThemeId();
                ThemeInfo themeInfo = new ThemeInfo(prevThemeId);
                ThemeAgent.getInstance().switchTheme(MainDrawer.this.mMainActivity, themeInfo, true);
            }
        });
        mSettingView = findViewById(R.id.tv_setting);
        mSettingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mQuitView = findViewById(R.id.tv_quit);
        mQuitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        applyDrawerCurrentTheme();
    }

    public void applyDrawerCurrentTheme() {
        Drawable cacheDrawerBgDrawable = ResourceRouter.getInstance().getCacheDrawerBgDrawable();
        Drawable cacheDrawerBottomDrawable = ResourceRouter.getInstance().getCacheDrawerBottomDrawable();
        findViewById(R.id.layout_bottom).setBackgroundDrawable(cacheDrawerBottomDrawable);
        mScrollView.setBackground(cacheDrawerBgDrawable);
//        mTvMyMessage.setBackground(cacheDrawerBgDrawable);
        if(getResourceRouter().isNightTheme()){
            mThemeModeView.setText(R.string.main_activity_drawer_light_theme);
            mThemeModeView.setCompoundDrawablesWithIntrinsicBoundsOriginal(R.drawable.ic_theme_dyatime_mode,0,0,0);
        }else{
            mThemeModeView.setText(R.string.main_activity_drawer_night_theme);
            mThemeModeView.setCompoundDrawablesWithIntrinsicBoundsOriginal(R.drawable.ic_theme_night_mode,0,0,0);
        }

        mSettingView.onThemeReset();
        mQuitView.onThemeReset();
    }

    @Override
    public void onClick(View v) {

    }

    private ResourceRouter getResourceRouter(){
        return ResourceRouter.getInstance();
    }

    public <T extends View> T findViewById(int i2) {
        return this.mMainActivity.findViewById(i2);
    }

}
