package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.util.Pair;
import android.view.View;

public class AbbrEffectView extends View implements DynamicEffectView {

    private int length;
    private int samplingRate;

    public AbbrEffectView(Context context) {
        super(context);
    }

    @Override
    public void setColor(int color) {

    }

    @Override
    public void onFftDataCapture(byte[] fft, int samplingRate) {
        int length = fft.length;
        if(length > 0){
            if(this.length != length || this.samplingRate != samplingRate){
                this.length = length;
                this.samplingRate = samplingRate;
            }
        }
    }

    @Override
    public void onWaveFormDataCapture(byte[] waveform, int samplingRate) {

    }

    @Override
    public void reset(boolean close) {

    }

    @Override
    public void prepareToDynamic() {

    }

    @Override
    public boolean isFft() {
        return true;
    }

    @Override
    public boolean isWaveform() {
        return false;
    }

    @Override
    public int getRate(VisualizerEntity visualizerEntity) {
        int rate = (int) (visualizerEntity.getMaxCaptureRate() * 0.85d);
        this.B = 1000000.0f /rate;
        return d2;
    }

    @Override
    public Pair<Integer, Integer> getArtViewSize() {
        return null;
    }

    @Override
    public int getCaptureSize(VisualizerEntity visualizerEntity) {
        return visualizerEntity.getCaptureSizeRange()[1];
    }
}
