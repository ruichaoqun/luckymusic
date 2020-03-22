package com.ruichaoqun.luckymusic.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.ruichaoqun.luckymusic.LuckyMusicApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

public class RenderScriptTransformation extends BitmapTransformation {
    // The version of this transformation, incremented to correct an error in onColorGet previous version.
    // See #455.
    private static final int VERSION = 1;
    private static final String ID = "com.bumptech.glide.load.resource.bitmap.CircleCrop." + VERSION;
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    // Bitmap doesn't implement equals, so == and .equals are equivalent here.
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Override
    protected Bitmap transform(
            @NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return createBlurredImageFromBitmap(LuckyMusicApp.getInstance(), toTransform, 10);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RenderScriptTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    public static Bitmap createBlurredImageFromBitmap(Context context, Bitmap bitmap, int inSampleSize) {

        RenderScript rs = RenderScript.create(context);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, stream);
        byte[] imageInByte = stream.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
        Bitmap blurTemplate = BitmapFactory.decodeStream(bis, null, options);

        final Allocation input = Allocation.createFromBitmap(rs, blurTemplate);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(25f);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(blurTemplate);
        Paint paint = new Paint();
        Bitmap bitmap1 =  Bitmap.createBitmap(blurTemplate.getWidth(),blurTemplate.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                0.5F, 0, 0, 0, 0,
                0, 0.5F, 0, 0, 0,
                0, 0, 0.5F, 0, 0,
                0, 0, 0, 1, 0,
        });
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(blurTemplate,0,0,paint);
        blurTemplate.recycle();
        return bitmap1;
    }
}
