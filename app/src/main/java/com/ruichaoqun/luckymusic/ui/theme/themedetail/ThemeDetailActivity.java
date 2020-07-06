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
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.ThemeInfo;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeAgent;
import com.ruichaoqun.luckymusic.theme.core.ThemeConfig;
import com.ruichaoqun.luckymusic.ui.theme.themecolor.ThemeColorDetailActivity;
import com.ruichaoqun.luckymusic.utils.drawhelper.ColorDrawableUtils;
import com.ruichaoqun.luckymusic.widget.drawable.CheckedDrawable;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ruichaoqun.luckymusic.Constants.CHANGED_THEME;

/**
 * 个性换肤
 */
public class ThemeDetailActivity extends BaseMvpToolbarActivity<ThemeDetailContact.Presenter> implements ThemeDetailContact.View {

    @BindView(R.id.iv_theme_default)
    ImageView ivThemeDefault;
    @BindView(R.id.iv_theme_red)
    ImageView ivThemeRed;
    @BindView(R.id.iv_theme_black)
    ImageView ivThemeBlack;
    @BindView(R.id.iv_theme_custom_color)
    ImageView ivThemeCustomColor;
    @BindView(R.id.iv_theme_custom_bg)
    ImageView ivThemeCustomBg;
    @BindView(R.id.iv_use_theme_default)
    ImageView ivUseThemeDefault;
    @BindView(R.id.iv_use_theme_red)
    ImageView ivUseThemeRed;
    @BindView(R.id.iv_use_theme_black)
    ImageView ivUseThemeBlack;
    @BindView(R.id.iv_use_theme_custom_color)
    ImageView ivUseThemeCustomColor;
    @BindView(R.id.iv_use_theme_custom_bg)
    ImageView ivUseThemeCustomBg;

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
    protected void initParams() {

    }

    @Override
    protected void initView() {
        ivThemeDefault.setBackground(ColorDrawableUtils.getCircleColorDrawable(ResourceRouter.getInstance().isNightTheme() ? Color.WHITE : 0x22000000));
        ivThemeDefault.setImageDrawable(ThemeHelper.configDrawableTheme(getDrawable(R.drawable.ic_logo), getResources().getColor(R.color.themeColor)));

        ivThemeRed.setBackground(ColorDrawableUtils.getCircleColorDrawable(getResources().getColor(R.color.themeColor)));
        ivThemeRed.setImageDrawable(ThemeHelper.configDrawableTheme(getDrawable(R.drawable.ic_logo), Color.WHITE));


        ivThemeBlack.setBackground(ColorDrawableUtils.getCircleColorDrawable(Color.BLACK));
        ivThemeBlack.setImageDrawable(ThemeHelper.configDrawableTheme(getDrawable(R.drawable.ic_logo), Color.WHITE));

        GlideApp.with(this).load(R.drawable.ic_custom_color).circleCrop().into(ivThemeCustomColor);

        Drawable drawable = new CheckedDrawable();
        ivUseThemeDefault.setImageDrawable(drawable);
        ivUseThemeDefault.setVisibility(View.GONE);

        ivUseThemeRed.setImageDrawable(drawable);
        ivUseThemeRed.setVisibility(View.GONE);

        ivUseThemeBlack.setImageDrawable(drawable);
        ivUseThemeBlack.setVisibility(View.GONE);

        ivUseThemeCustomColor.setImageDrawable(drawable);
        ivUseThemeCustomColor.setVisibility(View.GONE);

        ivUseThemeCustomBg.setImageDrawable(drawable);
        ivUseThemeCustomBg.setVisibility(View.GONE);
        switch (ResourceRouter.getInstance().getThemeId()){
            case ThemeConfig.THEME_DEFAULT:
                ivUseThemeDefault.setVisibility(View.VISIBLE);
                break;
            case ThemeConfig.THEME_RED:
                ivUseThemeRed.setVisibility(View.VISIBLE);
                break;
            case ThemeConfig.THEME_COOL_BLACK:
                ivUseThemeBlack.setVisibility(View.VISIBLE);
                break;
            case ThemeConfig.THEME_CUSTOM_COLOR:
                ivUseThemeCustomColor.setVisibility(View.VISIBLE);
                break;
            case ThemeConfig.THEME_CUSTOM_BG:
                ivUseThemeCustomBg.setVisibility(View.VISIBLE);
                break;
            default:
        }
        registerReceiver(updateThemeReceiver,new IntentFilter(CHANGED_THEME));

    }

    @Override
    protected void initData() {
        setTitle(R.string.theme_detail_activity_title);

    }


    @OnClick({R.id.click_theme_default, R.id.click_theme_red, R.id.click_theme_black, R.id.click_theme_custom_color, R.id.click_theme_custom_bg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.click_theme_default:
                setCurrentTheme(-1);
                break;
            case R.id.click_theme_red:
                setCurrentTheme(-5);
                break;
            case R.id.click_theme_black:
//                setCurrentTheme(-6);
                showToast("暂未完成");
                break;
            case R.id.click_theme_custom_color:
                ThemeColorDetailActivity.launchFrom(this,100);
                break;
            case R.id.click_theme_custom_bg:
                break;
            default:
        }
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
}