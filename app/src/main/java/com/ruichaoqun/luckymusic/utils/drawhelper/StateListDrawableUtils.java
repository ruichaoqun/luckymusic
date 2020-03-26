package com.ruichaoqun.luckymusic.utils.drawhelper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.core.graphics.ColorUtils;


import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.theme.drawable.ThemeStateDrawable;
import com.ruichaoqun.luckymusic.utils.CommonUtils;
import com.ruichaoqun.luckymusic.utils.UiUtils;
import com.ruichaoqun.luckymusic.widget.drawable.SupportV21AlphaDrawable;

import java.lang.reflect.Array;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 17:55
 * description:
 */
public class StateListDrawableUtils {
    public static ColorStateList createColorStateList(Context context, @ColorInt int color, int alpha) {
        int alphaComponent = ColorUtils.setAlphaComponent(color, (int) (255.0f / (100.0f / ((float) alpha))));
        return createColorStateList(context, Integer.valueOf(color), Integer.valueOf(alphaComponent), Integer.valueOf(alphaComponent));
    }

    public static ColorStateList createColorStateList2(Context context, @ColorRes int color, int alpha) {
        return createColorStateList(context, context.getResources().getColor(color), alpha);
    }

    public static ColorStateList createColorStateList(Context context, Integer color, Integer alphaComponent, Integer num3) {
        int i;
        int i2;
        int i3;
        if (color != null) {
            i = 1;
        } else {
            i = 0;
        }
        if (alphaComponent != null) {
            i++;
        }
        if (num3 != null) {
            i2 = i + 1;
        } else {
            i2 = i;
        }
        int[][] iArr = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i2, 1});
        int[] iArr2 = new int[i2];
        if (alphaComponent != null) {
            iArr[0] = new int[]{android.R.attr.state_pressed};
            iArr2[0] = alphaComponent.intValue();
            i3 = 1;
        } else {
            i3 = 0;
        }
        if (color != null) {
            iArr[i3] = new int[]{android.R.attr.state_enabled};
            iArr2[i3] = color.intValue();
            i3++;
        }
        if (num3 != null) {
            iArr[i3] = new int[]{-android.R.attr.state_enabled};
            iArr2[i3] = num3.intValue();
        }
        return new ColorStateList(iArr, iArr2);
    }

    public static ColorStateList a(Integer num, Integer num2, Integer num3, Integer num4) {
        int i;
        int i2;
        int i3;
        if (num != null) {
            i = 1;
        } else {
            i = 0;
        }
        if (num2 != null) {
            i++;
        }
        if (num3 != null) {
            i++;
        }
        if (num4 != null) {
            i2 = i + 1;
        } else {
            i2 = i;
        }
        int[][] iArr = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i2, 1});
        int[] iArr2 = new int[i2];
        if (num4 != null) {
            iArr[0] = new int[]{android.R.attr.state_selected};
            iArr2[0] = num4.intValue();
            i3 = 1;
        } else {
            i3 = 0;
        }
        if (num2 != null) {
            iArr[i3] = new int[]{android.R.attr.state_pressed};
            iArr2[i3] = num2.intValue();
            i3++;
        }
        if (num != null) {
            iArr[i3] = new int[]{android.R.attr.state_enabled};
            iArr2[i3] = num.intValue();
            i3++;
        }
        if (num3 != null) {
            iArr[i3] = new int[]{android.R.attr.state_selected};
            iArr2[i3] = num3.intValue();
        }
        return new ColorStateList(iArr, iArr2);
    }

    public static StateListDrawable getTotalStateDrawable(Context context, int normalRes, int pressRes, int focusRes, int notEnableRes) {
        return getTotalStateDrawable(context, normalRes, pressRes, focusRes, notEnableRes, -1);
    }

    public static StateListDrawable getTotalStateDrawable(Context context, int normalRes, int pressRes, int focusRes, int notEnableRes, int selectRes) {
        return getTotalStateDrawable(context, normalRes, pressRes, focusRes, notEnableRes, selectRes, false);
    }

    public static StateListDrawable getTotalStateDrawable(Context context, int normalRes, int pressRes, int focusRes, int notEnableRes, int selectRes, boolean z) {
        Drawable pressDrawable;
        Drawable focusDrawable;
        Drawable notEnableDrawable;
        Drawable selectDrawable = null;
        LuckyMusicApp instance = LuckyMusicApp.sInstance;
        Drawable normalDrawable = normalRes <= 0 ? null : instance.getResources().getDrawable(normalRes);
        if (pressRes <= 0) {
            pressDrawable = null;
        } else {
            pressDrawable = instance.getResources().getDrawable(pressRes);
        }
        if (focusRes <= 0) {
            focusDrawable = null;
        } else {
            focusDrawable = instance.getResources().getDrawable(focusRes);
        }
        if (notEnableRes <= 0) {
            notEnableDrawable = null;
        } else {
            notEnableDrawable = instance.getResources().getDrawable(notEnableRes);
        }
        if (selectRes > 0) {
            selectDrawable = instance.getResources().getDrawable(selectRes);
        }
        return getTotalStateDrawable((Context) instance, normalDrawable, pressDrawable, focusDrawable, notEnableDrawable, selectDrawable, z);
    }

    public static StateListDrawable getTotalStateDrawable(Context context, Drawable normalDrawable, Drawable pressDrawable, Drawable focusDrawable, Drawable notEnableDrawable, Drawable selectDrawable) {
        return getTotalStateDrawable(context, normalDrawable, pressDrawable, focusDrawable, notEnableDrawable, selectDrawable, false);
    }

    public static StateListDrawable getTotalStateDrawable(Context context, Drawable normalDrawable, Drawable pressDrawable, Drawable focusDrawable, Drawable notEnableDrawable, Drawable selectDrawable, boolean z) {
        StateListDrawable stateListDrawable = z ? new ThemeStateDrawable() : new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, focusDrawable);
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, notEnableDrawable);
        stateListDrawable.addState(new int[0], normalDrawable);
        return stateListDrawable;
    }

    public static StateListDrawable getCheckedDrawable(Context context, int enableDrawable, @DrawableRes int checkedDrawable) {
        Drawable drawable = null;

        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable drawable2 = enableDrawable == -1 ? null : context.getResources().getDrawable(enableDrawable);
        if (checkedDrawable != -1) {
            drawable = context.getResources().getDrawable(checkedDrawable);
        }
        stateListDrawable.addState(new int[]{android.R.attr.state_checked, android.R.attr.state_enabled}, drawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, drawable2);
        stateListDrawable.addState(new int[0], drawable2);
        return stateListDrawable;
    }

    public static StateListDrawable getPressedDrawable(Context context, Drawable normalDrawable, Drawable pressDrawable, Drawable focuseDrawable, Drawable notEnableDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focuseDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, focuseDrawable);
        //“-”号代表改属性为false，此时即表示state_enabled为false
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, notEnableDrawable);
        stateListDrawable.addState(new int[0], normalDrawable);
        return stateListDrawable;
    }

    public static StateListDrawable getPressdrawableWithAlpha(int drawableRes, int pressAlpha, int notEnableAlpha) {
        return getPressdrawableWithAlpha((BitmapDrawable) LuckyMusicApp.sInstance.getResources().getDrawable(drawableRes), pressAlpha, notEnableAlpha);
    }

    public static StateListDrawable getStateListDrawable(Drawable drawable, Drawable pressDrawable, Drawable selectDrawable, Drawable pressAndSelectDrawable, Drawable enableDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        if (pressAndSelectDrawable != null) {
            stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_selected, android.R.attr.state_pressed}, pressAndSelectDrawable);
        }
        if (selectDrawable != null) {
            stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_selected}, selectDrawable);
        }
        if (pressDrawable != null) {
            stateListDrawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed}, pressDrawable);
        }
        if (enableDrawable != null) {
            stateListDrawable.addState(new int[]{-android.R.attr.state_enabled,}, enableDrawable);
        }
        stateListDrawable.addState(new int[0], drawable);
        return stateListDrawable;
    }


    public static StateListDrawable getPressdrawableWithAlpha(Drawable drawable, int pressAlpha, int notEnableAlpha) {
        Drawable drawable2;
        Drawable drawable3;
        Drawable drawable4;
        if (pressAlpha != -1) {
            drawable2 = drawable.getConstantState().newDrawable().mutate();
            drawable2.setAlpha(pressAlpha);
        } else {
            drawable2 = null;
        }
        if (notEnableAlpha != -1) {
            drawable3 = drawable.getConstantState().newDrawable().mutate();
            drawable3.setAlpha(notEnableAlpha);
        } else {
            drawable3 = null;
        }
        return getPressedDrawable((Context) LuckyMusicApp.sInstance, drawable, drawable2, (Drawable) null, drawable3);
    }

    public static StateListDrawable getPressdrawableWithAlpha(BitmapDrawable bitmapDrawable, int pressAlpha, int notEnableAlpha) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        if (pressAlpha != -1) {
            drawable = new BitmapDrawable(LuckyMusicApp.sInstance.getResources(), bitmapDrawable.getBitmap());
            drawable.setAlpha(pressAlpha);
        } else {
            drawable = null;
        }
        if (notEnableAlpha != -1) {
            drawable2 = new BitmapDrawable(LuckyMusicApp.sInstance.getResources(), bitmapDrawable.getBitmap());
            drawable2.setAlpha(notEnableAlpha);
        } else {
            drawable2 = null;
        }
        if (!CommonUtils.versionAbove21()) {
            if (drawable != null) {
                drawable3 = new SupportV21AlphaDrawable(drawable);
            } else {
                drawable3 = drawable;
            }
            if (drawable2 != null) {
                drawable2 = new SupportV21AlphaDrawable(drawable2);
                drawable = drawable3;
            } else {
                drawable = drawable3;
            }
        }
        return getPressedDrawable((Context) LuckyMusicApp.sInstance, (Drawable) bitmapDrawable, drawable, (Drawable) null, drawable2);
    }

    public static StateListDrawable getPressdrawableWithAlpha(int drawableRes, int alpha, int i3, int right, int bottom) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        Drawable drawable4;
        LuckyMusicApp instance = LuckyMusicApp.sInstance;
        Bitmap decodeResource = BitmapFactory.decodeResource(instance.getResources(), drawableRes);
        byte[] ninePatchChunk = decodeResource.getNinePatchChunk();
        Drawable drawable5 = instance.getResources().getDrawable(drawableRes);
        if (alpha != -1) {
            NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(instance.getResources(), decodeResource, ninePatchChunk, new Rect(), null);
            ninePatchDrawable.setBounds(0, 0, right, bottom);
            ninePatchDrawable.setAlpha(alpha);
            drawable = ninePatchDrawable;
        } else {
            drawable = null;
        }
        if (i3 != -1) {
            drawable2 = new NinePatchDrawable(instance.getResources(), decodeResource, ninePatchChunk, new Rect(), null);
            drawable2.setBounds(0, 0, right, bottom);
            drawable2.setAlpha(alpha);
        } else {
            drawable2 = null;
        }
        if (!CommonUtils.versionAbove21()) {
            if (drawable != null) {
                drawable4 = new SupportV21AlphaDrawable(drawable);
            } else {
                drawable4 = drawable;
            }
            if (drawable2 != null) {
                drawable2 = new SupportV21AlphaDrawable(drawable2);
                drawable3 = drawable4;
            } else {
                drawable3 = drawable4;
            }
        } else {
            drawable3 = drawable;
        }
        return getPressedDrawable((Context) LuckyMusicApp.sInstance, drawable5, drawable3, (Drawable) null, drawable2);
    }

    public static StateListDrawable getPressedDrawable(int i, int i2) {
        return getPressedDrawable((BitmapDrawable) LuckyMusicApp.sInstance.getResources().getDrawable(i), (BitmapDrawable) LuckyMusicApp.sInstance.getResources().getDrawable(i2), 76);
    }

    public static StateListDrawable getPressedDrawable(BitmapDrawable bitmapDrawable, BitmapDrawable bitmapDrawable2, int i) {
        Drawable drawable;
        if (i != -1) {
            drawable = new BitmapDrawable(LuckyMusicApp.sInstance.getResources(), bitmapDrawable.getBitmap());
            drawable.setAlpha(i);
        } else {
            drawable = null;
        }
        if (!CommonUtils.versionAbove21() && drawable != null) {
            drawable = new SupportV21AlphaDrawable(drawable);
        }
        return getPressedDrawable((Context) LuckyMusicApp.sInstance, (Drawable) bitmapDrawable, (Drawable) bitmapDrawable2, (Drawable) null, drawable);
    }

    public static StateListDrawable b(Context context, @DimenRes int dimen, int i2, int i3, int i4) {
        return getTotalStateDrawable(context, UiUtils.getDimensionPixelSize(dimen), i2, context.getResources().getColor(i3), i2, context.getResources().getColor(i4), (Integer) null);
    }

    public static StateListDrawable b(Context context, int i, int i2, int i3, int i4, int i5) {
        return getTotalStateDrawable(context, i, i2, i3, i4, i5, (Integer) null);
    }

    public static StateListDrawable getTotalStateDrawable(Context context, int i, int i2, int i3, int i4, int i5, Integer num) {
        return getTotalStateDrawable(context, (Drawable) b(i, i2, i3), (Drawable) b(i, i4, i5), (Drawable) null, (Drawable) null,  num != null ? b(i, i2, num.intValue()) : null);
    }

    private static ShapeDrawable b(int i, int i2, int i3) {
        ShapeDrawable shapeDrawable;
        float f2 = ((float) i) / 2.0f;
        float[] fArr = {f2, f2, f2, f2, f2, f2, f2, f2};
        if (i2 != 0) {
            float f3 = ((float) (i - (i2 * 2))) / 2.0f;
            shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, new RectF((float) i2, (float) i2, (float) i2, (float) i2), new float[]{f3, f3, f3, f3, f3, f3, f3, f3}));
        } else {
            shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, null, null));
        }
        shapeDrawable.getPaint().setColor(i3);
        return shapeDrawable;
    }

//    public static int onColorGet(Context context) {
//        int i = 0;
//        int identifier = context.getResources().getIdentifier(onColorGet.auu.onColorGet.QueueData("PREVERQAOiwEBjoJFgwpDQA="), onColorGet.auu.onColorGet.QueueData("KgwZAA8="), onColorGet.auu.onColorGet.QueueData("LwsQFw4aAQ=="));
//        if (identifier > 0) {
//            i = context.getResources().getDimensionPixelSize(identifier);
//        }
//        if (i == 0) {
//            return UiUtils.dp2px(25.0f);
//        }
//        return i;
//    }
}
