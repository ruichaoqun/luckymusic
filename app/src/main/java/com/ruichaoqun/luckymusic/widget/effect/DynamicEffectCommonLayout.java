package com.ruichaoqun.luckymusic.widget.effect;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class DynamicEffectCommonLayout extends FrameLayout {


    protected ImageView mArtView;

    protected DynamicEffectView mEffectView;

    protected int mDominantColor;

    private int mSessionId;

    private VisualizerEntity mVisualizerEntity;

    private VisualizerEntity.DataCaptureListener mDataCaptureListener;
    private ObjectAnimator mAnimator;
    private long k;

    public DynamicEffectCommonLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public DynamicEffectCommonLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DynamicEffectCommonLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mDominantColor = -1;
        this.mSessionId = -1;
    }

    /**
     * 添加封面ImageView
     * @param view 
     * @param width
     * @param height
     */
    void addArtView(ImageView view, int width, int height) {
        if (mArtView != view) {
            if (mArtView != null && mArtView.getParent() == this) {
                removeView(this.mArtView);
            }
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
            layoutParams.gravity = Gravity.CENTER;
            addView(view, layoutParams);
            this.mArtView = view;
        }
    }

    public View getArtView() {
        return this.mArtView;
    }

    public int addDynamicEffectView(DynamicEffectView view) {
        int i2 = 0;
        if (this.mEffectView != view) {
            boolean z = mVisualizerEntity != null && mVisualizerEntity.getEnabled();
            pause();
            VisualizerEntity newVisualizerEntity = null;
            if (mVisualizerEntity != null) {
                //清空Visualizer回调
                mVisualizerEntity.setDataCaptureListener(null, 0, false, false);
            }
            if (mEffectView != null) {
                //将当前动效view从layout中移除（注意移除的时候内存泄露问题）
                removeView((View) mEffectView);
            }
            if (this.mArtView != null) {
                //设置专辑图View的宽高
                Pair<Integer, Integer> viewSize = view.getArtViewSize();
                if (viewSize.first > 0 && viewSize.second > 0) {
                    ViewGroup.LayoutParams layoutParams = this.mArtView.getLayoutParams();
                    layoutParams.width = viewSize.first;
                    layoutParams.height = viewSize.second;
                }
            }
            //添加动效View
            addView((View) view, 0, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if (mVisualizerEntity != null) {
                if (mEffectView == null || mEffectView.getCaptureSize(mVisualizerEntity) != view.getCaptureSize(this.mVisualizerEntity)) {
                    this.mVisualizerEntity.release();
                    try {
                        newVisualizerEntity = this.mVisualizerEntity.getNewVisualizer();
                        newVisualizerEntity.setCaptureSize(view.getCaptureSize(newVisualizerEntity));
                    } catch (Throwable th) {
                        th.printStackTrace();
                        i2 = (!(th instanceof RuntimeException) || !th.getMessage().contains("-3")) ? -1 : -3;
                    }
                    this.mVisualizerEntity = newVisualizerEntity;
                }
                if (mVisualizerEntity != null) {
                    mVisualizerEntity.setDataCaptureListener(this.mDataCaptureListener, view.getRate(mVisualizerEntity), view.isWaveform(), view.isFft());
                }
            }
            this.mEffectView = view;
            this.mEffectView.setColor(this.mDominantColor);
            if(z){
                prepare();
            }
        }
        return i2;
    }

    public int setVisualizer(int sessionId) {
        if (this.mVisualizerEntity == null || this.mSessionId != sessionId) {
            if (mVisualizerEntity != null) {
                mVisualizerEntity.release();
                this.mVisualizerEntity = null;
            }
            try {
                this.mVisualizerEntity = new RealVisualizer(sessionId);
                if (this.mVisualizerEntity.getEnabled()) {
                    this.mVisualizerEntity.setEnabled(false);
                }
                this.mSessionId = sessionId;
                initVisualizerListener();
            } catch (Throwable th) {
                th.printStackTrace();
                return (!(th instanceof RuntimeException) || !th.getMessage().contains("-3")) ? -1 : -3;
            }
        }
        return 0;
    }

    void initVisualizerListener() {
        if (this.mDataCaptureListener == null) {
            this.mDataCaptureListener = new VisualizerEntity.DataCaptureListener() {
                @Override
                public void onWaveFormDataCapture(byte[] waveform, int samplingRate) {
                    if(mEffectView != null){
                        mEffectView.onWaveFormDataCapture(waveform,samplingRate);
                    }
                }

                @Override
                public void onFftDataCapture(byte[] fft, int samplingRate) {
                    if(mEffectView != null){
                        mEffectView.onFftDataCapture(fft,samplingRate);
                    }
                }
            };
        }
        if (mEffectView != null) {
            mVisualizerEntity.setCaptureSize(mEffectView.getCaptureSize(mVisualizerEntity));
            mVisualizerEntity.setDataCaptureListener(this.mDataCaptureListener, this.mEffectView.getRate(mVisualizerEntity), this.mEffectView.isWaveform(), this.mEffectView.isFft());
        }
    }

    /**
     * 准备接收音频数据
     */
    public void prepare() {
        if (mVisualizerEntity != null) {
            mVisualizerEntity.setEnabled(true);
        }
        if (mEffectView != null) {
            mEffectView.prepareToDynamic();
        }
    }

    /**
     * Visualizer取消接收数据，EffectView清空取消异步操作
     */
    public void pause() {
        if (mVisualizerEntity != null) {
            mVisualizerEntity.setEnabled(false);
        }
        if (mEffectView != null) {
            mEffectView.reset(false);
        }
    }



    public void prepareAnimater(boolean z) {
        View view = this.mArtView;
        if (view != null) {
            if (this.mAnimator == null) {
                this.mAnimator = ObjectAnimator.ofFloat(view, ROTATION, new float[]{0.0f, 360.0f}).setDuration(25000);
                this.mAnimator.setRepeatCount(-1);
                this.mAnimator.setInterpolator(new LinearInterpolator());
            }
            if (!this.mAnimator.isRunning()) {
                this.mAnimator.setCurrentPlayTime(z ? this.k : 0);
                this.mAnimator.start();
            }
        }
    }

    public void stopAnimator(boolean z) {
        ObjectAnimator objectAnimator = this.mAnimator;
        if (objectAnimator != null) {
            this.k = objectAnimator.getCurrentPlayTime();
            this.mAnimator.cancel();
            if (z) {
                this.mAnimator.setCurrentPlayTime(0);
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        if (mVisualizerEntity != null) {
            mVisualizerEntity.release();
            this.mVisualizerEntity = null;
        }
        super.onDetachedFromWindow();
    }

}
