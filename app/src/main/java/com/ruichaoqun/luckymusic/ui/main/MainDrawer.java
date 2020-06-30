package com.ruichaoqun.luckymusic.ui.main;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Pair;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.ThemeInfo;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeAgent;
import com.ruichaoqun.luckymusic.theme.core.ThemeConfig;
import com.ruichaoqun.luckymusic.theme.impl.OnThemeResetListener;
import com.ruichaoqun.luckymusic.theme.ui.CustomThemeTextView;
import com.ruichaoqun.luckymusic.ui.theme.themedetail.ThemeDetailActivity;
import com.ruichaoqun.luckymusic.utils.UiUtils;

public class MainDrawer implements View.OnClickListener {
    public final MainActivity mMainActivity;
    private DrawerLayout mDrawerLayout;
    private ScrollView mScrollView;
    private ImageView mDrawIcon;
    private CustomThemeTextView mThemeModeView;
    private CustomThemeTextView mSettingView;
    private CustomThemeTextView mQuitView;
    private LinearLayout mLayoutContent;
    private TextView mTvMyMessage;
    private FrameLayout mDrawerHeaderContent;
    private DrawerItemEnum closeActionMenu;
    private ActionBarDrawerToggle mDrawerToggle;
    private VectorDrawableCompat mDrawerIconDrawable;

    public MainDrawer(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    public void initView(){

        mScrollView = findViewById(R.id.scroll_view);
        mLayoutContent = findViewById(R.id.drawer_content);
        mDrawerHeaderContent = findViewById(R.id.drawer_header_content);
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
        findViewById(R.id.click_my_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.click_my_friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.click_homepage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.click_my_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActionMenu = DrawerItemEnum.Theme;
                toggleDrawerMenu();
            }
        });
        initDrawer();
        applyDrawerCurrentTheme();
    }

    private void initDrawer() {
        mDrawIcon = findViewById(R.id.iv_back);
        this.mDrawerIconDrawable = VectorDrawableCompat.create(getResources(), R.drawable.icon_menu, null);
        this.mDrawIcon.setImageDrawable(this.mDrawerIconDrawable);
        this.mDrawerLayout = findViewById(R.id.drawer_layout);
        this.mDrawerToggle = new ActionBarDrawerToggle(mMainActivity, this.mDrawerLayout, mMainActivity.getToolbar(), R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                mMainActivity.onDrawerClosed(drawerView);
                mScrollView.fullScroll(View.FOCUS_UP);
                if(closeActionMenu != null){
                    switch (closeActionMenu){
                        case Theme:
                            ThemeDetailActivity.launchFrom(mMainActivity);
                        default:
                    }
                }
                closeActionMenu = null;
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        this.mDrawIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDrawerMenu();
            }
        });
        this.mDrawerLayout.addDrawerListener(this.mDrawerToggle);
    }

    private Resources getResources() {
        return mMainActivity.getResources();
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
        updateDrawerHeaderInfo(mDrawerHeaderContent);
        this.mDrawIcon.setImageDrawable(ThemeHelper.configDrawableThemeUseTint(this.mDrawerIconDrawable.getConstantState().newDrawable(), getResourceRouter().getToolbarIconColor()));
        this.mDrawIcon.setBackground(ThemeHelper.getBgSelectorWithNoBorder(mMainActivity));
    }

    private void updateDrawerHeaderInfo(View view) {
        if(view instanceof OnThemeResetListener){
            ((OnThemeResetListener) view).onThemeReset();
        }
        if(view instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                updateDrawerHeaderInfo(((ViewGroup) view).getChildAt(i));
            }
        }
    }

    private void toggleDrawerMenu() {
        if(mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
            return;
        }
        this.mDrawerLayout.openDrawer((int) GravityCompat.START);
    }

    @Override
    public void onClick(View v) {

    }

    public boolean interuptAndCloseDrawer() {
        if (this.mScrollView == null) {
            return true;
        }
        boolean isDrawerVisible = this.mDrawerLayout.isDrawerVisible((int) GravityCompat.START);
        if (isDrawerVisible) {
            this.mDrawerLayout.closeDrawer((int) GravityCompat.START);
        }
        return isDrawerVisible;
    }


    private ResourceRouter getResourceRouter(){
        return ResourceRouter.getInstance();
    }

    public <T extends View> T findViewById(int i2) {
        return this.mMainActivity.findViewById(i2);
    }

    public enum DrawerItemEnum {
        //主题
        Theme(0,R.drawable.vector_drawer_theme,R.string.drawer_my_theme);

        private int icon;
        private int priority;
        private int tag;
        private int title;

        private DrawerItemEnum(int priority, int icon, int title) {
            this.priority = priority;
            this.icon = icon;
            this.title = title;
        }

        public String getTitleStr() {
            return LuckyMusicApp.getInstance().getString(this.title);
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public int getTitle() {
            return title;
        }

        public void setTitle(int title) {
            this.title = title;
        }
    }

}
