package com.ruichaoqun.luckymusic.theme;

import android.app.ActionBar;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
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

import java.lang.reflect.Field;

public class ThemeHelper {
    public static int getColor700from500(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        fArr[2] = (i == -1 ? 0.8f : 0.85f) * fArr[2];
        return Color.HSVToColor(fArr);
    }

    public static Drawable configDrawableTheme(Drawable drawable, int i) {
        return configDrawableThemeUseTint(drawable, i);
    }

    public static Drawable configDrawableThemeUseTint(Drawable drawable, int i) {
        return configDrawableThemeUseTintList(drawable, ColorStateList.valueOf(i));
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
        ImageView imageView = (ImageView) searchView.findViewById(R.id.search_close_btn);
//        imageView.setImageDrawable(MyApplication.getInstance().getResources().getDrawable(android.R.drawable.abc_ic_clear_material));
        configDrawableTheme(imageView.getDrawable(), toolbarIconColor);
        LinearLayout linearLayout = (LinearLayout) searchView.findViewById(R.id.search_plate);
        linearLayout.setBackgroundDrawable(MyApplication.getInstance().getResources().getDrawable(R.drawable.abc_textfield_search_material));
        configDrawableTheme(linearLayout.getBackground(), toolbarIconColor);
        View findViewById = searchView.findViewById(R.id.search_src_text);
        findViewById.setPadding(0, findViewById.getPaddingTop(), findViewById.getPaddingRight(), findViewById.getPaddingBottom());
        linearLayout.setPadding(0, linearLayout.getPaddingTop(), linearLayout.getPaddingRight(), linearLayout.getPaddingBottom());
        linearLayout.getLayoutParams().height = findViewById.getLayoutParams().height;
        ((LinearLayout.LayoutParams) searchView.findViewById(R.id.search_edit_frame).getLayoutParams()).leftMargin = 0;
//        Drawable navigationIcon = toolbar.getNavigationIcon();
//        if (navigationIcon != null) {
//            ThemeHelper.configDrawableTheme(navigationIcon.mutate(), toolbarIconColor);
//        }
        ImageButton imageButton = (ImageButton) ReflectUtils.getDeclaredField(Toolbar.class, (Object) toolbar, "mNavButtonView");
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


}
