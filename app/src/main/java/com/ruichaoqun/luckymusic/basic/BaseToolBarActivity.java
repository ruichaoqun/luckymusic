package com.ruichaoqun.luckymusic.basic;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.ui.FitSystemWindowHackFrameLayout;
import com.ruichaoqun.luckymusic.ui.StatusBarHolderView;
import com.ruichaoqun.luckymusic.util.ReflectUtils;

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
                this.statusBarView.setVisibility(0);
            }
        }
    }

    /**
     * 设置状态栏背景色透明
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

    /* access modifiers changed from: protected */
    public boolean isToolbarOnImage() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean needToolBar() {
        return true;
    }

    /* access modifiers changed from: protected */
    public void initToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (this.toolbar == null) {
//            this.toolbar = (Toolbar) getLayoutInflater().inflate(R.layout.toolbar, null);
//        }
        this.toolbar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                BaseToolBarActivity.this.onToolBarClick();
            }
        });
        setSupportActionBar(this.toolbar);
    }

    public void onToolBarClick() {
    }

    public void setSupportActionBar(Toolbar toolbar2) {
        super.setSupportActionBar(toolbar2);
        if (needToobarUpIcon()) {
            setToolbarBackIcon();
        }
    }

    private boolean needToobarUpIcon() {
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
        AppBarLayout
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

//    public void applyCurrentTheme() {
//        if (getSupportActionBar() != null) {
//            if (needApplyCurrentTheme()) {
//                applyToolbarCurrentTheme();
//                applyStatusBarCurrentTheme();
//            }
//            applyRecentTaskPreviewCurrentTheme();
//        }
//    }


    @Override
    public void setContentView(int res) {
        if (needToolBar()) {
            doSetContentViewWithToolBar(res);
            transparentStatusBar(true);
        } else {
            super.setContentView(res);
        }
//        applyCurrentTheme();
    }


    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        if (needToolBar()) {
            doSetContentViewWithToolBar(view);
            transparentStatusBar(true);
        } else {
            super.setContentView(view, layoutParams);
        }
//        applyCurrentTheme();
    }


    public void setContentView(View view) {
        super.setContentView(view);
//        applyRecentTaskPreviewCurrentTheme();
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
        if (viewGroup instanceof RelativeLayout) {
            ((RelativeLayout.LayoutParams) this.toolbar.getLayoutParams()).addRule(3, R.id.av);
        }
    }




}
