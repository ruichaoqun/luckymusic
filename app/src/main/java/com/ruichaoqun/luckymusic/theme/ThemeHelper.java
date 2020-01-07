package com.ruichaoqun.luckymusic.theme;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.ColorUtils;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.service.impl.ThemeServiceImpl;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.utils.CommonUtils;
import com.ruichaoqun.luckymusic.utils.ReflectUtils;
import com.ruichaoqun.luckymusic.utils.drawhelper.DrawableUtils;
import com.ruichaoqun.luckymusic.widget.drawable.PaddingLeftBackgroundDrawable;

import java.lang.reflect.Field;

public class ThemeHelper {

    public static Drawable getBgSelector(Context context, int res) {
        return getBgSelector(context, res, false);
    }

    public static Drawable getBgSelector(Context context, int padding, boolean forCard) {
        return getBgSelector(context, padding, forCard, false);
    }

    public static Drawable getBgSelector(Context context, int paddingLeft, boolean forCard, boolean bothPadding) {
        Drawable drawable;
        Drawable paddingLeftBackgroundDrawable = (forCard || paddingLeft >= 0) ? new PaddingLeftBackgroundDrawable(paddingLeft, forCard, false, bothPadding) : null;
        Drawable paddingLeftBackgroundDrawable2 = new PaddingLeftBackgroundDrawable(paddingLeft, forCard, true, bothPadding);
        if (forCard) {
            drawable = new LayerDrawable(new Drawable[]{paddingLeftBackgroundDrawable, paddingLeftBackgroundDrawable2});
        } else {
            drawable = paddingLeftBackgroundDrawable2;
        }
        return getRippleDrawable(context, DrawableUtils.getPressedDrawable(context, paddingLeftBackgroundDrawable, drawable, (Drawable) null, (Drawable) null));
    }

    public static int getColor700from500(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        fArr[2] = (i == -1 ? 0.8f : 0.85f) * fArr[2];
        return Color.HSVToColor(fArr);
    }

    public static void configBg(View view, int i) {
        configBg(view, i, false);
    }

