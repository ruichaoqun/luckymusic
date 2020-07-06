package com.ruichaoqun.luckymusic.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.utils.UiUtils;


/**
 * Created by Administrator on 2018/1/9.
 */

public class PlayPauseView extends View {
    public static final int PLAY_STATE_PLAYING = 1;
    public static final int PLAY_STATE_PAUSE = 0;

    private Paint paint;
    private int state = PLAY_STATE_PLAYING;//播放状态
    private Path path,path1,path2;//里面三角路径
    private int radius;//半径
    private int color;
    private int pauseColor;
    private int playColor;
    private long max;//最大播放进度
    private long progress = 0;//播放进度

    public PlayPauseView(Context context) {
        this(context,null);
    }

    public PlayPauseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        color = ResourceRouter.getInstance().getThemeColor();
        pauseColor = ResourceRouter.getInstance().isNightTheme()?0x72FFFFFF:0xCC000000;
        playColor = ResourceRouter.getInstance().isNightTheme()?0x33FFFFFF:0X99000000;
        double hudu = 2*Math.PI/360;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int width = UiUtils.dp2px(1.0f);
        paint.setStrokeWidth(width%2!=0?width+1:width);
        paint.setStyle(Paint.Style.STROKE);
        radius = UiUtils.dp2px(25)/2;
        path = new Path();
        path1 = new Path();
        path2 = new Path();
        int x = UiUtils.dp2px(50)/2;
        int y = UiUtils.dp2px(50)/2;
        int side = radius*2/3;
        int x1 = (int) (x - side/(2*Math.tan(hudu*60)));
        int y1 = y - side/2;
        path.moveTo(x1,y1);
        path1.moveTo(x1,y1);
        int x2 = x1;
        int y2 = y1 + side;
        path.lineTo(x2,y2);
        path1.lineTo(x2,y2);
        int x3 = (int) (x1+side*Math.sin(hudu*60));
        int y3 = y1+side/2;
        path.lineTo(x3,y3);
        int x4 = (int) (x1 + side*Math.tan(hudu*30));
        path2.moveTo(x4,y1);
        path2.lineTo(x4,y2);
        path.close();
    }

    public void setColor(int color) {
        this.color = color;
        pauseColor = ResourceRouter.getInstance().isNightTheme()?0x72FFFFFF:0xCC000000;
        playColor = ResourceRouter.getInstance().isNightTheme()?0x33FFFFFF:0X99000000;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(UiUtils.dp2px(50), UiUtils.dp2px(50));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(state == PLAY_STATE_PAUSE){
            paint.setColor(pauseColor);
            canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,radius,paint);
            canvas.drawPath(path,paint);
            paint.setColor(color);
            RectF rectF = new RectF(UiUtils.dp2px( 25)/2,UiUtils.dp2px(25)/2,UiUtils.dp2px(50)-UiUtils.dp2px(25)/2,UiUtils.dp2px(50)-UiUtils.dp2px(25)/2);
            int angle = (int) (100*progress*3.6/max);
            canvas.drawArc(rectF,-90,angle,false,paint);
        }else{
            paint.setColor(playColor);
            canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,radius,paint);
            paint.setColor(color);
            canvas.drawPath(path1,paint);
            canvas.drawPath(path2,paint);
            RectF rectF = new RectF(UiUtils.dp2px(25)/2,UiUtils.dp2px(25)/2,UiUtils.dp2px(50)-UiUtils.dp2px(25)/2,UiUtils.dp2px(50)-UiUtils.dp2px(25)/2);
            int angle = (int) (100*progress*3.6/max);
            canvas.drawArc(rectF,-90,angle,false,paint);
        }
    }

    public void setMax(long max){
        this.max = max;
    }

    public void setProgress(long progress){
        this.progress = progress;
        postInvalidate();
    }

    public void setState(int state){
        this.state = state;
        postInvalidate();
    }
}
