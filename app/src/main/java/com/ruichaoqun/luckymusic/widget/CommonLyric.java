package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class CommonLyric {
    private Context mContext;
    private Scroller mScroller;
    private LyricView mLyricView;
    protected Paint mPaint;
    private List<String> mLyrics;
    private List<Integer> mLyricsY;
    private List<Long> mTimestamps;
    private List<StaticLayout> mStaticLayouts;
    private int linsSpacing = 20;
    private int offsetY = 0;
    private int currentIndex = 0;
    private boolean isFling;
    private boolean isReseting;
    private boolean isScrolling;
    private boolean isBuffering;
    private boolean isMoving;
    private int minOffsetY;
    private boolean hasInitLyric = false;
    private int currentCenterPosition;
    private int width;
    private int height;


    public CommonLyric( Context context, LyricView lyricView) {
        this.mContext = context;
        this.mLyricView = lyricView;
        mScroller = new Scroller(context,new DecelerateInterpolator(0.604f));
        this.isFling = false;
        this.isScrolling = false;
        this.isReseting = false;
        this.isBuffering = false;
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(context.getResources().getColor(R.color.white));
        this.mPaint.setTextSize(UiUtils.sp2px(16.0f));
        initTest();
    }

    private void initTest() {
        mLyrics = new ArrayList<>(50);
        mLyricsY = new ArrayList<>(50);
        mStaticLayouts = new ArrayList<>(50);
        mTimestamps = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            mLyrics.add("ceshiceshiasceshiceshiasdasdceshiceshiasdasdceshiceshiasdasdceshiceshiasdasdceshiceshiasdasddasd\n测试测试");
            mTimestamps.add(i*5000l);
        }
    }

    public void drawLyric(Canvas canvas, int width, int height) {
        this.width = width;
        this.height = height;
        if(!hasInitLyric){
            for (int i = 0; i < mLyrics.size(); i++) {
                StaticLayout staticLayout = new StaticLayout(mLyrics.get(i),new TextPaint(mPaint),width, Layout.Alignment.ALIGN_CENTER,1.0f,0.0f,false);
                if(i == 0){
                    mLyricsY.add(height/2 - staticLayout.getHeight()/2);
                }else{
                    mLyricsY.add(mLyricsY.get(mLyricsY.size()-1) + linsSpacing + mStaticLayouts.get(mStaticLayouts.size()-1).getHeight());
                }
                mStaticLayouts.add(staticLayout);
                if(i == mLyrics.size() - 1){
                    int anchorLast =  mLyricsY.get(i) + staticLayout.getHeight()/2;
                    minOffsetY = anchorLast - height/2 ;
                }
            }
            hasInitLyric = true;
        }
        drawSentence(canvas);
    }

    public void drawSentence(Canvas canvas){
        for (int i = 0; i < mLyrics.size(); i++) {
            int trueOffsetY = mLyricsY.get(i)- offsetY;
            boolean isInCenter = false;
            if(trueOffsetY > canvas.getHeight()/2 ){
                if(trueOffsetY - canvas.getHeight()/2 <= linsSpacing/2){
                    isInCenter = true;
                }
            }
            if(trueOffsetY < canvas.getHeight()/2){
                if(trueOffsetY + mStaticLayouts.get(i).getHeight() + linsSpacing/2 > canvas.getHeight()/2){
                    isInCenter = true;
                }
            }
            if(isInCenter && currentCenterPosition != i){
                currentCenterPosition = i;
                this.mLyricView.onCenterLyricChanged(mTimestamps.get(currentCenterPosition));
            }
            if(currentIndex == i){
                mStaticLayouts.get(i).getPaint().setColor(Color.WHITE);
            } else if(isInCenter){
                mStaticLayouts.get(i).getPaint().setColor(Color.parseColor("#80ffffff"));
            }else{
                mStaticLayouts.get(i).getPaint().setColor(Color.parseColor("#55ffffff"));
            }
            if(trueOffsetY < canvas.getHeight() && trueOffsetY > -10){
                canvas.save();
                canvas.translate(0.0f,trueOffsetY);
                mStaticLayouts.get(i).draw(canvas);
                canvas.restore();
            }
        }
    }

    public boolean startScroll(int distanceX,int distanceY,int duration){
        if(isReseting){
            return false;
        }
        this.isBuffering = true;
        this.isFling = false;
        this.isScrolling = true;

        this.mScroller.startScroll(0, this.offsetY, distanceX, distanceY, duration);
        return true;
    }

    public boolean fling(int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        if (this.isReseting) {
            return false;
        }
        this.isBuffering = true;
        this.isFling = true;
        this.isScrolling = false;
        Scroller scroller2 = this.mScroller;
        scroller2.fling(0, this.offsetY, velocityX, velocityY, minX, maxX, minY, maxY);
        return true;
    }


    public boolean computeScrollOffset() {
        boolean computeScrollOffset = this.mScroller.computeScrollOffset();
        if(computeScrollOffset){
            offsetY = mScroller.getCurrY();
            if(offsetY < 0){
                offsetY = 0;
            }
            if(offsetY > minOffsetY){
                offsetY = minOffsetY;
            }
        }else if(this.isFling){
            isFling = false;
        }else if(this.isScrolling){
            isScrolling = false;
        }
        return computeScrollOffset;
    }



    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    public boolean stopScroll() {
        boolean isFinished = this.mScroller.isFinished();
        this.mScroller.forceFinished(true);
        return isFinished;
    }


    public void setCurrentPosition(long position) {
        if(!isInCurrentPosition(position)){
            for (int i = 0; i < mTimestamps.size(); i++) {
                if(position < mTimestamps.get(i)){
                    currentIndex = i > 0?i-1:0;
                    if(!isFling && !isScrolling && !isReseting && !isBuffering){
                        this.scrollToCurrentPosition();
                    }
                    break;
                }
            }
        }
    }

    public boolean isInCurrentPosition(long position){
        return mTimestamps.get(currentIndex) <= position
                && ((currentIndex >= mTimestamps.size()-1) || mTimestamps.get(currentIndex + 1) > position);
    }

    public long getCurrentCenterTims(){
        if(currentIndex == currentCenterPosition){
            return -1;
        }
        return mTimestamps.get(currentCenterPosition);
    }

    public void scrollToCurrentPosition() {
        this.isFling = false;
        this.isScrolling = true;
        int distanceY = mStaticLayouts.get(currentIndex).getHeight()/2 +mLyricsY.get(currentIndex) - height/2 - offsetY;
        Log.w("aaaaaaa","scrollToCurrentPosition-->"+distanceY);
        this.mScroller.startScroll(0, this.offsetY, 0, distanceY, 300);
    }

    public boolean isFlingOrScrolling() {
        return isFling || isScrolling;
    }

    /**
     * 当滑动结束时调用改方法，对当前滑动到中间的歌词进行修正，使正对中心
     */
    public void reviseCurrentOffset(){
        this.isFling = false;
        this.isScrolling = true;
        int disY = mStaticLayouts.get(currentCenterPosition).getHeight()/2 +mLyricsY.get(currentCenterPosition) - height/2 - offsetY;
        this.mScroller.startScroll(0, this.offsetY, 0, disY, 300);
    }

    public void setBuffering(boolean isBuffering) {
        this.isBuffering = isBuffering;
    }
}
