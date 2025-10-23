package com.ruichaoqun.luckymusic.ui.theme.themedetail;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMvpToolbarActivity;
import com.ruichaoqun.luckymusic.common.GlideApp;
import com.ruichaoqun.luckymusic.databinding.ActivityThemeDetailBinding;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.ThemeInfo;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeAgent;
import com.ruichaoqun.luckymusic.theme.core.ThemeConfig;
import com.ruichaoqun.luckymusic.ui.theme.themecolor.ThemeColorDetailActivity;
import com.ruichaoqun.luckymusic.utils.drawhelper.ColorDrawableUtils;
import com.ruichaoqun.luckymusic.widget.drawable.CheckedDrawable;

import static com.ruichaoqun.luckymusic.Constants.CHANGED_THEME;

/**
 * 个性换肤
 */
public class ThemeDetailActivity extends BaseMvpToolbarActivity<ThemeDetailContact.Presenter> implements ThemeDetailContact.View, View.OnClickListener {

    private ActivityThemeDetailBinding mBinding;

    private BroadcastReceiver updateThemeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            dispatchResetTheme();
        }
    };




    public static void launchFrom(Activity activity) {
        activity.startActivity(new Intent(activity, ThemeDetailActivity.class));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_theme_detail;
    }

    @Override
    protected View getContentView() {
        mBinding = ActivityThemeDetailBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {
        mBinding.ivThemeDefault.setBackground(ColorDrawableUtils.getCircleColorDrawable(ResourceRouter.getInstance().isNightTheme() ? Color.WHITE : 0x22000000));
        mBinding.ivThemeDefault.setImageDrawable(ThemeHelper.configDrawableTheme(getDrawable(R.drawable.ic_logo), getResources().getColor(R.color.themeColor)));

        mBinding.ivThemeRed.setBackground(ColorDrawableUtils.getCircleColorDrawable(getResources().getColor(R.color.themeColor)));
        mBinding.ivThemeRed.setImageDrawable(ThemeHelper.configDrawableTheme(getDrawable(R.drawable.ic_logo), Color.WHITE));


        mBinding.ivThemeBlack.setBackground(ColorDrawableUtils.getCircleColorDrawable(Color.BLACK));
        mBinding.ivThemeBlack.setImageDrawable(ThemeHelper.configDrawableTheme(getDrawable(R.drawable.ic_logo), Color.WHITE));

        GlideApp.with(this).load(R.drawable.ic_custom_color).circleCrop().into(mBinding.ivThemeCustomColor);

        Drawable drawable = new CheckedDrawable();
        mBinding.ivUseThemeDefault.setImageDrawable(drawable);
        mBinding.ivUseThemeDefault.setVisibility(View.GONE);

        mBinding.ivUseThemeRed.setImageDrawable(drawable);
        mBinding.ivUseThemeRed.setVisibility(View.GONE);

        mBinding.ivUseThemeBlack.setImageDrawable(drawable);
        mBinding.ivUseThemeBlack.setVisibility(View.GONE);

        mBinding.ivUseThemeCustomColor.setImageDrawable(drawable);
        mBinding.ivUseThemeCustomColor.setVisibility(View.GONE);

        mBinding.ivUseThemeCustomBg.setImageDrawable(drawable);
        mBinding.ivUseThemeCustomBg.setVisibility(View.GONE);
        switch (ResourceRouter.getInstance().getThemeId()){
            case ThemeConfig.THEME_DEFAULT:
                mBinding.ivUseThemeDefault.setVisibility(View.VISIBLE);
                break;
            case ThemeConfig.THEME_RED:
                mBinding.ivUseThemeRed.setVisibility(View.VISIBLE);
                break;
            case ThemeConfig.THEME_COOL_BLACK:
                mBinding.ivUseThemeBlack.setVisibility(View.VISIBLE);
                break;
            case ThemeConfig.THEME_CUSTOM_COLOR:
                mBinding.ivUseThemeCustomColor.setVisibility(View.VISIBLE);
                break;
            case ThemeConfig.THEME_CUSTOM_BG:
                mBinding.ivUseThemeCustomBg.setVisibility(View.VISIBLE);
                break;
            default:
        }
        registerReceiver(updateThemeReceiver,new IntentFilter(CHANGED_THEME));

        //@OnClick({R.id.click_theme_default, R.id.click_theme_red, R.id.click_theme_black, R.id.click_theme_custom_color, R.id.click_theme_custom_bg})
        mBinding.clickThemeDefault.setOnClickListener(this);
        mBinding.clickThemeRed.setOnClickListener(this);
        mBinding.clickThemeBlack.setOnClickListener(this);
        mBinding.clickThemeCustomColor.setOnClickListener(this);
        mBinding.clickThemeCustomBg.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        setTitle(R.string.theme_detail_activity_title);

    }

    private void setCurrentTheme(int themeId) {
        if(ThemeConfig.getCurrentThemeId() != themeId){
            ThemeAgent.getInstance().switchTheme(this, new ThemeInfo(themeId), true);
        }
    }

    private void dispatchResetTheme() {
        setWindowBackground();
        applyStatusBarCurrentTheme();
        applyToolbarCurrentTheme();
        applyRecentTaskPreviewCurrentTheme();
        invalidateOptionsMenu();
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            ThemeAgent.getInstance().switchTheme(this, new ThemeInfo(ThemeConfig.THEME_CUSTOM_COLOR), true);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(updateThemeReceiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.click_theme_default) {
            setCurrentTheme(-1);
        } else if (id == R.id.click_theme_red) {
            setCurrentTheme(-5);
        } else if (id == R.id.click_theme_black) {//                setCurrentTheme(-6);
            showToast("暂未完成");
        } else if (id == R.id.click_theme_custom_color) {
            ThemeColorDetailActivity.launchFrom(this, 100);
        } else if (id == R.id.click_theme_custom_bg) {
        }
    }
}