package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.xuanwu.ump.HttpSimulateGate.entity.XmlConfig;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020-02-22
 * @Version 1.0.0
 */
@XStreamAlias("request-config")
public class RequestConfig extends XmlConfig {
    @XStreamAsAttribute
    @XStreamAlias("httpclient-config")
    private HttpClientConfig config;
    @XStreamAsAttribute
    @XStreamAlias("default-handlers")
    private DefaultHandlers handlers;
    private RequestsTagConfig requests;
    @XStreamAsAttribute
    @XStreamAlias("request-xml")
    private RequestXmlConfig xmlPathConfig;

    public HttpClientConfig getConfig() {
        return config;
    }

    public void setConfig(HttpClientConfig config) {
        this.config = config;
    }

    public DefaultHandlers getHandlers() {
        return handlers;
    }

    public void setHandlers(DefaultHandlers handlers) {
        this.handlers = handlers;
    }

    public RequestsTagConfig getRequests() {
        return requests;
    }

    public void setRequests(RequestsTagConfig requests) {
        this.requests = requests;
    }

    public RequestXmlConfig getXmlPathConfig() {
        return xmlPathConfig;
    }

    public void setXmlPathConfig(RequestXmlConfig xmlPathConfig) {
        this.xmlPathConfig = xmlPathConfig;
    }

    @Override
    public Type getType() {
        return Type.REQUEST;
    }
}
