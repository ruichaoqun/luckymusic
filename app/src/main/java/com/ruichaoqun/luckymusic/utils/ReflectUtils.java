package com.ruichaoqun.luckymusic.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {

    /**
     * 反射获取某一个属性
     * @param cls class类型
     * @param obj 获取的对象
     * @param str 属性名称
     * @return
     */
    public static Object getDeclaredField(Class<?> cls, Object obj, String str) {
        return getDeclaredField(false, cls, obj, str);
    }


    public static Object getDeclaredField(boolean z, Class<?> cls, Object obj, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (Exception e2) {
            if (z) {
                throw new RuntimeException(e2);
            }
            e2.printStackTrace();
            return null;
        }
    }

    public static Object invoke(Class<?> cls, String str, Class<?>[] clsArr, Object obj, Object... objArr) {
        return invoke(false, cls, str, clsArr, obj, objArr);
    }


    public static Object invoke(boolean throwException, Class<?> cls, String str, Class<?>[] clsArr, Object obj, Object... objArr) {
        try {
            Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(obj, objArr);
        } catch (Exception e2) {
            if (!throwException) {
                e2.printStackTrace();
                return null;
            }
            throw new RuntimeException(e2);
        }
    }


}
