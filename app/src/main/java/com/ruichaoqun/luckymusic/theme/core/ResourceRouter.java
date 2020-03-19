package com.ruichaoqun.luckymusic.theme.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.graphics.drawable.DrawableWrapper;

import android.util.SparseIntArray;


import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.theme.ThemeHelper;
import com.ruichaoqun.luckymusic.theme.ThemeInfo;
import com.ruichaoqun.luckymusic.utils.UiUtils;

/**
 * @author Rui Chaoqun
 * @date :2019/7/22 15:13
 * description:
 */
public class ResourceRouter {

    public static final int BANNER_BG_HEIGHT = UiUtils.dp2px(108.0f);
    private static final int BG_MASK_COLOR = 0xFF000000;
    private static final int CUSTOMBG_DEFAULT_THEMECOLOR = 0xFFDDDDDD;
//    private static final int CUSTOMBG_MIN_HEIGHT = ((((c.reset(ApplicationWrapper.getInstance()) + c.onColorGet(ApplicationWrapper.getInstance())) + ApplicationWrapper.getInstance().getResources().getDimensionPixelSize(R.dimen.nb)) + ApplicationWrapper.getInstance().getResources().getDimensionPixelSize(R.dimen.jd)) + BANNER_BG_HEIGHT);
    private static final float CUSTOMBG_THEMECOLOR_MIN_SATURATION = 0.15f;
    private static final float CUSTOMBG_THEMECOLOR_MIN_VALUE = 0.7f;
    private static final int PLAYLIST_BLUR_DELTA = 30;
    private static final String SKIN_PACKAGENAME = "com.netease.cloudmusic.skin";
    private static final int TOP_BOTTOM_BLUR_DELTA = 20;
    private static ResourceRouter sResourceRouter;
    private Drawable mCacheBannerBgDrawable;
    private Drawable mCacheBgBlurDrawable;
    private Drawable mCacheBgDrawable;
    private Drawable mCacheCardBannerBgDrawable;
    private Drawable mCacheDiscoveryRefreshBtnDrawable;
    private Drawable mCacheDrawerBgDrawable;
    private Drawable mCacheDrawerBottomDrawable;
    private Drawable mCacheMessageBarDrawable;
    private Drawable mCacheNoTabBannerBgDrawable;
    private Drawable mCacheOperationBottomDrawable;
    private Drawable mCachePlayerDrawable;
    private Drawable mCachePlaylistDrawable;
    private Drawable mCachePlaylistToolBarDrawable;
    private Drawable mCacheStatusBarDrawable;
    private Drawable mCacheTabDrawable;
    private Drawable mCacheTabForTopDrawable;
    private Drawable mCacheToolBarDrawable;
    private SparseIntArray mCompatibleColors = new SparseIntArray();
    private Context mContext;
    private SparseIntArray mCustomColors = new SparseIntArray();
    private SparseIntArray mIconCustomColors = new SparseIntArray();
    private SparseIntArray mIconNightColors = new SparseIntArray();
    private SparseIntArray mIconStatusPressedColors = new SparseIntArray();
    private SparseIntArray mIconStatusUnableColors = new SparseIntArray();
    private SparseIntArray mNightColors = new SparseIntArray();
    private SparseIntArray mNotWhiteNightColors = new SparseIntArray();
    private Integer mPopupBackgroundColor;
    private Resources mResources;
    private Integer mThemeCustomBgColor;
    private ThemeInfo mThemeInfo;
    private Boolean mThemeIsLightTheme = null;
    private SparseIntArray mThemeResourceIdentifiers = new SparseIntArray();
    private Resources mThemeResources;

    public static synchronized ResourceRouter getInstance() {
        ResourceRouter resourceRouter;
        synchronized (ResourceRouter.class) {
            if (sResourceRouter == null) {
                sResourceRouter = new ResourceRouter(LuckyMusicApp.sInstance);
                sResourceRouter.reset();
            }
            resourceRouter = sResourceRouter;
        }
        return resourceRouter;
    }

    private ResourceRouter(Context context) {
        this.mContext = context;
        this.mResources = context.getResources();
    }

