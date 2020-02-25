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
        return null;
    }

}
