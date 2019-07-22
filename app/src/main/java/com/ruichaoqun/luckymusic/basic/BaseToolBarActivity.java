package com.ruichaoqun.luckymusic.basic;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.ui.FitSystemWindowHackFrameLayout;
import com.ruichaoqun.luckymusic.ui.StatusBarHolderView;
import com.ruichaoqun.luckymusic.util.ReflectUtils;

/**
 *
 */
public class BaseToolBarActivity extends BaseActivity {
    public StatusBarHolderView statusBarView;
    protected Toolbar toolbar;
    private Menu menu;
    private boolean hadHackFitSystemWindow;

    public Toolbar getToolbar() {
        return this.toolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        if (this.toolbar != null) {
            this.toolbar.setTitle(charSequence);
        }
    }

    public void setSubTitle(CharSequence charSequence) {
        if (this.toolbar != null) {
            this.toolbar.setSubtitle(charSequence);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void hideActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null && supportActionBar.isShowing()) {
            supportActionBar.hide();
            transparentStatusBar(false);
            if (this.statusBarView != null) {
                this.statusBarView.setVisibility(View.GONE);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void showActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null && !supportActionBar.isShowing()) {
            supportActionBar.show();
            transparentStatusBar(true);
            if (this.statusBarView != null) {
                this.statusBarView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 设置沉浸式状态栏
     * @param z
     */
    public void transparentStatusBar(boolean z) {
        boolean hasSet;
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            if (z) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            hasSet = true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (z) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                //全屏 并 状态栏显示
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                //通过setStatusBarColor为状态栏设置背景色，同时需要同步设置FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS这个Window Flag,
                // 并且需要保证WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS这个Window Flag没有被设置。否则，不会生效。
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            hasSet = true;
        } else {
            hasSet = false;
        }
        if (hasSet && !this.hadHackFitSystemWindow) {
            //获取
            ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
            if (hasEditTextView(viewGroup) || needForceHackFitSystemWindow()) {
                this.hadHackFitSystemWindow = true;
                View childAt = viewGroup.getChildAt(0);
                viewGroup.removeView(childAt);
                FitSystemWindowHackFrameLayout fitSystemWindowHackFrameLayout = new FitSystemWindowHackFrameLayout(this);
                fitSystemWindowHackFrameLayout.addView(childAt, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                viewGroup.addView(fitSystemWindowHackFrameLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
    }


    private boolean hasEditTextView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                if (hasEditTextView((ViewGroup) viewGroup.getChildAt(i))) {
                    return true;
                }
            } else if (viewGroup.getChildAt(i) instanceof EditText) {
                return true;
            }
        }
        return false;
    }

    public boolean needForceHackFitSystemWindow() {
        return false;
    }





    /* access modifiers changed from: protected */
    public boolean needApplyCurrentTheme() {
        return true;
    }

    /**
     * toolbar是否在image上面
     * @return
     */
    public boolean isToolbarOnImage() {
        return false;
    }

    /**
     * 是否需要toolbar？
     * @return
     */
    public boolean needToolBar() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void initToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (this.toolbar == null) {
            this.toolbar = (Toolbar) getLayoutInflater().inflate(R.layout.layour_toolbar, null);
        }
        this.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseToolBarActivity.this.onToolBarClick();
            }
        });
        setSupportActionBar(this.toolbar);
    }

    public void onToolBarClick() {
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar2) {
        super.setSupportActionBar(toolbar2);
        if (needToobarUpIcon()) {
            setToolbarBackIcon();
        }
    }

