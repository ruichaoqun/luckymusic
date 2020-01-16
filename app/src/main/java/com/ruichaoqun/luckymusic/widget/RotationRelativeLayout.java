package com.ruichaoqun.luckymusic.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.utils.CommonUtils;

import java.util.HashMap;


public class RotationRelativeLayout extends RelativeLayout{
    private static final boolean DEBUG = false;
    private static final String TAG = "DiscRotation";
    private boolean isLollipopAndUp = false;
    /* access modifiers changed from: private */
    public boolean mActivityPaused = false;
    /* access modifiers changed from: private */
    public ValueAnimator mAnimator;
    private int mDelayTime;
    /* access modifiers changed from: private */
    public boolean mFirst = true;
    private AnimationHolder mHolder;
    private Runnable mResumeRunnable;

    /* compiled from: ProGuard */
    public interface AnimationHolderImpl {
        boolean isPrepared();

        boolean isRunning();

        void pause();

        void prepareAnimation();

        void reset();

        void start();

        void stop();
    }

    public RotationRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mAnimator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.mAnimator.setDuration(25000);
        this.mAnimator.setInterpolator(new LinearInterpolator());
        this.mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        this.mAnimator.setRepeatMode(ValueAnimator.RESTART);
        this.mHolder = new AnimationHolder();
        this.isLollipopAndUp = CommonUtils.versionAbove21();
        this.mDelayTime = 50;
    }

    public AnimationHolder getAnimationHolder() {
        return this.mHolder;
    }

//    public void dispatchDraw(Canvas canvas) {
//        canvas.setDrawFilter(MiniPlayBarInfoLayout.ANTI_ALIAS_FILTER);
//        super.dispatchDraw(canvas);
//    }

    public void reset() {
        this.mHolder.reset();
    }

    public void prepareAnimation() {
        this.mHolder.prepareAnimation();
    }

    public void stop() {
        this.mHolder.stop();
    }

    public void stopAndRest() {
        this.mHolder.stop();
        this.mHolder.reset();
    }

    public void start() {
        this.mHolder.start();
    }

    public void pause() {
        this.mHolder.pause();
    }

    public boolean isPrepared() {
        return this.mHolder.isPrepared();
    }

    public boolean isRunning() {
        return this.mHolder.isRunning();
    }

    /* access modifiers changed from: private */
    public void onResumeCalledInner(boolean z) {
        if (z) {
            this.mHolder.pause();
        }
        if (!this.mHolder.isPrepared()) {
            this.mHolder.prepareAnimation();
        }
    }

