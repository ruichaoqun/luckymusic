package com.ruichaoqun.luckymusic.widget.effect;

/**
 * @author Rui Chaoqun
 * @date :2020/3/19 10:01
 * description:
 */
public interface VisualizerEntity {
    interface DataCaptureListener {
        void onWaveFormDataCapture(byte[] waveform, int samplingRate);

        void onFftDataCapture(byte[] fft, int samplingRate);
    }

    int setCaptureSize(int size);

    int setDataCaptureListener(DataCaptureListener mlistener, int rate, boolean waveform, boolean fft);

    int setEnabled(boolean enabled);

    void release();

    boolean getEnabled();

    int[] getCaptureSizeRange();

    int getMaxCaptureRate();

    VisualizerEntity getNewVisualizer();

    void e();
}
