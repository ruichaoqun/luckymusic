package com.ruichaoqun.luckymusic.util.drawhelper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.graphics.ColorUtils;

import com.ruichaoqun.luckymusic.App;
import com.ruichaoqun.luckymusic.util.CommonUtils;

import java.lang.reflect.Array;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 17:55
 * description:
 */
public class DrawableUtils {
    public static ColorStateList a(Context context, @ColorInt int i, int i2) {
        int alphaComponent = ColorUtils.setAlphaComponent(i, (int) (255.0f / (100.0f / ((float) i2))));
        return a(context, Integer.valueOf(i), Integer.valueOf(alphaComponent), Integer.valueOf(alphaComponent));
    }

    public static ColorStateList b(Context context, @ColorRes int i, int i2) {
        return a(context, context.getResources().getColor(i), i2);
    }

    public static ColorStateList a(Context context, Integer num, Integer num2, Integer num3) {
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
            i2 = i + 1;
        } else {
            i2 = i;
        }
        int[][] iArr = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i2, 1});
        int[] iArr2 = new int[i2];
        if (num2 != null) {
            iArr[0] = new int[]{16842919};
            iArr2[0] = num2.intValue();
            i3 = 1;
        } else {
            i3 = 0;
        }
        if (num != null) {
            iArr[i3] = new int[]{16842910};
            iArr2[i3] = num.intValue();
            i3++;
        }
        if (num3 != null) {
            iArr[i3] = new int[]{-16842910};
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
            iArr[0] = new int[]{16842913};
            iArr2[0] = num4.intValue();
            i3 = 1;
        } else {
            i3 = 0;
        }
        if (num2 != null) {
            iArr[i3] = new int[]{16842919};
            iArr2[i3] = num2.intValue();
            i3++;
        }
        if (num != null) {
            iArr[i3] = new int[]{16842910};
            iArr2[i3] = num.intValue();
            i3++;
        }
        if (num3 != null) {
            iArr[i3] = new int[]{-16842910};
            iArr2[i3] = num3.intValue();
        }
        return new ColorStateList(iArr, iArr2);
    }

    public static StateListDrawable a(Context context, int i, int i2, int i3, int i4) {
        return a(context, i, i2, i3, i4, -1);
    }

    public static StateListDrawable a(Context context, int i, int i2, int i3, int i4, int i5) {
        return a(context, i, i2, i3, i4, i5, false);
    }

    public static StateListDrawable a(Context context, int i, int i2, int i3, int i4, int i5, boolean z) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        Drawable drawable4 = null;
        App instance = App.sInstance;
        Drawable drawable5 = i <= 0 ? null : instance.getResources().getDrawable(i);
        if (i2 <= 0) {
            drawable = null;
        } else {
            drawable = instance.getResources().getDrawable(i2);
        }
        if (i3 <= 0) {
            drawable2 = null;
        } else {
            drawable2 = instance.getResources().getDrawable(i3);
        }
        if (i4 <= 0) {
            drawable3 = null;
        } else {
            drawable3 = instance.getResources().getDrawable(i4);
        }
        if (i5 > 0) {
            drawable4 = instance.getResources().getDrawable(i5);
        }
        return a((Context) instance, drawable5, drawable, drawable2, drawable3, drawable4, z);
    }

    public static StateListDrawable a(Context context, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        return a(context, drawable, drawable2, drawable3, drawable4, drawable5, false);
    }

    public static StateListDrawable a(Context context, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5, boolean z) {
        StateListDrawable stateListDrawable = z ? new a() : new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919, 16842910}, drawable2);
        stateListDrawable.addState(new int[]{16842913}, drawable5);
        stateListDrawable.addState(new int[]{16842908}, drawable3);
        stateListDrawable.addState(new int[]{-16842910}, drawable4);
        stateListDrawable.addState(new int[0], drawable);
        return stateListDrawable;
    }

    public static StateListDrawable c(Context context, int i, int i2) {
        Drawable drawable = null;
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable drawable2 = i == -1 ? null : context.getResources().getDrawable(i);
        if (i2 != -1) {
            drawable = context.getResources().getDrawable(i2);
        }
        stateListDrawable.addState(new int[]{16842912, 16842910}, drawable);
        stateListDrawable.addState(new int[]{16842910}, drawable2);
        stateListDrawable.addState(new int[0], drawable2);
        return stateListDrawable;
    }

    public static StateListDrawable a(Context context, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919, 16842910}, drawable2);
        stateListDrawable.addState(new int[]{16842910, 16842908}, drawable3);
        stateListDrawable.addState(new int[]{16842910}, drawable);
        stateListDrawable.addState(new int[]{16842908}, drawable3);
        stateListDrawable.addState(new int[]{-16842910}, drawable4);
        stateListDrawable.addState(new int[0], drawable);
        return stateListDrawable;
    }

    public static StateListDrawable a(int i, int i2, int i3) {
        return a((BitmapDrawable) App.sInstance.getResources().getDrawable(i), i2, i3);
    }

    public static StateListDrawable a(Drawable drawable, int i, int i2) {
        Drawable drawable2;
        Drawable drawable3;
        Drawable drawable4;
        if (i != -1) {
            drawable2 = drawable.getConstantState().newDrawable().mutate();
            drawable2.setAlpha(i);
        } else {
            drawable2 = null;
        }
        if (i2 != -1) {
            drawable3 = drawable.getConstantState().newDrawable().mutate();
            drawable3.setAlpha(i2);
        } else {
            drawable3 = null;
        }
        return a((Context) App.sInstance, drawable, drawable2, (Drawable) null, drawable3);
    }

    public static StateListDrawable a(BitmapDrawable bitmapDrawable, int i, int i2) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        if (i != -1) {
            drawable = new BitmapDrawable(App.sInstance.getResources(), bitmapDrawable.getBitmap());
            drawable.setAlpha(i);
        } else {
            drawable = null;
        }
        if (i2 != -1) {
            drawable2 = new BitmapDrawable(App.sInstance.getResources(), bitmapDrawable.getBitmap());
            drawable2.setAlpha(i2);
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
        return a((Context) App.sInstance, (Drawable) bitmapDrawable, drawable, (Drawable) null, drawable2);
    }

    public static StateListDrawable a(int i, int i2, int i3, int i4, int i5) {
        Drawable drawable;
        Drawable drawable2;
        Drawable drawable3;
        Drawable drawable4;
        App instance = App.sInstance;
        Bitmap decodeResource = BitmapFactory.decodeResource(instance.getResources(), i);
        byte[] ninePatchChunk = decodeResource.getNinePatchChunk();
        Drawable drawable5 = instance.getResources().getDrawable(i);
        if (i2 != -1) {
            NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(instance.getResources(), decodeResource, ninePatchChunk, new Rect(), null);
            ninePatchDrawable.setBounds(0, 0, i4, i5);
            ninePatchDrawable.setAlpha(i2);
            drawable = ninePatchDrawable;
        } else {
            drawable = null;
        }
        if (i3 != -1) {
            drawable2 = new NinePatchDrawable(instance.getResources(), decodeResource, ninePatchChunk, new Rect(), null);
            drawable2.setBounds(0, 0, i4, i5);
            drawable2.setAlpha(i2);
        } else {
            drawable2 = null;
        }
        if (!v.l()) {
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
        return a((Context) ApplicationWrapper.getInstance(), drawable5, drawable3, (Drawable) null, drawable2);
    }

    public static StateListDrawable a(int i, int i2) {
        return a((BitmapDrawable) ApplicationWrapper.getInstance().getResources().getDrawable(i), (BitmapDrawable) ApplicationWrapper.getInstance().getResources().getDrawable(i2), 76);
    }

    public static StateListDrawable a(BitmapDrawable bitmapDrawable, BitmapDrawable bitmapDrawable2, int i) {
        Drawable drawable;
        if (i != -1) {
            drawable = new BitmapDrawable(ApplicationWrapper.getInstance().getResources(), bitmapDrawable.getBitmap());
            drawable.setAlpha(i);
        } else {
            drawable = null;
        }
        if (!v.l() && drawable != null) {
            drawable = new SupportV21AlphaDrawable(drawable);
        }
        return a((Context) ApplicationWrapper.getInstance(), (Drawable) bitmapDrawable, (Drawable) bitmapDrawable2, (Drawable) null, drawable);
    }

    public static StateListDrawable b(Context context, int i, int i2, int i3, int i4) {
        return a(context, NeteaseMusicUtils.a(i), i2, context.getResources().getColor(i3), i2, context.getResources().getColor(i4), (Integer) null);
    }

    public static StateListDrawable b(Context context, int i, int i2, int i3, int i4, int i5) {
        return a(context, i, i2, i3, i4, i5, (Integer) null);
    }

    public static StateListDrawable a(Context context, int i, int i2, int i3, int i4, int i5, Integer num) {
        return a(context, (Drawable) b(i, i2, i3), (Drawable) b(i, i4, i5), (Drawable) null, (Drawable) null, (Drawable) num != null ? b(i, i2, num.intValue()) : null);
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

    public static int a(Context context) {
        int i = 0;
        int identifier = context.getResources().getIdentifier(a.auu.a.c("PREVERQAOiwEBjoJFgwpDQA="), a.auu.a.c("KgwZAA8="), a.auu.a.c("LwsQFw4aAQ=="));
        if (identifier > 0) {
            i = context.getResources().getDimensionPixelSize(identifier);
        }
        if (i == 0) {
            return NeteaseMusicUtils.a(25.0f);
        }
        return i;
    }

    public static int b(Context context) {
        return ApplicationWrapper.getInstance().getResources().getDimensionPixelSize(C0299b.toolbarHeight);
    }

    public static int c(Context context) {
        return b(context) + a(context);
    }

    public static int d(Context context) {
        return (v.e() ? a(context) : 0) + b(context);
    }

}
