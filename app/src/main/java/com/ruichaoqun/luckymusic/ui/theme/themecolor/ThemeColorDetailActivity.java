package com.ruichaoqun.luckymusic.ui.theme.themecolor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMvpToolbarActivity;
import com.ruichaoqun.luckymusic.ui.theme.themedetail.ThemeDetailContact;

/**
 * 主题自选色页面
 * @author Administrator
 */
public class ThemeColorDetailActivity extends BaseMvpToolbarActivity<ThemeColorDetailContact.Presenter> implements ThemeColorDetailContact.View {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_theme_color_detail;
    }


    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}