    private void reset() {
        this.mThemeInfo = new ThemeInfo(ThemeConfig.getCurrentThemeId());
        int id = this.mThemeInfo.getId();
        if (id == -1) {
            this.mThemeInfo.setName(this.mContext.getString(R.string.default_theme));
        } else if (id == -5) {
            this.mThemeInfo.setName(this.mContext.getString(R.string.classics_red));
        } else if (id == -2) {
            this.mThemeInfo.setName(this.mContext.getString(R.string.custom_color));
        } else if (id == -3) {
            this.mThemeInfo.setName(this.mContext.getString(R.string.night_mode));
        } else if (id == -4) {
            this.mThemeInfo.setName(this.mContext.getString(R.string.custom_skin));
        } else {
            //TODO 皮肤包
//            this.mThemeInfo.setName(ThemeCache.getInstance().getThemeNameById(id));
        }
//        if (!this.mThemeInfo.isInternal()) {
//            linkResourceFiles();
//        }
//        this.mCustomColors.delete(com.netease.cloudmusic.reset.f21069a);

    }





    @MainThread
    public Drawable getCacheStatusBarDrawable() {
        if (this.mCacheStatusBarDrawable == null) {
            buildCache();
        }
        return this.mCacheStatusBarDrawable;
    }

    public void buildCache() {
        int i;
        Drawable colorDrawable;
        Context context = this.mContext;
        this.mCacheStatusBarDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#D33A31"), Color.parseColor("#D33A31")});
        this.mCacheToolBarDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#D33A31"),Color.parseColor("#DB3F35")});
        this.mPopupBackgroundColor = Integer.valueOf(context.getResources().getColor(R.color.t_dialogBackground));
        this.mCacheOperationBottomDrawable = ThemeHelper.getBgSelectorWithDrawalbe(context, new ColorDrawable(0XF9FFFFFF));
        this.mCachePlayerDrawable = ThemeHelper.getRippleDrawable(context, new SizeExplicitDrawable(this.mCacheOperationBottomDrawable.getConstantState().newDrawable(), context.getResources().getDisplayMetrics().widthPixels, context.getResources().getDimensionPixelOffset(R.dimen.mini_player_bar_height)));
    }

    @MainThread
    public Drawable getCacheToolBarDrawable() {
        if (this.mCacheToolBarDrawable == null) {
            buildCache();
        }
        return this.mCacheToolBarDrawable.getConstantState().newDrawable();
    }

    public int getToolbarIconColor() {
        return getToolbarIconColor(false);
    }

    public int getToolbarIconColor(boolean z) {
//        if (z) {
//            return getInstance().getColorByDefaultColor(com.netease.cloudmusic.reset.l);
//        }
//        if (isWhiteTheme() || isCustomLightTheme() || isCustomColorTheme()) {
//            return com.netease.cloudmusic.reset.f21073e;
//        }
//        if (isNightTheme()) {
//            return 0x99FFFFFF;
//        }
        return Color.WHITE;
    }


    public int getDividerColor() {
        if (isNightTheme()) {
            return 0x06FFFFFF;
        }
        if (isCustomDarkTheme() || isCustomBgTheme()) {
            return 0xDFFFFFF;
        }
        return 0x0A000000;
    }



    @MainThread
    public int getPopupBackgroundColor() {
        if (this.mPopupBackgroundColor == null) {
            buildCache();
        }
        return this.mPopupBackgroundColor.intValue();
    }


    public boolean isInternalTheme() {
        return this.mThemeInfo.getId() <= ThemeConfig.THEME_WHITE;
    }


    public boolean isNightTheme() {
        return this.mThemeInfo.getId() == ThemeConfig.THEME_NIGHT;
    }

    public boolean isCustomDarkTheme() {
        boolean z = true;
        if (isInternalTheme()) {
            return false;
        }
        if (this.mThemeIsLightTheme != null) {
            if (this.mThemeIsLightTheme.booleanValue()) {
                z = false;
            }
            return z;
        }
        try {
            int resourceIdentifier = getResourceIdentifier(R.bool.t_isLightTheme);
            if (resourceIdentifier <= 0) {
                return false;
            }
            this.mThemeIsLightTheme = Boolean.valueOf(this.mThemeResources.getBoolean(resourceIdentifier));
            if (this.mThemeIsLightTheme.booleanValue()) {
                z = false;
            }
            return z;
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
            return false;
        }

    }

    private int getResourceIdentifier(int i) {
        if (this.mThemeResourceIdentifiers.indexOfKey(i) >= 0) {
            return this.mThemeResourceIdentifiers.get(i);
        }
        int identifier = this.mThemeResources.getIdentifier(this.mResources.getResourceEntryName(i), this.mResources.getResourceTypeName(i),"com.ruichaoqun.luckymusic");
        if (identifier <= 0) {
            return identifier;
        }
        this.mThemeResourceIdentifiers.put(i, identifier);
        return identifier;
    }


