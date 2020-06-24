package com.ruichaoqun.luckymusic.ui.theme.themedetail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMvpToolbarActivity;

public class ThemeDetailActivity extends BaseMvpToolbarActivity<ThemeDetailContact.Presenter> implements ThemeDetailContact.View {

    public static void launchFrom(Activity activity){
        activity.startActivity(new Intent(activity,ThemeDetailActivity.class));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_theme_detail;
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