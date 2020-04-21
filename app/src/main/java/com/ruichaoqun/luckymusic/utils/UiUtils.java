package com.ruichaoqun.luckymusic.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.ruichaoqun.luckymusic.LuckyMusicApp;
import com.ruichaoqun.luckymusic.R;

import java.lang.reflect.Method;

public class UiUtils {

    /** px值转换为dp值 */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * dp转换px
     * @param dp
     * @return
     */
    public static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, LuckyMusicApp.getInstance().getResources().getDisplayMetrics());
    }

    /** sp值转换为px值*/
    public static int sp2px(float spValue) {
        final float fontScale = LuckyMusicApp.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /** 获取屏幕宽度 */
    public static int getScreenWidth(){
        DisplayMetrics dm = LuckyMusicApp.getInstance().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /** 获取屏幕高度 */
    public static int getScreenHeight(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }


    /**
     * dimen资源转换成px
     * @param res
     * @return
     */
    public static int getDimensionPixelSize(int res){
        return LuckyMusicApp.sInstance.getResources().getDimensionPixelSize(res);
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int i = 0;
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            i = context.getResources().getDimensionPixelSize(identifier);
        }
        if (i == 0) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, context.getResources().getDisplayMetrics());
        }
        return i;
    }

    /**
     * 获取底部导航栏高度
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        if(checkDeviceHasNavigationBar(context)){
            int navigationHeight = -1;
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            //获取NavigationBar的高度
            navigationHeight = resources.getDimensionPixelSize(resourceId);
            return navigationHeight;
        }
        return 0;
    }

    //判断是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

    public static int getToolbarHeight(){
        return LuckyMusicApp.getInstance().getResources().getDimensionPixelOffset(R.dimen.toolbar_height);
    }


}
