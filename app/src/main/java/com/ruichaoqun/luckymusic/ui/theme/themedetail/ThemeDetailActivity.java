package com.ruichaoqun.luckymusic.ui.theme.themedetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMvpToolbarActivity;
import com.ruichaoqun.luckymusic.common.GlideApp;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.ThemeInfo;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.theme.core.ThemeConfig;
import com.ruichaoqun.luckymusic.utils.drawhelper.ColorDrawableUtils;
import com.ruichaoqun.luckymusic.widget.drawable.CheckedDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    }

    @Override
    protected void initData() {
        setTitle(R.string.theme_detail_activity_title);
    }


    @OnClick({R.id.click_theme_default, R.id.click_theme_red, R.id.click_theme_black, R.id.click_theme_custom_color, R.id.click_theme_custom_bg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.click_theme_default:
                setCustomTheme(-1);
                break;
            case R.id.click_theme_red:
                break;
            case R.id.click_theme_black:
                break;
            case R.id.click_theme_custom_color:
                break;
            case R.id.click_theme_custom_bg:
                break;
            default:
        }
    }

}