    public static void configBg(View view, int bgType, boolean forCard) {
        if (CommonUtils.versionAbove19()) {
            setViewBackground(view, bgType, forCard);
            return;
        }
        int paddingLeft = view.getPaddingLeft();
        int paddingTop = view.getPaddingTop();
        int paddingRight = view.getPaddingRight();
        int paddingBottom = view.getPaddingBottom();
        setViewBackground(view, bgType, forCard);
        view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    private static void setViewBackground(View view, int bgType, boolean forCard) {
        configPaddingBg(view, bgType == 1 ? 0 : -1, forCard);
    }

    public static void configDrawableTheme(Drawable drawable) {
        configDrawableTheme(drawable, ThemeService.getInstance().getThemeColor());
    }


    public static Drawable configDrawableTheme(Drawable drawable, int color) {
        return configDrawableThemeUseTint(drawable, color);
    }

    public static Drawable configDrawableThemeUseTint(Drawable drawable, int color) {
        return configDrawableThemeUseTintList(drawable, ColorStateList.valueOf(color));
    }


    public static Drawable configDrawableThemeUseTintList(Drawable drawable, ColorStateList colorStateList) {
        if (drawable == null) {
            return null;
        }
        Drawable unwrap = DrawableCompat.unwrap(drawable);
        Drawable.Callback callback = unwrap.getCallback();
        Drawable wrap = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintList(wrap, colorStateList);
        if (callback == null || unwrap.getCallback() == callback) {
            return wrap;
        }
        unwrap.setCallback(callback);
        callback.invalidateDrawable(unwrap);
        return wrap;
    }

    public static void configSearchViewTheme(Toolbar toolbar, SearchView searchView) {
        configSearchViewTheme(toolbar, searchView, true);
    }

    public static void configSearchViewTheme(Toolbar toolbar, SearchView searchView, int toolbarIconColor) {
        configSearchViewTheme(toolbar, searchView, toolbarIconColor, true);
    }

    public static void configSearchViewTheme(Toolbar toolbar, SearchView searchView, boolean z) {
        configSearchViewTheme(toolbar, searchView, ResourceRouter.getInstance().getToolbarIconColor(), z);
    }

    public static void configSearchViewTheme(Toolbar toolbar, SearchView searchView, int toolbarIconColor, boolean z) {
        ImageView imageView = searchView.findViewById(R.id.search_close_btn);
        imageView.setImageDrawable(LuckyMusicApp.sInstance.getResources().getDrawable(R.drawable.abc_ic_clear_material));
        LinearLayout linearLayout =  searchView.findViewById(R.id.search_plate);
        linearLayout.setBackgroundDrawable(LuckyMusicApp.sInstance.getResources().getDrawable(R.drawable.abc_textfield_search_material));
        configDrawableTheme(linearLayout.getBackground(), toolbarIconColor);
        View searchText = searchView.findViewById(R.id.search_src_text);
        searchText.setPadding(0, searchText.getPaddingTop(), searchText.getPaddingRight(), searchText.getPaddingBottom());
        linearLayout.setPadding(0, linearLayout.getPaddingTop(), linearLayout.getPaddingRight(), linearLayout.getPaddingBottom());
        linearLayout.getLayoutParams().height = searchText.getLayoutParams().height;
        ((LinearLayout.LayoutParams) searchView.findViewById(R.id.search_edit_frame).getLayoutParams()).leftMargin = 0;
        ImageButton imageButton = (ImageButton) ReflectUtils.getDeclaredField(Toolbar.class, (Object) toolbar, "mCollapseButtonView");
        if (imageButton != null) {
            imageButton.setImageResource(R.mipmap.icon_arrow_back_white);
            configDrawableTheme(imageButton.getDrawable().mutate(), toolbarIconColor);
        }
        if (z) {
            expandSearchView(searchView);
        }
        try {
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) searchView.findViewById(R.id.search_src_text);
            autoCompleteTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.0f);
            autoCompleteTextView.setTextColor(ColorUtils.setAlphaComponent(toolbarIconColor, 178));
            autoCompleteTextView.setHintTextColor(ColorUtils.setAlphaComponent(toolbarIconColor, 76));
            autoCompleteTextView.setHighlightColor(Color.parseColor("#26000000"));
//            if (a2.isCustomDarkTheme() || a2.isCustomBgTheme()) {
//                autoCompleteTextView.setHighlightColor(654311423);
//            } else if (a2.isRedTheme()) {
//                autoCompleteTextView.setHighlightColor(Color.parseColor("#26000000"));
//            }
            Field declaredField = TextView.class.getDeclaredField("mCursorDrawableRes");
            declaredField.setAccessible(true);
            declaredField.set(autoCompleteTextView, LuckyMusicApp.sInstance.getResources().getDrawable(R.drawable.shape_cusor));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static Drawable getBgSelectorWithDrawalbe(Context context, Drawable drawable) {
        return getRippleDrawable(context, DrawableUtils.getStateListDrawable(drawable, (Drawable) new LayerDrawable(new Drawable[]{drawable, new PaddingLeftBackgroundDrawable(-1, false, true)}), (Drawable) null, (Drawable) null, (Drawable) null));
    }


    @SuppressLint("RestrictedApi")
    public static Drawable wrapTopOrBottomLineBackground(final Drawable drawable, final boolean z) {
        return ThemeService.getInstance().isGeneralRuleTheme() ? new DrawableWrapper(drawable) {
            private MyConstantState mMyConstantState;
            private Paint mPaint = new Paint();

            public void draw(@NonNull Canvas canvas) {
                drawable.draw(canvas);
                if (this.mPaint.getStrokeWidth() == 0.0f) {
                    this.mPaint.setColor(0x19000000);
                    this.mPaint.setStrokeWidth((float) LuckyMusicApp.getInstance().getResources().getDimensionPixelOffset(R.dimen.line_width));
                }
                float height = z ? 0.0f : ((float) getBounds().height()) - this.mPaint.getStrokeWidth();
                canvas.drawLine(0.0f, height, (float) getBounds().width(), height, this.mPaint);
            }

            @Nullable
            public Drawable.ConstantState getConstantState() {
                if (this.mMyConstantState == null) {
                    this.mMyConstantState = new MyConstantState();
                }
                return this.mMyConstantState;
            }

            /* renamed from: com.netease.cloudmusic.theme.core.ThemeHelper$1$MyConstantState */
            /* compiled from: ProGuard */
            class MyConstantState extends Drawable.ConstantState {
                MyConstantState() {
                }

                @NonNull
                public Drawable newDrawable() {
                    return ThemeHelper.wrapTopOrBottomLineBackground(drawable, z);
                }

                public int getChangingConfigurations() {
                    return 0;
                }
            }
        } : drawable;
    }



    public static void expandSearchView(SearchView searchView) {
        if (searchView != null) {
            ViewGroup.LayoutParams layoutParams = searchView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
            searchView.requestLayout();
        }
    }


    public static void configPaddingBg(View view, int padding, boolean forCard) {
        view.setBackgroundDrawable(getBgSelector(view.getContext(), padding, forCard));
    }

    public static Drawable getRippleDrawable(Context context, Drawable drawable) {
        boolean isNightTheme = ThemeService.getInstance().isNightTheme();
        if(!CommonUtils.versionAbove21()){
            return drawable;
        }
        return new RippleDrawable(ColorStateList.valueOf(context.getResources().getColor(isNightTheme ? R.color.theme_ripple_dark : R.color.theme_ripple_light)), drawable, new ColorDrawable(context.getResources().getColor(isNightTheme ? R.color.theme_ripple_mask_dark : R.color.theme_ripple_mask_light)));
    }

}
