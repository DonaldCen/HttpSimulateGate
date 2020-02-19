package com.xuanwu.ump.HttpSimulateGate.entity.config;

import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
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
