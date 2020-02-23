package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020-02-22
 * @Version 1.0.0
 *
 * QueueCapacity CDATA #IMPLIED
 *         CorePoolSize CDATA #IMPLIED
 *         MaxPoolSize CDATA #IMPLIED
 *         KeepAliveSeconds CDATA #IMPLIED
 */
@XStreamAlias("pool")
public class PoolConfig {
    @XStreamAsAttribute
    @XStreamAlias("QueueCapacity")
    private String queueCapacity;
    @XStreamAsAttribute
    @XStreamAlias("CorePoolSize")
    private String corePoolSize;
    @XStreamAsAttribute
    @XStreamAlias("MaxPoolSize")
    private String maxPoolSize;
    @XStreamAsAttribute
    @XStreamAlias("KeepAliveSeconds")
    private String keepAliveSeconds;

    public String getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(String queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public String getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(String corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public String getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(String maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public String getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(String keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }
}
