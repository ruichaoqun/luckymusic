package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.ui.PlayerActivity;
import com.ruichaoqun.luckymusic.utils.TimeUtils;
import com.ruichaoqun.luckymusic.utils.UiUtils;


/**
 * 歌词展示View
 */
public class LyricView extends View {

    private static boolean sFadingEdgeEnable = true;

    protected Paint pHighLight;
    protected Paint pNormal;
    protected GestureDetector gestureDetector;
    protected GestureDetector.OnGestureListener gestureListener;
    private ScaleGestureDetector mScaleDetector;

    private CommonLyric mLyric;
    private boolean attached;
    private int playbackState;

    public View.OnClickListener onClickListener;


    private Runnable hidePlayCurLyric = new Runnable() {
        public void run() {
            if (LyricView.this.mLyric != null || !LyricView.this.mLyric.isFlingOrScrolling()) {
                LyricView.this.hidePlayCurLyricView(true);
            } else {
                LyricView.this.showPlayLrcHandler.postDelayed(this, 5000);
            }
        }
    };
    public Handler showPlayLrcHandler = new Handler();

    private Runnable scroolToCurLyric = new Runnable() {
        @Override
        public void run() {
            if (LyricView.this.mLyric != null || !LyricView.this.mLyric.isFlingOrScrolling()) {
                    LyricView.this.mLyric.reviseCurrentOffset();
            } else {
                LyricView.this.showPlayLrcHandler.postDelayed(this, 500);
            }
        }
    };
    private boolean scrollFinishedBefore;


