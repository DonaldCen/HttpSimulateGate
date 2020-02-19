package com.xuanwu.ump.HttpSimulateGate.entity.config;

import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class RequestXmlPath extends HSHttpHelperConfigData {
    public RequestXmlPath(Map<String, Object> data) {
        super(data);
    }

    public String getPath() {
        return getValue("value");
    }
}
