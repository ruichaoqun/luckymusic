package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.ViewFlipper;

import androidx.core.view.ViewCompat;

import com.ruichaoqun.luckymusic.utils.CommonUtils;


/**
 * @author Rui Chaoqun
 * @date :2020/1/14 9:51
 * description:
 */
public class SceneRecordFlipper extends ViewFlipper {
    public static final int MAX_ANGLE = 60;
    private static final int switchDiscDuration = 400;
    private static final float SPEED_THRESHOLD = 1.7f;
    private static final int SCROLL_DURATION = 400;


    private Context mContext;
    private long downMotionEventTime = 0;
    private boolean hadNotifyLeft;
    private boolean hadNotifyRight;
    private boolean hadNotifyScroll = false;
    private boolean hadPassedDownToGesture;
    private boolean inTouching = false;
    private boolean isScrollingLeft;
    private boolean justSwitchDisc;
    private int leftOffset = 0;

    private OnGestureListener mOnGesture = new SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            boolean scrollLeft;
            //忽略滑动角度小于60度
            if (e1 == null || e2 == null || CommonUtils.getScrollAngle(e2.getX(), e2.getY(), e1.getX(), e1.getY()) >= 60) {
                return false;
            }
            scrollDistanceX = e1.getX() - e2.getX();
            isScrollingLeft = scrollDistanceX > 0;
            if (!hadNotifyScroll) {
                hadNotifyScroll = true;
                justSwitchDisc = false;
                if (onPageChangeListener != null) {
                    if (scrollDistanceX > 0.0f) {
                        scrollLeft = true;
                    } else {
                        scrollLeft = false;
                    }
                    onPageChangeListener.onScrolled(scrollLeft);
                }
            }
            if (getCurrentView().getLeft() < 0 && !hadNotifyRight) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onScrollDirectionChange(false);
                }
                hadNotifyLeft = false;
                hadNotifyRight = true;
            } else if (getCurrentView().getLeft() > 0 && !hadNotifyLeft) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onScrollDirectionChange(true);
                }
                hadNotifyLeft = true;
                hadNotifyRight = false;
            }
            requestLayout();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            leftOffset = getCurrentView().getLeft();
            scrollDistanceX = 0.0f;
            justSwitchDisc = false;
            return true;
        }
    };

    private GestureDetector mGesture = new GestureDetector(mContext, mOnGesture);

    private Scroller mScroller = new Scroller(mContext, new DecelerateInterpolator());

    private int needCallBackOnDiscSwitchComplete = 0;
    private int nextId = -1;
    public OnPageChangeListener onPageChangeListener;

    private Runnable requestLayoutRunnable = () -> requestLayout();

    public float scrollDistanceX = 0.0f;

    public interface OnPageChangeListener {
        /**
         * 滑动区域改变
         *
         * @param bool false表示下一View展示在右侧，true表示下一View展示在左侧
         */
        void onScrollDirectionChange(Boolean bool);

        /**
         * @param z               是否已切换disc
         * @param justSwitchDisc  是否直接调用切换disc
         * @param isScrollingLeft 是否是向左滑动
         */
        void onPageChange(boolean z, boolean justSwitchDisc, boolean isScrollingLeft);

        void onScrolled(boolean z);
    }

    public SceneRecordFlipper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init();
    }

    private void init() {
        FrameLayout firstFrame = new FrameLayout(mContext);
        firstFrame.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        firstFrame.setId(ViewCompat.generateViewId());
        addView(firstFrame);

        FrameLayout secondFrame = new FrameLayout(mContext);
        secondFrame.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        secondFrame.setId(ViewCompat.generateViewId());
        addView(secondFrame);
    }

    public boolean isInTouching() {
        return inTouching;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            inTouching = true;
            downMotionEventTime = System.currentTimeMillis();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            if (scrollDistanceX != 0.0f || justSwitchDisc) {
                needCallBackOnDiscSwitchComplete++;
            }
            inTouching = false;
            hadNotifyRight = false;
            hadNotifyLeft = false;
            hadNotifyScroll = false;
            float currentTimeMillis = scrollDistanceX / ((float) (System.currentTimeMillis() - downMotionEventTime));
            scrollDistanceX = 0.0f;
            //当前view的left
            int left = getCurrentView().getLeft();
            if (left > getMeasuredWidth() / 2 || currentTimeMillis < -SPEED_THRESHOLD) {
                //向右滑动到下一个view
                nextId = getDisplayedChild() + 1;
                if (hadPassedDownToGesture) {
                    mScroller.startScroll(left, 0, getMeasuredWidth() - getCurrentView().getLeft(), 0, 400);
                }

            } else if (left > 0) {
                //滑动回原View
                if (hadPassedDownToGesture) {
                    mScroller.startScroll(left, 0, -getCurrentView().getLeft(), 0, SCROLL_DURATION);
                }
            } else if (left < -getMeasuredWidth() / 2 || currentTimeMillis > SPEED_THRESHOLD) {
                //向左滑动到下一个view
                nextId = getDisplayedChild() + 1;
                if (hadPassedDownToGesture) {
                    mScroller.startScroll(left, 0, (-getMeasuredWidth()) - left, 0, SCROLL_DURATION);
                }
            } else if (left >= 0) {
                //滑动会原view
                needCallBackOnDiscSwitchComplete++;
            } else if (hadPassedDownToGesture) {
                mScroller.startScroll(left, 0, -left, 0, SCROLL_DURATION);
            }
            requestLayout();
        }
        boolean isFinished = mScroller.isFinished();
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            hadPassedDownToGesture = isFinished;
        }
        if (isFinished) {
            if (!hadPassedDownToGesture) {
                hadPassedDownToGesture = true;
                MotionEvent obtain = MotionEvent.obtain(motionEvent);
                obtain.setAction(0);
                mGesture.onTouchEvent(obtain);
            }
            mGesture.onTouchEvent(motionEvent);
        }
        return true;
    }


    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int currentX;
        int nextX;
        int nextY;
        boolean z2 = false;
        if (mScroller.computeScrollOffset()) {
            currentX = mScroller.getCurrX();
        } else {
            currentX = (int) (leftOffset - scrollDistanceX);
        }
        if (currentX <= -getMeasuredWidth() && isScrollingLeft) {
            currentX = -getMeasuredWidth();
        }
        int currentY = currentX + getMeasuredWidth();

        getCurrentView().layout(currentX, top, currentY, bottom);

        int measuredWidth2 = getMeasuredWidth() + currentY;

        if (!isScrollingLeft) {
            int measuredWidth3 = currentX - getMeasuredWidth();
            if (measuredWidth3 > 0) {
                measuredWidth3 = 0;
            }
            nextY = measuredWidth3;
            nextX = getMeasuredWidth() + measuredWidth3;
        } else {
            nextX = measuredWidth2;
            nextY = getMeasuredWidth();
        }
        if (nextY < getMeasuredWidth() || nextY > 0) {
            View nextView = getNextView();
            nextView.setVisibility(View.VISIBLE);
            nextView.layout(nextY, 0, nextX, getMeasuredHeight());
        }
        if (!mScroller.isFinished()) {
            post(requestLayoutRunnable);
            return;
        }
        if (nextId != -1) {
            setDisplayedChild(nextId);
            nextId = -1;
        } else {
            z2 = true;
        }
        if (needCallBackOnDiscSwitchComplete > 0) {
            if (onPageChangeListener != null) {
                boolean finalZ = z2;
                post(new Runnable() {
                    @Override
                    public void run() {
                        onPageChangeListener.onPageChange(finalZ, justSwitchDisc, isScrollingLeft);
                    }
                });
            }
            needCallBackOnDiscSwitchComplete--;
        }
    }

    public void switchDisc(boolean z) {
        int i;
        boolean z2;
        int i2 = 1;
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
            if (needCallBackOnDiscSwitchComplete > 0) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageChange(false, true, z);
                }
                needCallBackOnDiscSwitchComplete--;
            }
        }
        if (onPageChangeListener != null) {
            onPageChangeListener.onScrolled(scrollDistanceX > 0.0f);
        }
        if (onPageChangeListener != null) {
            OnPageChangeListener onPlayerDiscListener2 = onPageChangeListener;
            if (!z) {
                z2 = true;
            } else {
                z2 = false;
            }
            onPlayerDiscListener2.onScrollDirectionChange(Boolean.valueOf(z2));
        }
        int width = getWidth();
        if (z) {
            i = -1;
        } else {
            i = 1;
        }
        int i3 = width * i;
        isScrollingLeft = z;
        justSwitchDisc = true;
        int displayedChild = getDisplayedChild();
        if (i3 == 0) {
            i2 = 0;
        }
        nextId = displayedChild + i2;
        needCallBackOnDiscSwitchComplete++;
        mScroller.startScroll(0, 0, i3, 0, 400);
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
