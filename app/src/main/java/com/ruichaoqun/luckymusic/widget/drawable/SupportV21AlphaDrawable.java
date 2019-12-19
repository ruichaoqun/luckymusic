package com.ruichaoqun.luckymusic.widget.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @author Rui Chaoqun
 * @date :2019/10/14 10:58
 * description:
 */
public class SupportV21AlphaDrawable extends Drawable {
    private MyConstantState mMyConstantState;
    /* access modifiers changed from: private */
    public Drawable mOriginDrawalbe;

    /* compiled from: ProGuard */
    class MyConstantState extends ConstantState {
        MyConstantState() {
        }

        @Override
        @NonNull
        public Drawable newDrawable() {
            return new SupportV21AlphaDrawable(SupportV21AlphaDrawable.this.mOriginDrawalbe);
        }

        @Override
        public int getChangingConfigurations() {
            return 0;
        }
    }

    public SupportV21AlphaDrawable(Drawable drawable) {
        this.mOriginDrawalbe = drawable;
    }

    @Override
    public void draw(Canvas canvas) {
        this.mOriginDrawalbe.draw(canvas);
    }

    @Override
    public void setBounds(Rect rect) {
        this.mOriginDrawalbe.setBounds(rect);
    }

    @Override
    public void setAlpha(int i) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.mOriginDrawalbe.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return this.mOriginDrawalbe.getOpacity();
    }

    @Override
    public int getIntrinsicWidth() {
        return this.mOriginDrawalbe.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return this.mOriginDrawalbe.getIntrinsicHeight();
    }

    @Override
    @Nullable
    public ConstantState getConstantState() {
        if (this.mMyConstantState == null) {
            this.mMyConstantState = new MyConstantState();
        }
        return this.mMyConstantState;
    }

}
