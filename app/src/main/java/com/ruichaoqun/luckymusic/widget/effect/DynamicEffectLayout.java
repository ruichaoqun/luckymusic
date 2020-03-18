package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.palette.graphics.Palette;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.common.GlideApp;

public class DynamicEffectLayout extends DynamicEffectCommonLayout {

    public AsyncTask<Bitmap, Void, Palette> mAsyncTask;

    public Palette.PaletteAsyncListener mPaletteAsyncListener;

    private GestureDetector mGestureDetector;
    public GestureDetector.SimpleOnGestureListener mGestureListener;
    public OnColorGetListener mOnColorGetListener;


    public interface OnColorGetListener {
        void onColorGet(int color);
    }

    public DynamicEffectLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public DynamicEffectLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DynamicEffectLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        ImageView artView = new ImageView(context);
        Drawable drawable = context.getDrawable(R.drawable.ic_disc_playhoder);
        GlideApp.with(this)
                .load(drawable)
                .transform(new CircleCrop())
                .centerCrop()
                .into(artView);
        a(artView, ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT);

        final int scaledMinimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        this.mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            public boolean onDown(MotionEvent motionEvent) {
                return true;
            }

            public void onLongPress(MotionEvent motionEvent) {
                if (DynamicEffectLayout.this.mGestureListener != null) {
                    DynamicEffectLayout.this.mGestureListener.onLongPress(motionEvent);
                } else {
                    super.onLongPress(motionEvent);
                }
            }

            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (DynamicEffectLayout.this.mGestureListener == null || Math.abs(velocityX) <= ((float) scaledMinimumFlingVelocity) || Math.abs(e2.getX() - e1.getX()) <= Math.abs(e2.getY() - e1.getY())) {
                    return super.onFling(e1, e2, velocityX, velocityY);
                }
                return DynamicEffectLayout.this.mGestureListener.onFling(e1, e2, velocityX, velocityY);
            }

            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (DynamicEffectLayout.this.mGestureListener != null) {
                    return DynamicEffectLayout.this.mGestureListener.onDoubleTap(motionEvent);
                }
                return super.onDoubleTap(motionEvent);
            }

            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                if (DynamicEffectLayout.this.mGestureListener != null) {
                    return DynamicEffectLayout.this.mGestureListener.onSingleTapConfirmed(motionEvent);
                }
                return super.onSingleTapConfirmed(motionEvent);
            }
        });
    }

    public void setOnColorGetListener(OnColorGetListener listener) {
        this.mOnColorGetListener = listener;
    }

    public void a(String str, String str2) {
        cf.a((DraweeView) this.f11717d, str, str2, (NovaControllerListener) new NovaControllerListener() {
            public void onFinalBitmapSet(@h Bitmap bitmap, PlatformBitmapFactory platformBitmapFactory, ExecutorSupplier executorSupplier) {
                if (bitmap != null) {
                    if (DynamicEffectLayout.this.mAsyncTask != null) {
                        DynamicEffectLayout.this.mAsyncTask.cancel(true);
                    }
                    if (DynamicEffectLayout.this.mPaletteAsyncListener == null) {
                        Palette.PaletteAsyncListener unused = DynamicEffectLayout.this.mPaletteAsyncListener = new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {
                                if (palette != null) {
                                    DynamicEffectLayout.this.f11719f = palette.getDominantColor(-1);
                                    if (DynamicEffectLayout.this.f11718e != null) {
                                        DynamicEffectLayout.this.f11718e.setColor(DynamicEffectLayout.this.f11719f);
                                    }
                                    if (DynamicEffectLayout.this.mOnColorGetListener != null) {
                                        DynamicEffectLayout.this.mOnColorGetListener.onColorGet(DynamicEffectLayout.this.f11719f);
                                    }
                                }
                            }
                        };
                    }
                    AsyncTask unused2 = DynamicEffectLayout.this.mAsyncTask = Palette.from(bitmap).clearFilters().generate(a.this.mPaletteAsyncListener);
                }
            }
        });
    }

    public void setOnGestureListener(GestureDetector.SimpleOnGestureListener simpleOnGestureListener) {
        this.mGestureListener = simpleOnGestureListener;
    }

    public void a(int[] iArr) {
        int[] iArr2 = new int[2];
        getLocationOnScreen(iArr2);
        int width = this.f11717d.getWidth();
        int height = this.f11717d.getHeight();
        iArr[0] = (int) (((float) iArr2[0]) + (((float) (getWidth() - width)) / 2.0f) + 0.5f);
        iArr[1] = (int) (((float) iArr2[1]) + (((float) (getHeight() - height)) / 2.0f) + 0.5f);
        iArr[2] = width;
        iArr[3] = height;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mGestureDetector.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent);
    }

}
