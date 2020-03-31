package com.ruichaoqun.luckymusic.widget.effect;

import android.util.Pair;

/**
 * @author Rui Chaoqun
 * @date :2020/3/19 10:29
 * description:
 */
public interface DynamicEffectView {

    void reset(boolean close);

    void prepareToDynamic();

    boolean isFft();

    boolean isWaveform();

    int getRate(VisualizerEntity visualizerEntity);

    Pair<Integer,Integer> getArtViewSize();

    int getCaptureSize(VisualizerEntity visualizerEntity);

    void onFftDataCapture(byte[] fft, int samplingRate);

    void onWaveFormDataCapture(byte[] waveform, int samplingRate);

    /**
     * 设置动效主题颜色
     * @param color
     */
    void setColor(int color);
}
