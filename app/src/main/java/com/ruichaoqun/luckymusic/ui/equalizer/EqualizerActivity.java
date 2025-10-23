package com.ruichaoqun.luckymusic.ui.equalizer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.databinding.EqualizerActivityBinding;
import com.ruichaoqun.luckymusic.media.MusicService;
import com.ruichaoqun.luckymusic.media.audioeffect.AudioEffectJsonPackage;
import com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.DefaultEffectActivity;
import com.ruichaoqun.luckymusic.ui.equalizer.defaultsetting.savepreset.EqualizerSavePresetActivity;
import com.ruichaoqun.luckymusic.utils.UiUtils;
import com.ruichaoqun.luckymusic.widget.EqualizerChartView;
import com.ruichaoqun.luckymusic.widget.EqualizerHorizontalScrollView;
import com.ruichaoqun.luckymusic.widget.EqualizerSeekBar;
import com.ruichaoqun.luckymusic.widget.LuckyMusicToolbar;

import java.util.ArrayList;
import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;

/**
 * @author Rui Chaoqun
 * @date :2020-4-12 21:11:55
 * description:EqualizerActivity
 */
public class EqualizerActivity extends BaseMVPActivity<EqualizerPresenter> implements EqualizerContact.View, EqualizerHorizontalScrollView.OnEqualizerScrollViewScrollListener, EqualizerSeekBar.OnDragFinishListener, View.OnClickListener {
//    @BindView(R.id.tv_preinstall)
//    TextView tvPreinstall;
//    @BindView(R.id.tv_save)
//    TextView tvSave;
//    @BindView(R.id.tv_advanced_setup)
//    TextView tvAdvancedSetup;
    private Switch mSwitchButton;
    private List<EqualizerSeekBar> mSeekBars;
    private AudioEffectJsonPackage mEffectJsonPackage;
    private EqualizerActivityBinding mBinding;

    public static void launchFrom(Context context) {
        context.startActivity(new Intent(context, EqualizerActivity.class));
    }

    private EqualizerChartView mChartView;

    @Override
    protected int getLayoutResId() {
        return R.layout.equalizer_activity;
    }

    @Override
    protected View getContentView() {
        mBinding = EqualizerActivityBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {
        mChartView = findViewById(R.id.chart_view);
        String[] bands = getResources().getStringArray(R.array.frequency_band);
        EqualizerHorizontalScrollView equalizerHorizontalScrollView = findViewById(R.id.scrollView);
        LinearLayout layout = findViewById(R.id.ll_equalizer);
        mSeekBars = new ArrayList<>();
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            EqualizerSeekBar seekBar = view.findViewById(R.id.seek_bar);
            seekBar.setOnProgressChangedListener(bands[i], mChartView);
            TextView textView = view.findViewById(R.id.key);
            textView.setText(bands[i]);
            seekBar.setOnDragFinishListener(this);
            mSeekBars.add(seekBar);
        }
        this.mChartView.setOnChartViewScrollListener(equalizerHorizontalScrollView);
        equalizerHorizontalScrollView.setOnEqualizerScrollViewScrollListener(this);
        RelativeLayout relativeLayout = findViewById(R.id.rl_state);
        relativeLayout.measure(0, 0);
        float a = (float) (UiUtils.getScreenWidth() - relativeLayout.getMeasuredWidth());
        mChartView.setRectRatio(a / (UiUtils.dp2px(50.0f) * 10));
        mSwitchButton = (Switch) LayoutInflater.from(this).inflate(R.layout.view_switch_button, null);
        ((LuckyMusicToolbar) toolbar).addCustomView(mSwitchButton, Gravity.RIGHT, 0, UiUtils.dp2px(7.0f), view -> {
            boolean isChecked = mSwitchButton.isChecked();
            mChartView.setEffectEnabled(isChecked);
            if (isChecked) {
                mPresenter.setEffectEnable(true);
                updateEqualizer();
            } else {
                mPresenter.setEffectEnable(false);
                updateEqualizer();
            }
        });
        mBinding.tvSave.setOnClickListener(this);
        mBinding.tvPreinstall.setOnClickListener(this);
        mBinding.tvAdvancedSetup.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        boolean enable = mPresenter.isEffectEnable();
        mSwitchButton.setChecked(enable);
        mChartView.setEffectEnabled(enable);
        mEffectJsonPackage = mPresenter.getAudioEffectJsonPackage();
        for (int i = 0; i < mSeekBars.size(); i++) {
            mSeekBars.get(i).setProgress((int) ((mEffectJsonPackage.getEq().getEqs().get(i) + 12.0f) * 100));
        }
        mChartView.setData(mEffectJsonPackage.getEq().getEqs());
        if(!mEffectJsonPackage.getEq().isOn()){
            mBinding.tvPreinstall.setText(R.string.none);
            mBinding.tvSave.setEnabled(false);
            mBinding.tvSave.setTextColor(getResources().getColor(R.color.color_663a3a));
        }else{

            if(TextUtils.isEmpty(mEffectJsonPackage.getEq().getFileName())){
                mBinding.tvPreinstall.setText(R.string.equalizer_activity_custom_define);
                mBinding.tvSave.setEnabled(true);
                mBinding.tvSave.setTextColor(getResources().getColor(R.color.colorPrimary));

            }else{
                mBinding.tvPreinstall.setText(mEffectJsonPackage.getEq().getFileName());
                mBinding.tvSave.setEnabled(false);
                mBinding.tvSave.setTextColor(getResources().getColor(R.color.color_663a3a));
            }
        }
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
        mBinding.tvPreinstall.setText(R.string.equalizer_activity_custom_define);
        mBinding.tvSave.setEnabled(true);
        mBinding.tvSave.setTextColor(getResources().getColor(R.color.colorPrimary));
        mSwitchButton.setChecked(true);
        mChartView.setEffectEnabled(true);
        List<Float> list = mChartView.getData();
        mEffectJsonPackage.getEq().setFileName("");
        mEffectJsonPackage.getEq().setEqs(list);
        mPresenter.setEffectEnable(true);
        mPresenter.setAudioEffectJsonPackage(mEffectJsonPackage);
        updateEqualizer();
    }

    private void updateEqualizer() {
        if (mControllerCompat != null) {
            mControllerCompat.getTransportControls().sendCustomAction(MusicService.CUSTOM_ACTION_EFFECT, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 || requestCode == 101){
            initData();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_preinstall) {
            DefaultEffectActivity.launchFrom(this);
        } else if (id == R.id.tv_save) {
            EqualizerSavePresetActivity.launchFrom(this, mEffectJsonPackage);
        } else if (id == R.id.tv_advanced_setup) {
        }
    }
}