    public LyricView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //设置边界阴影
        if (sFadingEdgeEnable) {
            setVerticalFadingEdgeEnabled(true);
            setFadingEdgeLength(UiUtils.dp2px(50.0f));
        }
        initPaint(context);
        mLyric = new CommonLyric(context, this);
    }

    private void initPaint(Context context) {
        this.pNormal = new TextPaint();
        this.pNormal.setAntiAlias(true);
        this.pNormal.setColor(Color.parseColor("#80ffffff"));
        this.pNormal.setTextSize(UiUtils.sp2px( 16.0f));
        this.pNormal.setShadowLayer(0.5f, 0.0f, 1.0f, Color.WHITE);
        this.pHighLight = new Paint();
        this.pHighLight.setAntiAlias(true);
        this.pHighLight.setColor(context.getResources().getColor(R.color.white));
        this.pHighLight.setTextSize(UiUtils.sp2px( 16.0f));
        this.pHighLight.setShadowLayer(0.5f, 0.0f, 1.0f, Color.parseColor("#99000000"));
        this.gestureListener = new GestureListener();
        this.gestureDetector = new GestureDetector(context, this.gestureListener);

    }




    class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (LyricView.this.onClickListener != null && scrollFinishedBefore) {
                LyricView.this.onClickListener.onClick(LyricView.this);
            }
            return super.onSingleTapUp(e);

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //手指向下滑动 -  手指向上滑动+
//            Log.w("sasda","distanceX-->"+distanceX+"    distanceY-->"+distanceY);
            LyricView.this.invalidate();
            LyricView.this.showPlayCurLyricIcon();
            return LyricView.this.mLyric.startScroll((int) distanceX, (int) distanceY, 0);
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            LyricView.this.showPlayCurLyricIcon();
            return LyricView.this.mLyric.fling((int) velocityX, (int) ((double) -velocityY / 1.7d), 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean scrollFinished = false;
        if (this.mLyric != null) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                cancelPrepareToScroolToCurLyric();
                this.showPlayLrcHandler.removeCallbacks(hidePlayCurLyric);
                if(this.mLyric.stopScroll() ){
                    scrollFinished = true;
                }
                this.scrollFinishedBefore = scrollFinished;
            }
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                showPlayLrcHandler.postDelayed(hidePlayCurLyric, 5000);
                prepareToScroolToCurLyric();
            }
            if (!(action == MotionEvent.ACTION_MOVE && event.getPointerCount() == 2)) {
                this.gestureDetector.onTouchEvent(event);
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mLyric != null) {
            mLyric.drawLyric(canvas, canvas.getWidth(), canvas.getHeight());
        }
        super.onDraw(canvas);
    }


    @Override
    public void computeScroll() {
        CommonLyric commonLyric = this.mLyric;
        if (commonLyric == null) {
            super.computeScroll();
        } else if (commonLyric.computeScrollOffset()) {
            invalidate();
        }

    }

    @Override
    public float getTopFadingEdgeStrength() {
        if (sFadingEdgeEnable) {
            return 1.0f;
        }
        return super.getTopFadingEdgeStrength();
    }

    @Override
    public float getBottomFadingEdgeStrength() {
        if (sFadingEdgeEnable) {
            return 1.0f;
        }
        return super.getBottomFadingEdgeStrength();
    }

    public void setPosition(long position, int state) {
        this.playbackState = state;
        CommonLyric commonLyric = this.mLyric;
        if (commonLyric != null) {
            commonLyric.setCurrentPosition(position);
            invalidate();
        }

        synchronized (this) {
            if (this.attached) {
                postInvalidate();
            }
        }

    }

    @Override
    public void onAttachedToWindow() {
        try {
            super.onAttachedToWindow();
            synchronized (this) {
                this.attached = true;
            }
        } catch (Exception unused) {
            this.attached = true;
        }
    }

    @Override
    public void onDetachedFromWindow() {
        synchronized (this) {
            this.attached = false;
        }
        super.onDetachedFromWindow();
//        destory();
    }

    private void hidePlayCurLyricView(boolean hidePlayCurLyricView) {
        if (!(getPlayCurLyricContainer() == null || getPlayCurLyricContainer().getVisibility() == View.GONE)) {
            getPlayCurLyricContainer().setVisibility(View.GONE);
            if (mLyric != null) {
            }
        }
        this.mLyric.setBuffering(false);
        if (hidePlayCurLyricView && mLyric != null && playbackState == PlaybackStateCompat.STATE_PLAYING) {
            this.mLyric.scrollToCurrentPosition();
        }
    }

    public void showPlayCurLyricIcon() {
        if (getPlayCurLyricContainer() != null && getPlayCurLyricContainer().getVisibility() != View.VISIBLE) {
            getPlayCurLyricContainer().setVisibility(View.VISIBLE);
            this.showPlayLrcHandler.removeCallbacks(this.hidePlayCurLyric);
        }
    }


    private View getPlayCurLyricContainer() {
        if (getContext() instanceof PlayerActivity) {
            return ((PlayerActivity) getContext()).getPlayCurLyricContainer();
        }
        return null;
    }

    public void prepareToScroolToCurLyric() {
        if (getPlayCurLyricContainer() != null && getPlayCurLyricContainer().getVisibility() != View.GONE) {
            cancelPrepareToScroolToCurLyric();
            this.showPlayLrcHandler.postDelayed(this.scroolToCurLyric, 500);
        }
    }

    private void cancelPrepareToScroolToCurLyric() {
        this.showPlayLrcHandler.removeCallbacks(this.scroolToCurLyric);
    }

    public long getCurrentTims(){
        if (mLyric != null) {
            return mLyric.getCurrentCenterTims();
        }
        return -1;
    }

    public void onCenterLyricChanged(Long centerTimes) {
        if (getPlayCurLyricContainer() != null) {
            TextView textView = getPlayCurLyricContainer().findViewById(R.id.tv_lyric_container_time);
            textView.setText(TimeUtils.getCurrentPosition(centerTimes));
        }
    }

    @Override
    public void setOnClickListener(View.OnClickListener onClickListener2) {
        this.onClickListener = onClickListener2;
        super.setOnClickListener(onClickListener2);
    }

}
