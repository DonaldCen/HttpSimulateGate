package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020-02-22
 * @Version 1.0.0
 *
 * charset CDATA #IMPLIED
 * connection-timeout CDATA #IMPLIED
 * socket-timeout CDATA #IMPLIED
 */
@XStreamAlias("http")
public class HttpConfig {
    @XStreamAsAttribute
    @XStreamAlias("charset")
    private String charset;
    @XStreamAsAttribute
    @XStreamAlias("connection-timeout")
    private String connectionTimeout;
    @XStreamAsAttribute
    @XStreamAlias("socket-timeout")
    private String socketTimeout;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(String connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(String socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
