package com.ruichaoqun.luckymusic.theme;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.common.MyApplication;
import com.ruichaoqun.luckymusic.theme.core.ResourceRouter;
import com.ruichaoqun.luckymusic.util.ReflectUtils;
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
        return getRippleDrawable(context, c.a(context, paddingLeftBackgroundDrawable, drawable, (Drawable) null, (Drawable) null));
    }

    public static int getColor700from500(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        fArr[2] = (i == -1 ? 0.8f : 0.85f) * fArr[2];
        return Color.HSVToColor(fArr);
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
        imageView.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.abc_ic_clear_material));
        LinearLayout linearLayout =  searchView.findViewById(R.id.search_plate);
        linearLayout.setBackgroundDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.abc_textfield_search_material));
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
            declaredField.set(autoCompleteTextView, MyApplication.getInstance().getResources().getDrawable(R.drawable.shape_cusor));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
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

    @TargetApi(21)
    public static Drawable getRippleDrawable(Context context, Drawable drawable) {
        boolean isNightTheme = ThemeService.getInstance().isNightTheme();
        return new RippleDrawable(ColorStateList.valueOf(context.getResources().getColor(isNightTheme ? R.color.theme_ripple_dark : R.color.theme_ripple_light)), drawable, new ColorDrawable(context.getResources().getColor(isNightTheme ? R.color.theme_ripple_mask_dark : R.color.theme_ripple_mask_light)));
    }

}
