package com.ruichaoqun.luckymusic.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ruichaoqun.luckymusic.LuckyMusicApp;

/**
 * @author Rui Chaoqun
 * @date :2019/10/15 9:25
 * description:
 */
public class SharedPreferencesUtils {
    private SharedPreferences share;
    private SharedPreferences.Editor editor;
    private String SHARED_NAME = "luckymusic";//sp的文件名 自定义




    public static final String USER_NAME = "name";
    //当前主题id
    public static final String THEME_CURRENT_ID = "theme_current_id";


    //MyAPP.getContext() 是你的Application里面的一个Context
    private SharedPreferencesUtils() {
        share = LuckyMusicApp.sInstance.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        editor = share.edit();
    }

    /**
     * 单例模式
     */
    private static SharedPreferencesUtils instance;//单例模式 双重检查锁定
    public static SharedPreferencesUtils getInstance() {
        if (instance == null) {
            synchronized (SharedPreferencesUtils.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtils();
                }
            }
        }
        return instance;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void putInt(String spName, int value) {
        editor.putInt(spName, value);
        editor.commit();
    }


    public int getInt(String spName) {
        return getInt(spName, 0);
    }

    public int getInt(String spName, int defaultvalue) {
        return share.getInt(spName, defaultvalue);
    }

    /**
     * ------- String ---------
     */
    public void putString(String spName, String value) {
        editor.putString(spName, value);
        editor.commit();
    }

    public String getString(String spName, String defaultvalue) {
        return share.getString(spName, defaultvalue);
    }

    public String getString(String spName) {
        return share.getString(spName, "");
    }


    /**
     * ------- boolean ---------
     */
    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return share.getBoolean(key, defValue);
    }

    /**
     * ------- float ---------
     */
    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public float getFloat(String key, float defValue) {
        return share.getFloat(key, defValue);
    }


    /**
     * ------- long ---------
     */
    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key, long defValue) {
        return share.getLong(key, defValue);
    }

    /**
     * 清空SP里所有数据 谨慎调用
     */
    public void clear() {
        editor.clear();//清空
        editor.commit();//提交
    }

    /**
     * 删除SP里指定key对应的数据项
     *
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);//删除掉指定的值
        editor.commit();//提交
    }

    /**
     * 查看sp文件里面是否存在此 key
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return share.contains(key);
    }
}
