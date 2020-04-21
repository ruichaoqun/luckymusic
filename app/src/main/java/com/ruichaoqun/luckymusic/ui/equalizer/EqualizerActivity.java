package com.ruichaoqun.luckymusic.ui.equalizer;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseMediaBrowserActivity;
import com.ruichaoqun.luckymusic.utils.UiUtils;
import com.ruichaoqun.luckymusic.widget.EqualizerChartView;
import com.ruichaoqun.luckymusic.widget.EqualizerHorizontalScrollView;
import com.ruichaoqun.luckymusic.widget.EqualizerSeekBar;

import java.util.List;

/**
 * @author Rui Chaoqun
 * @date :2020-4-12 21:11:55
 * description:EqualizerActivity
 */
public class EqualizerActivity extends BaseMediaBrowserActivity implements EqualizerContact.View, EqualizerHorizontalScrollView.OnEqualizerScrollViewScrollListener, EqualizerSeekBar.OnDragFinishListener {
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
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            EqualizerSeekBar seekBar = (EqualizerSeekBar) view.findViewById(R.id.seek_bar);
            seekBar.setOnProgressChangedListener(bands[i],mChartView);
            TextView textView = view.findViewById(R.id.key);
            textView.setText(bands[i]);
            seekBar.setOnDragFinishListener(this);
        }
        this.mChartView.setOnChartViewScrollListener(equalizerHorizontalScrollView);
        equalizerHorizontalScrollView.setOnEqualizerScrollViewScrollListener(this);
        RelativeLayout relativeLayout = findViewById(R.id.rl_state);
        relativeLayout.measure(0,0);
        float a = (float) (UiUtils.getScreenWidth() - relativeLayout.getMeasuredWidth());
        mChartView.setRectRatio(a/(UiUtils.dp2px(50.0f)*10));
    }

    @Override
    protected void initData() {

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
    public void onDragFinish() {
        List<Float> list = mChartView.getData();
        updateEqualizer();
    }

    private void updateEqualizer() {
        Bundle bundle = new Bundle();
//        mControllerCompat.getTransportControls().sendCustomAction();
//        Equalizer equaliz = new Equalizer();
    }
}