    public boolean needToobarUpIcon() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void setToolbarBackIcon() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setToolbarBackIcon(this.toolbar);
        }
    }

    /**
     * 设置toolbar返回图标
     * @param toolbar2
     */
    public void setToolbarBackIcon(Toolbar toolbar2) {
        setToolbarBackIcon(toolbar2, needCloseButton());
        //反射获取toolbar中的返回View
        View view = (View) ReflectUtils.getDeclaredField(Toolbar.class, (Object) toolbar2, "mNavButtonView");
        if (view != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    BaseToolBarActivity.this.onIconLongClick();
                    return true;
                }
            });
        }
    }

    public boolean needCloseButton() {
        return false;
    }


    private void onIconLongClick() {

    }

    /**
     * 设置toolbar的返回图标
     * @param toolbar2
     * @param z 是否设置
     */
    private void setToolbarBackIcon(Toolbar toolbar2, boolean z) {
        toolbar2.setNavigationIcon(z ? R.mipmap.icon_toolbar_colse : R.mipmap.icon_arrow_back_white);
    }

    public void addToolBarByDefaultWrap(int i) {
        addToolBarByDefaultWrap(getLayoutInflater().inflate(i, null));
    }

    public void addToolBarByDefaultWrap(View view) {
        super.setContentView((int) R.layout.layout_contain_toolbar);
        initToolBar();
        ((ViewGroup) findViewById(R.id.parent)).addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void doSetContentViewWithToolBar(int i) {
        doSetContentViewWithToolBar(getLayoutInflater().inflate(i, null));
    }

    public void doSetContentViewWithToolBar(View view) {
        if (!(view instanceof LinearLayout) || ((LinearLayout) view).getOrientation() != LinearLayout.VERTICAL) {
            addToolBarByDefaultWrap(view);
            return;
        }
        initToolBar();
        super.setContentView(view);
        ((LinearLayout) view).addView(this.toolbar, 0);
    }


    @Override
    public void setContentView(int res) {
        if (needToolBar()) {
            doSetContentViewWithToolBar(res);
            transparentStatusBar(true);
        } else {
            super.setContentView(res);
        }
        applyCurrentTheme();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
//        applyRecentTaskPreviewCurrentTheme();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        if (needToolBar()) {
            doSetContentViewWithToolBar(view);
            transparentStatusBar(true);
        } else {
            super.setContentView(view, layoutParams);
        }
        applyCurrentTheme();
    }




    public void applyCurrentTheme() {
        if (getSupportActionBar() != null) {
            if (needApplyCurrentTheme()) {
                applyToolbarCurrentTheme();
                applyStatusBarCurrentTheme();
            }
//            applyRecentTaskPreviewCurrentTheme();
        }
    }

    public void applyToolbarCurrentTheme() {
        if (getSupportActionBar() != null) {
            applyToolbarCurrentTheme(this.toolbar);
        }
    }

    public void applyToolbarCurrentTheme(Toolbar toolbar2) {
        applyToolbarCurrentTheme(toolbar2, isToolbarOnImage());
    }

    public void applyToolbarCurrentTheme(Toolbar toolbar2, boolean z) {
        toolbar2.setBackgroundDrawable(getToolbarBg());
        applyToolbarCurrentThemeWithViewColor(toolbar2, z);
    }


    public Drawable getToolbarBg() {
        return ResourceRouter.getInstance().getCacheToolBarDrawable();
    }

    public void applyToolbarCurrentThemeWithViewColor(Toolbar toolbar2, boolean z) {
        Drawable navigationIcon = toolbar2.getNavigationIcon();
        if (navigationIcon != null) {
            ThemeHelper.configDrawableTheme(navigationIcon.mutate(), getToolbarIconColor(z));
        }
        toolbar2.setTitleTextColor(getTitleTextColor(z));
        toolbar2.setSubtitleTextColor(getSubtitleTextColor(z));
    }

    public int getTitleTextColor(boolean z) {
        return Color.WHITE;
//        return ResourceRouter.getInstance().getTitleTextColor(z);
    }

    public int getSubtitleTextColor(boolean z) {
        if (z) {
            return getResources().getColor(R.color.color_66FFFFFF);
        }
        int titleTextColor = getTitleTextColor(z);
        return ColorUtils.setAlphaComponent(titleTextColor, Color.alpha(titleTextColor) / 2);
    }

    public int getToolbarIconColor(boolean z) {
//        return getResourceRouter().getToolbarIconColor(z);
        return Color.WHITE;
    }

    public ResourceRouter getResourceRouter() {
        return ResourceRouter.getInstance();
    }










    /**
     * 设置状态栏当前主题
     */
    public void applyStatusBarCurrentTheme() {
        if (getSupportActionBar() == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        //如果包含actionbar并且版本大于等于19
        if (this.statusBarView == null) {
            this.statusBarView = initStatusBarHolderView(R.id.statusbar_view);
            addStatusBarView();
            return;
        }
        setStyleForStatusBarView(this.statusBarView);
    }


    public StatusBarHolderView initStatusBarHolderView(int i) {
        StatusBarHolderView statusBarHolderView = new StatusBarHolderView(this);
        statusBarHolderView.setId(i);
        setStyleForStatusBarView(statusBarHolderView);
        return statusBarHolderView;
    }

    public void setStyleForStatusBarView(StatusBarHolderView statusBarHolderView) {
        setStyleForStatusBarView(statusBarHolderView, isToolbarOnImage());
    }

    //设置statusbarview背景色
    public void setStyleForStatusBarView(StatusBarHolderView statusBarHolderView, boolean z) {
        boolean z2 = true;
        boolean z3 = false;
        if(Build.VERSION.SDK_INT >= 23){
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            statusBarHolderView.setStatusBarTranslucent(true);
            statusBarHolderView.setBackgroundDrawable(getStatusbarBg());
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (getResourceRouter().isWhiteTheme() || getResourceRouter().isCustomLightTheme() || getResourceRouter().isCustomColorTheme()) {
//                boolean j = Build.VERSION.SDK_INT >= 23;
//                statusBarHolderView.setStatusBarTranslucent(j);
//                if (!j || z) {
//                    z3 = true;
//                } else {
//                    getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                }
//                z2 = z3;
//            } else {
//                if (getResourceRouter().getColor(R.color.nn) == 0) {
//                    z3 = true;
//                }
//                statusBarHolderView.setStatusBarTranslucent(z3);
//            }
//            if (z2) {
//                getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            }
//        }
//        statusBarHolderView.setBackgroundDrawable(getStatusbarBg());
    }


    public Drawable getStatusbarBg() {
        return ResourceRouter.getInstance().getCacheStatusBarDrawable();
    }









    /* access modifiers changed from: protected */
    public void addStatusBarView() {
        ViewGroup viewGroup = (ViewGroup) this.toolbar.getParent();
        int i = 0;
        while (true) {
            if (i >= viewGroup.getChildCount()) {
                i = 0;
                break;
            } else if (viewGroup.getChildAt(i) == this.toolbar) {
                break;
            } else {
                i++;
            }
        }
        viewGroup.addView(this.statusBarView, i);
        //如果父View继承自RelativeLayout，将toolbar设置到statusbar下面
        if (viewGroup instanceof RelativeLayout) {
            ((RelativeLayout.LayoutParams) this.toolbar.getLayoutParams()).addRule(RelativeLayout.BELOW, R.id.statusbar_view);
        }
    }




}
