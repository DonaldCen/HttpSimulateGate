package com.xuanwu.ump.request;

import com.xuanwu.ump.HSHttpHelperXmlConfig;
import com.xuanwu.ump.entity.config.RequestConfigData;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/19
 * @Version 1.0.0
 */
public class HSHttpRequestFactory {
    public static HSHttpRequest getHttpRequest(String name) throws Exception {
        RequestConfigData requestConfigData = HSHttpHelperXmlConfig.getInstance().getRequestConfigData(name);
        HSXmlConfigHttpRequest request = new HSXmlConfigHttpRequest(requestConfigData);
        return request;
    }
}