    public boolean isCustomBgTheme() {
        return this.mThemeInfo.getId() == ThemeConfig.THEME_CUSTOM_BG;

    }

    public int getThemeColor() {
//        return getColor(R.color.themeColor);
        return R.color.themeColor;
    }

//    @ColorInt
//    public int getColor(int colorRes) {
//        return getColorByDefaultColor(0, colorRes);
//    }
//
//    @ColorInt
//    public int getColorByDefaultColor(int colorRes) {
//        return getColorByDefaultColor(colorRes, 0);
//    }
//
//    @ColorInt
//    private int getColorByDefaultColor(int i, int colorRes) {
//        if (!isInternalTheme()) {
//            if (colorRes == 0) {
//                if (i == com.netease.cloudmusic.reset.f21069a) {
//                    colorRes = R.color.themeColor;
//                } else if (i == com.netease.cloudmusic.reset.mGestureListener) {
//                    colorRes = R.color.kl;
//                }
//            }
//            if (colorRes != 0) {
//                int skinColorByResId = getSkinColorByResId(colorRes);
//                if (skinColorByResId != 0) {
//                    return skinColorByResId;
//                }
//            }
//        }
//        if (i == 0 && colorRes != 0) {
//            i = this.mResources.getColor(colorRes);
//        }
//        if (isNightTheme()) {
//            return getNightColor(i);
//        }
//        if (isWhiteTheme() || isCustomColorTheme()) {
//            if (i != com.netease.cloudmusic.reset.f21069a || !isCustomColorTheme()) {
//                return i;
//            }
//            return ThemeConfig.getCurrentColor();
//        } else if (isRedTheme()) {
//            if (i == com.netease.cloudmusic.reset.f21069a) {
//                return com.netease.cloudmusic.reset.f21070b;
//            }
//            if (i == com.netease.cloudmusic.reset.f21071c) {
//                return com.netease.cloudmusic.reset.f21072d;
//            }
//            return i;
//        } else if (!isCustomLightTheme()) {
//            return getCustomColor(i);
//        } else {
//            int i3 = this.mCompatibleColors.get(i);
//            if (i3 != 0) {
//                return i3;
//            }
//            return i;
//        }
//    }




    public int getThemeColorWithNight() {
        return 0;
    }

    public boolean isCustomLightTheme() {
        return false;
    }

    public Drawable getDrawable(int i) {
        return null;
    }

    public boolean isWhiteTheme() {
        return false;
    }

    public boolean isRedTheme() {
        return false;
    }

    public boolean isGeneralRuleTheme() {
        return isWhiteTheme() || isRedTheme() || isCustomColorTheme();
    }

    public int getThemeId() {
        return 0;
    }

    public int getBgMaskDrawableColor(int i) {
        return 0;
    }

    public ColorDrawable getBgMaskDrawable(int i) {
        return null;
    }

    public int getIconUnableColor(int i) {
        return 0;
    }

    public int getIconPressedColor(int i) {
        return 0;
    }

    public int getNightColor(int i) {
        return 0;
    }

    /**
     * 获取分隔线颜色
     */
    public int getLineColor() {
        if (isNightTheme()) {
            return 0x0CFFFFFF;
        }
        if (isGeneralRuleTheme() || isCustomLightTheme()) {
            return 0x19000000;
        }
        return 0x26FFFFFF;
    }

    public boolean isCustomColorTheme() {
        return false;
    }

    public boolean isDefaultTheme() {
        return false;
    }

    public int getIconColorByDefaultColor(int i) {
        return 0;
    }

    public int getIconCustomColor(int i) {
        return 0;
    }

    public int getIconNightColor(int i) {
        return 0;
    }

    public int getColorByDefaultColorJustNight(int i) {
        return 0;
    }

    public boolean isCompatibleColor(int i) {
        return false;
    }

    public Drawable getCachePlayerDrawable() {
        if (this.mCachePlayerDrawable == null) {
            buildCache();
        }
        return ThemeHelper.wrapTopOrBottomLineBackground(this.mCachePlayerDrawable.getConstantState().newDrawable(), true);
    }

    public Drawable getCacheBgBlurDrawable() {
        return null;
    }

