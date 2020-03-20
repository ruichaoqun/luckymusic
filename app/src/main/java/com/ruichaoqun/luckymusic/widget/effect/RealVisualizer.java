package com.ruichaoqun.luckymusic.widget.effect;

import android.media.audiofx.Visualizer;
import android.util.Log;

/**
 * @author Rui Chaoqun
 * @date :2020/3/19 10:00
 * description:
 */
public class RealVisualizer implements VisualizerEntity{
    private int mAudioSessionId;

    private Visualizer mVisualizer;

    private Visualizer.OnDataCaptureListener mCaptureListener;

    private VisualizerEntity.DataCaptureListener mDataCaptureListener;


    public RealVisualizer(int sessionId) {
        this.mAudioSessionId = sessionId;
        this.mVisualizer = new Visualizer(sessionId);
    }

    @Override
    public void release() {
        this.mVisualizer.release();
        this.mVisualizer = null;
    }

    @Override
    public int setEnabled(boolean enabled) {
        return this.mVisualizer.setEnabled(enabled);
    }

    @Override
    public boolean getEnabled() {
        return this.mVisualizer.getEnabled();
    }

    @Override
    public int[] getCaptureSizeRange() {
        return Visualizer.getCaptureSizeRange();
    }

    @Override
    public int getMaxCaptureRate() {
        return Visualizer.getMaxCaptureRate();
    }

    @Override
    public int setCaptureSize(int size) {
        return this.mVisualizer.setCaptureSize(size);
    }

    @Override
    public int setDataCaptureListener(VisualizerEntity.DataCaptureListener listener, int rate, boolean waveform, boolean fft) {
        this.mDataCaptureListener = listener;
        Log.w("AAAAAA",""+listener);
        if (listener == null) {
            return this.mVisualizer.setDataCaptureListener(null, rate, waveform, fft);
        }
        if (this.mCaptureListener == null) {
            this.mCaptureListener = new Visualizer.OnDataCaptureListener() {
                @Override
                public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                    if (RealVisualizer.this.mDataCaptureListener != null) {
                        RealVisualizer.this.mDataCaptureListener.onWaveFormDataCapture(waveform, samplingRate);
                    }
                }

                @Override
                public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                    if (RealVisualizer.this.mDataCaptureListener != null) {
                        RealVisualizer.this.mDataCaptureListener.onFftDataCapture(fft, samplingRate);
                    }
                }
            };
        }
        return this.mVisualizer.setDataCaptureListener(this.mCaptureListener, rate, waveform, fft);
    }

    @Override
    public VisualizerEntity getNewVisualizer() {
        return new RealVisualizer(this.mAudioSessionId);
    }

    @Override
    public void e() {

    }
}
