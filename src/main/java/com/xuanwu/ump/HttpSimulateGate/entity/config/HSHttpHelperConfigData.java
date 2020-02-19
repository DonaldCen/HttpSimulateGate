package com.xuanwu.ump.HttpSimulateGate.entity.config;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class HSHttpHelperConfigData {
    protected Map<String, Object> data = null;

    public HSHttpHelperConfigData(Map<String, Object> data) {
        this.data = data;
    }

    public Object getObject(Map<String, Object> dataMap,String key) {
        if (!StringUtils.isEmpty(key)) {
            if (key.indexOf(":") != -1) {
                String[] l = key.split(":");
                String k1 = l[0];
                String k2 = l[1];
                Object obj = dataMap.get(k1);
                if (obj != null) {
                    if (obj instanceof Map) {
                        Map map = (Map) obj;
                        if (map.containsKey(k2)) {
                            return map.get(k2);
                        }
                    }
                }
            }
        }
        return dataMap.get(key);
    }
    public Object getObject(String key) {
        return getObject(data,key);
    }

    /**
     * 获取值，支持:为内容引用符
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        return getValue(data, key);
    }

    public String getValue(Map<String, Object> dataMap, String key) {
        String value = null;
        if (!StringUtils.isEmpty(key)) {
            if (key.indexOf(":") != -1) {
                String[] l = key.split(":");
                String k1 = l[0];
                String k2 = l[1];
                Object obj = dataMap.get(k1);
                if (obj != null) {
                    if (obj instanceof Map) {
                        Map map = (Map) obj;
                        if (map.containsKey(k2)) {
                            value = String.valueOf(map.get(k2));
                        }
                    }
                }
            } else {
                if (dataMap.containsKey(key)) {
                    value = String.valueOf(dataMap.get(key));
                }
            }
        }
        return value;
    }

    public int getInt(String key) {
        try {
            return Integer.valueOf(getValue(key));
        } catch (Exception e) {
            return -1;
        }
    }

    public int getInt(Map<String, Object> dataMap, String key) {
        try {
            return Integer.valueOf(getValue(dataMap, key));
        } catch (Exception e) {
            return -1;
        }
    }

    public String toString() {
        return this.data.toString();
    }
}