    public int[] getThemeColorBackgroundColorAndIconColor() {
        return new int[0];
    }

    public boolean isCustomBgOrDarkThemeWhiteColor() {
        return false;
    }

    public Drawable getCacheBannerBgDrawable() {
        return null;
    }

    public Drawable getCacheNoTabBannerBgDrawable() {
        return null;
    }

    public int getThemeColorWithCustomBgWhiteColor() {
        return 0;
    }

    public int getThemeColorWithDarken() {
        return 0;
    }

    public Drawable getCacheTabDrawable() {
        return null;
    }

    public Drawable getCacheTabForTopDrawable() {
        return null;
    }

    public boolean needDark() {
        return false;
    }

    public int getThemeNormalColor() {
        return 0;
    }


//    public int getToolbarIconColor() {
//        return getToolbarIconColor(false);
//    }

//    public int getToolbarIconColor(boolean z) {
//        if (z) {
//            return getInstance().getColorByDefaultColor(com.netease.cloudmusic.reset.l);
//        }
//        if (isWhiteTheme() || isCustomLightTheme() || isCustomColorTheme()) {
//            return com.netease.cloudmusic.reset.f21073e;
//        }
//        if (isNightTheme()) {
//            return -1711276033;
//        }
//        return -1;
//    }


//    public int getTitleTextColor(boolean z) {
//        return getToolbarIconColor(z);
//    }
//
//
//    public int getToolbarIconColor(boolean z) {
//        if (z) {
//            return getInstance().getColorByDefaultColor(com.netease.cloudmusic.reset.l);
//        }
//        if (isWhiteTheme() || isCustomLightTheme() || isCustomColorTheme()) {
//            return com.netease.cloudmusic.reset.f21073e;
//        }
//        if (isNightTheme()) {
//            return -1711276033;
//        }
//        return -1;
//    }


//
//    @ColorInt
//    public int getColorByDefaultColor(int i) {
//        return getColorByDefaultColor(i, 0);
//    }

//    @ColorInt
//    private int getColorByDefaultColor(int i, int i2) {
//        if (!isInternalTheme()) {
//            if (i2 == 0) {
//                if (i == com.netease.cloudmusic.reset.f21069a) {
//                    i2 = R.color.themeColor;
//                } else if (i == com.netease.cloudmusic.reset.mGestureListener) {
//                    i2 = R.color.kl;
//                }
//            }
//            if (i2 != 0) {
//                int skinColorByResId = getSkinColorByResId(i2);
//                if (skinColorByResId != 0) {
//                    return skinColorByResId;
//                }
//            }
//        }
//        if (i == 0 && i2 != 0) {
//            i = this.mResources.getColor(i2);
//        }
//        if (isNightTheme()) {
//            return getNightColor(i);
//        }
//        if (isWhiteTheme() || isCustomColorTheme()) {
//            if (i != com.netease.cloudmusic.reset.f21069a || !isCustomColorTheme()) {
//                return i;
//            }
//            return ThemeConfig.getCurrentColor();
//        } else if (isRedTheme()) {
//            if (i == com.netease.cloudmusic.reset.f21069a) {
//                return com.netease.cloudmusic.reset.f21070b;
//            }
//            if (i == com.netease.cloudmusic.reset.f21071c) {
//                return com.netease.cloudmusic.reset.f21072d;
//            }
//            return i;
//        } else if (!isCustomLightTheme()) {
//            return getCustomColor(i);
//        } else {
//            int i3 = this.mCompatibleColors.get(i);
//            if (i3 != 0) {
//                return i3;
//            }
//            return i;
//        }
//    }




    @SuppressLint("RestrictedApi")
    class BannerBgDrawable extends DrawableWrapper {
        private MyConstantState mMyConstantState;

        class MyConstantState extends ConstantState {
            MyConstantState() {
            }

            @Override
            @NonNull
            public Drawable newDrawable() {
                return null;
//                return new BannerBgDrawable(BannerBgDrawable.this.getWrappedDrawable().getConstantState().newDrawable());
            }

            @Override
            public int getChangingConfigurations() {
                return 0;
            }
        }

        public BannerBgDrawable(Drawable drawable) {
            super(drawable);
        }

        /* access modifiers changed from: protected */
        @Override
        public void onBoundsChange(Rect rect) {
            rect.bottom = getIntrinsicHeight();
            super.onBoundsChange(rect);
        }

