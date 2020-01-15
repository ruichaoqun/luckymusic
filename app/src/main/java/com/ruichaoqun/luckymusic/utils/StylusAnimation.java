package com.ruichaoqun.luckymusic.utils;

import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;

public class StylusAnimation extends RotateAnimation {

    private int degrees = Integer.MIN_VALUE;
    private float pivotX;
    private float pivotY;
    private float interpolatedTime;

    public StylusAnimation(float fromDegrees, float toDegrees, float pivotX, float pivotY) {
        super(fromDegrees, toDegrees, pivotX, pivotY);
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    @Override
    public void applyTransformation(float interpolatedTime, Transformation transformation) {
        this.interpolatedTime = interpolatedTime;
        if (this.degrees != Integer.MIN_VALUE) {
            transformation.getMatrix().setRotate((float) this.degrees, this.pivotX, this.pivotY);
        } else {
            super.applyTransformation(interpolatedTime, transformation);
        }
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    public float getInterpolatedTime() {
        return this.interpolatedTime;
    }
}

