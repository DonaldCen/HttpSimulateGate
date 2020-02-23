package com.xuanwu.ump.HttpSimulateGate.entity.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020-02-22
 * @Version 1.0.0
 *
 * clazz CDATA #REQUIRED
 *         type (init|parameter|url|validation|parse|user) "user"
 */
@XStreamAlias("handler")
public class HandlerConfig {
    @XStreamAsAttribute
    @XStreamAlias("clazz")
    private String clazz;
    @XStreamAsAttribute
    @XStreamAlias("type")
    private String type;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