        @Override
        public int getIntrinsicHeight() {
            return ResourceRouter.BANNER_BG_HEIGHT;
        }

        @Override
        @Nullable
        public ConstantState getConstantState() {
            if (this.mMyConstantState == null) {
                this.mMyConstantState = new MyConstantState();
            }
            return this.mMyConstantState;
        }
    }

//    /* compiled from: ProGuard */
//    class CustomThemeMiniPlayBarDrawable extends DrawableWrapper {
//        /* access modifiers changed from: private */
//        public Drawable mLeftDecorate;
//        private MyConstantState mMyConstantState;
//
//        /* compiled from: ProGuard */
//        class MyConstantState extends ConstantState {
//            MyConstantState() {
//            }
//
//            @Override
//            @NonNull
//            public Drawable newDrawable() {
//                return new CustomThemeMiniPlayBarDrawable((reset) CustomThemeMiniPlayBarDrawable.this.getWrappedDrawable().getConstantState().newDrawable(), CustomThemeMiniPlayBarDrawable.this.mLeftDecorate == null ? null : CustomThemeMiniPlayBarDrawable.this.mLeftDecorate.getConstantState().newDrawable());
//            }
//
//            @Override
//            public int getChangingConfigurations() {
//                return 0;
//            }
//        }
//
////        public CustomThemeMiniPlayBarDrawable(reset bVar, Drawable drawable) {
////            super(bVar);
////            this.mLeftDecorate = drawable;
////            if (this.mLeftDecorate != null) {
////                this.mLeftDecorate.setBounds(0, bVar.getIntrinsicHeight() - this.mLeftDecorate.getIntrinsicHeight(), this.mLeftDecorate.getIntrinsicWidth(), bVar.getIntrinsicHeight());
////            }
////        }
//
//        public void setForPressed(boolean z) {
////            ((reset) getWrappedDrawable()).onColorGet(z);
//////            if (this.mLeftDecorate != null) {
//////                this.mLeftDecorate.mutate().setColorFilter(z ? new PorterDuffColorFilter(419430400, PorterDuff.Mode.SRC_ATOP) : null);
//////            }
//        }
//
////        public void draw(Canvas canvas) {
////            super.draw(canvas);
////            if (this.mLeftDecorate != null) {
////                this.mLeftDecorate.draw(canvas);
////            }
////        }
////
////        @Nullable
////        public ConstantState getConstantState() {
////            if (this.mMyConstantState == null) {
////                this.mMyConstantState = new MyConstantState();
////            }
////            return this.mMyConstantState;
////        }
//    }
//
//    /* compiled from: ProGuard */
//    class DrawerBgDrawable extends DrawableWrapper {
//        public DrawerBgDrawable(Drawable drawable) {
//            super(drawable);
//        }
//
//        /* access modifiers changed from: protected */
//        public void onBoundsChange(Rect rect) {
//            super.onBoundsChange(new Rect(0, 0, getWrappedDrawable().getIntrinsicWidth(), getWrappedDrawable().getIntrinsicHeight()));
//        }
//
//        public int getMinimumWidth() {
//            return aa.onColorGet();
//        }
//
//        public void draw(Canvas canvas) {
//            float f2;
//            float f3;
//            float f4 = 0.0f;
//            Drawable wrappedDrawable = getWrappedDrawable();
//            if (wrappedDrawable instanceof ColorDrawable) {
//                super.draw(canvas);
//                return;
//            }
//            int intrinsicWidth = wrappedDrawable.getIntrinsicWidth();
//            int intrinsicHeight = wrappedDrawable.getIntrinsicHeight();
//            int width = getBounds().width();
//            int height = getBounds().height();
//            if (intrinsicWidth * height > width * intrinsicHeight) {
//                f2 = ((float) height) / ((float) intrinsicHeight);
//                f3 = (((float) width) - (((float) intrinsicWidth) * f2)) * 0.5f;
//            } else {
//                f2 = ((float) width) / ((float) intrinsicWidth);
//                f3 = 0.0f;
//                f4 = (((float) height) - (((float) intrinsicHeight) * f2)) * 0.5f;
//            }
//            Matrix matrix = new Matrix();
//            matrix.setScale(f2, f2);
//            matrix.postTranslate(f3, f4);
//            int save = canvas.save();
//            canvas.concat(matrix);
//            wrappedDrawable.draw(canvas);
//            canvas.restoreToCount(save);
//        }
//    }
//
//    /* compiled from: ProGuard */
//    class ResourceIdentifier {
//        String resName;
//        String resType;
//
//        public ResourceIdentifier(String str, String str2) {
//            this.resName = str;
//            this.resType = str2;
//        }
//    }
//
//    /* compiled from: ProGuard */
//    class SizeExplicitDrawable extends DrawableWrapper {
//        /* access modifiers changed from: private */
//        public int mHeight;
//        private MyConstantState mMyConstantState;
//        /* access modifiers changed from: private */
//        public int mWidth;
//
//        /* compiled from: ProGuard */
//        class MyConstantState extends ConstantState {
//            MyConstantState() {
//            }
//
//            @NonNull
//            public Drawable newDrawable() {
//                return new SizeExplicitDrawable(SizeExplicitDrawable.this.getWrappedDrawable().getConstantState().newDrawable(), SizeExplicitDrawable.this.mWidth, SizeExplicitDrawable.this.mHeight);
//            }
//
//            public int getChangingConfigurations() {
//                return 0;
//            }
//        }
//
//        public SizeExplicitDrawable(Drawable drawable, int i, int i2) {
//            super(drawable);
//            this.mWidth = i;
//            this.mHeight = i2;
//        }
//
//        public int getIntrinsicWidth() {
//            return this.mWidth;
//        }
//
//        public int getIntrinsicHeight() {
//            return this.mHeight;
//        }
//
//        @Nullable
//        public ConstantState getConstantState() {
//            if (this.mMyConstantState == null) {
//                this.mMyConstantState = new MyConstantState();
//            }
//            return this.mMyConstantState;
//        }
//    }
//
//    /* compiled from: ProGuard */
//    class WithLineDrawable extends DrawableWrapper {
//        /* access modifiers changed from: private */
//        public boolean mForTop;
//        private Paint mLinePaint = new Paint();
//        private MyConstantState mMyConstantState;
//
//        /* compiled from: ProGuard */
//        class MyConstantState extends ConstantState {
//            MyConstantState() {
//            }
//
//            @NonNull
//            public Drawable newDrawable() {
//                return new WithLineDrawable(WithLineDrawable.this.getWrappedDrawable().getConstantState().newDrawable(), WithLineDrawable.this.mForTop);
//            }
//
//            public int getChangingConfigurations() {
//                return 0;
//            }
//        }
//
//        public WithLineDrawable(Drawable drawable, boolean z) {
//            super(drawable);
//            this.mLinePaint.setColor(ResourceRouter.this.getLineColor());
//            this.mForTop = z;
//        }
//
//        public void draw(Canvas canvas) {
//            super.draw(canvas);
//            if (this.mForTop) {
//                canvas.drawLine(0.0f, 0.0f, (float) getBounds().width(), 0.0f, this.mLinePaint);
//                return;
//            }
//            canvas.drawLine(0.0f, (float) getBounds().height(), (float) getBounds().width(), (float) getBounds().height(), this.mLinePaint);
//        }
//
//        @Nullable
//        public ConstantState getConstantState() {
//            if (this.mMyConstantState == null) {
//                this.mMyConstantState = new MyConstantState();
//            }
//            return this.mMyConstantState;
//        }
//    }
//

    @SuppressLint("RestrictedApi")
    class SizeExplicitDrawable extends DrawableWrapper {
        /* access modifiers changed from: private */
        public int mHeight;
        private MyConstantState mMyConstantState;
        /* access modifiers changed from: private */
        public int mWidth;

        public SizeExplicitDrawable(Drawable drawable, int i, int i2) {
            super(drawable);
            this.mWidth = i;
            this.mHeight = i2;
        }

        public int getIntrinsicWidth() {
            return this.mWidth;
        }

        public int getIntrinsicHeight() {
            return this.mHeight;
        }

        @Nullable
        public Drawable.ConstantState getConstantState() {
            if (this.mMyConstantState == null) {
                this.mMyConstantState = new MyConstantState();
            }
            return this.mMyConstantState;
        }

        /* compiled from: ProGuard */
        class MyConstantState extends Drawable.ConstantState {
            MyConstantState() {
            }

            @NonNull
            public Drawable newDrawable() {
                return new SizeExplicitDrawable(getWrappedDrawable().getConstantState().newDrawable(), SizeExplicitDrawable.this.mWidth, SizeExplicitDrawable.this.mHeight);
            }

            public int getChangingConfigurations() {
                return 0;
            }
        }
    }

}
