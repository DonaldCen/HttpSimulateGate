package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020-02-22
 * @Version 1.0.0
 */
@XStreamAlias("response-config")
public class RequestConfig {
    @XStreamImplicit(itemFieldName="httpclient-config")
    private HttpClientConfig config;
    @XStreamImplicit(itemFieldName="default-handlers")
    private DefaultHandlers handlers;


}
