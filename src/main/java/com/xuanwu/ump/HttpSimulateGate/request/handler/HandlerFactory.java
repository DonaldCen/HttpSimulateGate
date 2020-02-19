package com.xuanwu.ump.HttpSimulateGate.request.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class HandlerFactory {
    private static Map<Object, Object> handlersMap = new HashMap<Object, Object>();

    private HandlerFactory() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T finadHandler(Class<T> clazz) throws Exception {
        if (handlersMap.containsKey(clazz)) {
            return (T) handlersMap.get(clazz);
        } else {
            try {
                T handler = clazz.newInstance();
                handlersMap.put(clazz, handler);
                return handler;
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    public static <T> T finadHandler(Class<T> panentClass, String className) throws Exception {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new Exception(e);
        }
        if (handlersMap.containsKey(clazz)) {
            return (T) handlersMap.get(clazz);
        } else {
            try {
                T handler = (T) clazz.newInstance();
                handlersMap.put(clazz, handler);
                return handler;
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    public static boolean remove(Class<?> clazz, Object handle) {
        if (handlersMap.containsKey(clazz)) {
            handlersMap.remove(handle);
            return true;
        }
        return false;
    }
}
