package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.utils.ColorUtil;

/**
 * @author Rui Chaoqun
 * @date :2020/4/3 15:58
 * description:
 */
public class TestEffectView7 extends FrameLayout implements DynamicEffectView {

    /* renamed from: a  reason: collision with root package name */
    private ImageView f9786a;

    /* renamed from: b  reason: collision with root package name */
    private DynamicEffectView f9787b;

    /* renamed from: c  reason: collision with root package name */
    private float[] f9788c = new float[3];

    public TestEffectView7(Context context, boolean z) {
        super(context);
        this.f9786a = new ImageView(context);
        this.f9786a.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.f9786a.setImageResource(R.drawable.c0e);
        this.f9786a.setColorFilter(ColorUtil.getEffectColor(-1, this.f9788c));
        addView(this.f9786a, new FrameLayout.LayoutParams(-1, -1));
        this.f9787b = z ? new TestEffectView8(context) : new TestEffectView9(context);
        addView((View) this.f9787b, new FrameLayout.LayoutParams(-1, -1));
    }

    @Override
    public Pair<Integer, Integer> getArtViewSize() {
        return this.f9787b.getArtViewSize();
    }

    @Override
    public int getCaptureSize(VisualizerEntity dVar) {
        return this.f9787b.getCaptureSize(dVar);
    }

    @Override
    public int getRate(VisualizerEntity dVar) {
        return this.f9787b.getRate(dVar);
    }

    @Override
    public boolean isFft() {
        return this.f9787b.isFft();
    }

    @Override
    public boolean isWaveform() {
        return this.f9787b.isWaveform();
    }

    @Override
    public void setColor(int i2) {
        this.f9786a.setColorFilter(ColorUtil.getEffectColor(i2, this.f9788c));
        this.f9787b.setColor(i2);
    }

    @Override
    public void onFftDataCapture(byte[] obj, int i2) {
        this.f9787b.onFftDataCapture(obj, i2);
    }

    @Override
    public void onWaveFormDataCapture(byte[] obj, int i2) {
        this.f9787b.onWaveFormDataCapture(obj, i2);
    }

    @Override
    public void prepareToDynamic() {
        this.f9787b.prepareToDynamic();
    }

    @Override
    public void reset(boolean z) {
        this.f9787b.reset(z);
    }
}
