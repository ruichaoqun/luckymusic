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
    protected long mCurrentPlayTime;

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
     *
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

    public void addDynamicEffectView( DynamicEffectView view) {
        if (this.mEffectView != view) {
            boolean visualizerEnable = mVisualizerEntity != null && mVisualizerEntity.getEnabled();
            if (mVisualizerEntity != null) {
                //清空Visualizer回调
                mVisualizerEntity.setEnabled(false);
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
                    this.mVisualizerEntity  = this.mVisualizerEntity.getNewVisualizer();
                    this.mVisualizerEntity .setCaptureSize(view.getCaptureSize(this.mVisualizerEntity));
                    this.mVisualizerEntity.setEnabled(false);
                }
                this.mVisualizerEntity.setDataCaptureListener(this.mDataCaptureListener, view.getRate(mVisualizerEntity), view.isWaveform(), view.isFft());
            }
            this.mEffectView = view;
            this.mEffectView.setColor(this.mDominantColor);
            if (visualizerEnable) {
                prepare();
            }
        }
    }

    public void setVisualizer(int sessionId) {
        if (this.mVisualizerEntity == null || this.mSessionId != sessionId) {
            if (mVisualizerEntity != null) {
                mVisualizerEntity.release();
                this.mVisualizerEntity = null;
            }
            this.mVisualizerEntity = new VisualizerProxy(sessionId);
            this.mVisualizerEntity.setEnabled(false);
            this.mSessionId = sessionId;
            initVisualizerListener();
        }
    }

    void initVisualizerListener() {
        if (this.mDataCaptureListener == null) {
            this.mDataCaptureListener = new VisualizerEntity.DataCaptureListener() {
                @Override
                public void onWaveFormDataCapture(byte[] waveform, int samplingRate) {
                    if (mEffectView != null) {
                        mEffectView.onWaveFormDataCapture(waveform, samplingRate);
                    }
                }

                @Override
                public void onFftDataCapture(byte[] fft, int samplingRate) {
                    if (mEffectView != null) {
                        mEffectView.onFftDataCapture(fft, samplingRate);
                    }
                }
            };
        }
        if (mEffectView != null) {
            mVisualizerEntity.setCaptureSize(mEffectView.getCaptureSize(mVisualizerEntity));
            mVisualizerEntity.setDataCaptureListener(this.mDataCaptureListener, mEffectView.getRate(mVisualizerEntity), mEffectView.isWaveform(), mEffectView.isFft());
        }
    }

    /**
     * 准备接收音频数据
     */
    public void prepare() {
        if(!mVisualizerEntity.getEnabled()){
            if (mVisualizerEntity != null) {
                mVisualizerEntity.setEnabled(true);
            }
            if (mEffectView != null) {
                mEffectView.prepareToDynamic();
            }
            prepareAnimator(true);
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
        stopAnimator();
    }


    public void prepareAnimator(boolean prepare) {
        if (this.mArtView != null) {
            if (this.mAnimator == null) {
                this.mAnimator = ObjectAnimator.ofFloat(this.mArtView, ROTATION, new float[]{0.0f, 360.0f}).setDuration(25000);
                this.mAnimator.setRepeatCount(-1);
                this.mAnimator.setInterpolator(new LinearInterpolator());
            }
            if (!this.mAnimator.isRunning()) {
                this.mAnimator.setCurrentPlayTime(prepare ? this.mCurrentPlayTime : 0);
                this.mAnimator.start();
            }
        }
    }

    public void stopAnimator() {
        if (this.mAnimator != null) {
            this.mCurrentPlayTime = this.mAnimator.getCurrentPlayTime();
            this.mAnimator.cancel();
        }
    }

    public void resetArtViewAnimator() {
        this.mArtView.setRotation(0.0f);
        this.mCurrentPlayTime = 0;
        if(this.mAnimator != null && this.mAnimator.isRunning()){
            this.mAnimator.cancel();
            this.mAnimator.setCurrentPlayTime(0);
            this.mAnimator.start();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        if (mVisualizerEntity != null) {
            mVisualizerEntity.release();
            this.mVisualizerEntity = null;
        }
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
        }
        super.onDetachedFromWindow();
    }

}