//    public void onResumeCalled(final PlayerSeekBar playerSeekBar) {
//        this.mActivityPaused = false;
//        if (this.isLollipopAndUp) {
//            this.mResumeRunnable = new Runnable() {
//                public void run() {
//                    RotationRelativeLayout.this.onResumeCalledInner(playerSeekBar.isCaching());
//                }
//            };
//        } else {
//            this.mResumeRunnable = new Runnable() {
//                public void run() {
//                    boolean z = false;
//                    if (RotationRelativeLayout.this.mFirst) {
//                        boolean unused = RotationRelativeLayout.this.mFirst = false;
//                        RotationRelativeLayout.this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                            public boolean onPreDraw() {
//                                boolean z;
//                                RotationRelativeLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
//                                RotationRelativeLayout rotationRelativeLayout = RotationRelativeLayout.this;
//                                if (playerSeekBar == null || !playerSeekBar.isCaching()) {
//                                    z = false;
//                                } else {
//                                    z = true;
//                                }
//                                rotationRelativeLayout.onResumeCalledInner(z);
//                                return false;
//                            }
//                        });
//                        return;
//                    }
//                    RotationRelativeLayout rotationRelativeLayout = RotationRelativeLayout.this;
//                    if (playerSeekBar != null && playerSeekBar.isCaching()) {
//                        z = true;
//                    }
//                    rotationRelativeLayout.onResumeCalledInner(z);
//                }
//            };
//        }
//        postDelayed(this.mResumeRunnable, this.isLollipopAndUp ? (long) (this.mDelayTime * 2) : (long) this.mDelayTime);
//    }

    public void onEnterAnimationCompleteCalled(boolean z) {
        removeCallbacks(this.mResumeRunnable);
        onResumeCalledInner(z);
    }

    public void onPauseCalled() {
        this.mActivityPaused = true;
        removeCallbacks(this.mResumeRunnable);
        this.mHolder.stop();
    }

    public static HashMap<Integer, int[]> getAnimationInfo(View view) {
        HashMap<Integer, int[]> hashMap = new HashMap<>();
        int[] iArr = new int[2];
        int[] iArr2 = new int[5];
        view.getLocationOnScreen(iArr);
        iArr2[0] = iArr[0] + view.getPaddingLeft();
        iArr2[1] = iArr[1] + view.getPaddingTop();
        if (view.getParent() instanceof RotationRelativeLayout) {
            Rect rect = new Rect();
            ((View) view.getParent().getParent()).getGlobalVisibleRect(rect);
            iArr2[0] = (rect.centerX() - (view.getWidth() / 2)) + view.getPaddingLeft();
            iArr2[1] = (rect.centerY() - (view.getHeight() / 2)) + view.getPaddingTop();
        }
        iArr2[2] = (view.getWidth() - view.getPaddingLeft()) - view.getPaddingRight();
        iArr2[3] = (view.getHeight() - view.getPaddingTop()) - view.getPaddingBottom();
        iArr2[4] = 1;
        hashMap.put(0, iArr2);
        return hashMap;
    }

    /* compiled from: ProGuard */
    public class AnimationHolder implements ValueAnimator.AnimatorUpdateListener, AnimationHolderImpl {
        private boolean mPrepared = false;
        private float mPrevRotation = 0.0f;
        private float mRotationOffset = 0.0f;
        private boolean mRunning = false;
        private boolean mStartCalled = false;

        public AnimationHolder() {
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float animatedFraction = ((valueAnimator.getAnimatedFraction() * 360.0f) + this.mRotationOffset) % 360.0f;
            RotationRelativeLayout.this.setRotation(animatedFraction);
            this.mPrevRotation = animatedFraction;
        }

        private void startAnimator() {
            if (RotationRelativeLayout.this.mAnimator.isStarted()) {
                RotationRelativeLayout.this.mAnimator.end();
                RotationRelativeLayout.this.mAnimator.removeAllUpdateListeners();
            }
            this.mPrevRotation = 0.0f;
            RotationRelativeLayout.this.mAnimator.addUpdateListener(this);
            RotationRelativeLayout.this.mAnimator.start();
            this.mRunning = true;
        }

        private void stopAnimator() {
            if (RotationRelativeLayout.this.mAnimator.isStarted()) {
                RotationRelativeLayout.this.mAnimator.end();
                RotationRelativeLayout.this.mAnimator.removeAllUpdateListeners();
            }
            this.mPrevRotation = 0.0f;
            this.mRunning = false;
        }

        private void resetRotation(float f2) {
            RotationRelativeLayout.this.setRotation(f2);
        }

        @Override
        public void reset() {
            resetRotation(0.0f);
            this.mPrevRotation = 0.0f;
            this.mRotationOffset = 0.0f;
        }

        @Override
        public void prepareAnimation() {
            if (!RotationRelativeLayout.this.mActivityPaused) {
                this.mPrepared = true;
                if (this.mStartCalled && !this.mRunning) {
                    startAnimator();
                }
            }
        }

        @Override
        public void stop() {
            this.mStartCalled = false;
            if (this.mRunning) {
                this.mRotationOffset = this.mPrevRotation;
            }
            stopAnimator();
            this.mPrepared = false;
        }

        @Override
        public void start() {
            this.mStartCalled = true;
            if (this.mPrepared && !this.mRunning) {
                startAnimator();
            }
        }

        @Override
        public void pause() {
            this.mStartCalled = false;
            if (this.mRunning && this.mPrepared) {
                this.mRotationOffset = this.mPrevRotation;
                stopAnimator();
            }
        }

        @Override
        public boolean isPrepared() {
            return this.mPrepared;
        }

        @Override
        public boolean isRunning() {
            return this.mRunning;
        }
    }

}
