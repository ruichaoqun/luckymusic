package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import android.widget.ViewFlipper;

import com.ruichaoqun.luckymusic.utils.CommonUtils;

/**
 * @author Rui Chaoqun
 * @date :2020/1/14 9:51
 * description:
 */
public class PlayerDiscViewFlipper extends ViewFlipper{
    public static final int MAX_ANGLE = 60;
    private static final int switchDiscDuration = 400;
    private long downMotionEventTime = 0;
    private boolean gestureEnable = true;
    /* access modifiers changed from: private */
    public boolean hadNotifyHalfLeft;
    /* access modifiers changed from: private */
    public boolean hadNotifyHalfMiddle;
    /* access modifiers changed from: private */
    public boolean hadNotifyHalfRight;
    /* access modifiers changed from: private */
    public boolean hadNotifyLeft;
    /* access modifiers changed from: private */
    public boolean hadNotifyRight;
    /* access modifiers changed from: private */
    public boolean hadNotifyScroll = false;
    private boolean hadPassedDownToGesture;
    private boolean inTouching = false;
    /* access modifiers changed from: private */
    public boolean isScrollingLeft;
    /* access modifiers changed from: private */
    public boolean justSwitchDisc;
    /* access modifiers changed from: private */
    public int leftOffset = 0;
    private OnGestureListener mOnGesture = new SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            boolean z;
            if (motionEvent == null || motionEvent2 == null || CommonUtils.getScrollAngle(motionEvent2.getX(), motionEvent2.getY(), motionEvent.getX(), motionEvent.getY()) >= 60) {
                return false;
            }
            PlayerDiscViewFlipper.this.scrollDistanceX = motionEvent.getX() - motionEvent2.getX();
            PlayerDiscViewFlipper.this.isScrollingLeft = PlayerDiscViewFlipper.this.scrollDistanceX > 0.0f;
            if (!PlayerDiscViewFlipper.this.hadNotifyScroll) {
                PlayerDiscViewFlipper.this.hadNotifyScroll = true;
                PlayerDiscViewFlipper.this.justSwitchDisc = false;
                if (PlayerDiscViewFlipper.this.onPlayerDiscListener != null) {
                    OnPlayerDiscListener access$200 = PlayerDiscViewFlipper.this.onPlayerDiscListener;
                    if (PlayerDiscViewFlipper.this.scrollDistanceX > 0.0f) {
                        z = true;
                    } else {
                        z = false;
                    }
                    access$200.onScrolled(z);
                }
            }
            if (PlayerDiscViewFlipper.this.getCurrentView().getLeft() < 0 && !PlayerDiscViewFlipper.this.hadNotifyRight) {
                if (PlayerDiscViewFlipper.this.onPlayerDiscListener != null) {
                    PlayerDiscViewFlipper.this.onPlayerDiscListener.onDiscDirectionChange(Boolean.valueOf(false));
                }
                PlayerDiscViewFlipper.this.hadNotifyLeft = false;
                PlayerDiscViewFlipper.this.hadNotifyRight = true;
            } else if (PlayerDiscViewFlipper.this.getCurrentView().getLeft() > 0 && !PlayerDiscViewFlipper.this.hadNotifyLeft) {
                if (PlayerDiscViewFlipper.this.onPlayerDiscListener != null) {
                    PlayerDiscViewFlipper.this.onPlayerDiscListener.onDiscDirectionChange(Boolean.valueOf(true));
                }
                PlayerDiscViewFlipper.this.hadNotifyLeft = true;
                PlayerDiscViewFlipper.this.hadNotifyRight = false;
            }
            if (PlayerDiscViewFlipper.this.getCurrentView().getLeft() < (-PlayerDiscViewFlipper.this.getWidth()) / 2 && !PlayerDiscViewFlipper.this.hadNotifyHalfRight) {
                if (PlayerDiscViewFlipper.this.onPlayerDiscListener != null) {
                    PlayerDiscViewFlipper.this.onPlayerDiscListener.onDiscSwitchHalf(Boolean.valueOf(false));
                }
                PlayerDiscViewFlipper.this.hadNotifyHalfMiddle = false;
                PlayerDiscViewFlipper.this.hadNotifyHalfLeft = false;
                PlayerDiscViewFlipper.this.hadNotifyHalfRight = true;
            } else if (PlayerDiscViewFlipper.this.getCurrentView().getLeft() > PlayerDiscViewFlipper.this.getWidth() / 2 && !PlayerDiscViewFlipper.this.hadNotifyHalfLeft) {
                if (PlayerDiscViewFlipper.this.onPlayerDiscListener != null) {
                    PlayerDiscViewFlipper.this.onPlayerDiscListener.onDiscSwitchHalf(Boolean.valueOf(true));
                }
                PlayerDiscViewFlipper.this.hadNotifyHalfMiddle = false;
                PlayerDiscViewFlipper.this.hadNotifyHalfLeft = true;
                PlayerDiscViewFlipper.this.hadNotifyHalfRight = false;
            } else if (((PlayerDiscViewFlipper.this.getCurrentView().getLeft() >= (-PlayerDiscViewFlipper.this.getWidth()) / 2 && PlayerDiscViewFlipper.this.getCurrentView().getLeft() < 0) || (PlayerDiscViewFlipper.this.getCurrentView().getLeft() <= PlayerDiscViewFlipper.this.getWidth() / 2 && PlayerDiscViewFlipper.this.getCurrentView().getLeft() > 0)) && !PlayerDiscViewFlipper.this.hadNotifyHalfMiddle) {
                if (PlayerDiscViewFlipper.this.onPlayerDiscListener != null) {
                    PlayerDiscViewFlipper.this.onPlayerDiscListener.onDiscSwitchHalf(null);
                }
                PlayerDiscViewFlipper.this.hadNotifyHalfMiddle = true;
                PlayerDiscViewFlipper.this.hadNotifyHalfLeft = false;
                PlayerDiscViewFlipper.this.hadNotifyHalfRight = false;
            }
            PlayerDiscViewFlipper.this.requestLayout();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            PlayerDiscViewFlipper.this.leftOffset = PlayerDiscViewFlipper.this.getCurrentView().getLeft();
            PlayerDiscViewFlipper.this.scrollDistanceX = 0.0f;
            PlayerDiscViewFlipper.this.justSwitchDisc = false;
            return true;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            if (PlayerDiscViewFlipper.this.onLongClickListener != null) {
                PlayerDiscViewFlipper.this.onLongClickListener.onLongClick(PlayerDiscViewFlipper.this);
            }
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (PlayerDiscViewFlipper.this.onDoubleTapListener != null) {
                return PlayerDiscViewFlipper.this.onDoubleTapListener.onDoubleTap(motionEvent);
            }
            return super.onDoubleTap(motionEvent);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (PlayerDiscViewFlipper.this.onClickListener != null) {
                PlayerDiscViewFlipper.this.onClickListener.onClick(PlayerDiscViewFlipper.this);
            }
            return true;
        }
    };
    private GestureDetector mGesture = new GestureDetector(getContext(), this.mOnGesture);
    private Scroller mScroller = new Scroller(getContext(), new DecelerateInterpolator());
    private int needCallBackOnDiscSwitchComplete = 0;
    private int nextId = -1;
    /* access modifiers changed from: private */
    public OnClickListener onClickListener;
    /* access modifiers changed from: private */
    public OnDoubleTapListener onDoubleTapListener;
    /* access modifiers changed from: private */
    public OnLongClickListener onLongClickListener;
    /* access modifiers changed from: private */
    public OnPlayerDiscListener onPlayerDiscListener;
    private Runnable requestLayoutRunnable = new Runnable() {
        @Override
        public void run() {
            PlayerDiscViewFlipper.this.requestLayout();
        }
    };
    /* access modifiers changed from: private */
    public float scrollDistanceX = 0.0f;

    /* renamed from: com.netease.cloudmusic.ui.PlayerDiscViewFlipper$OnPlayerDiscListener */
    /* compiled from: ProGuard */
    public interface OnPlayerDiscListener {
        void onDiscDirectionChange(Boolean bool);

        /**
         *
         * @param z 是否已切换disc
         * @param justSwitchDisc 是否直接调用切换disc
         * @param isScrollingLeft  是否是向左滑动
         */
        void onDiscSwitchComplete(boolean z, boolean justSwitchDisc, boolean isScrollingLeft);

        void onDiscSwitchHalf(Boolean bool);

        void onScrolled(boolean z);
    }

    public PlayerDiscViewFlipper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setGestureEnable(boolean z) {
        this.gestureEnable = z;
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener2) {
        this.onClickListener = onClickListener2;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener onLongClickListener2) {
        this.onLongClickListener = onLongClickListener2;
    }

    public void setOnDoubleTapListener(OnDoubleTapListener onDoubleTapListener2) {
        this.onDoubleTapListener = onDoubleTapListener2;
    }

    public void setOnPlayerDiscListener(OnPlayerDiscListener onPlayerDiscListener2) {
        this.onPlayerDiscListener = onPlayerDiscListener2;
    }

    public boolean isInTouching() {
        return this.inTouching;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.gestureEnable) {
            return false;
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            this.inTouching = true;
            this.downMotionEventTime = System.currentTimeMillis();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            if (this.scrollDistanceX != 0.0f || this.justSwitchDisc) {
                this.needCallBackOnDiscSwitchComplete++;
            }
            this.inTouching = false;
            this.hadNotifyHalfMiddle = false;
            this.hadNotifyHalfLeft = false;
            this.hadNotifyHalfRight = false;
            this.hadNotifyRight = false;
            this.hadNotifyLeft = false;
            this.hadNotifyScroll = false;
            float currentTimeMillis = this.scrollDistanceX / ((float) (System.currentTimeMillis() - this.downMotionEventTime));
            this.scrollDistanceX = 0.0f;
            int left = getCurrentView().getLeft();
            if (left > getMeasuredWidth() / 2 || ((double) currentTimeMillis) < -1.7d) {
                this.nextId = getDisplayedChild() + 1;
                if (this.hadPassedDownToGesture) {
                    this.mScroller.startScroll(left, 0, getMeasuredWidth() - getCurrentView().getLeft(), 0, 400);
                }
            } else if (left > 0) {
                if (this.hadPassedDownToGesture) {
                    this.mScroller.startScroll(left, 0, -getCurrentView().getLeft(), 0, 400);
                }
            } else if (left < (-getMeasuredWidth()) / 2 || ((double) currentTimeMillis) > 1.7d) {
                this.nextId = getDisplayedChild() + 1;
                if (this.hadPassedDownToGesture) {
                    this.mScroller.startScroll(left, 0, (-getMeasuredWidth()) - left, 0, 400);
                }
            } else if (left >= 0) {
                this.needCallBackOnDiscSwitchComplete++;
            } else if (this.hadPassedDownToGesture) {
                this.mScroller.startScroll(left, 0, -left, 0, 400);
            }
            requestLayout();
        }
        boolean isFinished = this.mScroller.isFinished();
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            this.hadPassedDownToGesture = isFinished;
        }
        if (isFinished) {
            if (!this.hadPassedDownToGesture) {
                this.hadPassedDownToGesture = true;
                MotionEvent obtain = MotionEvent.obtain(motionEvent);
                obtain.setAction(0);
                this.mGesture.onTouchEvent(obtain);
            }
            this.mGesture.onTouchEvent(motionEvent);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    @Override
    public void onLayout(boolean changed,int left, int top, int right, int bottom) {
        int currentX;
        int i6;
        int i7;
        boolean z2 = false;
        if (this.mScroller.computeScrollOffset()) {
            currentX = this.mScroller.getCurrX();
        } else {
            currentX = (int) (((float) this.leftOffset) - this.scrollDistanceX);
        }
        if (currentX <= (-getMeasuredWidth()) && this.isScrollingLeft) {
            currentX = -getMeasuredWidth();
        }
        int measuredWidth = currentX + getMeasuredWidth();
        getCurrentView().layout(currentX, 0, measuredWidth, getMeasuredHeight());
        int measuredWidth2 = getMeasuredWidth() + measuredWidth;
        if (!this.isScrollingLeft) {
            int measuredWidth3 = currentX - getMeasuredWidth();
            if (measuredWidth3 > 0) {
                measuredWidth3 = 0;
            }
            i7 = measuredWidth3;
            i6 = getMeasuredWidth() + measuredWidth3;
        } else {
            i6 = measuredWidth2;
            i7 = measuredWidth;
        }
        if (i7 < getMeasuredWidth() || i7 > 0) {
            View nextView = getNextView();
            nextView.setVisibility(View.VISIBLE);
            nextView.layout(i7, 0, i6, getMeasuredHeight());
        }
        if (!this.mScroller.isFinished()) {
            post(this.requestLayoutRunnable);
            return;
        }
        if (this.nextId != -1) {
            setDisplayedChild(this.nextId);
            this.nextId = -1;
        } else {
            z2 = true;
        }
        if (this.needCallBackOnDiscSwitchComplete > 0) {
            if (this.onPlayerDiscListener != null) {
                boolean finalZ = z2;
                post(new Runnable() {
                    @Override
                    public void run() {
                        PlayerDiscViewFlipper.this.onPlayerDiscListener.onDiscSwitchComplete(finalZ, PlayerDiscViewFlipper.this.justSwitchDisc, PlayerDiscViewFlipper.this.isScrollingLeft);
                    }
                });
            }
            this.needCallBackOnDiscSwitchComplete--;
        }
    }

    public void switchDisc(boolean z) {
        int i;
        boolean z2;
        int i2 = 1;
        if (!this.mScroller.isFinished()) {
            this.mScroller.forceFinished(true);
            if (this.needCallBackOnDiscSwitchComplete > 0) {
                if (this.onPlayerDiscListener != null) {
                    this.onPlayerDiscListener.onDiscSwitchComplete(false, true, z);
                }
                this.needCallBackOnDiscSwitchComplete--;
            }
        }
        if (this.onPlayerDiscListener != null) {
            this.onPlayerDiscListener.onScrolled(this.scrollDistanceX > 0.0f);
        }
        if (this.onPlayerDiscListener != null) {
            OnPlayerDiscListener onPlayerDiscListener2 = this.onPlayerDiscListener;
            if (!z) {
                z2 = true;
            } else {
                z2 = false;
            }
            onPlayerDiscListener2.onDiscDirectionChange(Boolean.valueOf(z2));
        }
        int width = getWidth();
        if (z) {
            i = -1;
        } else {
            i = 1;
        }
        int i3 = width * i;
        this.isScrollingLeft = z;
        this.justSwitchDisc = true;
        int displayedChild = getDisplayedChild();
        if (i3 == 0) {
            i2 = 0;
        }
        this.nextId = displayedChild + i2;
        this.needCallBackOnDiscSwitchComplete++;
        this.mScroller.startScroll(0, 0, i3, 0, 400);
        requestLayout();
    }

    public View getNextView() {
        int i = 1;
        if (getChildAt(1) == getCurrentView()) {
            i = 0;
        }
        return getChildAt(i);
    }

}
