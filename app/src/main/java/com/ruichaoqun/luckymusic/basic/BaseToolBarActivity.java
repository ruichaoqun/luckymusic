package com.ruichaoqun.luckymusic.basic;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.util.ReflectUtils;

public class BaseToolBarActivity extends BaseActivity {
    protected Toolbar toolbar;
    private Menu menu;

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

    @TargetApi(19)
    public void hideActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null && supportActionBar.isShowing()) {
            supportActionBar.hide();
            transparentStatusBar(false);
            if (this.statusBarView != null) {
                this.statusBarView.setVisibility(8);
            }
        }
    }

    @TargetApi(19)
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
        if (this.toolbar == null) {
            this.toolbar = (Toolbar) getLayoutInflater().inflate(R.layout.layout_toolbar, null);
        }
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

    private void onIconLongClick() {

    }

    private void setToolbarBackIcon(Toolbar toolbar2, boolean z) {
        toolbar2.setNavigationIcon(z ? R.mipmap.icon_toolbar_colse : R.mipmap.icon_arrow_back_white);
    }

    public void addToolBarByDefaultWrap(int i) {
        addToolBarByDefaultWrap(getLayoutInflater().inflate(i, null));
    }

    public void addToolBarByDefaultWrap(View view) {
        super.setContentView((int) R.layout.ai);
        initToolBar();
        ((ViewGroup) findViewById(R.id.j0)).addView(view, new ViewGroup.LayoutParams(-1, -1));
    }

    public void doSetContentViewWithToolBar(int i) {
        doSetContentViewWithToolBar(getLayoutInflater().inflate(i, null));
    }

    public void doSetContentViewWithToolBar(View view) {
        if (!(view instanceof LinearLayout) || ((LinearLayout) view).getOrientation() != 1) {
            addToolBarByDefaultWrap(view);
            return;
        }
        initToolBar();
        super.setContentView(view);
        ((LinearLayout) view).addView(this.toolbar, 0);
    }

    @Override
    public void setContentView(int i) {
        if (needToolBar()) {
            doSetContentViewWithToolBar(i);
            transparentStatusBar(true);
        } else {
            super.setContentView(i);
        }
        applyCurrentTheme();
    }


    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        if (needToolBar()) {
            doSetContentViewWithToolBar(view);
            transparentStatusBar(true);
        } else {
            super.setContentView(view, layoutParams);
        }
        applyCurrentTheme();
    }


    public void setContentView(View view) {
        super.setContentView(view);
        applyRecentTaskPreviewCurrentTheme();
    }



}
