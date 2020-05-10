package com.ruichaoqun.luckymusic.widget.effect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.common.GlideApp;
import com.ruichaoqun.luckymusic.ui.PlayerActivity;

public class DynamicEffectLayout extends DynamicEffectCommonLayout {

    public AsyncTask<Bitmap, Void, Palette> mAsyncTask;

    public Palette.PaletteAsyncListener mPaletteAsyncListener;

    private GestureDetector mGestureDetector;
    public GestureDetector.SimpleOnGestureListener mGestureListener;
    public OnColorGetListener mOnColorGetListener;

    private Uri mCurrentImageUri;


    public interface OnColorGetListener {
        void onColorGet(int color);
    }

    public DynamicEffectLayout(Context context) {
        this(context,  null);
    }

    public DynamicEffectLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DynamicEffectLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        ImageView artView = new ImageView(context);
        addArtView(artView, ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT);

        final int scaledMinimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        this.mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                if (DynamicEffectLayout.this.mGestureListener != null) {
                    DynamicEffectLayout.this.mGestureListener.onLongPress(motionEvent);
                } else {
                    super.onLongPress(motionEvent);
                }
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (DynamicEffectLayout.this.mGestureListener == null || Math.abs(velocityX) <= ((float) scaledMinimumFlingVelocity) || Math.abs(e2.getX() - e1.getX()) <= Math.abs(e2.getY() - e1.getY())) {
                    return super.onFling(e1, e2, velocityX, velocityY);
                }
                return DynamicEffectLayout.this.mGestureListener.onFling(e1, e2, velocityX, velocityY);
            }

            @Override
            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (DynamicEffectLayout.this.mGestureListener != null) {
                    return DynamicEffectLayout.this.mGestureListener.onDoubleTap(motionEvent);
                }
                return super.onDoubleTap(motionEvent);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                if (DynamicEffectLayout.this.mGestureListener != null) {
                    return DynamicEffectLayout.this.mGestureListener.onSingleTapConfirmed(motionEvent);
                }
                return super.onSingleTapConfirmed(motionEvent);
            }
        });

        mPaletteAsyncListener = new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                DynamicEffectLayout.this.mDominantColor = palette.getDominantColor(Color.WHITE);
                if (DynamicEffectLayout.this.mEffectView != null) {
                    DynamicEffectLayout.this.mEffectView.setColor(DynamicEffectLayout.this.mDominantColor);
                }
                if (DynamicEffectLayout.this.mOnColorGetListener != null) {
                    DynamicEffectLayout.this.mOnColorGetListener.onColorGet(DynamicEffectLayout.this.mDominantColor);
                }
            }
        };
    }

    public void setOnColorGetListener(OnColorGetListener listener) {
        this.mOnColorGetListener = listener;
    }

    public void setArtViewResource(Uri uri) {
        if(mCurrentImageUri != null && TextUtils.equals(mCurrentImageUri.toString(),uri.toString())){
            return;
        }
        this.mCurrentImageUri = uri;
        Drawable drawable = getResources().getDrawable(R.drawable.ic_disc_playhoder);
        RequestBuilder<Drawable> requestBuilder = GlideApp.with(this).load(drawable).circleCrop();
        GlideApp.with(this)
                .load(uri)
                .circleCrop()
                .thumbnail(requestBuilder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        asyncGetColor(null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        asyncGetColor(resource);
                        mArtView.setImageDrawable(resource);
                        return false;
                    }
                })
                .into(mArtView);
        resetArtViewAnimator();
    }



    public void asyncGetColor(Drawable drawable){
        if(drawable == null){
            DynamicEffectLayout.this.mDominantColor = Color.WHITE;
            if (DynamicEffectLayout.this.mEffectView != null) {
                DynamicEffectLayout.this.mEffectView.setColor(DynamicEffectLayout.this.mDominantColor);
            }
            if (DynamicEffectLayout.this.mOnColorGetListener != null) {
                DynamicEffectLayout.this.mOnColorGetListener.onColorGet(DynamicEffectLayout.this.mDominantColor);
            }
            return;
        }
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        if (bitmap != null) {
            if (DynamicEffectLayout.this.mAsyncTask != null) {
                DynamicEffectLayout.this.mAsyncTask.cancel(true);
            }
            if (DynamicEffectLayout.this.mPaletteAsyncListener == null) {
                DynamicEffectLayout.this.mPaletteAsyncListener = new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        if (palette != null) {
                            DynamicEffectLayout.this.mDominantColor = palette.getDominantColor(Color.WHITE);
                            if (DynamicEffectLayout.this.mEffectView != null) {
                                DynamicEffectLayout.this.mEffectView.setColor(DynamicEffectLayout.this.mDominantColor);
                            }
                            if (DynamicEffectLayout.this.mOnColorGetListener != null) {
                                DynamicEffectLayout.this.mOnColorGetListener.onColorGet(DynamicEffectLayout.this.mDominantColor);
                            }
                        }
                    }
                };
            }
            DynamicEffectLayout.this.mAsyncTask = Palette.from(bitmap).clearFilters().generate(mPaletteAsyncListener);
        }
    }

    public void setOnGestureListener(GestureDetector.SimpleOnGestureListener simpleOnGestureListener) {
        this.mGestureListener = simpleOnGestureListener;
    }

//    public void addToHead(int[] iArr) {
//        int[] iArr2 = new int[2];
//        getLocationOnScreen(iArr2);
//        int width = this.mArtView.getWidth();
//        int height = this.mArtView.getHeight();
//        iArr[0] = (int) (((float) iArr2[0]) + (((float) (getWidth() - width)) / 2.0f) + 0.5f);
//        iArr[1] = (int) (((float) iArr2[1]) + (((float) (getHeight() - height)) / 2.0f) + 0.5f);
//        iArr[2] = width;
//        iArr[3] = height;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mGestureDetector.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent);
    }

}
