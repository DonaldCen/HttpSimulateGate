package com.xuanwu.ump.entity.config;

import java.util.Map;

/**
 * Created by Administrator on 15-12-29.
 */
public class HandlerData extends HSHttpHelperConfigData {
    private String type;
    private String clazz;

    public HandlerData(Map<String, Object> data) {
        super(data);
    }

    public String getType() {
        return getValue("type");
    }

    public String getClazz() {
        return getValue("clazz");
    }

}
