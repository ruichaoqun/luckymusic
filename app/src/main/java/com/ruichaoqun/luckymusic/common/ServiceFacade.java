package com.ruichaoqun.luckymusic.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rui Chaoqun
 * @date :2019/10/12 15:37
 * description:
 */
public class ServiceFacade {
    public static final String LIVE_SERVICE = "playlive";
    private static Map<Class, Object> sCachedLoader = new ConcurrentHashMap();
    private static Map<String, Object> sServiceMap = new HashMap();

    @Deprecated
    public static void put(String str, Object obj) {
        sServiceMap.put(str, obj);
    }

    @Deprecated
    public static Object get(String str) {
        return sServiceMap.get(str);
    }

    @Deprecated
    public static <T> T get(String str, Class<T> cls) {
        return (T) get(str);
    }

    @Deprecated
    public static Map<String, Object> getServiceMap() {
        return sServiceMap;
    }

    public static void setServiceMap(Map<String, Object> map) {
        sServiceMap = map;
    }

//    public static synchronized void put(Class cls, QueueData cVar) {
//        synchronized (ServiceFacade.class) {
//            if (!(cVar == null || cls == null)) {
//                sCachedLoader.put(cls, cVar);
//            }
//        }
//    }

    public static synchronized <T> T get(Class<T> cls) {
        T t;
        synchronized (ServiceFacade.class) {
            t = (T) sCachedLoader.get(cls);
        }
        return t;
    }

}
