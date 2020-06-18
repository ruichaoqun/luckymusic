package com.ruichaoqun.luckymusic.base.activity;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeConfig;
import com.ruichaoqun.luckymusic.utils.CommonUtils;
import com.ruichaoqun.luckymusic.widget.FitSystemWindowHackFrameLayout;
import com.ruichaoqun.luckymusic.widget.StatusBarHolderView;
import com.ruichaoqun.luckymusic.utils.ReflectUtils;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 */
public abstract class BaseToolBarActivity extends BaseActivity {
    public StatusBarHolderView statusBarView;
    protected Toolbar toolbar;
    private Menu menu;
    private boolean hadHackFitSystemWindow;
    //用于解决FLAG_ACTIVITY_REORDER_TO_FRONT导致的BUG
    private boolean mIsRestoredToTop;

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ((intent.getFlags() | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT) > 0) {
            this.mIsRestoredToTop = true;
        }
        if (enablePopFragments()) {
            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
        overridePendingTransition(R.anim.l, R.anim.m);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setWindowBackground();
        super.onCreate(savedInstanceState);
    }

    protected void setWindowBackground() {
        getWindow().setBackgroundDrawable(getWindowBackgroundDrawable());
    }

    public Drawable getWindowBackgroundDrawable() {
        return ResourceRouter.getInstance().getCacheBgBlurDrawable();
    }



    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //当前页面是否需要toolbar？
        if (needToolBar()) {
            doSetContentViewWithToolBar(layoutResID);
            transparentStatusBar(true);
        } else {
            super.setContentView(layoutResID);
        }
        //设置透明主题
        applyCurrentTheme();

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }

    public void doSetContentViewWithToolBar(int layoutResID) {
        doSetContentViewWithToolBar(getLayoutInflater().inflate(layoutResID, null));
    }

    public void doSetContentViewWithToolBar(View view) {
        if (!(view instanceof LinearLayout) || ((LinearLayout) view).getOrientation() != LinearLayout.VERTICAL) {
            //如果当前根layout不是LinearLayout或者getOrientation不是VERTICAL
            //例如：根layout是RelativeLayout就会走这儿
            addToolBarByDefaultWrap(view);
            return;
        }
        //根layout是LinearLayout且VERTICAL走这儿
        super.setContentView(view);
        initToolBar();
        ((LinearLayout) view).addView(this.toolbar, 0);
    }

    public void addToolBarByDefaultWrap(View view) {
        super.setContentView((int) R.layout.layout_contain_toolbar);
        initToolBar();
        ((ViewGroup) findViewById(R.id.parent)).addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 初始化toolbar
     */
    public void initToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (this.toolbar == null) {
            this.toolbar = (Toolbar) getLayoutInflater().inflate(R.layout.layout_toolbar, null);
        }
        setSupportActionBar(this.toolbar);
        //是否需要设置返回按钮
        if (needToobarUpIcon()) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                this.toolbar.setNavigationIcon(needCloseButton() ? R.mipmap.icon_toolbar_colse : R.mipmap.icon_arrow_back_white);
            }
        }
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

    /**
     * 设置沉浸式状态栏
     *
     * @param transparent 是否透明状态栏
     */
    public void transparentStatusBar(boolean transparent) {
        boolean hasSet;
        if (transparent) {
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

        if (hasSet && !this.hadHackFitSystemWindow) {
            //获取
            ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
            if (hasEditTextView(viewGroup) || needForceHackFitSystemWindow()) {
                this.hadHackFitSystemWindow = true;
                View childAt = viewGroup.getChildAt(0);
                viewGroup.removeView(childAt);
                FitSystemWindowHackFrameLayout fitSystemWindowHackFrameLayout = new FitSystemWindowHackFrameLayout(this);
                fitSystemWindowHackFrameLayout.addView(childAt, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                viewGroup.addView(fitSystemWindowHackFrameLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
     *
     * @return
     */
    public boolean isToolbarOnImage() {
        return false;
    }

    /**
     * 是否需要toolbar？
     *
     * @return
     */
    public boolean needToolBar() {
        return true;
    }

    public boolean needToobarUpIcon() {
        return true;
    }


    public boolean needCloseButton() {
        return false;
    }


    public void applyCurrentTheme() {
        if (getSupportActionBar() != null) {
            if (needApplyCurrentTheme()) {
                applyStatusBarCurrentTheme();
                applyToolbarCurrentTheme();
            }
            applyRecentTaskPreviewCurrentTheme();
        }
    }

    public void applyRecentTaskPreviewCurrentTheme() {
        int color;
        if (CommonUtils.versionAbove21()) {
            if (ResourceRouter.getInstance().isWhiteTheme()) {
                color = -1;
            } else if (ResourceRouter.getInstance().isRedTheme()) {
                color = ThemeConfig.COLOR_RED_TOOLBAR_END;
            } else if (ResourceRouter.getInstance().isNightTheme()) {
                color = 0xff17181a;
            } else if (!ResourceRouter.getInstance().isInternalTheme()) {
                color = ResourceRouter.getInstance().getPopupBackgroundColor();
            } else {
                color = ResourceRouter.getInstance().getThemeColor();
            }
            setTaskDescription(new ActivityManager.TaskDescription(null, null, ColorUtils.setAlphaComponent(color, 255)));
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

    public void applyToolbarCurrentTheme(Toolbar toolbar2, boolean isToolbarOnImage) {
        toolbar2.setBackgroundDrawable(getToolbarBg());
        applyToolbarCurrentThemeWithViewColor(toolbar2, isToolbarOnImage);
    }


    public Drawable getToolbarBg() {
        return ResourceRouter.getInstance().getCacheToolBarDrawable();
    }

    public void applyToolbarCurrentThemeWithViewColor(Toolbar toolbar){
        applyToolbarCurrentThemeWithViewColor(toolbar,isToolbarOnImage());
    }

    public void applyToolbarCurrentThemeWithViewColor(Toolbar toolbar2, boolean isToolbarOnImage) {
        Drawable navigationIcon = toolbar2.getNavigationIcon();
        if (navigationIcon != null) {
            ThemeHelper.configDrawableTheme(navigationIcon.mutate(), getToolbarIconColor(isToolbarOnImage));
        }
        toolbar2.setTitleTextColor(getTitleTextColor(isToolbarOnImage));
        toolbar2.setSubtitleTextColor(getSubtitleTextColor(isToolbarOnImage));
    }

    public int getTitleTextColor(boolean isToolbarOnImage) {
        return ResourceRouter.getInstance().getTitleTextColor(isToolbarOnImage);
    }

    public int getSubtitleTextColor(boolean isToolbarOnImage) {
        if (isToolbarOnImage) {
            return getResources().getColor(R.color.color_66FFFFFF);
        }
        int titleTextColor = getTitleTextColor(false);
        return ColorUtils.setAlphaComponent(titleTextColor, Color.alpha(titleTextColor) / 2);
    }

    public int getToolbarIconColor(boolean isToolbarOnImage) {
        return getResourceRouter().getToolbarIconColor(isToolbarOnImage);
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


    public StatusBarHolderView initStatusBarHolderView(int id) {
        StatusBarHolderView statusBarHolderView = new StatusBarHolderView(this);
        statusBarHolderView.setId(id);
        setStyleForStatusBarView(statusBarHolderView);
        return statusBarHolderView;
    }

    public void setStyleForStatusBarView(StatusBarHolderView statusBarHolderView) {
        setStyleForStatusBarView(statusBarHolderView, isToolbarOnImage());
    }

    //设置statusbarview背景色
    public void setStyleForStatusBarView(StatusBarHolderView statusBarHolderView, boolean isToolbarOnImage) {
        boolean z2 = true;
        boolean z3 = false;

        if(ResourceRouter.getInstance().isWhiteTheme() || ResourceRouter.getInstance().isCustomLightTheme() || ResourceRouter.getInstance().isCustomColorTheme()){
            statusBarHolderView.setStatusBarTranslucent(true);
            if(!isToolbarOnImage){
                getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() | 8192);
                z3 = false;
            }
        }else{
            if (ResourceRouter.getInstance().getColor(R.color.transpaint) == 0) {
                z2 = true;
            }
            statusBarHolderView.setStatusBarTranslucent(z2);

        }
        if (z3) {
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() & -8193);
        }
        statusBarHolderView.setBackgroundDrawable(getStatusbarBg());
    }


    public Drawable getStatusbarBg() {
        return ResourceRouter.getInstance().getCacheStatusBarDrawable();
    }

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


    @Override
    public boolean onPrepareOptionsMenu(Menu menu2) {
        if (needApplyCurrentTheme()) {
            applyMenuItemCurrentTheme(menu2);
        }
        return super.onPrepareOptionsMenu(menu2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu2) {
        return super.onCreateOptionsMenu(menu2);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackIconClick();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }


    private void applyMenuItemCurrentTheme(Menu menu2) {
        applyMenuItemCurrentTheme(menu2, this.toolbar);
    }


    public void applyMenuItemCurrentTheme(Menu menu2, Toolbar toolbar2) {
        applyMenuItemCurrentTheme(menu2, toolbar2, isToolbarOnImage());
    }

    //改变menu菜单栏主题
    public void applyMenuItemCurrentTheme(Menu menu2, Toolbar toolbar2, final boolean isToolbarOnImage) {
        for (int i = 0; i < menu2.size(); i++) {
            MenuItem item = menu2.getItem(i);
            if (item.getIcon() != null) {
                applyMenuItemIconColor(item.getIcon());
            }
        }
        ViewGroup viewGroup = (ViewGroup) ReflectUtils.getDeclaredField(Toolbar.class, (Object) toolbar2, "mMenuView");
        if (viewGroup != null) {
            ArrayList arrayList = new ArrayList();
            findTextViewChild(viewGroup, arrayList);
            if (arrayList.size() > 0) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    applyMenuItemTheme((TextView) it.next(), isToolbarOnImage);
                }
                return;
            }
            viewGroup.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                @Override
                public void onChildViewAdded(View view, View view2) {
                    if (view2 instanceof ActionMenuItemView) {
                        applyMenuItemTheme((ActionMenuItemView) view2, isToolbarOnImage);
                    }
                }

                @Override
                public void onChildViewRemoved(View view, View view2) {
                }
            });
        }
    }

    private void findTextViewChild(ViewGroup viewGroup, ArrayList<TextView> arrayList) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof TextView)) {
                arrayList.add((TextView) childAt);
            } else if (childAt instanceof ViewGroup) {
                findTextViewChild((ViewGroup) childAt, arrayList);
            }
        }
    }

    public void applyMenuItemIconColor(Drawable drawable) {
        ThemeHelper.configDrawableTheme(drawable.mutate(), getToolbarIconColor(isToolbarOnImage()));
    }

    public void applyMenuItemTheme(TextView textView, boolean z) {
        textView.setTextColor(getTitleTextColor(z));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) getResources().getDimensionPixelSize(R.dimen.toolbar_menu_text_size));
    }


    @Override
    public void startActivityForResult(Intent intent, int i) {
        try {
            super.startActivityForResult(intent, i);
            overridePendingTransition(R.anim.l, R.anim.m);
        } catch (SecurityException e2) {
            e2.printStackTrace();
        } catch (ActivityNotFoundException e3) {
            e3.printStackTrace();
        }
    }

    public String getLogName() {
        return getClass().getSimpleName();
    }


    @Override
    public void finish() {
        super.finish();
        if (this.mIsRestoredToTop && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) && !isTaskRoot()) {
            try {
                ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).moveTaskToFront(getTaskId(), ActivityManager.MOVE_TASK_NO_USER_ACTION);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        overridePendingTransition(0, R.anim.k);
    }


    public void onBackIconClick() {
        back(true);
    }

    public void back(boolean b) {
        if (b) {
            supportFinishAfterTransition();
            return;
        }
        try {
            super.onBackPressed();
        } catch (IllegalStateException e2) {
            e2.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        back(false);
    }

    private boolean enablePopFragments() {
        return true;
    }


    public Toolbar getToolbar() {
        return this.toolbar;
    }

}
