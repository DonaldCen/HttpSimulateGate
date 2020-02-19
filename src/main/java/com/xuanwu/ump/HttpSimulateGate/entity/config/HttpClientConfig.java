package com.xuanwu.ump.HttpSimulateGate.entity.config;

import java.util.Map;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/18
 * @Version 1.0.0
 */
public class HttpClientConfig extends HSHttpHelperConfigData {

    public HttpClientConfig(Map<String, Object> data) {
        super(data);
    }

    public String getHttpCharset() {
        return getValue("http:charset");
    }

    public int getConnectionTimeout() {
        return getInt("http:connection-timeout");
    }

    public int getSocketTimeout() {
        return getInt("http:socket-timeout");
    }

    public int getPoolQueueCapacity() {
        return getInt("pool:QueueCapacity");
    }

    public int getPoolMaxPoolSize() {
        return getInt("pool:MaxPoolSize");
    }

    public int getCorePoolSize() {
        return getInt("pool:CorePoolSize");
    }

    public int getKeepAliveSeconds() {
        return getInt("pool:KeepAliveSeconds");
    }

}
