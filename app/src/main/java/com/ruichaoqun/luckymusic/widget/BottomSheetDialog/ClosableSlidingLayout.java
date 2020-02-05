package com.ruichaoqun.luckymusic.widget.BottomSheetDialog;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import java.util.HashSet;
import java.util.Set;

public class ClosableSlidingLayout extends FrameLayout {
    private static final int INVALID_POINTER = -1;
    private static final String TAG = "ClosableSlidingLayout";
    /* access modifiers changed from: private */
    public final float MINVEL;
    private boolean collapsible;
    /* access modifiers changed from: private */
    public int height;
    private Set<View> ignoreViews;
    private int mActivePointerId;
    /* access modifiers changed from: private */
    public ViewDragHelper mDragHelper;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    /* access modifiers changed from: private */
    public SlideListener mListener;
    public View mTarget;
    private Set<View> requestDisallowInterceptTouchEventViews;
    boolean swipeable;
    /* access modifiers changed from: private */
    public int top;
    private float xDiff;
    private float yDiff;

    /* compiled from: ProGuard */
    public interface SlideListener {
        void onClosed();

        void onOpened();
    }

    public void addIgnoreScrollView(View view) {
        this.ignoreViews.add(view);
    }

    public void addRequestDisallowInterceptTouchEventViews(View view) {
        this.requestDisallowInterceptTouchEventViews.add(view);
    }

