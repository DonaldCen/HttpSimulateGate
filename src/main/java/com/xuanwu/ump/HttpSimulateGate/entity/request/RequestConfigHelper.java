package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.xuanwu.ump.HttpSimulateGate.common.ListUtil;
import com.xuanwu.ump.HttpSimulateGate.entity.config.HandlerData;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/24
 * @Version 1.0.0
 */
public class RequestConfigHelper {
    private static final String DEFAULT_CHARSET = "utf-8";
    private static final String CONNECT_TIMEOUT = "15000";
    private static final String SOCKET_TIMEOUT = "15000";
    private static final int CORE_POOL_SIZE = 50;
    private static final int MAX_POOL_SIZE = 100;
    private static final int KEEP_ALIVE_SECONDS = 300;
    private static final int QUEUE_CAPACITY = 150;
    private static RequestConfig requestConfig;



    public RequestConfigHelper(RequestConfig requestConfig){
        this.requestConfig = requestConfig;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    /**
     * 获取网络配置
     * @return
     */
    public static HttpClientConfig getHttpClientConfig(){
        return requestConfig.getConfig();
    }

    /**
     * 获取 编码方式
     * @return
     */
    public String getCharset(){
        if(requestConfig.getConfig() != null
                && requestConfig.getConfig().getHttp() != null){
            return requestConfig.getConfig().getHttp().getCharset();
        }
        return DEFAULT_CHARSET;
    }

    public DefaultHandlers getDefaultHandlers(){
        if(requestConfig.getHandlers() != null){
            return requestConfig.getHandlers();
        }
        return new DefaultHandlers();
    }

    public String getConnectTimeout(){
        HttpClientConfig clientConfig = getHttpClientConfig();
        if(clientConfig != null){
            return clientConfig.getHttp().getConnectionTimeout();
        }
        return CONNECT_TIMEOUT;
    }

    public String getSocKetTimeout(){
        HttpClientConfig clientConfig = getHttpClientConfig();
        if(clientConfig != null){
            return clientConfig.getHttp().getSocketTimeout();
        }
        return SOCKET_TIMEOUT;
    }

    public int getKeepAliveSeconds(){
        HttpClientConfig clientConfig = getHttpClientConfig();
        if(clientConfig != null){
            return getInt(clientConfig.getPool().getKeepAliveSeconds());
        }
        return KEEP_ALIVE_SECONDS;
    }

    public int getCorePoolSize(){
        HttpClientConfig clientConfig = getHttpClientConfig();
        if(clientConfig != null){
            return getInt(clientConfig.getPool().getCorePoolSize());
        }
        return CORE_POOL_SIZE;
    }

    public int getMaxPoolSize(){
        HttpClientConfig clientConfig = getHttpClientConfig();
        if(clientConfig != null){
            return getInt(clientConfig.getPool().getMaxPoolSize());
        }
        return MAX_POOL_SIZE;
    }

    public int getQueueCapacity(){
        HttpClientConfig clientConfig = getHttpClientConfig();
        if(clientConfig != null){
            return getInt(clientConfig.getPool().getQueueCapacity());
        }
        return QUEUE_CAPACITY;
    }

    public int getInt(String key) {
        try {
            return Integer.valueOf(key);
        } catch (Exception e) {
            return -1;
        }
    }

}
