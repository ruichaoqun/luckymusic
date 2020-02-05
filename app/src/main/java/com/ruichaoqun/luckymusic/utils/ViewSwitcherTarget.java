package com.ruichaoqun.luckymusic.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ruichaoqun.luckymusic.R;

public class ViewSwitcherTarget extends CustomViewTarget<ViewSwitcher,Drawable> {

    /**
     * Constructor that defaults {@code waitForLayout} to {@code false}.
     *
     * @param view
     */
    public ViewSwitcherTarget(@NonNull ViewSwitcher view) {
        super(view);
    }

    @Override
    protected void onResourceCleared(@Nullable Drawable placeholder) {

    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        ((ImageView)view.getNextView()).setImageResource(R.drawable.bg_playing);
        view.showNext();
    }

    @Override
    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
        ((ImageView)view.getNextView()).setImageDrawable(resource);
        view.showNext();
    }

}
