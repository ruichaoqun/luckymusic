package com.ruichaoqun.luckymusic.ui.equalizer;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.media.MusicService;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectJsonPackage;
import com.ruichaoqun.luckymusic.utils.UiUtils;
import com.ruichaoqun.luckymusic.widget.EqualizerChartView;
import com.ruichaoqun.luckymusic.widget.EqualizerHorizontalScrollView;
import com.ruichaoqun.luckymusic.widget.EqualizerSeekBar;
import com.ruichaoqun.luckymusic.widget.LuckyMusicToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2020-4-12 21:11:55
 * description:EqualizerActivity
 */
public class EqualizerActivity extends BaseMVPActivity<EqualizerPresenter> implements EqualizerContact.View, EqualizerHorizontalScrollView.OnEqualizerScrollViewScrollListener, EqualizerSeekBar.OnDragFinishListener {
    private SwitchButton mSwitchButton;
    private List<EqualizerSeekBar> mSeekBars;
    private AudioEffectJsonPackage mEffectJsonPackage;

    public static void launchFrom(Context context){
        context.startActivity(new Intent(context,EqualizerActivity.class));
    }

    private EqualizerChartView mChartView;

    @Override
    protected int getLayoutResId() {
        return R.layout.equalizer_activity;
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {
        mChartView = findViewById(R.id.chart_view);
        String[] bands = getResources().getStringArray(R.array.frequency_band);
        EqualizerHorizontalScrollView equalizerHorizontalScrollView = findViewById(R.id.scrollView) ;
        LinearLayout layout = findViewById(R.id.ll_equalizer);
        mSeekBars = new ArrayList<>();
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            EqualizerSeekBar seekBar =  view.findViewById(R.id.seek_bar);
            seekBar.setOnProgressChangedListener(bands[i],mChartView);
            TextView textView = view.findViewById(R.id.key);
            textView.setText(bands[i]);
            seekBar.setOnDragFinishListener(this);
            mSeekBars.add(seekBar);
        }
        this.mChartView.setOnChartViewScrollListener(equalizerHorizontalScrollView);
        equalizerHorizontalScrollView.setOnEqualizerScrollViewScrollListener(this);
        RelativeLayout relativeLayout = findViewById(R.id.rl_state);
        relativeLayout.measure(0,0);
        float a = (float) (UiUtils.getScreenWidth() - relativeLayout.getMeasuredWidth());
        mChartView.setRectRatio(a/(UiUtils.dp2px(50.0f)*10));
        mSwitchButton = (SwitchButton) LayoutInflater.from(this).inflate(R.layout.view_switch_button,null);
        ((LuckyMusicToolbar) toolbar).addCustomView(mSwitchButton, Gravity.RIGHT, 0, UiUtils.dp2px(7.0f), view -> {
            boolean isChecked = mSwitchButton.isChecked();
            if (isChecked) {
                mPresenter.setEffectEnable(true);
                updateEqualizer();
            } else {
                mPresenter.setEffectEnable(false);
                updateEqualizer();
            }
        });
    }

    @Override
    protected void initData() {
        boolean enable = mPresenter.isEffectEnable();
        mSwitchButton.setChecked(enable);
        mChartView.setEffectEnabled(enable);
        mEffectJsonPackage = mPresenter.getAudioEffectJsonPackage();
        for (int i = 0; i < mSeekBars.size(); i++) {
            mSeekBars.get(i).setProgress((int) ((mEffectJsonPackage.getEq().getEqs().get(i)+12.0f)*100));
        }
        mChartView.setData(mEffectJsonPackage.getEq().getEqs());
    }

    @Override
    public void onEqualizerScrollViewScroll(float ratio) {
        mChartView.setScrollRatio(ratio);
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void hideLoading() {
    }

    @Override
    public boolean isNeedMiniPlayerBar() {
        return false;
    }

    @Override
    public void onDragFinish() {
        mSwitchButton.setChecked(true);
        mChartView.setEffectEnabled(true);
        List<Float> list = mChartView.getData();
        mEffectJsonPackage.getEq().setEqs(list);
        mPresenter.setEffectEnable(true);
        mPresenter.setAudioEffectJsonPackage(mEffectJsonPackage);
        updateEqualizer();
    }

    private void updateEqualizer() {
        if(mControllerCompat != null){
            mControllerCompat.getTransportControls().sendCustomAction(MusicService.CUSTOM_ACTION_EFFECT,null);
        }
    }
}