    public ClosableSlidingLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClosableSlidingLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @TargetApi(11)
    public ClosableSlidingLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.ignoreViews = new HashSet();
        this.requestDisallowInterceptTouchEventViews = new HashSet();
        this.collapsible = false;
        this.swipeable = true;
        this.mDragHelper = ViewDragHelper.create(this, 0.8f, new ViewDragCallback());
        this.MINVEL = getResources().getDisplayMetrics().density * 400.0f;
    }

    @SuppressLint("Range")
    private boolean isInIgnoreViews(@NonNull MotionEvent motionEvent, Set<View> set) {
        for (View next : set) {
            int[] iArr = new int[4];
            next.getLocationOnScreen(iArr);
            iArr[2] = next.getWidth() + iArr[0];
            iArr[3] = next.getHeight() + iArr[1];
            if (motionEvent.getRawY() > ((float) iArr[1]) && motionEvent.getRawY() < ((float) iArr[3]) && motionEvent.getRawX() > ((float) iArr[0]) && motionEvent.getRawX() < ((float) iArr[2])) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint({"TryCatchExceptionError"})
    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent motionEvent) {
        //重写事件拦截
        int actionMasked = motionEvent.getActionMasked();
        if (isInIgnoreViews(motionEvent, this.requestDisallowInterceptTouchEventViews)) {
            return false;
        }
        if (actionMasked == MotionEvent.ACTION_MOVE && !isInIgnoreViews(motionEvent, this.ignoreViews) && (!isEnabled() || canChildScrollUp())) {
            return false;
        }
        if (actionMasked == MotionEvent.ACTION_CANCEL || actionMasked == MotionEvent.ACTION_UP) {
            this.mActivePointerId = -1;
            this.mIsBeingDragged = false;
            if (this.collapsible && (-this.yDiff) > ((float) this.mDragHelper.getTouchSlop())) {
                expand(this.mDragHelper.getCapturedView(), 0.0f);
            }
            this.mDragHelper.cancel();
            return false;
        }
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                this.height = getChildAt(0).getHeight();
                this.top = getChildAt(0).getTop();
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                this.mIsBeingDragged = false;
                float motionEventX = getMotionEventX(motionEvent, this.mActivePointerId);
                float motionEventY = getMotionEventY(motionEvent, this.mActivePointerId);
                if (motionEventX != -1.0f && motionEventY != -1.0f) {
                    this.mInitialMotionX = motionEventX;
                    this.mInitialMotionY = motionEventY;
                    this.xDiff = 0.0f;
                    this.yDiff = 0.0f;
                    break;
                } else {
                    return false;
                }
            case MotionEvent.ACTION_MOVE:
                if (this.mActivePointerId == -1) {
                    return false;
                }
                float motionEventX2 = getMotionEventX(motionEvent, this.mActivePointerId);
                if (motionEventX2 == -1.0f) {
                    return false;
                }
                this.xDiff = motionEventX2 - this.mInitialMotionX;
                float motionEventY2 = getMotionEventY(motionEvent, this.mActivePointerId);
                if (motionEventY2 != -1.0f) {
                    this.yDiff = motionEventY2 - this.mInitialMotionY;
                    if (this.swipeable && Math.abs(this.xDiff) < Math.abs(this.yDiff) && this.yDiff > ((float) this.mDragHelper.getTouchSlop()) && !this.mIsBeingDragged) {
                        this.mIsBeingDragged = true;
                        this.mDragHelper.captureChildView(getChildAt(0), 0);
                        break;
                    }
                } else {
                    return false;
                }
        }
        try {
            this.mDragHelper.shouldInterceptTouchEvent(motionEvent);
        } catch (Exception e2) {
        }
        return this.mIsBeingDragged;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean z) {
    }

    private boolean canChildScrollUp() {
        boolean z;
        boolean z2 = true;
        if (this.mTarget == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTarget, -1);
        }
        if (this.mTarget instanceof AbsListView) {
            AbsListView absListView = (AbsListView) this.mTarget;
            if (absListView.getChildCount() <= 0 || (absListView.getFirstVisiblePosition() <= 0 && absListView.getChildAt(0).getTop() >= absListView.getPaddingTop())) {
                z = false;
            } else {
                z = true;
            }
            return z;
        }
        if (this.mTarget.getScrollY() <= 0) {
            z2 = false;
        }
        return z2;
    }

    private float getMotionEventY(MotionEvent motionEvent, int i) {
        int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, i);
        if (findPointerIndex < 0) {
            return -1.0f;
        }
        return MotionEventCompat.getY(motionEvent, findPointerIndex);
    }

    private float getMotionEventX(MotionEvent motionEvent, int i) {
        int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, i);
        if (findPointerIndex < 0) {
            return -1.0f;
        }
        return MotionEventCompat.getX(motionEvent, findPointerIndex);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if ((!isEnabled() || canChildScrollUp()) && actionMasked == MotionEvent.ACTION_MOVE && !isInIgnoreViews(motionEvent, this.ignoreViews)) {
            return false;
        }
        try {
            if (this.swipeable) {
                this.mDragHelper.processTouchEvent(motionEvent);
            }
        } catch (Exception e2) {
        }
        return true;
    }

    public void computeScroll() {
        if (this.mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setSlideListener(SlideListener slideListener) {
        this.mListener = slideListener;
    }

    /* access modifiers changed from: package-private */
    public void setCollapsible(boolean z) {
        this.collapsible = z;
    }

    /* compiled from: ProGuard */
    private class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            //对所有的子View都进行处理
            return true;
        }

        /**
         * 当释放拖拽时调用
         * @param view
         * @param xvel x轴速率
         * @param yvel y轴速率
         */
        @Override
        public void onViewReleased(View view, float xvel, float yvel) {
            //如果y轴速率大于指定值，直接隐藏当前layout
            if (yvel > ClosableSlidingLayout.this.MINVEL) {
                ClosableSlidingLayout.this.dismiss(view, yvel);
            } else if (view.getTop() >= ClosableSlidingLayout.this.top + (ClosableSlidingLayout.this.height / 2)) {
                //如果当前拖拽高度大于一半，直接隐藏当前layout
                ClosableSlidingLayout.this.dismiss(view, yvel);
            } else {
                //返回到最初位置
                ClosableSlidingLayout.this.mDragHelper.smoothSlideViewTo(view, 0, ClosableSlidingLayout.this.top);
            }
            ViewCompat.postInvalidateOnAnimation(ClosableSlidingLayout.this);
        }

        @Override
        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            if (Build.VERSION.SDK_INT < 11) {
                ClosableSlidingLayout.this.invalidate();
            }
            if (ClosableSlidingLayout.this.height - i2 < 1 && ClosableSlidingLayout.this.mListener != null) {
                ClosableSlidingLayout.this.mListener.onClosed();
            }
        }

        @Override
        public int clampViewPositionVertical(View view, int i, int i2) {
            return Math.max(i, ClosableSlidingLayout.this.top);
        }
    }

    private void expand(View view, float f2) {
        if (this.mListener != null) {
            this.mListener.onOpened();
        }
    }

    /* access modifiers changed from: private */
    public void dismiss(View view, float f2) {
        this.mDragHelper.smoothSlideViewTo(view, 0, (int) (((double) this.top) + (((double) this.height) * 1.2d)));
        this.mDragHelper.cancel();
        ViewCompat.postInvalidateOnAnimation(this);
    }
}